package cbs.batch.ac.bcb8574;

import cbs.batch.ac.bcb8574.dao.BCB8574Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.IbatisFactory;
import cbs.common.enums.ACEnum;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/*
 * 清核算码总帐发生额、发生数ACTCAL
 *  User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8574Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8574Handler.class);
    @Inject
    private BCB8574Mapper mapper;
    @Inject
    private ActsctMapper sctMapper;
    @Inject
    private ActsctExample sctExample;
    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
    private Actsct actsct;

    protected void initBatch(final BatchParameterData batchParam) {

    }

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

        //事务处理  cbs系统中不存在ACTCAL，不做处理
        try {
            String date = sdfdate.format(actsct.getNwkday()).substring(0, 8) + "01";
            if (ACEnum.TDYMAK_TRUE.equals(actsct.getTdymak())) {
                mapper.deleteTjfByTjcode("T01");
            }
            if (ACEnum.MONMAK_TRUE.equals(actsct.getMonmak())) {
                //mapper.updateCal(date,ACEnum.RECTYP_M.getStatus(),ACEnum.RECSTS_VALID.getStatus());
                mapper.deleteTjf();
            }
            /* if(ACEnum.YERMAK_TRUE.equals(actsct.getYermak())){
               mapper.updateCal(date,ACEnum.RECTYP_Y.getStatus(),ACEnum.RECSTS_VALID.getStatus());
           }
             mapper.updateCal(sdfdate.format(actsct.getNwkday()),ACEnum.RECTYP_D.getStatus(),ACEnum.RECSTS_VALID.getStatus());*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Actsct initSct() {

        sctExample.createCriteria().andSctnumEqualTo((short) 8).andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
        List<Actsct> sctList = sctMapper.selectByExample(sctExample);
        return (sctList != null && sctList.size() > 0) ? sctList.get(0) : null;
    }

}