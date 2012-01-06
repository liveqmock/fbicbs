package cbs.batch.ac.bcb8541;

import cbs.batch.ac.bcb8541.dao.BCB8541Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-10
 * Time: 14:10:35
 * 确定不计息余额
 */
@Service
public class BCB8541Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8541Handler.class);
    @Inject
    private BCB8541Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    private List<BvaBean> bvaList;
    private String crndat;


    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8541 确定不计息余额 BEGIN");
        try {
            initProps();
            mapper.updateAct(ACEnum.INTFLG_AIFCON.getStatus(), ACEnum.ACTSTS_VALID.getStatus(),
                    ACEnum.RECSTS_VALID.getStatus());
            bvaList = mapper.selectBvasUp(crndat, ACEnum.BVAFLG_LATE.getStatus(),
                    ACEnum.RECSTS_VALID.getStatus());
            if (bvaList != null && !bvaList.isEmpty()) {
                for (BvaBean bva : bvaList) {
                    mapper.updateActCifbal(bva.getSumBvaamt(), systemService.getSysidtAC(), bva.getOrgidt(), bva.getCusidt(),
                            bva.getApcode(), bva.getCurcde(), ACEnum.ACTSTS_VALID.getStatus(), ACEnum.RECSTS_VALID.getStatus());
                }
            }
            bvaList = mapper.selectBvasDown(crndat, ACEnum.BVAFLG_LATE.getStatus(),
                    ACEnum.RECSTS_VALID.getStatus());
            if (bvaList != null && !bvaList.isEmpty()) {
                for (BvaBean bva : bvaList) {
                    mapper.updateActDifbal(bva.getSumBvaamt(), systemService.getSysidtAC(), bva.getOrgidt(), bva.getCusidt(),
                            bva.getApcode(), bva.getCurcde(), ACEnum.ACTSTS_VALID.getStatus(), ACEnum.RECSTS_VALID.getStatus());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8541 确定不计息余额 END");
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    private void initProps() {
        this.crndat = systemService.getBizDate10();
    }
}
