package cbs.view.ac.tradeinfo;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
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
    private Map<String, String> voucherTypeMap;          // 凭证类型

    private String paramBorlen;   //借贷标志:0=借；1=贷
    private List<SelectItem> cdOptions;  //借贷list
    private String vchTotalAmt = "0.00";
    private String cvchTotalAmt = "0.00";//贷方总金额
    private String dvchTotalAmt = "0.00";//借方总金额
    private String cvchTamt = "0.00";  //转账贷方总金额
    private String dvchTamt = "0.00";  //转账借方总金额
    private String cvchCamt = "0.00";  //现金贷方总金额
    private String dvchCamt = "0.00";  //现金借方总金额
    private String  cnum = "0";    //  现金总笔数
    private String  tnum = "0";    //  转账总笔数

    @PostConstruct
    private void init() {
        cdOptions = new ArrayList<SelectItem>();
        cdOptions.add(new SelectItem("2", " "));
        cdOptions.add(new SelectItem("0", "借"));
        cdOptions.add(new SelectItem("1", "贷"));
    }

    public void queryRecords() {
        session = ibatisManager.getSessionFactory().openSession();
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        String rvslbl = "";
        int t = 0;
        int c = 0;
        try {
            rvslbl = type2rvslbl(vo.getRvslbl());
            ActvchMapper mapper = session.getMapper(ActvchMapper.class);
                vchList = mapper.selectByCondition(vo.getTlrnum(), vo.getCusidt(), vo.getVchset(),
                        vo.getCurcde(), vo.getGlcode(), vo.getApcode(),
                        handleAmt(vo.getTxnamt()), vo.getAccode(), paramBorlen,
                        vo.getAnacde(), rvslbl, handleAmt(vo.getMinamt()), handleAmt(vo.getMaxamt()));
            BigDecimal cvchamt = new BigDecimal("0.00");
            BigDecimal dvchamt = new BigDecimal("0.00");
            BigDecimal tcamt = new BigDecimal("0.00");
            BigDecimal tdamt = new BigDecimal("0.00");
            BigDecimal ccamt = new BigDecimal("0.00");
            BigDecimal cdamt = new BigDecimal("0.00");
            if (vchList == null || vchList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
                return;
            } else {
                this.voucherTypeMap = initPtenudetailMap("VOUCHERTYPE");
                for (ActvchModel vch : vchList) {
                    vch.setAnacde(voucherTypeMap.get(vch.getAnacde()));
                    if (vch.getTxnamt() > 0L) {  //贷方
                        cvchamt = cvchamt.add(new BigDecimal(vch.getTxnamt()));

                        if ("T".equals(vch.getAnacde())) {  //转账
                            t++;
                            tcamt = cvchamt.add(new BigDecimal(vch.getTxnamt()));

                        } else if ("C".equals(vch.getAnacde())) { //现金
                            c++;
                            ccamt = cvchamt.add(new BigDecimal(vch.getTxnamt()));
                        }
                    }else { //借方
                        dvchamt = dvchamt.add(new BigDecimal(vch.getTxnamt()));
                        if ("T".equals(vch.getAnacde())) {  //转账
                            t++;
                            tdamt = dvchamt.add(new BigDecimal(vch.getTxnamt()));
                        } else if ("C".equals(vch.getAnacde())) { //现金
                            c++;
                            cdamt = dvchamt.add(new BigDecimal(vch.getTxnamt()));
                        }
                    }
                }
                cvchTotalAmt = df.format(cvchamt.divide(new BigDecimal("100.00")));  //贷方总金额
                dvchTotalAmt = df.format(dvchamt.divide(new BigDecimal("100.00"))); //借方总金额
                cvchTamt = df.format(tcamt.divide(new BigDecimal("100.00")));    //转账贷方金额
                cvchTamt = df.format(tdamt.divide(new BigDecimal("100.00")));    //转账借方金额
                cvchCamt = df.format(ccamt.divide(new BigDecimal("100.00")));     //现金贷方金额
                dvchCamt = df.format(cdamt.divide(new BigDecimal("100.00")));     //现金借方金额
                cnum = c+"";  //现金笔数
                tnum = t+"";  //转账笔数
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

    private String type2rvslbl(String type) {
        String rvslbl;
        if (("22").equals(type)) {
            rvslbl = ACEnum.RVSLBL_TRUE.getStatus();
        } else if (("12").equals(type)) {
            rvslbl = ACEnum.RVSLBL_TRAN.getStatus();
        } else if (("11").equals(type)) {
            rvslbl = ACEnum.RVSLBL_CASH.getStatus();
        } else if (("52").equals(type)) {
            rvslbl = ACEnum.RVSLBL_BUZH.getStatus();
        } else {
            rvslbl = type;
        }

        return rvslbl;
    }

    public String onExpExcel() {
        try {
            if (vchList == null || vchList.size() == 0) {
                MessageUtil.addWarn("未查询数据！");
                return null;
            }
            String sctdat = SystemService.getBizDate10();
            String excelFilename = "当日传票流水-" + sctdat + ".xls";
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

    // pdf 生成及打印
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
            String title = (orgnam == null) ? "当日传票流水" : orgnam + "当日传票流水";
            float[] widths = {80f, 140f, 140f, 80f, 120f, 180f, 80f, 80f, 180f, 80f, 140f, 120f};// 设置表格的列以及列宽
            String[] columns = new String[]{"柜员", "传票套号", "套内序号", "科目", "核算码", "账号", "币别", "借贷", "金额", "类型", "凭证种类", "凭证号"};
            String[] fields = new String[]{"tlrnum", "vchset", "setseq", "glcode", "apcode", "accode", "curcde", "borlen", "txnamt", "rvslbl", "anacde", "furinf"};
            int[] aligns = new int[]{Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER,
                    Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_CENTER,
                    Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, Element.ALIGN_LEFT};
//            String qryDate = vo.getBgndat() + "至" + vo.getEnddat();
//            String actmsg = "账号:"+act.getAccode()+" 单位:"+act.getActnam();
            String strDate = "记账日期:" + SystemService.getBizDate10();
//            String[] afterTitleMsgs = new String[]{actmsg,strDate};
            String[] afterTitleMsgs = new String[]{strDate, "总笔数:" + vchList.size()};
            int[] msgAligns = new int[]{Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
            String[] lastMsgs = new String[]{"", ""};
            // "打印时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+"  "};
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

    public String getCvchTotalAmt() {
        return cvchTotalAmt;
    }

    public void setCvchTotalAmt(String cvchTotalAmt) {
        this.cvchTotalAmt = cvchTotalAmt;
    }

    public String getDvchTotalAmt() {
        return dvchTotalAmt;
    }

    public void setDvchTotalAmt(String dvchTotalAmt) {
        this.dvchTotalAmt = dvchTotalAmt;
    }

    public String getCvchTamt() {
        return cvchTamt;
    }

    public void setCvchTamt(String cvchTamt) {
        this.cvchTamt = cvchTamt;
    }

    public String getDvchTamt() {
        return dvchTamt;
    }

    public void setDvchTamt(String dvchTamt) {
        this.dvchTamt = dvchTamt;
    }

    public String getCvchCamt() {
        return cvchCamt;
    }

    public void setCvchCamt(String cvchCamt) {
        this.cvchCamt = cvchCamt;
    }

    public String getDvchCamt() {
        return dvchCamt;
    }

    public void setDvchCamt(String dvchCamt) {
        this.dvchCamt = dvchCamt;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public String getTnum() {
        return tnum;
    }

    public void setTnum(String tnum) {
        this.tnum = tnum;
    }

    /**
     * 交易金额乘以10
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

    // 重置按钮

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
}