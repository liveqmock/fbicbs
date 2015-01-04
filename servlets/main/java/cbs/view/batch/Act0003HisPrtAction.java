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
    private static final Log logger = LogFactory.getLog(ActvchPrtAction.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

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
            setTableToFirstPage("resultForm:resultDataTable");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("��ʷ���˵���ѯ����ʧ��");
            MessageUtil.addErrorWithClientID("msgs", "M932");
        } finally {
            closeSession(session);
        }
    }

    // pdf ���ɼ���ӡ
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
            String title = (orgnam == null) ? "���˵���ˮ��" : orgnam + "���˵���ˮ��";
            float[] widths = {140f, 140f, 140f, 100f, 140f, 140f, 140f};// ���ñ�������Լ��п�
            String[] columns = new String[]{"��������", "ƾ֤����", "ժҪ", "�����", "�跽", "����", "���׺����"};
            String[] fields = new String[]{"stmdat", "furinf", "thrref", "borlen", "boramt", "lenamt", "actbal"};
            int[] aligns = new int[]{Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_CENTER,
                    Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT};
            String qryDate = vo.getBgndat() + "��" + vo.getEnddat();
            String actmsg = "�˺�:" + act.getAccode() + " ��λ:" + act.getActnam();
            String strDate = "��������:" + qryDate;
            String[] afterTitleMsgs = new String[]{actmsg, strDate};
            int[] msgAligns = new int[]{Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
            String[] lastMsgs = new String[]{"", "���׼�¼��:" + stmList.size()};
            // "��ӡʱ��:"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+"  "};
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
    // ����Ϊ��һҳ

    private void setTableToFirstPage(String formDT) {

        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(formDT);
        if (dataTable != null) {
            dataTable.setFirst(0);
            dataTable.setPage(1);
        }
    }

    // �ر�SqlSession
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