package cbs.batch.ac.bcb85518sbl;

import cbs.batch.ac.bcb85518sbl.dao.BCB85518SBLMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.code.model.Actsct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-3
 * Time: 9:31:33
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB85518SBLHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85518SBLHandler.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Inject
    private BCB85518SBLMapper bcb85518sblMap;

    private List<Actsbl> sblList;
    private Actsct sct;
    private String crndatYear;
    private String outDay;      //yyyy-mm-dd

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            initBatch(); //获取Actsbl数据
            processBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initBatch() {
        SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sct = this.bcb85518sblMap.selectActsctByPK((short)8);
        this.crndatYear = sdfY.format(sct.getCrndat());
        this.outDay = sdf1.format(sct.getCrndat());
        if (!sct.getWdymak().equals(ACEnum.WDYMAK_FALSE.getStatus()) || !sct.getTdymak().equals(ACEnum.TDYMAK_FALSE.getStatus()) ||
                !sct.getQtrmak().equals(ACEnum.QTRMAK_FALSE.getStatus()) || !sct.getHyrmak().equals(ACEnum.HYRMAK_FALSE.getStatus()) ||
                !sct.getYermak().equals(ACEnum.YERMAK_FALSE.getStatus())) {
            this.sblList = this.bcb85518sblMap.selectActsbl();
        }
        
    }

    private void processBatch() throws ParseException {
        for (Actsbl sbl:this.sblList) {
            String wkCDT = sbl.getStmcdt();
            //重新组合outDay
            this.outDay = outDay.substring(0,8) + wkCDT.substring(2);
            Date dtOutDay = sdf.parse(outDay);
            if ( ((sbl.getStmcyc().equals(ACEnum.STMCYC_T.getStatus()) && sct.getTdymak().equals(ACEnum.TDYMAK_TRUE.getStatus()))
                    || (sbl.getStmcyc().equals(ACEnum.STMCYC_S.getStatus()) && sct.getQtrmak().equals(ACEnum.QTRMAK_TRUE.getStatus()))
                    || (sbl.getStmcyc().equals(ACEnum.STMCYC_Y.getStatus()) && sct.getYermak().equals(ACEnum.YERMAK_TRUE.getStatus()))
                    || ( (sbl.getStmcyc().equals(ACEnum.STMCYC_M.getStatus()) && sbl.getStmcdt().equals("0031") &&
                              sct.getMonmak().equals(ACEnum.MONMAK_TRUE.getStatus())
                          )
                         || (
                           sbl.getStmcyc().equals(ACEnum.STMCYC_M.getStatus()) && !sbl.getStmcdt().equals("0031") &&
                                   (sct.getCrndat().equals(dtOutDay) || (sct.getCrndat().before(dtOutDay) && sct.getNwkday().after(dtOutDay)))
                          )
                          )
                    || (    (sbl.getStmcyc().equals(ACEnum.STMCYC_P.getStatus()) && sbl.getStmcdt().equals("0031") && sct.getMonmak().equals(ACEnum.MONMAK_TRUE.getStatus()))
                         || (sbl.getStmcyc().equals(ACEnum.STMCYC_P.getStatus()) && !sbl.getStmcdt().equals("0031") &&
                                    !sbl.getStmcdt().equals("0000") && !sbl.getStmcdt().trim().equals("")
                                    && (sct.getCrndat().equals(dtOutDay) || (sct.getCrndat().before(dtOutDay) && sct.getNwkday().after(dtOutDay)))) )
                    || sct.getYermak().equals(ACEnum.YERMAK_TRUE.getStatus()) )
                  && !sbl.getRecsts().equals(ACEnum.RECSTS_INVALID.getStatus())
                ) {
                if (sbl.getStmfmt().equals("C") || sbl.getStmfmt().equals("S") || sbl.getStmfmt().equals("L")) {
                    this.bcb85518sblMap.updateActsblFstpag(sbl.getSysidt(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                            sbl.getCurcde(),sbl.getSecccy());
                    this.bcb85518sblMap.updateActsblNstmpg(sbl.getSysidt(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                            sbl.getCurcde(),sbl.getSecccy());
                }
                if (sbl.getStmfmt().equals("C") || sbl.getStmfmt().equals("S")) {
                    this.bcb85518sblMap.updateActnsmRecsts(ACEnum.SYSIDT_AC.getStatus(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                            sbl.getCurcde());
                }
                if (sbl.getStmfmt().equals("L")) {
                    this.bcb85518sblMap.updateActlsmRecsts(sbl.getSysidt(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                            sbl.getCurcde(),sbl.getStmdep());
                }
            }
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }
}
