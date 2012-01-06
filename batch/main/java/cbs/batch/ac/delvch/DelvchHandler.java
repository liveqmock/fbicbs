package cbs.batch.ac.delvch;

import cbs.batch.ac.delvch.dao.DelvchMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.IbatisFactory;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * DELETE ACTVTH,ACTTVC,ACTCTV,TMPVCH
 * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service("DelvchHandler")
public class DelvchHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(DelvchHandler.class);
    @Inject
    private DelvchMapper mapper;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        //������
        try {
            mapper.deleteActvth();
            mapper.deleteActtvc();
            //cbsϵͳ������ACTCTV,TMPVCH����������
            //mapper.deleteActctv();
            // mapper.deleteTmpvch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }
}