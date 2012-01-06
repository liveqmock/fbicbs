package cbs.batch.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统阻塞状态管理.
 * User: zhanrui
 * Date: 2010-12-30
 * Time: 11:29:16
 * To change this template use File | Settings | File Templates.
 */
@Service 
public class SystemBlockStatusLogic {
    //系统阻塞状态：解除中
    private static final int BLOCK_STATUS_OFF = 0;
    //系统阻塞状态：阻塞中
    private static final int BLOCK_STATUS_ON = 1;

    private static final int TRANSACTION_TIMEOUT = 30;

    private static final Logger logger = LoggerFactory.getLogger(SystemBlockStatusLogic.class);

    public void checkBlockOff(String ModuleId) {

    }

    public void checkBlockOn(String ModuleId) {

    }

    public void checkBlockStatus(String ModuleId) {
          logger.info("检查系统阻塞状态...");
    }

    /**
     * 阻塞状态（异常）更新处理
     *
     * @param subsystemId
     * @param updateValue    更新值（true：阻塞中，false：接触中）
     * @param updatorId
     * @param updateModuleId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = TRANSACTION_TIMEOUT)
    public void updateAbnormalBlockBySubsystemId(String subsystemId,
                                                 boolean updateValue,
                                                 String updatorId,
                                                 String updateModuleId) {
    }
    /**
     * 阻塞状态（异常）更新处理
     *
     * @param moduleId
     * @param updateValue    更新值（true：阻塞中，false：接触中）
     * @param updatorId
     * @param updateModuleId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = TRANSACTION_TIMEOUT)
    public void updateAbnormalBlockByModuleId(String moduleId,
                                                 boolean updateValue,
                                                 String updatorId,
                                                 String updateModuleId) {
    }

    /**
     * 阻塞状态（正常）更新处理
     *
     * @param subsystemId
     * @param updateValue    更新值（true：阻塞中，false：接触中）
     * @param updatorId
     * @param updateModuleId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = TRANSACTION_TIMEOUT)
    public void updateNormalBlockBySubsystemId(String subsystemId,
                                                 boolean updateValue,
                                                 String updatorId,
                                                 String updateModuleId) {
    }
    /**
     * 阻塞状态（正常）更新处理
     *
     * @param moduleId
     * @param updateValue    更新值（true：阻塞中，false：接触中）
     * @param updatorId
     * @param updateModuleId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = TRANSACTION_TIMEOUT)
    public void updateNormalBlockByModuleId(String moduleId,
                                                 boolean updateValue,
                                                 String updatorId,
                                                 String updateModuleId) {
    }



}
