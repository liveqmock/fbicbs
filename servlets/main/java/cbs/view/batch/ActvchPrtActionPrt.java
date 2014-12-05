package cbs.view.batch;

import cbs.common.IbatisManager;
import cbs.common.SystemService;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActvhhMapper;
import cbs.repository.account.maininfo.dao.ActvthMapper;
import cbs.repository.account.maininfo.model.ActvchForGlDRpt;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actorg;
import cbs.repository.platform.dao.PtenudetailMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lichao.W
 * Date: 2014/11/11
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ActvchPrtActionPrt implements Serializable {
    private static final Log logger = LogFactory.getLog(ActvchPrtActionPrt.class);
    private static final String BASE_FONT = PropertyManager.getProperty("print.pdf.font");
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private ActorgMapper orgmap;
    private ActvthMapper vchmap;
    private ActvhhMapper vhhmap;
    private PtenudetailMapper enudetail;
    private String ptdate = "";
    private String glcode = "";

    @PostConstruct
    public void init() {

    }

    public String vchDataList() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        DecimalFormat df01 = new DecimalFormat("###,###");

        String sysdate = SystemService.getBizDate10();
        String reportdate = sysdate;
        String orgidt = "";
        String orgnam = "";
        //ptdate = ptdate=="" ? reportdate : ptdate;
        if (ptdate == null || "".equals(ptdate)) {
            ptdate = "2014-11-16";
        }
        String titleOrg = "";   //������
        String titleCurDate = "";//����
        String titleGlc = "";   //��Ŀ
        String titleCur = "";   //�ұ�
        String title2Cell1 = "                                           ";
        String title1 = title2Cell1 + "                                 ��  Ŀ  ��  ��  ��";
        String title3 = "��   ��Ŀ           ��  �跽����   ��               �跽���                   ��    �������� ��                �������                  ����";
        String title4 = "���ֽ�              ��";
        String title5 = "��ת��              ��";
        String title6 = "������              ��";
        String title7 = "���ϼ�              ��";
        String nodtTitle = "                     0 ��                                              0.00��                     0��                                              0.00��";
        String footer = title2Cell1 + "���ˣ�                      ���ˣ�                        �Ʊ�                         ";
        String topplan = "�������������ש����������ש����������������������ש����������ש�������������������������";
        String middleplan = "�������������贈���������贈���������������������贈���������贈����������������������";
        String bottomplan = "�������������ߩ����������ߩ����������������������ߩ����������ߩ�������������������������";
        String title4s = "";
        String title5s = "";
        String title6s = "";
        String title7s = "";
        String title0s = "";
        String title00s = "";
        try {
            orgmap = session.getMapper(ActorgMapper.class);
            vhhmap = session.getMapper(ActvhhMapper.class);
            PdfPTable table = new PdfPTable(new float[]{1000f});// ����һ��pdf���
            table.setSpacingBefore(160f);// ���ñ������հ׿��
            table.setTotalWidth(835);// ���ñ��Ŀ��
            table.setLockedWidth(false);// ���ñ��Ŀ�ȹ̶�
            table.getDefaultCell().setBorder(0);//���ñ��Ĭ��Ϊ�ޱ߿�
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            Font headFont1 = new Font(bfChinese, 10, Font.NORMAL);// ���������С
            List<Actorg> orgLst = orgmap.selectForActvhh();
            if (orgLst.size() == 0) {
                System.out.print("��Ŀ�սᵥ�������������ɡ�");
                logger.info("��Ŀ�սᵥ�������������ɡ�");
                return null;
            }
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                List<ActvchForGlDRpt> glcurLst = vhhmap.selectForGlCur(orgidt, ptdate);
                Iterator it1 = glcurLst.iterator();
                List<PdfPTable> tables = new ArrayList<PdfPTable>();
                int tabln = 1;
                while (it1.hasNext()) {
                    ActvchForGlDRpt glcur = (ActvchForGlDRpt) it1.next();
                    String glcode = glcur.getGlcode();
                    String curcde = glcur.getCurcde();
                    String glcnam = glcur.getGlcnam();
                    String curnmc = glcur.getCurnmc();
                    titleOrg = ReportHelper.getLeftSpaceStr("������" + orgnam, 48);
                    titleCurDate = ReportHelper.getLeftSpaceStr("���ڣ�" + ptdate, 40);
                    titleGlc = ReportHelper.getLeftSpaceStr("��Ŀ��" + glcode + " " + glcnam, 48);
                    titleCur = ReportHelper.getLeftSpaceStr("�ұ�" + curnmc, 38);
                    title0s = titleOrg + titleCurDate;
                    title00s = titleGlc + titleCur;
                    List<ActvchForGlDRpt> gldrptLst = vhhmap.selectForGlDRpt(orgidt, curcde, glcode, ptdate);
                    Iterator it2 = gldrptLst.iterator();
                    int totalDcunt = 0;
                    double totalDamt = 0;
                    int totalCcunt = 0;
                    double totalCamt = 0;
                    while (it2.hasNext()) {
                        ActvchForGlDRpt gldrpt = (ActvchForGlDRpt) it2.next();
                        String rvslbl = gldrpt.getRvslbl();
                        int intDcun = Integer.parseInt(gldrpt.getDcunt());
                        double doubleDamt = Double.parseDouble(gldrpt.getDamt()) * -1 / 100;
                        int intCcunt = Integer.parseInt(gldrpt.getCcunt());
                        double doubleCamt = Double.parseDouble(gldrpt.getCamt()) / 100;
                        String dcunt = ReportHelper.getRightSpaceStr(df01.format(intDcun), 23) + "��";   //�跽����
                        String damt = ReportHelper.getRightSpaceStr(df0.format(doubleDamt), 47) + "��";     //�跽���
                        String ccunt = ReportHelper.getRightSpaceStr(df01.format(intCcunt), 23) + "��";   //��������
                        String camt = ReportHelper.getRightSpaceStr(df0.format(doubleCamt), 47) + "��";     //�������
                        if (rvslbl.equals("1")) {
                            title4s = title4 + dcunt + damt + ccunt + camt + "��";
                        } else if (rvslbl.equals("2")) {
                            title5s = title5 + dcunt + damt + ccunt + camt + "ӡ";
                        } else{
                            title6s = title6 + dcunt + damt + ccunt + camt + "��";
                        }
                        totalDcunt += intDcun;
                        totalDamt += doubleDamt;
                        totalCcunt += intCcunt;
                        totalCamt += doubleCamt;
                    }
                    String strTotalDcunt = ReportHelper.getRightSpaceStr(df01.format(totalDcunt), 23) + "��";
                    String strTotalDamt = ReportHelper.getRightSpaceStr(df0.format(totalDamt), 47) + "��";
                    String strTotalCcunt = ReportHelper.getRightSpaceStr(df01.format(totalCcunt), 23) + "��";
                    String strTotalCamt = ReportHelper.getRightSpaceStr(df0.format(totalCamt), 47) + "��";
                    if ("".equals(title6s)){
                        title6s = title6 + nodtTitle + "��";
                    }
                    title7s = title7 + strTotalDcunt + strTotalDamt + strTotalCcunt + strTotalCamt;
                    String rows[] = new String[]{title1, title2Cell1, title0s, title00s, topplan, title3, middleplan, title4s,
                            middleplan, title5s, middleplan, title6s, middleplan, title7s, bottomplan, footer,title2Cell1,title2Cell1,
                            title2Cell1,title2Cell1,title2Cell1,title2Cell1,title2Cell1};

                    PdfPCell cell = null;
                    for (String row : rows) {
                        cell = new PdfPCell(new Paragraph(row, headFont1));
                        cell.setBorder(0);
                        cell.setFixedHeight(15);//��Ԫ��߶�
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_LEFT);
                        table.addCell(cell);
                    }
                    int tablnum = tabln % 2;
                    if (glcurLst.size()==1){
                        tables.add(table);
                    }else if (glcurLst.size()==2){
                        if (tablnum%2==0){
                            tables.add(table);
                        }
                    }
                    tabln++;
                }
                printPdfTableLst(tables);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("�����սᵥ���ݳ���");
        } finally {
            session.close();
        }
        return null;
    }

    public void tmpPath() {
        PdfReader reader = null;
        //String path = ActvchPrtAction.class.getClassLoader().getResource("/").getPath();
        //String path2 = FacesContext.class.getResource("/").getPath();
        InputStream isr = FacesContext.class.getResourceAsStream("/../../batchTmp/rijiedan.pdf");
        //System.out.println(path+"====="+path2 + "======"+isr);
        try {
            reader = new PdfReader(isr);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfStamper ps = new PdfStamper(reader, baos);
            AcroFields fields = ps.getAcroFields();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private void printPdfTableLst(List<PdfPTable> tableLst) throws DocumentException, IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        Document document = new Document(PageSize.A4, 16, 16, 36, 90);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, bos);
        writer.setPageEvent(new PdfPageEventHelper());
        document.open();
        for (PdfPTable table : tableLst) {
            document.newPage();
            document.add(table);
        }
        writer.addJavaScript("this.print({bUI: false,bSilent: true,bShrinkToFit: false});" + "\r\nthis.closeDoc();");
        document.close();
        response.reset();
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline");
        response.setContentLength(bos.size());
        response.setHeader("Cache-Control", "max-age=30");
        bos.writeTo(out);
        out.flush();
        out.close();
        ctx.responseComplete();
    }

/*// ��ҳ��ӡ
    private void printPdfTable(PdfPTable table) throws DocumentException, IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        Document document = new Document(PageSize.A4, 16, 16, 36, 90);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, bos);
        writer.setPageEvent(new PdfPageEventHelper());
        document.open();
        document.add(table);
        writer.addJavaScript("this.print({bUI: false,bSilent: true,bShrinkToFit: false});" + "\r\nthis.closeDoc();");
        document.close();
        response.reset();
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline");
        response.setContentLength(bos.size());
        response.setHeader("Cache-Control", "max-age=30");
        bos.writeTo(out);
        out.flush();
        out.close();
        ctx.responseComplete();
    }*/
    // = = = = = = = = = = = = = = = = = = = = = = = = =


    public String getPtdate() {
        return ptdate;
    }

    public void setPtdate(String ptdate) {
        this.ptdate = ptdate;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public ActorgMapper getOrgmap() {
        return orgmap;
    }

    public void setOrgmap(ActorgMapper orgmap) {
        this.orgmap = orgmap;
    }

    public ActvthMapper getVchmap() {
        return vchmap;
    }

    public void setVchmap(ActvthMapper vchmap) {
        this.vchmap = vchmap;
    }

    public PtenudetailMapper getEnudetail() {
        return enudetail;
    }

    public void setEnudetail(PtenudetailMapper enudetail) {
        this.enudetail = enudetail;
    }

    public ActvhhMapper getVhhmap() {
        return vhhmap;
    }

    public void setVhhmap(ActvhhMapper vhhmap) {
        this.vhhmap = vhhmap;
    }
}
