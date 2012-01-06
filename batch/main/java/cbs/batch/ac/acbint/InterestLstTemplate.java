package cbs.batch.ac.acbint;

import cbs.batch.common.BatchResultFileHandler;

/**
 * ��Ϣ�嵥
 */
public class InterestLstTemplate {
    private BatchResultFileHandler fileHandler;
    private String fileName;
    private String title;                  // ����
    private String orgidt;              // ������
    private String orgnam;           // ������
    private String crndat;             // ҵ������
    private String filePath;
    private TableTextFile textFile;

    private String[] fields = new String[]{"�˺�   ", "����", "����", "������", "��Ϣ", "��Ʊ�׺�"};


    private String newLineCh = "\r\n";

    public InterestLstTemplate(String fileName) {
        this.fileName = fileName;
       this.fileHandler = new BatchResultFileHandler(fileName);
       textFile = new TableTextFile();
         textFile.setAlignFlags(new int[]{2,0,2,2,2,1});
        textFile.setElementWidths(new int[]{14,28,15,15,15,11});
       textFile.setHeadElements(fields);
    }



    // ���������
    public void appendFieldValues(String[] fieldValues) {
        textFile.addElementsToList(fieldValues);
    }

    private void initReportFile() {
        this.fileHandler.setFilePath(filePath);
        textFile.setTitle(title);
        textFile.setLinesAfterTitle(new String[]{"      ����: " + orgidt  + orgnam + "                                            ҵ������:" + crndat,
        "--------------------------------------------------------------------------------------------------------------------"});
         textFile.setOneLine("-----------------------------------------------------------------------------------------------------------------");
        fileHandler.appendToBody(textFile.toString());
    }

    // д���ļ�
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
