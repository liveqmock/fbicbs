package cbs.batch.ac.bcb8522;

import cbs.batch.ac.bcb8522.dao.BCB8522Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.code.model.Actapc;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8522Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8522Handler.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Inject
    private BCB8522Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    private List<Actoac> oacList;
    private BatchResultFileHandler fileHandler;
    private Date crndat;
    private String actnumBody;
    private String nameBody = "";
    private String processBody = "";

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            initOacList();
            this.crndat = mapper.selectCrndat((short) 8);
            processBusiness();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {
        this.fileHandler = new BatchResultFileHandler("list8522.txt");
    }

    private void initOacList() {
        this.oacList = mapper.selectOac(ACEnum.RECSTS_OAC_VALID.getStatus(),
                ACEnum.OACFLG_OPEN.getStatus(), ACEnum.OACFLG_UPDATE.getStatus());
    }

    private void processBusiness() {
        try {
            logger.info("BCB8522 BEGIN");
            if (this.oacList != null && this.oacList.size() > 0) {
                this.fileHandler.setFileTitle("------------------ACOPC000-11-" + sdf.format(crndat) + "-200922------------------");
                this.fileHandler.appendNewLineAffterTitle("�����嵥 ���ڣ�" + sdf.format(crndat));
                this.fileHandler.setSpaces("       ");
                this.fileHandler.setFileHeadByArray(new String[]{"�ʺ�", "����", "���"});
                for (Actoac oac : this.oacList) {
                    processBean(oac);
                }
            } else {
                logger.info("ACTOAC List Ϊ��");
            }
            logger.info("BCB8522 END");
            this.fileHandler.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processBean(Actoac oac) {
        if ("F".equalsIgnoreCase(oac.getLegfmt()) && !ACEnum.ACTTYP_FXE.getStatus().equalsIgnoreCase(oac.getActtyp())) {
            // orgidt cusidt apcode curcde
            this.actnumBody = new StringBuffer().append(oac.getOrgidt()).append(oac.getCusidt())
                    .append(oac.getApcode()).append(oac.getCurcde()).toString();
            if (ACEnum.OACFLG_OPEN.getStatus().equalsIgnoreCase(oac.getOacflg())
                    || "B".equalsIgnoreCase(oac.getOacflg())) {
                this.processBody = "����ʧ��:  �����ڸú�����";
            } else {
                this.processBody = "�޸�ʧ��:  �����ڸú�����";
            }
            this.fileHandler.setFileBodyByArray(new String[]{this.actnumBody, this.nameBody, this.processBody});
            return;
        }
        Actapc apc = mapper.selectApcByCode(oac.getApcode());
        if (apc == null) {
            this.actnumBody = new StringBuffer().append(oac.getOrgidt()).append(oac.getCusidt())
                    .append(oac.getApcode()).append(oac.getCurcde()).toString();
            if (ACEnum.OACFLG_OPEN.getStatus().equalsIgnoreCase(oac.getOacflg())
                    || "B".equalsIgnoreCase(oac.getOacflg())) {
                this.processBody = "����ʧ��:  �����ڸú�����";
            } else {
                this.processBody = "�޸�ʧ��:  �����ڸú�����";
            }
            this.fileHandler.setFileBodyByArray(new String[]{this.actnumBody, this.nameBody, this.processBody});
            return;
        }
        Actact act = mapper.selectActByOac(systemService.getSysidtAC(), oac.getOrgidt(),
                oac.getCusidt(), oac.getApcode(), oac.getCurcde());
        String wkFindFlag = null;
        if (act != null) {
            wkFindFlag = "Y";
        } else {
            wkFindFlag = "N";
        }
        logger.info("wk-find_flag=" + wkFindFlag);
        if ("Y".equals(wkFindFlag) &&
                (ACEnum.OACFLG_OPEN.getStatus().equalsIgnoreCase(oac.getOacflg())
                        || "B".equalsIgnoreCase(oac.getOacflg()))) {
            if (ACEnum.RECSTS_INVALID.getStatus().equalsIgnoreCase(act.getRecsts())
                    || ACEnum.ACTSTS_INVALID.getStatus().equalsIgnoreCase(act.getActsts())) {
                int num = mapper.deleteActByOac(systemService.getSysidtAC(), oac.getOrgidt(),
                        oac.getCusidt(), oac.getApcode(), oac.getCurcde());
                logger.info("BCB8522ɾ��ACTACT" + num + "����¼.");
            } else {
                this.actnumBody = new StringBuffer().append(oac.getOrgidt()).append(oac.getCusidt())
                        .append(oac.getApcode()).append(oac.getCurcde()).toString();
                this.processBody = "�������ɹ�:  �û��Ѵ���";
                this.fileHandler.setFileBodyByArray(new String[]{this.actnumBody, this.nameBody, this.processBody});
                logger.info("ljf1");
                oac.setRecsts(ACEnum.RECSTS_OAC_ACCESSED.getStatus());
            }
        }
        if ("N".equals(wkFindFlag) || ACEnum.RECSTS_INVALID.getStatus().equalsIgnoreCase(act.getRecsts())
                || ACEnum.ACTSTS_INVALID.getStatus().equalsIgnoreCase(act.getActsts())
                && (ACEnum.OACFLG_OPEN.getStatus().equalsIgnoreCase(oac.getOacflg())
                || "B".equalsIgnoreCase(oac.getOacflg()))) {
            mapper.insertActByOac(systemService.getSysidtAC(), oac.getOrgidt(), oac.getCusidt(),
                    oac.getApcode(), oac.getCurcde(), oac.getActnam(), oac.getDinrat(), oac.getCinrat(),
                    oac.getDratsf(), oac.getCratsf(), oac.getIntflg(), oac.getIntcyc(), oac.getInttra(),
                    oac.getOpndat(), oac.getActtyp(), apc.getGlcode(), apc.getPlcode(),
                    oac.getDepnum(), oac.getCqeflg(), oac.getBallim(), oac.getOvelim(), oac.getOveexp(),
                    oac.getClsdat(), ACEnum.REGSTS_NORMAL.getStatus(),
                    ACEnum.FRZSTS_NORMAL.getStatus(), ACEnum.ACTSTS_VALID.getStatus(), oac.getCretlr(),
                    oac.getCredat(), ACEnum.RECSTS_VALID.getStatus());
            this.actnumBody = new StringBuffer().append(oac.getOrgidt()).append(oac.getCusidt())
                    .append(oac.getApcode()).append(oac.getCurcde()).toString();
            this.processBody = "�����ɹ�";
            this.fileHandler.setFileBodyByArray(new String[]{this.actnumBody, this.nameBody, this.processBody});
            oac.setRecsts(ACEnum.RECSTS_OAC_OPEN.getStatus());
        }

        if ("Y".equals(wkFindFlag) && ACEnum.OACFLG_UPDATE.getStatus().equalsIgnoreCase(oac.getOacflg())) {
            mapper.updateActByOac(oac.getActnam(), oac.getCqeflg(), oac.getBallim(), oac.getOvelim(),
                    sdf.format(oac.getOveexp()), oac.getDinrat(), oac.getCinrat(), oac.getIntflg(), oac.getInttra(),
                    oac.getIntcyc(), oac.getDratsf(), oac.getCratsf(), this.crndat,
                    oac.getDepnum(), systemService.getSysidtAC(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
            Actsbl sbl = mapper.selectSblByOac(systemService.getSysidtAC(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
            if (sbl != null) {
                mapper.updateSblByOac(oac.getStmsht(), oac.getStmdep(), oac.getStmadd(),
                        oac.getStmzip(), oac.getStmcyc(), oac.getStmcdt(), oac.getStmfmt(), oac.getActnam(),
                        systemService.getSysidtAC(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
            }
            Actlbl lbl = mapper.selectLblByOac(systemService.getSysidtAC(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
            if (lbl != null) {
                mapper.updateLblByOac(oac.getLegsht(), oac.getLegdep(), oac.getLegadd(),
                        oac.getLegzip(), oac.getLegcyc(), oac.getLegcdt(), oac.getLegfmt(), oac.getActnam(),
                        systemService.getSysidtAC(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
            }
            this.actnumBody = new StringBuffer().append(oac.getOrgidt()).append(oac.getCusidt())
                    .append(oac.getApcode()).append(oac.getCurcde()).toString();
            this.processBody = "�޸ĳɹ�";
            this.fileHandler.setFileBodyByArray(new String[]{this.actnumBody, this.nameBody, this.processBody});
            logger.info("ljf2");
            oac.setRecsts(ACEnum.RECSTS_OAC_UPDATE.getStatus());
        }
        if ("N".equals(wkFindFlag) && ACEnum.OACFLG_UPDATE.getStatus().equalsIgnoreCase(oac.getOacflg())) {
            this.actnumBody = new StringBuffer().append(oac.getOrgidt()).append(oac.getCusidt())
                    .append(oac.getApcode()).append(oac.getCurcde()).toString();
            this.processBody = "�޸�ʧ�ܣ����ʻ�������";
            this.fileHandler.setFileBodyByArray(new String[]{this.actnumBody, this.nameBody, this.processBody});
            logger.info("ljf3");
            oac.setRecsts(ACEnum.RECSTS_OAC_ACCESSED.getStatus());
        }
        mapper.updateOacSts(oac.getRecsts(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
    }

    public BCB8522Mapper getMapper() {
        return mapper;
    }

    public void setMapper(BCB8522Mapper mapper) {
        this.mapper = mapper;
    }
}
