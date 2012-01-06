package cbs.batch.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;

import javax.inject.Inject;

/**
 * �Թ�ҵ��������.
 * User: zhanrui
 * Date: 2010-12-12
 * Time: 9:35:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractACBatchJobLogic extends AbstractBatchJobLogic {

    //������ֹ
    protected static final int STATUS_OK = 0;
    //�쳣��ֹ
    protected static final int STATUS_FAILED = 1;
    //��ϵͳ��ʶ
    protected static final String SUBSYSTEM_ID = "AC";
    //ϵͳ����״̬���ر�
    protected static final boolean BLOCK_ON = true;
    //ϵͳ����״̬�����
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

        //�������ʼ��
        initBatch(batchParam);

        //����ҵ�������
        //HashMap<String, String> bizParam = setDataToMap();

        //logger.debug("Batch ParaMap MODULE_ID = " + batchParam.get(MODULE_ID));
        //logger.debug("Batch ParaMap SUBSYSTEM_ID = " + batchParam.get(SUBSYSTEM_ID));

        //�����ʼ״̬
        startMessageOutput(batchParam);

        //ȷ��ϵͳ����״̬���������쳣����
        systemBlockStatusLogic.checkBlockStatus(batchParam.getModuleId());

        //����ϵͳ����״̬��������
        systemBlockStatusLogic.updateNormalBlockBySubsystemId(SUBSYSTEM_ID, BLOCK_ON, null, batchParam.getModuleId());

        //ҵ���߼�����
        processBusiness(batchParam);

        //�����ϵͳ����״̬��������
        systemBlockStatusLogic.updateNormalBlockBySubsystemId(SUBSYSTEM_ID, BLOCK_OFF, null, batchParam.getModuleId());

        //������״̬
        endMessageOutput(batchParam);

        //�������������״̬
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
