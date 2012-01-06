package cbs.view.ac.batchbookreview;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.ext.domain.BatchBookReviewVO;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActvchMapper;
import cbs.repository.account.maininfo.model.ActoacExample;
import cbs.repository.account.maininfo.model.Actvch;
import cbs.repository.account.maininfo.model.ActvchExample;
import cbs.repository.account.other.dao.ActtvcMapper;
import cbs.repository.account.other.model.Acttvc;
import cbs.repository.code.dao.ActaniMapper;
import cbs.repository.code.dao.ActdepMapper;
import cbs.repository.code.model.Actani;
import cbs.repository.code.model.ActdepExample;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.dao.ScttlrMapper;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import cbs.repository.platform.model.Scttlr;
import cbs.repository.platform.model.ScttlrExample;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 批量录入复核
 * User: zhanrui
 * Date: 2011-03-01
 */

@ManagedBean
@ViewScoped
public class BatchBookReviewAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BatchBookReviewAction.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

    private String sysidt;
    private String orgidt;
    private String tlrnum;
    private String depnum;
    private short vchset;
    private short vchsetTemp = 0;

    private long totalDebitAmt;
    private long totalCreditAmt;
    private long totalAmt;

    private BatchBookReviewVO selectedRecord;
    private BatchBookReviewVO[] selectedRecords;

    private BatchBookReviewVO vo;
    private List<BatchBookReviewVO> voNeedReviewList;
    private List<BatchBookReviewVO> voReviewedList;

    private String sysdate;

    private long currentTime;

    private List<Ptenudetail> txnTypeList;
    private List<Ptenudetail> voucherTypeList;

    private String reviewedTlrnum;
    private short reviewedVchset;
    private int needReviewCount;
    private int reviewedCount;
    private static final long serialVersionUID = 9207352267098657673L;

    @PostConstruct
    public void postConstruct() {
        this.sysidt = SystemService.getSysidtAC();

        OperatorManager om = OnlineService.getOperatorManager();
        //TODO 需检查柜员的当前状态

        this.orgidt = om.getScttlr().getOrgidt();
        this.tlrnum = om.getOperatorId();
        this.depnum = om.getScttlr().getDepnum();

        this.sysdate = SystemService.getBizDate();
        this.vchset = 0;

        this.txnTypeList = getTxnTypeFromDB();
        if (this.txnTypeList.size() == 0) {
            MessageUtil.addError("交易类型未定义！");
            logger.error("交易类型未定义！");
            throw new RuntimeException("交易类型未定义");
        }
        this.voucherTypeList = getVoucherTypeFromDB();
        if (this.voucherTypeList.size() == 0) {
            MessageUtil.addError("凭证种类未定义！");
            logger.error("凭证种类未定义！");
            throw new RuntimeException("凭证种类未定义");
        }

        init();
        initCurrentVORecord();
    }


    public String onSearchReviewRecords() {

        RequestContext requestContext = RequestContext.getCurrentInstance();

        if (StringUtils.isEmpty((String) this.reviewedTlrnum)) {
            MessageUtil.addWarn("请输入被复核柜员号。");
            requestContext.addCallbackParam("isValid", false);
            return null;
        }
        if (this.reviewedTlrnum.equals(this.tlrnum)) {
            MessageUtil.addWarn("不能复核本柜员录入的传票。");
            requestContext.addCallbackParam("isValid", false);
            return null;
        }
        init();
        requestContext.addCallbackParam("isValid", true);
        return null;
    }

    /**
     * 复核主流程
     *
     * @return
     */
    public String onReviewRecord() {

        //重复提交检查
        if (this.currentTime != 0) {
            long time = System.currentTimeMillis();
            if (time - currentTime < 800) {
                //MessageUtil.addErrorWithClientID("msgs","请勿重复提交记录。");
                this.currentTime = System.currentTimeMillis();
                return null;
            }
        }
        this.currentTime = System.currentTimeMillis();

        //TODO 输入类型需在输入过程中动态判断 (如现金 外币等) 暂不实现 20101128
        this.vo.setErytyp("0");

        if (!checkInputDataBeforeInsert()) {
            return null;
        }

        vo.setVchset(this.reviewedVchset);
        vo.setOrgidt(this.orgidt);
        vo.setTlrnum(this.reviewedTlrnum);
        vo.setDepnum(this.depnum);
        vo.setPrdcde("VCH1");

        String actno = vo.getActno();
        String[] actnocode = getActnoCodeByActno(actno, this.orgidt);
        vo.setCusidt(actnocode[0]);
        vo.setApcode(actnocode[1]);
        vo.setCurcde(actnocode[2]);

        vo.setRvslbl(type2rvslbl(vo.getType()));

        //3.起息日期处理
        if (StringUtils.isEmpty(vo.getValdat())) {
            vo.setValdat(SystemService.getBizDate());
        }

        //初始化默认值
        initSimpleBeanStringValue(vo);

        if (this.reviewedVchset == 0) {
            MessageUtil.addWarn("请先检索待复核传票记录。");
            return null;
        }
        if (reviewOneRecord()) {
//            MessageUtil.addInfoWithClientID("growls","复核通过...");
        } else {
            MessageUtil.addWarnWithClientID("growls", "复核失败！");
        }
        init();
        return null;
    }

    private boolean reviewOneRecord() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActvchMapper actvchMapper = session.getMapper(ActvchMapper.class);
            Actvch actvch = new Actvch();
            try {
                PropertyUtils.copyProperties(actvch, this.vo);
            } catch (Exception e) {
                MessageUtil.addError("数据转换错误。");
                return false;
            }
            List<Actvch> actvchList = actvchMapper.reviewRecord(actvch);
            if (actvchList.size() == 0) {
                return false;
            } else {
                actvch = actvchList.get(0);
                ActvchExample example = new ActvchExample();
                example.createCriteria().andOrgidtEqualTo(actvch.getOrgidt())
                        .andTlrnumEqualTo(actvch.getTlrnum())
                        .andVchsetEqualTo(actvch.getVchset())
                        .andSetseqEqualTo(actvch.getSetseq());
                //设置已复核标志
                actvch.setClrbak("1");
                actvchMapper.updateByExample(actvch, example);
                session.commit();
                return true;
            }
        } catch (Exception e) {
            logger.error("复核传票出现错误！", e);
            throw new RuntimeException("复核传票出现错误", e);
        } finally {
            session.close();
        }
    }

    /**
     * 值变事件  检查帐号
     *
     * @param actionEvent e
     */
    public void onCheckActno(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (StringUtils.isEmpty(this.reviewedTlrnum)) {
            MessageUtil.addError("请输入被复核柜员。");
            requestContext.addCallbackParam("isValid", false);
            return;
        }
        if (StringUtils.isEmpty(vo.getActno())) {
            requestContext.addCallbackParam("isValid", false);
        } else {
            requestContext.addCallbackParam("isValid", true);
        }
    }

    public void onCheckType(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (checkInputType(vo.getType())) {
            requestContext.addCallbackParam("isValid", true);
        } else {
            requestContext.addCallbackParam("isValid", false);
            MessageUtil.addError("类型输入错误！");
        }
    }

    public void onCheckValdat(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        if (checkDate8(this.vo.getValdat())) {
            requestContext.addCallbackParam("isValid", true);
        } else {
            requestContext.addCallbackParam("isValid", false);
        }
    }

    /**
     * 凭证种类检查
     *
     * @param actionEvent
     */
    public void onCheckAnacde(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        boolean isValid = false;

        if (vo.getTxnamt() == 0) {
            MessageUtil.addError("金额不能为空！");
            isValid = false;
        } else {
            isValid = checkVoucherType(vo.getAnacde(), vo.getTxnamt());
        }

        if (isValid) {
            requestContext.addCallbackParam("isValid", true);
        } else {
            requestContext.addCallbackParam("isValid", false);
        }
    }

    /**
     * 凭证号码检查
     *
     * @param actionEvent
     */
    public void onCheckFurinf(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        boolean isValid = false;

        isValid = checkFurinf(vo.getFurinf());

        if (isValid) {
            requestContext.addCallbackParam("isValid", true);
        } else {
            requestContext.addCallbackParam("isValid", false);
        }
    }


    public void onCheckVchatt(ActionEvent actionEvent) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        boolean isValid = false;
        if (checkVchatt(vo.getVchatt())) {
            isValid = true;
        }
        if (isValid) {
            requestContext.addCallbackParam("isValid", true);
        } else {
            MessageUtil.addError("附件数输入错误！");
            requestContext.addCallbackParam("isValid", false);
        }
    }


    public void onCheckTxnamt(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (vo.getTxnamt() == 0) {
            MessageUtil.addError("金额不能为空！");
            requestContext.addCallbackParam("isValid", false);
        } else {
            requestContext.addCallbackParam("isValid", true);
        }
    }

    public String onReset() {
        this.vo = new BatchBookReviewVO();
        //init();
        return null;
    }


    //======================================================================
    // Private Methods
    //======================================================================

    private List<Ptenudetail> getVoucherTypeFromDB() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            PtenudetailMapper mapper = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample example = new PtenudetailExample();
            example.createCriteria().andEnutypeEqualTo("VOUCHERTYPE");
            example.setOrderByClause(" enuitemvalue ");
            List<Ptenudetail> records = mapper.selectByExample(example);
            return records;
        } catch (Exception e) {
            MessageUtil.addError("获取凭证种类出现错误！");
            logger.error("获取凭证种类出现错误！", e);
            throw new RuntimeException("获取凭证种类出现错误", e);
        } finally {
            session.close();
        }
    }

    private List<Ptenudetail> getTxnTypeFromDB() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            PtenudetailMapper mapper = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample example = new PtenudetailExample();
            example.createCriteria().andEnutypeEqualTo("TXNTYPE");
            example.setOrderByClause(" enuitemvalue ");
            List<Ptenudetail> records = mapper.selectByExample(example);
            return records;
        } catch (Exception e) {
            MessageUtil.addError("获取交易种类出现错误！");
            logger.error("获取交易种类出现错误！", e);
            throw new RuntimeException("获取交易种类出现错误", e);
        } finally {
            session.close();
        }
    }

    /**
     * 需更新前台列表时的初始化
     */
    private void init() {
        if (!initNeedReviewTlrnumAndVchset()) {
            if (this.reviewedTlrnum == null) {
                MessageUtil.addWarn("已无待复核传票。");
                return;
            }
        }
        initAllNeedReviewVORecords();
        if (this.voNeedReviewList.size() == 0) {
            this.reviewedVchset = 0;
            MessageUtil.addWarn("已无待复核传票。");
        }
        this.needReviewCount = this.voNeedReviewList.size();
        initAllReviewedVORecords();
    }

    /**
     * 1、如果是第一次进入复核换面，此时未指定柜员及套号，系统检索表中最小柜员号和传票号。
     * 2、复核完每一笔后，此时已经指定柜员及套号，系统检索表中此柜员号和传票号中待复核记录。
     *
     * @return
     */
    private boolean initNeedReviewTlrnumAndVchset() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        List<Actvch> records = null;
        try {
            ActvchMapper mapper = session.getMapper(ActvchMapper.class);
            ActvchExample example = new ActvchExample();
            if (this.reviewedTlrnum == null) {
                example.createCriteria().andOrgidtEqualTo(this.orgidt).
                        andRecstsEqualTo(" ").andClrbakEqualTo(" ").andTlrnumNotEqualTo(this.tlrnum);
                example.setOrderByClause("tlrnum,vchset");
                records = mapper.selectByExample(example);
            } else {
                example.clear();
                example.createCriteria().andOrgidtEqualTo(this.orgidt)
                        .andTlrnumEqualTo(this.reviewedTlrnum)
                        .andVchsetEqualTo(this.reviewedVchset)
                        .andRecstsEqualTo(" ").andClrbakEqualTo(" ");
                example.setOrderByClause("tlrnum,vchset");
                records = mapper.selectByExample(example);
                if (records.size() == 0) {
                    example.clear();
                    example.createCriteria().andOrgidtEqualTo(this.orgidt)
                            .andTlrnumEqualTo(this.reviewedTlrnum)
                            .andRecstsEqualTo(" ").andClrbakEqualTo(" ");
                    example.setOrderByClause("tlrnum,vchset");
                    records = mapper.selectByExample(example);
                }
            }
            if (records.size() == 0) {
                return false;
            } else {
                this.reviewedTlrnum = records.get(0).getTlrnum();
                this.reviewedVchset = records.get(0).getVchset();
//                this.bindingReviewedTlrnum.setValue(this.reviewedTlrnum);
//                this.bindingReviewedVchset.setValue(this.reviewedVchset);
            }
        } catch (Exception e) {
            logger.error("查询待复核传票出现错误！", e);
            throw new RuntimeException("查询待复核传票出现错误", e);
        } finally {
            session.close();
        }
        return true;
    }

    private void initCurrentVORecord() {
        this.vo = new BatchBookReviewVO();
        this.vo.setOrgidt(this.orgidt);
        this.vo.setTlrnum(this.tlrnum);
        this.vo.setType("12");
        this.vo.setVoErytyp("0");
    }

    /**
     * 前后台 交易类型值转换
     *
     * @param type 前台页面值
     * @return 数据库值
     */
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

    private String rvslbl2type(String rvslbl) {
        String type;
        if (ACEnum.RVSLBL_TRUE.getStatus().equals(rvslbl)) {
            type = "22";
        } else if (ACEnum.RVSLBL_TRAN.getStatus().equals(rvslbl)) {
            type = "12";
        } else if (ACEnum.RVSLBL_CASH.getStatus().equals(rvslbl)) {
            type = "11";
        } else if (ACEnum.RVSLBL_BUZH.getStatus().equals(rvslbl)) {
            type = "52";
        } else {
            //不变
            type = rvslbl;
        }
        return type;
    }

    /**
     * 查询指定柜员及套号内所有待复核记录
     */
    private void initAllNeedReviewVORecords() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActvchMapper mapper = session.getMapper(ActvchMapper.class);
            List<BatchBookReviewVO> voRecords = mapper.selectNeedReviewRecords(this.sysidt, this.orgidt, this.reviewedTlrnum, this.reviewedVchset);
            this.voNeedReviewList = assembleVOList(voRecords);
        } catch (Exception e) {
            MessageUtil.addError("获取当前套号传票出现错误！");
            logger.error("获取当前套号传票出现错误！", e);
            throw new RuntimeException("获取当前套号传票出现错误", e);
        } finally {
            session.close();
        }
    }

    /**
     * 查询指定柜员及套号内所有已复核通过记录
     */
    private void initAllReviewedVORecords() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActvchMapper mapper = session.getMapper(ActvchMapper.class);
            List<BatchBookReviewVO> voRecords = mapper.selectReviewedRecords(this.sysidt, this.orgidt, this.reviewedTlrnum, this.reviewedVchset);
            this.voReviewedList = assembleVOList(voRecords);
            this.reviewedCount = voReviewedList.size();
        } catch (Exception e) {
            MessageUtil.addError("获取当前套号传票出现错误！");
            logger.error("获取当前套号传票出现错误！", e);
            throw new RuntimeException("获取当前套号传票出现错误", e);
        } finally {
            session.close();
        }
    }

    /**
     * 对于新帐套 根据条件过滤需显示到页面的LIST
     * 条件：记录状态为正常
     *
     * @param voRecords records
     */
    private List<BatchBookReviewVO> assembleVOList(List<BatchBookReviewVO> voRecords) {
        List<BatchBookReviewVO> voList = new ArrayList<BatchBookReviewVO>();
        for (Iterator it = voRecords.iterator(); it.hasNext();) {
            //初始化vchset
            BatchBookReviewVO vo = (BatchBookReviewVO) it.next();
            if (this.vchset == 0) {
                this.vchset = vo.getVchset();
            }
            if (vo.getRecsts() == null) {
                //测试用
                throw new RuntimeException("Fatal Error!");
            }
            if (ACEnum.RECSTS_TVC_VALID.getStatus().equals(vo.getRecsts())) {
                vo.setType(rvslbl2type(vo.getType()));
                vo.setFormatedActno(formatActno(vo.getActno()));
                vo.setFormatedTxnType(formatTxnType(vo.getType()));
                vo.setFormatedAnacde(formatAnacde(vo.getAnacde()));
                voList.add(vo);
            }

        }
        return voList;
    }

    /**
     * 对于已套平帐套 根据条件过滤需显示到页面的LIST
     * 条件：记录状态为正常
     *
     * @param voRecords records
     */
    private void filterVOListForBalancedVchset(List<BatchBookReviewVO> voRecords) {
        this.voNeedReviewList = new ArrayList<BatchBookReviewVO>();
        for (Iterator it = voRecords.iterator(); it.hasNext();) {
            //初始化vchset
            BatchBookReviewVO vo = (BatchBookReviewVO) it.next();
            if (ACEnum.RECSTS_TVC_VALID.getStatus().equals(vo.getRecsts())) {
                vo.setType(rvslbl2type(vo.getType()));
                vo.setFormatedActno(formatActno(vo.getActno()));
                vo.setFormatedTxnType(formatTxnType(vo.getType()));
                vo.setFormatedAnacde(formatAnacde(vo.getAnacde()));
                voNeedReviewList.add(vo);
            }
        }
    }


    /**
     * 获取柜员表中传票套号
     *
     * @return vchset
     */
    private short getVchsetFromScttlr() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        short vchset = 0;
        try {
            ScttlrMapper mapper = session.getMapper(ScttlrMapper.class);
            vchset = (short) mapper.selectVchsetByTlrnum(this.tlrnum);
        } catch (Exception e) {
            session.close();
            MessageUtil.addError("获取传票套号出现错误！");
            logger.error("获取传票套号出现错误！", e);
            throw new RuntimeException("获取传票套号出现错误！", e);
        }
        return vchset;
    }

    /**
     * 根据DATATABLE 计算合计金额
     */
    private void flushTotalData() {
        Long amt = 0L;
        for (BatchBookReviewVO vo : this.voNeedReviewList)
            amt = vo.getTxnamt();
        if (amt > 0) {
            if ("22".equals(vo.getType())) {
                totalDebitAmt += (-amt);
            } else {
                this.totalCreditAmt += amt;
            }
        } else {
            if ("22".equals(vo.getType())) {
                this.totalCreditAmt += amt;
            } else {
                totalDebitAmt += (-amt);
            }
        }
        this.totalAmt = this.totalCreditAmt - this.totalDebitAmt;
    }

    /**
     * 合计数据库中借贷方数据
     *
     * @param mapper 数据库映射
     */
    private void flushTotalData(ActtvcMapper mapper) {
        Long result = mapper.selectTotalDebitAmt(this.orgidt, this.tlrnum, this.vchset);
        if (result == null) {
            result = 0L;
        }
        this.totalDebitAmt = result;
        result = mapper.selectTotalCreditAmt(this.orgidt, this.tlrnum, this.vchset);
        if (result == null) {
            result = 0L;
        }
        this.totalCreditAmt = result;

        this.totalAmt = totalCreditAmt - totalDebitAmt;
    }


    /**
     * 计算 套内有效记录数
     *
     * @param vchset 套号
     * @return count
     */
    private int countValidRecordByVchset(short vchset) {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            Integer count = mapper.countValidRecordOfVchset(this.orgidt, this.tlrnum, vchset);
            if (count == null) {
                count = 0;
            }
            return count;
        } catch (Exception e) {
            MessageUtil.addError("计算套内笔数错误！");
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * 输入传票提交前检查
     *
     * @return
     */
    private boolean checkInputDataBeforeInsert() {
        boolean result = true;

        //被复核柜员
        if (StringUtils.isEmpty(this.reviewedTlrnum)) {
            MessageUtil.addError("请输入被复核柜员。");
            result = false;
        }

        //输入类型
        if (!checkInputType(this.vo.getType())) {
            result = false;
        }
        //金额检查
        if (vo.getTxnamt() == 0) {
            MessageUtil.addErrorWithClientID("msgs", "请输入具体金额！");
            result = false;
        }

        if (!checkVoucherType(vo.getAnacde(), vo.getTxnamt())) {
            result = false;
        }

        if (!checkVchatt(vo.getVchatt())) {
            result = false;
        }
        //日期检查
        if (!checkDate8(this.vo.getValdat())) {
            result = false;
        }
        //主管授权
/*
        if (StringUtils.isNotEmpty(this.vo.getValdat())) {
            if (!SystemService.getBizDate().equals(this.vo.getValdat())
                    && StringUtils.isEmpty(this.vo.getVchaut())) {
                MessageUtil.addErrorWithClientID("msgs", "未进行主管授权!");
                return false;
            }
        }
*/
        return result;
    }


    private boolean checkInputType(String type) {
        if (StringUtils.isEmpty(type)) {
            //throw new IllegalArgumentException("类型不能为空！");
            MessageUtil.addErrorWithClientID("msgs", "类型不能为空！");
            return false;
        } else {
            if (type.equals("11") || type.equals("12") || type.equals("52") || type.equals("22")) {
                return true;
            } else {
                MessageUtil.addErrorWithClientID("msgs", "类型错误！");
                return false;
            }
        }
    }

    /**
     * 传票种类检查
     *
     * @param type
     * @return
     */
    private boolean checkVoucherType(String type, long txnamt) {
        if (StringUtils.isEmpty(type)) {
            MessageUtil.addErrorWithClientID("msgs", "传票种类不能为空！");
            return false;
        } else {
            if (txnamt == 0) {
                MessageUtil.addErrorWithClientID("msgs", "金额不能为空！");
                return false;
            } else {
                for (Ptenudetail ptenudetail : this.voucherTypeList) {
                    if (type.equals(ptenudetail.getEnuitemvalue())) {
                        if (txnamt > 0) {
                            if (StringUtils.isEmpty(ptenudetail.getEnuitemexpand())
                                    || ptenudetail.getEnuitemexpand().equals("C")) {
                                return true;
                            }
                        } else {
                            if (StringUtils.isEmpty(ptenudetail.getEnuitemexpand())
                                    || ptenudetail.getEnuitemexpand().equals("D")) {
                                return true;
                            }
                        }
                    }
                }
                MessageUtil.addErrorWithClientID("msgs", "输入的传票种类不正确！");
                return false;
            }
        }
    }

    /**
     * 凭证号码检查 
     *
     * @param furinf
     * @return
     */
    private boolean checkFurinf(String furinf) {
        if (StringUtils.isEmpty(furinf)) {
            MessageUtil.addErrorWithClientID("msgs", "凭证号码不能为空！");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkVchatt(int vchatt) {
        if (vo.getVchatt() == 0) {
            return true;
        }
        if (vo.getVchatt() > 999) {
            MessageUtil.addErrorWithClientID("msgs", "附件数不能超过999！");
            return false;
        }
        if (vo.getVchatt() < 0) {
            MessageUtil.addErrorWithClientID("msgs", "附件数不能为负数！");
            return false;
        }
        return true;
    }

    private boolean checkDate8(String date8) {
        if (StringUtils.isEmpty(this.vo.getValdat())) {
            //MessageUtil.addErrorWithClientID("msgs", "日期不能为空！");
            return true;
        }
        if (date8.length() != 8) {
            //throw new IllegalArgumentException("日期长度不为8位，长度：" + date8.length());
            MessageUtil.addErrorWithClientID("msgs", "日期长度不为8位，长度：" + date8.length());
            return false;
        }

        try {
            int year = Integer.parseInt(date8.substring(0, 4));
            int month = Integer.parseInt(date8.substring(4, 6));
            int day = Integer.parseInt(date8.substring(6, 8));
            if ((year < 1899 || year > 2999)
                    || (month < 1 || month > 12)
                    || (day < 1 || day > 31)) {
                MessageUtil.addErrorWithClientID("msgs", "日期范围应在1899/01/01-2999/12/31之间");
                return false;
            }
            Date date = new SimpleDateFormat("yyyyMMdd").parse(date8);
        } catch (Exception e) {
            MessageUtil.addErrorWithClientID("msgs", "日期格式错误！");
            return false;
        }
        return true;
    }


    /**
     * 新增记录前 检查柜员、机构基础信息 以及帐号信息
     *
     * @param session db
     * @param acttvc  this
     * @return true/false
     */
    private boolean checkAccountInfo(SqlSession session, Acttvc acttvc) {
        int count = 0;

        //检查输入类型
        if (StringUtils.isEmpty(acttvc.getErytyp())) {
            MessageUtil.addError("M313");
            return false;
        }
        //检查柜员
        ScttlrMapper tlrmapper = session.getMapper(ScttlrMapper.class);
        ScttlrExample tlrexample = new ScttlrExample();
        tlrexample.createCriteria().andTlrnumEqualTo(acttvc.getTlrnum());
        List<Scttlr> tlrrecords = tlrmapper.selectByExample(tlrexample);
        String depnum = "";
        if (tlrrecords == null || tlrrecords.size() == 0) {
            MessageUtil.addError("M901");
            return false;
        } else {
            depnum = tlrrecords.get(0).getDepnum();
        }
        //检查柜员
        ActdepMapper depmapper = session.getMapper(ActdepMapper.class);
        ActdepExample depexample = new ActdepExample();
        depexample.createCriteria().andDepcdeEqualTo(depnum);
        count = depmapper.countByExample(depexample);
        if (count == 0) {
            MessageUtil.addError("M223", new String[]{"机构未定义!"});
            return false;
        }
        //TODO 特殊核算码 转换 （废弃）

        //检查帐号
        ActoacMapper oacmapper = session.getMapper(ActoacMapper.class);
        ActoacExample oacexample = new ActoacExample();
        oacexample.createCriteria().andOrgidtEqualTo(acttvc.getOrgidt())
                .andCusidtEqualTo(acttvc.getCusidt())
                .andApcodeEqualTo(acttvc.getApcode())
                .andCurcdeEqualTo(acttvc.getCurcde());
        count = oacmapper.countByExample(oacexample);
        if (count == 0) {
            MessageUtil.addError("MZZZ", new String[]{"联机帐户表（ACTOAC）中不存在此帐号!"});
            return false;
        }
        return true;
    }


    /**
     * 根据帐号获取帐号内码
     *
     * @param actno  actno
     * @param orgidt orgidt
     * @return array
     */
    private String[] getActnoCodeByActno(String actno, String orgidt) {
        //检查输入类型
        if ((StringUtils.isEmpty(actno) || StringUtils.isEmpty(orgidt))) {
            MessageUtil.addError("M313");
            throw new RuntimeException("参数错误");
        }
        String[] actnocode = new String[3];
        SqlSession session = ibatisManager.getSessionFactory().openSession();

        try {
            ActaniMapper mapper = session.getMapper(ActaniMapper.class);
            Actani record = mapper.selectByOldacn(actno, orgidt);
            actnocode[0] = record.getCusidt();
            actnocode[1] = record.getApcode();
            actnocode[2] = record.getCurcde();
        } catch (Exception e) {
            String msg = "帐号" + actno + "未找到。";
            MessageUtil.addError(msg);
            throw new RuntimeException(msg, e);
        }
        return actnocode;
    }

    private String getActnoByActnoCode(SqlSession session, String orgidt, String cusidt, String apcode, String curcde) {
        String actno = "";
        ActaniMapper mapper = session.getMapper(ActaniMapper.class);
        actno = mapper.selectByActnoCode(orgidt, cusidt, apcode, curcde);
        return actno;
    }


    /**
     * 对应数据库表 字符字段默认值为 “ ”  便于COBOL程序处理
     *
     * @param bean javabean
     */
    private void initSimpleBeanStringValue(Object bean) {
        try {
            Class clazz = bean.getClass();
            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                String methodName = m.getName();
                if (methodName.startsWith("set")) {
                    Class mType = m.getParameterTypes()[0];
                    if ("java.lang.String".equals(mType.getName())) {
                        Method getMethod = clazz.getMethod("get" + methodName.substring(3));
                        String prop = (String) getMethod.invoke(bean);
                        if (StringUtils.isEmpty(prop)) {
                            //Method setMethod = clazz.getMethod("set" + methodName.substring(3));
                            m.invoke(bean, " ");
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化默认值错误！", e);
        }
    }


    private List<Acttvc> getValidRecordsbyVchset() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            return mapper.selectValidRecordByVchset(this.orgidt, this.tlrnum, this.vchset);
        } finally {
            session.close();
        }
    }


    /**
     * 合计本套内 本帐号总发生额
     *
     * @param records 1
     * @param cusidt  2
     * @param apcode  3
     * @param curcde  4
     * @return 5
     */
    private long sumAmtByActno(List<Acttvc> records, String cusidt, String apcode, String curcde) {
        long amt = 0;
        for (Acttvc record : records) {
            //TODO 外币情况？
            if (record.getCusidt().equals(cusidt)
                    && record.getApcode().equals(apcode)
                    && record.getCurcde().equals(curcde)) {
                if (ACEnum.RVSLBL_TRUE.getStatus().equals(record.getRvslbl())) {
                    amt += (-record.getTxnamt());
                } else {
                    amt += record.getTxnamt();
                }
                //amt += record.getTxnamt();
            }
        }
        return amt;
    }

    /**
     * 计算本套传票的总发生额轧差数
     *
     * @param records 1
     * @return 2
     */
    private long sumAmtByVchset(List<Acttvc> records) {
        long amt = 0;
        for (Acttvc record : records) {
            //TODO 外币情况？
            if (ACEnum.RVSLBL_TRUE.getStatus().equals(record.getRvslbl())) {
                amt += (-record.getTxnamt());
            } else {
                amt += record.getTxnamt();
            }
        }
        return amt;
    }

    /**
     * 帐号格式化
     *
     * @param actno act
     * @return act
     */
    private String formatActno(String actno) {
        if (actno.length() == 14) {
            return (actno.substring(0, 7) + "-" + actno.substring(7, 11) + "-" + actno.substring(11));
        } else {
            return (actno.substring(0, 3) + "-" + actno.substring(3));
        }
    }

    /**
     * @param txnType
     * @return
     */
    private String formatTxnType(String txnType) {
        if (StringUtils.isEmpty(txnType)) {
            return "";
        }
        for (Ptenudetail enu : this.txnTypeList) {
            if (txnType.equals(enu.getEnuitemvalue())) {
                return enu.getEnuitemlabel();
            }
        }
        return "";
    }

    /**
     * 传票种类格式化
     *
     * @param anacde
     * @return
     */
    private String formatAnacde(String anacde) {
        if (StringUtils.isEmpty(anacde)) {
            return "";
        }
        for (Ptenudetail enu : this.voucherTypeList) {
            if (anacde.equals(enu.getEnuitemvalue())) {
                return enu.getEnuitemlabel();
            }
        }
        return "";
    }


    //=======================================================================
    // getter & setter
    //=======================================================================

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public BatchBookReviewVO getVo() {
        return vo;
    }

    public void setVo(BatchBookReviewVO vo) {
        this.vo = vo;
    }


    public BatchBookReviewVO getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(BatchBookReviewVO selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public BatchBookReviewVO[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(BatchBookReviewVO[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public long getTotalDebitAmt() {
        return totalDebitAmt;
    }

    public void setTotalDebitAmt(long totalDebitAmt) {
        this.totalDebitAmt = totalDebitAmt;
    }

    public long getTotalCreditAmt() {
        return totalCreditAmt;
    }

    public void setTotalCreditAmt(long totalCreditAmt) {
        this.totalCreditAmt = totalCreditAmt;
    }

    public long getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(long totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getOrgidt() {
        return orgidt;
    }

    public String getTlrnum() {
        return tlrnum;
    }

    public short getVchset() {
        return vchset;
    }

    public void setVchset(short vchset) {
        this.vchset = vchset;
    }

    public short getVchsetTemp() {
        return vchsetTemp;
    }

    public void setVchsetTemp(short vchsetTemp) {
        this.vchsetTemp = vchsetTemp;
    }

    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }

    public List<Ptenudetail> getVoucherTypeList() {
        return voucherTypeList;
    }

    public List<Ptenudetail> getTxnTypeList() {
        return txnTypeList;
    }

    public List<BatchBookReviewVO> getVoNeedReviewList() {
        return voNeedReviewList;
    }

    public void setVoNeedReviewList(List<BatchBookReviewVO> voNeedReviewList) {
        this.voNeedReviewList = voNeedReviewList;
    }

    public List<BatchBookReviewVO> getVoReviewedList() {
        return voReviewedList;
    }

    public void setVoReviewedList(List<BatchBookReviewVO> voReviewedList) {
        this.voReviewedList = voReviewedList;
    }

    public String getReviewedTlrnum() {
        return reviewedTlrnum;
    }

    public void setReviewedTlrnum(String reviewedTlrnum) {
        this.reviewedTlrnum = reviewedTlrnum;
    }

    public short getReviewedVchset() {
        return reviewedVchset;
    }

    public void setReviewedVchset(short reviewedVchset) {
        this.reviewedVchset = reviewedVchset;
    }

    public int getNeedReviewCount() {
        return needReviewCount;
    }

    public void setNeedReviewCount(int needReviewCount) {
        this.needReviewCount = needReviewCount;
    }

    public int getReviewedCount() {
        return reviewedCount;
    }

    public void setReviewedCount(int reviewedCount) {
        this.reviewedCount = reviewedCount;
    }
}
