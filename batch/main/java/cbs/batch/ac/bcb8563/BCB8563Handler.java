package cbs.batch.ac.bcb8563;

import cbs.batch.ac.bcb8563.dao.BCB8563Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.IbatisFactory;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/*
 * 写历史记录（ACTVTH->ACTVHH,ACTNSM->ACTSTM,ACTLSM->ACTSTM,ACTLGC->ACTLGH,ACTLGI->ACTLGH,ACTLGF->ACTLGH)
  * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8563Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8563Handler.class);
    @Inject
    private BCB8563Mapper mapper;

    protected void initBatch(final BatchParameterData batchParam) {

    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        //事务处理
        try {
            mapper.insertVhh();
            mapper.deleteVth();

            mapper.insertStmFromNsm();
            mapper.insertStmFromLsm();

            mapper.deleteNsm();
            mapper.deleteLsm();

            mapper.insertLghFromLgc();
            mapper.insertLghFromLgi();
            mapper.insertLghFromLgf();

            mapper.deleteLgc();
            mapper.deleteLgi();
            mapper.deleteLgf();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}