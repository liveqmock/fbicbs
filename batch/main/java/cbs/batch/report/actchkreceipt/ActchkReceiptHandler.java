package cbs.batch.report.actchkreceipt;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.batch.report.actchkreceipt.dao.ActchkReceiptMapper;
import cbs.common.SystemService;
import cbs.common.utils.PropertyManager;
import cbs.repository.platform.model.Ptdept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * 旧版余额表
 */
@Service("ActchkReceiptHandler")
public class ActchkReceiptHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(ActchkReceiptHandler.class);
    @Inject
    private ActchkReceiptMapper mapper;
    private String busDate = SystemService.getBizDate();
    private String busDate10 = SystemService.getBizDate10();
    private int tableByteWidth = 98;
    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        String notNeedAcn =  PropertyManager.getProperty("NOT_NECESSARY_OLDACN");
        String filePath = PropertyManager.getProperty("REPORT_ROOTPATH")+ busDate+"/";
        try {
            List<Ptdept> deptList = mapper.qryAllPtdept();
            List<BalReceipt> balList = null;
            // 按机构号出余额表
            for(Ptdept dept : deptList) {
                  BatchResultFileHandler deptFileHandler  =  new BatchResultFileHandler("存款对账单回执.txt");
                  deptFileHandler.setFilePath(filePath+dept.getDeptid());
                  balList = mapper.qryBalReceipts(notNeedAcn, dept.getDeptid());
                  int i = 1;
                 for(BalReceipt bal : balList) {
                     deptFileHandler.appendToBody(" \r\n");
                     deptFileHandler.appendToBody(" \r\n");
                     deptFileHandler.appendToBody(" \r\n");
                     deptFileHandler.appendToBody(" \r\n");
                     deptFileHandler.appendToBody(" \r\n");
                     deptFileHandler.appendToBody(" \r\n");

                     deptFileHandler.appendToBody("                        \u001BW1"+dept.getDeptname()+"对账单回执\u001BW0 ");
                     deptFileHandler.appendToBody(" \r\n");
                     deptFileHandler.appendToBody("       序号: "+(i++));
                     deptFileHandler.appendToBody("       账号: "+bal.getOldacn()+appendSpaceToStr(87-bal.getOldacn().getBytes().length,"截止日: "+busDate10));
                     deptFileHandler.appendToBody("       单位名称: "+bal.getActnam()+appendSpaceToChStr(86-bal.getActnam().getBytes().length,"科目: "+bal.getGlcnam()));
                     // 30空格 一个汉字两个空格
                     deptFileHandler.appendToBody("      ┏━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━┓");
                     deptFileHandler.appendToBody("      ┃            限额存款          ┃         核定限额余额         ┃            存款余额          ┃");
                     deptFileHandler.appendToBody("      ┣━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━┫");
                     deptFileHandler.appendToBody("      ┃                              ┃                              ┃"+appendSpaceToStr(30,SystemService.formatStrAmt(String.valueOf(bal.getBokbal()/100)))+"┃");
                     deptFileHandler.appendToBody("      ┣━━━━━━━━━━━━━━━┻━━━━━━━┳━━━━━━━┻━━━━━━━━━━━━━━━┫");
                     deptFileHandler.appendToBody("      ┃        相              符                    ┃                                              ┃");
                     deptFileHandler.appendToBody("      ┃业经核对                  ,希查照。           ┃       上列数字请核对签证后于7日内退我处      ┃");
                     deptFileHandler.appendToBody("      ┃        兹将不符合各款列下                    ┃                                              ┃");
                     deptFileHandler.appendToBody("      ┃                    （单位签章）              ┃                                  （单位签章）┃");
                     deptFileHandler.appendToBody("      ┣━━━━┳━━━━━━┳━━━━━━━━━━━╋━━━━━━━━━━━┳━━━━━━━━━━━┫");
                     deptFileHandler.appendToBody("      ┃  记帐  ┃    凭证    ┃                      ┃单位账上有，结算中心无┃单位账上无，结算中心有┃");
                     deptFileHandler.appendToBody("      ┃        ┃            ┃         摘要         ┣━━━━━┳━━━━━╋━━━━━┳━━━━━┫");
                     deptFileHandler.appendToBody("      ┃  日期  ┃    号码    ┃                      ┃  借  方  ┃  贷  方  ┃  借  方  ┃  贷  方  ┃");
                     deptFileHandler.appendToBody("      ┣━━━━╋━━━━━━╋━━━━━━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫");
                     deptFileHandler.appendToBody("      ┃        ┃            ┃                      ┃          ┃          ┃          ┃          ┃");
                     deptFileHandler.appendToBody("      ┣━━━━╋━━━━━━╋━━━━━━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫");
                     deptFileHandler.appendToBody("      ┃        ┃            ┃                      ┃          ┃          ┃          ┃          ┃");
                     deptFileHandler.appendToBody("      ┣━━━━╋━━━━━━╋━━━━━━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫");
                     deptFileHandler.appendToBody("      ┃        ┃            ┃                      ┃          ┃          ┃          ┃          ┃");
                     deptFileHandler.appendToBody("      ┣━━━━╋━━━━━━╋━━━━━━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫");
                     deptFileHandler.appendToBody("      ┃        ┃            ┃                      ┃          ┃          ┃          ┃          ┃");
                     deptFileHandler.appendToBody("      ┣━━━━╋━━━━━━╋━━━━━━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫");
                     deptFileHandler.appendToBody("      ┃        ┃            ┃                      ┃          ┃          ┃          ┃          ┃");
                     deptFileHandler.appendToBody("      ┣━━━━╋━━━━━━╋━━━━━━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫");
                     deptFileHandler.appendToBody("      ┃        ┃            ┃                      ┃          ┃          ┃          ┃          ┃");
                     deptFileHandler.appendToBody("      ┣━━━━╋━━━━━━╋━━━━━━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫");
                     deptFileHandler.appendToBody("      ┃        ┃            ┃                      ┃          ┃          ┃          ┃          ┃" );
                     deptFileHandler.appendToBody("      ┣━━━━┻━━━━━━┻━━━━━━━━━━━╋━━━━━┻━━━━━┻━━━━━┻━━━━━┫");
                     deptFileHandler.appendToBody("      ┃           我  单  位 账 面  余  额           ┃                                              ┃");
                     deptFileHandler.appendToBody("      ┗━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━┛");
                     deptFileHandler.appendToBody(" \r\n");
                     //deptFileHandler.appendToBody("  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                      deptFileHandler.appendToBody(" \f");
                 }
                 deptFileHandler.writeToFile();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {
    }
// 字符串添加空格
    private String appendSpaceToStr(int spaceCnt , String content) {
        int spaceAddCnt = spaceCnt - content.toCharArray().length;
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < spaceAddCnt ; i++) {
            builder.append(" ");
        }
        return builder.append(content).toString();
    }
    // 汉字添加空格
    private String appendSpaceToChStr(int spaceCnt , String content) {
        int spaceAddCnt = spaceCnt - content.getBytes().length;
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < spaceAddCnt ; i++) {
            builder.append(" ");
        }
        return builder.append(content).toString();
    }
}