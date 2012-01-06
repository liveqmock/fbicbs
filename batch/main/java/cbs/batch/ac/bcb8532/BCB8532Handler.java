package cbs.batch.ac.bcb8532;

import cbs.batch.ac.bcb8532.dao.BCB8532Mapper;
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
 * Date: 2011-3-1
 * Time: 15:07:08
 * 总帐竖平检查
 */
@Service
public class BCB8532Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8532Handler.class);
    @Inject
    private BCB8532Mapper mapper;
    private Date crndat;
    private BatchResultFileHandler fileHandler1;
    private BatchResultFileHandler fileHandler2;
    private List<CglccyBean> cglccyList;
    private String validRecsts = ACEnum.RECSTS_VALID.getStatus();

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8532 总帐竖平检查 BEGIN");
        try {
            initProps();
            if (crndat == null) {
                logger.info("OPEN  ACTSCT IS ERROR");
                return;
            }
            this.fileHandler1.setFileTitle("-------------CGL 竖不平清单(CGL SUM-DRBALA !=SUM-CRBALA!)-----------------");
            this.fileHandler2.setFileTitle("-------------CGL 竖不平清单(CGL SUM-DRAMNT !=SUM-CRAMNT!)-----------------");
            this.fileHandler1.setSpaces("       ");
            this.fileHandler1.setFileHeadByArray(new String[]{"CURCDE", "SUM-DRBALA", "SUM-CRBALA", "BAL-ALABAL"});
            this.fileHandler2.setSpaces("       ");
            this.fileHandler2.setFileHeadByArray(new String[]{"CURCDE", "SUM-DRAMNT", "SUM-CRAMNT", "BAL-MNTBAL"});
            int checkOutNum1 = 0;
            int checkOutNum2 = 0;
            if (cglccyList != null && !cglccyList.isEmpty()) {
                for (CglccyBean cglccy : cglccyList) {
                    long wkBalAlaamt = 0;
                    wkBalAlaamt = cglccy.getSumDrbala() + cglccy.getSumCrbala();
                    if (wkBalAlaamt != 0) {
                        this.fileHandler1.setFileBodyByArray(new String[]{cglccy.getCurcde(), String.valueOf(cglccy.getSumDrbala())
                                , String.valueOf(cglccy.getSumCrbala()), String.valueOf(wkBalAlaamt)});
                        checkOutNum1++;
                    }
                    long wkBalMntbal = 0;
                    wkBalMntbal = cglccy.getSumDramnt() + cglccy.getSumCramnt();
                    if (wkBalMntbal != 0) {
                        this.fileHandler2.setFileBodyByArray(new String[]{cglccy.getCurcde(), String.valueOf(cglccy.getSumDramnt())
                                , String.valueOf(cglccy.getSumCramnt()), String.valueOf(wkBalMntbal)});
                        checkOutNum2++;
                    }
                }
            }
            this.fileHandler1.appendToFoot("检查到" + checkOutNum1 + "条记录");
            this.fileHandler2.appendToFoot("检查到" + checkOutNum2 + "条记录");
            this.fileHandler1.writeToFile();
            this.fileHandler2.writeToFile();
            //
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8532 总帐竖平检查 END");
    }

    protected void initBatch(final BatchParameterData batchParam) {
        this.fileHandler1 = new BatchResultFileHandler("list8532.txt");
        this.fileHandler2 = new BatchResultFileHandler("list8532a.txt");
    }

    private void initProps() {
        this.crndat = mapper.selectCrndat((short) 8, validRecsts);
        this.cglccyList = mapper.qryCglCcy(validRecsts, validRecsts);
    }
}
