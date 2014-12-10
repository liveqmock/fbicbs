package cbs.view.batch;

import cbs.common.IbatisManager;
import cbs.common.SystemService;
import cbs.common.utils.PropertyManager;
import cbs.repository.account.maininfo.dao.OldBalanceMapper;
import cbs.repository.account.maininfo.model.ActBalance;
import cbs.repository.account.maininfo.model.ActstmForStmCotent;
import cbs.repository.platform.model.Ptdept;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * On 2014/12/3 At 9:18
 */
@ManagedBean
@ViewScoped
public class OldBalanceAction implements Serializable {
    private static final Log logger = LogFactory.getLog(OldBalanceAction.class);
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    List<ActBalance> balList = null;

    public void onPrintBal(){
        try{
            String deptid = "010";
            String notNeedAcn =  PropertyManager.getProperty("NOT_NECESSARY_OLDACN");
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
            PdfReader reader = null;

            GregorianCalendar gc = new GregorianCalendar();
            Date sysdate = SystemService.sysDate11();
            gc.setTime(sysdate);
            gc.add(5, -1);//默认应该是业务日期的前一天
            String reportdate = sdfdate10.format(gc.getTime());
            SqlSession session = ibatisManager.getSessionFactory().openSession();
            OldBalanceMapper balmap = session.getMapper(OldBalanceMapper.class);
            balList = balmap.qryActBalance(notNeedAcn, deptid);
            int count = balList.size(); //  总行数
            final int pageCount = 30;       // 每页记录数
            int page = 0;             // 总共页数
            /** 主要控制总共的页数*/
            if (count >= pageCount && count % pageCount == 0) {
                page = count / pageCount;
            } else {
                page = count / pageCount + 1;
            }
            ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page];
            for (int item = 0; item < page; item++) {
                InputStream isr = FacesContext.class.getResourceAsStream("/../../batchTmp/balance.pdf");
                String coun = item + 1+"";
                String coun2 = page + "";
                baos[item] = new ByteArrayOutputStream();
                reader = new PdfReader(isr);
                PdfStamper ps = new PdfStamper(reader, baos[item]);
                AcroFields fields = ps.getAcroFields();
                fields.setField("sysdat", reportdate);   //出账日
                fields.setField("no",coun);  //页数
                fields.setField("no2",coun2);
                int i = 0;
                int from = item * pageCount;
                int to = from + pageCount;
                if (to>count){
                    to = count;
                }
                for (ActBalance bal : balList.subList(from,to)) {
                    for (int j = 0; j < 3; j++) {
                        switch (j) {
                            case 0:
                                fields.setField("num." + i + "." + j, bal.getOldacn());
                                break;
                            case 1:
                                fields.setField("num." + i + "." + j, bal.getActnam());
                                break;
                            case 2:
                                String balamt = df0.format(bal.getBokbal() / 100);
                                fields.setField("num." + i + "." + j, balamt);//金额
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
    }


    // = = = = = = = = = = = = = = = = get set = = = = = = = = = = = = =

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }
}
