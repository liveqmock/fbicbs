package cbs.batch.ac.bcb8564;

import cbs.batch.ac.bcb8563.dao.BCB8563Mapper;
import cbs.batch.ac.bcb8564.dao.BCB8564Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/*  ������������
  * User: zhangxiaobo
 * Date: 2010-4-2
 */
@Service
public class BCB8564Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8564Handler.class);
    @Inject
    private BCB8564Mapper mapper;

    protected void initBatch(final BatchParameterData batchParam) {

    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            SctDat sct = mapper.qryDatBySctnum((short) 8);
            if (sct == null) {
                logger.error("SCT NOT FOUND");
                return;
            } else {
                int delMnum = mapper.delGlf(ACEnum.RECTYP_M.getStatus(), sct.getmDate());
                int delSnum = mapper.delGlf(ACEnum.RECTYP_S.getStatus(), sct.getsDate());
                int delYnum = mapper.delGlf(ACEnum.RECTYP_Y.getStatus(), sct.getyDate());
                logger.info("��ɾ��Glf��¼��: "+(delMnum+delSnum+delYnum));
                int insetNum = mapper.insertCglToGlf();
                logger.info("Insert Glf��¼��:  "+insetNum);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}