package pub.print.pdf;

import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.utils.PropertyManager;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pub.platform.security.OperatorManager;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-4-21
 * Time: ����9:32
 * ע��   List�ж������Ե���������ֻ֧���ַ�����Double���ͣ�Double���;�����ʽ��Ϊ��λС��
 * remark:  haiyuhuang  �����ѯ����PDF�ļ��������
 */
public class PdfReportFile {
    private String title;
    private String[] afterTitleMsg;
    private String[] columns;
    private String[] fields;
    private int[] aligns;
    private Rectangle pageSize;
    private PdfPTable table;
    private float[] tableWidths;
    private List<?> dataList;
    private BaseFont bfChinese;
    private int titleFontSize;
    private int cellFontSize;
    private int lastFontSize;
    private Font titleFont;
    private Font cellFont;
    private Font lastFont;
    private String[] lastMsgs;
    private int[] msgAligns;
    private boolean isAutoOrSet;
    private Document document;
    private PdfWriter docWriter;
    private String sysPdfFont = SystemService.getPdfFont();
    private PdfPrintHelper printHelper;
    //createBy: haiyuhuang
    private List<ArrayList> dataAryList;
    //createBy: haiyuhuang
    public PdfReportFile(String title, String[] afterTitleMsg, int[] msgAligns, String[] columns, String[] fields, int[] aligns,
                         Rectangle pageSize, float[] tableWidths, List<ArrayList> dataList) {
        printHelper = new PdfPrintHelper();
        this.title = title;
        this.afterTitleMsg = afterTitleMsg;
        this.columns = columns;
        this.fields = fields;
        this.aligns = aligns;
        this.pageSize = pageSize;
        this.tableWidths = tableWidths;
        this.dataAryList = dataList;
        this.lastMsgs = lastMsgs;
        this.msgAligns = msgAligns;
        this.isAutoOrSet = true;   // Ĭ���Զ���ӡ
        this.titleFontSize = 14;
        this.cellFontSize = 12;
        this.lastFontSize = 10;
        try {
            bfChinese = BaseFont.createFont(sysPdfFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleFont = new Font(bfChinese, titleFontSize, Font.BOLD);
        cellFont = new Font(bfChinese, cellFontSize, Font.NORMAL);
        lastFont = new Font(bfChinese, lastFontSize, Font.NORMAL);
    }

    public PdfReportFile(String title, String[] afterTitleMsg, int[] msgAligns, String[] columns, String[] fields, int[] aligns,
                         Rectangle pageSize, float[] tableWidths, List<?> dataList, String[] lastMsgs) {
        printHelper = new PdfPrintHelper();
        this.title = title;
        this.afterTitleMsg = afterTitleMsg;
        this.columns = columns;
        this.fields = fields;
        this.aligns = aligns;
        this.pageSize = pageSize;
        this.tableWidths = tableWidths;
        this.dataList = dataList;
        this.lastMsgs = lastMsgs;
        this.msgAligns = msgAligns;
        this.isAutoOrSet = true;   // Ĭ���Զ���ӡ
        this.titleFontSize = 14;
        this.cellFontSize = 12;
        this.lastFontSize = 10;
        try {
            bfChinese = BaseFont.createFont(sysPdfFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleFont = new Font(bfChinese, titleFontSize, Font.BOLD);
        cellFont = new Font(bfChinese, cellFontSize, Font.NORMAL);
        lastFont = new Font(bfChinese, lastFontSize, Font.NORMAL);
    }

    public PdfReportFile(String title, String[] afterTitleMsg, int[] msgAligns, String[] columns, String[] fields, int[] aligns,
                         Rectangle pageSize, float[] tableWidths, List<?> dataList, String[] lastMsgs,
                         int titleFontSize, int cellFontSize, int lastFontSize) {
        this(title, afterTitleMsg, msgAligns, columns, fields, aligns, pageSize, tableWidths, dataList, lastMsgs);
        this.titleFontSize = titleFontSize > 1 ? titleFontSize : 14;
        this.cellFontSize = cellFontSize > 1 ? cellFontSize : 12;
        this.lastFontSize = lastFontSize > 1 ? lastFontSize : 10;
        titleFont = new Font(bfChinese, titleFontSize, Font.BOLD);// ���������С
        cellFont = new Font(bfChinese, cellFontSize, Font.NORMAL);// ���������С
        lastFont = new Font(bfChinese, lastFontSize, Font.NORMAL);// ���������С
    }

    //PdfWriter docWriter, Document document, ByteArrayOutputStream ops
    public ByteArrayOutputStream generatePdfDataStream() throws Exception {
        if (dataList == null || dataList.size() == 0) {
            throw new RuntimeException("�����б�Ϊ�գ�");
        }
        document = new Document(this.pageSize);
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        docWriter = PdfWriter.getInstance(document, ops);
        document.setMargins(30f, 30f, 10f, 60f);

        // ��ʼ��printHelper����
        this.initPrintHelper();
        docWriter.setPageEvent(printHelper);
        document.open();
        docWriter.setViewerPreferences(PdfWriter.HideMenubar | PdfWriter.HideToolbar);
        table = this.createTable(tableWidths, 5f, 535, true, 0);
        //PDF����
        this.setTitleCellToTable(40, 0, this.createTable(tableWidths, 30f, 535, true, 0));
        this.setAfterTitleCellToTable(0, this.createTable(new float[]{400f, 235f}, 10f, 535, true, 0), msgAligns, this.afterTitleMsg);
        //�б���
        this.setColumnsCellToTable(table);
        // ���������б���ӵ����
        this.addDataCellToTable(dataList, table);

        // �����������ʾ��ע
        if (lastMsgs != null) {
            for (String msg : lastMsgs) {
                this.addLastCellToTable(msg, 0, 20, table);
            }
        }
        document.add(table);
        // �Զ���ӡ
        if (isAutoOrSet) {
            docWriter.addJavaScript("this.print({bUI: false,bSilent: true,bShrinkToFit: false});" + "\r\nthis.closeDoc();");
        } else {  // �����ú��ӡ
            docWriter.addJavaScript("this.print(true); var msgHandlerObject = new Object();doc.onWillPrint = myOnMessage;this.hostContainer.messageHandler = msgHandlerObject;");
        }
        this.closeDocument();
        this.closePdfWriter();
        return ops;
    }

    //����PDF�ļ�  createBy��haiyuhuang
    public void savePDFfile() throws Exception {
        if (dataAryList == null || dataAryList.size() == 0) {
            throw new RuntimeException("�����б�Ϊ�գ�");
        }
        document = new Document(this.pageSize);
        OperatorManager opm = OnlineService.getOperatorManager();
        String strPath = PropertyManager.getProperty("REPEAT_PRNTPATH") + opm.getOperatorId() +  ".pdf";
        FileOutputStream out = new FileOutputStream(strPath);
        PdfWriter.getInstance(document, out);
        document.open();
        table = this.createTable(tableWidths, 5f, 535, true, 0);
        //PDF����
        this.setTitleCellToTable(40, 0, this.createTable(tableWidths, 30f, 535, true, 0));
        this.setAfterTitleCellToTable(0, this.createTable(new float[]{400f, 235f}, 10f, 535, true, 0), msgAligns, this.afterTitleMsg);

        this.addDatacellToPDF(dataAryList,table);
        document.add(table);
        this.closeDocument();
    }

    //pdf����ע�� createBy�� haiyuhuang
    private void addDatacellToPDF(List<ArrayList> dataList, PdfPTable pdfTable) {
        PdfPCell cell = null;
        for (ArrayList<String> dtlist:dataList) {
            for (int i=0;i<dtlist.size();i++) {
                String cont = dtlist.get(i);
                cell = new PdfPCell(new Paragraph(cont,cellFont));
                cell.setBorder(0);
                table.addCell(cell);
            }
        }
    }

    // ��ʼ��PrintHelper
    private void initPrintHelper() {
        printHelper.setAddColumn(true);
        printHelper.setCellFont(cellFont);
        printHelper.setColumns(columns);
        printHelper.setTable(this.createTable(tableWidths, 10f, 535, true, 0));
        printHelper.setTableWidths(tableWidths);
        printHelper.setTitle(title);
        printHelper.setTitleFont(titleFont);
        printHelper.setAfterTableWidths(new float[]{400f, 235f});
        printHelper.setLastFont(lastFont);
        printHelper.setAfterTitleMsg(afterTitleMsg);
        printHelper.setMsgAligns(msgAligns);
    }

    // ��β��ע��Ϣ���Ҷ���
    private void addLastCellToTable(String content, int border, int height, PdfPTable pdfTable) {
        PdfPCell cell = null;
        cell = new PdfPCell(new Paragraph(content, lastFont));
        cell.setBorder(border);
        cell.setFixedHeight(height);
        cell.setColspan(columns.length);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    // ���������б���ӵ����
    private void addDataCellToTable(List<?> dataList, PdfPTable pdfTable) throws Exception {
        PdfPCell cell = null;
        int fieldLength = fields.length;
        for (Object model : dataList) {
            for (int i = 0; i < fieldLength; i++) {
                String field = fields[i];
                Field classField = model.getClass().getDeclaredField(field);
                if (classField != null) {
                    classField.setAccessible(true);
                    if (classField.getType() == String.class) {
                        cell = new PdfPCell(new Paragraph((String) classField.get(model), cellFont));
                    } else if (classField.getType() == Double.class) {
                        cell = new PdfPCell(new Paragraph(String.format("%.2f", (Double) classField.get(model)), cellFont));
                    }
                    cell.setHorizontalAlignment(aligns[i]);
                    table.addCell(cell);
                }
            }
        }
    }

    // �����б���
    private void setColumnsCellToTable(PdfPTable pdfTable) {
        PdfPCell cell = null;
        for (String column : columns) {
            cell = new PdfPCell(new Paragraph(column, cellFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
        }
    }

    // ���ñ����·�������Ϣ
    private void setAfterTitleCellToTable(int border, PdfPTable pdfTable, int[] aligns, String... afterTitleMsg) throws DocumentException {

        PdfPCell cell = null;
        for (int i = 0; i < aligns.length; i++) {
            cell = new PdfPCell(new Paragraph(afterTitleMsg[i], lastFont));
            cell.setBorder(border);
            cell.setPaddingBottom(10);
            cell.setHorizontalAlignment(aligns[i]);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfTable.addCell(cell);
        }
        document.add(pdfTable);
    }

    // �����������
    private PdfPTable createTable(float[] tableWidths, float beforeSpace, int totalWidth, boolean lockedWidth, int border) {
        PdfPTable newTable = new PdfPTable(tableWidths);// ����һ��pdf���
        newTable.setSpacingBefore(beforeSpace);// ���ñ������հ׿��
        newTable.setTotalWidth(totalWidth);// ���ñ��Ŀ��
        newTable.setLockedWidth(lockedWidth);// ���ñ��Ŀ�ȹ̶�
        newTable.getDefaultCell().setBorder(border);//���ñ��Ĭ��Ϊ�ޱ߿�
        return newTable;
    }

    //PDF����
    private void setTitleCellToTable(int fixedHeight, int border, PdfPTable table) throws DocumentException {

        PdfPCell cell = null;
        cell = new PdfPCell(new Paragraph(title, titleFont));
        cell.setFixedHeight(fixedHeight);//��Ԫ��߶�
        cell.setColspan(columns.length);// ���úϲ���Ԫ�������
        cell.setBorder(border);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);// ��������ˮƽ������ʾ
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        document.add(table);
    }

    // ���ӵ�Ԫ��Table
    private void addCellToTable(PdfPCell cell, PdfPTable pdfTable) {
        if (pdfTable != null) {
            pdfTable.addCell(cell);
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAfterTitleMsg() {
        return afterTitleMsg;
    }

    public void setAfterTitleMsg(String[] afterTitleMsg) {
        this.afterTitleMsg = afterTitleMsg;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public int[] getAligns() {
        return aligns;
    }

    public void setAligns(int[] aligns) {
        this.aligns = aligns;
    }

    public Rectangle getPageSize() {
        return pageSize;
    }

    public void setPageSize(Rectangle pageSize) {
        this.pageSize = pageSize;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public PdfPTable getTable() {
        return table;
    }

    public void setTable(PdfPTable table) {
        this.table = table;
    }

    public float[] getTableWidths() {
        return tableWidths;
    }

    public void setTableWidths(float[] tableWidths) {
        this.tableWidths = tableWidths;
    }

    public String[] getLastMsgs() {
        return lastMsgs;
    }

    public void setLastMsgs(String[] lastMsgs) {
        this.lastMsgs = lastMsgs;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public boolean isAutoOrSet() {
        return isAutoOrSet;
    }

    public void setAutoOrSet(boolean autoOrSet) {
        isAutoOrSet = autoOrSet;
    }

    public String getSysPdfFont() {
        return sysPdfFont;
    }

    public void setSysPdfFont(String sysPdfFont) {
        this.sysPdfFont = sysPdfFont;
    }

    public void closeDocument() {
        this.document.close();
    }

    public void closePdfWriter() {
        this.docWriter.close();
    }
}
