package cbs.batch.ac.bcb85519;

import cbs.batch.ac.bcb85519.dao.BCB85519Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.IbatisFactory;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * 修改对帐单分户帐出帐方式
 * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB85519Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85519Handler.class);
    @Inject
    private BCB85519Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    
    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        //事务处理
        try {
            mapper.updateSblNextPageMsg(systemService.getSysidtAC(), "U");
            mapper.updateSblMsg(systemService.getSysidtAC(), "U");
            mapper.updateLblNextPageMsg(systemService.getSysidtAC(), "U");
            mapper.updateLblMsg(systemService.getSysidtAC(), "U");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }
}
