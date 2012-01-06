package cbs.batch.ac.bcb8558;

import cbs.batch.ac.bcb8558.dao.BCB8558Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.IbatisFactory;
import cbs.common.enums.ACEnum;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/*
 *    计算平均余额（ACTACT，ACTCGL）
 * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8558Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8558Handler.class);
    @Inject
    private BCB8558Mapper mapper;
    private ActsctParaBean apb;
    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");

    protected void initBatch(final BatchParameterData batchParam) {

    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            this.apb = initSct();
            if (apb == null) {
                logger.info("ACTSCT查询结果为空！");
                return;
            }

        } catch (Exception e) {
            logger.error("初始化错误！", e);
            return;
        }

        //事务处理
        try {
            String date = sdfdate.format(apb.getCrndat());
            String crnYear = date.substring(0, 4);
            String crnMonth = date.substring(5, 7);
            String crnDay = date.substring(8, 10);
            date = sdfdate.format(apb.getLwkday());
            String lwkYear = date.substring(0, 4);
            String lwkMonth = date.substring(5, 7);
            String lwkDay = date.substring(8, 10);
            // 计算年平均余额
            if (!crnYear.equalsIgnoreCase(lwkYear)) {
                if ("01".equalsIgnoreCase(crnDay)) {
                    mapper.updateActYavUseBok();
                    mapper.updateCglDavCav(apb.getWkByydat(), ACEnum.RECTYP_Y.getStatus(),
                            ACEnum.RECSTS_VALID.getStatus());
                } else {
                    mapper.updateActYavCompute(apb.getByydds());
                    mapper.updateCglCompute(apb.getByydds(), apb.getWkByydat(), ACEnum.RECTYP_Y.getStatus(),
                            ACEnum.RECSTS_VALID.getStatus());
                }
            } else {
                mapper.updateActYavEqu(apb.getByydds());
                System.out.println(
                        apb.getByydds() + "    " + apb.getWkByydat() + "    " + ACEnum.RECTYP_Y.getStatus() +
                                "    " + ACEnum.RECSTS_VALID.getStatus());
                mapper.updateCglYerEqu(apb.getByydds(), apb.getWkByydat(), ACEnum.RECTYP_Y.getStatus(),
                        ACEnum.RECSTS_VALID.getStatus());
            }
            // 计算月平均余额
            if (!crnMonth.equalsIgnoreCase(lwkMonth)) {
                if ("01".equalsIgnoreCase(crnDay)) {
                    mapper.updateActMavUseBok();
                    mapper.updateCglDavCav(apb.getWkBmmdat(), ACEnum.RECTYP_M.getStatus(),
                            ACEnum.RECSTS_VALID.getStatus());
                } else {
                    mapper.updateActMavCompute(apb.getBmmdds());
                    mapper.updateCglCompute(apb.getByydds(), apb.getWkBmmdat(), ACEnum.RECTYP_M.getStatus(),
                            ACEnum.RECSTS_VALID.getStatus());
                }
            } else {
                mapper.updateActMavEqu(apb.getBmmdds());
                mapper.updateCglMonEqu(apb.getByydds(), apb.getWkBmmdat(), ACEnum.RECTYP_M.getStatus(),
                        ACEnum.RECSTS_VALID.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ActsctParaBean initSct() {

        return mapper.selectActsct((short) 8, ACEnum.RECSTS_VALID.getStatus());
    }

}