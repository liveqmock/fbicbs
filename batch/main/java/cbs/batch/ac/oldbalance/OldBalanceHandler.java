package cbs.batch.ac.oldbalance;

import cbs.batch.ac.oldbalance.dao.OldBalanceMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.common.SystemService;
import cbs.common.utils.PropertyManager;
import cbs.repository.platform.model.Ptdept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 旧版余额表
 */
//@Service("OldBalanceHandler")
@Service
public class OldBalanceHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(OldBalanceHandler.class);
    @Inject
    private OldBalanceMapper mapper;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        String notNeedAcn = PropertyManager.getProperty("NOT_NECESSARY_OLDACN");
        String filePath = PropertyManager.getProperty("REPORT_ROOTPATH") + SystemService.getBizDate() + "/";
        try {
            List<Ptdept> deptList = mapper.qryAllPtdept();
            List<ActBalance> balList = null;
            int rowLength = 60;
            // 按机构号出余额表
            for (Ptdept dept : deptList) {
                BatchResultFileHandler deptFileHandler = new BatchResultFileHandler("简明余额表.txt");
                deptFileHandler.setFilePath(filePath + dept.getDeptid());
                balList = mapper.qryActBalance(notNeedAcn, dept.getDeptid());
                int rowLength2 = 0;
                deptFileHandler.appendToBody("                                       \u001BW1余额表\u001BW0   "+SystemService.getBizDate10());
                for (ActBalance bal : balList) {
                    rowLength2 = (bal.getOldacn() + bal.getActnam()).getBytes().length + 4;
                    deptFileHandler.appendToBody(new StringBuffer("     ").append(bal.getOldacn()).append(" | ")
                            .append(bal.getActnam()).append(appendSpaceToStr(
                                    rowLength - rowLength2, new DecimalFormat("###,###.00").format(bal.getBokbal() / 100))).toString());
//                            .append(bal.getActnam()).append(appendSpaceToStr(rowLength - rowLength2, SystemService.formatStrAmt(String.format("%.2f", bal.getBokbal() / 100)))).toString());
                }
                deptFileHandler.writeToFile();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {
    }

    private String appendSpaceToStr(int spaceCnt, String content) {
        int spaceAddCnt = spaceCnt - content.toCharArray().length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < spaceAddCnt; i++) {
            builder.append(" ");
        }
        return builder.append(content).toString();
    }
}