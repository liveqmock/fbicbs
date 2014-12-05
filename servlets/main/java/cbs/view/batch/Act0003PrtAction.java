package cbs.view.batch;

import cbs.common.IbatisManager;
import cbs.common.SystemService;
import cbs.repository.account.billinfo.dao.ActnsmMapper;
import cbs.repository.account.maininfo.dao.ActstmMapper;
import cbs.repository.account.maininfo.model.ActstmForActnum;
import cbs.repository.account.maininfo.model.ActstmForStmCotent;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Author: Lichao.W
 * On 2014/12/1 At 17:03
 */
@ManagedBean
@ViewScoped
public class Act0003PrtAction implements Serializable {
    private static final Log logger = LogFactory.getLog(ActvchPrtAction.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private String actacn = "";

    public String ActnsmPrtLst(){
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActstmMapper stmmap = session.getMapper(ActstmMapper.class);
        ActnsmMapper nsmmap = session.getMapper(ActnsmMapper.class);

        String orgidt = "";
        String orgnam = "";
        try{
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");
            GregorianCalendar gc = new GregorianCalendar();
            Date sysdate = SystemService.sysDate11();
            gc.setTime(sysdate);
            gc.add(5, -1);//默认应该是业务日期的前一天
            String reportdate = dateformt.format(gc.getTime());
            PdfReader reader = null;
            List<ActstmForActnum> stmnumLst = stmmap.selectForActnum2(actacn);
            ActstmForActnum stmnum = stmnumLst.get(0);
            String curnmc = stmnum.getCurnmc();
            String actnam = stmnum.getActnam();
            String glcnam = stmnum.getGlcnam();
            String depnam = stmnum.getDepnam();
            String actacn = stmnum.getOldacn();
            String sysidt = stmnum.getSysidt();
            String cusidt = stmnum.getCusidt();
            String apcode = stmnum.getApcode();
            String curcde = stmnum.getCurcde();
            orgidt = stmnum.getOrgidt();
            orgnam = stmnum.getOrgnam();
            String irtval = "";
            DecimalFormat dformt = new DecimalFormat("0.0000");
            if (stmnum.getIrtval() == null) {
                irtval = "0 ‰";
            } else {
                irtval = dformt.format(stmnum.getIrtval().doubleValue() / 12 * 10) + " ‰";
            }
            List<ActstmForStmCotent> stmcont = nsmmap.selectForStmContent(sysidt, orgidt, cusidt, apcode, curcde);
            int count = stmcont.size(); //  总行数
            int pageCount = 30;       // 每页记录数
            int page = 0;             // 总共页数
            /** 主要控制总共的页数*/
            if (count >= pageCount && count % pageCount == 0) {
                page = count / pageCount;
            } else {
                page = count / pageCount + 1;
            }
            ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page];
            for (int item = 0; item < page; item++) {
                InputStream isr = FacesContext.class.getResourceAsStream("/../../batchTmp/rp0003.pdf");
                baos[item] = new ByteArrayOutputStream();
                reader = new PdfReader(isr);
                PdfStamper ps = new PdfStamper(reader, baos[item]);
                AcroFields fields = ps.getAcroFields();
                fields.setField("curnmc", curnmc);   //货币
                fields.setField("glcnam", glcnam);   //科目
                fields.setField("orgnam", orgnam);   //机构
                fields.setField("depnam", depnam);   //部门
                fields.setField("actnam", actnam);   //户名
                fields.setField("actacn", actacn);   //账号
                fields.setField("irtval", irtval);   //利率：月
                fields.setField("dat", reportdate);   //出账日
                int i = 0;
                int from = item*pageCount;
                int to = from +pageCount;
                if (to>count){
                    to = count;
                }
                for (ActstmForStmCotent stm : stmcont.subList(from,to)) {
                    for (int j = 0; j < 8; j++) {
                        switch (j) {
                            case 0:
                                Date datedat = stm.getValdat();//日期
                                String valdat = dateformt.format(datedat);
                                fields.setField("num." + i + "." + j, valdat);
                                break;
                            case 1:
                                String[] strFurinf = stm.getFurinf().split(" ");
                                String furinf2 = ((strFurinf.length > 1) ? strFurinf[1] : "");//凭证号
                                fields.setField("num." + i + "." + j, furinf2);
                                break;
                            case 2:
                                fields.setField("num." + i + "." + j, stm.getThrref());//摘要
                                break;
                            case 3:
                                double dtxnamt = (double) (stm.getDtxnamt() * -1) / 100;//借方发生额
                                String spaDtxnamt = df0.format(dtxnamt);
                                fields.setField("num." + i + "." + j, spaDtxnamt);
                                break;
                            case 4:
                                double ctxnamt = (double) stm.getCtxnamt() / 100;//贷方发生额
                                String spaCtxnamt = df0.format(ctxnamt);
                                fields.setField("num." + i + "." + j, spaCtxnamt);
                                break;
                            case 5:
                                double actbal = (double) stm.getActbal() / 100;
                                String spaActbal = df0.format(actbal);
                                fields.setField("num." + i + "." + j, spaActbal);
                                break;
                            case 6:
                                String daynum = String.valueOf(stm.getDaynum());
                                fields.setField("num." + i + "." + j, daynum);
                                break;
                            case 7:
                                double dbcraccm = (double) stm.getCraccm() / 100;
                                String craccm = df0.format(dbcraccm);
                                fields.setField("num." + i + "." + j, craccm);
                                break;
                            default:
                                break;
                        }
                    }
                    i++;
                }
                ps.setFormFlattening(true);
                ps.close();
            }
            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpServletResponse resp = (HttpServletResponse) ctx.getExternalContext().getResponse();
            Document document = new Document();
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            PdfCopy pdfCopy = new PdfCopy(document, bao);
            document.open();
            PdfImportedPage impPage = null;
            /**取出之前保存的每页内容*/
            for (int i = 0; i < page; i++) {
                impPage = pdfCopy.getImportedPage(new PdfReader(baos[i]
                        .toByteArray()), 1);
                pdfCopy.addPage(impPage);
            }
            pdfCopy.addJavaScript("this.print({bUI: false,bSilent: true,bShrinkToFit: false});" + "\r\nthis.closeDoc();");
            document.close();
            resp.reset();
            ServletOutputStream out = resp.getOutputStream();
            resp.setContentType("application/pdf");
            resp.setHeader("Content-disposition", "inline");
            resp.setContentLength(bao.size());
            resp.setHeader("Cache-Control", "max-age=30");
            bao.writeTo(out);
            out.flush();
            out.close();
            ctx.responseComplete();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //= = = = = = = = = = = = = = get set = = = = = = =

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public String getActacn() {
        return actacn;
    }

    public void setActacn(String actacn) {
        this.actacn = actacn;
    }
}
