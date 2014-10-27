package cbs.view.ac.actslp;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.BeanHelper;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActoahMapper;
import cbs.repository.account.maininfo.dao.ActobfMapper;
import cbs.repository.account.maininfo.dao.ActslpMapper;
import cbs.repository.account.maininfo.model.*;
import cbs.repository.code.dao.ActaniMapper;
import cbs.view.ac.accountinfo.OacPassOah;
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
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lichao.W
 * Date: 2014/10/16
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ActSlpInfoAction implements Serializable {
    private static final Log logger = LogFactory.getLog(ActSlpInfoAction.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

    private Actslp actslp = new Actslp();  //˯�߻���
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
                        double bokbal = (double) tmpobf.getBokbal() / 100;
                        double avabal = (double) tmpobf.getAvabal() / 100;
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


    private void initPage() {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        Iterator<UIComponent> components = viewRoot.findComponent("addForm:basePanel").findComponent("basePaneltabContent").getFacetsAndChildren();
        this.setComponentsToReadonly(components);
        HtmlCommandButton btnDel = (HtmlCommandButton) viewRoot.findComponent("addForm:btnDelete");
        HtmlCommandButton btnRegReback = (HtmlCommandButton) viewRoot.findComponent("addForm:btnRegReback");
        HtmlCommandButton btnRelReback = (HtmlCommandButton) viewRoot.findComponent("addForm:btnRelReback");
        HtmlCommandButton btnSave = (HtmlCommandButton) viewRoot.findComponent("addForm:btnSave");
        if ("register".equals(formerAction)) {
            btnDel.setRendered(false);
            btnRelReback.setRendered(false);
        } else {
            btnSave.setRendered(false);
            btnRegReback.setRendered(false);
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

    //���߻��Ǽ�
    public String onBtnRegClick() {
        FacesContext context = FacesContext.getCurrentInstance();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActoacMapper oacmap = session.getMapper(ActoacMapper.class);
        ActoahMapper oahmap = session.getMapper(ActoahMapper.class);
        ActobfMapper obfmap = session.getMapper(ActobfMapper.class);
        ActaniMapper animap = session.getMapper(ActaniMapper.class);
        /**  actslp  */
        ActslpMapper slpmap = session.getMapper(ActslpMapper.class);
        BeanHelper.copy(actslp,actoac);
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
        actchd.setBallim((long) (Double.parseDouble(actchd.getBallimD()) * 100));
        actchd.setOvelim((long) (Double.parseDouble(actchd.getOvelimD()) * 100));
        actoac.setBallim(actchd.getBallim());
        actoac.setOvelim(actchd.getOvelim());
        actoac.setCredat(dt);
        actoac.setCretlr(opm.getOperatorId());
        //˯�߻�����
        actoac.setIntflg("0");      //����Ϣ
        actoac.setStmfmt("O");      //������ҳ
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
            slpmap.insertSelective(actslp);
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



    //������߻�
    public String onBtnRelClick() {
        FacesContext context = FacesContext.getCurrentInstance();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActoacMapper oacmap = session.getMapper(ActoacMapper.class);
        ActoahMapper oahmap = session.getMapper(ActoahMapper.class);
        ActobfMapper obfmap = session.getMapper(ActobfMapper.class);
        ActaniMapper animap = session.getMapper(ActaniMapper.class);
        /**  actslp  */
        ActslpMapper slpmap = session.getMapper(ActslpMapper.class);
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
        actchd.setBallim((long) (Double.parseDouble(actchd.getBallimD()) * 100));
        actchd.setOvelim((long) (Double.parseDouble(actchd.getOvelimD()) * 100));
        actoac.setBallim(actchd.getBallim());
        actoac.setOvelim(actchd.getOvelim());
        actoac.setCredat(dt);
        actoac.setCretlr(opm.getOperatorId());

        try{
        //˯�߻�����
            ActslpExample slpxam = new ActslpExample();
            slpxam.createCriteria().andOrgidtEqualTo(this.orgidt).andCusidtEqualTo(actoac.getCusidt()).andApcodeEqualTo(actoac.getApcode()).andCurcdeEqualTo(actCurcde);
            List<Actslp> slpList = slpmap.selectByExample(slpxam);
            actoac.setIntflg(slpList.get(0).getIntflg());      //ԭ��Ϣ
            actoac.setStmfmt(slpList.get(0).getStmfmt());      //ԭ����
        }catch (Exception es){
            logger.error("��ȡԭ��Ϣ���ʧ�ܣ�" + es.getMessage());
            es.printStackTrace();
            return null;
        }

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

    // = = = = = = = = = = = = get set = = = = = =  = = = = = = =

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

    public ActoacChd getActchd() {
        return actchd;
    }

    public void setActchd(ActoacChd actchd) {
        this.actchd = actchd;
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

    public ArrayList<SelectItem> getAlirt() {
        return alirt;
    }

    public void setAlirt(ArrayList<SelectItem> alirt) {
        this.alirt = alirt;
    }

    public String getOrgidt() {
        return orgidt;
    }

    public void setOrgidt(String orgidt) {
        this.orgidt = orgidt;
    }

    public String getActCurcde() {
        return actCurcde;
    }

    public void setActCurcde(String actCurcde) {
        this.actCurcde = actCurcde;
    }

    public String getFormerAction() {
        return formerAction;
    }

    public void setFormerAction(String formerAction) {
        this.formerAction = formerAction;
    }

}
