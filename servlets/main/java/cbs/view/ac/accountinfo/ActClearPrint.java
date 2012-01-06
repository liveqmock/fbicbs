package cbs.view.ac.accountinfo;

import cbs.common.OnlineService;
import cbs.common.utils.PropertyManager;
import com.itextpdf.text.pdf.PdfWriter;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlColumn;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-14
 * Time: 14:55:22
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "actclearprnt")
@RequestScoped
public class ActClearPrint {
    private HtmlDataTable htmltb;
    private HtmlColumn htmlclm;
    private List<ActClearBean> clearbeans;
    protected PdfWriter docwrt;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            OperatorManager opm = OnlineService.getOperatorManager();
            String pdfFile = PropertyManager.getProperty("REPEAT_PRNTPATH") + opm.getOperatorId() + ".pdf";
            File file = new File(pdfFile);
            if (file.exists()) {
                FileInputStream is = new FileInputStream(file);
                byte[] isbyte = new byte[(int) file.length()];
                is.read(isbyte);
                response.reset();
                ServletOutputStream out = response.getOutputStream();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline");
                response.setContentLength(isbyte.length);
                response.setHeader("Cache-Control", "max-age=30");
                out.write(isbyte);
                out.flush();
                out.close();
                // 自动打印
//                docwrt.
                docwrt.addJavaScript("this.print({bUI: false,bSilent: true,bShrinkToFit: false});" + "\r\nthis.closeDoc();");
//            if (isAutoOrSet) {
//                docWriter.addJavaScript("this.print({bUI: false,bSilent: true,bShrinkToFit: false});" + "\r\nthis.closeDoc();");
//            } else {  // 先设置后打印
//                docWriter.addJavaScript("this.print(true); var msgHandlerObject = new Object();doc.onWillPrint = myOnMessage;this.hostContainer.messageHandler = msgHandlerObject;");
//            }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String replace(String from, String to, String str) {
        if (str == null || from == null || to == null)
            return null;
        StringBuffer bf = new StringBuffer("");
        int index = -1;
        while ((index = str.indexOf(from)) != -1) {
            bf.append(str.substring(0, index) + to);
            str = str.substring(index + from.length());
            index = str.indexOf(from);
        }
        bf.append(str);
        return bf.toString();
    }

    public List<ActClearBean> getClearbeans() {
        return clearbeans;
    }

    public void setClearbeans(List<ActClearBean> clearbeans) {
        this.clearbeans = clearbeans;
    }

    public HtmlColumn getHtmlclm() {
        return htmlclm;
    }

    public void setHtmlclm(HtmlColumn htmlclm) {
        this.htmlclm = htmlclm;
    }

    public HtmlDataTable getHtmltb() {
        return htmltb;
    }

    public void setHtmltb(HtmlDataTable htmltb) {
        this.htmltb = htmltb;
    }
}
