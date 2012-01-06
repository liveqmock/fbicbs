package cbs.batch.report.saveprtfile;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.utils.PropertyManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-6-2
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
@Service
public class saveprtfileHandler extends AbstractACBatchJobLogic {
    private static final Log logger = LogFactory.getLog(saveprtfileHandler.class);

    /*public static void main(String argv[]) {
        saveprtfileHandler handle = new saveprtfileHandler();
        BatchParameterData parameterData = new BatchParameterData();

        handle.processBusiness(parameterData);
    }*/

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            String[] strarry = parameterData.getCommandLine();
            String strdate = strarry[0];
            String filename = strarry[1];
            int beginPnum = Integer.parseInt(StringUtils.isEmpty(strarry[2]) ? "1" : strarry[2]);
            int endPnum = Integer.parseInt(StringUtils.isEmpty(strarry[3]) ? "0" : strarry[3]);
            String readFilePath = PropertyManager.getProperty("REPORT_ROOTPATH") + strdate + "/010/" + filename + ".txt";
            String writeFilePath = PropertyManager.getProperty("REPORT_ROOTPATH") + "tmp/prttmp.txt";
            String writeFileDirec = PropertyManager.getProperty("REPORT_ROOTPATH") + "tmp";
            createFileDirec(writeFileDirec);
            readFile(strdate, readFilePath,writeFilePath, beginPnum, endPnum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void readFile(String strdate, String readFilePath,String writeFilePath, int beginPnum, int endPnum) throws IOException {
        File file = new File(readFilePath);
        FileInputStream is = null;
        is = new FileInputStream(file);
        byte[] isbyte = new byte[(int) file.length()];
        is.read(isbyte);
        String readStr = new String(isbyte);
        String[] readStrary = readStr.split("\\f");
        beginPnum = (beginPnum == 0) ? 1 : beginPnum;
        endPnum = (endPnum == 0) ? readStrary.length : endPnum;
        FileOutputStream out = new FileOutputStream(writeFilePath);
        if (beginPnum <= endPnum) {
            String[] writeStrary;
            writeStrary = new String[endPnum - beginPnum + 1];
            int acnt = 0;
            for (int i = beginPnum; i <= endPnum; i++) {
                writeStrary[acnt] = readStrary[i - 1];
                acnt++;
            }
            String writeStr = "";
            for (int i = 0; i < writeStrary.length; i++) {
                if (i == (writeStrary.length - 1)) {
                    writeStr += writeStrary[i];
                } else {
                    writeStr += writeStrary[i] + "\f";
                }
            }
            //创建文件路径
            out.write(writeStr.getBytes());
            out.close();
        }
    }

    private void createFileDirec(String fileDirecPath) {
        File file = new File(fileDirecPath);
        if (!file.isDirectory() || !file.exists()) {
            if (!file.mkdirs()) {
                logger.error("路径创建失败!");
            }
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }
}
