package cbs.batch.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;

import javax.inject.Inject;

/**
 * 对公业务批处理.
 * User: zhanrui
 * Date: 2010-12-12
 * Time: 9:35:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractACBatchJobLogic extends AbstractBatchJobLogic {

    //正常终止
    protected static final int STATUS_OK = 0;
    //异常终止
    protected static final int STATUS_FAILED = 1;
    //子系统标识
    protected static final String SUBSYSTEM_ID = "AC";
    //系统阻塞状态：关闭
    protected static final boolean BLOCK_ON = true;
    //系统阻塞状态：解除
    protected static final boolean BLOCK_OFF = false;

    private static final Logger logger = LoggerFactory.getLogger(AbstractACBatchJobLogic.class);

    private SystemBlockStatusLogic systemBlockStatusLogic;

    @Autowired
    @Inject
    public void setSystemBlockStatusLogic(SystemBlockStatusLogic systemBlockStatusLogic) {
        this.systemBlockStatusLogic = systemBlockStatusLogic;
    }

    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
    @org.mybatis.guice.transactional.Transactional
    public void run(final BatchParameterData batchParam) {
        logger.info("Batch Job ID = " + batchParam.getJobId());

        //批处理初始化
        initBatch(batchParam);

        //设置业务处理参数
        //HashMap<String, String> bizParam = setDataToMap();

        //logger.debug("Batch ParaMap MODULE_ID = " + batchParam.get(MODULE_ID));
        //logger.debug("Batch ParaMap SUBSYSTEM_ID = " + batchParam.get(SUBSYSTEM_ID));

        //输出开始状态
        startMessageOutput(batchParam);

        //确认系统阻塞状态（正常、异常）。
        systemBlockStatusLogic.checkBlockStatus(batchParam.getModuleId());

        //置子系统阻塞状态（正常）
        systemBlockStatusLogic.updateNormalBlockBySubsystemId(SUBSYSTEM_ID, BLOCK_ON, null, batchParam.getModuleId());

        //业务逻辑处理
        processBusiness(batchParam);

        //解除子系统阻塞状态（正常）
        systemBlockStatusLogic.updateNormalBlockBySubsystemId(SUBSYSTEM_ID, BLOCK_OFF, null, batchParam.getModuleId());

        //输出结果状态
        endMessageOutput(batchParam);

        //置正常处理结束状态
        batchParam.setExitStatus(STATUS_OK);

    }

    protected abstract void initBatch(final BatchParameterData batchParam);

    protected abstract void processBusiness(final BatchParameterData batchParam);

    protected final void startMessageOutput(final BatchParameterData batchParam) {

    }

    protected final void endMessageOutput(final BatchParameterData batchParam) {

    }

    //protected abstract HashMap<String, String> setDataToMap();
}
