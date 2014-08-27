package cbs.view.ac.tradeinfo;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.utils.JxlsManager;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.ext.domain.ActvchModel;
import cbs.repository.account.maininfo.dao.ActvchMapper;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.security.OperatorManager;
import pub.print.pdf.PdfReportFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2010-11-27
 * Time: 8:08:38
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ActvchBean {

    private static final Logger logger = LoggerFactory.getLogger(ActvchBean.class);
    private ActvchVO vo = new ActvchVO();
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private SqlSession session;
    private ActvchModel selectedRecord;
    private List<ActvchModel> vchList;
    private Map<String, String> voucherTypeMap;          // ƾ֤����

    private String paramBorlen;   //�����־:0=�裻1=��
    private List<SelectItem> cdOptions;  //���list
    private String vchTotalAmt = "0.00";

    @PostConstruct
    private void init() {
        cdOptions = new ArrayList<SelectItem>();
        cdOptions.add(new SelectItem("2", " "));
        cdOptions.add(new SelectItem("0", "��"));
        cdOptions.add(new SelectItem("1", "��"));
    }

    public void queryRecords() {
        session = ibatisManager.getSessionFactory().openSession();
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        try {
            ActvchMapper mapper = session.getMapper(ActvchMapper.class);
            vchList = mapper.selectByCondition(vo.getTlrnum(), vo.getCusidt(), vo.getVchset(),
                    vo.getCurcde(), vo.getGlcode(), vo.getApcode(),
                    handleAmt(vo.getTxnamt()), vo.getAccode(), paramBorlen);
            BigDecimal vchamt = new BigDecimal("0.00");
            if (vchList == null || vchList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
                return;
            } else {
                this.voucherTypeMap = initPtenudetailMap("VOUCHERTYPE");
                for (ActvchModel vch : vchList) {
                    vch.setAnacde(voucherTypeMap.get(vch.getAnacde()));
                    if (vch.getTxnamt() > 0L) {
                        vchamt = vchamt.add(new BigDecimal(vch.getTxnamt()));
                    }
                }
                vchTotalAmt = df.format(vchamt.divide(new BigDecimal("100.00")));
            }
            setTableToFirstPage("resultForm:resultDataTable");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("��ѯ��Ʊ����ʧ��");
            MessageUtil.addErrorWithClientID("msgs", "M932");
        } finally {
            closeSession(session);
        }
    }

    public String onExpExcel() {
        try {
            if (vchList == null || vchList.size() == 0) {
                MessageUtil.addWarn("δ��ѯ���ݣ�");
                return null;
            }
            String sctdat = SystemService.getBizDate10();
            String excelFilename = "���մ�Ʊ��ˮ-" + sctdat + ".xls";
            JxlsManager jxls = new JxlsManager();
            Map beansMap = new HashMap();
            beansMap.put("records", vchList);
            beansMap.put("sctdat", sctdat);
            beansMap.put("vchTotalAmt", vchTotalAmt);
            beansMap.put("tltcnt", String.valueOf(vchList.size()));
            jxls.exportDataToXls(excelFilename, "/actvch.xls", beansMap);

        } catch (Exception e) {

        }
        return null;
    }

    // pdf ���ɼ���ӡ
    public void onPrint(ActionEvent event) {

        queryRecords();
        if (vchList == null || vchList.size() == 0) {
            return;
        }
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        OperatorManager om = OnlineService.getOperatorManager();
        String deptid = om.getOperator().getDeptid();
        String orgnam = SystemService.getDepnamByDeptid(deptid);
        try {
            String title = (orgnam == null) ? "���մ�Ʊ��ˮ" : orgnam + "���մ�Ʊ��ˮ";
            float[] widths = {80f, 140f, 140f, 80f, 120f, 180f, 80f, 80f, 180f, 80f, 140f, 120f};// ���ñ������Լ��п�
            String[] columns = new String[]{"��Ա", "��Ʊ�׺�", "�������", "��Ŀ", "������", "�˺�", "�ұ�", "���", "���", "����", "ƾ֤����", "ƾ֤��"};
            String[] fields = new String[]{"tlrnum", "vchset", "setseq", "glcode", "apcode", "accode", "curcde", "borlen", "txnamt", "rvslbl", "anacde", "furinf"};
            int[] aligns = new int[]{Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER,
                    Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_CENTER,
                    Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, Element.ALIGN_LEFT};
//            String qryDate = vo.getBgndat() + "��" + vo.getEnddat();
//            String actmsg = "�˺�:"+act.getAccode()+" ��λ:"+act.getActnam();
            String strDate = "��������:" + SystemService.getBizDate10();
//            String[] afterTitleMsgs = new String[]{actmsg,strDate};
            String[] afterTitleMsgs = new String[]{strDate, "�ܱ���:" + vchList.size()};
            int[] msgAligns = new int[]{Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
            String[] lastMsgs = new String[]{"", ""};
            // "��ӡʱ��:"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+"  "};
            Rectangle pageSize = new Rectangle(241 * 72 / 25.4f, 250 * 72 / 25.4f);
            // Rectangle pageSize = PageSize.CROWN_QUARTO;
            PdfReportFile pdfReport = new PdfReportFile(title, afterTitleMsgs, msgAligns, columns, fields, aligns, pageSize, widths, vchList, lastMsgs);
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

    public Map<String, String> getVoucherTypeMap() {
        return voucherTypeMap;
    }

    public void setVoucherTypeMap(Map<String, String> voucherTypeMap) {
        this.voucherTypeMap = voucherTypeMap;
    }

    public String getVchTotalAmt() {
        return vchTotalAmt;
    }

    public void setVchTotalAmt(String vchTotalAmt) {
        this.vchTotalAmt = vchTotalAmt;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public ActvchModel getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ActvchModel selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public List<ActvchModel> getVchList() {
        return vchList;
    }

    public void setVchList(List<ActvchModel> vchList) {
        this.vchList = vchList;
    }

    public ActvchVO getVo() {
        return vo;
    }

    public void setVo(ActvchVO vo) {
        this.vo = vo;
    }

    public String getParamBorlen() {
        return paramBorlen;
    }

    public void setParamBorlen(String paramBorlen) {
        this.paramBorlen = paramBorlen;
    }

    public List<SelectItem> getCdOptions() {
        return cdOptions;
    }

    public void setCdOptions(List<SelectItem> cdOptions) {
        this.cdOptions = cdOptions;
    }

    /**
     * ���׽�����10
     *
     * @param amt
     * @return
     */
    private String handleAmt(String amt) {
        String rtnAmt = null;
        if (amt != null && !"".equals(amt.trim())) {
            if (amt.endsWith(".") || !amt.contains(".")) {
                rtnAmt = amt + "00";
            } else if (amt.substring(amt.indexOf(".")).length() < 2) {
                rtnAmt = amt.replace(".", "") + "0";
            } else {
                rtnAmt = amt.replace(".", "");
            }
        }
        return rtnAmt;
    }

    private Map<String, String> initPtenudetailMap(String enutyp) {
        PtenudetailMapper detailMapper = session.getMapper(PtenudetailMapper.class);
        PtenudetailExample pe = new PtenudetailExample();
        pe.createCriteria().andEnutypeEqualTo(enutyp);
        pe.setOrderByClause("DISPNO");
        List<Ptenudetail> enudetailList = detailMapper.selectByExample(pe);
        Map<String, String> map = new HashMap<String, String>();
        for (Ptenudetail p : enudetailList) {
            map.put(p.getEnuitemvalue(), p.getEnuitemlabel());
        }
        closeSession(session);
        return map;
    }

    // ���ð�ť

    public void reset() {
        if (vo != null) {
            vo.setAccode(null);
            vo.setApcode(null);
            vo.setCurcde(null);
            vo.setCusidt(null);
            vo.setGlcode(null);
            vo.setTlrnum(null);
            vo.setTxnamt(null);
            vo.setVchset(null);
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
}