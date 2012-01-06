package pub.print.pdf;

import cbs.common.SystemService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * pdf事件触发类
 */
public class PdfPrintHelper extends PdfPageEventHelper {
    private PdfTemplate pdfTemplate;
    private BaseFont baseFont;
    private boolean isAddColumn;
    private String[] columns;
    private Font cellFont;
    private int cellFontSize;
    private PdfPTable table;
    // 基本表格参数
    private float[] tableWidths;
    // 标题参数
    private String title;
    private Font titleFont;

    // 标题下方信息参数
    private float[] afterTableWidths;
    private Font lastFont;
    private String[] afterTitleMsg;
    private int[] msgAligns;

    private boolean isAddPageNo = false;

    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            pdfTemplate = writer.getDirectContent().createTemplate(100, 100);
            baseFont = BaseFont.createFont(SystemService.getPdfFont(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            isAddColumn = true; // 默认为添加表头
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
       // if(document.getPageNumber() > 1 && this.isAddColumn) {
        if(document.getPageNumber() >= 2) {
        try {
         //PDF标题
       // document.add(this.setTitleCellToTable(40, 0, this.createTable(tableWidths, 30f, 535, true, 0)));
      //  document.add(this.setAffterTitleCellToTable(0, this.createTable(afterTableWidths, 10f, 535, true, 0), msgAligns, this.afterTitleMsg));
        //列标题
        PdfPTable columnTable = this.createTable(tableWidths, 30f, 535, true, 0);
        this.setColumnsCellToTable(columnTable);
        document.add(columnTable);
       /* this.setColumnsCellToTable(table);
        document.add(table);*/
        } catch (DocumentException e) {
            e.printStackTrace();
        }
      }
    }

    public void onEndPage(PdfWriter writer, Document document) {
         if(isAddPageNo) {
        //在每页结束的时候把“第x页”信息写道模版指定位置
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        cb.restoreState();
        String text = "第" + writer.getPageNumber() + "页,共";
        cb.beginText();
        cb.setFontAndSize(baseFont, 8);
        cb.setTextMatrix(270, 36);//定位“第x页,共” 在具体的页面调试时候需要更改这xy的坐标
        cb.showText(text);
        cb.endText();
        cb.addTemplate(pdfTemplate, 302,36);//定位“y页” 在具体的页面调试时候需要更改这xy的坐标

        cb.saveState();
        cb.stroke();
        cb.restoreState();
        cb.closePath();
         }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        if(isAddPageNo) {
        //关闭document的时候获取总页数，并把总页数按模版写道之前预留的位置
        pdfTemplate.beginText();
        pdfTemplate.setFontAndSize(baseFont, 8);
        pdfTemplate.showText(Integer.toString(writer.getPageNumber() - 1) + "页");
        pdfTemplate.endText();
        pdfTemplate.closePath();
        }
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
    private PdfPTable setTitleCellToTable(int fixedHeight, int border, PdfPTable table) throws DocumentException {

        PdfPCell cell = null;
        cell = new PdfPCell(new Paragraph(title, titleFont));
        cell.setFixedHeight(fixedHeight);//单元格高度
        cell.setColspan(columns.length);// 设置合并单元格的列数
        cell.setBorder(border);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        return table;
    }

    // 设置标题下方报表信息
    private PdfPTable setAffterTitleCellToTable( int border, PdfPTable pdfTable,int[] aligns,String... afterTitleMsg) throws DocumentException {

        PdfPCell cell = null;
        for(int i = 0; i< aligns.length; i++){
        cell = new PdfPCell(new Paragraph(afterTitleMsg[i], lastFont));
        cell.setBorder(border);
        cell.setPaddingBottom(10);
        cell.setHorizontalAlignment(aligns[i]);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfTable.addCell(cell);
        }
        return pdfTable;
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

    public PdfTemplate getPdfTemplate() {
        return pdfTemplate;
    }

    public void setPdfTemplate(PdfTemplate pdfTemplate) {
        this.pdfTemplate = pdfTemplate;
    }

    public BaseFont getBaseFont() {
        return baseFont;
    }

    public void setBaseFont(BaseFont baseFont) {
        this.baseFont = baseFont;
    }

    public void setAddColumn(boolean isAddColumn) {
        this.isAddColumn = isAddColumn;
    }

    public boolean isAddColumn() {
        return isAddColumn;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public Font getCellFont() {
        return cellFont;
    }

    public void setCellFont(Font cellFont) {
        this.cellFont = cellFont;
    }

    public int getCellFontSize() {
        return cellFontSize;
    }

    public void setCellFontSize(int cellFontSize) {
        this.cellFontSize = cellFontSize;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public Font getLastFont() {
        return lastFont;
    }

    public void setLastFont(Font lastFont) {
        this.lastFont = lastFont;
    }

    public float[] getAfterTableWidths() {
        return afterTableWidths;
    }

    public void setAfterTableWidths(float[] afterTableWidths) {
        this.afterTableWidths = afterTableWidths;
    }

    public String[] getAfterTitleMsg() {
        return afterTitleMsg;
    }

    public void setAfterTitleMsg(String[] afterTitleMsg) {
        this.afterTitleMsg = afterTitleMsg;
    }

    public int[] getMsgAligns() {
        return msgAligns;
    }

    public void setMsgAligns(int[] msgAligns) {
        this.msgAligns = msgAligns;
    }

    public boolean isAddPageNo() {
        return isAddPageNo;
    }

    public void setAddPageNo(boolean addPageNo) {
        isAddPageNo = addPageNo;
    }
}
