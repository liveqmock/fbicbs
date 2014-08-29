package cbs.batch.ac.cbs0001;

import cbs.batch.ac.bcb85519.dao.BCB85519Mapper;
import cbs.batch.ac.cbs0001.dao.CBS0001Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * 修改对帐单分户帐出帐方式
 * User: zhangxiaobo
 * Date: 2014-8-26
 */
@Service
public class CBS0001Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(CBS0001Handler.class);
    @Inject
    private CBS0001Mapper mapper;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        //事务处理
        try {
            String sumamt = mapper.qrySumAmt();
            BigDecimal bd = new BigDecimal(sumamt);
            if(new BigDecimal("0.00").compareTo(bd) != 0) {
                throw new RuntimeException("当日传票账不平！");
            }
        } catch (Exception e) {
            throw new RuntimeException("当日传票账不平！");
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }
}
