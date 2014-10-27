package cbs.view.ac.batchbook;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.ext.domain.BatchBookVO;
import cbs.repository.account.maininfo.dao.*;
import cbs.repository.account.maininfo.model.*;
import cbs.repository.account.other.dao.ActtvcMapper;
import cbs.repository.account.other.model.Acttvc;
import cbs.repository.account.other.model.ActtvcExample;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 批量录入 （8401）
 * User: zhanrui
 * Date: 2010-11-10
 * Time: 16:52:00
 */

@ManagedBean
@ViewScoped
public class BatchBookAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BatchBookAction.class);

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

    //private Acttvc acttvc;
    private BatchBookVO selectedRecord;
    private BatchBookVO[] selectedRecords;

    private BatchBookVO vo;
    private List<BatchBookVO> voList;

    private String sysdate;

    private long currentTime;

    private List<Ptenudetail> txnTypeList;
    private List<Ptenudetail> voucherTypeList;

    Actrep actrep = new Actrep();//支票挂失表

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
    }


    /**
     * 事件方法  增加新纪录 或修改记录
     *
     * @return 视图
     */
    public String onCreateNewRecord() {
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

        Acttvc acttvc = new Acttvc();
        try {
            PropertyUtils.copyProperties(acttvc, vo);
        } catch (Exception e) {
            MessageUtil.addError("数据转换错误。");
            return null;
        }

        SqlSession session = ibatisManager.getSessionFactory().openSession();

        acttvc.setVchset(this.vchset);
        acttvc.setOrgidt(this.orgidt);
        acttvc.setTlrnum(this.tlrnum);
        acttvc.setDepnum(this.depnum);
        acttvc.setPrdcde("VCH1");

        String actno = vo.getActno();
        String[] actnocode = getActnoCodeByActno(session, actno, this.orgidt);
        acttvc.setCusidt(actnocode[0]);
        acttvc.setApcode(actnocode[1]);
        acttvc.setCurcde(actnocode[2]);

        acttvc.setRvslbl(type2rvslbl(vo.getType()));

        //新增纪录
        acttvc.setVchsts(ACEnum.VCHSTS_NEWRECORD.getStatus());
        //正常状态
        acttvc.setRecsts(ACEnum.RECSTS_TVC_VALID.getStatus());

        acttvc.setErydat(SystemService.getBizDate());
        acttvc.setErytim(SystemService.getBizTime8());

        acttvc.setFxrate(new BigDecimal(0));
        acttvc.setSecamt(0L);

        try {
            checkAccountInfo(session, acttvc);
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);

            long wk_txnamt = 0L;
            long tvcamt = 0L;
            if (acttvc.getRvslbl().equals(ACEnum.RVSLBL_TRUE.getStatus())) {
                tvcamt = -acttvc.getTxnamt();
            } else {
                tvcamt = acttvc.getTxnamt();
            }
            //2011-11-24 by haiyu 整套(已套平的除外)同一账号余额检查
            List<Acttvc> acttvcListForvch = mapper.selectAllRecordByActnoAndVchset(acttvc.getOrgidt(), acttvc.getCusidt(), acttvc.getApcode(), acttvc.getCurcde(),
                    acttvc.getTlrnum(), acttvc.getVchset());
            wk_txnamt = tvcamt + sumAmtByActno(acttvcListForvch, acttvc.getCusidt(), acttvc.getApcode(), acttvc.getCurcde());
            //1.检查联机余额表       2011-11-24 by haiyu   透支检查
            // 读取新老检查切换枚举   0=老检查标准 ； 1=新检查标准
            String chkflag = SystemService.getChkOverDraftFlag();
            if (chkflag.equals("1")) {
                if (!checkAccountBalance(session, this.sysidt, acttvc.getOrgidt(), acttvc.getCusidt(),
                        acttvc.getApcode(), acttvc.getCurcde(), wk_txnamt, tvcamt, true, true)) {
                    //return null;
                    throw new RuntimeException();
                }
            } else {
                if (!checkAccountBalance(session, this.sysidt, acttvc.getOrgidt(), acttvc.getCusidt(),
                        acttvc.getApcode(), acttvc.getCurcde(), tvcamt, tvcamt, false, false)) {
                    //return null;
                    throw new RuntimeException();
                }
            }
            //2.特殊核算码控制
            //if ("9991".equals(acttvc.getApcode())) {
            //    acttvc.setCusidt("9990000");
            //}
            //3.起息日期处理
            if (StringUtils.isEmpty(acttvc.getValdat())) {
                acttvc.setValdat(SystemService.getBizDate());
            }
            //4.对方核算码处理
            if ("1".equals(vo.getVoErytyp())) {
                acttvc.setCorapc(vo.getCorapc());
            } else {
                acttvc.setCorapc(" ");
            }
            //5.输入类型处理（EryType）
            if ("2".equals(vo.getVoErytyp()) || "3".equals(vo.getVoErytyp()) || "4".equals(vo.getVoErytyp())) {
                acttvc.setFxeflg("1");
            } else {
                acttvc.setFxeflg("0");
            }
            if ("0".equals(vo.getVoErytyp())) {
                acttvc.setErytyp(vo.getVoErytyp());
            } else {
                acttvc.setErytyp("1");
            }

            //初始化默认值
            initSimpleBeanStringValue(acttvc);

            Integer setseq = mapper.selectMaxSetseq(this.orgidt, this.tlrnum, this.vchset);
            if (setseq == null) {
                setseq = 0;
            } else {
                if (setseq >= 99) {
                    MessageUtil.addErrorWithClientID("msgs", "本套传票交易序号超过范围，最大为99。");
                    throw new RuntimeException();
                }
            }
            setseq++;
            acttvc.setSetseq(setseq.shortValue());
            mapper.insert(acttvc);

            //TODO: 根据输入类型ERYTYP 自动生成现金 外汇等传票  暂不实现20101125

            //修改联机余额表 (新增时不修改)

            //若为套内第一笔  增加柜员表中的套号
            if (setseq == 1) {
                ScttlrMapper tlrmapper = session.getMapper(ScttlrMapper.class);
                int i = tlrmapper.selectVchsetByTlrnum(this.tlrnum);
                tlrmapper.updateVchsetByTlrnum(this.tlrnum);
                i = tlrmapper.selectVchsetByTlrnum(this.tlrnum);
                int j = i;
                if (this.vchset != tlrmapper.selectVchsetByTlrnum(this.tlrnum)) {
                    MessageUtil.addErrorWithClientID("msgs", "M410");
                    throw new RuntimeException("M410 该套传票已存在");
                }
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            logger.error("增加新传票时出现错误！", e);
            MessageUtil.addErrorWithClientID("msgs", "增加新传票时出现错误！");
            return null;
        } finally {
            session.close();
        }

        initAllVORecords();
        return null;
    }

    /**
     * 删除前检查帐户透支情况 新标准检查
     * 2011-11-25 by haiyu
     *
     * @param wk_txnamt 删除金额
     * @param orgidt
     * @param cusidt
     * @param apcode
     * @param curcde
     * @param vchset    套号
     */

    private boolean chkActBal(SqlSession session, long wk_txnamt, String orgidt, String cusidt, String apcode,
                              String curcde, String tlrnum, short vchset, String actno) {
        if (StringUtils.isEmpty(sysidt) || StringUtils.isEmpty(orgidt)
                || StringUtils.isEmpty(cusidt) || StringUtils.isEmpty(apcode)
                || StringUtils.isEmpty(curcde)) {
            throw new IllegalArgumentException();
        }

        ActobfMapper mapper = session.getMapper(ActobfMapper.class);
        ActobfExample example = new ActobfExample();
        example.clear();
        example.createCriteria().andSysidtEqualTo(sysidt).andOrgidtEqualTo(orgidt)
                .andCusidtEqualTo(cusidt).andApcodeEqualTo(apcode).andCurcdeEqualTo(curcde);
        List<Actobf> records;
        records = mapper.selectByExampleForUpdate(example);

        if (records == null || records.size() != 1) {
            //M103 该帐户不存在
            MessageUtil.addErrorWithClientID("msgs", "M103");
            return false;
        }
        Actobf obf = records.get(0);
        //2011-11-24 by haiyu 整套(已套平的除外)同一账号余额检查
        ActtvcMapper tvcmapper = session.getMapper(ActtvcMapper.class);
        List<Acttvc> acttvcListForvch = tvcmapper.selectAllRecordByActnoAndVchset(orgidt, cusidt, apcode, curcde,
                tlrnum, vchset);
        long totalamt = wk_txnamt + sumAmtByActno(acttvcListForvch, cusidt, apcode, curcde);
        if (
                ((obf.getGlcbal().equals(ACEnum.GLCBAL_C.getStatus()))
                        && (totalamt < 0)
                        && (obf.getAvabal() + totalamt < 0)
                        && ((obf.getAvabal() + totalamt) * (-1) > obf.getOvelim()))
                        ||
                        ((obf.getGlcbal().equals(ACEnum.GLCBAL_D.getStatus()))
                                && (totalamt > 0)
                                && (obf.getAvabal() + totalamt > 0)
                                && (obf.getAvabal() + totalamt > obf.getOvelim()))
                ) {
            //M309 帐户透支
            MessageUtil.addError("账号透支 " + actno);
            return false;
        }
        //跨套帐户余额检查  2011-11-24 by haiyu
        List<Acttvc> acttvcListForVchsts = tvcmapper.selectAllRecordByVchsts(orgidt, cusidt, apcode, curcde);
        totalamt = wk_txnamt + sumAmtByActno(acttvcListForVchsts, cusidt, apcode, curcde);
        if (
                ((obf.getGlcbal().equals(ACEnum.GLCBAL_C.getStatus()))
                        && (totalamt < 0)
                        && (obf.getAvabal() + totalamt < 0)
                        && ((obf.getAvabal() + totalamt) * (-1) > obf.getOvelim()))
                        ||
                        ((obf.getGlcbal().equals(ACEnum.GLCBAL_D.getStatus()))
                                && (totalamt > 0)
                                && (obf.getAvabal() + totalamt > 0)
                                && (obf.getAvabal() + totalamt > obf.getOvelim()))
                ) {
            //M309 帐户透支
            MessageUtil.addError("账号透支 " + actno);
            return false;
        }
        return true;
    }

    /**
     * 事件 删除数据库记录
     *
     * @return null
     */
    public String onDeleteRecord() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            // 删除前判断余下的是否透支 获取chkflag 新旧判断标准  2011-11-25 by haiyu
            String chkflag = SystemService.getChkOverDraftFlag();
            if (chkflag.equals("1")) {
                long wk_txnamt = 0L;
                if (ACEnum.RVSLBL_TRUE.getStatus().equals(selectedRecord.getRvslbl())) {
                    wk_txnamt = -selectedRecord.getTxnamt();
                } else {
                    wk_txnamt = selectedRecord.getTxnamt();
                }
                if (!chkActBal(session, -wk_txnamt, this.orgidt, selectedRecord.getCusidt(),
                        selectedRecord.getApcode(), selectedRecord.getCurcde(), this.tlrnum,
                        selectedRecord.getVchset(), selectedRecord.getActno())) {
                    throw new RuntimeException();
                }
            }
            //false 单笔删除
            deleteOneTvcRecord(session, this.selectedRecord.getSetseq(), false);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("删除记录出现错误！");
            logger.error("删除记录出现错误", e);
            return null;
        } finally {
            session.close();
        }
        if (countValidRecordByVchset(this.vchset) == 0) {
            this.vchset = 0;
        }
        init();
        return null;
    }

    /**
     * 事件 修改记录
     *
     * @return null
     */
    public String onEditRecord() {
        onDeleteRecord();
        //TODO this.selectedRecord.setVchsts();
        this.selectedRecord.setRecsts(ACEnum.RECSTS_TVC_INVALID.getStatus());
        //20110322 修改记录时 重置待复核标志
        this.selectedRecord.setClrbak(" ");
        try {
            PropertyUtils.copyProperties(this.vo, this.selectedRecord);
            this.vo.setOrgidt(this.orgidt);
            this.vo.setTlrnum(this.tlrnum);
        } catch (Exception e) {
            logger.error("数据转换错误", e);
            MessageUtil.addError("数据转换错误");
        }
        return null;
    }


    /**
     * 事件 套平
     *
     * @param e event
     */
    public void onBalanceAct(ActionEvent e) {

        boolean isChkDC = SystemService.isChkAllVchIsDCBalanced();

        logger.info("套平开始...");
        if (checkVchsetBeforeBalance(isChkDC)) {
            if (processBalanceAct()) {
                if (isChkDC) {
                    MessageUtil.addWarnWithClientID("msgs", "套平成功...");
                } else {
                    MessageUtil.addWarnWithClientID("msgs", "套平成功...(未进行借贷平衡检查)");
                }
                this.vchset = 0;
                init();
            }
        }
    }

    /**
     * 事件 套平后修改
     *
     * @param e event
     */
    public void onModifyVchset(ActionEvent e) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        if (this.voList.size() > 0) {
            MessageUtil.addError("存在未套平传票...");
            requestContext.addCallbackParam("result", false);
        } else {
            if (this.vchsetTemp == 0 || this.vchsetTemp == this.vchset) {
                MessageUtil.addError("请输入正确的传票套号...");
                requestContext.addCallbackParam("result", false);
                return;
            }
            if (countValidRecordByVchset(this.vchsetTemp) == 0) {
                MessageUtil.addError("此套传票无有效传票...");
                requestContext.addCallbackParam("result", false);
                return;
            }
            this.vchset = this.vchsetTemp;
            init();
            this.vchsetTemp = 0;
            requestContext.addCallbackParam("result", true);
        }
    }

    /**
     * 事件 多笔记录删除 （需区分套平前和套平后两种状态）
     *
     * @param event e
     */
    public void onMultiDelete(ActionEvent event) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            //删除前判断余下的是否透支 获取chkflag 新旧判断标准  2011-11-25 by haiyu
            String chkflag = SystemService.getChkOverDraftFlag();
            if (chkflag.equals("1")) {
                long wk_txnamt = 0L;
                int tmpcnt = 0;
                for (BatchBookVO vo : selectedRecords) {
                    if (tmpcnt == 0) {
                        wk_txnamt = sumAmtByActnoForchk(selectedRecords, vo.getCusidt(), vo.getApcode(), vo.getCurcde());
                        if (!chkActBal(session, -wk_txnamt, this.orgidt, vo.getCusidt(),
                                vo.getApcode(), vo.getCurcde(), this.tlrnum,
                                vo.getVchset(), vo.getActno())) {
                            throw new RuntimeException();
                        }
                    } else {
                        if (!vo.getCusidt().equals(selectedRecords[tmpcnt].getCusidt())
                                || vo.getApcode().equals(selectedRecords[tmpcnt].getApcode())
                                || vo.getCurcde().equals(selectedRecords[tmpcnt].getCurcde())) {
                            wk_txnamt = sumAmtByActnoForchk(selectedRecords, vo.getCusidt(), vo.getApcode(), vo.getCurcde());
                            if (!chkActBal(session, -wk_txnamt, this.orgidt, vo.getCusidt(),
                                    vo.getApcode(), vo.getCurcde(), this.tlrnum,
                                    vo.getVchset(), vo.getActno())) {
                                throw new RuntimeException();
                            }
                        }
                    }
                    tmpcnt++;
                }
            }
            int result = 0;
            for (BatchBookVO vo : selectedRecords) {
                deleteOneTvcRecord(session, vo.getSetseq(), false);
                result++;
            }
            session.commit();
            if (result == 0) {
                MessageUtil.addErrorWithClientID("msgs", "请选择需删除的传票...");
            } else {
                MessageUtil.addErrorWithClientID("msgs", "已删除 " + result + " 笔传票...");
            }
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("删除传票出现错误");
            logger.error("删除传票出现错误", e);
            return;
        } finally {
            session.close();
        }
        if (countValidRecordByVchset(this.vchset) == 0) {
            this.vchset = 0;
        }
        init();
    }

    /**
     * 事件 套删除  可套平前/后删除
     *
     * @param event e
     */
    public void onDeleteVchset(ActionEvent event) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            //删除前判断余下的是否透支 获取chkflag 新旧判断标准  2011-11-25 by haiyu
            String chkflag = SystemService.getChkOverDraftFlag();
            if (chkflag.equals("1")) {
                long wk_txnamt = 0L;
                int tmpcnt = 0;
                for (BatchBookVO vo : voList) {
                    if (tmpcnt == 0) {
                        wk_txnamt = sumAmtByActnoForchk(voList, vo.getCusidt(), vo.getApcode(), vo.getCurcde());
                        if (!chkActBal(session, -wk_txnamt, this.orgidt, vo.getCusidt(),
                                vo.getApcode(), vo.getCurcde(), this.tlrnum,
                                vo.getVchset(), vo.getActno())) {
                            throw new RuntimeException();
                        }
                    } else {
                        if (!vo.getCusidt().equals(voList.get(tmpcnt).getCusidt())
                                || vo.getApcode().equals(voList.get(tmpcnt).getApcode())
                                || vo.getCurcde().equals(voList.get(tmpcnt).getCurcde())) {
                            wk_txnamt = sumAmtByActnoForchk(voList, vo.getCusidt(), vo.getApcode(), vo.getCurcde());
                            if (!chkActBal(session, -wk_txnamt, this.orgidt, vo.getCusidt(),
                                    vo.getApcode(), vo.getCurcde(), this.tlrnum,
                                    vo.getVchset(), vo.getActno())) {
                                throw new RuntimeException();
                            }
                        }
                    }
                    tmpcnt++;
                }
            }
            int result = 0;
            //1.处理有效记录
            for (BatchBookVO vo : this.voList) {
                //true:整套删除
                deleteOneTvcRecord(session, vo.getSetseq(), true);
                result++;
            }
            //2.处理无效记录
            // 根据当前套号 修改TVC中全部非有效记录的 VCHSTS字段 为“套平前套删除” 或“套平后套删除”
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            mapper.updateMultiRecordsVchsts(ACEnum.VCHSTS_TVC_DELTVC_SET.getStatus(),
                    this.orgidt, this.tlrnum, this.vchset,
                    ACEnum.RECSTS_INVALID.getStatus(),
                    ACEnum.VCHSTS_TVC_DELTVC.getStatus());
            mapper.updateMultiRecordsVchsts(ACEnum.VCHSTS_TVC_DELVCH_SET.getStatus(),
                    this.orgidt, this.tlrnum, this.vchset,
                    ACEnum.RECSTS_INVALID.getStatus(),
                    ACEnum.VCHSTS_TVC_DELVCH.getStatus());

            session.commit();
            if (result == 0) {
                MessageUtil.addErrorWithClientID("msgs", "无可删除传票...");
            } else {
                MessageUtil.addErrorWithClientID("msgs", "已删除 " + result + " 笔传票...");
            }
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("删除记录出现错误");
            logger.error("删除记录出现错误", e);
            return;
        } finally {
            session.close();
        }
        if (countValidRecordByVchset(this.vchset) == 0) {
            this.vchset = 0;
        }
        init();
    }

    /**
     * 值变事件  检查帐号
     *
     * @param actionEvent e
     */
    public void onCheckActno(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (getRepInfo() > 0){
            requestContext.addCallbackParam("isValid", false);
            MessageUtil.addError("M408");
        }else if (StringUtils.isEmpty(vo.getActno())) {
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
     * 凭证种类检查 (根据金额正负 以及枚举表中 凭证种类的借贷方向 判断是否一致)
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
     * 凭证号码检查 ：当日传票中不允许重复
     *
     * @param actionEvent
     */
    public void onCheckFurinf(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        boolean isValid = false;

        isValid = checkFurinf();

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
        this.vo = new BatchBookVO();
        //init();
        return null;
    }

    /**
     * 检查支票挂失
     * wang
     */
    public int getRepInfo(){
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActrepMapper repmap = session.getMapper(ActrepMapper.class);
        int n = repmap.countByactno(vo.getActno());
        return n;
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
        initAllVORecords();
        initCurrentVORecord();
        logger.info("已读取数据：ACTTVC");
    }

    private void initCurrentVORecord() {
        this.vo = new BatchBookVO();
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
     * 查询当前套号内所有记录
     */
    private void initAllVORecords() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            List<BatchBookVO> voRecords;
            if (this.vchset == 0) {
                if (SystemService.isChkAllVchIsDCBalanced()) {
                    voRecords = mapper.selectMultiVchsetRecords(this.sysidt, this.orgidt, this.tlrnum);
                } else {
                    voRecords = mapper.selectMultiVchsetRecordsForNoDCBalChk(this.sysidt, this.orgidt, this.tlrnum);
                }
                filterVOListForNewVchset(voRecords);
            } else {
                voRecords = mapper.selectOneVchsetRecords(this.sysidt, this.orgidt, this.tlrnum, this.vchset);
                filterVOListForBalancedVchset(voRecords);
            }
            flushTotalData(mapper);
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
    private void filterVOListForNewVchset(List<BatchBookVO> voRecords) {
        this.voList = new ArrayList<BatchBookVO>();
        for (Iterator it = voRecords.iterator(); it.hasNext(); ) {
            //初始化vchset
            BatchBookVO vo = (BatchBookVO) it.next();
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
        if (this.vchset == 0) {
            this.vchset = (short) (getVchsetFromScttlr() + 1);
        }
    }

    /**
     * 对于已套平帐套 根据条件过滤需显示到页面的LIST
     * 条件：记录状态为正常
     *
     * @param voRecords records
     */
    private void filterVOListForBalancedVchset(List<BatchBookVO> voRecords) {
        this.voList = new ArrayList<BatchBookVO>();
        for (Iterator it = voRecords.iterator(); it.hasNext(); ) {
            //初始化vchset
            BatchBookVO vo = (BatchBookVO) it.next();
            if (ACEnum.RECSTS_TVC_VALID.getStatus().equals(vo.getRecsts())) {
                vo.setType(rvslbl2type(vo.getType()));
                vo.setFormatedActno(formatActno(vo.getActno()));
                vo.setFormatedTxnType(formatTxnType(vo.getType()));
                vo.setFormatedAnacde(formatAnacde(vo.getAnacde()));
                voList.add(vo);
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
        for (BatchBookVO vo : this.voList)
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

        if (!checkFurinf()) {
            result = false;
        }

        /*删除附件
        if (!checkVchatt(vo.getVchatt())) {
            result = false;
        }*/
        //日期检查
        if (!checkDate8(this.vo.getValdat())) {
            result = false;
        }
        //主管授权
        if (StringUtils.isNotEmpty(this.vo.getValdat())) {
            if (!SystemService.getBizDate().equals(this.vo.getValdat())
                    && StringUtils.isEmpty(this.vo.getVchaut())) {
                MessageUtil.addErrorWithClientID("msgs", "未进行主管授权!");
                return false;
            }
        }
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
     * 凭证号码检查 ：当日传票中不允许重复
     *
     * @return
     */
    private boolean checkFurinf() {
        if (StringUtils.isEmpty(this.vo.getFurinf())) {
            MessageUtil.addErrorWithClientID("msgs", "凭证号码不能为空！");
            return false;
        } else {
            SqlSession session = ibatisManager.getSessionFactory().openSession();
            try {
                String actno = this.vo.getActno();
                String[] actnocode = getActnoCodeByActno(session, actno, this.orgidt);
                ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
                ActtvcExample example = new ActtvcExample();
                example.clear();
                example.createCriteria().andFurinfEqualTo(this.vo.getFurinf()).andRecstsEqualTo(" ")
                        .andCusidtEqualTo(actnocode[0]).andApcodeEqualTo(actnocode[1])
                        .andCurcdeEqualTo(actnocode[2]).andAnacdeEqualTo(this.vo.getAnacde());
                List<Acttvc> records = mapper.selectByExample(example);
                if (records.size() == 0) {
                    return true;
                } else {
                    Acttvc tvc = records.get(0);
                    MessageUtil.addError("凭证号码重复！ 柜员:[" + tvc.getTlrnum() + "]  套号:[" + tvc.getVchset() + "]　序号:[" + tvc.getSetseq() + "]");
                    return false;
                }
            } catch (Exception e) {
                MessageUtil.addError("查找凭证号码错误！");
                throw new RuntimeException(e);
            } finally {
                session.close();
            }
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
     * @param session db
     * @param actno   actno
     * @param orgidt  orgidt
     * @return array
     */
    private String[] getActnoCodeByActno(SqlSession session, String actno, String orgidt) {
        //检查输入类型
        if ((StringUtils.isEmpty(actno) || StringUtils.isEmpty(orgidt))) {
            MessageUtil.addError("M313");
            throw new RuntimeException("参数错误");
        }
        String[] actnocode = new String[3];
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
     * 账户余额及相关检查
     *
     * @param session     db
     * @param sysidt      this
     * @param orgidt      this
     * @param cusidt      this
     * @param apcode      this
     * @param curcde      this
     * @param wk_txnamt   本套总金额（录入时）；本交易金额（其他情况下）
     * @param tvcamt      本交易金额
     * @param isForUpdate 联机余额表的读取锁
     * @param isOverdraft 是否跨套检查透支情況
     * @return true/false
     */
    private boolean checkAccountBalance(SqlSession session,
                                        String sysidt, String orgidt, String cusidt, String apcode, String curcde,
                                        long wk_txnamt, long tvcamt, boolean isForUpdate, boolean isOverdraft) {

        if (StringUtils.isEmpty(sysidt) || StringUtils.isEmpty(orgidt)
                || StringUtils.isEmpty(cusidt) || StringUtils.isEmpty(apcode)
                || StringUtils.isEmpty(curcde)) {
            throw new IllegalArgumentException();
        }

        ActobfMapper mapper = session.getMapper(ActobfMapper.class);
        ActobfExample example = new ActobfExample();
        example.clear();
        example.createCriteria().andSysidtEqualTo(sysidt).andOrgidtEqualTo(orgidt)
                .andCusidtEqualTo(cusidt).andApcodeEqualTo(apcode).andCurcdeEqualTo(curcde);
        List<Actobf> records;
        if (isForUpdate) {
            records = mapper.selectByExampleForUpdate(example);
        } else {
            records = mapper.selectByExample(example);
        }
        if (records == null || records.size() != 1) {
            //M103 该帐户不存在
            MessageUtil.addErrorWithClientID("msgs", "M103");
            return false;
        }
        Actobf obf = records.get(0);

        if (!obf.getActsts().equals(ACEnum.ACTSTS_VALID.getStatus())
                || !obf.getRecsts().equals(ACEnum.RECSTS_VALID.getStatus())) {
            //M405 该帐户已销户
            MessageUtil.addErrorWithClientID("msgs", "M405");
            return false;

        }

        if (obf.getFrzsts().equals(ACEnum.FRZSTS_NOOPER.getStatus())
                || (obf.getFrzsts().equals(ACEnum.FRZSTS_NOSAVE.getStatus()) && wk_txnamt > 0)
                || (obf.getFrzsts().equals(ACEnum.FRZSTS_NODRAW.getStatus()) && wk_txnamt < 0)
                ) {
            //M409 该帐户已冻结
            MessageUtil.addErrorWithClientID("msgs", "M409");
            return false;
        }

        if (obf.getFrzsts().equals(ACEnum.FRZSTS_NOSAVE_1.getStatus()) && wk_txnamt > 0) {
            ActfrzMapper frzmapper = session.getMapper(ActfrzMapper.class);
            ActfrzExample frzexample = new ActfrzExample();
            frzexample.createCriteria().andSysidtEqualTo(sysidt).andOrgidtEqualTo(orgidt)
                    .andCusidtEqualTo(cusidt).andApcodeEqualTo(apcode).andCurcdeEqualTo(curcde)
                    .andFrzflgEqualTo(ACEnum.FRZSTS_NOSAVE_1.getStatus())
                    .andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
            Long minFrzamt = frzmapper.selectMinamtByExample(frzexample);
            //TODO 测试未找到记录情况 返回 null？ or “” ？
            if (minFrzamt == null) {
                //M104 记录不存在
                MessageUtil.addErrorWithClientID("msgs", "M104");
                return false;
            }

            if (obf.getBokbal() + wk_txnamt > minFrzamt) {
                //M422 此一本通号不存在 ??
                MessageUtil.addErrorWithClientID("msgs", "M422");
                return false;
            }
        }

        //帐户余额检查  2011-11-24 by haiyu
        if (isForUpdate) {
            if (
                    ((obf.getGlcbal().equals(ACEnum.GLCBAL_C.getStatus()))
                            && (wk_txnamt < 0)
                            && (obf.getAvabal() + wk_txnamt < 0)
                            && ((obf.getAvabal() + wk_txnamt) * (-1) > obf.getOvelim()))
                            ||
                            ((obf.getGlcbal().equals(ACEnum.GLCBAL_D.getStatus()))
                                    && (wk_txnamt > 0)
                                    && (obf.getAvabal() + wk_txnamt > 0)
                                    && (obf.getAvabal() + wk_txnamt > obf.getOvelim()))
                    ) {
                //M309 帐户透支
                MessageUtil.addErrorWithClientID("msgs", "M309");
                return false;
            }
        }
        //跨套帐户余额检查  2011-11-24 by haiyu
        if (isOverdraft) {
            ActtvcMapper tvcmapper = session.getMapper(ActtvcMapper.class);
            List<Acttvc> acttvcListForVchsts = tvcmapper.selectAllRecordByVchsts(orgidt, cusidt, apcode, curcde);
            long totalamt = sumAmtByActno(acttvcListForVchsts, cusidt, apcode, curcde) + tvcamt;
            if (
                    ((obf.getGlcbal().equals(ACEnum.GLCBAL_C.getStatus()))
                            && (totalamt < 0)
                            && (obf.getAvabal() + totalamt < 0)
                            && ((obf.getAvabal() + totalamt) * (-1) > obf.getOvelim()))
                            ||
                            ((obf.getGlcbal().equals(ACEnum.GLCBAL_D.getStatus()))
                                    && (totalamt > 0)
                                    && (obf.getAvabal() + totalamt > 0)
                                    && (obf.getAvabal() + totalamt > obf.getOvelim()))
                    ) {
                //M309 帐户透支
                MessageUtil.addErrorWithClientID("msgs", "M309");
                return false;
            }
        }
        return true;
    }

    /**
     * 新增套内第一条记录时 柜员表中套号流水号加1
     *
     * @param session db
     * @return num
     */
    private short updateScttlrVchset(SqlSession session) {
        ScttlrMapper mapper = session.getMapper(ScttlrMapper.class);
        ScttlrExample example = new ScttlrExample();
        example.createCriteria().andTlrnumEqualTo(this.tlrnum);
        Scttlr record = mapper.selectByExample(example).get(0);
        record.setVchset((short) (record.getVchset() + 1));
        mapper.updateByExampleSelective(record, example);
        return record.getVchset();
    }

    /**
     * 删除ACTTVC表中对应当前套内 指定序号的记录
     *
     * @param session         db
     * @param setseq          套内序号
     * @param isVchsetDelMode 单笔/整套 删除模式   true 整套模式
     */
    private void deleteOneTvcRecord(SqlSession session, short setseq, boolean isVchsetDelMode) {
        ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
        ActtvcExample example = new ActtvcExample();
        example.createCriteria().andOrgidtEqualTo(this.orgidt).andTlrnumEqualTo(this.tlrnum).andVchsetEqualTo(this.vchset).andSetseqEqualTo(setseq);
        List<Acttvc> records = mapper.selectByExample(example);
        if (records.size() == 0) {
            MessageUtil.addErrorWithClientID("msgs", "M104");
            throw new RuntimeException("未找到删除记录！");
        }
        Acttvc acttvc = records.get(0);

        //删除前检查套平状态

        String recsts = acttvc.getVchsts();
        //默认vchsts 为单笔套平前删除
        String vchsts = ACEnum.VCHSTS_TVC_DELTVC.getStatus();

        if (ACEnum.VCHSTS_TVC_INSTVC.getStatus().equals(recsts)) {
            if (isVchsetDelMode)
                vchsts = ACEnum.VCHSTS_TVC_DELTVC_SET.getStatus();
            else
                vchsts = ACEnum.VCHSTS_TVC_DELTVC.getStatus();
        } else { //已套平
            //同时修改actvch表 和 联机余额 表
            deleteOneVchRecord(session, setseq);
            if (isVchsetDelMode)
                vchsts = ACEnum.VCHSTS_TVC_DELVCH_SET.getStatus();
            else
                vchsts = ACEnum.VCHSTS_TVC_DELVCH.getStatus();
        }
        acttvc.setVchsts(vchsts);
        acttvc.setRecsts(ACEnum.RECSTS_TVC_INVALID.getStatus());
        //TODO 是否修改 操作日期时间
        mapper.updateByExample(acttvc, example);
    }

    /**
     * 删除套平后的记录（ACTVCH）
     *
     * @param session
     * @param setseq
     */
    private void deleteOneVchRecord(SqlSession session, short setseq) {
        ActvchMapper mapper = session.getMapper(ActvchMapper.class);
        ActvchExample example = new ActvchExample();
        example.createCriteria().andOrgidtEqualTo(this.orgidt).andTlrnumEqualTo(this.tlrnum).andVchsetEqualTo(this.vchset).andSetseqEqualTo(setseq);
        List<Actvch> records = mapper.selectByExample(example);
        if (records.size() == 0) {
            MessageUtil.addErrorWithClientID("msgs", "M104");
            throw new RuntimeException("未找到删除记录！");
        }
        Actvch actvch = records.get(0);

        //9991核算码处理 （暂不用）

        long wk_txnamt = 0L;
        if (ACEnum.RVSLBL_TRUE.getStatus().equals(actvch.getRvslbl())) {
            wk_txnamt = -actvch.getTxnamt();
        } else {
            wk_txnamt = actvch.getTxnamt();
        }

        //锁读方式检查
        //注意金额取反
        if (!checkAccountBalance(session, this.sysidt, actvch.getOrgidt(), actvch.getCusidt(),
                actvch.getApcode(), actvch.getCurcde(), -wk_txnamt, -wk_txnamt, true, false)) {
            logger.error("账户余额检查错误!");
            throw new RuntimeException();
        }

        //更新联机余额表
        //注意金额取反
        updateActobf(session, actvch.getOrgidt(), actvch.getCusidt(),
                actvch.getApcode(), actvch.getCurcde(), -wk_txnamt);

        //更新actvch
        actvch.setRecsts(ACEnum.RECSTS_INVALID.getStatus());
        mapper.updateByExampleSelective(actvch, example);
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
     * 套平前检查
     *
     * @return true/false
     */
    private boolean checkVchsetBeforeBalance(boolean isChkDC) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();

        boolean result = true;
        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            List<Acttvc> records = mapper.selectValidRecordByVchset(this.orgidt, this.tlrnum, this.vchset);
            if (records.size() == 0) {
                return false;
            }

            //检查本套金额是否平衡
            if (isChkDC) {
                if (sumAmtByVchset(records) != 0) {
                    MessageUtil.addErrorWithClientID("msgs", "M144");
                    return false;
                }
            }

            //检查帐号及透支情况
            int count = 0;
            ActobfMapper obfmapper = session.getMapper(ActobfMapper.class);
            ActobfExample obfexample = new ActobfExample();

            List<Acttvc> tmpRecords = new ArrayList<Acttvc>();

            //20130121 zhanrui   修改透支检查逻辑：合计金额时不在统计已套平过未修改的记录
            for (Acttvc record : records) {
                //无效记录或已套平未修改过的跳过
                if (!ACEnum.RECSTS_TVC_VALID.getStatus().equals(record.getRecsts())
                        || !ACEnum.VCHSTS_TVC_INSTVC.getStatus().equals(record.getVchsts())) {
                    continue;
                }
                tmpRecords.add(record);
            }

            for (Acttvc record : tmpRecords) {
                String actno = getActnoByActnoCode(session, record.getOrgidt(), record.getCusidt(), record.getApcode(), record.getCurcde());
                obfexample.clear();
                obfexample.createCriteria().andSysidtEqualTo(this.sysidt).andOrgidtEqualTo(record.getOrgidt())
                        .andCusidtEqualTo(record.getCusidt()).andApcodeEqualTo(record.getApcode())
                        .andCurcdeEqualTo(record.getCurcde());
                count = obfmapper.countByExample(obfexample);
                if (count == 0) {
                    MessageUtil.addError("帐号已不存在:" + actno);
                    result = false;
                    continue;
                }
                Actobf obf = obfmapper.selectByExample(obfexample).get(0);
                long totalamt = sumAmtByActno(tmpRecords, record.getCusidt(), record.getApcode(), record.getCurcde());

                if (((obf.getGlcbal().equals(ACEnum.GLCBAL_C.getStatus()))
                        && (totalamt < 0)
                        && (obf.getAvabal() + totalamt < 0)
                        && ((obf.getAvabal() + totalamt) * (-1) > obf.getOvelim()))
                        || ((obf.getGlcbal().equals(ACEnum.GLCBAL_D.getStatus()))
                        && (totalamt > 0)
                        && (obf.getAvabal() + totalamt > 0)
                        && (obf.getAvabal() + totalamt > obf.getOvelim()))) {
                    //M309 帐户透支
                    MessageUtil.addError("帐号透支：" + actno);
                    result = false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("套平前数据检查出现错误。", e);
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * 合计套内 本帐号总发生额
     *
     * @param records 1
     * @param cusidt  2
     * @param apcode  3
     * @param curcde  4
     */
    private long sumAmtByActnoForchk(List<BatchBookVO> records, String cusidt, String apcode, String curcde) {
        long amt = 0;
        for (BatchBookVO record : records) {
            //TODO 支持外币情况下需按币种进行汇总
            if (record.getCusidt().equals(cusidt)
                    && record.getApcode().equals(apcode)
                    && record.getCurcde().equals(curcde)) {
                if (ACEnum.RVSLBL_TRUE.getStatus().equals(record.getRvslbl())) {
                    amt += (-record.getTxnamt());
                } else {
                    amt += record.getTxnamt();
                }
            }
        }
        return amt;
    }

    /**
     * 合计被选中数据 本帐号总发生额
     *
     * @param records 1
     * @param cusidt  2
     * @param apcode  3
     * @param curcde  4
     */
    private long sumAmtByActnoForchk(BatchBookVO[] records, String cusidt, String apcode, String curcde) {
        long amt = 0;
        for (BatchBookVO record : records) {
            //TODO 支持外币情况下需按币种进行汇总
            if (record.getCusidt().equals(cusidt)
                    && record.getApcode().equals(apcode)
                    && record.getCurcde().equals(curcde)) {
                if (ACEnum.RVSLBL_TRUE.getStatus().equals(record.getRvslbl())) {
                    amt += (-record.getTxnamt());
                } else {
                    amt += record.getTxnamt();
                }
            }
        }
        return amt;
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
            //TODO 支持外币情况下需按币种进行汇总
            if (record.getCusidt().equals(cusidt)
                    && record.getApcode().equals(apcode)
                    && record.getCurcde().equals(curcde)) {
                if (ACEnum.RVSLBL_TRUE.getStatus().equals(record.getRvslbl())) {
                    amt += (-record.getTxnamt());
                } else {
                    amt += record.getTxnamt();
                }
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
            //TODO 支持外币情况下需按币种进行汇总
            if (ACEnum.RVSLBL_TRUE.getStatus().equals(record.getRvslbl())) {
                amt += (-record.getTxnamt());
            } else {
                amt += record.getTxnamt();
            }
        }
        return amt;
    }

    /**
     * 套平
     *
     * @return t/f
     */
    private boolean processBalanceAct() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();

        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            List<Acttvc> records = mapper.selectValidRecordByVchset(this.orgidt, this.tlrnum, this.vchset);

            if (records.size() == 0) {
                MessageUtil.addError("读取本套传票记录出错！");
                throw new RuntimeException();
            }

            long wk_txnamt = 0L;
            for (Acttvc acttvc : records) {
                //已套平的跳过
                if (!ACEnum.RECSTS_TVC_VALID.getStatus().equals(acttvc.getRecsts())
                        || !ACEnum.VCHSTS_TVC_INSTVC.getStatus().equals(acttvc.getVchsts())) {
                    continue;
                }

                if (ACEnum.RVSLBL_TRUE.getStatus().equals(acttvc.getRvslbl())) {
                    wk_txnamt = -acttvc.getTxnamt();
                } else {
                    wk_txnamt = acttvc.getTxnamt();
                }
                //2011-11-25 by haiyu 新老检查一致
                if (!checkAccountBalance(session, this.sysidt, acttvc.getOrgidt(), acttvc.getCusidt(),
                        acttvc.getApcode(), acttvc.getCurcde(), wk_txnamt, wk_txnamt, true, false)) {
                    logger.error("账户余额检查错误!");
                    MessageUtil.addError("套内序号: " + acttvc.getSetseq());
                    throw new RuntimeException();
                } else {
                    //更新联机余额表
                    updateActobf(session, acttvc.getOrgidt(), acttvc.getCusidt(),
                            acttvc.getApcode(), acttvc.getCurcde(), wk_txnamt);

                    //更新ACTTVC
                    int result = mapper.updateOneVchsts(ACEnum.VCHSTS_TVC_INSVCH_SET.getStatus(),
                            acttvc.getOrgidt(), acttvc.getTlrnum(), acttvc.getVchset(), acttvc.getSetseq());
                    if (result != 1) {
                        throw new RuntimeException();
                    }

                    //插入ACTVCH
                    Actvch actvch = new Actvch();
                    PropertyUtils.copyProperties(actvch, acttvc);
                    initSimpleBeanStringValue(actvch);
                    actvch.setCrnyer(SystemService.getBizYear2());
                    actvch.setPrdseq(0);
                    actvch.setErytim(SystemService.getBizTime8());
                    actvch.setErytyp(ACEnum.ERYTYP_VCH_NORMAL.getStatus());
                    actvch.setOrgid2(acttvc.getOrgidt());
                    actvch.setOrgid3(acttvc.getOrgidt());
                    actvch.setOrgid4(acttvc.getOrgidt());
                    ActvchMapper vchmapper = session.getMapper(ActvchMapper.class);
                    vchmapper.insert(actvch);
                }
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            logger.error("套平失败", e);
            return false;
        } finally {
            session.close();
        }
        return true;
    }


    /**
     * 更新联机余额表
     *
     * @param session db
     * @param orgidt  this
     * @param cusidt  this
     * @param apcode  this
     * @param curcde  this
     * @param txnamt  交易金额  （删除时 取反 传入）
     */
    private void updateActobf(SqlSession session, String orgidt, String cusidt,
                              String apcode, String curcde, long txnamt) {

        ActobfMapper mapper = session.getMapper(ActobfMapper.class);
        ActobfExample example = new ActobfExample();
        example.createCriteria().andSysidtEqualTo(this.sysidt).andOrgidtEqualTo(orgidt)
                .andCusidtEqualTo(cusidt).andApcodeEqualTo(apcode).andCurcdeEqualTo(curcde);
        List<Actobf> records = mapper.selectByExample(example);
        if (records == null || records.size() != 1) {
            throw new RuntimeException("联机余额表处理错误！");
        }
        Actobf actobf = records.get(0);

        actobf.setAvabal(actobf.getAvabal() + txnamt);
        actobf.setBokbal(actobf.getBokbal() + txnamt);

        mapper.updateByExample(actobf, example);
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

    public BatchBookVO getVo() {
        return vo;
    }

    public void setVo(BatchBookVO vo) {
        this.vo = vo;
    }

    public List<BatchBookVO> getVoList() {
        return voList;
    }

    public void setVoList(List<BatchBookVO> voList) {
        this.voList = voList;
    }

    public BatchBookVO getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(BatchBookVO selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public BatchBookVO[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(BatchBookVO[] selectedRecords) {
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

    public Actrep getActrep() {
        return actrep;
    }

    public void setActrep(Actrep actrep) {
        this.actrep = actrep;
    }
}
