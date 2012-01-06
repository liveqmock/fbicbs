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
 * 获取系统日期 输出到批量临时文件夹中（路径由命令行传入）
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
    //结果输出路径
    private String fileOutPath;
    //命令行参数1  C：取当前日期  L:上一工作日期
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
                logger.error("命令行参数错误：请输入业务日期类型及文件输出路径！");
                throw new RuntimeException("命令行参数错误：请输入业务日期类型及文件输出路径！");
            }

            this.paramType = paras[0].toUpperCase();
            if (!"C".equals(this.paramType)&&!"L".equals(this.paramType)) {
                logger.error("命令行参数错误：请输入正确的业务日期类型！C（当前业务日期），L（上一业务日期）");
                throw new RuntimeException("命令行参数错误：请输入正确的业务日期类型！C（当前业务日期），L（上一业务日期）");
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
                logger.error("文件操作错误。");
                throw new RuntimeException(ie);
            }
        }
    }
}
