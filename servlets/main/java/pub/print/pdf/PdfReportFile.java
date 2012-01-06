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
 * Time: 上午9:32
 * 注意   List中对象属性的数据类型只支持字符串和Double类型，Double类型均被格式化为两位小数
 * remark:  haiyuhuang  结清查询生成PDF文件方法添加
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
        this.isAutoOrSet = true;   // 默认自动打印
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
        this.isAutoOrSet = true;   // 默认自动打印
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
        titleFont = new Font(bfChinese, titleFontSize, Font.BOLD);// 设置字体大小
        cellFont = new Font(bfChinese, cellFontSize, Font.NORMAL);// 设置字体大小
        lastFont = new Font(bfChinese, lastFontSize, Font.NORMAL);// 设置字体大小
    }

    //PdfWriter docWriter, Document document, ByteArrayOutputStream ops
    public ByteArrayOutputStream generatePdfDataStream() throws Exception {
        if (dataList == null || dataList.size() == 0) {
            throw new RuntimeException("数据列表为空！");
        }
        document = new Document(this.pageSize);
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        docWriter = PdfWriter.getInstance(document, ops);
        document.setMargins(30f, 30f, 10f, 60f);

        // 初始化printHelper参数
        this.initPrintHelper();
        docWriter.setPageEvent(printHelper);
        document.open();
        docWriter.setViewerPreferences(PdfWriter.HideMenubar | PdfWriter.HideToolbar);
        table = this.createTable(tableWidths, 5f, 535, true, 0);
        //PDF标题
        this.setTitleCellToTable(40, 0, this.createTable(tableWidths, 30f, 535, true, 0));
        this.setAfterTitleCellToTable(0, this.createTable(new float[]{400f, 235f}, 10f, 535, true, 0), msgAligns, this.afterTitleMsg);
        //列标题
        this.setColumnsCellToTable(table);
        // 遍历数据列表，添加到表格
        this.addDataCellToTable(dataList, table);

        // 报表结束，显示附注
        if (lastMsgs != null) {
            for (String msg : lastMsgs) {
                this.addLastCellToTable(msg, 0, 20, table);
            }
        }
        document.add(table);
        // 自动打印
        if (isAutoOrSet) {
            docWriter.addJavaScript("this.print({bUI: false,bSilent: true,bShrinkToFit: false});" + "\r\nthis.closeDoc();");
        } else {  // 先设置后打印
            docWriter.addJavaScript("this.print(true); var msgHandlerObject = new Object();doc.onWillPrint = myOnMessage;this.hostContainer.messageHandler = msgHandlerObject;");
        }
        this.closeDocument();
        this.closePdfWriter();
        return ops;
    }

    //生成PDF文件  createBy：haiyuhuang
    public void savePDFfile() throws Exception {
        if (dataAryList == null || dataAryList.size() == 0) {
            throw new RuntimeException("数据列表为空！");
        }
        document = new Document(this.pageSize);
        OperatorManager opm = OnlineService.getOperatorManager();
        String strPath = PropertyManager.getProperty("REPEAT_PRNTPATH") + opm.getOperatorId() +  ".pdf";
        FileOutputStream out = new FileOutputStream(strPath);
        PdfWriter.getInstance(document, out);
        document.open();
        table = this.createTable(tableWidths, 5f, 535, true, 0);
        //PDF标题
        this.setTitleCellToTable(40, 0, this.createTable(tableWidths, 30f, 535, true, 0));
        this.setAfterTitleCellToTable(0, this.createTable(new float[]{400f, 235f}, 10f, 535, true, 0), msgAligns, this.afterTitleMsg);

        this.addDatacellToPDF(dataAryList,table);
        document.add(table);
        this.closeDocument();
    }

    //pdf内容注入 createBy： haiyuhuang
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

    // 初始化PrintHelper
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

    // 结尾附注信息，右对齐
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

    // 遍历数据列表，添加到表格
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

    // 设置列标题
    private void setColumnsCellToTable(PdfPTable pdfTable) {
        PdfPCell cell = null;
        for (String column : columns) {
            cell = new PdfPCell(new Paragraph(column, cellFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
        }
    }

    // 设置标题下方报表信息
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

    // 创建基本表格
    private PdfPTable createTable(float[] tableWidths, float beforeSpace, int totalWidth, boolean lockedWidth, int border) {
        PdfPTable newTable = new PdfPTable(tableWidths);// 建立一个pdf表格
        newTable.setSpacingBefore(beforeSpace);// 设置表格上面空白宽度
        newTable.setTotalWidth(totalWidth);// 设置表格的宽度
        newTable.setLockedWidth(lockedWidth);// 设置表格的宽度固定
        newTable.getDefaultCell().setBorder(border);//设置表格默认为无边框
        return newTable;
    }

    //PDF标题
    private void setTitleCellToTable(int fixedHeight, int border, PdfPTable table) throws DocumentException {

        PdfPCell cell = null;
        cell = new PdfPCell(new Paragraph(title, titleFont));
        cell.setFixedHeight(fixedHeight);//单元格高度
        cell.setColspan(columns.length);// 设置合并单元格的列数
        cell.setBorder(border);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        document.add(table);
    }

    // 增加单元格到Table
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
