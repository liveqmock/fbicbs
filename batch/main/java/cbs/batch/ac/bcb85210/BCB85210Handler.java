package cbs.batch.ac.bcb85210;

import cbs.batch.ac.bcb85210.dao.BCB85210Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;

import javax.inject.Inject;

import cbs.batch.common.BatchResultFileHandler;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actoac;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 批量关户
 * User: zhangxiaobo
 */
@Service
public class BCB85210Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85210Handler.class);
    @Inject
    private BCB85210Mapper mapper;
    private BatchResultFileHandler fileHandler;
    @Inject
    private BatchSystemService systemService;
    private List<Actoac> oacList;
    private Date crndat;

    protected void initBatch(final BatchParameterData batchParam) {
        this.fileHandler = new BatchResultFileHandler("list85210.txt");
        this.crndat = systemService.getCrnDat();
    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            this.fileHandler.setFileTitle("            BCB85210 销户清单(" + systemService.getBizDate10() + ")                   ");
            this.oacList = mapper.qryOac(ACEnum.RECSTS_VALID.getStatus(), ACEnum.OACFLG_CLOSE.getStatus(), crndat);
            if (oacList == null || oacList.isEmpty()) {
                this.fileHandler.appendNewLineAffterTitle("记录为空！");
                return;
            } else {
                this.fileHandler.setSpaces("             ");
                this.fileHandler.appendNewLineAffterTitle("=========================================================");
                this.fileHandler.setFileHeadByArray(new String[]{"帐号", "名称", "标记"});
                for (Actoac oac : oacList) {
                    String actnum = "";
                    String name = "";
                    String process = "";
                    long pleAvabal = mapper.qrySumAvabalFromPle(ACEnum.SYSIDT_AC.getStatus(), oac.getOrgidt(), oac.getCusidt(),
                            oac.getApcode(), oac.getCurcde(), ACEnum.RECSTS_VALID.getStatus());
                    if (pleAvabal != 0) {
                        actnum = oac.getOrgidt() + oac.getCusidt() + oac.getApcode() + oac.getCurcde();
                        this.fileHandler.setFileBodyByArray(new String[]{actnum, name, process});
                        continue;
                    }
                    int updnum = mapper.updateAct(ACEnum.ACTSTS_INVALID.getStatus(), ACEnum.ACTSTS_INVALID.getStatus(),
                            oac.getClsdat(), crndat, ACEnum.SYSIDT_AC.getStatus(), oac.getOrgidt(), oac.getCusidt(),
                            oac.getApcode(), oac.getCurcde(), ACEnum.RECSTS_VALID.getStatus(), ACEnum.ACTSTS_VALID.getStatus(),
                            ACEnum.REGSTS_NORMAL.getStatus(), ACEnum.FRZSTS_NORMAL.getStatus());
                    if (updnum == 0) {
                        Actact act = mapper.qryUniqueAct(ACEnum.SYSIDT_AC.getStatus(), oac.getOrgidt(), oac.getCusidt(),
                                oac.getApcode(), oac.getCurcde(), ACEnum.RECSTS_VALID.getStatus());
                        actnum = oac.getOrgidt() + oac.getCusidt() + oac.getApcode() + oac.getCurcde();
                        if (act != null) {
                            if (act.getBokbal() != 0) {
                                process = "C-FAIL-ACT-BOKBAL<>0";
                            }
                            if (!ACEnum.ACTSTS_VALID.getStatus().equals(act.getActsts())) {
                                process = "C-FAIL-ACT-HAVE-CLOSED";
                            }
                            if (!ACEnum.REGSTS_NORMAL.getStatus().equals(act.getRegsts())) {
                                process = "C-FAIL-ACT-REGSTS-LOCK";
                            }
                            if (!ACEnum.FRZSTS_NORMAL.getStatus().equals(act.getFrzsts())) {
                                process = "C-FAIL-ACT-FRZSTS-LOCK";
                            }
                        } else {
                            process = "C-FAIL-ACT-NOTFOUNT";
                        }
                        this.fileHandler.setFileBodyByArray(new String[]{actnum, name, process});
                        mapper.updateOac(ACEnum.RECSTS_OAC_ACCESSED.getStatus(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
                        continue;
                    }
                    mapper.updatePle(ACEnum.RECSTS_INVALID.getStatus(), ACEnum.SYSIDT_AC.getStatus(), oac.getOrgidt(), oac.getCusidt(),
                            oac.getApcode(), oac.getCurcde(), ACEnum.RECSTS_VALID.getStatus());
                    mapper.updateSbl(ACEnum.SYSIDT_AC.getStatus(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
                    mapper.updateLbl(ACEnum.SYSIDT_AC.getStatus(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
                    mapper.updateOac(ACEnum.RECSTS_OAC_ACCESSED.getStatus(), oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
                    actnum = oac.getOrgidt() + oac.getCusidt() + oac.getApcode() + oac.getCurcde();
                    process = "CLOSE";
                    this.fileHandler.setFileBodyByArray(new String[]{actnum, name, process});
                }
            }
            this.fileHandler.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
