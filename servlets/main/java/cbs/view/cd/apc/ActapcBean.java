package cbs.view.cd.apc;

import cbs.common.IbatisManager;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.code.dao.ActapcMapper;
import cbs.repository.code.model.Actapc;
import cbs.repository.code.model.ActapcExample;
import cbs.common.OnlineService;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 核算码表增删改查
 */
@ManagedBean
@ViewScoped
public class ActapcBean {

    private static final Logger logger = LoggerFactory.getLogger(ActapcBean.class);
    private String glcode;
    private String apcode;
    private String apcnam;
    private String apctyp;
    private List<Actapc> apcList;
    private Actapc actapc = new Actapc();
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private String formerAction = null;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String param = null;
        param = params.get("param");
        formerAction = params.get("action");
        if (formerAction != null) {
            initPage();
            initContentByParam(param);
        } else {
            if (context.getRenderResponse()) {
                queryRecords();
            }
        }
    }

    private void initContentByParam(String param) {
        if (!"addBean".equals(formerAction)) {
            SqlSession session = ibatisManager.getSessionFactory().openSession();
            ActapcMapper mapper = session.getMapper(ActapcMapper.class);
            this.actapc = mapper.selectApcByCode(param,ACEnum.RECSTS_VALID.getStatus());
        }
    }

    public void queryRecords() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActapcMapper mapper = session.getMapper(ActapcMapper.class);
            apcList = mapper.selectApcByCondition(emptyToNull(glcode), emptyToNull(apctyp),
                    emptyToNull(apcode), emptyToNull(apcnam));
            if (apcList == null || apcList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
            }
            setTableToFirstPage("resultForm:resultDataTable");
        } catch (Exception e) {
            logger.error("核算码查询失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M932");
        }
        finally {
            closeSession(session);
        }
    }
    // TODO 尽量通用

    private void initPage() {

        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        CommandButton backBtn = (CommandButton) viewRoot.findComponent("inputform:back");
        CommandButton resetBtn = (CommandButton) viewRoot.findComponent("inputform:reset");
        CommandButton cmbtn = (CommandButton) viewRoot.findComponent("inputform:actionbtn");
        if (!"addBean".equals(formerAction) && !"editContent".equals(formerAction)) {
            Iterator<UIComponent> components = null;
            components = viewRoot.findComponent("inputform:basePanel").
                    findComponent("basePanelGrid").getFacetsAndChildren();
            this.setComponentsToReadonly(components);
            resetBtn.setRendered(false);
        }

        if ("logicDelete".equals(formerAction)) {
            cmbtn.setValue("删除");
        } else if ("showDetail".equals(formerAction) || "showQryDetail".equals(formerAction)) {
            cmbtn.setRendered(false);
        } else if ("editContent".equals(formerAction)) {
            resetBtn.setRendered(false);
            ((HtmlInputText) viewRoot.findComponent("inputform:apcode")).setDisabled(true);
        }
    }

    private void setComponentsToReadonly(Iterator<UIComponent> components) {
        while (components.hasNext()) {
            UIComponent child = components.next();
            if (child instanceof HtmlInputText) {
                ((HtmlInputText) child).setReadonly(true);
            } else if (child instanceof HtmlSelectOneMenu) {
                ((HtmlSelectOneMenu) child).setDisabled(true);
            }
        }
    }
    // 添加

    public void insert() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActapcMapper mapper = session.getMapper(ActapcMapper.class);
            OperatorManager oper = OnlineService.getOperatorManager();

            actapc.setUpddat(new Date());
            actapc.setAmdtlr(oper.getOperatorId());
            actapc.setRecsts(ACEnum.RECSTS_VALID.getStatus());
            int rtnInt = 0;
            // 如果已存在此记录，则更新此记录数据
            if (query(mapper, actapc) != null) {
                ActapcExample example = new ActapcExample();
                example.createCriteria().andApcodeEqualTo(actapc.getApcode());
                rtnInt =  mapper.updateByExample(actapc, example);
            } else {
                rtnInt = mapper.insertSelective(actapc);
            }
            if (rtnInt == 1) {
                    session.commit();
                    MessageUtil.addInfo("W005");
                    queryRecords();
                } else {
                    session.rollback();
                    MessageUtil.addError("M802");
                }
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("M802");
            logger.error(e.getMessage());
        } finally {
            closeSession(session);
        }
    }
    // 更新

    public void update() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActapcMapper mapper = session.getMapper(ActapcMapper.class);
            OperatorManager oper = OnlineService.getOperatorManager();
            actapc.setUpddat(new Date());
            actapc.setAmdtlr(oper.getOperatorId());
            ActapcExample example = new ActapcExample();
            example.createCriteria().andApcodeEqualTo(actapc.getApcode());
            int rtnNum = mapper.updateByExample(actapc, example);
            updByRtnNum(session, rtnNum);
        } catch (Exception e) {
            session.rollback();
            logger.error("修改核算码信息失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M803");
        }
        finally {
            closeSession(session);
        }
    }

    public void delete() {
        String delCode = this.actapc.getApcode();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActapcMapper apcMapper = session.getMapper(ActapcMapper.class);
            ActapcExample apcExample = new ActapcExample();
            apcExample.createCriteria().andApcodeEqualTo(delCode);
            actapc.setRecsts(ACEnum.RECSTS_INVALID.getStatus());
            actapc.setAmdtlr(OnlineService.getOperatorManager().getOperatorId());
            actapc.setUpddat(new Date());
            int rtnNum = apcMapper.updateByExample(actapc, apcExample);
            resByRtnNum(session, rtnNum);
        } catch (Exception e) {
            session.rollback();
            logger.error("删除核算码信息失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M804");
        }
        finally {
            closeSession(session);
        }
    }

    private Actapc query(ActapcMapper mapper, Actapc actapc) {
        Actapc apc = null;
        apc = mapper.selectApcByCode(actapc.getApcode(),ACEnum.RECSTS_INVALID.getStatus());
        return apc;
    }

    private void resByRtnNum(SqlSession whichSession, int rtnNum) {
        if (rtnNum == 1) {
            whichSession.commit();
            MessageUtil.addInfoWithClientID("msgs", "W004");
            logger.info("删除核算码成功");
        } else {
            whichSession.rollback();
            logger.error("删除核算码时，更新记录状态未成功");
            MessageUtil.addErrorWithClientID("msgs", "M804");
        }
    }

    public String action() {
        return "actapcBean";
    }

    public void beanAction() {
        if ("logicDelete".equals(formerAction)) {
            this.delete();
        }
        if ("editContent".equals(formerAction)) {
            this.update();
        }
        if ("addBean".equals(formerAction)) {
            this.insert();
        }
    }

    // 返回按钮的响应，跳转到查询页面

    public String back() {
        if ("showQryDetail".equals(formerAction)) {
            return "actapcQry?faces-redirect=true";
        }
        return "actapcMng?faces-redirect=true";
    }

    private void updByRtnNum(SqlSession whichSession, int rtnNum) {
        if (rtnNum == 1) {
            whichSession.commit();
            MessageUtil.addInfoWithClientID("msgs", "WD01");
            logger.info("修改核算码成功");
        } else {
            whichSession.rollback();
            logger.error("修改核算码时，更新未成功");
            MessageUtil.addErrorWithClientID("msgs", "M803");
        }
    }

    private String emptyToNull(String qryField) {

        return (qryField != null && "".equalsIgnoreCase(qryField.trim())) || qryField == null ? null : qryField.trim();
    }
    // 设置为第一页

    private void setTableToFirstPage(String formDT) {

        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(formDT);
        if (dataTable != null) {
            dataTable.setFirst(0);
            dataTable.setPage(1);
        }
    }

    // 关闭SqlSession

    private void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public Actapc getActapc() {
        return actapc;
    }

    public void setActapc(Actapc actapc) {
        this.actapc = actapc;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getApcode() {
        return apcode;
    }

    public void setApcode(String apcode) {
        this.apcode = apcode;
    }

    public String getApcnam() {
        return apcnam;
    }

    public void setApcnam(String apcnam) {
        this.apcnam = apcnam;
    }

    public String getApctyp() {
        return apctyp;
    }

    public void setApctyp(String apctyp) {
        this.apctyp = apctyp;
    }

    public List<Actapc> getApcList() {
        return apcList;
    }

    public void setApcList(List<Actapc> apcList) {
        this.apcList = apcList;
    }

    public Actapc getactapc() {
        return actapc;
    }

    public void setactapc(Actapc actapc) {
        this.actapc = actapc;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public String getFormerAction() {
        return formerAction;
    }

    public void setFormerAction(String formerAction) {
        this.formerAction = formerAction;
    }
}