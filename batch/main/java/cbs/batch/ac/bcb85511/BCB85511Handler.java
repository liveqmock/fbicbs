package cbs.batch.ac.bcb85511;

import cbs.batch.ac.bcb85511.dao.BCB85511Mapper;
import cbs.batch.ac.bcb85511.model.ActsctForDate;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.billinfo.model.Actnsm;
import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.account.maininfo.model.Actvth;
import cbs.common.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-25
 * Time: 15:03:00
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB85511Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85511Handler.class);
    @Inject
    private BCB85511Mapper bcb85511Map;

    private String sctYermak;
    private String sctTdymak;
    private String sctQtrmak;
    private short wkYear2;   //获取ActsctForDate表crndatYear后两位
    private int wkprcount = 0;   //插入nsm数据记录数
    private List<Actvth> actvthList;
    private String crnOrgidt = "";
    private String crnCurcde = "";
    private String crnApcode = "";
    private String crnCusidt = "";
    private int lasNstmpg = 0;
    private short lasPaglin = 0;
    private Actnsm nsm = new Actnsm();
    private Actsbl sbl = new Actsbl();
    private final int cpLineLim = 42;
    private final int cnLineLimS = 45;
    private final int cnLineLimB = 50;
    private int linelimt;
    private String needUpSBL = "N";
    private String outThisTime = "N";

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            updateActsbl();//更新Actsbl下次出账数据字段
            initActvthList(); //获取Actvth所有数据
            processActvth();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateActsbl() {
        ActsctForDate sct = this.bcb85511Map.selectActsctByPK((short) 8);
        sctYermak = sct.getYermak();
        this.sctTdymak = sct.getTdymak();
        this.sctQtrmak = sct.getQtrmak();
        wkYear2 = Short.parseShort(sct.getCrndatYear().substring(2));
        if (!sct.getCrndatYear().equals(sct.getLwkdayYear())) {
            this.bcb85511Map.updateActsblForNst(this.wkYear2);
        }
    }

    private void initActvthList() {
         this.linelimt = SystemService.getLineLimit();
         this.actvthList = this.bcb85511Map.selectActvth();

    }

    private int processActvth() {

        for (Actvth vth : this.actvthList) {
            if (!vth.getOrgid3().equals(this.crnOrgidt) || !vth.getCurcde().equals(this.crnCurcde) ||
                    !vth.getApcode().equals(this.crnApcode) || !vth.getCusidt().equals(this.crnCusidt)) {
                if (!this.crnOrgidt.equals("") && this.needUpSBL.equals("Y")) {
                    //  5930-UPDATE-LAST-SBL-RTN
                    updateLastSBLRTN();
                }
                this.crnOrgidt = vth.getOrgid3();
                this.crnCurcde = vth.getCurcde();
                this.crnApcode = vth.getApcode();
                this.crnCusidt = vth.getCusidt();
                this.outThisTime = "N";
                this.needUpSBL = "N";
                //
                selectActsbl();
                if (!sbl.getStmfmt().equals("C") && !sbl.getStmfmt().equals("S")) {
                    if (vth.getOrgid3().equals(this.crnOrgidt) && vth.getCurcde().equals(this.crnCurcde) &&
                            vth.getApcode().equals(this.crnApcode) && vth.getCusidt().equals(this.crnCusidt)) {
                        if (vth.getApcode().equals("9991")) {
                            vth.setCusidt("9990000");
                        }
                        continue;
                    }
                }
            }
            this.needUpSBL = "Y";
            nsm.setLasbal(nsm.getActbal());
            if (vth.getRvslbl().equals(ACEnum.RVSLBL_TRUE.getStatus())) {
                nsm.setActbal(nsm.getActbal() - vth.getTxnamt());
            } else {
                nsm.setActbal(nsm.getActbal() + vth.getTxnamt());
            }
            //todo 5950-INSERT-NSM-RTN
//            nsm.setDayend("");
            System.out.print(ACEnum.SYSIDT_AC.getStatus() + "," + vth.getOrgid3() + "," + vth.getCusidt() + "," + vth.getApcode() + "," +
                    vth.getCurcde() + ",wkYear2: " + this.wkYear2 + ",getNstmpg:" +
                    nsm.getNstmpg() + ",getPaglin: " + nsm.getPaglin() + ",getErydat: " + vth.getErydat() + ",getErytim: " + vth.getErytim() + ",getTlrnum: " + vth.getTlrnum() + ",getVchset:" + vth.getVchset() + ",getSetseq: " + vth.getSetseq() + ",getTxnamt:" + vth.getTxnamt() + ",getActbal: " +
                    nsm.getActbal() + ",getValdat: " + vth.getValdat() + ",getRvslbl: " + vth.getRvslbl() + ",getOrgid2: " + vth.getOrgid2() + ",getPrdcde: " + vth.getPrdcde() + ",getCrnyer: " + vth.getCrnyer() + ",getPrdseq: " + vth.getPrdseq() + "," +
                    vth.getThrref() + ",getFurinf: " + vth.getFurinf() + ",getFxrate: " + vth.getFxrate() + ",getSecccy: " + vth.getSecccy() + ",getSecamt: " + vth.getSecamt() + ",getSecbal: " + nsm.getSecbal() + ",getRecsts: " + nsm.getRecsts() + ",getFstpag: " + nsm.getFstpag() + ",getDayend: " + ",getFxeflg: " + vth.getFxeflg() + ",getStmdep: " + nsm.getStmdep() + ",getSeclba: " +
                    nsm.getSeclba() + "," + nsm.getLasbal() + "," + vth.getOrgidt());
            //   changed by zxb
            if(vth.getSecccy() == null) vth.setSecccy("");
            if(vth.getThrref() == null) vth.setThrref("");
            this.bcb85511Map.insertActnsm(ACEnum.SYSIDT_AC.getStatus(), vth.getOrgid3(), vth.getCusidt(), vth.getApcode(),
                    vth.getCurcde(), vth.getErydat(), vth.getErytim(), vth.getTlrnum(), vth.getVchset(), vth.getSetseq(), vth.getTxnamt(),
                    nsm.getActbal(), vth.getValdat(), vth.getRvslbl(), vth.getOrgid2(), vth.getPrdcde(), vth.getCrnyer(), vth.getPrdseq(),
                    vth.getThrref(), vth.getFurinf(), vth.getFxrate(), vth.getSecccy(), vth.getSecamt(), nsm.getSecbal(), this.wkYear2,
                    nsm.getNstmpg(), nsm.getPaglin(), nsm.getRecsts(), nsm.getFstpag(), "", vth.getFxeflg(), nsm.getStmdep(),
                    nsm.getSeclba(), nsm.getLasbal(), vth.getOrgidt());
            this.lasNstmpg = nsm.getNstmpg();
            this.lasPaglin = nsm.getPaglin();
            if ((nsm.getPaglin() == linelimt && sbl.getStmcyc().equals("P")) ||
                    ((nsm.getPaglin() == linelimt && nsm.getFstpag().equals("Y")) ||
                            (nsm.getPaglin() == linelimt && !nsm.getFstpag().equals("Y")))) {
                nsm.setFstpag("N");
                if (sbl.getStmcyc().equals("P")) {
                    this.bcb85511Map.updateActnsmRecsts(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                            this.crnApcode, this.crnCurcde, nsm.getNstmpg());
                    this.outThisTime = "Y";
                }
                nsm.setNstmpg(nsm.getNstmpg() + 1);
                nsm.setPaglin((short) 0);
            }
            wkprcount += 1;
            nsm.setPaglin((short) (nsm.getPaglin() + 1));
            logger.info("INSERT NSM  RECORD COUNT IS:" + String.valueOf(this.wkprcount));
            if (vth.getApcode().equals("9991")) {
                vth.setCusidt("9990000");
            }
        }
        processActvthNull();
        return 0;
    }

    /*
    * 讀取actsbl表數據
    * */

    private void selectActsbl() {
        sbl = this.bcb85511Map.selectsblByPKandRecsts(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                this.crnApcode, this.crnCurcde, ACEnum.RECSTS_INVALID.getStatus());
        //插入sbl
        if (sbl == null) {
            Actoac oac = this.bcb85511Map.selectOacByPK(this.crnOrgidt, this.crnCusidt, this.crnApcode, this.crnCurcde);
            if (oac == null) {
                logger.info("ACTOAC NOT FOR INSERT SBL" + this.crnCusidt + " " + this.crnApcode + " " + this.crnCurcde);
            } else {
                sbl.setStmfmt(oac.getStmfmt());
                sbl.setStmcyc(oac.getStmcyc());
                sbl.setStmcdt(oac.getStmcdt());
                sbl.setStmdep(oac.getStmdep());
                sbl.setStmadd(oac.getStmadd());
                sbl.setStmzip(oac.getStmzip());
                sbl.setStmsht(oac.getStmsht());
                sbl.setActnam(oac.getActnam());
                sbl.setSecccy("   ");
                sbl.setSecsbl((long) 0);
                sbl.setStmbal((long) 0);
                sbl.setNstmll(1);
                sbl.setNstmny(wkYear2);
                sbl.setNstmpg(1);
                sbl.setStmpln((short) 1);
                sbl.setFstpag("Y");
                this.bcb85511Map.insertActsbl(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt, this.crnApcode,
                        this.crnCurcde, sbl.getSecccy(), " ", sbl.getActnam(), sbl.getStmbal(), sbl.getSecsbl(), sbl.getNstmll(),
                        sbl.getNstmny(), sbl.getNstmpg(), sbl.getStmpln(), sbl.getStmcyc(), sbl.getStmcdt(), sbl.getStmfmt(),
                        sbl.getStmsht(), sbl.getStmdep(), sbl.getStmadd(), sbl.getStmzip());
            }
        }
        if (sbl.getStmcyc().equals(ACEnum.STMCYC_D.getStatus()) || sbl.getStmcyc().equals(ACEnum.STMCYC_E.getStatus()) ||
                (sbl.getStmcyc().equals(ACEnum.STMCYC_T.getStatus()) && this.sctTdymak.equals(ACEnum.TDYMAK_TRUE.getStatus())) ||
                (sbl.getStmcyc().equals(ACEnum.STMCYC_S.getStatus()) && this.sctQtrmak.equals(ACEnum.QTRMAK_TRUE.getStatus())) ||
                (sbl.getStmcyc().equals(ACEnum.STMCYC_Y.getStatus()) && sctYermak.equals(ACEnum.YERMAK_TRUE.getStatus()))) {
            nsm.setRecsts("P");
            if (sbl.getStmfmt().equals("C") || sbl.getStmfmt().equals("S")) {
                this.outThisTime = "Y";
            }
        } else {
            nsm.setRecsts(" ");
        }
        nsm.setSecbal((long) 0);
        nsm.setSeclba((long) 0);
        nsm.setActbal(sbl.getStmbal());
        nsm.setStmpny(sbl.getNstmny());
        nsm.setNstmpg(sbl.getNstmpg());
        nsm.setPaglin(sbl.getStmpln());
        nsm.setStmdep(sbl.getStmdep());
        nsm.setFstpag(sbl.getFstpag());
    }

    /*
    * actvth循环到最后一行数据处理*/

    private void processActvthNull() {
        if (!this.crnOrgidt.equals("") && this.needUpSBL.equals("Y")) {
            // 5930-UPDATE-LAST-SBL-RTN
            updateLastSBLRTN();
        }
    }

    private void updateLastSBLRTN() {
        Actact act = this.bcb85511Map.selectActactByPK(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                this.crnApcode, this.crnCurcde);
        this.bcb85511Map.updateActnsmByActact(act.getDraccm(), act.getCraccm(), ACEnum.SYSIDT_AC.getStatus(),
                this.crnOrgidt, this.crnCusidt, this.crnApcode, this.crnCurcde, this.lasNstmpg, this.lasPaglin);
        sbl.setStmbal(nsm.getActbal());
        sbl.setNstmpg(nsm.getNstmpg());
        sbl.setStmpln(nsm.getPaglin());
        sbl.setFstpag(nsm.getFstpag());
        if ((nsm.getPaglin() == linelimt && sbl.getStmcyc().equals("P")) || (nsm.getPaglin() == linelimt && nsm.getFstpag().equals("Y")) ||
                (nsm.getPaglin() == linelimt && !nsm.getFstpag().equals("Y"))) {
            if (sbl.getStmcyc().equals("P")) {
                this.bcb85511Map.updateActnsmRecsts(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                        this.crnApcode, this.crnCurcde, nsm.getNstmpg());
                this.outThisTime = "Y";
            }
            sbl.setStmpln((short) 1);
            sbl.setNstmpg(sbl.getNstmpg() + 1);
        }
//        else if (nsm.getPaglin() != 1)
//            sbl.setStmpln((short) (nsm.getPaglin() + (short) 1));
        if (this.outThisTime.equals("Y")) {
            if (sbl.getStmcyc().equals("P")) {
                //更新ACTNSM。FSTPAG
                this.bcb85511Map.updateActnsmFstpag(ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt,
                        this.crnApcode, this.crnCurcde, nsm.getNstmpg());
            } else if (sbl.getStmpln() != 1) {
                sbl.setNstmpg(sbl.getNstmpg() + 1);
                sbl.setStmpln((short) 1);
            }
            sbl.setFstpag("Y");
        }
        //更新ACBSBL
        this.bcb85511Map.updateLastActsbl(sbl.getStmbal(), sbl.getNstmny(), sbl.getNstmpg(), sbl.getStmpln(), sbl.getFstpag(),
                ACEnum.SYSIDT_AC.getStatus(), this.crnOrgidt, this.crnCusidt, this.crnApcode, this.crnCurcde);

    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public void setBcb85511Map(BCB85511Mapper bcb85511Map) {
        this.bcb85511Map = bcb85511Map;
    }
}
