package cbs.batch.ac.act2obf;

import cbs.batch.ac.act2obf.dao.Act2obfMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.IbatisFactory;
import cbs.common.enums.ACEnum;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 生成OBF(DELETE ACTOBF,ACTACT->ACTOBF)
 * * User: zhangxiaobo
 * Date: 2010-2-22
 */
//@Service("Act2obfHandler")
@Service
public class Act2obfHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(Act2obfHandler.class);
    @Inject
    private Act2obfMapper mapper;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        //事务处理
        try {
            mapper.deleteActobf();
            mapper.insertActobf(ACEnum.RECSTS_VALID.getStatus(), ACEnum.RECSTS_VALID.getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public Act2obfMapper getMapper() {
        return mapper;
    }

    public void setMapper(Act2obfMapper mapper) {
        this.mapper = mapper;
    }

}