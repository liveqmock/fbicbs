package cbs.batch.ac.bcb85510;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.billinfo.dao.ActlblMapper;
import cbs.repository.account.billinfo.dao.ActsblMapper;
import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.billinfo.model.ActlblExample;
import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.account.billinfo.model.ActsblExample;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.ActactExample;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.account.maininfo.model.ActoacExample;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-11
 * Time: 14:25:04
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB85510Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85510Handler.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    @Inject
    private ActoacMapper oacMapper;
    @Inject
    private ActoacExample oacExample;
    @Inject
    private ActsctMapper sctMapper;
    @Inject
    private ActsblMapper sblMapper;
    @Inject
    private ActsblExample sblExample;
    @Inject
    private ActlblMapper lblMapper;
    @Inject
    private ActlblExample lblExample;
    @Inject
    private ActactMapper actMapper;
    @Inject
    private ActactExample actExample;

    private List<Actoac> actoacList;
    private final Short fSctnum = 8;
    private Actact act;
    private short wkYear;
    private String oldacn;

//===================================================================

    public static void main(String argv[]) {
        BCB85510Handler handler = new BCB85510Handler();
        BatchParameterData parameterData = new BatchParameterData();
        handler.processBusiness(parameterData);
    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            initBatch();
            initActoacList();
            processActoacList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    private void initBatch() throws ParseException {
        cbs.batch.ac.bcb85510.DateConverter dateConverter = new DateConverter();
        ConvertUtils.register(dateConverter, Date.class);
    }

    private void initActoacList() {
        Actsct sct = new Actsct();
        sct = sctMapper.selectByPrimaryKey(fSctnum);
        Date crndate = sct.getCrndat();
        SimpleDateFormat dateformat = new SimpleDateFormat("yy");
        String year = dateformat.format(crndate);
        wkYear = Short.parseShort(year);
        List<String> strList = new ArrayList();
        strList.add("O");
        strList.add("U");
        oacExample.createCriteria().andCredatEqualTo(crndate).andRecstsIn(strList);
        this.actoacList = oacMapper.selectByExample(oacExample);
    }

    private int processActoacList() throws ParseException {
        int count = 0;
        for (Actoac oac : this.actoacList) {
            //获取actact数据
            act = getActactRecord(oac);
            //
            oldacn = "0";
            String oac_recsts = oac.getRecsts();
            String oac_stmfmt = oac.getStmfmt();
            String oac_legfmt = oac.getLegfmt();
            //查询表actsbl
            sblExample.clear();
            this.sblExample.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(oac.getOrgidt())
                        .andCusidtEqualTo(oac.getCusidt()).andApcodeEqualTo(oac.getApcode()).andCurcdeEqualTo(oac.getCurcde());
            List<Actsbl> sblList = this.sblMapper.selectByExample(this.sblExample);
            //查询表actlbl
            lblExample.clear();
            this.lblExample.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(oac.getOrgidt())
                        .andCusidtEqualTo(oac.getCusidt()).andApcodeEqualTo(oac.getApcode()).andCurcdeEqualTo(oac.getCurcde());
//            this.lblExample.;
            this.lblExample.setDistinct(true);
            List<Actlbl> lblList = this.lblMapper.selectByExample(this.lblExample);
            if (oac_recsts.equals("O")) {
                //处理actsbl表
                if (sblList.size() > 0) {
                    updateActsbl(oac);
                } else if (oac_stmfmt.equals("C") || oac_stmfmt.equals("L") || oac_stmfmt.equals("S")
                        || oac_stmfmt.equals("O")) {
                    insertActsbl(oac);
                }
                //处理actlbl表
                if (lblList.size() > 0) {
                    updateActlbl(oac);
                } else if (oac_legfmt.equals("C") || oac_legfmt.equals("I") || oac_legfmt.equals("O")) {
                    insertActlbl_CI(oac);
                } else if (oac_legfmt.equals("F")) {
                    this.lblMapper.insertFromFXE(oldacn,oac.getActnam(),wkYear,oac.getLegcyc(),oac.getLegcdt(),
                            oac.getLegfmt(),oac.getLegsht(),oac.getLegdep(),oac.getLegadd(),oac.getLegzip(),
                            ACEnum.SYSIDT_AC.getStatus(),ACEnum.RECSTS_VALID.getStatus(),oac.getOrgidt(),
                            oac.getCusidt(),oac.getApcode(),oac.getCurcde());
                }
            } else if (oac_recsts.equals("U")) {
                boolean sblFound = true;
                boolean lblFound = true;
                //处理actsbl表
                if (sblList.size() > 0) {
                    Actsbl sbl = sblList.get(0);
                    if ((sbl.getStmfmt().equals("O") || sbl.getStmfmt().equals(" ")) &&
                            (oac.getStmfmt().equals("C") || oac.getStmfmt().equals("S") || oac.getStmfmt().equals("L"))) {
                        sbl.setStmcyc(oac.getStmcyc());
                        sbl.setStmfmt(oac.getStmfmt());
                        sbl.setRecsts(" ");
                    } else if (!sbl.getStmfmt().equals(oac.getStmfmt()) || !sbl.getStmcyc().equals(oac.getStmcyc())) {
                        sbl.setRecsts("U");
                    }
                    updateActsblForU(sbl,oac);
                } else {
                    sblFound = false;
                    logger.info("SBL-ACTNUM:not found!");
                }
                //处理actlbl表
                if (lblList.size() > 0) {
                    Actlbl lbl = lblList.get(0);
                    if ((lbl.getLegfmt().equals("O") || lbl.getLegfmt().equals(" ")) &&
                            (oac.getLegfmt().equals("C") || oac.getLegfmt().equals("I") || oac.getLegfmt().equals("F"))) {
                        lbl.setLegfmt(oac.getLegfmt());
                        lbl.setLegcyc(oac.getLegcyc());
                        lbl.setRecsts(" ");
                    } else if (!lbl.getLegfmt().equals(oac.getLegfmt()) || !lbl.getLegcyc().equals(oac.getLegcyc())) {
                        lbl.setRecsts("U");
                    }
                    int flag = 0;
                    if (oac.getActtyp().equals("5")) {
                        flag = 1;
                        this.lblMapper.updateLegbal(ACEnum.SYSIDT_AC.getStatus(),oac.getOrgidt(),oac.getCusidt(),
                                oac.getApcode(),oac.getCurcde());
                    } else {
                        flag = 2;
                    }
                    updateActlblForU(lbl,oac,flag);

                } else {
                    lblFound = false;
                    logger.info("LBL-ACTNUM:not found!");
                }
                //如果以上两表有一个不存在 执行过程oac_recsts.equals("O") row：134-151
                if (!sblFound || !lblFound) {
                     //处理actsbl表
                    if (sblList.size() > 0) {
                        updateActsbl(oac);
                    } else if (oac_stmfmt.equals("C") || oac_stmfmt.equals("L") || oac_stmfmt.equals("S")
                            || oac_stmfmt.equals("O")) {
                        insertActsbl(oac);
                    }
                    //处理actlbl表
                    if (lblList.size() > 0) {
                        updateActlbl(oac);
                    } else if (oac_legfmt.equals("C") || oac_legfmt.equals("I") || oac_legfmt.equals("O")) {
                        insertActlbl_CI(oac);
                    } else if (oac_legfmt.equals("F")) {
                        this.lblMapper.insertFromFXE(oldacn,oac.getActnam(),wkYear,oac.getLegcyc(),oac.getLegcdt(),
                                oac.getLegfmt(),oac.getLegsht(),oac.getLegdep(),oac.getLegadd(),oac.getLegzip(),
                                ACEnum.SYSIDT_AC.getStatus(),ACEnum.RECSTS_VALID.getStatus(),oac.getOrgidt(),
                                oac.getCusidt(),oac.getApcode(),oac.getCurcde());
                    }
                    sblFound = true;
                    lblFound = true;
                }
            } else if (!oac_recsts.equals("O") && !oac_recsts.equals("U") && !oac_recsts.equals("C") && !oac_recsts.equals("A")) {
                logger.error("THIS ACCOUNT  OAC ERR, OAC-ACTNUM=" + oac.getActnam());
            }
            count++;
            updateActoacRecsts(oac);
        }
        return count;
    }

    private Actact getActactRecord(Actoac oac) {
        this.actExample.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(oac.getOrgidt())
                        .andCusidtEqualTo(oac.getCusidt()).andApcodeEqualTo(oac.getApcode()).andCurcdeEqualTo(oac.getCurcde());
        List<Actact> actList = this.actMapper.selectByExample(actExample);
        return actList.get(0);
    }

    private void updateActsbl(Actoac oac) {
        Actsbl sbl = new Actsbl();
        sbl.setActnam(oac.getActnam());
        sbl.setStmcyc(oac.getStmcyc());
        sbl.setStmcdt(oac.getStmcdt());
        sbl.setStmfmt(oac.getStmfmt());
        sbl.setStmsht(oac.getStmsht());
        sbl.setStmdep(oac.getStmdep());
        sbl.setStmadd(oac.getStmadd());
        sbl.setStmzip(oac.getStmzip());
        sbl.setStmbal(act.getBokbal());
        sbl.setFstpag("Y");
        sbl.setRecsts(" ");
        this.sblMapper.updateByExampleSelective(sbl,this.sblExample);
    }

     private void updateActsblForU(Actsbl sblX,Actoac oac) {
        Actsbl sbl = new Actsbl();
        sbl.setActnam(oac.getActnam());
        sbl.setStmcyc(sblX.getStmcyc());
        sbl.setStmcdt(oac.getStmcdt());
        sbl.setStmfmt(sblX.getStmfmt());
        sbl.setStmsht(oac.getStmsht());
        sbl.setStmdep(oac.getStmdep());
        sbl.setStmadd(oac.getStmadd());
        sbl.setStmzip(oac.getStmzip());
        sbl.setStmbal(act.getBokbal());
        sbl.setRecsts(sblX.getRecsts());
        this.sblMapper.updateByExampleSelective(sbl,this.sblExample);
    }

    private void insertActsbl(Actoac oac) {
        Actsbl sbl = new Actsbl();
        sbl.setSysidt(ACEnum.SYSIDT_AC.getStatus());
        sbl.setOrgidt(oac.getOrgidt());
        sbl.setCusidt(oac.getCusidt());
        sbl.setApcode(oac.getApcode());
        sbl.setCurcde(oac.getCurcde());
        sbl.setSecccy("   ");
        sbl.setOldacn(oldacn);  //????
        sbl.setActnam(oac.getActnam());
        sbl.setStmbal(act.getBokbal());
        sbl.setSecsbl((long)0);
        sbl.setNstmll(0);
        sbl.setNstmny(wkYear);        
        sbl.setNstmpg(1);
        sbl.setStmpln((short)1);
        sbl.setStmcyc(oac.getStmcyc());
        sbl.setStmcdt(oac.getStmcdt());
        sbl.setStmfmt(oac.getStmfmt());
        sbl.setStmsht(oac.getStmsht());
        sbl.setStmdep(oac.getStmdep());
        sbl.setStmadd(oac.getStmadd());
        sbl.setStmzip(oac.getStmzip());
        sbl.setFstpag("Y");
        sbl.setRecsts(ACEnum.RECSTS_VALID.getStatus());
        this.sblMapper.insert(sbl);
    }

    private void updateActlbl(Actoac oac) {
        Actlbl lbl = new Actlbl();
        lbl.setActnam(oac.getActnam());
        lbl.setLegcyc(oac.getLegcyc());
        lbl.setLegcdt(oac.getLegcdt());
        lbl.setLegfmt(oac.getLegfmt());
        lbl.setLegsht(oac.getLegsht());
        lbl.setLegdep(oac.getLegdep());
        lbl.setLegadd(oac.getLegadd());
        lbl.setLegzip(oac.getLegzip());
        lbl.setLegbal(act.getBokbal());
        lbl.setFstpag("Y");
        lbl.setRecsts(" ");
        lblMapper.updateByExampleSelective(lbl,this.lblExample);
    }

    private void updateActlblForU(Actlbl lblX,Actoac oac,int flag) {
        Actlbl lbl = new Actlbl();
        lbl.setActnam(oac.getActnam());
        lbl.setLegcyc(lblX.getLegcyc());
        lbl.setLegcdt(oac.getLegcdt());
        lbl.setLegfmt(lblX.getLegfmt());
        lbl.setLegsht(oac.getLegsht());
        lbl.setLegdep(oac.getLegdep());
        lbl.setLegadd(oac.getLegadd());
        lbl.setLegzip(oac.getLegzip());
        if (flag == 2){
            lbl.setLegbal(act.getBokbal());
        }
        lbl.setRecsts(lblX.getRecsts());
        lblMapper.updateByExampleSelective(lbl,this.lblExample);
    }

    private void insertActlbl_CI(Actoac oac) {
        Actlbl lbl = new Actlbl();
        lbl.setSysidt(ACEnum.SYSIDT_AC.getStatus());
        lbl.setOrgidt(oac.getOrgidt());
        lbl.setCusidt(oac.getCusidt());
        lbl.setApcode(oac.getApcode());
        lbl.setCurcde(oac.getCurcde());
        lbl.setSecccy("   ");
        lbl.setOldacn(oldacn);  //????
        lbl.setActnam(oac.getActnam());
        lbl.setLegbal(act.getBokbal());
        lbl.setSeclbl((long)0);
        lbl.setNlegll(0);
        lbl.setNlegny(wkYear);        
        lbl.setNlegpg(1);
        lbl.setLegpln((short)1);
        lbl.setLegcyc(oac.getLegcyc());
        lbl.setLegcdt(oac.getLegcdt());
        lbl.setLegfmt(oac.getLegfmt());
        lbl.setLegsht(oac.getLegsht());
        lbl.setLegdep(oac.getLegdep());
        lbl.setLegadd(oac.getLegadd());
        lbl.setLegzip(oac.getLegzip());
        lbl.setFstpag("Y");
        lbl.setRecsts(ACEnum.RECSTS_VALID.getStatus());
        lblMapper.insert(lbl);
    }

    /*
    * 更新联机余额表Actoac*/
    private void updateActoacRecsts(Actoac oac) {
        this.oacExample.clear();
        this.oacExample.createCriteria().andOrgidtEqualTo(oac.getOrgidt()).andCusidtEqualTo(oac.getCusidt()).
                andApcodeEqualTo(oac.getApcode()).andCurcdeEqualTo(oac.getCurcde());
        Actoac oac1 = new Actoac();
        oac1.setRecsts(ACEnum.RECSTS_OAC_ACCESSED.getStatus());
        this.oacMapper.updateByExampleSelective(oac1,this.oacExample);
    }

    public void setOacMapper(ActoacMapper oacMapper) {
        this.oacMapper = oacMapper;
    }

    public void setOacExample(ActoacExample oacExample) {
        this.oacExample = oacExample;
    }

    public void setSctMapper(ActsctMapper sctMapper) {
        this.sctMapper = sctMapper;
    }

    public void setSblMapper(ActsblMapper sblMapper) {
        this.sblMapper = sblMapper;
    }

    public void setSblExample(ActsblExample sblExample) {
        this.sblExample = sblExample;
    }

    public void setLblMapper(ActlblMapper lblMapper) {
        this.lblMapper = lblMapper;
    }

    public void setLblExample(ActlblExample lblExample) {
        this.lblExample = lblExample;
    }

    public void setActMapper(ActactMapper actMapper) {
        this.actMapper = actMapper;
    }

    public void setActExample(ActactExample actExample) {
        this.actExample = actExample;
    }
}
