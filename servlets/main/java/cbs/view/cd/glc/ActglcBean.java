package cbs.view.cd.glc;

import cbs.common.IbatisManager;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.code.dao.ActglcMapper;
import cbs.repository.code.model.Actglc;
import cbs.repository.code.model.ActglcExample;
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
 * 总账码表增删改查
 */
@ManagedBean
@ViewScoped
public class ActglcBean {

    private static final Logger logger = LoggerFactory.getLogger(ActglcBean.class);
    private String glcode;
    private String glcnam;
    private List<Actglc> glcList;
    private Actglc actglc = new Actglc();

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
            ActglcMapper mapper = session.getMapper(ActglcMapper.class);
            this.actglc = mapper.selectGlcByCode(param,ACEnum.RECSTS_VALID.getStatus());
        }
    }

    public void queryRecords() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActglcMapper mapper = session.getMapper(ActglcMapper.class);
            ActglcExample example = makeCondition();
            example.setOrderByClause("glcode");
            glcList = mapper.selectByExample(example);
            if (glcList == null || glcList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
            }
            setTableToFirstPage("glcMsgForm:glcMsgDataTable");
        } catch (Exception e) {
            logger.error("总账码查询失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M932");
        }
        finally {
            closeSession(session);
        }
    }
    // 添加

    public void insert() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActglcMapper mapper = session.getMapper(ActglcMapper.class);
            OperatorManager oper = OnlineService.getOperatorManager();
            actglc.setUpddat(new Date());
            actglc.setAmdtlr(oper.getOperatorId());
            actglc.setRecsts(ACEnum.RECSTS_VALID.getStatus());
            int rtnInt = 0;
            if (query(mapper, actglc) != null) {
                ActglcExample example = new ActglcExample();
                example.createCriteria().andGlcodeEqualTo(actglc.getGlcode());
                rtnInt =  mapper.updateByExample(actglc, example);
            } else {
                rtnInt = mapper.insertSelective(actglc);
            }
            if (rtnInt == 1) {
                    session.commit();
                    queryRecords();
                    MessageUtil.addInfo("W005");
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

    private ActglcExample makeCondition() {
        ActglcExample example = new ActglcExample();
        String recsts = ACEnum.RECSTS_VALID.getStatus();
        if (!isEmpty(glcode) && !isEmpty(glcnam)) {
            example.createCriteria().andRecstsEqualTo(recsts).andGlcodeEqualTo(glcode.trim()).andGlcnamLike("%" + glcnam.trim() + "%");
        } else if (!isEmpty(glcode)) {
            example.createCriteria().andRecstsEqualTo(recsts).andGlcodeEqualTo(glcode.trim());
        } else if (!isEmpty(glcnam)) {
            example.createCriteria().andRecstsEqualTo(recsts).andGlcnamLike("%" + glcnam.trim() + "%");
        } else {
            example.createCriteria().andRecstsEqualTo(recsts);
        }
        return example;
    }
    // 更新

    public void update() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActglcMapper mapper = session.getMapper(ActglcMapper.class);
            OperatorManager oper = OnlineService.getOperatorManager();
            actglc.setUpddat(new Date());
            actglc.setAmdtlr(oper.getOperatorId());
            ActglcExample glcExample = new ActglcExample();
            glcExample.createCriteria().andGlcodeEqualTo(actglc.getGlcode());
            int rtnNum = mapper.updateByExample(actglc, glcExample);
            updByRtnNum(session, rtnNum);

        } catch (Exception e) {
            session.rollback();
            logger.error("修改总账码信息失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M803");
        }
        finally {
            closeSession(session);
        }
    }

    // 逻辑删除  -- 更新recsts字段值 为  I(无效)

    public void delete() {
        String delCode = this.actglc.getGlcode();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActglcMapper glcMapper = session.getMapper(ActglcMapper.class);
            ActglcExample glcExample = new ActglcExample();
            glcExample.createCriteria().andGlcodeEqualTo(delCode);
            actglc.setRecsts(ACEnum.RECSTS_INVALID.getStatus());
            actglc.setAmdtlr(OnlineService.getOperatorManager().getOperatorId());
            actglc.setUpddat(new Date());
            int rtnNum = glcMapper.updateByExample(actglc, glcExample);
            resByRtnNum(session, rtnNum);
        } catch (Exception e) {
            session.rollback();
            logger.error("删除总账码信息失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M804");
        }
        finally {
            closeSession(session);
        }
    }

    private Actglc query(ActglcMapper mapper, Actglc actglc) {
        Actglc glc = null;
        glc = mapper.selectGlcByCode(actglc.getGlcode(),ACEnum.RECSTS_INVALID.getStatus());
        return glc;
    }

    private void resByRtnNum(SqlSession whichSession, int rtnNum) {
        if (rtnNum == 1) {
            whichSession.commit();
            MessageUtil.addInfoWithClientID("msgs", "W004");
            queryRecords();
            logger.info("删除总账码成功");
        } else {
            whichSession.rollback();
            logger.error("删除总账码时，更新记录状态未成功");
            MessageUtil.addErrorWithClientID("msgs", "M804");
        }
    }

    public String action() {
        return "actglcBean";
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
            ((HtmlInputText) viewRoot.findComponent("inputform:glcode")).setDisabled(true);
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

    private void updByRtnNum(SqlSession whichSession, int rtnNum) {
        if (rtnNum == 1) {
            whichSession.commit();
            MessageUtil.addInfoWithClientID("msgs", "WD01");
            queryRecords();
            logger.info("修改总账码成功");
        } else {
            whichSession.rollback();
            logger.error("修改总账码时，更新未成功");
            MessageUtil.addErrorWithClientID("msgs", "M803");
        }
    }
    // 返回按钮的响应，跳转到查询页面

    public String back() {
        if ("showQryDetail".equals(formerAction)) {
            return "actglcQry?faces-redirect=true";
        }
        return "actglcMng?faces-redirect=true";
    }
    // 设置为第一页

    private void setTableToFirstPage(String formDT) {

        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(formDT);
        if (dataTable != null) {
            dataTable.setFirst(0);
            dataTable.setPage(1);
        }
    }

    private boolean isEmpty(String field) {
        return (field == null || "".equalsIgnoreCase(field)) ? true : false;
    }

    // 关闭SqlSession

    private void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public Actglc getActglc() {
        return actglc;
    }

    public void setActglc(Actglc actglc) {
        this.actglc = actglc;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getGlcnam() {
        return glcnam;
    }

    public void setGlcnam(String glcnam) {
        this.glcnam = glcnam;
    }

    public List<Actglc> getGlcList() {
        return glcList;
    }

    public void setGlcList(List<Actglc> glcList) {
        this.glcList = glcList;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }
}
