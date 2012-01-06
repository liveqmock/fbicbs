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
    private Actoac actoac = new Actoac();  //�������ػ����ϱ�
    private ActoacChd actchd = new ActoacChd();//�������ػ����ϱ��ֱ�
    private HtmlInputText txtStmcdt;       //���ʵ�������������
    private HtmlInputText txtLegcdt;       //�ֻ��˳�����������
    private HtmlInputText txtDratsf;       //�跽����
    private HtmlInputText txtCratsf;       //��������
    private ArrayList<SelectItem> alirt;   //������
    private String orgidt = "";  //������
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
                    //�����������������ԣ���
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
                        logger.error("�����˻����ݲ�ѯ����" + ex.getMessage());
                        ex.printStackTrace();
                    } finally {
                        session.close();
                    }
                }
                //�ж�ҳ������
                initPage();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * �޸�ҳ������
     * ��ϸ���޸ģ��ر��˻�
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
    * ���ʵ��������� = ��� ʱ�����ƶ��ʵ�����������������*/

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
    * �ֻ��˳�������= ��� ʱ�����Ʒֻ��˳���������������*/

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
    * ���ݽ跽���ʴ����ѯ����ֵ*/

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
    * ���ݴ����������ѯ����ֵ*/

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
    * ��������ֵ*/

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
            logger.error("��ǰ���ڻ�ȡʧ�ܣ�" + ex1.getMessage());
            ex1.printStackTrace();
            return null;
        }
        //��֤תϢ�ʻ� 2011-11-24 by haiyu
        String strinttra = actoac.getInttra();
        if (strinttra == null || StringUtils.isEmpty(strinttra)) {
            actoac.setInttra(ACEnum.SYSIDT_AC.getStatus() + this.orgidt + actoac.getCusidt() + actoac.getApcode() + actoac.getCurcde());
        } else {
            //��֤תϢ�ʻ��Ƿ����                    801000010002631001
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
        //���ý��ֵ
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
            MessageUtil.addError("����ʧ�ܣ�");
            logger.error("�˻����³���" + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
        return null;
    }
    /*�ر��˻�*/

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
            //�����������ػ���ʷ
            OacPassOah opo = new OacPassOah();
            Actoah oah = opo.oacPassOah(this.actoac);
            oah.setOacflg(ACEnum.OACFLG_CLOSE.getStatus());
            oah.setCredat(dt);
            oah.setCretlr(opm.getOperatorId());
            oah.setRecsts(ACEnum.RECSTS_VALID.getStatus());
            oah.setClsdat(dt);
            ActoahMapper oahmap = session.getMapper(ActoahMapper.class);
            oahmap.insertSelective(oah);
            //���� �������ػ����� �� ��������
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
            //������������
            obf.setRecsts(ACEnum.RECSTS_DEL.getStatus());
            ActobfExample obfexample = new ActobfExample();
            obfexample.clear();
            obfexample.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(obf.getOrgidt()).andCusidtEqualTo(obf.getCusidt())
                    .andApcodeEqualTo(obf.getApcode()).andCurcdeEqualTo(obf.getCurcde());
            obfmap.updateByExampleSelective(obf, obfexample);
            session.commit();
            MessageUtil.addInfo("�˻��رճɹ�");
            return "accountClose";
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addInfo("�˻��ر�ʧ��");
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    //����ƾ֤�����ļ� ��Ա��.txt

    private void createFile(String fileName) throws IOException {
        String strPath = "d:\\fbicbs\\online\\toa\\";
        //����·��
        File fileDirec = new File(strPath);
        if (!fileDirec.isDirectory() || !fileDirec.exists()) {
            if (!fileDirec.mkdirs()) {
                MessageUtil.addInfo("·������ʧ�ܣ�");
                logger.error("·������ʧ�ܣ�");
            }
        }
        File file = new File(strPath + fileName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(this.actoac.getActnam() + ":�رճɹ���");
        bw.newLine();
        bw.flush();
        bw.close();
    }

    /**
     * ��ÿؼ�bingdingֵ
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
            logger.error("���ݻ��ҺŻ�ȡ���������");
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
