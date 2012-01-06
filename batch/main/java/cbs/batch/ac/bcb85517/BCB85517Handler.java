package cbs.batch.ac.bcb85517;

import cbs.batch.ac.bcb85517.dao.BCB85517Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.utils.DataFormater;
import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.code.model.Actccy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-2
 * Time: 12:25:08
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB85517Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85517Handler.class);
    @Inject
    private BCB85517Mapper bcb85517Map;

    private List<Actsbl> sblList;
    private List<Actlbl> lblList;
    private int wkTotalSblCnt;
    private int wkErrSblCnt;
    private int wkTotalLblCnt;
    private int wkErrLblCnt;
    private String wkNotFindAct = "0";
    private long act_bokbal = 0;
    private String ccy_Curcde = "";
    private short ccy_decpos = 0;
    private long wkConvAMT1 = 0;
    private String sbl_actnum = "";
    private String lbl_actnum = "";


    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            initBatch(); //获取Actsbl Actlbl数据
            processBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initBatch() {
        this.sblList = this.bcb85517Map.selectActsbl();
        this.lblList = this.bcb85517Map.selectActlbl();
    }

    private void processBatch() {
        for(Actsbl sbl:this.sblList) {
            this.wkTotalSblCnt += 1;
            this.sbl_actnum = sbl.getSysidt() + sbl.getOrgidt() + sbl.getCusidt() + sbl.getApcode() + sbl.getCurcde();
            //5500-SELECT-ACT
            Actact act = this.bcb85517Map.selectActactByPK(sbl.getSysidt(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                    sbl.getCurcde());
            if (act == null) {
                this.act_bokbal = 0;
                logger.info(" ACTACT  NOT  FOUND BUT SBL HAVING IT " + sbl.getOrgidt() + " " + sbl.getCusidt() + " " +
                        sbl.getApcode() + " " + sbl.getCurcde());
                this.wkNotFindAct = "1";
            } else {
                this.act_bokbal = act.getBokbal();
            }
            if (sbl.getStmbal() != this.act_bokbal) {
                this.ccy_Curcde = sbl.getCurcde();
                //9807
                selectActccy();
                this.wkConvAMT1 = sbl.getStmbal();
                String wkStmbal = DataFormater.formatNum(this.ccy_decpos,this.wkConvAMT1);
                String wkActbokbal = DataFormater.formatNum(this.ccy_decpos,this.act_bokbal);
                logger.info("ACTNUM: " + sbl_actnum + " STMBAL:" + wkStmbal + "  ACTBAL:" + wkActbokbal);
                this.wkErrSblCnt += 1;
            }
            
        }
        //ACTLBL
        for (Actlbl lbl:this.lblList) {
            this.lbl_actnum = lbl.getSysidt() + lbl.getOrgidt() + lbl.getCusidt() + lbl.getApcode() + lbl.getCurcde();
            if (lbl.getApcode().equals("9991") || lbl.getApcode().equals("9990")) {
                continue;
            }
            this.wkTotalLblCnt += 1;
            //5501
            Actact act = this.bcb85517Map.selectActactByPK(lbl.getSysidt(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                    lbl.getCurcde());
            if (act == null) {
                this.act_bokbal = 0;
                logger.info(" ACTACT  NOT  FOUND BUT LBL HAVING IT " + lbl.getOrgidt() + " " + lbl.getCusidt() + " " +
                        lbl.getApcode() + " " + lbl.getCurcde());
                this.wkNotFindAct = "1";
            } else {
                this.act_bokbal = act.getBokbal();
            }
            if (lbl.getLegbal() != this.act_bokbal) {
                this.ccy_Curcde = lbl.getCurcde();
                //9807
                selectActccy();
                this.wkConvAMT1 = lbl.getLegbal();
                String wklegbal = DataFormater.formatNum(this.ccy_decpos,this.wkConvAMT1);
                String wkActbokbal = DataFormater.formatNum(this.ccy_decpos, this.act_bokbal);
                logger.info("ACTNUM: " + lbl_actnum + " LEGBAL:" + wklegbal + "  ACTBAL:" + wkActbokbal);
                this.wkErrLblCnt += 1;
            }
        }
        //统计
        logger.info("TOTAL SBL-COUNT=" + this.wkTotalSblCnt);
        logger.info("DIFF  SBL-COUNT=" + this.wkErrSblCnt);
        logger.info("TOTAL LBL-COUNT=" + this.wkTotalLblCnt);
        logger.info("DIFF  LBL-COUNT=" + this.wkErrLblCnt);
        if (this.wkErrSblCnt != 0 || this.wkErrLblCnt != 0 || this.wkNotFindAct != "0") {
            logger.info("ERR !!!!!!!!! PLEASE CHECK IT");
        }
    }

    //9807
    private void selectActccy() {
        Actccy ccy = this.bcb85517Map.selectActccyByPK(this.ccy_Curcde);
        if (ccy == null) {
            logger.info("CURCDE:" + this.ccy_Curcde + " NOT FOUND!");
        } else {
            this.ccy_decpos = ccy.getDecpos();
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public void setBcb85517Map(BCB85517Mapper bcb85517Map) {
        this.bcb85517Map = bcb85517Map;
    }
}
