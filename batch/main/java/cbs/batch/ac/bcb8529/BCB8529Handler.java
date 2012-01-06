package cbs.batch.ac.bcb8529;

import cbs.batch.ac.bcb8529.dao.BCB8529Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.IbatisFactory;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actfrz;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8529Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8529Handler.class);
    @Inject
    private BCB8529Mapper mapper;
    private String wk_crndat;
    private List<Actfrz> frzList;
    private String have_recorder = "";
    private String wk_actnum = "";
    private String frz_actnum;
    private String wk_first = "Y";
    private String wk_orgidt = "";
    private String wk_cusidt = "";
    private String wk_apcode = "";
    private String wk_curcde = "";
    private BigDecimal wk_boksum;
    private BigDecimal wk_frzsum;
    private long act_bokval;
    private String wk_all_frz;
    private String act_frzsts;
    private BigDecimal wk_sum_frzamt;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            wk_crndat = mapper.selectCrndat((short) 8, ACEnum.RECSTS_VALID.getStatus());
            if (wk_crndat == null) {
                logger.info("ACTSCT.CRNDAT查询结果为空！");
                throw new RuntimeException("ACTSCT.CRNDAT查询结果为空！");
            }
            frzList = mapper.selectActfrzs(wk_crndat, ACEnum.SYSIDT_AC.getStatus(), ACEnum.RECSTS_VALID.getStatus());
            if (frzList == null || frzList.isEmpty()) {
                logger.info("ACTFRZ(冻结帐户资料)查询结果为空！");
                return;
            }
        } catch (Exception e) {
            logger.error("初始化错误！", e);
            return;
        }

        //事务处理
        try {
            mapper.updateAct(ACEnum.RECSTS_VALID.getStatus());
            for (Actfrz frz : frzList) {
                this.frz_actnum = frz.getCusidt() + frz.getApcode() + frz.getCurcde();
                if (!this.wk_actnum.equalsIgnoreCase(frz_actnum)) {
                    if ("Y".equals(this.wk_first)) {
                        this.wk_orgidt = frz.getOrgidt();
                        this.wk_actnum = frz_actnum;
                        this.wk_cusidt = wk_actnum.substring(0, 7);
                        this.wk_apcode = wk_actnum.substring(7, 11);
                        this.wk_curcde = wk_actnum.substring(11, 14);
                        this.wk_first = "N";
                        this.have_recorder = "Y";
                    }
                    if (!this.wk_orgidt.equalsIgnoreCase(frz.getOrgidt())
                            || !this.wk_curcde.equalsIgnoreCase(frz.getCurcde())) {
                        this.wk_boksum = new BigDecimal(0);
                        this.wk_frzsum = new BigDecimal(0);
                    }
                    this.act_bokval = mapper.selectBokbal(ACEnum.SYSIDT_AC.getStatus(), frz.getOrgidt()
                            , frz.getCusidt(), frz.getApcode(), frz.getCurcde());
                    // this.ccy_decpos = mapper.selectDecpos(frz.getCurcde());
                    this.wk_boksum = this.wk_boksum.add(new BigDecimal(this.act_bokval));
                    updateAct();
                    this.wk_orgidt = frz.getOrgidt();
                    this.wk_actnum = this.frz_actnum;
                    this.wk_cusidt = wk_actnum.substring(0, 7);
                    this.wk_apcode = wk_actnum.substring(7, 11);
                    this.wk_curcde = wk_actnum.substring(11, 14);
                    this.wk_all_frz = "";
                    this.act_frzsts = ACEnum.FRZSTS_NORMAL.getStatus();
                    this.wk_sum_frzamt = new BigDecimal(0);
                }
                if (ACEnum.FRZSTS_NOOPER.getStatus().equalsIgnoreCase(frz.getFrzflg())) {
                    this.act_frzsts = frz.getFrzflg();
                    this.wk_all_frz = "Y";
                }
                if (ACEnum.FRZSTS_NOSAVE.getStatus().equalsIgnoreCase(frz.getFrzflg())) {
                    if (!ACEnum.FRZSTS_NOOPER.getStatus().equalsIgnoreCase(this.act_frzsts)) {
                        this.act_frzsts = frz.getFrzflg();
                    }
                }
                if (ACEnum.FRZSTS_NODRAW.getStatus().equalsIgnoreCase(frz.getFrzflg())) {
                    this.wk_all_frz = "Y";
                    if (ACEnum.FRZSTS_NORMAL.getStatus().equalsIgnoreCase(this.act_frzsts)) {
                        this.act_frzsts = ACEnum.FRZSTS_RECORD.getStatus();
                    }
                }
                if (ACEnum.FRZSTS_NOSAVE_1.getStatus().equalsIgnoreCase(frz.getFrzflg())) {
                    if (!ACEnum.FRZSTS_NOOPER.getStatus().equalsIgnoreCase(this.act_frzsts)
                            && !ACEnum.FRZSTS_NOSAVE.getStatus().equalsIgnoreCase(this.act_frzsts)) {
                        this.act_frzsts = frz.getFrzflg();
                    }
                }
                if (ACEnum.FRZSTS_NODRAW_1.getStatus().equalsIgnoreCase(frz.getFrzflg())) {
                    this.wk_sum_frzamt = this.wk_sum_frzamt.add(new BigDecimal(frz.getFrzamt()));
                    if (ACEnum.FRZSTS_NORMAL.getStatus().equalsIgnoreCase(this.act_frzsts)) {
                        this.act_frzsts = ACEnum.FRZSTS_RECORD.getStatus();
                    }
                }
                this.wk_frzsum = this.wk_frzsum.add(new BigDecimal(frz.getFrzamt()));
            }
            if ("Y".equals(this.have_recorder)) {
                updateAct();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateAct() {
        if ("Y".equals(this.wk_all_frz)) {
            mapper.updateAct1(this.act_frzsts, ACEnum.SYSIDT_AC.getStatus(), this.wk_orgidt,
                    this.wk_cusidt, this.wk_apcode, this.wk_curcde, ACEnum.RECSTS_VALID.getStatus());
        } else if (this.wk_sum_frzamt.compareTo(new BigDecimal(0)) != 0) {
            mapper.updateAct2(this.wk_sum_frzamt.longValue(), this.act_frzsts, ACEnum.SYSIDT_AC.getStatus(), this.wk_orgidt,
                    this.wk_cusidt, this.wk_apcode, this.wk_curcde, ACEnum.RECSTS_VALID.getStatus());
        } else {
            mapper.updateAct3(this.act_frzsts, ACEnum.SYSIDT_AC.getStatus(), this.wk_orgidt,
                    this.wk_cusidt, this.wk_apcode, this.wk_curcde, ACEnum.RECSTS_VALID.getStatus());
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }
}