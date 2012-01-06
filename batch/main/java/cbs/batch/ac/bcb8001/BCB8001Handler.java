package cbs.batch.ac.bcb8001;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * ��ȡϵͳ���� �����������ʱ�ļ����У�·���������д��룩
 * User: zhanrui
 * Date: 2011-03-10
 */
@Service
public class BCB8001Handler extends AbstractACBatchJobLogic {

    private static final Logger logger = LoggerFactory.getLogger(BCB8001Handler.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    @Inject
    private BatchSystemService systemService;
    
    private String sysidt;
    private String bizStrDate;
    private String lastBizStrDate;
    //������·��
    private String fileOutPath;
    //�����в���1  C��ȡ��ǰ����  L:��һ��������
    private String paramType = "C";


    public static void main(String argv[]) {
        BCB8001Handler handle = new BCB8001Handler();
        BatchParameterData parameterData = new BatchParameterData();
        handle.processBusiness(parameterData);
    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            String[] paras = parameterData.getCommandLine();
            if (paras.length != 2) {
                logger.error("�����в�������������ҵ���������ͼ��ļ����·����");
                throw new RuntimeException("�����в�������������ҵ���������ͼ��ļ����·����");
            }

            this.paramType = paras[0].toUpperCase();
            if (!"C".equals(this.paramType)&&!"L".equals(this.paramType)) {
                logger.error("�����в���������������ȷ��ҵ���������ͣ�C����ǰҵ�����ڣ���L����һҵ�����ڣ�");
                throw new RuntimeException("�����в���������������ȷ��ҵ���������ͣ�C����ǰҵ�����ڣ���L����һҵ�����ڣ�");
            }
            this.fileOutPath = paras[1];

            initBatch();
            if ("C".equals(this.paramType)) {
                writeTxtFile(this.bizStrDate);
            }else if ("L".equals(this.paramType)) {
                writeTxtFile(this.lastBizStrDate);
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void initBatch(final BatchParameterData batchParam) {

    }

    private void initBatch() throws ParseException {
        this.sysidt = systemService.getSysidtAC();
        this.bizStrDate = systemService.getBizDate();
        this.lastBizStrDate = systemService.getLastBizDate();
    }

    private void writeTxtFile(String bizdate) throws IOException {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(this.fileOutPath + "\\SYSDATE.txt");
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("SYSDATE=" + bizdate);
        }
        finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            }
            catch (IOException ie) {
                logger.error("�ļ���������");
                throw new RuntimeException(ie);
            }
        }
    }
}
