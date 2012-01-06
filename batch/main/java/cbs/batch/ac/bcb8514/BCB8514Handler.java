package cbs.batch.ac.bcb8514;

import cbs.batch.ac.bcb8514.dao.BCB8514Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.IbatisFactory;
import cbs.common.enums.ACEnum;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/*
 *   清总帐发生额、发生数，总账过历史ACTCGL(日、月、季、年分别处理）
 *  * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8514Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8514Handler.class);
    @Inject
    private BCB8514Mapper mapper;
    @Inject
    private ActsctMapper sctMapper;
    @Inject
    private ActsctExample sctExample;
    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
    private Actsct actsct;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            this.actsct = initSct();
            if (actsct == null) {
                logger.info("ACTSCT查询结果为空！");
                return;
            }
        } catch (Exception e) {
            logger.error("初始化错误！", e);
            return;
        }

        //事务处理
        try {
            String date = sdfdate.format(actsct.getNwkday()).substring(0, 8) + "01";
            if (ACEnum.MONMAK_TRUE.getStatus().equals(actsct.getMonmak())) {
                mapper.updateCgl(date, ACEnum.RECTYP_M.getStatus(), ACEnum.RECSTS_VALID.getStatus());
            }
            if (ACEnum.QTRMAK_TRUE.getStatus().equals(actsct.getQtrmak())) {
                mapper.updateCgl(date, ACEnum.RECTYP_S.getStatus(), ACEnum.RECSTS_VALID.getStatus());
            }
            if (ACEnum.YERMAK_TRUE.getStatus().equals(actsct.getYermak())) {
                mapper.updateCgl(date, ACEnum.RECTYP_Y.getStatus(), ACEnum.RECSTS_VALID.getStatus());
            }
            mapper.updateCgl(sdfdate.format(actsct.getNwkday()), ACEnum.RECTYP_D.getStatus(), ACEnum.RECSTS_VALID.getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Actsct initSct() {
        sctExample.createCriteria().andSctnumEqualTo((short) 8).andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
        List<Actsct> sctList = sctMapper.selectByExample(sctExample);
        return (sctList != null && sctList.size() > 0) ? sctList.get(0) : null;
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public BCB8514Mapper getMapper() {
        return mapper;
    }

    public void setMapper(BCB8514Mapper mapper) {
        this.mapper = mapper;
    }

    public ActsctMapper getSctMapper() {
        return sctMapper;
    }

    public void setSctMapper(ActsctMapper sctMapper) {
        this.sctMapper = sctMapper;
    }

    public ActsctExample getSctExample() {
        return sctExample;
    }

    public void setSctExample(ActsctExample sctExample) {
        this.sctExample = sctExample;
    }
}