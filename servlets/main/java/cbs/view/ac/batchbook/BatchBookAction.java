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
 * ����¼�� ��8401��
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

    Actrep actrep = new Actrep();//֧Ʊ��ʧ��

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
    }


    /**
     * �¼�����  �����¼�¼ ���޸ļ�¼
     *
     * @return ��ͼ
     */
    public String onCreateNewRecord() {
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

        Acttvc acttvc = new Acttvc();
        try {
            PropertyUtils.copyProperties(acttvc, vo);
        } catch (Exception e) {
            MessageUtil.addError("����ת������");
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

        //������¼
        acttvc.setVchsts(ACEnum.VCHSTS_NEWRECORD.getStatus());
        //����״̬
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
            //2011-11-24 by haiyu ����(����ƽ�ĳ���)ͬһ�˺������
            List<Acttvc> acttvcListForvch = mapper.selectAllRecordByActnoAndVchset(acttvc.getOrgidt(), acttvc.getCusidt(), acttvc.getApcode(), acttvc.getCurcde(),
                    acttvc.getTlrnum(), acttvc.getVchset());
            wk_txnamt = tvcamt + sumAmtByActno(acttvcListForvch, acttvc.getCusidt(), acttvc.getApcode(), acttvc.getCurcde());
            //1.�����������       2011-11-24 by haiyu   ͸֧���
            // ��ȡ���ϼ���л�ö��   0=�ϼ���׼ �� 1=�¼���׼
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
            //2.������������
            //if ("9991".equals(acttvc.getApcode())) {
            //    acttvc.setCusidt("9990000");
            //}
            //3.��Ϣ���ڴ���
            if (StringUtils.isEmpty(acttvc.getValdat())) {
                acttvc.setValdat(SystemService.getBizDate());
            }
            //4.�Է������봦��
            if ("1".equals(vo.getVoErytyp())) {
                acttvc.setCorapc(vo.getCorapc());
            } else {
                acttvc.setCorapc(" ");
            }
            //5.�������ʹ���EryType��
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

            //��ʼ��Ĭ��ֵ
            initSimpleBeanStringValue(acttvc);

            Integer setseq = mapper.selectMaxSetseq(this.orgidt, this.tlrnum, this.vchset);
            if (setseq == null) {
                setseq = 0;
            } else {
                if (setseq >= 99) {
                    MessageUtil.addErrorWithClientID("msgs", "���״�Ʊ������ų�����Χ�����Ϊ99��");
                    throw new RuntimeException();
                }
            }
            setseq++;
            acttvc.setSetseq(setseq.shortValue());
            mapper.insert(acttvc);

            //TODO: ������������ERYTYP �Զ������ֽ� ���ȴ�Ʊ  �ݲ�ʵ��20101125

            //�޸��������� (����ʱ���޸�)

            //��Ϊ���ڵ�һ��  ���ӹ�Ա���е��׺�
            if (setseq == 1) {
                ScttlrMapper tlrmapper = session.getMapper(ScttlrMapper.class);
                int i = tlrmapper.selectVchsetByTlrnum(this.tlrnum);
                tlrmapper.updateVchsetByTlrnum(this.tlrnum);
                i = tlrmapper.selectVchsetByTlrnum(this.tlrnum);
                int j = i;
                if (this.vchset != tlrmapper.selectVchsetByTlrnum(this.tlrnum)) {
                    MessageUtil.addErrorWithClientID("msgs", "M410");
                    throw new RuntimeException("M410 ���״�Ʊ�Ѵ���");
                }
            }
            session.commit();
        } catch (Exception e) {
            session.rollback();
            logger.error("�����´�Ʊʱ���ִ���", e);
            MessageUtil.addErrorWithClientID("msgs", "�����´�Ʊʱ���ִ���");
            return null;
        } finally {
            session.close();
        }

        initAllVORecords();
        return null;
    }

    /**
     * ɾ��ǰ����ʻ�͸֧��� �±�׼���
     * 2011-11-25 by haiyu
     *
     * @param wk_txnamt ɾ�����
     * @param orgidt
     * @param cusidt
     * @param apcode
     * @param curcde
     * @param vchset    �׺�
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
            //M103 ���ʻ�������
            MessageUtil.addErrorWithClientID("msgs", "M103");
            return false;
        }
        Actobf obf = records.get(0);
        //2011-11-24 by haiyu ����(����ƽ�ĳ���)ͬһ�˺������
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
            //M309 �ʻ�͸֧
            MessageUtil.addError("�˺�͸֧ " + actno);
            return false;
        }
        //�����ʻ������  2011-11-24 by haiyu
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
            //M309 �ʻ�͸֧
            MessageUtil.addError("�˺�͸֧ " + actno);
            return false;
        }
        return true;
    }

    /**
     * �¼� ɾ�����ݿ��¼
     *
     * @return null
     */
    public String onDeleteRecord() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            // ɾ��ǰ�ж����µ��Ƿ�͸֧ ��ȡchkflag �¾��жϱ�׼  2011-11-25 by haiyu
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
            //false ����ɾ��
            deleteOneTvcRecord(session, this.selectedRecord.getSetseq(), false);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("ɾ����¼���ִ���");
            logger.error("ɾ����¼���ִ���", e);
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
     * �¼� �޸ļ�¼
     *
     * @return null
     */
    public String onEditRecord() {
        onDeleteRecord();
        //TODO this.selectedRecord.setVchsts();
        this.selectedRecord.setRecsts(ACEnum.RECSTS_TVC_INVALID.getStatus());
        //20110322 �޸ļ�¼ʱ ���ô����˱�־
        this.selectedRecord.setClrbak(" ");
        try {
            PropertyUtils.copyProperties(this.vo, this.selectedRecord);
            this.vo.setOrgidt(this.orgidt);
            this.vo.setTlrnum(this.tlrnum);
        } catch (Exception e) {
            logger.error("����ת������", e);
            MessageUtil.addError("����ת������");
        }
        return null;
    }


    /**
     * �¼� ��ƽ
     *
     * @param e event
     */
    public void onBalanceAct(ActionEvent e) {

        boolean isChkDC = SystemService.isChkAllVchIsDCBalanced();

        logger.info("��ƽ��ʼ...");
        if (checkVchsetBeforeBalance(isChkDC)) {
            if (processBalanceAct()) {
                if (isChkDC) {
                    MessageUtil.addWarnWithClientID("msgs", "��ƽ�ɹ�...");
                } else {
                    MessageUtil.addWarnWithClientID("msgs", "��ƽ�ɹ�...(δ���н��ƽ����)");
                }
                this.vchset = 0;
                init();
            }
        }
    }

    /**
     * �¼� ��ƽ���޸�
     *
     * @param e event
     */
    public void onModifyVchset(ActionEvent e) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        if (this.voList.size() > 0) {
            MessageUtil.addError("����δ��ƽ��Ʊ...");
            requestContext.addCallbackParam("result", false);
        } else {
            if (this.vchsetTemp == 0 || this.vchsetTemp == this.vchset) {
                MessageUtil.addError("��������ȷ�Ĵ�Ʊ�׺�...");
                requestContext.addCallbackParam("result", false);
                return;
            }
            if (countValidRecordByVchset(this.vchsetTemp) == 0) {
                MessageUtil.addError("���״�Ʊ����Ч��Ʊ...");
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
     * �¼� ��ʼ�¼ɾ�� ����������ƽǰ����ƽ������״̬��
     *
     * @param event e
     */
    public void onMultiDelete(ActionEvent event) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            //ɾ��ǰ�ж����µ��Ƿ�͸֧ ��ȡchkflag �¾��жϱ�׼  2011-11-25 by haiyu
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
                MessageUtil.addErrorWithClientID("msgs", "��ѡ����ɾ���Ĵ�Ʊ...");
            } else {
                MessageUtil.addErrorWithClientID("msgs", "��ɾ�� " + result + " �ʴ�Ʊ...");
            }
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("ɾ����Ʊ���ִ���");
            logger.error("ɾ����Ʊ���ִ���", e);
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
     * �¼� ��ɾ��  ����ƽǰ/��ɾ��
     *
     * @param event e
     */
    public void onDeleteVchset(ActionEvent event) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            //ɾ��ǰ�ж����µ��Ƿ�͸֧ ��ȡchkflag �¾��жϱ�׼  2011-11-25 by haiyu
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
            //1.������Ч��¼
            for (BatchBookVO vo : this.voList) {
                //true:����ɾ��
                deleteOneTvcRecord(session, vo.getSetseq(), true);
                result++;
            }
            //2.������Ч��¼
            // ���ݵ�ǰ�׺� �޸�TVC��ȫ������Ч��¼�� VCHSTS�ֶ� Ϊ����ƽǰ��ɾ���� ����ƽ����ɾ����
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
                MessageUtil.addErrorWithClientID("msgs", "�޿�ɾ����Ʊ...");
            } else {
                MessageUtil.addErrorWithClientID("msgs", "��ɾ�� " + result + " �ʴ�Ʊ...");
            }
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addError("ɾ����¼���ִ���");
            logger.error("ɾ����¼���ִ���", e);
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
     * ֵ���¼�  ����ʺ�
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
     * ƾ֤������ (���ݽ������ �Լ�ö�ٱ��� ƾ֤����Ľ������ �ж��Ƿ�һ��)
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
     * ƾ֤������ �����մ�Ʊ�в������ظ�
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
        this.vo = new BatchBookVO();
        //init();
        return null;
    }

    /**
     * ���֧Ʊ��ʧ
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
        initAllVORecords();
        initCurrentVORecord();
        logger.info("�Ѷ�ȡ���ݣ�ACTTVC");
    }

    private void initCurrentVORecord() {
        this.vo = new BatchBookVO();
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
     * ��ѯ��ǰ�׺������м�¼
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
    private void filterVOListForNewVchset(List<BatchBookVO> voRecords) {
        this.voList = new ArrayList<BatchBookVO>();
        for (Iterator it = voRecords.iterator(); it.hasNext(); ) {
            //��ʼ��vchset
            BatchBookVO vo = (BatchBookVO) it.next();
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
        if (this.vchset == 0) {
            this.vchset = (short) (getVchsetFromScttlr() + 1);
        }
    }

    /**
     * ��������ƽ���� ����������������ʾ��ҳ���LIST
     * ��������¼״̬Ϊ����
     *
     * @param voRecords records
     */
    private void filterVOListForBalancedVchset(List<BatchBookVO> voRecords) {
        this.voList = new ArrayList<BatchBookVO>();
        for (Iterator it = voRecords.iterator(); it.hasNext(); ) {
            //��ʼ��vchset
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

        if (!checkFurinf()) {
            result = false;
        }

        /*ɾ������
        if (!checkVchatt(vo.getVchatt())) {
            result = false;
        }*/
        //���ڼ��
        if (!checkDate8(this.vo.getValdat())) {
            result = false;
        }
        //������Ȩ
        if (StringUtils.isNotEmpty(this.vo.getValdat())) {
            if (!SystemService.getBizDate().equals(this.vo.getValdat())
                    && StringUtils.isEmpty(this.vo.getVchaut())) {
                MessageUtil.addErrorWithClientID("msgs", "δ����������Ȩ!");
                return false;
            }
        }
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
     * ƾ֤������ �����մ�Ʊ�в������ظ�
     *
     * @return
     */
    private boolean checkFurinf() {
        if (StringUtils.isEmpty(this.vo.getFurinf())) {
            MessageUtil.addErrorWithClientID("msgs", "ƾ֤���벻��Ϊ�գ�");
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
                    MessageUtil.addError("ƾ֤�����ظ��� ��Ա:[" + tvc.getTlrnum() + "]  �׺�:[" + tvc.getVchset() + "]�����:[" + tvc.getSetseq() + "]");
                    return false;
                }
            } catch (Exception e) {
                MessageUtil.addError("����ƾ֤�������");
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
     * @param session db
     * @param actno   actno
     * @param orgidt  orgidt
     * @return array
     */
    private String[] getActnoCodeByActno(SqlSession session, String actno, String orgidt) {
        //�����������
        if ((StringUtils.isEmpty(actno) || StringUtils.isEmpty(orgidt))) {
            MessageUtil.addError("M313");
            throw new RuntimeException("��������");
        }
        String[] actnocode = new String[3];
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
     * �˻�����ؼ��
     *
     * @param session     db
     * @param sysidt      this
     * @param orgidt      this
     * @param cusidt      this
     * @param apcode      this
     * @param curcde      this
     * @param wk_txnamt   �����ܽ�¼��ʱ���������׽���������£�
     * @param tvcamt      �����׽��
     * @param isForUpdate ��������Ķ�ȡ��
     * @param isOverdraft �Ƿ���׼��͸֧��r
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
            //M103 ���ʻ�������
            MessageUtil.addErrorWithClientID("msgs", "M103");
            return false;
        }
        Actobf obf = records.get(0);

        if (!obf.getActsts().equals(ACEnum.ACTSTS_VALID.getStatus())
                || !obf.getRecsts().equals(ACEnum.RECSTS_VALID.getStatus())) {
            //M405 ���ʻ�������
            MessageUtil.addErrorWithClientID("msgs", "M405");
            return false;

        }

        if (obf.getFrzsts().equals(ACEnum.FRZSTS_NOOPER.getStatus())
                || (obf.getFrzsts().equals(ACEnum.FRZSTS_NOSAVE.getStatus()) && wk_txnamt > 0)
                || (obf.getFrzsts().equals(ACEnum.FRZSTS_NODRAW.getStatus()) && wk_txnamt < 0)
                ) {
            //M409 ���ʻ��Ѷ���
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
            //TODO ����δ�ҵ���¼��� ���� null�� or ���� ��
            if (minFrzamt == null) {
                //M104 ��¼������
                MessageUtil.addErrorWithClientID("msgs", "M104");
                return false;
            }

            if (obf.getBokbal() + wk_txnamt > minFrzamt) {
                //M422 ��һ��ͨ�Ų����� ??
                MessageUtil.addErrorWithClientID("msgs", "M422");
                return false;
            }
        }

        //�ʻ������  2011-11-24 by haiyu
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
                //M309 �ʻ�͸֧
                MessageUtil.addErrorWithClientID("msgs", "M309");
                return false;
            }
        }
        //�����ʻ������  2011-11-24 by haiyu
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
                //M309 �ʻ�͸֧
                MessageUtil.addErrorWithClientID("msgs", "M309");
                return false;
            }
        }
        return true;
    }

    /**
     * �������ڵ�һ����¼ʱ ��Ա�����׺���ˮ�ż�1
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
     * ɾ��ACTTVC���ж�Ӧ��ǰ���� ָ����ŵļ�¼
     *
     * @param session         db
     * @param setseq          �������
     * @param isVchsetDelMode ����/���� ɾ��ģʽ   true ����ģʽ
     */
    private void deleteOneTvcRecord(SqlSession session, short setseq, boolean isVchsetDelMode) {
        ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
        ActtvcExample example = new ActtvcExample();
        example.createCriteria().andOrgidtEqualTo(this.orgidt).andTlrnumEqualTo(this.tlrnum).andVchsetEqualTo(this.vchset).andSetseqEqualTo(setseq);
        List<Acttvc> records = mapper.selectByExample(example);
        if (records.size() == 0) {
            MessageUtil.addErrorWithClientID("msgs", "M104");
            throw new RuntimeException("δ�ҵ�ɾ����¼��");
        }
        Acttvc acttvc = records.get(0);

        //ɾ��ǰ�����ƽ״̬

        String recsts = acttvc.getVchsts();
        //Ĭ��vchsts Ϊ������ƽǰɾ��
        String vchsts = ACEnum.VCHSTS_TVC_DELTVC.getStatus();

        if (ACEnum.VCHSTS_TVC_INSTVC.getStatus().equals(recsts)) {
            if (isVchsetDelMode)
                vchsts = ACEnum.VCHSTS_TVC_DELTVC_SET.getStatus();
            else
                vchsts = ACEnum.VCHSTS_TVC_DELTVC.getStatus();
        } else { //����ƽ
            //ͬʱ�޸�actvch�� �� ������� ��
            deleteOneVchRecord(session, setseq);
            if (isVchsetDelMode)
                vchsts = ACEnum.VCHSTS_TVC_DELVCH_SET.getStatus();
            else
                vchsts = ACEnum.VCHSTS_TVC_DELVCH.getStatus();
        }
        acttvc.setVchsts(vchsts);
        acttvc.setRecsts(ACEnum.RECSTS_TVC_INVALID.getStatus());
        //TODO �Ƿ��޸� ��������ʱ��
        mapper.updateByExample(acttvc, example);
    }

    /**
     * ɾ����ƽ��ļ�¼��ACTVCH��
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
            throw new RuntimeException("δ�ҵ�ɾ����¼��");
        }
        Actvch actvch = records.get(0);

        //9991�����봦�� ���ݲ��ã�

        long wk_txnamt = 0L;
        if (ACEnum.RVSLBL_TRUE.getStatus().equals(actvch.getRvslbl())) {
            wk_txnamt = -actvch.getTxnamt();
        } else {
            wk_txnamt = actvch.getTxnamt();
        }

        //������ʽ���
        //ע����ȡ��
        if (!checkAccountBalance(session, this.sysidt, actvch.getOrgidt(), actvch.getCusidt(),
                actvch.getApcode(), actvch.getCurcde(), -wk_txnamt, -wk_txnamt, true, false)) {
            logger.error("�˻���������!");
            throw new RuntimeException();
        }

        //������������
        //ע����ȡ��
        updateActobf(session, actvch.getOrgidt(), actvch.getCusidt(),
                actvch.getApcode(), actvch.getCurcde(), -wk_txnamt);

        //����actvch
        actvch.setRecsts(ACEnum.RECSTS_INVALID.getStatus());
        mapper.updateByExampleSelective(actvch, example);
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
     * ��ƽǰ���
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

            //��鱾�׽���Ƿ�ƽ��
            if (isChkDC) {
                if (sumAmtByVchset(records) != 0) {
                    MessageUtil.addErrorWithClientID("msgs", "M144");
                    return false;
                }
            }

            //����ʺż�͸֧���
            int count = 0;
            ActobfMapper obfmapper = session.getMapper(ActobfMapper.class);
            ActobfExample obfexample = new ActobfExample();

            List<Acttvc> tmpRecords = new ArrayList<Acttvc>();

            //20130121 zhanrui   �޸�͸֧����߼����ϼƽ��ʱ����ͳ������ƽ��δ�޸ĵļ�¼
            for (Acttvc record : records) {
                //��Ч��¼������ƽδ�޸Ĺ�������
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
                    MessageUtil.addError("�ʺ��Ѳ�����:" + actno);
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
                    //M309 �ʻ�͸֧
                    MessageUtil.addError("�ʺ�͸֧��" + actno);
                    result = false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("��ƽǰ���ݼ����ִ���", e);
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * �ϼ����� ���ʺ��ܷ�����
     *
     * @param records 1
     * @param cusidt  2
     * @param apcode  3
     * @param curcde  4
     */
    private long sumAmtByActnoForchk(List<BatchBookVO> records, String cusidt, String apcode, String curcde) {
        long amt = 0;
        for (BatchBookVO record : records) {
            //TODO ֧�����������谴���ֽ��л���
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
     * �ϼƱ�ѡ������ ���ʺ��ܷ�����
     *
     * @param records 1
     * @param cusidt  2
     * @param apcode  3
     * @param curcde  4
     */
    private long sumAmtByActnoForchk(BatchBookVO[] records, String cusidt, String apcode, String curcde) {
        long amt = 0;
        for (BatchBookVO record : records) {
            //TODO ֧�����������谴���ֽ��л���
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
            //TODO ֧�����������谴���ֽ��л���
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
     * ���㱾�״�Ʊ���ܷ�����������
     *
     * @param records 1
     * @return 2
     */
    private long sumAmtByVchset(List<Acttvc> records) {
        long amt = 0;
        for (Acttvc record : records) {
            //TODO ֧�����������谴���ֽ��л���
            if (ACEnum.RVSLBL_TRUE.getStatus().equals(record.getRvslbl())) {
                amt += (-record.getTxnamt());
            } else {
                amt += record.getTxnamt();
            }
        }
        return amt;
    }

    /**
     * ��ƽ
     *
     * @return t/f
     */
    private boolean processBalanceAct() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();

        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            List<Acttvc> records = mapper.selectValidRecordByVchset(this.orgidt, this.tlrnum, this.vchset);

            if (records.size() == 0) {
                MessageUtil.addError("��ȡ���״�Ʊ��¼����");
                throw new RuntimeException();
            }

            long wk_txnamt = 0L;
            for (Acttvc acttvc : records) {
                //����ƽ������
                if (!ACEnum.RECSTS_TVC_VALID.getStatus().equals(acttvc.getRecsts())
                        || !ACEnum.VCHSTS_TVC_INSTVC.getStatus().equals(acttvc.getVchsts())) {
                    continue;
                }

                if (ACEnum.RVSLBL_TRUE.getStatus().equals(acttvc.getRvslbl())) {
                    wk_txnamt = -acttvc.getTxnamt();
                } else {
                    wk_txnamt = acttvc.getTxnamt();
                }
                //2011-11-25 by haiyu ���ϼ��һ��
                if (!checkAccountBalance(session, this.sysidt, acttvc.getOrgidt(), acttvc.getCusidt(),
                        acttvc.getApcode(), acttvc.getCurcde(), wk_txnamt, wk_txnamt, true, false)) {
                    logger.error("�˻���������!");
                    MessageUtil.addError("�������: " + acttvc.getSetseq());
                    throw new RuntimeException();
                } else {
                    //������������
                    updateActobf(session, acttvc.getOrgidt(), acttvc.getCusidt(),
                            acttvc.getApcode(), acttvc.getCurcde(), wk_txnamt);

                    //����ACTTVC
                    int result = mapper.updateOneVchsts(ACEnum.VCHSTS_TVC_INSVCH_SET.getStatus(),
                            acttvc.getOrgidt(), acttvc.getTlrnum(), acttvc.getVchset(), acttvc.getSetseq());
                    if (result != 1) {
                        throw new RuntimeException();
                    }

                    //����ACTVCH
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
            logger.error("��ƽʧ��", e);
            return false;
        } finally {
            session.close();
        }
        return true;
    }


    /**
     * ������������
     *
     * @param session db
     * @param orgidt  this
     * @param cusidt  this
     * @param apcode  this
     * @param curcde  this
     * @param txnamt  ���׽��  ��ɾ��ʱ ȡ�� ���룩
     */
    private void updateActobf(SqlSession session, String orgidt, String cusidt,
                              String apcode, String curcde, long txnamt) {

        ActobfMapper mapper = session.getMapper(ActobfMapper.class);
        ActobfExample example = new ActobfExample();
        example.createCriteria().andSysidtEqualTo(this.sysidt).andOrgidtEqualTo(orgidt)
                .andCusidtEqualTo(cusidt).andApcodeEqualTo(apcode).andCurcdeEqualTo(curcde);
        List<Actobf> records = mapper.selectByExample(example);
        if (records == null || records.size() != 1) {
            throw new RuntimeException("�������������");
        }
        Actobf actobf = records.get(0);

        actobf.setAvabal(actobf.getAvabal() + txnamt);
        actobf.setBokbal(actobf.getBokbal() + txnamt);

        mapper.updateByExample(actobf, example);
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
