package cbs.batch.ac.acbint;

import cbs.batch.common.BatchResultFileHandler;

/**
 * 计息清单
 */
public class InterestLstTemplate {
    private BatchResultFileHandler fileHandler;
    private String fileName;
    private String title;                  // 标题
    private String orgidt;              // 机构号
    private String orgnam;           // 机构名
    private String crndat;             // 业务日期
    private String filePath;
    private TableTextFile textFile;

    private String[] fields = new String[]{"账号   ", "户名", "积数", "年利率", "利息", "传票套号"};


    private String newLineCh = "\r\n";

    public InterestLstTemplate(String fileName) {
        this.fileName = fileName;
       this.fileHandler = new BatchResultFileHandler(fileName);
       textFile = new TableTextFile();
         textFile.setAlignFlags(new int[]{2,0,2,2,2,1});
        textFile.setElementWidths(new int[]{14,28,15,15,15,11});
       textFile.setHeadElements(fields);
    }



    // 添加数据行
    public void appendFieldValues(String[] fieldValues) {
        textFile.addElementsToList(fieldValues);
    }

    private void initReportFile() {
        this.fileHandler.setFilePath(filePath);
        textFile.setTitle(title);
        textFile.setLinesAfterTitle(new String[]{"      机构: " + orgidt  + orgnam + "                                            业务日期:" + crndat,
        "--------------------------------------------------------------------------------------------------------------------"});
         textFile.setOneLine("-----------------------------------------------------------------------------------------------------------------");
        fileHandler.appendToBody(textFile.toString());
    }

    // 写入文件
    public void writeToReport() {
        initReportFile();
        fileHandler.writeToFile();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgidt() {
        return orgidt;
    }

    public void setOrgidt(String orgidt) {
        this.orgidt = orgidt;
    }

    public String getOrgnam() {
        return orgnam;
    }

    public void setOrgnam(String orgnam) {
        this.orgnam = orgnam;
    }

    public String getCrndat() {
        return crndat;
    }

    public void setCrndat(String crndat) {
        this.crndat = crndat;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }
}
