package cbs.batch.ac.bcb8534;

import cbs.batch.ac.bcb8534.dao.BCB8534Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.common.enums.ACEnum;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-2
 * Time: 10:47:40
 * 总帐特征检查  写入文件操作被注释
 */
@Service
public class BCB8534Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8534Handler.class);
    @Inject
    private BCB8534Mapper mapper;
    //private BatchResultFileHandler fileHandler;
    private String validRecsts = ACEnum.RECSTS_VALID.getStatus();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<CglGlcCcy> checkList;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8534 总帐特征检查 BEGIN");
        try {
            initProps();
            /* this.fileHandler.setFileTitle("---------------   总帐特征检查    ---------------");
         this.fileHandler.setSpaces("           ");
         this.fileHandler.setFileHeadByArray(
                 new String[]{"GLC CUR", "DRBALA", "CRBALA", "DRAMNT","CRAMNT",
                         "CCY-FLG-ERR","BAL-FLG-ERR","AMT-FLG-ERR"});
         int checkOutNum = 0;*/
            String glfkey = "";
            String drbala = "";
            String crbala = "";
            String dramnt = "";
            String cramnt = "";
            String ccyFlgErr = "";
            String balFlgErr = "";
            String amtFlgErr = "";
            String glcbalC = ACEnum.GLCBAL_C.getStatus();
            String glcbalD = ACEnum.GLCBAL_D.getStatus();
            String glcoccC = ACEnum.GLCOCC_C.getStatus();
            String glcoccD = ACEnum.GLCOCC_D.getStatus();
            String glcccyRMB = ACEnum.GLCCCY_RMB.getStatus();
            String glcccyFX = ACEnum.GLCCCY_FX.getStatus();
            String curcde001 = ACEnum.CURCDE_001.getStatus();
            //int wkErrFlag = 0;
            if (checkList != null && !checkList.isEmpty()) {
                for (CglGlcCcy bean : checkList) {
                    glfkey = new StringBuffer(bean.getOrgidt()).append(bean.getGlcode()).append(bean.getCurcde())
                            .append(bean.getRectyp()).append(sdf.format(bean.getGldate())).toString();
                    if (glcbalC.equalsIgnoreCase(bean.getGlcbal()) && bean.getDrbala() != 0) {
                        drbala = String.valueOf(bean.getDrbala());
                        balFlgErr = "D-BALA";
                        logger.info(new StringBuffer("C-BAL BUT D-BAL<>0  ").append(bean.getGlcode()).append(" ")
                                .append(bean.getCurcde()).append(" ").append(bean.getRectyp()).append(" ").append(drbala).toString());
                    }
                    if (glcbalD.equalsIgnoreCase(bean.getGlcbal()) && bean.getCrbala() != 0) {
                        crbala = String.valueOf(bean.getCrbala());
                        balFlgErr = "C-BALA";
                        logger.info(new StringBuffer("D-BAL BUT C-BAL<>0  ").append(bean.getGlcode()).append(" ")
                                .append(bean.getCurcde()).append(" ").append(bean.getRectyp()).append(" ").append(crbala).toString());
                    }
                    if (glcoccC.equalsIgnoreCase(bean.getGlcocc()) && bean.getDramnt() != 0) {
                        dramnt = String.valueOf(bean.getDramnt());
                        amtFlgErr = "D-AMT";
                        logger.info(new StringBuffer("C-AMT BUT D-AMT<>0  ").append(bean.getGlcode()).append(" ")
                                .append(bean.getCurcde()).append(" ").append(bean.getRectyp()).append(" ").append(dramnt).toString());
                    }
                    if (glcoccD.equalsIgnoreCase(bean.getGlcocc()) && bean.getCramnt() != 0) {
                        cramnt = String.valueOf(bean.getCramnt());
                        amtFlgErr = "C-AMT";
                        logger.info(new StringBuffer("D-AMT BUT C-AMT<>0  ").append(bean.getGlcode()).append(" ")
                                .append(bean.getCurcde()).append(" ").append(bean.getRectyp()).append(" ").append(cramnt).toString());
                    }
                    if (glcccyRMB.equalsIgnoreCase(bean.getGlcccy()) && !curcde001.equalsIgnoreCase(bean.getCurcde())) {
                        ccyFlgErr = "CCY-MAK1";
                        logger.info(new StringBuffer("RMB BUT CURCDE<>001  ").append(bean.getGlcode()).append(" ")
                                .append(bean.getCurcde()).append(" ").append(bean.getRectyp()).toString());
                    }
                    if (glcccyFX.equalsIgnoreCase(bean.getGlcccy()) && curcde001.equalsIgnoreCase(bean.getCurcde())) {
                        ccyFlgErr = "CCY-MAK2";
                        logger.info(new StringBuffer("WAIBI BUT CURCDE=001  ").append(bean.getGlcode()).append(" ")
                                .append(bean.getCurcde()).append(" ").append(bean.getRectyp()).toString());
                    }

                }
            }

            /*this.fileHandler.appendToFoot("检查到" + checkOutNum + "条记录");
            this.fileHandler.writeToFile();*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8534 总帐特征检查 END");
    }

    protected void initBatch(final BatchParameterData batchParam) {
        // this.fileHandler = new BatchResultFileHandler("list8534_" +sdf.format(new Date()) + ".txt");
    }

    private void initProps() {
        this.checkList = mapper.qryCglGlcCcyBySts(validRecsts, validRecsts);
    }
}
