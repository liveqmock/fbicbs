package cbs.view.ac.accountinfo;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActoahMapper;
import cbs.repository.account.maininfo.dao.ActobfMapper;
import cbs.repository.account.maininfo.model.*;
import cbs.repository.code.dao.ActaniMapper;
import cbs.repository.code.dao.ActccyMapper;
import cbs.repository.code.model.Actirt;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-11-29
 * Time: 16:55:30
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "actupdate")
@ViewScoped
public class ActUpdate {
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private static final Log logger = LogFactory.getLog(ActUpdate.class);
    private Actoac actoac = new Actoac();  //联机开关户资料表
    private ActoacChd actchd = new ActoacChd();//联机开关户资料表字表
    private HtmlInputText txtStmcdt;       //对帐单出帐周期日期
    private HtmlInputText txtLegcdt;       //分户账出账周期日期
    private HtmlInputText txtDratsf;       //借方利率
    private HtmlInputText txtCratsf;       //贷方利率
    private ArrayList<SelectItem> alirt;   //核算码
    private String orgidt = "";  //机构号
    private String actCurcde;
    private String formerAction = null;

    @PostConstruct
    private void init() {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            FacesContext context = FacesContext.getCurrentInstance();
            if (context.getRenderResponse()) {
                Map<String, String> params = context.getExternalContext().getRequestParameterMap();
                String oldacn = params.get("oldacn");
                actchd.setOldacn(oldacn);
                String actCusidt = params.get("cusidt");
                String actApcode = params.get("apcode");
                orgidt = params.get("orgidt");
                actCurcde = params.get("curcde");
                formerAction = params.get("action");
                if (!actCusidt.equals("") && !actApcode.equals("") && !actCurcde.equals("")) {
                    //利率码与核算码关联性？？
                    SqlSession session = ibatisManager.getSessionFactory().openSession();
                    try {
                        ActoacMapper oacmap = session.getMapper(ActoacMapper.class);
                        ActoacExample oacxam = new ActoacExample();
                        oacxam.createCriteria().andOrgidtEqualTo(this.orgidt).andCusidtEqualTo(actCusidt).andApcodeEqualTo(actApcode).andCurcdeEqualTo(actCurcde);
                        List<Actoac> oacLst = oacmap.selectByExample(oacxam);
                        Iterator it = oacLst.iterator();
                        while (it.hasNext()) {
                            actoac = (Actoac) it.next();
                        }
                        double balLim = (double) actoac.getBallim() / 100;
                        double oveLim = (double) actoac.getOvelim() / 100;
                        ActobfMapper obfmap = session.getMapper(ActobfMapper.class);
                        ActobfExample obfexam = new ActobfExample();
                        obfexam.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(orgidt).
                                andCusidtEqualTo(actoac.getCusidt()).andApcodeEqualTo(actoac.getApcode()).andCurcdeEqualTo(actoac.getCurcde());
                        List<Actobf> obfLst = obfmap.selectByExample(obfexam);
                        Actobf tmpobf = obfLst.get(0);
                        double bokbal = (double)tmpobf.getBokbal()/100;
                        double avabal = (double)tmpobf.getAvabal()/100;
                        actchd.setStrBokbal(df.format(bokbal));
                        actchd.setStrAvabal(df.format(avabal));
                        actchd.setBallimD(df.format(balLim));
                        actchd.setOvelimD(df.format(oveLim));
                    } catch (Exception ex) {
                        session.close();
                        logger.error("单个账户数据查询出错：" + ex.getMessage());
                        ex.printStackTrace();
                    } finally {
                        session.close();
                    }
                }
                //判断页面属性
                initPage();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 修改页面属性
     * 详细，修改，关闭账户
     */
    private void initPage() {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        HtmlCommandButton btnDel = (HtmlCommandButton) viewRoot.findComponent("addForm:btnDelete");
        HtmlCommandButton btnQryReback = (HtmlCommandButton) viewRoot.findComponent("addForm:btnQryReback");
        HtmlCommandButton btnClsReback = (HtmlCommandButton) viewRoot.findComponent("addForm:btnClsReback");
        HtmlCommandButton btnEdtReback = (HtmlCommandButton) viewRoot.findComponent("addForm:btnEdtReback");
        if ("edit".equals(formerAction)) {
            btnDel.setRendered(false);
            btnQryReback.setRendered(false);
            btnClsReback.setRendered(false);

        } else {
            Iterator<UIComponent> components = viewRoot.findComponent("addForm:basePanel").findComponent("basePaneltabContent").getFacetsAndChildren();
            this.setComponentsToReadonly(components);
            HtmlCommandButton btnReset = (HtmlCommandButton) viewRoot.findComponent("addForm:btnReset");
            HtmlCommandButton btnSave = (HtmlCommandButton) viewRoot.findComponent("addForm:btnSave");
            btnReset.setRendered(false);
            btnSave.setRendered(false);
            btnEdtReback.setRendered(false);
            if ("detail".equals(formerAction)) {
                btnDel.setRendered(false);
                btnClsReback.setRendered(false);
            } else {
                btnQryReback.setRendered(false);
            }
        }

    }

    private void setComponentsToReadonly(Iterator<UIComponent> components) {
        while (components.hasNext()) {
            UIComponent child = components.next();
            if (child instanceof HtmlPanelGroup) {
                Iterator<UIComponent> coms = child.getFacetsAndChildren();
                this.setComponentsToReadonly(coms);
            } else if (child instanceof HtmlInputText) {
                ((HtmlInputText) child).setDisabled(true); //setReadonly
            } else if (child instanceof HtmlSelectOneMenu) {
                ((HtmlSelectOneMenu) child).setDisabled(true);
            }
        }
    }

    /*
    * 对帐单出帐周期 = 年底 时，控制对帐单出帐周期日期属性*/

    public void handleStmcdt(ActionEvent actionEvent) {
        txtStmcdt = (HtmlInputText) this.getUICom("addForm:txtStmcdt");
        HtmlSelectOneMenu som = (HtmlSelectOneMenu) actionEvent.getComponent();
        String stmcyc1 = (String) som.getValue();
        if (stmcyc1.equals(ACEnum.STMCYC_Y.getStatus())) {
            txtStmcdt.setValue("1231");
            txtStmcdt.setDisabled(true);
        } else if (stmcyc1.equals(ACEnum.STMCYC_D.getStatus()) || stmcyc1.equals(ACEnum.STMCYC_F.getStatus()) ||
                stmcyc1.equals(ACEnum.STMCYC_P.getStatus()) || stmcyc1.equals(ACEnum.STMCYC_E.getStatus())) {
            txtStmcdt.setValue("0000");
            txtStmcdt.setDisabled(true);
        } else {
            txtStmcdt.setValue("");
            txtStmcdt.setDisabled(false);
        }
    }

    /*
    * 分户账出账周期= 年底 时，控制分户账出账周期日期属性*/

    public void handleLegcdt(ActionEvent actionEvent) {
        txtLegcdt = (HtmlInputText) this.getUICom("addForm:legcdt");
        HtmlSelectOneMenu som = (HtmlSelectOneMenu) actionEvent.getComponent();
        String stmcyc1 = (String) som.getValue();
        if (stmcyc1.equals(ACEnum.STMCYC_Y.getStatus())) {
            txtLegcdt.setValue("1231");
            txtLegcdt.setDisabled(true);
        } else if (stmcyc1.equals(ACEnum.STMCYC_D.getStatus()) || stmcyc1.equals(ACEnum.STMCYC_F.getStatus()) ||
                stmcyc1.equals(ACEnum.STMCYC_P.getStatus()) || stmcyc1.equals(ACEnum.STMCYC_E.getStatus())) {
            txtLegcdt.setValue("0000");
            txtLegcdt.setDisabled(true);
        } else {
            txtLegcdt.setValue("");
            txtLegcdt.setDisabled(false);
        }
    }

    /*
    * 根据借方利率代码查询利率值*/

    public void getDratsfValue(ActionEvent actionEvent) {
        txtDratsf = (HtmlInputText) this.getUICom("addForm:txtDratsf");
        HtmlSelectOneMenu som = (HtmlSelectOneMenu) actionEvent.getComponent();
        String dinrat = (String) som.getValue();
        if (dinrat.length() == 3) {
            txtDratsf.setValue(getIrtval(dinrat));

        } else {
            txtDratsf.setValue("");
        }

    }

    /*
    * 根据贷方利率码查询利率值*/

    public void getCratsfValue(ActionEvent actionEvent) {
        txtCratsf = (HtmlInputText) this.getUICom("addForm:txtCratsf");
        HtmlSelectOneMenu som = (HtmlSelectOneMenu) actionEvent.getComponent();
        String cinrat = (String) som.getValue();
        if (cinrat.length() == 3) {
            txtCratsf.setValue(getIrtval(cinrat));
        } else {
            txtCratsf.setValue("");
        }
    }

    /*
    * 返回利率值*/

    private String getIrtval(String rat) {
        String curcde = ACEnum.CURCDE_001.getStatus();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        String irtkd1 = rat.substring(0, 1);
        String irtkd2 = rat.substring(1, 3);
        ActccyMapper ccymap = session.getMapper(ActccyMapper.class);
        double irtval = ccymap.selectByParam(curcde, irtkd1, irtkd2);
        return String.valueOf(irtval);
    }

    public String onBtnSaveClick() {
        FacesContext context = FacesContext.getCurrentInstance();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActoacMapper oacmap = session.getMapper(ActoacMapper.class);
        ActoahMapper oahmap = session.getMapper(ActoahMapper.class);
        ActobfMapper obfmap = session.getMapper(ActobfMapper.class);
        ActaniMapper animap = session.getMapper(ActaniMapper.class);
        OperatorManager opm = OnlineService.getOperatorManager();
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bizDate = SystemService.getBizDate10();
        Date dt = null;
        try {
            dt = dtFormat.parse(bizDate);
        } catch (ParseException ex1) {
            logger.error("当前日期获取失败：" + ex1.getMessage());
            ex1.printStackTrace();
            return null;
        }
        //验证转息帐户 2011-11-24 by haiyu
        String strinttra = actoac.getInttra();
        if (strinttra == null || StringUtils.isEmpty(strinttra)) {
            actoac.setInttra(ACEnum.SYSIDT_AC.getStatus() + this.orgidt + actoac.getCusidt() + actoac.getApcode() + actoac.getCurcde());
        } else {
            //验证转息帐户是否存在                    801000010002631001
            String strorgidt = strinttra.substring(1,4);
            String strcusidt = strinttra.substring(4,11);
            String strapcode = strinttra.substring(11,15);
            String strcurcde = strinttra.substring(15,18);
            String actold = animap.selectByActnoCode(strorgidt,strcusidt,strapcode,strcurcde);
            if (actold == null || StringUtils.isEmpty(actold)) {
                MessageUtil.addInfo("M127");
                return null;
            }
        }
        //设置金额值
        actchd.setBallim((long) (Double.parseDouble(actchd.getBallimD()) * 100));
        actchd.setOvelim((long) (Double.parseDouble(actchd.getOvelimD()) * 100));
        actoac.setBallim(actchd.getBallim());
        actoac.setOvelim(actchd.getOvelim());
        actoac.setCredat(dt);
        actoac.setCretlr(opm.getOperatorId());
        OacPassOah opo = new OacPassOah();
        Actoah oah = opo.oacPassOah(this.actoac);
        oah.setOrgidt(orgidt);
        Actobf obf = null;
        ActobfExample obfexam = new ActobfExample();
        if (!actoac.getCqeflg().equals("")) {
            obf = new Actobf();
            obf.setCqeflg(actoac.getCqeflg());
            obfexam.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(orgidt).andCusidtEqualTo(actoac.getCusidt()).andApcodeEqualTo(actoac.getApcode()).andCurcdeEqualTo(actoac.getCurcde());
        }
        ActoacExample oacexam = new ActoacExample();
        oacexam.createCriteria().andOrgidtEqualTo(orgidt).andCusidtEqualTo(actoac.getCusidt()).andApcodeEqualTo(actoac.getApcode()).andCurcdeEqualTo(actoac.getCurcde());
        try {
            oacmap.updateByExampleSelective(this.actoac, oacexam);
            oahmap.insertSelective(oah);
            if (obf != null) {
                obfmap.updateByExampleSelective(obf, obfexam);
            }
            session.commit();
            MessageUtil.addInfo("W301");
        } catch (Exception ex) {
            MessageUtil.addError("交易失败！");
            logger.error("账户更新出错：" + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
        return null;
    }
    /*关闭账户*/

    public String onlkBtnCloseClick() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        FacesContext context = FacesContext.getCurrentInstance();
        String reqCusidt = actoac.getCusidt();
        String reqApcode = actoac.getApcode();
        String reqCurcde = actoac.getCurcde();
        try {
            ActobfMapper obfmap = session.getMapper(ActobfMapper.class);
            Actobf obf = obfmap.selectByPKForlock(ACEnum.SYSIDT_AC.getStatus(), this.orgidt, reqCusidt, reqApcode, reqCurcde);
            if (obf == null) {
                session.commit();
                MessageUtil.addInfo("M103");
                return null;
            } else if (!obf.getRecsts().equals(ACEnum.RECSTS_VALID.getStatus())) {
                session.commit();
                MessageUtil.addInfo("M405");
                return null;
            } else if (!obf.getFrzsts().equals(ACEnum.FRZSTS_NORMAL.getStatus())) {
                session.commit();
                MessageUtil.addInfo("M409");
                return null;
            } else if (obf.getBokbal() != 0 || obf.getAvabal() != 0 || obf.getDifbal() != 0 || obf.getCifbal() != 0) {
                session.commit();
                MessageUtil.addInfo("M204");
                return null;
            }
            if (actoac.getOacflg().equals(ACEnum.OACFLG_CLOSE.getStatus()) || actoac.getOacflg().equals(ACEnum.OACFLG_BOTH.getStatus())) {
                session.commit();
                MessageUtil.addInfo("M405");
                return null;
            }
            if (actoac.getOacflg().equals(ACEnum.OACFLG_OPEN.getStatus()) && actoac.getRecsts().equals(ACEnum.RECSTS_OAC_VALID.getStatus())) {
                session.commit();
                MessageUtil.addInfo("M212");
                return null;
            }
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = dtFormat.parse(SystemService.getBizDate10());
            OperatorManager opm = OnlineService.getOperatorManager();
            //插入联机开关户历史
            OacPassOah opo = new OacPassOah();
            Actoah oah = opo.oacPassOah(this.actoac);
            oah.setOacflg(ACEnum.OACFLG_CLOSE.getStatus());
            oah.setCredat(dt);
            oah.setCretlr(opm.getOperatorId());
            oah.setRecsts(ACEnum.RECSTS_VALID.getStatus());
            oah.setClsdat(dt);
            ActoahMapper oahmap = session.getMapper(ActoahMapper.class);
            oahmap.insertSelective(oah);
            //更新 联机开关户资料 及 联机余额表
            ActoacMapper oacmap = session.getMapper(ActoacMapper.class);
            Actoac oac = new Actoac();
            oac.setOacflg(ACEnum.OACFLG_CLOSE.getStatus());
            oac.setCredat(oah.getCredat());
            oac.setClsdat(oah.getClsdat());
            oac.setCretlr(oah.getCretlr());
            oac.setRecsts(oah.getRecsts());
            ActoacExample oacexample = new ActoacExample();
            oacexample.clear();
            oacexample.createCriteria().andOrgidtEqualTo(this.orgidt).andCusidtEqualTo(reqCusidt).andApcodeEqualTo(reqApcode).andCurcdeEqualTo(reqCurcde);
            oacmap.updateByExampleSelective(oac, oacexample);
            //更新联机余额表
            obf.setRecsts(ACEnum.RECSTS_DEL.getStatus());
            ActobfExample obfexample = new ActobfExample();
            obfexample.clear();
            obfexample.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(obf.getOrgidt()).andCusidtEqualTo(obf.getCusidt())
                    .andApcodeEqualTo(obf.getApcode()).andCurcdeEqualTo(obf.getCurcde());
            obfmap.updateByExampleSelective(obf, obfexample);
            session.commit();
            MessageUtil.addInfo("账户关闭成功");
            return "accountClose";
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addInfo("账户关闭失败");
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    //生成凭证报表文件 柜员号.txt

    private void createFile(String fileName) throws IOException {
        String strPath = "d:\\fbicbs\\online\\toa\\";
        //创建路径
        File fileDirec = new File(strPath);
        if (!fileDirec.isDirectory() || !fileDirec.exists()) {
            if (!fileDirec.mkdirs()) {
                MessageUtil.addInfo("路径创建失败！");
                logger.error("路径创建失败！");
            }
        }
        File file = new File(strPath + fileName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(this.actoac.getActnam() + ":关闭成功！");
        bw.newLine();
        bw.flush();
        bw.close();
    }

    /**
     * 获得控件bingding值
     */
    private UIComponent getUICom(String clientID) {
        UIComponent com = null;
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        com = viewRoot.findComponent(clientID);
        return com;
    }

    public ArrayList<SelectItem> getAlirt() {
        Actirt irt = new Actirt();
        try {
            return irt.getData(actCurcde);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("根据货币号获取利率码错误！");
            return null;
        }

    }

    public void setAlirt(ArrayList<SelectItem> alirt) {
        this.alirt = alirt;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public Actoac getActoac() {
        return actoac;
    }

    public void setActoac(Actoac actoac) {
        this.actoac = actoac;

    }

    public HtmlInputText getTxtStmcdt() {
        return txtStmcdt;
    }

    public void setTxtStmcdt(HtmlInputText txtStmcdt) {
        this.txtStmcdt = txtStmcdt;
    }

    public HtmlInputText getTxtLegcdt() {
        return txtLegcdt;
    }

    public void setTxtLegcdt(HtmlInputText txtLegcdt) {
        this.txtLegcdt = txtLegcdt;
    }

    public HtmlInputText getTxtDratsf() {
        return txtDratsf;
    }

    public void setTxtDratsf(HtmlInputText txtDratsf) {
        this.txtDratsf = txtDratsf;
    }

    public HtmlInputText getTxtCratsf() {
        return txtCratsf;
    }

    public void setTxtCratsf(HtmlInputText txtCratsf) {
        this.txtCratsf = txtCratsf;
    }

    public ActoacChd getActchd() {
        return actchd;
    }

    public void setActchd(ActoacChd actchd) {
        this.actchd = actchd;
    }
}
