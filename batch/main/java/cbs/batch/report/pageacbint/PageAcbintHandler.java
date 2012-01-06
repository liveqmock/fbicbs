package cbs.batch.report.pageacbint;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.common.utils.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 利息通知单分页打印
 */
@Service("PageAcbintHandler")
public class PageAcbintHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(PageAcbintHandler.class);
    private String busDate;
    private int startPage;
    private int endPage;
    private String readFilePath;
    private String writeFilePath;
    private String readFileName;
    private String writeFileName;
    private BatchResultFileHandler fileHandler;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            String[] params = parameterData.getCommandLine();
            if (params == null || params.length != 4) {
                throw new RuntimeException("命令行参数长度应为4 ! ");
            }
            busDate = params[0];
            readFileName = params[1];
            startPage = Integer.parseInt(params[2]);
            endPage = Integer.parseInt(params[3]);
            if(startPage > endPage) {
                 throw new RuntimeException("截止页码应不小于起始页码 ! ");
            }
            readFilePath = PropertyManager.getProperty("REPORT_ROOTPATH") + busDate + "/010/";
            writeFilePath = PropertyManager.getProperty("REPORT_ROOTPATH") + "tmp";
            writeFileName = "利息通知单8910out_按页.txt";
            fileHandler = new BatchResultFileHandler(writeFileName);
            fileHandler.setFilePath(writeFilePath);
            String pageContent = readContentFromFile();
            fileHandler.appendToBody(pageContent);
            fileHandler.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *   按页码查找文件内容
     * @return       所需内容
     */
    private String readContentFromFile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        FileInputStream fis = new FileInputStream(new File(readFilePath+readFileName));
        byte [] byteContent = new byte[fis.available()];
        fis.read(byteContent);
        String allContent = new String(byteContent);
        String[] pageContents = allContent.split("\\f");
        for(int i = startPage; i <= endPage; i++) {
            contentBuilder.append(pageContents[i-1]);
            if(i != endPage) { contentBuilder.append("\f"); }
        }
        return contentBuilder.toString();
    }

    protected void initBatch(final BatchParameterData batchParam) {
    }
}