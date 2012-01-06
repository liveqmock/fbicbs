package cbs.batch.ac.bcb8554;

import cbs.batch.ac.bcb8554.dao.BCB8554Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-3
 * Time: 15:59:48
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB8554Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8554Handler.class);

    @Inject
    private BCB8554Mapper bcb8554Map;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            processBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processBatch() {
        this.bcb8554Map.deleteActfgl();
        this.bcb8554Map.insertActfgl();
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

}
