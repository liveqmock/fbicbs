package cbs.batch.ac.bcb8523;

import cbs.batch.ac.bcb8523.dao.VoucherMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.BeanHelper;
import cbs.repository.account.maininfo.dao.*;
import cbs.repository.account.maininfo.model.*;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 批量处理：入帐.  BCB8523
 * User: zhanrui
 * Date: 2010-12-2
 * Time: 15:58:18
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB8523Handler extends AbstractACBatchJobLogic {

    private static final Logger logger = LoggerFactory.getLogger(BCB8523Handler.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Inject
    private BatchSystemService systemService;

    @Inject
    private VoucherMapper voucherMapper;

    @Inject
    private ActvchMapper actvchMapper;
    @Inject
    private ActvchExample actvchExample;

    @Inject
    private ActvthMapper actvthMapper;
    @Inject
    private ActvthExample actvthExample;
    @Inject
    private ActactMapper actactMapper;
    @Inject
    private ActactExample actactExample;
    @Inject
    private ActpleMapper actpleMapper;
    @Inject
    private ActpleExample actpleExample;
    @Inject
    private ActbvaMapper actbvaMapper;

    //private ActblhMapper actblhMapper;

    private String sysidt;
    private String bizStrDate;
    private Date bizDateDate;
    private List<Actvch> actvchList;


    public void setVoucherMapper(VoucherMapper voucherMapper) {
        this.voucherMapper = voucherMapper;
    }

    public void setActvchMapper(ActvchMapper actvchMapper) {
        this.actvchMapper = actvchMapper;
    }

    public void setActvchExample(ActvchExample actvchExample) {
        this.actvchExample = actvchExample;
    }

    public void setActvthMapper(ActvthMapper actvthMapper) {
        this.actvthMapper = actvthMapper;
    }

    public void setActvthExample(ActvthExample actvthExample) {
        this.actvthExample = actvthExample;
    }

    public void setActactMapper(ActactMapper actactMapper) {
        this.actactMapper = actactMapper;
    }

    public void setActactExample(ActactExample actactExample) {
        this.actactExample = actactExample;
    }

    public void setActpleMapper(ActpleMapper actpleMapper) {
        this.actpleMapper = actpleMapper;
    }

    public void setActpleExample(ActpleExample actpleExample) {
        this.actpleExample = actpleExample;
    }

    public void setActbvaMapper(ActbvaMapper actbvaMapper) {
        this.actbvaMapper = actbvaMapper;
    }

    //===================================================================

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            initBatch();
            initActvchList();
            processActtvcList();
            processClearActvch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }


    private void initBatch() throws ParseException {
        this.sysidt = systemService.getSysidtAC();
        this.bizStrDate = systemService.getBizDate();
        this.bizDateDate = sdf.parse(this.bizStrDate);

        DateConverter dateConverter = new DateConverter();
        ConvertUtils.register(dateConverter, Date.class);
    }

    private void initActvchList() {
        this.actvchList = voucherMapper.selectActvchByRecsts(ACEnum.RECSTS_VALID.getStatus());
    }

    /**
     * 主处理程序
     *
     * @return 处理传票笔数
     * @throws ParseException
     */
    private int processActtvcList() throws ParseException {
        int count = 0;
        Actact actact = new Actact();

        long wk_txnamt = 0;
        long wk_secamt = 0;

        int wk_ddrcnt = 0;
        long wk_ddramt = 0;
        int wk_dcrcnt = 0;
        long wk_dcramt = 0;

        for (Actvch actvch : this.actvchList) {
            //特殊核算码处理
            if ("9991".equals(actvch.getApcode())) {
                actvch.setCusidt("9990000");
            }

            //冲正类传票处理
            if (ACEnum.RVSLBL_TRUE.getStatus().equals(actvch.getRvslbl())) {
                wk_txnamt = 0 - actvch.getTxnamt();
                wk_secamt = 0 - actvch.getSecamt();
            } else {
                wk_txnamt = actvch.getTxnamt();
                wk_secamt = actvch.getSecamt();
            }

            //第一笔传票及帐号未发生变化 初始化金额变量 及ACTACT
            if (count == 0 ||
                    (!actvch.getOrgid3().equals(actact.getOrgidt())
                            || !actvch.getCusidt().equals(actact.getCusidt())
                            || !actvch.getApcode().equals(actact.getApcode())
                            || !actvch.getCurcde().equals(actact.getCurcde()))) {
                if (StringUtils.isNotEmpty(actact.getCusidt())) {
                    updateActact(actact, wk_ddrcnt, wk_ddramt, wk_dcrcnt, wk_dcramt);
                }
                actact = selectValidActact(actvch.getOrgidt(), actvch.getCusidt(), actvch.getApcode(), actvch.getCurcde());
                if (actact == null) {
                    //TODO
                    logger.error("未找到与传票表中对应的账户记录。");
                    logger.error("actvch 对象内容为：" + actvch.toString());
                    throw new RuntimeException("未找到与传票表中对应的账户记录。");
                }
                wk_ddrcnt = wk_dcrcnt = 0;
                wk_ddramt = wk_dcramt = 0;
            }

            //积数累加
            actact.setBokbal(actact.getBokbal() + wk_txnamt);
            if (actvch.getTxnamt() < 0) {
                wk_ddrcnt++;
                wk_ddramt += wk_txnamt;
            } else {
                wk_dcrcnt++;
                wk_dcramt += wk_txnamt;
            }

            //追加传票历史表
            insertActvth(actvch);

            if (ACEnum.ACTTYP_FXE.getStatus().equals(actact.getActtyp())
                    && StringUtils.isNotBlank(actvch.getSecccy())) {
                //TODO 外汇类 暂不处理
                //processActfxe();
            }

            //根据ACTACT的帐户类型 进行 损益扩展帐户表处理
            if (ACEnum.ACTTYP_PLE.getStatus().equals(actact.getActtyp())) {
                processActple(actvch, wk_txnamt);
            }

            /*
                0-不计息
                1-AIF分段计息
                2-TIF计息
                3-CBH计息,AIF协定户计息
                4-AIF不分段计息
                5-同业存放类计息
                6-903计息
                7-901单限额协定帐户计息

                INTFLG_NO("0"),
                INTFLG_AIF("1"),
                INTFLG_TIF("2"),
                INTFLG_AIFCON("3"),
                INTFLG_AIF_4("4"),
             */
            //判断ACTACT中本帐户的计息类型 进行积数处理
            if (ACEnum.INTFLG_AIF.getStatus().equals(actact.getIntflg())
                    || ACEnum.INTFLG_AIFCON.getStatus().equals(actact.getIntflg())
                    || ACEnum.INTFLG_AIF_4.getStatus().equals(actact.getIntflg())) {
                Date tmp_valdat = sdf.parse(actvch.getValdat());

                if (tmp_valdat.before(this.bizDateDate)) {//传票中的起息日在当前业务日之前（不包括相等）
                    processActblh(actact, wk_txnamt, sdf.parse(actvch.getValdat()), actvch.getValdat());
                    insertActbva(actvch, tmp_valdat);
                } else {
                    if (ACEnum.INTFLG_AIFCON.getStatus().equals(actact.getIntflg())) {
                        insertActbva(actvch, tmp_valdat);
                    }
                }
            }
            count++;
        }
        if (count > 0) {
            updateActact(actact, wk_ddrcnt, wk_ddramt, wk_dcrcnt, wk_dcramt);
        }

        return count;
    }

    private void processClearActvch() {
        actvchExample.clear();
        actvchExample.createCriteria();
        actvchMapper.deleteByExample(actvchExample);
    }

    /**
     * 积数处理(修改积数历史表)
     *
     * @param actact
     * @param wk_txnamt
     * @param tmp_valdat
     * @param vch_valdat
     */
    private void processActblh(Actact actact, long wk_txnamt, Date tmp_valdat, String vch_valdat) throws ParseException {
        long wk_accm = wk_txnamt;
        int ccy_decpos = voucherMapper.selectDecposFromActccy(actact.getCurcde());
        // TODO 未找到 if (ccy_decp)

        //超前起息计算
        if (tmp_valdat.before(actact.getLindth())) {
            wk_accm = voucherMapper.selectAccmByLindth(wk_accm, sdf.format(actact.getLindth()), Integer.parseInt(this.sysidt));
        } else {
            wk_accm = voucherMapper.selectAccmByValdat(wk_accm, vch_valdat, Integer.parseInt(this.sysidt));
        }

        //核算码表
        String wkt_glcode = voucherMapper.selectGlcodeFromActapc(actact.getApcode());
        //总帐码表  余额方向标志
        String wkt_glcbal = voucherMapper.selectGlcbalFromActglc(wkt_glcode);

        if (ACEnum.GLCBAL_C.getStatus().equals(wkt_glcbal)) {
            actact.setCraccm(actact.getCraccm() + wk_accm);
        }
        if (ACEnum.GLCBAL_D.getStatus().equals(wkt_glcbal)) {
            actact.setDraccm(actact.getDraccm() + wk_accm);
        }
        if (ACEnum.GLCBAL_B.getStatus().equals(wkt_glcbal)) {
            if (actact.getBokbal() < 0) {
            } else {
                if (actact.getBokbal() < 0) {
                    actact.setDraccm(actact.getDraccm() + wk_accm);
                } else {
                    actact.setCraccm(actact.getCraccm() + wk_accm);
                }
            }
        }
        //UPDATE
        ActblhParaBean para = new ActblhParaBean();

        para.setActOrgidt(actact.getOrgidt());
        para.setActCusidt(actact.getCusidt());
        para.setActApcode(actact.getApcode());
        para.setActCurcde(actact.getCurcde());

        para.setActLintdt(actact.getLintdt());
        para.setTmpValdat(tmp_valdat);

        para.setWktGlcbal(wkt_glcbal);

        para.setWkTxnamt(wk_txnamt);
        para.setVchValdat(sdf.parse(vch_valdat));

        int result = -1;
        result = this.voucherMapper.updateActblhDraccm1(para);
        logger.info("更新积数历史表记录数" + result);
        result = this.voucherMapper.updateActblhDraccm2(para);
        logger.info("更新积数历史表记录数" + result);
        result = this.voucherMapper.updateActblhCraccm1(para);
        logger.info("更新积数历史表记录数" + result);
        result = this.voucherMapper.updateActblhCraccm2(para);
        logger.info("更新积数历史表记录数" + result);
    }

    /**
     * 冲帐倒起息后起息资料 处理
     *
     * @param actvch
     * @param tmp_valdat
     */
    private void insertActbva(Actvch actvch, Date tmp_valdat) throws ParseException {

        Actbva actbva = new Actbva();
        actbva.setOrgidt(actvch.getOrgid3());
        actbva.setCusidt(actvch.getCusidt());
        actbva.setApcode(actvch.getApcode());
        actbva.setCurcde(actvch.getCurcde());
        actbva.setErydat(this.bizDateDate);
        actbva.setTlrnum(actvch.getTlrnum());
        actbva.setVchset(actvch.getVchset());
        actbva.setSetseq(actvch.getSetseq());
        actbva.setBvadat(sdf.parse(actvch.getValdat()));
        actbva.setBvaamt(actvch.getTxnamt());
        actbva.setBvasts(ACEnum.BVASTS_FALSE.getStatus());
        actbva.setRecsts(ACEnum.RECSTS_VALID.getStatus());

        if (ACEnum.RVSLBL_TRUE.getStatus().equals(actvch.getRvslbl())) {
            actbva.setBvaflg(ACEnum.BVAFLG_REVERSE.getStatus());
            actbvaMapper.insert(actbva);
        } else {
            if (tmp_valdat.before(this.bizDateDate)) {
                actbva.setBvaflg(ACEnum.BVAFLG_BACK.getStatus());
                actbvaMapper.insert(actbva);
            }
            if (tmp_valdat.after(this.bizDateDate)) {
                actbva.setBvaflg(ACEnum.BVAFLG_LATE.getStatus());
                actbvaMapper.insert(actbva);
            }
        }
    }


    /**
     * 处理损益扩展表
     *
     * @param actvch
     * @param wk_txnamt
     */
    private void processActple(Actvch actvch, long wk_txnamt) {
        actpleExample.clear();
        actpleExample.createCriteria().andSysidtEqualTo(this.sysidt).andOrgidtEqualTo(actvch.getOrgid3())
                .andCusidtEqualTo(actvch.getCusidt()).andApcodeEqualTo(actvch.getApcode())
                .andCurcdeEqualTo(actvch.getCurcde()).andCorapcEqualTo(actvch.getCorapc());
        List<Actple> records = actpleMapper.selectByExample(actpleExample);
        Actple actple;
        if (records.size() == 0) {
            actple = new Actple();
            actple.setSysidt(this.sysidt);
            actple.setOrgidt(actvch.getOrgid3());
            actple.setCusidt(actvch.getCusidt());
            actple.setApcode(actvch.getApcode());
            actple.setCurcde(actvch.getCurcde());
            actple.setCorapc(actvch.getCorapc());
            actple.setOpndat(this.bizDateDate);
            actple.setLentdt(this.bizDateDate);
            actple.setRecsts(ACEnum.RECSTS_VALID.getStatus());
            actpleMapper.insert(actple);
        } else {
            actple = records.get(0);
        }

        actple.setAvabal(actple.getAvabal() + wk_txnamt);
        if (actvch.getTxnamt() < 0) {
            actple.setDdramt(actple.getDdramt().add((new BigDecimal(wk_txnamt / 100))));
            actple.setMdramt(actple.getMdramt() + wk_txnamt);
            actple.setYdramt(actple.getYdramt() + wk_txnamt);
            actple.setDdrcnt(actple.getDdrcnt() + 1);
            actple.setMdrcnt(actple.getMdrcnt() + 1);
            actple.setYdrcnt(actple.getYdrcnt() + 1);
        } else {
            actple.setDcramt(actple.getDcramt().add((new BigDecimal(wk_txnamt / 100))));
            actple.setMcramt(actple.getMcramt() + wk_txnamt);
            actple.setYcramt(actple.getYcramt() + wk_txnamt);
            actple.setDcrcnt(actple.getDcrcnt() + 1);
            actple.setMcrcnt(actple.getMcrcnt() + 1);
            actple.setYcrcnt(actple.getYcrcnt() + 1);
        }
        //TODO ?
        actpleExample.createCriteria().andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
        actpleMapper.updateByExample(actple, actpleExample);
    }

    /**
     * 传票历史表
     *
     * @param actvch
     */
    private void insertActvth(Actvch actvch) {
        Actvth record = new Actvth();
        try {
            //BeanUtils.copyProperties(record, actvch);
            BeanHelper.copy(record, actvch);
            record.setValdat(sdf.parse(actvch.getValdat()));
            record.setErydat(sdf.parse(actvch.getErydat()));
            actvthMapper.insert(record);
        } catch (Exception e) {
            throw new RuntimeException("传票历史表ACTVTH新增记录时出错！", e);
        }

    }

    private void updateActact(Actact actact, int wk_ddrcnt, long wk_ddramt, int wk_dcrcnt, long wk_dcramt) {
        actactExample.clear();
        actactExample.createCriteria().andSysidtEqualTo(this.sysidt)
                .andOrgidtEqualTo(actact.getOrgidt()).andCusidtEqualTo(actact.getCusidt())
                .andApcodeEqualTo(actact.getApcode()).andCurcdeEqualTo(actact.getCurcde())
                .andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
        Actact record = actactMapper.selectByExample(actactExample).get(0);
        record.setBokbal(actact.getBokbal());
        record.setDdrcnt(record.getDdrcnt() + wk_ddrcnt);
        record.setDcrcnt(record.getDcrcnt() + wk_dcrcnt);
        record.setDdramt(record.getDdramt() + wk_ddramt);
        record.setDcramt(record.getDcramt() + wk_dcramt);

        record.setMdrcnt(record.getMdrcnt() + wk_ddrcnt);
        record.setMcrcnt(record.getMcrcnt() + wk_dcrcnt);
        record.setMdramt(record.getMdramt() + wk_ddramt);
        record.setMcramt(record.getMcramt() + wk_dcramt);

        record.setYdrcnt(record.getYdrcnt() + wk_ddrcnt);
        record.setYcrcnt(record.getYcrcnt() + wk_dcrcnt);
        record.setYdramt(record.getYdramt() + wk_ddramt);
        record.setYcramt(record.getYcramt() + wk_dcramt);

        record.setDraccm(actact.getDraccm());
        record.setCraccm(actact.getCraccm());

        record.setLentdt(this.bizDateDate);

        record.setAvabal(actact.getBokbal());
        record.setUpddat(this.bizDateDate);

        actactMapper.updateByExampleSelective(record, actactExample);
    }

    private Actact selectValidActact(String orgidt, String cusidt, String apcode, String curcde) {
        actactExample.clear();
        actactExample.createCriteria().andOrgidtEqualTo(orgidt).andCusidtEqualTo(cusidt)
                .andApcodeEqualTo(apcode).andCurcdeEqualTo(curcde);
        List<Actact> records = actactMapper.selectByExample(actactExample);
        if (records.size() == 0) {
            //TODO LOGGER
            return null;
        }
        return records.get(0);
    }

}
