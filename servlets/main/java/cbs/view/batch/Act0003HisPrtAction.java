package cbs.view.batch;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.utils.JxlsManager;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.billinfo.dao.ActnsmMapper;
import cbs.repository.account.ext.domain.ActnoName;
import cbs.repository.account.ext.domain.ActstmModel;
import cbs.repository.account.ext.domain.ActstmhisModel;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActstmMapper;
import cbs.repository.account.maininfo.model.ActstmForActnum;
import cbs.repository.account.maininfo.model.ActstmForStmCotent;
import cbs.view.ac.tradeinfo.ActstmVO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.security.OperatorManager;
import pub.print.pdf.PdfReportFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Lichao.W
 * On 2014/12/1 At 17:03
 */
@ManagedBean
@ViewScoped
public class Act0003HisPrtAction implements Serializable {
    private static final Log logger = LogFactory.getLog(Act0003HisPrtAction.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

    String curnmc = "";
    String actnam = "";
    String glcnam = "";
    String depnam = "";
    String actacn = "";
    String sysidt = "";
    String cusidt = "";
    String apcode = "";
    String curcde = "";
    String irtval = "";
    private ActstmVO vo = new ActstmVO();
    private ActnoName act = new ActnoName();
    private ActstmhisModel selectedRecord;
    private List<ActstmhisModel> stmList;

    public void queryRecords() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActoacMapper oacMapper = session.getMapper(ActoacMapper.class);
            act = oacMapper.selectActnoNameByNo(vo.getAccode().trim());
            ActstmMapper mapper = session.getMapper(ActstmMapper.class);
            stmList = mapper.selectStmByCondition2(null, vo.getBgndat(), vo.getEnddat(),
                    vo.getAccode(), null);
            if (stmList == null || stmList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
            }
            ActstmMapper stmmap = session.getMapper(ActstmMapper.class);
            List<ActstmForActnum> stmnumLst = stmmap.selectForActnum2(vo.getAccode().trim());
            ActstmForActnum stmnum = stmnumLst.get(0);
            curnmc = stmnum.getCurnmc();
            actnam = stmnum.getActnam();
            glcnam = stmnum.getGlcnam();
            depnam = stmnum.getDepnam();
            actacn = stmnum.getOldacn();
            sysidt = stmnum.getSysidt();
            cusidt = stmnum.getCusidt();
            apcode = stmnum.getApcode();
            curcde = stmnum.getCurcde();
            irtval = "";
            DecimalFormat dformt = new DecimalFormat("0.0000");
            if (stmnum.getIrtval() == null) {
                irtval = "0 ‰";
            } else {
                irtval = dformt.format(stmnum.getIrtval().doubleValue() / 12 * 10) + " ‰";
            }
            setTableToFirstPage("resultForm:resultDataTable");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("历史对账单查询操作失败");
            MessageUtil.addErrorWithClientID("msgs", "M932");
        } finally {
            closeSession(session);
        }
    }

    // pdf 生成及打印
    public void printActDetailReport(ActionEvent event) {

        queryRecords();
        if (stmList == null || stmList.size() == 0) {
            return;
        }
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse resp = (HttpServletResponse) ctx.getExternalContext().getResponse();
        String reportdate = SystemService.getBizDate10();//出账日业务日期
        OperatorManager om = OnlineService.getOperatorManager();
        String deptid = om.getOperator().getDeptid();
        String orgnam = SystemService.getDepnamByDeptid(deptid);
        try {
            int count = stmList.size(); //  总行数
            int pageCount = 27;       // 每页记录数
            int page = 0;             // 总共页数
            /** 主要控制总共的页数*/
            if (count >= pageCount && count % pageCount == 0) {
                page = count / pageCount;
            } else {
                page = count / pageCount + 1;
            }
            PdfReader reader = null;
            Document document = new Document();
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            PdfCopy pdfCopy = new PdfCopy(document, bao);
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
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
                fields.setField("actnam", act.getActnam());   //户名
                fields.setField("actacn", act.getAccode());   //账号
                fields.setField("irtval", irtval);   //利率：月
                fields.setField("dat", reportdate);   //出账日
                int i = 0;
                int from = item * pageCount;
                int to = from + pageCount;
                if (to > count) {
                    to = count;
                }
                for (ActstmhisModel stm : stmList.subList(from, to)) {
                    for (int j = 0; j < 8; j++) {
                        switch (j) {
                            case 0:
                                fields.setField("num." + i + "." + j, stm.getStmdat());
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
                                /*String spaDtxnamt = "";
                                if (!"".equals(stm.getBoramt())){
                                    spaDtxnamt = df0.format(stm.getBoramt());//借方发生额
                                }*/
                                fields.setField("num." + i + "." + j, stm.getBoramt());
                                break;
                            case 4:
                                /*String spaCtxnamt = "";
                                if (!"".equals(stm.getLenamt())){
                                    spaCtxnamt= df0.format(stm.getLenamt());//贷方发生额
                                }*/
                                fields.setField("num." + i + "." + j, stm.getLenamt());
                                break;
                            case 5:
                                String spaActbal = df0.format(stm.getActbal());
                                fields.setField("num." + i + "." + j, spaActbal);    //余额
                                break;
                            case 6:
                                String daynum = String.valueOf(stm.getDaynum());
                                fields.setField("num." + i + "." + j, daynum);      //天数
                                break;
                            case 7:
                                String craccm = df0.format(stm.getCraccm());     //积数
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
            document.open();
            PdfImportedPage impPage = null;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 设置为第一页

    private void setTableToFirstPage(String formDT) {

        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(formDT);
        if (dataTable != null) {
            dataTable.setFirst(0);
            dataTable.setPage(1);
        }
    }

    // 关闭SqlSession
    private void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
            session = null;
        }
    }


    public ActstmVO getVo() {
        return vo;
    }

    public void setVo(ActstmVO vo) {
        this.vo = vo;
    }

    public ActnoName getAct() {
        return act;
    }

    public void setAct(ActnoName act) {
        this.act = act;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public ActstmhisModel getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ActstmhisModel selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public List<ActstmhisModel> getStmList() {
        return stmList;
    }

    public void setStmList(List<ActstmhisModel> stmList) {
        this.stmList = stmList;
    }
}
