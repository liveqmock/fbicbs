package cbs.batch.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * ϵͳ����״̬����.
 * User: zhanrui
 * Date: 2010-12-30
 * Time: 11:29:16
 * To change this template use File | Settings | File Templates.
 */
@Service 
public class SystemBlockStatusLogic {
    //ϵͳ����״̬�������
    private static final int BLOCK_STATUS_OFF = 0;
    //ϵͳ����״̬��������
    private static final int BLOCK_STATUS_ON = 1;

    private static final int TRANSACTION_TIMEOUT = 30;

    private static final Logger logger = LoggerFactory.getLogger(SystemBlockStatusLogic.class);

    public void checkBlockOff(String ModuleId) {

    }

    public void checkBlockOn(String ModuleId) {

    }

    public void checkBlockStatus(String ModuleId) {
          logger.info("���ϵͳ����״̬...");
    }

    /**
     * ����״̬���쳣�����´���
     *
     * @param subsystemId
     * @param updateValue    ����ֵ��true�������У�false���Ӵ��У�
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
     * ����״̬���쳣�����´���
     *
     * @param moduleId
     * @param updateValue    ����ֵ��true�������У�false���Ӵ��У�
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
     * ����״̬�����������´���
     *
     * @param subsystemId
     * @param updateValue    ����ֵ��true�������У�false���Ӵ��У�
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
     * ����״̬�����������´���
     *
     * @param moduleId
     * @param updateValue    ����ֵ��true�������У�false���Ӵ��У�
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
