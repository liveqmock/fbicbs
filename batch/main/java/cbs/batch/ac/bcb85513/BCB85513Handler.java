package cbs.batch.ac.bcb85513;

import cbs.batch.ac.bcb85511.model.ActsctForDate;
import cbs.batch.ac.bcb85513.dao.BCB85513Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.billinfo.model.Actlgc;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.account.maininfo.model.Actvth;
import cbs.repository.code.model.Actani;
import cbs.common.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-28
 * Time: 16:51:39
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB85513Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85513Handler.class);
    @Inject
    private BCB85513Mapper bcb85513Map;

    private String sctYermak;
    private String sctTdymak;
    private String sctQtrmak;
    private short wkYear2;   //获取ActsctForDate表crndatYear后两位
    private int wkprcount = 0;   //插入lgc数据记录数
    private List<Actvth> actvthList;
    private String crnOrgidt = "";
    private String crnCurcde = "";
    private String crnApcode = "";
    private String crnCusidt = "";
    private int lasNlegpg = 0;
    private short lasPaglin = 0;
    private Actlbl lbl = new Actlbl();
    private Actlgc lgc = new Actlgc();

    private String needUpLBL = "N";
    private String outThisTime = "N";
    private int cpLineLim = 0;


    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            updateActlbl();//更新Actsbl下次出账数据字段
            initActvthList(); //获取Actvth所有数据
            processActvth();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateActlbl() {
        ActsctForDate sct = this.bcb85513Map.selectActsctByPK((short) 8);
        sctYermak = sct.getYermak();
        this.sctTdymak = sct.getTdymak();
        this.sctQtrmak = sct.getQtrmak();
        wkYear2 = Short.parseShort(sct.getCrndatYear().substring(2));
        if (!sct.getCrndatYear().equals(sct.getLwkdayYear())) {
            this.bcb85513Map.updateActlblForNlegny(this.wkYear2);
        }
    }

    private int processActvth() {
        for (Actvth vth : this.actvthList) {
            if (!vth.getOrgid3().equals(this.crnOrgidt) || !vth.getCurcde().equals(this.crnCurcde) ||
                    !vth.getApcode().equals(this.crnApcode) || !vth.getCusidt().equals(this.crnCusidt)) {
                if (!this.crnOrgidt.equals("") && this.needUpLBL.equals("Y")) {
                    // 5930-UPDATE-LAST-LBL-RTN
                    updateLastLBLRTN();
                }
                this.crnOrgidt = vth.getOrgid3();
                this.crnCurcde = vth.getCurcde();
                this.crnApcode = vth.getApcode();
                this.crnCusidt = vth.getCusidt();
                this.outThisTime = "N";
                this.needUpLBL = "N";
                //todo 5920-SELECT-LBL-RTN
                selectActlbl();
                //循环如果主键相同跳过本次循环 以下操作不予处理
                if (!lbl.getLegfmt().equals("C") && !lbl.getLegfmt().equals("I")) {
                    if (vth.getOrgid3().equals(this.crnOrgidt) && vth.getCurcde().equals(this.crnCurcde) &&
                            vth.getApcode().equals(this.crnApcode) && vth.getCusidt().equals(this.crnCusidt)) {
                        //循环vth。cusidt特殊值处理
                        if (vth.getApcode().equals("9991")) {
                            vth.setCusidt("9990000");
                        }
                        continue;
                    }
                }
            }
            this.needUpLBL = "Y";
            lgc.setLasbal(lgc.getActbal());
            if (vth.getRvslbl().equals(ACEnum.RVSLBL_TRUE.getStatus())) {
                lgc.setActbal(lgc.getActbal() - vth.getTxnamt());
            } else {
                lgc.setActbal(lgc.getActbal() + vth.getTxnamt());
            }
            //todo 5950-INSERT-LGC-RTN   问题 lgc里边的值为Null
            // changed by zxb
            if(vth.getSecccy() == null) vth.setSecccy("");
            if(vth.getThrref() == null) vth.setThrref("");
            System.out.print(ACEnum.SYSIDT_AC.getStatus() + "," + vth.getOrgid3() + "," + vth.getCusidt() + "," + vth.getApcode() + "," +
                    vth.getCurcde() + ",wkYear2: " + this.wkYear2 + ",getNlegpg:" + lgc.getNlegpg() + ",getPaglin" + lgc.getPaglin());
            this.bcb85513Map.insertActlgc(ACEnum.SYSIDT_AC.getStatus(), vth.getOrgid3(), vth.getCusidt(), vth.getApcode(),
                    vth.getCurcde(), vth.getErydat(), vth.getErytim(), vth.getTlrnum(), vth.getVchset(), vth.getSetseq(), vth.getTxnamt(),
                    lgc.getActbal(), vth.getValdat(), vth.getRvslbl(), vth.getOrgid2(), vth.getPrdcde(), vth.getCrnyer(), vth.getPrdseq(),
                    vth.getThrref(), vth.getFurinf(), vth.getFxrate(), vth.getSecccy(), vth.getSecamt(), lgc.getSecbal(), this.wkYear2,
                    lgc.getNlegpg(), lgc.getPaglin(), lgc.getRecsts(), lgc.getFstpag(), lgc.getDayend(), vth.getFxeflg(), lgc.getLegdep(),
                    lgc.getSeclba(), lgc.getLasbal(), vth.getOrgidt());
            this.lasNlegpg = lgc.getNlegpg();
            this.lasPaglin = lgc.getPaglin();
            if (lgc.getPaglin() == this.cpLineLim) {
                lgc.setFstpag("N");
                if (lbl.getLegcyc().equals("P")) {
                    this.bcb85513Map.updateActlgcRecsts(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                            this.crnApcode, this.crnCurcde, lgc.getNlegpg());
                    this.outThisTime = "Y";
                }
                lgc.setNlegpg(lgc.getNlegpg() + 1);
                lgc.setPaglin((short) 0);
            }
            wkprcount += 1;
            logger.info("INSERT LGC  RECORD COUNT IS:" + String.valueOf(this.wkprcount));
            lgc.setPaglin((short) (lgc.getPaglin() + 1));
            //循环vth。cusidt特殊值处理
            if (vth.getApcode().equals("9991")) {
                vth.setCusidt("9990000");
            }
        }
        processActvthNull();
        return 1;
    }

    /*
    * 讀取actlbl表數據
    * */

    private void selectActlbl() {
        lbl = this.bcb85513Map.selectLblByPKandRecsts(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                this.crnApcode, this.crnCurcde, ACEnum.RECSTS_INVALID.getStatus());
        if (lbl == null) {
            Actoac oac = this.bcb85513Map.selectOacByPK(this.crnOrgidt, this.crnCusidt, this.crnApcode, this.crnCurcde);
            if (oac == null) {
                logger.info("ACTOAC NOT FOR INSERT LBL" + this.crnCusidt + " " + this.crnApcode + " " + this.crnCurcde);
            } else {
                lbl = new Actlbl();
                String oldacn = "";
                Actani ani = this.bcb85513Map.selectActaniByPK(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                        this.crnApcode, this.crnCurcde, ACEnum.RECSTS_VALID.getStatus());
                if (ani != null) {
                    oldacn = ani.getOldacn();
                }
                lbl.setOldacn(oldacn);
                lbl.setLegfmt(oac.getLegfmt());
                lbl.setLegcyc(oac.getLegcyc());
                lbl.setLegcdt(oac.getLegcdt());
                lbl.setLegdep(oac.getLegdep());
                lbl.setLegadd(oac.getLegadd());
                lbl.setLegzip(oac.getLegzip());
                lbl.setLegsht(oac.getLegsht());
                lbl.setActnam(oac.getActnam());
                lbl.setSecccy("   ");
                lbl.setSeclbl((long) 0);
                lbl.setLegbal((long) 0);
                lbl.setNlegll(1);
                lbl.setNlegny(this.wkYear2);
                lbl.setNlegpg(1);
                lbl.setLegpln((short) 1);
                lbl.setFstpag("Y");
                //插入表ACTLBL
                this.bcb85513Map.insertActlbl(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt, this.crnApcode,
                        this.crnCurcde, lbl.getSecccy(), lbl.getOldacn(), lbl.getActnam(), lbl.getLegbal(), lbl.getSeclbl(),
                        lbl.getNlegll(), lbl.getNlegny(), lbl.getNlegpg(), lbl.getLegpln(), lbl.getLegcyc(), lbl.getLegcdt(),
                        lbl.getLegfmt(), lbl.getLegsht(), lbl.getLegdep(), lbl.getLegadd(), lbl.getLegzip());

            }
        }
        if (lbl.getLegcyc().equals(ACEnum.LEGCYC_D.getStatus()) || lbl.getLegcyc().equals(ACEnum.LEGCYC_E.getStatus()) ||
                (lbl.getLegcyc().equals(ACEnum.LEGCYC_T.getStatus()) && this.sctTdymak.equals(ACEnum.TDYMAK_TRUE.getStatus())) ||
                (lbl.getLegcyc().equals(ACEnum.LEGCYC_S.getStatus()) && this.sctQtrmak.equals(ACEnum.QTRMAK_TRUE.getStatus())) ||
                (lbl.getLegcyc().equals(ACEnum.LEGCYC_Y.getStatus()) && this.sctYermak.equals(ACEnum.YERMAK_TRUE.getStatus()))) {
            lgc.setRecsts("P");
            if (lbl.getLegfmt().equals("C") || lbl.getLegfmt().equals("I")) {
                this.outThisTime = "Y";
            }
        } else {
            lgc.setRecsts(" ");
        }
        lgc.setSecbal((long) 0);
        lgc.setSeclba((long) 0);
        lgc.setActbal(lbl.getLegbal());
        lgc.setLegpny(lbl.getNlegny());
        lgc.setNlegpg(lbl.getNlegpg());
        lgc.setPaglin(lbl.getLegpln());
        lgc.setLegdep(lbl.getLegdep());
        lgc.setFstpag(lbl.getFstpag());
    }

    private void processActvthNull() {
        if (!this.crnOrgidt.equals("") && this.needUpLBL.equals("Y")) {
            // 5930-UPDATE-LAST-LBL-RTN
            updateLastLBLRTN();
        }
    }

    private void updateLastLBLRTN() {
        Actact act = this.bcb85513Map.selectActactByPK(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                this.crnApcode, this.crnCurcde);
        this.bcb85513Map.updateActlgcByActact(act.getDraccm(), act.getCraccm(), ACEnum.SYSIDT_AC.getStatus(),
                this.crnOrgidt, this.crnCusidt, this.crnApcode, this.crnCurcde, this.lasNlegpg, this.lasPaglin);
        lbl.setLegbal(lgc.getActbal());
        lbl.setNlegpg(lgc.getNlegpg());
        lbl.setLegpln(lgc.getPaglin());
        lbl.setFstpag(lgc.getFstpag());
        if (lgc.getPaglin().equals(cpLineLim)) {
            if (lbl.getLegcyc().equals("P")) {
                this.bcb85513Map.updateActlgcRecsts(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                        this.crnApcode, this.crnCurcde, lgc.getNlegpg());
                this.outThisTime = "Y";
            }
            lbl.setLegpln((short) 1);
            lbl.setNlegpg(lbl.getNlegpg() + 1);
        }
        /*else if (lgc.getPaglin() != 1) {
            lbl.setLegpln((short) (lgc.getPaglin() + (short) 1));
        }*/
        if (this.outThisTime.equals("Y")) {
            if (lbl.getLegcyc().equals("P")) {
                this.bcb85513Map.updateActlgcFstpag(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                        this.crnApcode, this.crnCurcde, lgc.getNlegpg());
            } else if (!lgc.getPaglin().equals(1)) {
                lbl.setLegpln((short) 1);
                lbl.setNlegpg(lbl.getNlegpg() + 1);
            }
            lbl.setFstpag("Y");
        }
        //更新actlbl表
        this.bcb85513Map.updateLastActlbl(lbl.getLegbal(), lbl.getNlegny(), lbl.getNlegpg(), lbl.getLegpln(), lbl.getFstpag(),
                ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt, this.crnApcode, this.crnCurcde);

    }

    private void initActvthList() {
        cpLineLim = SystemService.getLineLimit();
        this.actvthList = this.bcb85513Map.selectActvth();
        initActlgc();  //lgc对象赋初值
    }

    private void initActlgc() {
        lgc.setActbal((long) 0);
        lgc.setLasbal((long) 0);
        lgc.setSecbal((long) 0);
        lgc.setNlegpg(0);
        lgc.setPaglin((short) 0);
        lgc.setRecsts(" ");
        lgc.setFstpag("");
        lgc.setDayend("");
        lgc.setLegdep("");
        lgc.setSeclba((long) 0);
        lgc.setLasbal((long) 0);
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public void setBcb85513Map(BCB85513Mapper bcb85513Map) {
        this.bcb85513Map = bcb85513Map;
    }

}
