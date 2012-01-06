package cbs.view.ac.accountinfo;

import cbs.common.IbatisManager;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActobfMapper;
import cbs.repository.account.maininfo.model.*;
import cbs.repository.code.dao.ActaniMapper;
import cbs.repository.code.model.Actani;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.datatable.DataTable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-4
 * Time: 15:45:21
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "actquery")
@ViewScoped
public class ActQuery {
    private static final Log logger = LogFactory.getLog(ActQuery.class);
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private Actoac actoac = new Actoac();  //联机开关户资料表
    private Actoah actoah = new Actoah();   //联机开关户历史
    private Actobf actobf = new Actobf();   //联机余额表
    private Actani actani = new Actani();   //账号信息表(新旧账号对照表)
    private List<ActoacInfo> oacInfoLst;
    private ActoacInfo selectRec;

    @PostConstruct
    private void init() {
        queryInfo("", "");
    }

    /*查询数据方法
    * oldacn: 账户号*/

    private void queryInfo(String oldacn, String actnam) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        FacesContext context = FacesContext.getCurrentInstance();
        ActoacMapper oacmap = session.getMapper(ActoacMapper.class);
        ActaniMapper animap = session.getMapper(ActaniMapper.class);
        ActoacExample oacexample = new ActoacExample();
        try {
            if (!oldacn.equals("") || !actnam.equals("")) {
                oacInfoLst = oacmap.selectOLDACNsetRecord(oldacn, actnam);
            } else {
                oacInfoLst = oacmap.selectAllRecords();
            }
            bokbalFormat();
        } catch (Exception ex) {
            logger.error("查询账户信息出错：" + ex.getMessage());
            MessageUtil.addError("查询失败！");
            ex.printStackTrace();
        } finally {
            session.close();
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("queryForm:actoacInfo");
            if (dataTable != null) {
                dataTable.setFirst(0);
                dataTable.setPage(1);
            }
        }

    }

    /*查询*/
    public String onBtnQueryClick() {
        queryInfo(actani.getOldacn(),actoac.getActnam());
        return null;
    }

    private void bokbalFormat() {
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        for (ActoacInfo oacInfo : this.oacInfoLst) {
            double bokbal = (double) oacInfo.getBokbal() / 100;
            oacInfo.setStrBokbal(df0.format(bokbal));

        }
    }

    /*
    * 查询数据（edit页面）*/

    public String onQueryActInfo() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        FacesContext context = FacesContext.getCurrentInstance();
        String reqOrgidt = context.getExternalContext().getRequestParameterMap().get("orgidt");
        String reqCusidt = context.getExternalContext().getRequestParameterMap().get("cusidt");
        String reqApcode = context.getExternalContext().getRequestParameterMap().get("apcode");
        String reqCurcde = context.getExternalContext().getRequestParameterMap().get("curcde");
        Actobf obf1 = null;
        ActobfMapper obfmap1 = session.getMapper(ActobfMapper.class);
        ActobfExample obfexam1 = new ActobfExample();
        obfexam1.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(reqOrgidt).andCusidtEqualTo(reqCusidt)
                .andApcodeEqualTo(reqApcode).andCurcdeEqualTo(reqCurcde);
        try {
            List<Actobf> obf1Lst = obfmap1.selectByExample(obfexam1);
            Iterator it = obf1Lst.iterator();
            String actsts1 = "";
            while (it.hasNext()) {
                obf1 = (Actobf) it.next();
                actsts1 = obf1.getActsts();
            }
            if (obf1 == null) {
                MessageUtil.addInfo("该账户已不存在");
                return null;
            } else if (!actsts1.equals(" ")) {
                MessageUtil.addInfo("该账户不可修改");
                return null;
            }
            context.getExternalContext().getSessionMap().put("actCusidt", reqCusidt);
            context.getExternalContext().getSessionMap().put("actApcode", reqApcode);
            context.getExternalContext().getSessionMap().put("actCurcde", reqCurcde);
            return "accountSingleEdition";
        } catch (Exception ex) {
            logger.error("编辑页面进入出错:" + ex.getMessage());
            ex.printStackTrace();
            MessageUtil.addError("不能进入编辑页面");
            return null;
        }
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

    public Actoah getActoah() {
        return actoah;
    }

    public void setActoah(Actoah actoah) {
        this.actoah = actoah;
    }

    public Actobf getActobf() {
        return actobf;
    }

    public void setActobf(Actobf actobf) {
        this.actobf = actobf;
    }

    public Actani getActani() {
        return actani;
    }

    public void setActani(Actani actani) {
        this.actani = actani;
    }

    public List<ActoacInfo> getOacInfoLst() {
        return oacInfoLst;
    }

    public void setOacInfoLst(List<ActoacInfo> oacInfoLst) {
        this.oacInfoLst = oacInfoLst;
    }

    public ActoacInfo getSelectRec() {
        return selectRec;
    }

    public void setSelectRec(ActoacInfo selectRec) {
        this.selectRec = selectRec;
    }
}
