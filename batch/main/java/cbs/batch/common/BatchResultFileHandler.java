package cbs.batch.common;

import cbs.common.utils.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-2-25
 * Time: 13:04:05
 * DEMO: fileHandler.setFileTitle("--------DEMO-------");
 * this.fileHandler.setSpaces("     ");
 * this.fileHandler.setFileHeadByArray(new String[]{"ORGIDT", "CURCDE", "SUM-DDRAMT", "SUM-DCRAMT"
 * , "SUM-MDRAMT", "SUM-MCRAMT", "SUM-YDRAMT", "SUM-YCRAMT", "SUM-BALAMT"});
 * this.fileHandler.setFileBodyByArray("---" );
 * this.fileHandler.appendToFoot("检查到" + num + "条记录")
 * this.fileHandler.writeToFile();
 */
public class BatchResultFileHandler {

    private static final Logger logger = LoggerFactory.getLogger(BatchResultFileHandler.class);
    private String filePath = PropertyManager.getProperty("BATCH_RESULT_FILE_PATH");
    private String fileName;
    private File file;
    private String fileTitle;
    private StringBuffer fileHead;
    private String[] headElements;
    private StringBuffer fileBody;
    private StringBuffer fileFoot;
    private String spaces = "        ";              // 间隔的空格数(默认为8个)
    private String newLineCh = "\r\n";       // 换行 适用于windows系统
    private StringBuffer otherLineContent;    //  备用行

    public BatchResultFileHandler(String fileName) {
        this.fileName = fileName;
        this.fileTitle = "";
        this.fileHead = new StringBuffer("");
        this.fileBody = new StringBuffer("");
        this.fileFoot = new StringBuffer("");
        this.otherLineContent = new StringBuffer("");
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSpaces() {
        return spaces;
    }

    public void setSpaces(String spaces) {
        this.spaces = spaces;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public StringBuffer getFileHead() {
        return fileHead;
    }

    public void setFileHead(StringBuffer fileHead) {
        this.fileHead = fileHead;
    }

    public StringBuffer getFileBody() {
        return fileBody;
    }

    public void setFileBody(StringBuffer fileBody) {
        this.fileBody = fileBody;
    }

    public StringBuffer getFileFoot() {
        return fileFoot;
    }

    public void setFileFoot(StringBuffer fileFoot) {
        this.fileFoot = fileFoot;
    }

    public void appendNewLineAffterTitle(String lineContent) {
        if (this.fileTitle != null) {
            this.fileTitle = new StringBuffer().append(fileTitle).append(newLineCh).append(lineContent).toString();
        } else {
            this.fileTitle = lineContent;
        }
    }

    public void appendToOtherLineContent(String lineContent) {
        this.otherLineContent.append(newLineCh).append(lineContent);
    }
    // 设置表头 并保存各表头内容

    public void setFileHeadByArray(String[] elements) {
        this.headElements = elements;
        for (int i = 0; i < elements.length; i++) {
            headElements[i] = this.spaces + elements[i];
            this.fileHead.append(headElements[i]);
        }
        this.fileHead.append(newLineCh);

    }

    // 根据表头内容长度补充各元素空格

    public void setFileBodyByArray(String[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if(elements[i] == null) elements[i] = "null";
            int appendSpaceNum = headElements[i].length() - elements[i].length();
            if (appendSpaceNum > 0) {
                StringBuffer bufferSpaces = new StringBuffer();
                for (int j = 0; j < appendSpaceNum; j++) {
                    bufferSpaces.append(" ");
                }
                this.fileBody.append(bufferSpaces.append(elements[i]).toString());
            } else {
                this.fileBody.append("  ").append(elements[i]);
            }

        }
        this.fileBody.append(newLineCh);
        this.fileBody.append("----------------------------------------------------------------------------------------------------------------------------");
        this.fileBody.append(newLineCh);
    }

    public void appendToBody(String line) {
        this.fileBody.append(newLineCh).append(line);
    }

    public void appendToFoot(String line) {
        this.fileFoot.append(newLineCh).append(line);
    }

    // 若文件存在则删除原文件

    private File createFile(String filePath, String fileName) throws IOException {
        File dir = new File(filePath);
        if (!dir.isDirectory() || !dir.exists()) {
            dir.mkdirs();
        }
        File tempFile = new File(filePath, fileName);
        if (tempFile.exists()) {
            tempFile.delete();
            tempFile.createNewFile();
        }
        return tempFile;
    }

    /**
     * 将字符串内容写入文件中 0--正常写入 -1 -- file为null
     */
    public int writeToFile() {

        int rtnFlag = -1;
        try {
            this.file = createFile(filePath, fileName);
        } catch (IOException e) {
            throw new RuntimeException(this.filePath + this.fileName + " 文件创建失败。", e);
        }
        if (file != null) {
            try {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(fileTitle);
                bw.newLine();
                bw.write(this.fileHead.toString());
                bw.newLine();
                bw.write(this.fileBody.toString());
                bw.newLine();
                bw.write(this.fileFoot.toString());
                bw.newLine();
                bw.write(this.otherLineContent.toString());
                bw.newLine();
                bw.flush();
                fw.close();
                bw.close();
            } catch (Exception e) {
                throw new RuntimeException(this.filePath + this.fileName + " 文件写入错误。", e);
            }
            rtnFlag = 0;
        }
        return rtnFlag;
    }
}
