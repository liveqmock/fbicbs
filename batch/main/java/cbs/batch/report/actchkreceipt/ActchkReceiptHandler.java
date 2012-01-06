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
 * �ɰ�����
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
            // �������ų�����
            for(Ptdept dept : deptList) {
                  BatchResultFileHandler deptFileHandler  =  new BatchResultFileHandler("�����˵���ִ.txt");
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

                     deptFileHandler.appendToBody("                        \u001BW1"+dept.getDeptname()+"���˵���ִ\u001BW0 ");
                     deptFileHandler.appendToBody(" \r\n");
                     deptFileHandler.appendToBody("       ���: "+(i++));
                     deptFileHandler.appendToBody("       �˺�: "+bal.getOldacn()+appendSpaceToStr(87-bal.getOldacn().getBytes().length,"��ֹ��: "+busDate10));
                     deptFileHandler.appendToBody("       ��λ����: "+bal.getActnam()+appendSpaceToChStr(86-bal.getActnam().getBytes().length,"��Ŀ: "+bal.getGlcnam()));
                     // 30�ո� һ�����������ո�
                     deptFileHandler.appendToBody("      ���������������������������������ש������������������������������ש�������������������������������");
                     deptFileHandler.appendToBody("      ��            �޶���          ��         �˶��޶����         ��            ������          ��");
                     deptFileHandler.appendToBody("      �ǩ������������������������������贈�����������������������������贈������������������������������");
                     deptFileHandler.appendToBody("      ��                              ��                              ��"+appendSpaceToStr(30,SystemService.formatStrAmt(String.valueOf(bal.getBokbal()/100)))+"��");
                     deptFileHandler.appendToBody("      �ǩ������������������������������ߩ��������������ש��������������ߩ�������������������������������");
                     deptFileHandler.appendToBody("      ��        ��              ��                    ��                                              ��");
                     deptFileHandler.appendToBody("      ��ҵ���˶�                  ,ϣ���ա�           ��       ����������˶�ǩ֤����7�������Ҵ�      ��");
                     deptFileHandler.appendToBody("      ��        �Ƚ������ϸ�������                    ��                                              ��");
                     deptFileHandler.appendToBody("      ��                    ����λǩ�£�              ��                                  ����λǩ�£���");
                     deptFileHandler.appendToBody("      �ǩ��������ש������������ש����������������������贈���������������������ש�����������������������");
                     deptFileHandler.appendToBody("      ��  ����  ��    ƾ֤    ��                      ����λ�����У����������ީ���λ�����ޣ����������Щ�");
                     deptFileHandler.appendToBody("      ��        ��            ��         ժҪ         �ǩ����������ש����������贈���������ש�����������");
                     deptFileHandler.appendToBody("      ��  ����  ��    ����    ��                      ��  ��  ��  ��  ��  ��  ��  ��  ��  ��  ��  ��  ��");
                     deptFileHandler.appendToBody("      �ǩ��������贈�����������贈���������������������贈���������贈���������贈���������贈����������");
                     deptFileHandler.appendToBody("      ��        ��            ��                      ��          ��          ��          ��          ��");
                     deptFileHandler.appendToBody("      �ǩ��������贈�����������贈���������������������贈���������贈���������贈���������贈����������");
                     deptFileHandler.appendToBody("      ��        ��            ��                      ��          ��          ��          ��          ��");
                     deptFileHandler.appendToBody("      �ǩ��������贈�����������贈���������������������贈���������贈���������贈���������贈����������");
                     deptFileHandler.appendToBody("      ��        ��            ��                      ��          ��          ��          ��          ��");
                     deptFileHandler.appendToBody("      �ǩ��������贈�����������贈���������������������贈���������贈���������贈���������贈����������");
                     deptFileHandler.appendToBody("      ��        ��            ��                      ��          ��          ��          ��          ��");
                     deptFileHandler.appendToBody("      �ǩ��������贈�����������贈���������������������贈���������贈���������贈���������贈����������");
                     deptFileHandler.appendToBody("      ��        ��            ��                      ��          ��          ��          ��          ��");
                     deptFileHandler.appendToBody("      �ǩ��������贈�����������贈���������������������贈���������贈���������贈���������贈����������");
                     deptFileHandler.appendToBody("      ��        ��            ��                      ��          ��          ��          ��          ��");
                     deptFileHandler.appendToBody("      �ǩ��������贈�����������贈���������������������贈���������贈���������贈���������贈����������");
                     deptFileHandler.appendToBody("      ��        ��            ��                      ��          ��          ��          ��          ��" );
                     deptFileHandler.appendToBody("      �ǩ��������ߩ������������ߩ����������������������贈���������ߩ����������ߩ����������ߩ�����������");
                     deptFileHandler.appendToBody("      ��           ��  ��  λ �� ��  ��  ��           ��                                              ��");
                     deptFileHandler.appendToBody("      �������������������������������������������������ߩ�����������������������������������������������");
                     deptFileHandler.appendToBody(" \r\n");
                     //deptFileHandler.appendToBody("  ��������������������������������������������������������������������������������������������������");
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
// �ַ�����ӿո�
    private String appendSpaceToStr(int spaceCnt , String content) {
        int spaceAddCnt = spaceCnt - content.toCharArray().length;
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < spaceAddCnt ; i++) {
            builder.append(" ");
        }
        return builder.append(content).toString();
    }
    // ������ӿո�
    private String appendSpaceToChStr(int spaceCnt , String content) {
        int spaceAddCnt = spaceCnt - content.getBytes().length;
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < spaceAddCnt ; i++) {
            builder.append(" ");
        }
        return builder.append(content).toString();
    }
}