package cbs.batch.ac.bcb8535;

import cbs.batch.ac.bcb8535.dao.BCB8535Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actcgl;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-2
 * Time: 12:47:40
 * 总分平帐
 */
@Service
public class BCB8535Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8535Handler.class);
    @Inject
    private BCB8535Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    private BatchResultFileHandler fileHandler;
    private String validRecsts = ACEnum.RECSTS_VALID.getStatus();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<Actcgl> cglList;
    private List<SumAct> sumList;
    private int diffFlag;
    private int errFlag;
    private int newFlag = 1;

    private String cglKey;
    private String actKey;
    private String acMRpReason;
    private String acMRpOrgidt;
    private String acMRpCurcde;
    private String acMRpGlcode;
    private String acMRpCramnt;
    private String acMRpDramnt;
    private String acMRpCrbala;
    private String acMRpDrbala;
    private String acMRpDcramt;
    private String acMRpDdramt;
    private String acMRpBokbal;
    private short decpos;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8535 总分平帐 BEGIN");
        try {
            initProps();
            this.fileHandler.setFileTitle("---------------   (总分平帐) CGL 与ACT 总和不平清单    ---------------");
            int checkNum = 0;
            int cglEndFlag = 0;
            int actEndFlag = 0;
            int cglIndex = 0;
            int actIndex = 0;
            Actcgl cgl = null;
            SumAct act = null;
            if (cglList != null && !cglList.isEmpty()) {
                cgl = cglList.get(cglIndex);
            } else {
                cglEndFlag = 1;
            }
            if (sumList != null && !sumList.isEmpty()) {
                act = sumList.get(actIndex);
            } else {
                actEndFlag = 1;
            }
            this.diffFlag = 0;
            this.errFlag = 0;

            while (cglEndFlag != 1 && actEndFlag != 1) {
                cglKey = cgl.getOrgidt() + cgl.getCurcde() + cgl.getGlcode();
                actKey = act.getOrgidt() + act.getCurcde() + act.getActglc();
                if (!cglKey.equalsIgnoreCase(actKey)) {
                    if (cglKey.compareToIgnoreCase(actKey) < 0) {
                        if (cglEndFlag == 0) {
                            if (cgl.getCramnt() != 0 || cgl.getDramnt() != 0 || cgl.getCrcunt() != 0
                                    || cgl.getDrcunt() != 0 || cgl.getCrbala() != 0 || cgl.getDrbala() != 0) {
                                this.diffFlag = 1;
                                this.errFlag = 1;
                            }
                        } else {
                            this.diffFlag = 2;
                            this.errFlag = 1;
                        }
                    } else {
                        if (actEndFlag == 0) {
                            this.diffFlag = 2;
                            this.errFlag = 1;
                        } else {
                            if (cgl.getCramnt() != 0 || cgl.getDramnt() != 0 || cgl.getCrcunt() != 0
                                    || cgl.getDrcunt() != 0 || cgl.getCrbala() != 0 || cgl.getDrbala() != 0) {
                                this.diffFlag = 1;
                                this.errFlag = 1;
                            }
                        }
                    }
                } else {
                    if (ACEnum.RECTYP_D.getStatus().equalsIgnoreCase(cgl.getRectyp())) {
                        if (act.getSumDdramt() != cgl.getDramnt() || act.getSumDcramt() != cgl.getCramnt()
                                || act.getSumDdrcnt() != cgl.getDrcunt() || act.getSumDcrcnt() != cgl.getCrcunt()
                                || (cgl.getDrbala() + cgl.getCrbala() != act.getSumBokbal())) {
                            this.diffFlag = 3;
                            this.errFlag = 1;
                        }
                    }
                }
                if (this.diffFlag == 1) {
                    this.acMRpReason = "EXIST IN CGL BUT NOT IN ACT!";
                    this.decpos = mapper.selectDecpos(cgl.getCurcde());
                    this.acMRpOrgidt = cgl.getOrgidt();
                    this.acMRpCurcde = cgl.getCurcde();
                    this.acMRpGlcode = cgl.getGlcode();
                    this.acMRpCramnt = String.valueOf(cgl.getCramnt());
                    this.acMRpDramnt = String.valueOf(cgl.getDramnt());
                    this.acMRpCrbala = String.valueOf(cgl.getCrbala());
                    this.acMRpDrbala = String.valueOf(cgl.getDrbala());
                    this.acMRpDcramt = " ";
                    this.acMRpDdramt = " ";
                    this.acMRpBokbal = " ";

                }
                if (this.diffFlag == 2) {
                    this.acMRpReason = "EXIST IN ACT BUT NOT IN CGL!";
                    this.decpos = mapper.selectDecpos(act.getCurcde());
                    this.acMRpOrgidt = act.getOrgidt();
                    this.acMRpCurcde = act.getCurcde();
                    this.acMRpGlcode = act.getActglc();
                    this.acMRpDcramt = String.valueOf(act.getSumDcramt());
                    this.acMRpDdramt = String.valueOf(act.getSumDdramt());
                    this.acMRpBokbal = String.valueOf(act.getSumBokbal());
                    this.acMRpCramnt = " ";
                    this.acMRpDramnt = " ";
                    this.acMRpCrbala = " ";
                    this.acMRpDrbala = " ";
                }
                if (this.diffFlag == 3) {
                    this.acMRpReason = "CGL发生额或余额 != ACT";
                    this.decpos = mapper.selectDecpos(act.getCurcde());
                    this.acMRpOrgidt = act.getOrgidt();
                    this.acMRpCurcde = act.getCurcde();
                    this.acMRpGlcode = act.getActglc();
                    this.acMRpCramnt = String.valueOf(cgl.getCramnt());
                    this.acMRpDramnt = String.valueOf(cgl.getDramnt());
                    this.acMRpCrbala = String.valueOf(cgl.getCrbala());
                    this.acMRpDrbala = String.valueOf(cgl.getDrbala());
                    this.acMRpDcramt = String.valueOf(act.getSumDcramt());
                    this.acMRpDdramt = String.valueOf(act.getSumDdramt());
                    this.acMRpBokbal = String.valueOf(act.getSumBokbal());
                }
                if (this.diffFlag != 0) {
                    StringBuffer showBuffer = new StringBuffer();
                    if (this.newFlag == 1) {
                        String errMainMsg = new StringBuffer().append("ERR ON:   机构号ORGIDT:").append(acMRpOrgidt)
                                .append("     总帐码GLCODE:").append(acMRpGlcode).append("     货币码CURCDE:")
                                .append(acMRpCurcde).toString();
                        String detailMsg1 = new StringBuffer().append("   ACTACT- SUMBAL:").append(acMRpBokbal)
                                .append("     SUMDRAMT:").append(acMRpDdramt).append("     SUMCRAMT:").append(acMRpDcramt)
                                .toString();
                        String detailMsg2 = new StringBuffer().append("   ACTCGL- CRBALA:").append(acMRpCrbala)
                                .append("     DRBALA:").append(acMRpDrbala).append("     DDRAMT:").append(acMRpDramnt)
                                .append("     DCRAMT:").append(acMRpCramnt).toString();
                        this.fileHandler.appendToBody(acMRpReason);
                        this.fileHandler.appendToBody(errMainMsg);
                        this.fileHandler.appendToBody(detailMsg1);
                        this.fileHandler.appendToBody(detailMsg2);
                        this.newFlag = 0;
                        checkNum++;  // 计数器
                    }
                    this.diffFlag = 0;
                }
                if ((cglEndFlag == 0 && cglKey.compareToIgnoreCase(actKey) <= 0)
                        || (cglEndFlag == 0 && actEndFlag == 1)) {
                    cglIndex++;
                    if (cglIndex >= cglList.size()) {
                        cglEndFlag = 1;
                    } else {
                        cgl = cglList.get(cglIndex);
                    }
                }
                if ((actEndFlag == 0 && cglKey.compareToIgnoreCase(actKey) >= 0)
                        || (cglEndFlag == 1 && actEndFlag == 0)) {
                    actIndex++;
                    if (actIndex >= sumList.size()) {
                        actEndFlag = 1;
                    } else {
                        act = sumList.get(actIndex);
                    }
                }
            }
            if (checkNum == 0) {
                this.fileHandler.appendNewLineAffterTitle("记录为空！");
                this.fileHandler.writeToFile();
            }else{
                this.fileHandler.writeToFile();
                throw new RuntimeException("总分平帐数据不平!请检查 list8535.txt ");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8535 总分平帐 END");
    }

    protected void initBatch(final BatchParameterData batchParam) {
        this.fileHandler = new BatchResultFileHandler("list8535.txt");
    }

    private void initProps() {
        this.cglList = mapper.qryCgl(validRecsts, ACEnum.RECTYP_D.getStatus());
        this.sumList = mapper.qrySumAct(validRecsts, systemService.getSysidtAC());
    }
}