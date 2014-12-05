package cbs.view.batch;

import cbs.common.IbatisManager;
import cbs.common.SystemService;
import cbs.repository.account.maininfo.dao.ActvhhMapper;
import cbs.repository.account.maininfo.dao.ActvthMapper;
import cbs.repository.account.maininfo.model.ActvchForGlDRpt;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actorg;
import cbs.repository.platform.dao.PtenudetailMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class ActvchPrtActionbak implements Serializable {
    private static final Log logger = LogFactory.getLog(ActvchPrtActionbak.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private ActorgMapper orgmap;
    private ActvthMapper vchmap;
    private ActvhhMapper vhhmap;
    private PtenudetailMapper enudetail;
    private String ptdate = "";
    private String glcode2 = "";

    @PostConstruct
    public void init() {

    }

    public String vchDataList() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        DecimalFormat df01 = new DecimalFormat("###,###");
        SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc = new GregorianCalendar();
        Date sysdate = SystemService.sysDate11();
        gc.setTime(sysdate);
        gc.add(5, -1);//默认应该是业务日期的前一天
        String reportdate = sdfdate10.format(gc.getTime());
        String orgidt = "";
        String orgnam = "";
        if (ptdate == null || "".equals(ptdate)) {
            ptdate = reportdate;
        }
        try {
            PdfReader reader = null;
            InputStream isr = FacesContext.class.getResourceAsStream("/../../batchTmp/vchprt.pdf");
            reader = new PdfReader(isr);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfStamper ps = new PdfStamper(reader, baos);
            AcroFields fields = ps.getAcroFields();
            orgmap = session.getMapper(ActorgMapper.class);
            vhhmap = session.getMapper(ActvhhMapper.class);
            List<Actorg> orgLst = orgmap.selectForActvhh();
            if (orgLst.size() == 0) {
                System.out.print("科目日结单报表无数据生成。");
                logger.info("科目日结单报表无数据生成。");
                return null;
            }
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                List<ActvchForGlDRpt> glcurLst = vhhmap.selectForGlCur(orgidt, ptdate);//得到表的个数
                Iterator it1 = glcurLst.iterator();
                if (!"".equals(glcode2)) {
                    glcurLst.get(0).setGlcode(glcode2);
                }
                if (glcurLst.get(0) != null) {
                    ActvchForGlDRpt glcur = (ActvchForGlDRpt) glcurLst.get(0);
                    String glcode = glcur.getGlcode();
                    String curcde = glcur.getCurcde();
                    String glcnam = glcur.getGlcnam();
                    String curnmc = glcur.getCurnmc();
                    fields.setField("org1", orgnam);     //机构号
                    fields.setField("dat1", ptdate); //日期
                    fields.setField("glc1", glcode + glcnam);  //科目
                    fields.setField("cur1", curnmc);         //币别

                    List<ActvchForGlDRpt> gldrptLst = vhhmap.selectForGlDRpt(orgidt, curcde, glcode, ptdate);
                    Iterator it2 = gldrptLst.iterator();
                    int totalDcunt = 0;
                    double totalDamt = 0.00;
                    int totalCcunt = 0;
                    double totalCamt = 0.00;
                    while (it2.hasNext()) {
                        ActvchForGlDRpt gldrpt = (ActvchForGlDRpt) it2.next();
                        String rvslbl = gldrpt.getRvslbl();
                        int intDcun = Integer.parseInt(gldrpt.getDcunt());
                        double doubleDamt = Double.parseDouble(gldrpt.getDamt()) * -1 / 100;
                        int intCcunt = Integer.parseInt(gldrpt.getCcunt());
                        double doubleCamt = Double.parseDouble(gldrpt.getCamt()) / 100;
                        String dcunt = df01.format(intDcun);     //借方笔数
                        String damt = df0.format(doubleDamt);     //借方金额
                        String ccunt = df01.format(intCcunt);    //贷方笔数
                        String camt = df0.format(doubleCamt);     //贷方金额
                        if (rvslbl.equals("1")) {
                            fields.setField("fill_1", dcunt);
                            fields.setField("fill_2", damt);
                            fields.setField("fill_3", ccunt);
                            fields.setField("fill_4", camt);
                        } else if (rvslbl.equals("2")) {
                            fields.setField("fill_5", dcunt);
                            fields.setField("fill_6", damt);
                            fields.setField("fill_7", ccunt);
                            fields.setField("fill_8", camt);
                        } else {
                            fields.setField("fill_9", dcunt);
                            fields.setField("fill_10", damt);
                            fields.setField("fill_11", ccunt);
                            fields.setField("fill_12", camt);
                        }
                        totalDcunt += intDcun;
                        totalDamt += doubleDamt;
                        totalCcunt += intCcunt;
                        totalCamt += doubleCamt;
                    }
                    String strTotalDcunt = df01.format(totalDcunt);
                    String strTotalDamt = df0.format(totalDamt);
                    String strTotalCcunt = df01.format(totalCcunt);
                    String strTotalCamt = df0.format(totalCamt);
                    fields.setField("fill_13", strTotalDcunt);
                    fields.setField("fill_14", strTotalDamt);
                    fields.setField("fill_15", strTotalCcunt);
                    fields.setField("fill_16", strTotalCamt);
                }
                if (glcurLst.size() >= 2 && "".equals(glcode2)) {
                    ActvchForGlDRpt glcur = (ActvchForGlDRpt) glcurLst.get(1);
                    String glcode = glcur.getGlcode();
                    String curcde = glcur.getCurcde();
                    String glcnam = glcur.getGlcnam();
                    String curnmc = glcur.getCurnmc();
                    fields.setField("org2", orgnam);     //机构号
                    fields.setField("dat2", ptdate); //日期
                    fields.setField("glc2", glcode + glcnam);  //科目
                    fields.setField("cur2", curnmc);         //币别

                    List<ActvchForGlDRpt> gldrptLst = vhhmap.selectForGlDRpt(orgidt, curcde, glcode, ptdate);
                    Iterator it2 = gldrptLst.iterator();
                    int totalDcunt = 0;
                    double totalDamt = 0.00;
                    int totalCcunt = 0;
                    double totalCamt = 0.00;
                    while (it2.hasNext()) {
                        ActvchForGlDRpt gldrpt = (ActvchForGlDRpt) it2.next();
                        String rvslbl = gldrpt.getRvslbl();
                        int intDcun = Integer.parseInt(gldrpt.getDcunt());
                        double doubleDamt = Double.parseDouble(gldrpt.getDamt()) * -1 / 100;
                        int intCcunt = Integer.parseInt(gldrpt.getCcunt());
                        double doubleCamt = Double.parseDouble(gldrpt.getCamt()) / 100;
                        String dcunt = df01.format(intDcun);   //借方笔数
                        String damt = df0.format(doubleDamt);     //借方金额
                        String ccunt = df01.format(intCcunt);   //贷方笔数
                        String camt = df0.format(doubleCamt);     //贷方金额
                        if (rvslbl.equals("1")) {
                            fields.setField("fill_17", dcunt);
                            fields.setField("fill_18", damt);
                            fields.setField("fill_19", ccunt);
                            fields.setField("fill_20", camt);
                        } else if (rvslbl.equals("2")) {
                            fields.setField("fill_21", dcunt);
                            fields.setField("fill_22", damt);
                            fields.setField("fill_23", ccunt);
                            fields.setField("fill_24", camt);
                        } else {
                            fields.setField("fill_25", dcunt);
                            fields.setField("fill_26", damt);
                            fields.setField("fill_27", ccunt);
                            fields.setField("fill_28", camt);
                        }
                        totalDcunt += intDcun;
                        totalDamt += doubleDamt;
                        totalCcunt += intCcunt;
                        totalCamt += doubleCamt;
                    }
                    String strTotalDcunt = df01.format(totalDcunt);
                    String strTotalDamt = df0.format(totalDamt);
                    String strTotalCcunt = df01.format(totalCcunt);
                    String strTotalCamt = df0.format(totalCamt);
                    fields.setField("fill_29", strTotalDcunt);
                    fields.setField("fill_30", strTotalDamt);
                    fields.setField("fill_31", strTotalCcunt);
                    fields.setField("fill_32", strTotalCamt);
                }
            }
            ps.setFormFlattening(true);
            ps.close();
            printTempPdf(baos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取日结单数据出错！");
        } finally {
            session.close();
        }
        return null;
    }

    /*public void tmpPath() {
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

    }*/

    private void printTempPdf(ByteArrayOutputStream baos) throws IOException, DocumentException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse resp = (HttpServletResponse) ctx.getExternalContext().getResponse();
        Document document = new Document();
        PdfCopy pdfCopy = new PdfCopy(document, baos);
        document.open();
        PdfImportedPage impPage = null;
        impPage = pdfCopy.getImportedPage(new PdfReader(baos
                .toByteArray()), 1);
        pdfCopy.addPage(impPage);
        pdfCopy.addJavaScript("this.print({bUI: false,bSilent: true,bShrinkToFit: false});" + "\r\nthis.closeDoc();");
        document.close();
        resp.reset();
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition", "inline");
        resp.setContentLength(baos.size());
        resp.setHeader("Cache-Control", "max-age=30");
        baos.writeTo(out);
        out.flush();
        out.close();
        ctx.responseComplete();
    }
    // = = = = = = = = = = = = = = = = = = = = = = = = =


    public String getPtdate() {
        return ptdate;
    }

    public void setPtdate(String ptdate) {
        this.ptdate = ptdate;
    }

    public String getGlcode2() {
        return glcode2;
    }

    public void setGlcode2(String glcode2) {
        this.glcode2 = glcode2;
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
