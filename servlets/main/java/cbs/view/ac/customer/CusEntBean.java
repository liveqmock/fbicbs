package cbs.view.ac.customer;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.customer.dao.ActcusMapper;
import cbs.repository.customer.dao.ActentMapper;
import cbs.repository.customer.model.Actcus;
import cbs.repository.customer.model.ActcusKey;
import cbs.repository.customer.model.Actent;
import cbs.repository.customer.model.ActentExample;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.primefaces.component.commandbutton.CommandButton;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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

@ManagedBean
@ViewScoped
public class CusEntBean {

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private Actcus actcus;
    private Actent actent;
    private String sysidt = SystemService.getSysidtAC();
    private String initCusidt;
    private String dbl_crdlim;  // 授信额度
    private String dbl_reglap; // 注册外币资本
    private String dbl_loclap; // 注册本币资本
    private String sh_eff;      // 效期
    private String formerAction = null;
    private static final Logger logger = Logger.getLogger(CusEntBean.class);

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String cusidt = null;
        cusidt = params.get("param");
        formerAction = params.get("action");
        if (formerAction != null) {
            initPage();
            initCusEntByCusidt(cusidt);
        } else {
            initCusEnt();
        }
    }

    /*
          保存新增客户信息
    */
    public void insert() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActcusMapper cusMapper = session.getMapper(ActcusMapper.class);
        try {
            actcus.setCrdlim(parseStringToMoney(this.dbl_crdlim));
            actent.setLoccap(parseStringToMoney(this.dbl_loclap));
            actent.setRegcap(parseStringToMoney(this.dbl_reglap));
            if (sh_eff != null && !"".equalsIgnoreCase(sh_eff)) {
                actent.setEffdur(Short.parseShort(this.sh_eff));
            } else {
                actent.setEffdur((short) 0);
            }
            int cusNum = cusMapper.insertSelective(actcus);
            ActentMapper entMapper = session.getMapper(ActentMapper.class);

            logger.info("Cusidt : " + actent.getCusidt());
            logger.info("Cusidt : " + actcus.getCusidt());
            actent.setCusidt(actcus.getCusidt());
            int entNum = entMapper.insertSelective(actent);
            if (cusNum == 1) {
                if (entNum == 1) {
                    session.commit();
                    MessageUtil.addInfo("W005");
                    this.showMsgsInfo("","客户号:" + actcus.getCusidt() + "  客户姓名:" + actcus.getCusnam());
                    init();    //清空界面
                } else {
                    showMsgsError("","客户扩展信息建立失败！");
                    throw new Exception("客户扩展信息建立失败！");
                }
            } else {
                showMsgsError("","客户基本信息建立失败！");
                throw new Exception("客户基本信息建立失败！");
            }
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("M802");
            this.showMsgsInfo("","客户号:" + actcus.getCusidt() + "  客户姓名:" + actcus.getCusnam());
            logger.error(e.getMessage(), e);
        }
        finally {
            closeSession(session);
        }
    }

    // 更新公司客户信息
    public void update() {
        actcus.setSysidt(sysidt);
        actent.setSysidt(sysidt);
        actent.setCusidt(actcus.getCusidt());
        actcus.setCrdlim(parseStringToMoney(dbl_crdlim));
        actent.setLoccap(parseStringToMoney(dbl_loclap));
        actent.setRegcap(parseStringToMoney(dbl_reglap));
        if (sh_eff != null && !"".equalsIgnoreCase(sh_eff)) {
            actent.setEffdur(Short.parseShort(sh_eff));
        } else {
            actent.setEffdur((short) 0);
        }
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActcusMapper cusMapper = session.getMapper(ActcusMapper.class);
            int cusNum = cusMapper.updateByPrimaryKeySelective(actcus);
            ActentMapper entMapper = session.getMapper(ActentMapper.class);
            ActentExample entExample = new ActentExample();
            entExample.createCriteria().andCusidtEqualTo(actent.getCusidt());
            int entNum = entMapper.updateByExampleSelective(actent, entExample);
            resByRtnNum(session, cusNum, entNum);
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("M803");
            logger.error(e.getMessage());
        } finally {
            closeSession(session);
        }
    }

    public void delete() {
        String cusidt = actcus.getCusidt();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActcusMapper cusMapper = session.getMapper(ActcusMapper.class);
            int rtnNum1 = cusMapper.deleteCusByCusidt(ACEnum.RECSTS_INVALID.getStatus(), cusidt);

            ActentMapper entMapper = session.getMapper(ActentMapper.class);
            int rtnNum2 = entMapper.deleteEntByCusidt(ACEnum.RECSTS_INVALID.getStatus(), cusidt);
            showMsgByRtnNum(session, rtnNum1, rtnNum2);
        } catch (Exception e) {
            session.rollback();
            logger.error("删除客户信息失败：" + e.getMessage());
            MessageUtil.addInfoWithClientID("msgs", "M804");
        }
        finally {
            closeSession(session);
        }
    }

    public void beanAction() {
        if ("logicDelete".equals(formerAction)) {
            this.delete();
        }
        if ("editContent".equals(formerAction)) {
            this.update();
        }
    }

    // 返回按钮的响应，跳转到查询页面
    public String back() {
        if ("showQryDetail".equals(formerAction)) {
            return "entcusQry?faces-redirect=true";
        }
        return "entcusQryEdit?faces-redirect=true";
    }

    // 1--更新成功记录的返回结果
    private void resByRtnNum(SqlSession whichSession, int one, int theOther) {
        if (one == 1 && theOther == 1) {
            whichSession.commit();
            MessageUtil.addInfoWithClientID("msgs", "WD01");
        } else {
            whichSession.rollback();
            MessageUtil.addError("M803");
        }
    }

    // 1--逻辑删除成功一条记录
    private void showMsgByRtnNum(SqlSession whichSession, int rtnNum1, int rtnNum2) {
        if (rtnNum1 == 1 && rtnNum2 == 1) {
            whichSession.commit();
            MessageUtil.addInfoWithClientID("msgs", "W004");
            showMsgsInfo("","已删除客户号:" + actcus.getCusidt() + "   客户名称:" + actcus.getCusnam());
        } else {
            whichSession.rollback();
            logger.error("删除客户时，更新记录状态未成功");
            MessageUtil.addErrorWithClientID("msgs", "M804");
        }
    }

    /*
           生成客户号
    */
    private String generateCusidt() {

        String newCusidt = null;
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActcusMapper mapper = session.getMapper(ActcusMapper.class);
            int maxCusidt = 0;
            maxCusidt = mapper.selectMaxCusidt();
            if (maxCusidt != 0 && maxCusidt < 8999999) {
                newCusidt = String.valueOf(maxCusidt + 1);
            } else {
                int minCusidt = 0;
                minCusidt = mapper.selectMinCusidt();
                do {
                    newCusidt = String.valueOf(minCusidt + 1);
                } while (mapper.selectCusCountById(newCusidt) != 0);
            }
        } catch (Exception e) {
            MessageUtil.addError("MH00");
            logger.error(e.getMessage());
            return null;
        } finally {
            closeSession(session);
        }
        return addZeroTo(newCusidt);
    }

    // 不足七位则前面补0
    private String addZeroTo(String param) {
        if (param == null || param.length() > 7) {
            return null;
        }
        int idLength = param.length();
        if (idLength < 7) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 1; i <= 7 - idLength; i++) {
                buffer.append("0");
            }
            param = buffer.append(param).toString();
        }
        return param;
    }

    private void initPage() {

        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        CommandButton cmbtn = null;
        if(!"editContent".equals(formerAction)){
            cmbtn = (CommandButton) viewRoot.findComponent("inputform:actionbtn");
            Iterator<UIComponent> components = null;
            components = viewRoot.findComponent("inputform:basePanel").
                    findComponent("basePanelGrid").getFacetsAndChildren();
            this.setComponentsToReadonly(components);
            components = viewRoot.findComponent("inputform:entPanel").
                    findComponent("entPanelGrid").getFacetsAndChildren();
            this.setComponentsToReadonly(components);
        }
        if ("logicDelete".equals(formerAction)) {
            cmbtn.setValue("删除");
        } else if ("showDetail".equals(formerAction)||"showQryDetail".equals(formerAction)) {
            cmbtn.setRendered(false);
        }
    }

    // 初始化编辑页面数据
    private void initCusEntByCusidt(String cusidt) {
        if (cusidt != null && !"".equalsIgnoreCase(cusidt)) {
            SqlSession session = ibatisManager.getSessionFactory().openSession();
            ActcusMapper cusMapper = session.getMapper(ActcusMapper.class);
            ActcusKey cusKey = new ActcusKey();
            cusKey.setSysidt(sysidt);
            cusKey.setCusidt(cusidt);
            actcus = cusMapper.selectByPrimaryKey(cusKey);
            ActentMapper entMapper = session.getMapper(ActentMapper.class);
            ActentExample entExample = new ActentExample();
            entExample.createCriteria().andCusidtEqualTo(cusidt);
            List<Actent> entList = entMapper.selectByExample(entExample);
            if (entList.size() > 0) {
                actent = entList.get(0);
            }
            closeSession(session);
        } else return;
    }

    private void initCusEnt() {
        this.actcus = new Actcus();
        this.actent = new Actent();
        initCusidt = generateCusidt();
        actcus.setCusidt(initCusidt);
        actent.setCusidt(initCusidt);
        actcus.setSysidt(sysidt);
        actent.setSysidt(sysidt);
        OperatorManager oper = OnlineService.getOperatorManager();
        actcus.setOrgidt(oper.getOperator().getDeptid());
        actcus.setAmdtlr(oper.getOperatorId());
        actcus.setUpddat(new Date());
        actent.setAmdtlr(oper.getOperatorId());
        actent.setUpddat(new Date());
    }

    private Long parseStringToMoney(String value) {
        long money = 0L;
        if (value != null && !"".equalsIgnoreCase(value)) {
            money = new Double(Double.parseDouble(value) * 100).longValue();
        }
        return money;

    }
   private void setComponentsToReadonly(Iterator<UIComponent> components){
      while(components.hasNext()){
                  UIComponent child =  components.next();
                  if(child instanceof HtmlInputText){
                      ((HtmlInputText)child).setReadonly(true);
                  }else if(child instanceof HtmlSelectOneMenu){
                       ((HtmlSelectOneMenu)child).setDisabled(true);
                  }
            }
   }
    private void showMsgsInfo(String summary,String detail){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,detail);
        FacesContext.getCurrentInstance().addMessage("msgs", message);
    }
    private void showMsgsError(String summary,String detail){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,detail);
        FacesContext.getCurrentInstance().addMessage("msgs", message);
    }
    // 关闭SqlSession
    private void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public Actcus getActcus() {
        return actcus;
    }

    public void setActcus(Actcus actcus) {
        this.actcus = actcus;
    }

    public Actent getActent() {
        return actent;
    }

    public void setActent(Actent actent) {
        this.actent = actent;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public String getInitCusidt() {
        return initCusidt;
    }

    public void setInitCusidt(String initCusidt) {
        this.initCusidt = initCusidt;
    }

    public String getDbl_crdlim() {
        return dbl_crdlim;
    }

    public void setDbl_crdlim(String dbl_crdlim) {
        this.dbl_crdlim = dbl_crdlim;
    }

    public String getDbl_reglap() {
        return dbl_reglap;
    }

    public void setDbl_reglap(String dbl_reglap) {
        this.dbl_reglap = dbl_reglap;
    }

    public String getDbl_loclap() {
        return dbl_loclap;
    }

    public void setDbl_loclap(String dbl_loclap) {
        this.dbl_loclap = dbl_loclap;
    }

    public String getSh_eff() {
        return sh_eff;
    }

    public void setSh_eff(String sh_eff) {
        this.sh_eff = sh_eff;
    }
}
