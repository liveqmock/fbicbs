package cbs.view.ac.accountinfo;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActoahMapper;
import cbs.repository.account.maininfo.dao.ActobfMapper;
import cbs.repository.account.maininfo.dao.CpSequenceMapper;
import cbs.repository.account.maininfo.model.*;
import cbs.repository.code.dao.ActaniMapper;
import cbs.repository.code.dao.ActccyMapper;
import cbs.repository.code.dao.ActglcMapper;
import cbs.repository.code.model.Actani;
import cbs.repository.code.model.Actapc;
import cbs.repository.code.model.Actglc;
import cbs.repository.code.model.Actirt;
import cbs.repository.customer.dao.ActcusMapper;
import cbs.repository.customer.model.Actcus;
import cbs.repository.customer.model.ActcusKey;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import pub.platform.security.OperatorManager;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-11-25
 * Time: 16:20:31
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "actaddition")
@RequestScoped
public class ActAddition implements Serializable {
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private static final Log logger = LogFactory.getLog(ActAddition.class);
    private Actoac actoac = new Actoac();  //�������ػ����ϱ�
    private ActoacChd actchd = new ActoacChd();//�������ػ����ϱ��ֱ�
    private Actoah actoah = new Actoah();   //�������ػ���ʷ
    private Actobf actobf = new Actobf();   //��������
    private Actani actani = new Actani();   //�˺���Ϣ��(�¾��˺Ŷ��ձ�)
    private ArrayList<SelectItem> alapc = null;         //������
    private final String orgidt = "010";  //������
    private final String depid = "60";    //���ź�
    private final String sysid = "8";      //ϵͳ��
    private HtmlInputText txtActname;       //�˻�
    private HtmlInputText txtDinrat;         //�跽���ʴ���ؼ�
    private HtmlInputText txtCinrat;       //�������ʴ���ؼ�
    private HtmlInputText txtStmcdt;       //���ʵ�������������
    private HtmlInputText txtLegcdt;       //�ֻ��˳�����������
    private HtmlInputText txtDratsf;       //�跽����
    private HtmlInputText txtCratsf;       //��������
    private UIInput maskstmzip;            //���˵���ַ�ʱ�
    private UIInput maskstmadd;            //���˵���ַ
    private HtmlSelectOneMenu selActccy;   //
    private HtmlSelectOneMenu selActapc;   //������ؼ�
    private UISelectItems selItemActapc;  //������item�ؼ�
    private HtmlSelectOneMenu selActtyp;   //�˻����Ϳؼ�
    private HtmlSelectOneMenu selDinrat;   //�˻����Ϳؼ�
    private UISelectItems selItemDinrat;  //�跽���ʴ���item�ؼ�
    private HtmlSelectOneMenu selCinrat;   //�˻����Ϳؼ�
    private UISelectItems selItemCinrat;  //�跽���ʴ���item�ؼ�
    private ArrayList<SelectItem> alirt;   //������
    private HtmlInputText txtActcnt;      //�˺�

    /*
    * ��֤���ʴ��������*/
    public void validateRat(FacesContext context, UIComponent com, Object value) {
        String strText = value.toString();
        if (!strText.matches("[A-Za-z]*")) {
            throw new ValidatorException(new FacesMessage("��ʽ����"));
        }

    }

    /**
     * ��֤����
     */
    public void validateLength(FacesContext context, UIComponent com, Object value) {
        String strText = value.toString();
        if (strText.getBytes().length > 72) {
            throw new ValidatorException(new FacesMessage("��ʽ����"));
        }
    }

    /**
     * ���ݱұ��ȡ������list
     */
    public void handleDinrat(ActionEvent actionEvent) {
        String curcde = (String) selActccy.getValue();
        Actirt irt = new Actirt();
        try {
            ArrayList<SelectItem> irtLst = irt.getData(curcde);
            selItemDinrat.setValue(irtLst);
            selItemCinrat.setValue(irtLst);
            txtCratsf.setValue("");
            txtDratsf.setValue("");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("���ݻ��ҺŲ�ѯ���ʴ������");
        }


    }

    /*
    * �жϺ������ڽ���������ϵ�����ƽ跽���ʴ���ʹ������ʴ���Ŀ������*/
    public void handleCom(ActionEvent actionEvent) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        Actglc glc = new Actglc();
        String glcbal = glc.getGLCBAL(actoac.getApcode());  //�����־
        if (glcbal != null) {
            if (glcbal.equals(ACEnum.GLCBAL_D.getStatus())) {
                selCinrat.setDisabled(true);
                selDinrat.setDisabled(false);
                txtCratsf.setValue("");
                txtDratsf.setValue("");
            } else if (glcbal.equals(ACEnum.GLCBAL_C.getStatus())) {
                selDinrat.setDisabled(true);
                selCinrat.setDisabled(false);
                txtCratsf.setValue("");
                txtDratsf.setValue("");
            } else {
                selDinrat.setDisabled(false);
                selCinrat.setDisabled(false);
                txtCratsf.setValue("");
                txtDratsf.setValue("");
            }
        }
        ActglcMapper glcmap = session.getMapper(ActglcMapper.class);
        List<Actglc> glcLst = glcmap.selectByApcode(actoac.getApcode());
        Actglc glc1 = (Actglc) glcLst.get(0);
        String glccat = glc1.getGlccat();
        if (glccat.equals("2")) {
            selActtyp.setValue("3");  //ѡ�������ͻ��˻�����
            selActtyp.setDisabled(true); //���ò�����
        } else {
            selActtyp.setValue("4");  //ѡ������ϵͳ�ۺ��ʻ�����
            selActtyp.setDisabled(true); //���ò�����
        }
    }

    /*
    * ���ʵ��������� = ��� ʱ�����ƶ��ʵ�����������������*/
    public void handleStmcdt(ActionEvent actionEvent) {
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

    /**
     * �ͻ�����ಹ�����
     */
    private String getPattern(String value, int len) {
        String strZero = "";
        if (value.equals("")) {
            return "";
        } else {
            for (int i = 1; (len - value.length()) >= i; i++) {
                strZero += "0";
            }
            value = strZero + value;
            return value;
        }
    }

    /**
     * ���ݿͻ���ȡ��:
     * 1 �ͻ�������ΪĬ���˻���
     * 2 ���˵���ַ�ʱ�
     * 3 ���ʵ���ַ
     */
    public void getActname(ActionEvent actionEvent) {
        HtmlInputText txtCusidt = (HtmlInputText) actionEvent.getComponent();
        String cusidtVal = (String) txtCusidt.getValue();
        cusidtVal = getPattern(cusidtVal, 7);
        try {
            if (!cusidtVal.equals("")) {
                SqlSession session = ibatisManager.getSessionFactory().openSession();
                ActcusMapper cusmap = session.getMapper(ActcusMapper.class);
                ActcusKey cuskey = new ActcusKey();
                cuskey.setCusidt(cusidtVal);
                cuskey.setSysidt(ACEnum.SYSIDT_AC.getStatus());
                Actcus actcus = cusmap.selectByPrimaryKey(cuskey);
                if (actcus != null) {
                    String actname = actcus.getCusnam();
                    String stmzip = actcus.getZipcde();
                    String stmadd = actcus.getCoradd();
                    String cuskid = actcus.getCuskid();
                    Actapc apc = new Actapc();
                    if (cuskid.equals("1")) {
                        alapc = apc.getData("2");
                    }
                    txtActname.setValue(actname);
                    maskstmzip.setValue(stmzip);
                    maskstmadd.setValue(stmadd);
                    selItemActapc.setValue(alapc);
                }
                txtCusidt.setValue(cusidtVal);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("���ݿͻ��Ų�ѯĬ��ֵ���ݳ���");
        }

    }

    /*
    * ���ݽ跽���ʴ����ѯ����ֵ*/
    public void getDratsfValue(ActionEvent actionEvent) {

        String dinrat = (String) selDinrat.getValue();
        if (dinrat.length() == 3) {
            txtDratsf.setValue(getIrtval(dinrat));

        } else {
            txtDratsf.setValue("");
        }

    }

    /*
    * ���ݴ����������ѯ����ֵ*/
    public void getCratsfValue(ActionEvent actionEvent) {
        String cinrat = (String) selCinrat.getValue();
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

    /*
    *ȡ���˻�˳���*/
    private String getActSeqCode(SqlSession session) throws Exception {
        long curnum = 0;
        try {
            CpSequenceMapper sqmap = session.getMapper(CpSequenceMapper.class);
            CpSequenceExample sqexample = new CpSequenceExample();
            sqexample.createCriteria().andSeqnameEqualTo("ACTSEQ");
            List<CpSequence> sqLst = sqmap.selectByExampleForupdate(sqexample);
            Iterator it = sqLst.iterator();
            while (it.hasNext()) {
                CpSequence sq = (CpSequence) it.next();
                curnum = (Long) sq.getCurvalue() + 1;
            }
            return String.valueOf(curnum);
        } catch (Exception ex) {
            throw ex;
        }

    }

    /*
    * ���ݱ���*/
    public String onBtnSaveClick() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        FacesContext context = FacesContext.getCurrentInstance();
        ActoacMapper oacmap = session.getMapper(ActoacMapper.class);
        ActoahMapper oahmap = session.getMapper(ActoahMapper.class);
        ActobfMapper obfmap = session.getMapper(ActobfMapper.class);
        ActaniMapper animap = session.getMapper(ActaniMapper.class);
        CpSequenceMapper seqmap = session.getMapper(CpSequenceMapper.class);
        //��֤�˻������Ƿ��ظ� 2011-11-24 by haiyu �˻��ſ�����
        String oldacn = actani.getOldacn();
        if (oldacn != null && !StringUtils.isEmpty(oldacn)) {
            Actani tempani = animap.selectByOldacn(oldacn, this.orgidt);
            if (tempani != null) {
                MessageUtil.addInfo("�ʻ����Ѵ��ڣ��޷����롣");
                return null;
            }
        }

        //��֤�ͻ���
        ActcusMapper cusmap = session.getMapper(ActcusMapper.class);
        ActcusKey cuskey = new ActcusKey();
        cuskey.setCusidt(this.actoac.getCusidt());
        cuskey.setSysidt(sysid);
        Actcus acus = cusmap.selectByPrimaryKey(cuskey);
        //�ÿͻ�������
        if (acus == null) {
            MessageUtil.addInfo("M101");
            return null;
        }
        //��֤�˻���
        ActoacExample oacexample = new ActoacExample();
        oacexample.createCriteria().andOrgidtEqualTo(orgidt).andCusidtEqualTo(this.actoac.getCusidt()).andApcodeEqualTo(this.actoac.getApcode()).andCurcdeEqualTo(this.actoac.getCurcde());
        List<Actoac> oacList = oacmap.selectByExample(oacexample);
        //�˻��Ѵ���
        if (!oacList.isEmpty()) {
            MessageUtil.addInfo("M401");
            return null;
        }
        //��֤תϢ�ʻ� 2011-11-24 by haiyu
        String strinttra = actoac.getInttra();
        if (strinttra == null || StringUtils.isEmpty(strinttra)) {
            actoac.setInttra(this.sysid + this.orgidt + actoac.getCusidt() + actoac.getApcode() + actoac.getCurcde());
        } else {
            //��֤תϢ�ʻ��Ƿ����                    801000010002631001
            String strorgidt = strinttra.substring(1, 4);
            String strcusidt = strinttra.substring(4, 11);
            String strapcode = strinttra.substring(11, 15);
            String strcurcde = strinttra.substring(15, 18);
            String actold = animap.selectByActnoCode(strorgidt, strcusidt, strapcode, strcurcde);
            if (actold == null || StringUtils.isEmpty(actold)) {
                MessageUtil.addInfo("M127");
                return null;
            }
        }
        //���ý��ֵ
//        actchd.setBallim((long)(Double.parseDouble(actchd.getBallimD())*100));
//        actchd.setOvelim((long)(Double.parseDouble(actchd.getOvelimD())*100));
        actoac.setBallim((long) (Double.parseDouble(actchd.getBallimD()) * 100));
        actoac.setOvelim((long) (Double.parseDouble(actchd.getOvelimD()) * 100));
        this.actoac.setOrgidt(orgidt);
        OperatorManager opm = OnlineService.getOperatorManager();
        this.actoac.setCretlr(opm.getOperatorId());
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bizDate = SystemService.getBizDate10();
        try {
            Date dt = dtFormat.parse(bizDate);
            actoac.setCredat(dt);
            actoac.setOacflg(ACEnum.OACFLG_OPEN.getStatus());
            OacPassOah opo = new OacPassOah();
            actoah = opo.oacPassOah(actoac);
            actobf = opo.oacPassobf(actoac);
            actani = opo.oacPassani(actoac);
            //2011-11-24 by haiyu
            if (oldacn == null || StringUtils.isEmpty(oldacn)) {
                /*�������˺�*/
                Actapc apc = new Actapc();
                String glcode = apc.getGLCODE(actoac.getApcode());
                String seqcode = getActSeqCode(session);
                oldacn = glcode + seqcode;
                actani.setOldacn(oldacn);
                seqmap.updateCurvaluePlus1();
            } else {
                actani.setOldacn(oldacn);
            }
            oacmap.insertSelective(this.actoac);
            oahmap.insertSelective(this.actoah);
            obfmap.insertSelective(this.actobf);
            animap.insertSelective(this.actani);
            MessageUtil.addInfo("W301");
            session.commit();
            txtActcnt.setValue("�˺ţ�" + oldacn);
            txtActcnt.setStyle("display:online;color:#2C4359;font-weight:bold");
        } catch (Exception ex) {
            session.rollback();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "�������ʧ�ܣ�", ex.getMessage()));
            logger.error("���˺���Ϣ��Ӽ�¼����" + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        return null;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public ArrayList<SelectItem> getAlapc() {
        Actapc apc = new Actapc();
        return apc.getAllData();
    }

    public void setAlapc(ArrayList<SelectItem> alapc) {
        this.alapc = alapc;
    }

    public HtmlInputText getTxtDinrat() {
        return txtDinrat;
    }

    public void setTxtDinrat(HtmlInputText txtDinrat) {
        this.txtDinrat = txtDinrat;
    }

    public HtmlInputText getTxtCinrat() {
        return txtCinrat;
    }

    public void setTxtCinrat(HtmlInputText txtCinrat) {
        this.txtCinrat = txtCinrat;
    }

    public HtmlInputText getTxtStmcdt() {
        return txtStmcdt;
    }

    public void setTxtStmcdt(HtmlInputText txtStmcdt) {
        this.txtStmcdt = txtStmcdt;
    }

    public HtmlSelectOneMenu getSelActccy() {
        return selActccy;
    }

    public void setSelActccy(HtmlSelectOneMenu selActccy) {
        this.selActccy = selActccy;
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

    public HtmlInputText getTxtLegcdt() {
        return txtLegcdt;
    }

    public void setTxtLegcdt(HtmlInputText txtLegcdt) {
        this.txtLegcdt = txtLegcdt;
    }

    public HtmlInputText getTxtActname() {
        return txtActname;
    }

    public void setTxtActname(HtmlInputText txtActname) {
        this.txtActname = txtActname;
    }

    public UIInput getMaskstmzip() {
        return maskstmzip;
    }

    public void setMaskstmzip(UIInput maskstmzip) {
        this.maskstmzip = maskstmzip;
    }

    public UIInput getMaskstmadd() {
        return maskstmadd;
    }

    public void setMaskstmadd(UIInput maskstmadd) {
        this.maskstmadd = maskstmadd;
    }

    public HtmlSelectOneMenu getSelActapc() {
        return selActapc;
    }

    public void setSelActapc(HtmlSelectOneMenu selActapc) {
        this.selActapc = selActapc;
    }

    public UISelectItems getSelItemActapc() {
        return selItemActapc;
    }

    public void setSelItemActapc(UISelectItems selItemActapc) {
        this.selItemActapc = selItemActapc;
    }

    public HtmlSelectOneMenu getSelActtyp() {
        return selActtyp;
    }

    public void setSelActtyp(HtmlSelectOneMenu selActtyp) {
        this.selActtyp = selActtyp;
    }

    public UISelectItems getSelItemDinrat() {
        return selItemDinrat;
    }

    public void setSelItemDinrat(UISelectItems selItemDinrat) {
        this.selItemDinrat = selItemDinrat;
    }

    public HtmlSelectOneMenu getSelDinrat() {
        return selDinrat;
    }

    public void setSelDinrat(HtmlSelectOneMenu selDinrat) {
        this.selDinrat = selDinrat;
    }

    public HtmlSelectOneMenu getSelCinrat() {
        return selCinrat;
    }

    public void setSelCinrat(HtmlSelectOneMenu selCinrat) {
        this.selCinrat = selCinrat;
    }

    public UISelectItems getSelItemCinrat() {
        return selItemCinrat;
    }

    public void setSelItemCinrat(UISelectItems selItemCinrat) {
        this.selItemCinrat = selItemCinrat;
    }

    public ArrayList<SelectItem> getAlirt() {
        Actirt irt = new Actirt();
        try {
            return irt.getData(ACEnum.CURCDE_001.getStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("���ݻ��ҺŻ�ȡ���������");
            return null;
        }

    }

    public void setAlirt(ArrayList<SelectItem> alirt) {
        this.alirt = alirt;
    }

    public HtmlInputText getTxtActcnt() {
        return txtActcnt;
    }

    public void setTxtActcnt(HtmlInputText txtActcnt) {
        this.txtActcnt = txtActcnt;
    }

    public ActoacChd getActchd() {
//        actchd.setStmdep("01060");
//        actchd.setLegdep("01060");
//        actchd.setDepnum("60");
//        short d = 1;
//        actchd.setStmsht(d);
        return actchd;
    }

    public void setActchd(ActoacChd actchd) {
        this.actchd = actchd;
    }

    public Actoac getActoac() {
        actoac.setStmdep("01060");
        actoac.setLegdep("01060");
        actoac.setDepnum("60");
        short d = 1;
        actoac.setStmsht(d);
        return actoac;
    }

    public void setActoac(Actoac actoac) {
        this.actoac = actoac;
    }

    public Actani getActani() {
        return actani;
    }

    public void setActani(Actani actani) {
        this.actani = actani;
    }
}
