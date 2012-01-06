package cbs.batch.ac.bcb8513;

import cbs.batch.ac.bcb8513.dao.BCB8513Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;

import java.util.List;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * ��Ҫ���������в���
 * ��ACTACT��ACTFXE��ACTPLE���ա��¡��귢�������ͽ��    cbsϵͳ������ACTFXE����������
 * * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8513Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8513Handler.class);
    @Inject
    private BCB8513Mapper mapper;
    @Inject
    private ActsctMapper sctMapper;
    @Inject
    private ActsctExample sctExample;

    private Actsct actsct;
    // ������
    private String rec_typ;

    public BCB8513Mapper getMapper() {
        return mapper;
    }

    public void setMapper(BCB8513Mapper mapper) {
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

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            actsct = initSct();
            if (actsct == null) return;
        } catch (Exception e) {
            logger.error("��ʼ������", e);
            return;
        }
        //������
        try {
            // ���շ������� ����
            mapper.updateActDdr();
            // mapper.updateFxeDdr();
            mapper.updatePleDdr();
            String[] args = parameterData.getCommandLine();
            if (args == null || args.length < 1) {
                logger.error("�����в�������Ϊ�գ�");
                return;
            }
            this.rec_typ = args[0];
            if ("Y".equalsIgnoreCase(this.rec_typ)) {
                // �������ݿ��±�Ǻ������ֶ��ж��Ƿ��巢������
                if (ACEnum.MONMAK_TRUE.getStatus().equals(actsct.getMonmak())) {
                    mapper.updateActMdr();
                    //mapper.updateFxeMdr();
                    mapper.updatePleMdr();
                }
                if (ACEnum.YERMAK_TRUE.getStatus().equals(actsct.getYermak())) {
                    mapper.updateActYdr();
                    //mapper.updateFxeYdr();
                    mapper.updatePleYdr();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    private Actsct initSct() {
        sctExample.createCriteria().andSctnumEqualTo((short) 8).andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
        List<Actsct> sctList = sctMapper.selectByExample(sctExample);
        return (sctList != null && sctList.size() > 0) ? sctList.get(0) : null;
    }
}