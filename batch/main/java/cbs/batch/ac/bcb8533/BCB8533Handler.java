package cbs.batch.ac.bcb8533;

import cbs.batch.ac.bcb8532.CglccyBean;
import cbs.batch.ac.bcb8533.dao.BCB8533Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.common.enums.ACEnum;
import cbs.repository.code.model.Actsct;
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
 * Time: 16:57:20
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB8533Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8533Handler.class);
    @Inject
    private BCB8533Mapper mapper;
    private BatchResultFileHandler fileHandler;
    private String validRecsts = ACEnum.RECSTS_VALID.getStatus();
    private List<CglccyProp> propList;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8533 总帐横平检查 BEGIN");
        try {
            initProps();
            this.fileHandler.setFileTitle("-------------CGL 横不平清单-----------------");
            this.fileHandler.setSpaces("       ");
            this.fileHandler.setFileHeadByArray(
                    new String[]{"ORGIDT", "RECTYP", "GLCODE", "CURCDE", "DRAMNT", "CRAMNT",
                            "DRBALA", "CRBALA", "LST-DRBALA", "LST-CRBALA"});
            int checkOutNum = 0;
            int rightNum = 0;
            if (propList != null && !propList.isEmpty()) {
                for (CglccyProp cglccy : propList) {
                    if ((cglccy.getDlsbal() + cglccy.getClsbal() + (cglccy.getDramnt() + cglccy.getCramnt())
                            != (cglccy.getDrbala() + cglccy.getCrbala()))) {
                        logger.info("glcode=" + cglccy.getGlcode() + "  curcde=" + cglccy.getCurcde());
                        logger.info("rectyp=" + cglccy.getRectyp());
                        checkOutNum++;
                        this.fileHandler.setFileBodyByArray(new String[]{cglccy.getOrgidt(), cglccy.getRectyp(),
                                cglccy.getGlcode(), cglccy.getCurcde(), String.valueOf(cglccy.getDramnt()),
                                String.valueOf(cglccy.getCramnt()), String.valueOf(cglccy.getDrbala()),
                                String.valueOf(cglccy.getCrbala()), String.valueOf(cglccy.getDlsbal()), String.valueOf(cglccy.getClsbal())});
                    } else {
                        rightNum++;
                    }
                }
            }
            this.fileHandler.appendToFoot("错误记录数:" + checkOutNum);
            this.fileHandler.appendToFoot("正确记录数:" + rightNum);
            this.fileHandler.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8533 总帐横平检查 END");
    }

    protected void initBatch(final BatchParameterData batchParam) {
        this.fileHandler = new BatchResultFileHandler("list8533.txt");
    }

    private void initProps() {
        propList = mapper.selectCglccy(validRecsts, validRecsts, "D");
    }
}
