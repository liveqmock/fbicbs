package cbs.batch.ac.bcb85518lbl;

import cbs.batch.ac.bcb85518lbl.dao.BCB85518LBLMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.billinfo.model.Actlbl;
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
 * Time: 14:12:19
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB85518LBLHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85518LBLHandler.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Inject
    private BCB85518LBLMapper bcb85518lblMap;

    private List<Actlbl> lblList;
    private Actsct sct;
    private String crndatYear;
    private String outDay;      //yyyy-mm-dd

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            initBatch(); //获取Actlbl数据
            processBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initBatch() {
        SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sct = this.bcb85518lblMap.selectActsctByPK((short)8);
        this.crndatYear = sdfY.format(sct.getCrndat());
        this.outDay = sdf1.format(sct.getCrndat());
        if (!sct.getWdymak().equals(ACEnum.WDYMAK_FALSE.getStatus()) || !sct.getTdymak().equals(ACEnum.TDYMAK_FALSE.getStatus()) ||
                        !sct.getQtrmak().equals(ACEnum.QTRMAK_FALSE.getStatus()) || !sct.getHyrmak().equals(ACEnum.HYRMAK_FALSE.getStatus()) ||
                        !sct.getYermak().equals(ACEnum.YERMAK_FALSE.getStatus())) {
            this.lblList = this.bcb85518lblMap.selectActlbl();
        }
    }

    private void processBatch() throws ParseException {
        for (Actlbl lbl:this.lblList) {
            String wkCDT = lbl.getLegcdt();
            //重新组合outDay

            this.outDay = outDay.substring(0,8) + wkCDT.substring(2);
            Date dtOutDay = sdf.parse(outDay);
            if ( ((lbl.getLegcyc().equals(ACEnum.STMCYC_T.getStatus()) && sct.getTdymak().equals(ACEnum.TDYMAK_TRUE.getStatus()))
                    || (lbl.getLegcyc().equals(ACEnum.STMCYC_S.getStatus()) && sct.getQtrmak().equals(ACEnum.QTRMAK_TRUE.getStatus()))
                    || (lbl.getLegcyc().equals(ACEnum.STMCYC_Y.getStatus()) && sct.getYermak().equals(ACEnum.YERMAK_TRUE.getStatus()))
                    || ( (lbl.getLegcyc().equals(ACEnum.STMCYC_M.getStatus()) && lbl.getLegcdt().equals("0031") &&
                              sct.getMonmak().equals(ACEnum.MONMAK_TRUE.getStatus())
                          )
                         || (
                           lbl.getLegcyc().equals(ACEnum.STMCYC_M.getStatus()) && !lbl.getLegcdt().equals("0031") &&
                                   (sct.getCrndat().equals(dtOutDay) || (sct.getCrndat().before(dtOutDay) && sct.getNwkday().after(dtOutDay)))
                          )
                          )
                    || (    (lbl.getLegcyc().equals(ACEnum.STMCYC_P.getStatus()) && lbl.getLegcdt().equals("0031") && sct.getMonmak().equals(ACEnum.MONMAK_TRUE.getStatus()))
                         || (lbl.getLegcyc().equals(ACEnum.STMCYC_P.getStatus()) && !lbl.getLegcdt().equals("0031") &&
                                    !lbl.getLegcdt().equals("0000") && !lbl.getLegcdt().trim().equals("")
                                    && (sct.getCrndat().equals(dtOutDay) || (sct.getCrndat().before(dtOutDay) && sct.getNwkday().after(dtOutDay)))) )
                    || sct.getYermak().equals(ACEnum.YERMAK_TRUE.getStatus()) )
                  && !lbl.getRecsts().equals(ACEnum.RECSTS_INVALID.getStatus())
                ) {
                if (lbl.getLegfmt().equals("C") || lbl.getLegfmt().equals("I") || lbl.getLegfmt().equals("F")) {
                    this.bcb85518lblMap.updateActlblFstpag(lbl.getSysidt(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                            lbl.getCurcde(),lbl.getSecccy());
                    this.bcb85518lblMap.updateActlblNlegpg(lbl.getSysidt(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                            lbl.getCurcde(),lbl.getSecccy());
                }
                if (lbl.getLegfmt().equals("C") || lbl.getLegfmt().equals("I")) {
                    this.bcb85518lblMap.updateActlgcRecsts(lbl.getSysidt(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                            lbl.getCurcde());
                }
                if (lbl.getLegfmt().equals("F")) {
                    this.bcb85518lblMap.updateActlgfRecsts(lbl.getSysidt(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                            lbl.getCurcde(),lbl.getSecccy());
                }
            }

        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public void setBcb85518lblMap(BCB85518LBLMapper bcb85518lblMap) {
        this.bcb85518lblMap = bcb85518lblMap;
    }
}
