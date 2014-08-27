package cbs.view.cd.irt;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.code.dao.ActirtMapper;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actirt;
import cbs.repository.code.model.ActirtExample;
import cbs.repository.code.model.Actsct;
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
import javax.faces.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 利率码表增删改查
 */
@ManagedBean
@ViewScoped
public class ActirtBean {

    private static final Logger logger = LoggerFactory.getLogger(ActirtBean.class);
    private String curcde;
    private String effdat;
    private String irtkd;
    private String irtnam;
    private List<Actirt> irtList;
    private Actirt actirt = new Actirt();
    private Actirt irtParam;
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private String formerAction = null;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        formerAction = params.get("action");
        if (formerAction != null) {
            initPage();
            if (actirt == null || actirt.getCurcde() == null) {
                if (!"addBean".equals(formerAction)) {
                    String curcde = params.get("curcde");
                    String effdat = params.get("effdat");
                    String irtkd1 = params.get("irtkd1");
                    String irtkd2 = params.get("irtkd2");
                    effdat = parseDefaultDate(effdat, Locale.US);
                    SqlSession session = ibatisManager.getSessionFactory().openSession();
                    ActirtMapper irtMapper = session.getMapper(ActirtMapper.class);
                    this.actirt = irtMapper.selectUniqueIrt(curcde, effdat, irtkd1, irtkd2, ACEnum.RECSTS_VALID.getStatus());
                }
            }
            /* else {
                actirt.setCurflg("0");   // 当天使用标记为0
                actirt.setModflg("Y");  // 当天修改标记为Y
                // 浮动上下限
                actirt.setIrtsph(new BigDecimal(0));
                actirt.setIrtspl(new BigDecimal(0));
                actirt.setSprflg("0");      // 浮动标记
                actirt.setIrttrm((short)360);    // 期限   一年360
                actirt.setTrmunt("Y");    //  期限单位年Y
            }*/
        } else {
            if (context.getRenderResponse()) {
                queryRecords();
            }
        }
    }

    public void queryRecords() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActirtMapper mapper = session.getMapper(ActirtMapper.class);
            String strDate = emptyToNull(effdat);
            String qryDate = strDate == null ? this.formatDate(new Date()) : strDate;
            String strCurcde = emptyToNull(curcde);
            String qryCurcde = strCurcde == null ? "001" : strCurcde;
            irtkd = irtkd != null ? irtkd.toUpperCase() : null;
            irtList = mapper.selectIrtByCondition(qryCurcde, emptyToNull(irtkd), emptyToNull(irtnam));
            if (irtList == null || irtList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
            }
            setTableToFirstPage("resultForm:resultDataTable");
        } catch (Exception e) {
            logger.error("利率查询失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M932");
        } finally {
            closeSession(session);
        }
    }

    // 添加

    public void insert() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActirtMapper mapper = session.getMapper(ActirtMapper.class);
            // 验证利率是否已存在
            if (isExist(mapper)) {
                MessageUtil.addErrorWithClientID("msgs", "WD02");
                return;
            }
            OperatorManager oper = OnlineService.getOperatorManager();
            // 新增利率 生效
            //Actsct
            ActsctMapper sctMapper = session.getMapper(ActsctMapper.class);
            Actsct sct = sctMapper.selectByPrimaryKey((short) 8);
            if (sct.getCrndat().after(actirt.getEffdat())) {
                actirt.setCurflg("1");
            } else {
                actirt.setCurflg("0");
            }
            //   新增利率不可设置为Y，只有利率修改的时候才会将其设置为Y
            // 利率修改时将
            // actirt.setModflg("Y");
            actirt.setCredat(new Date());
            actirt.setCretlr(oper.getOperatorId());
            actirt.setRecsts(ACEnum.RECSTS_VALID.getStatus());
            int rtnInt = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (mapper.selectUniqueIrt(actirt.getCurcde(), sdf.format(actirt.getEffdat()), actirt.getIrtkd1(), actirt.getIrtkd2(),
                    ACEnum.RECSTS_INVALID.getStatus()) != null) {
                ActirtExample example = new ActirtExample();
                example.createCriteria().andCurcdeEqualTo(actirt.getCurcde()).andEffdatEqualTo(actirt.getEffdat())
                        .andIrtkd1EqualTo(actirt.getIrtkd1()).andIrtkd2EqualTo(actirt.getIrtkd2());
                rtnInt = mapper.updateByExampleSelective(actirt, example);
            } else {
                // 原利率置为无效

                if (sct.getCrndat().after(actirt.getEffdat())) {
                    Actirt irt = mapper.selectCurIrt(actirt.getCurcde(), actirt.getIrtkd1(), actirt.getIrtkd2());
                    irt.setCurflg("0");
                    irt.setRecsts(ACEnum.RECSTS_INVALID.getStatus());
                    ActirtExample example = new ActirtExample();
                    example.createCriteria().andCurcdeEqualTo(actirt.getCurcde()).andCurflgEqualTo(ACEnum.CLRFLG_TRUE.getStatus())
                            .andIrtkd1EqualTo(actirt.getIrtkd1()).andIrtkd2EqualTo(actirt.getIrtkd2());
                    mapper.updateByExample(irt, example);
                }

                rtnInt = mapper.insertSelective(actirt);
            }
            if (rtnInt == 1) {
                session.commit();
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

    public boolean isExist(ActirtMapper mapper) {
        boolean isExist = false;
        Actirt tempIrt = mapper.selectUniqueIrt(actirt.getCurcde(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(actirt.getEffdat())
                , actirt.getIrtkd1(), actirt.getIrtkd2(), ACEnum.RECSTS_VALID.getStatus());
        if (tempIrt != null) {
            isExist = true;
        }
        return isExist;
    }

    public void update() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActirtMapper irtMapper = session.getMapper(ActirtMapper.class);
            ActirtExample irtExample = new ActirtExample();
            irtExample.createCriteria().andCurcdeEqualTo(actirt.getCurcde()).
                    andIrtkd1EqualTo(actirt.getIrtkd1()).andIrtkd2EqualTo(actirt.getIrtkd2())
                    .andEffdatEqualTo(actirt.getEffdat());
            actirt.setCretlr(OnlineService.getOperatorManager().getOperatorId());
            actirt.setCredat(new Date());
            int rtnNum = irtMapper.updateByExample(actirt, irtExample);
            updByRtnNum(session, rtnNum);
        } catch (Exception e) {
            session.rollback();
            logger.error("修改利率码信息失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M803");
        } finally {
            closeSession(session);
        }
    }

    public void delete() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {

            ActirtMapper irtMapper = session.getMapper(ActirtMapper.class);
            ActirtExample irtExample = new ActirtExample();
            irtExample.createCriteria().andCurcdeEqualTo(actirt.getCurcde()).
                    andIrtkd1EqualTo(actirt.getIrtkd1()).andIrtkd2EqualTo(actirt.getIrtkd2())
                    .andEffdatEqualTo(actirt.getEffdat());
            actirt.setRecsts(ACEnum.RECSTS_INVALID.getStatus());
            actirt.setCretlr(OnlineService.getOperatorManager().getOperatorId());
            actirt.setCredat(new Date());
            int rtnNum = irtMapper.updateByExample(actirt, irtExample);
            resByRtnNum(session, rtnNum);
        } catch (Exception e) {
            session.rollback();
            logger.error("删除利率码信息失败：" + e.getMessage());
            MessageUtil.addErrorWithClientID("msgs", "M804");
        } finally {
            closeSession(session);
        }
    }

    public String back() {
        if ("showQryDetail".equals(formerAction)) {
            return "actirtQry?faces-redirect=true";
        }
        return "actirtMng?faces-redirect=true";
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

    private void resByRtnNum(SqlSession whichSession, int rtnNum) {
        if (rtnNum == 1) {
            whichSession.commit();
            MessageUtil.addInfoWithClientID("msgs", "W004");
            logger.info("删除利率码成功");
        } else {
            whichSession.rollback();
            logger.error("删除利率码时，更新记录状态未成功");
            MessageUtil.addErrorWithClientID("msgs", "M804");
        }
    }

    public String action() {
        return "actirtBean";
    }

    public void makeParams(ActionEvent ae) {

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

    private void updByRtnNum(SqlSession whichSession, int rtnNum) {
        if (rtnNum == 1) {
            whichSession.commit();
            MessageUtil.addInfoWithClientID("msgs", "WD01");
            logger.info("修改利率码成功");
        } else {
            whichSession.rollback();
            logger.error("修改利率码时，更新未成功");
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

    private String parseDefaultDate(String strDate, Locale locale) {
        String cmnDate = null;
        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
        try {
            Date date = formatter.parse(strDate);
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cmnDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cmnDate;
    }

    private void updateIrtDate() {
        /*new SimpleDateFormat(Locale.US).;*/
    }

    public String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    // 关闭SqlSession

    private void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public Actirt getActirt() {
        return actirt;
    }

    public void setActirt(Actirt actirt) {
        this.actirt = actirt;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = curcde;
    }

    public String getEffdat() {
        return effdat;
    }

    public void setEffdat(String effdat) {
        this.effdat = effdat;
    }

    public String getIrtkd() {
        return irtkd;
    }

    public void setIrtkd(String irtkd) {
        this.irtkd = irtkd;
    }

    public String getIrtnam() {
        return irtnam;
    }

    public void setIrtnam(String irtnam) {
        this.irtnam = irtnam;
    }

    public List<Actirt> getIrtList() {
        return irtList;
    }

    public void setIrtList(List<Actirt> irtList) {
        this.irtList = irtList;
    }

    public Actirt getactirt() {
        return actirt;
    }

    public void setactirt(Actirt actirt) {
        this.actirt = actirt;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public Actirt getIrtParam() {
        return irtParam;
    }

    public void setIrtParam(Actirt irtParam) {
        this.irtParam = irtParam;
    }
}