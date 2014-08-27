package cbs.view.ac.tradeinfo;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.utils.JxlsManager;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.ext.domain.ActnoName;
import cbs.repository.account.ext.domain.ActstmModel;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActstmMapper;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-4-19
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ActCheckDetailBean {

    private static final Logger logger = LoggerFactory.getLogger(ActCheckDetailBean.class);
    private ActstmVO vo = new ActstmVO();
    private ActnoName act = new ActnoName();
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private ActstmModel selectedRecord;
    private List<ActstmModel> stmList;

    public void queryRecords() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActoacMapper oacMapper = session.getMapper(ActoacMapper.class);
            act = oacMapper.selectActnoNameByNo(vo.getAccode().trim());
            ActstmMapper mapper = session.getMapper(ActstmMapper.class);
            stmList = mapper.selectStmByCondition(null, vo.getBgndat(), vo.getEnddat(),
                    vo.getAccode(), null);
            if (stmList == null || stmList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
            }
            setTableToFirstPage("resultForm:resultDataTable");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询传票操作失败");
            MessageUtil.addErrorWithClientID("msgs", "M932");
        } finally {
            closeSession(session);
        }
    }

    public String onExpExcel() {
        try {
            if (stmList == null || stmList.size() == 0) {
                MessageUtil.addWarn("未查询数据！");
                return null;
            }
            String excelFilename = vo.getAccode() + "记账流水" + vo.getBgndat() + "--" + vo.getEnddat() + ".xls";
            JxlsManager jxls = new JxlsManager();
            Map beansMap = new HashMap();
            beansMap.put("records", stmList);
            beansMap.put("actnum", vo.getAccode());
            beansMap.put("bgndat", vo.getBgndat());
            beansMap.put("enddat", vo.getEnddat());
            beansMap.put("tltcnt", String.valueOf(stmList.size()));
            jxls.exportDataToXls(excelFilename, "/actstm.xls", beansMap);

        } catch (Exception e) {

        }
        return null;
    }


    // pdf 生成及打印
    public void printActDetailReport(ActionEvent event) {

        queryRecords();
        if (stmList == null || stmList.size() == 0) {
            return;
        }
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        OperatorManager om = OnlineService.getOperatorManager();
        String deptid = om.getOperator().getDeptid();
        String orgnam = SystemService.getDepnamByDeptid(deptid);
        try {
            String title = (orgnam == null) ? "对账单流水账" : orgnam + "对账单流水账";
            float[] widths = {140f, 140f, 140f, 100f, 140f, 140f, 140f};// 设置表格的列以及列宽
            String[] columns = new String[]{"记账日期", "凭证号码", "摘要", "借贷别", "借方", "贷方", "交易后余额"};
            String[] fields = new String[]{"stmdat", "furinf", "thrref", "borlen", "boramt", "lenamt", "actbal"};
            int[] aligns = new int[]{Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_CENTER,
                    Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT};
            String qryDate = vo.getBgndat() + "至" + vo.getEnddat();
            String actmsg = "账号:" + act.getAccode() + " 单位:" + act.getActnam();
            String strDate = "记账日期:" + qryDate;
            String[] afterTitleMsgs = new String[]{actmsg, strDate};
            int[] msgAligns = new int[]{Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
            String[] lastMsgs = new String[]{"", "交易记录数:" + stmList.size()};
            // "打印时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+"  "};
            Rectangle pageSize = new Rectangle(241 * 72 / 25.4f, 250 * 72 / 25.4f);
            // Rectangle pageSize = PageSize.CROWN_QUARTO;
            PdfReportFile pdfReport = new PdfReportFile(title, afterTitleMsgs, msgAligns, columns, fields, aligns, pageSize, widths, stmList, lastMsgs);
            ByteArrayOutputStream ops = pdfReport.generatePdfDataStream();
            response.reset();
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline");
            response.setContentLength(ops.size());
            response.setHeader("Cache-Control", "max-age=30");
            ops.writeTo(out);
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

    public ActstmModel getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ActstmModel selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public List<ActstmModel> getStmList() {
        return stmList;
    }

    public void setStmList(List<ActstmModel> stmList) {
        this.stmList = stmList;
    }

}
