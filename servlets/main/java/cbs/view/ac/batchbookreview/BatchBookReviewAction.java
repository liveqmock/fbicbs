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
 * ����¼�븴��
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
        //TODO �����Ա�ĵ�ǰ״̬

        this.orgidt = om.getScttlr().getOrgidt();
        this.tlrnum = om.getOperatorId();
        this.depnum = om.getScttlr().getDepnum();

        this.sysdate = SystemService.getBizDate();
        this.vchset = 0;

        this.txnTypeList = getTxnTypeFromDB();
        if (this.txnTypeList.size() == 0) {
            MessageUtil.addError("��������δ���壡");
            logger.error("��������δ���壡");
            throw new RuntimeException("��������δ����");
        }
        this.voucherTypeList = getVoucherTypeFromDB();
        if (this.voucherTypeList.size() == 0) {
            MessageUtil.addError("ƾ֤����δ���壡");
            logger.error("ƾ֤����δ���壡");
            throw new RuntimeException("ƾ֤����δ����");
        }

        init();
        initCurrentVORecord();
    }


    public String onSearchReviewRecords() {

        RequestContext requestContext = RequestContext.getCurrentInstance();

        if (StringUtils.isEmpty((String) this.reviewedTlrnum)) {
            MessageUtil.addWarn("�����뱻���˹�Ա�š�");
            requestContext.addCallbackParam("isValid", false);
            return null;
        }
        if (this.reviewedTlrnum.equals(this.tlrnum)) {
            MessageUtil.addWarn("���ܸ��˱���Ա¼��Ĵ�Ʊ��");
            requestContext.addCallbackParam("isValid", false);
            return null;
        }
        init();
        requestContext.addCallbackParam("isValid", true);
        return null;
    }

    /**
     * ����������
     *
     * @return
     */
    public String onReviewRecord() {

        //�ظ��ύ���
        if (this.currentTime != 0) {
            long time = System.currentTimeMillis();
            if (time - currentTime < 800) {
                //MessageUtil.addErrorWithClientID("msgs","�����ظ��ύ��¼��");
                this.currentTime = System.currentTimeMillis();
                return null;
            }
        }
        this.currentTime = System.currentTimeMillis();

        //TODO ��������������������ж�̬�ж� (���ֽ� ��ҵ�) �ݲ�ʵ�� 20101128
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

        //3.��Ϣ���ڴ���
        if (StringUtils.isEmpty(vo.getValdat())) {
            vo.setValdat(SystemService.getBizDate());
        }

        //��ʼ��Ĭ��ֵ
        initSimpleBeanStringValue(vo);

        if (this.reviewedVchset == 0) {
            MessageUtil.addWarn("���ȼ��������˴�Ʊ��¼��");
            return null;
        }
        if (reviewOneRecord()) {
//            MessageUtil.addInfoWithClientID("growls","����ͨ��...");
        } else {
            MessageUtil.addWarnWithClientID("growls", "����ʧ�ܣ�");
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
                MessageUtil.addError("����ת������");
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
                //�����Ѹ��˱�־
                actvch.setClrbak("1");
                actvchMapper.updateByExample(actvch, example);
                session.commit();
                return true;
            }
        } catch (Exception e) {
            logger.error("���˴�Ʊ���ִ���", e);
            throw new RuntimeException("���˴�Ʊ���ִ���", e);
        } finally {
            session.close();
        }
    }

    /**
     * ֵ���¼�  ����ʺ�
     *
     * @param actionEvent e
     */
    public void onCheckActno(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (StringUtils.isEmpty(this.reviewedTlrnum)) {
            MessageUtil.addError("�����뱻���˹�Ա��");
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
            MessageUtil.addError("�����������");
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
     * ƾ֤������
     *
     * @param actionEvent
     */
    public void onCheckAnacde(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        boolean isValid = false;

        if (vo.getTxnamt() == 0) {
            MessageUtil.addError("����Ϊ�գ�");
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
     * ƾ֤������
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
            MessageUtil.addError("�������������");
            requestContext.addCallbackParam("isValid", false);
        }
    }


    public void onCheckTxnamt(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (vo.getTxnamt() == 0) {
            MessageUtil.addError("����Ϊ�գ�");
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
            MessageUtil.addError("��ȡƾ֤������ִ���");
            logger.error("��ȡƾ֤������ִ���", e);
            throw new RuntimeException("��ȡƾ֤������ִ���", e);
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
            MessageUtil.addError("��ȡ����������ִ���");
            logger.error("��ȡ����������ִ���", e);
            throw new RuntimeException("��ȡ����������ִ���", e);
        } finally {
            session.close();
        }
    }

    /**
     * �����ǰ̨�б�ʱ�ĳ�ʼ��
     */
    private void init() {
        if (!initNeedReviewTlrnumAndVchset()) {
            if (this.reviewedTlrnum == null) {
                MessageUtil.addWarn("���޴����˴�Ʊ��");
                return;
            }
        }
        initAllNeedReviewVORecords();
        if (this.voNeedReviewList.size() == 0) {
            this.reviewedVchset = 0;
            MessageUtil.addWarn("���޴����˴�Ʊ��");
        }
        this.needReviewCount = this.voNeedReviewList.size();
        initAllReviewedVORecords();
    }

    /**
     * 1������ǵ�һ�ν��븴�˻��棬��ʱδָ����Ա���׺ţ�ϵͳ����������С��Ա�źʹ�Ʊ�š�
     * 2��������ÿһ�ʺ󣬴�ʱ�Ѿ�ָ����Ա���׺ţ�ϵͳ�������д˹�Ա�źʹ�Ʊ���д����˼�¼��
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
            logger.error("��ѯ�����˴�Ʊ���ִ���", e);
            throw new RuntimeException("��ѯ�����˴�Ʊ���ִ���", e);
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
     * ǰ��̨ ��������ֵת��
     *
     * @param type ǰ̨ҳ��ֵ
     * @return ���ݿ�ֵ
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
            //����
            type = rvslbl;
        }
        return type;
    }

    /**
     * ��ѯָ����Ա���׺������д����˼�¼
     */
    private void initAllNeedReviewVORecords() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActvchMapper mapper = session.getMapper(ActvchMapper.class);
            List<BatchBookReviewVO> voRecords = mapper.selectNeedReviewRecords(this.sysidt, this.orgidt, this.reviewedTlrnum, this.reviewedVchset);
            this.voNeedReviewList = assembleVOList(voRecords);
        } catch (Exception e) {
            MessageUtil.addError("��ȡ��ǰ�׺Ŵ�Ʊ���ִ���");
            logger.error("��ȡ��ǰ�׺Ŵ�Ʊ���ִ���", e);
            throw new RuntimeException("��ȡ��ǰ�׺Ŵ�Ʊ���ִ���", e);
        } finally {
            session.close();
        }
    }

    /**
     * ��ѯָ����Ա���׺��������Ѹ���ͨ����¼
     */
    private void initAllReviewedVORecords() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActvchMapper mapper = session.getMapper(ActvchMapper.class);
            List<BatchBookReviewVO> voRecords = mapper.selectReviewedRecords(this.sysidt, this.orgidt, this.reviewedTlrnum, this.reviewedVchset);
            this.voReviewedList = assembleVOList(voRecords);
            this.reviewedCount = voReviewedList.size();
        } catch (Exception e) {
            MessageUtil.addError("��ȡ��ǰ�׺Ŵ�Ʊ���ִ���");
            logger.error("��ȡ��ǰ�׺Ŵ�Ʊ���ִ���", e);
            throw new RuntimeException("��ȡ��ǰ�׺Ŵ�Ʊ���ִ���", e);
        } finally {
            session.close();
        }
    }

    /**
     * ���������� ����������������ʾ��ҳ���LIST
     * ��������¼״̬Ϊ����
     *
     * @param voRecords records
     */
    private List<BatchBookReviewVO> assembleVOList(List<BatchBookReviewVO> voRecords) {
        List<BatchBookReviewVO> voList = new ArrayList<BatchBookReviewVO>();
        for (Iterator it = voRecords.iterator(); it.hasNext();) {
            //��ʼ��vchset
            BatchBookReviewVO vo = (BatchBookReviewVO) it.next();
            if (this.vchset == 0) {
                this.vchset = vo.getVchset();
            }
            if (vo.getRecsts() == null) {
                //������
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
     * ��������ƽ���� ����������������ʾ��ҳ���LIST
     * ��������¼״̬Ϊ����
     *
     * @param voRecords records
     */
    private void filterVOListForBalancedVchset(List<BatchBookReviewVO> voRecords) {
        this.voNeedReviewList = new ArrayList<BatchBookReviewVO>();
        for (Iterator it = voRecords.iterator(); it.hasNext();) {
            //��ʼ��vchset
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
     * ��ȡ��Ա���д�Ʊ�׺�
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
            MessageUtil.addError("��ȡ��Ʊ�׺ų��ִ���");
            logger.error("��ȡ��Ʊ�׺ų��ִ���", e);
            throw new RuntimeException("��ȡ��Ʊ�׺ų��ִ���", e);
        }
        return vchset;
    }

    /**
     * ����DATATABLE ����ϼƽ��
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
     * �ϼ����ݿ��н��������
     *
     * @param mapper ���ݿ�ӳ��
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
     * ���� ������Ч��¼��
     *
     * @param vchset �׺�
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
            MessageUtil.addError("�������ڱ�������");
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * ���봫Ʊ�ύǰ���
     *
     * @return
     */
    private boolean checkInputDataBeforeInsert() {
        boolean result = true;

        //�����˹�Ա
        if (StringUtils.isEmpty(this.reviewedTlrnum)) {
            MessageUtil.addError("�����뱻���˹�Ա��");
            result = false;
        }

        //��������
        if (!checkInputType(this.vo.getType())) {
            result = false;
        }
        //�����
        if (vo.getTxnamt() == 0) {
            MessageUtil.addErrorWithClientID("msgs", "����������");
            result = false;
        }

        if (!checkVoucherType(vo.getAnacde(), vo.getTxnamt())) {
            result = false;
        }

        if (!checkVchatt(vo.getVchatt())) {
            result = false;
        }
        //���ڼ��
        if (!checkDate8(this.vo.getValdat())) {
            result = false;
        }
        //������Ȩ
/*
        if (StringUtils.isNotEmpty(this.vo.getValdat())) {
            if (!SystemService.getBizDate().equals(this.vo.getValdat())
                    && StringUtils.isEmpty(this.vo.getVchaut())) {
                MessageUtil.addErrorWithClientID("msgs", "δ����������Ȩ!");
                return false;
            }
        }
*/
        return result;
    }


    private boolean checkInputType(String type) {
        if (StringUtils.isEmpty(type)) {
            //throw new IllegalArgumentException("���Ͳ���Ϊ�գ�");
            MessageUtil.addErrorWithClientID("msgs", "���Ͳ���Ϊ�գ�");
            return false;
        } else {
            if (type.equals("11") || type.equals("12") || type.equals("52") || type.equals("22")) {
                return true;
            } else {
                MessageUtil.addErrorWithClientID("msgs", "���ʹ���");
                return false;
            }
        }
    }

    /**
     * ��Ʊ������
     *
     * @param type
     * @return
     */
    private boolean checkVoucherType(String type, long txnamt) {
        if (StringUtils.isEmpty(type)) {
            MessageUtil.addErrorWithClientID("msgs", "��Ʊ���಻��Ϊ�գ�");
            return false;
        } else {
            if (txnamt == 0) {
                MessageUtil.addErrorWithClientID("msgs", "����Ϊ�գ�");
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
                MessageUtil.addErrorWithClientID("msgs", "����Ĵ�Ʊ���಻��ȷ��");
                return false;
            }
        }
    }

    /**
     * ƾ֤������ 
     *
     * @param furinf
     * @return
     */
    private boolean checkFurinf(String furinf) {
        if (StringUtils.isEmpty(furinf)) {
            MessageUtil.addErrorWithClientID("msgs", "ƾ֤���벻��Ϊ�գ�");
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
            MessageUtil.addErrorWithClientID("msgs", "���������ܳ���999��");
            return false;
        }
        if (vo.getVchatt() < 0) {
            MessageUtil.addErrorWithClientID("msgs", "����������Ϊ������");
            return false;
        }
        return true;
    }

    private boolean checkDate8(String date8) {
        if (StringUtils.isEmpty(this.vo.getValdat())) {
            //MessageUtil.addErrorWithClientID("msgs", "���ڲ���Ϊ�գ�");
            return true;
        }
        if (date8.length() != 8) {
            //throw new IllegalArgumentException("���ڳ��Ȳ�Ϊ8λ�����ȣ�" + date8.length());
            MessageUtil.addErrorWithClientID("msgs", "���ڳ��Ȳ�Ϊ8λ�����ȣ�" + date8.length());
            return false;
        }

        try {
            int year = Integer.parseInt(date8.substring(0, 4));
            int month = Integer.parseInt(date8.substring(4, 6));
            int day = Integer.parseInt(date8.substring(6, 8));
            if ((year < 1899 || year > 2999)
                    || (month < 1 || month > 12)
                    || (day < 1 || day > 31)) {
                MessageUtil.addErrorWithClientID("msgs", "���ڷ�ΧӦ��1899/01/01-2999/12/31֮��");
                return false;
            }
            Date date = new SimpleDateFormat("yyyyMMdd").parse(date8);
        } catch (Exception e) {
            MessageUtil.addErrorWithClientID("msgs", "���ڸ�ʽ����");
            return false;
        }
        return true;
    }


    /**
     * ������¼ǰ ����Ա������������Ϣ �Լ��ʺ���Ϣ
     *
     * @param session db
     * @param acttvc  this
     * @return true/false
     */
    private boolean checkAccountInfo(SqlSession session, Acttvc acttvc) {
        int count = 0;

        //�����������
        if (StringUtils.isEmpty(acttvc.getErytyp())) {
            MessageUtil.addError("M313");
            return false;
        }
        //����Ա
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
        //����Ա
        ActdepMapper depmapper = session.getMapper(ActdepMapper.class);
        ActdepExample depexample = new ActdepExample();
        depexample.createCriteria().andDepcdeEqualTo(depnum);
        count = depmapper.countByExample(depexample);
        if (count == 0) {
            MessageUtil.addError("M223", new String[]{"����δ����!"});
            return false;
        }
        //TODO ��������� ת�� ��������

        //����ʺ�
        ActoacMapper oacmapper = session.getMapper(ActoacMapper.class);
        ActoacExample oacexample = new ActoacExample();
        oacexample.createCriteria().andOrgidtEqualTo(acttvc.getOrgidt())
                .andCusidtEqualTo(acttvc.getCusidt())
                .andApcodeEqualTo(acttvc.getApcode())
                .andCurcdeEqualTo(acttvc.getCurcde());
        count = oacmapper.countByExample(oacexample);
        if (count == 0) {
            MessageUtil.addError("MZZZ", new String[]{"�����ʻ���ACTOAC���в����ڴ��ʺ�!"});
            return false;
        }
        return true;
    }


    /**
     * �����ʺŻ�ȡ�ʺ�����
     *
     * @param actno  actno
     * @param orgidt orgidt
     * @return array
     */
    private String[] getActnoCodeByActno(String actno, String orgidt) {
        //�����������
        if ((StringUtils.isEmpty(actno) || StringUtils.isEmpty(orgidt))) {
            MessageUtil.addError("M313");
            throw new RuntimeException("��������");
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
            String msg = "�ʺ�" + actno + "δ�ҵ���";
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
     * ��Ӧ���ݿ�� �ַ��ֶ�Ĭ��ֵΪ �� ��  ����COBOL������
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
            throw new RuntimeException("��ʼ��Ĭ��ֵ����", e);
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
     * �ϼƱ����� ���ʺ��ܷ�����
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
            //TODO ��������
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
     * ���㱾�״�Ʊ���ܷ�����������
     *
     * @param records 1
     * @return 2
     */
    private long sumAmtByVchset(List<Acttvc> records) {
        long amt = 0;
        for (Acttvc record : records) {
            //TODO ��������
            if (ACEnum.RVSLBL_TRUE.getStatus().equals(record.getRvslbl())) {
                amt += (-record.getTxnamt());
            } else {
                amt += record.getTxnamt();
            }
        }
        return amt;
    }

    /**
     * �ʺŸ�ʽ��
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
     * ��Ʊ�����ʽ��
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
