package pub.print.pdf;

import cbs.common.SystemService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * pdf�¼�������
 */
public class PdfPrintHelper extends PdfPageEventHelper {
    private PdfTemplate pdfTemplate;
    private BaseFont baseFont;
    private boolean isAddColumn;
    private String[] columns;
    private Font cellFont;
    private int cellFontSize;
    private PdfPTable table;
    // ����������
    private float[] tableWidths;
    // �������
    private String title;
    private Font titleFont;

    // �����·���Ϣ����
    private float[] afterTableWidths;
    private Font lastFont;
    private String[] afterTitleMsg;
    private int[] msgAligns;

    private boolean isAddPageNo = false;

    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            pdfTemplate = writer.getDirectContent().createTemplate(100, 100);
            baseFont = BaseFont.createFont(SystemService.getPdfFont(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            isAddColumn = true; // Ĭ��Ϊ��ӱ�ͷ
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
       // if(document.getPageNumber() > 1 && this.isAddColumn) {
        if(document.getPageNumber() >= 2) {
        try {
         //PDF����
       // document.add(this.setTitleCellToTable(40, 0, this.createTable(tableWidths, 30f, 535, true, 0)));
      //  document.add(this.setAffterTitleCellToTable(0, this.createTable(afterTableWidths, 10f, 535, true, 0), msgAligns, this.afterTitleMsg));
        //�б���
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
        //��ÿҳ������ʱ��ѡ���xҳ����Ϣд��ģ��ָ��λ��
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        cb.restoreState();
        String text = "��" + writer.getPageNumber() + "ҳ,��";
        cb.beginText();
        cb.setFontAndSize(baseFont, 8);
        cb.setTextMatrix(270, 36);//��λ����xҳ,���� �ھ����ҳ�����ʱ����Ҫ������xy������
        cb.showText(text);
        cb.endText();
        cb.addTemplate(pdfTemplate, 302,36);//��λ��yҳ�� �ھ����ҳ�����ʱ����Ҫ������xy������

        cb.saveState();
        cb.stroke();
        cb.restoreState();
        cb.closePath();
         }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        if(isAddPageNo) {
        //�ر�document��ʱ���ȡ��ҳ����������ҳ����ģ��д��֮ǰԤ����λ��
        pdfTemplate.beginText();
        pdfTemplate.setFontAndSize(baseFont, 8);
        pdfTemplate.showText(Integer.toString(writer.getPageNumber() - 1) + "ҳ");
        pdfTemplate.endText();
        pdfTemplate.closePath();
        }
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
    private PdfPTable setTitleCellToTable(int fixedHeight, int border, PdfPTable table) throws DocumentException {

        PdfPCell cell = null;
        cell = new PdfPCell(new Paragraph(title, titleFont));
        cell.setFixedHeight(fixedHeight);//��Ԫ��߶�
        cell.setColspan(columns.length);// ���úϲ���Ԫ�������
        cell.setBorder(border);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);// ��������ˮƽ������ʾ
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        return table;
    }

    // ���ñ����·�������Ϣ
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

     // �����б���
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
