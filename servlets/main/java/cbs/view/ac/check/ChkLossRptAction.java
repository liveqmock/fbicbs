package cbs.view.ac.check;

import cbs.common.IbatisManager;
import cbs.common.SystemService;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.maininfo.dao.ActrepMapper;
import cbs.repository.account.maininfo.model.Actrep;
import cbs.repository.code.dao.ActaniMapper;
import cbs.repository.code.model.Actani;
import cbs.repository.code.model.ActaniExample;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.datatable.DataTable;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.mail.Message;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lichao.W
 * Date: 2014/10/20
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ChkLossRptAction implements Serializable{
    private static final Log logger = LogFactory.getLog(ChkLossRptAction.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    Actrep actrep = new Actrep();//Ö§Æ±¹ÒÊ§±í
    private String sgndat = "";
    private String avabdt = "";
    private String avaedt = "";
    private  String tlrnum = "";  //¹ñÔ±ºÅ
    private String reppap = "";
    private String actnum = "";
    String bizDate = SystemService.getBizDate10();   //ÒµÎñÊ±¼ä

    @PostConstruct
    private void init() {
        tlrnum = new OperatorManager().getOperatorId();
    }


    //Ö§Æ±¹ÒÊ§
    public void onChkRpt(){
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActrepMapper repmap = session.getMapper(ActrepMapper.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = sdf.parse(bizDate);
            actrep.setOrgidt("010");
            actrep.setDisflg("1");
            if(!"".equals(avabdt)){
                Date d1 = java.sql.Date.valueOf(avabdt);
                actrep.setAvabdt(d1);
            }
            if (!"".equals(avaedt)){
                Date d2 = java.sql.Date.valueOf(avaedt);
                actrep.setAvaedt(d2);
            }
            if (!"".equals(sgndat)){
                Date d3 = java.sql.Date.valueOf(sgndat);
                actrep.setSgndat(d3);
            }
            actrep.setAmdtlr(tlrnum);
            actrep.setUpddat(dt);
            repmap.insertSelective(actrep);
            session.commit();
            MessageUtil.addInfo("W301");
        }catch (Exception e){
            logger.error("Ö§Æ±¹ÒÊ§³ö´í£º" + e.getMessage());
            MessageUtil.addError("Ö§Æ±¹ÒÊ§Ê§°Ü£¡");
            e.printStackTrace();
        }finally {
            session.close();
        }

    }
    //ÅÐ¶ÏÕËºÅÊÇ·ñ´æÔÚ
    public void handleCom(){
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActaniMapper animap = session.getMapper(ActaniMapper.class);
        try {

            int n = animap.countByOldacn(actrep.getActnum());
            if (n == 0) {
                MessageUtil.addError("M103");
            }
        }catch (Exception ex){
            logger.error("ÕËºÅ²éÑ¯³ö´í£º" + ex.getMessage());
            MessageUtil.addError("ÕËºÅ²éÑ¯Ê§°Ü£¡");
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }
    //ÅÐ¶ÏÖ§Æ±ºÅÊÇ·ñÖØ¸´
    public void handlePap(){
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActrepMapper repmap = session.getMapper(ActrepMapper.class);
        try {
            int n  = repmap.countBypapcde(actrep.getPapcde());
            if (n > 0) {
                MessageUtil.addError("M713");
            }
        }catch (Exception ex){
            logger.error("Ö§Æ±ºÅ²éÑ¯³ö´í£º" + ex.getMessage());
            MessageUtil.addError("Ö§Æ±ºÅ²éÑ¯Ê§°Ü£¡");
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }

    //Ö§Æ±½â³ý¹ÒÊ§
    public void onChkLossRel(){
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActrepMapper repmap = session.getMapper(ActrepMapper.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = sdf.parse(bizDate);
            actrep.setUpddat(dt);
            actrep.setAmdtlr(tlrnum);
            repmap.updateRep(reppap);
            session.commit();
            MessageUtil.addInfo("W301");
        }catch (Exception e){
            logger.error("Ö§Æ±½â³ý¹ÒÊ§³ö´í£º" + e.getMessage());
            MessageUtil.addError("Ö§Æ±½â³ý¹ÒÊ§Ê§°Ü£¡");
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    //Ö§Æ±²éÑ¯
    public void   handlePapRel(){
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActrepMapper repmap = session.getMapper(ActrepMapper.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            actrep =  repmap.selectRep(reppap);
            if (actrep == null || "".equals(actrep)){
                MessageUtil.addError("M713");
            }else {
                sgndat = sdf.format(actrep.getSgndat());
                avabdt = sdf.format(actrep.getAvabdt());
                avaedt = sdf.format(actrep.getAvaedt());
            }
        }catch (Exception ex){
            logger.error("Ö§Æ±ºÅ²éÑ¯³ö´í£º" + ex.getMessage());
            MessageUtil.addError("Ö§Æ±ºÅ²éÑ¯Ê§°Ü£¡");
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }
    // = = = = = = = = = = = = = get set = = = = = = = = = = = = = = = = = =


    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public Actrep getActrep() {
        return actrep;
    }

    public void setActrep(Actrep actrep) {
        this.actrep = actrep;
    }

    public String getSgndat() {
        return sgndat;
    }

    public void setSgndat(String sgndat) {
        this.sgndat = sgndat;
    }

    public String getAvabdt() {
        return avabdt;
    }

    public void setAvabdt(String avabdt) {
        this.avabdt = avabdt;
    }

    public String getAvaedt() {
        return avaedt;
    }

    public void setAvaedt(String avaedt) {
        this.avaedt = avaedt;
    }

    public String getReppap() {
        return reppap;
    }

    public void setReppap(String reppap) {
        this.reppap = reppap;
    }
}
