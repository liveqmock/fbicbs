package cbs.batch.ac.bcb8542;

import cbs.batch.ac.bcb8542.dao.BCB8542Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actibl;
import cbs.repository.account.maininfo.model.Actvth;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-10
 * Time: 14:10:35
 * 同业存放类帐户计息
 */
@Service
public class BCB8542Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8542Handler.class);
    @Inject
    private BCB8542Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    private String intmak;    // accept intmak
    private SctPojo sct;
    private int wkIpydats;
    private int wkNwkdays;
    private int wkCrndats;
    private long wkTxnamt;
    private String cirIrtcde;
    private BigDecimal wkdCurirt;
    private BigDecimal wkdNxtirt;
    private String cirIrtkd2;
    private String cirIrtkd1;
    private Date cirEffdat;
    private String cirModflg;
    private int wkEffdats;
    private String wkModflg;
    private BigDecimal wkcCurirt;
    private BigDecimal wkcNxtirt;
    private BigDecimal cxrCurrat;

    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8542 同业存放类帐户计息 BEGIN");
        try {
            // 命令行参数
            String[] args = parameterData.getCommandLine();
            if (args == null || args.length < 1) {
                logger.error("命令行参数不能为空！");
                return;
            }
            this.intmak = parameterData.getCommandLine()[0];
            initProps();
            if (sct != null) {
                this.wkIpydats = sct.getIpydats();
                this.wkNwkdays = sct.getNwkdays();
                this.wkCrndats = sct.getCrndats();
                if ("Y".equalsIgnoreCase(sct.getIpymak())) {
                    if ("Y".equalsIgnoreCase(this.intmak)) {
                        this.wkCrndats = this.wkIpydats;
                    } else {
                        this.wkNwkdays = this.wkIpydats;
                    }
                } else {
                    if ("Y".equalsIgnoreCase(this.intmak)) {
                        return;
                    }
                }
                if ("N".equalsIgnoreCase(this.intmak)) {
                    List<Actvth> vthList = mapper.selectVths(sct.getCrndat());
                    if (vthList != null && !vthList.isEmpty()) {
                        for (Actvth vth : vthList) {
                            if (ACEnum.RVSLBL_TRUE.getStatus().equalsIgnoreCase(vth.getRvslbl())) {
                                this.wkTxnamt = 0 - vth.getTxnamt();
                            } else {
                                this.wkTxnamt = vth.getTxnamt();
                            }
                            if (vth.getTxnamt() > 0) {
                                mapper.updateIblCinbal(wkTxnamt, systemService.getSysidtAC(),
                                        vth.getOrgid3(), vth.getCusidt(), vth.getApcode(), vth.getCurcde(), vth.getValdat());
                            }
                            if (vth.getTxnamt() < 0) {
                                mapper.updateIblDinbal(wkTxnamt, systemService.getSysidtAC(),
                                        vth.getOrgid3(), vth.getCusidt(), vth.getApcode(), vth.getCurcde(), vth.getValdat());
                            }
                        }
                    }
                    mapper.updateIblcdUp();
                    mapper.updateIblcdDown();
                }
                List<Actact> actList = mapper.selectActs(ACEnum.RECSTS_VALID.getStatus(), systemService.getSysidtAC());
                if (actList != null && !actList.isEmpty()) {
                    Actibl ibl = null;
                    for (Actact act : actList) {
                        ibl = new Actibl();
                        if (act.getBokbal() < 0) {
                            ibl.setCinbal(0L);
                            ibl.setDinbal(act.getBokbal());
                        } else {
                            ibl.setCinbal(act.getBokbal());
                            ibl.setDinbal(0L);
                        }
                        this.cirIrtcde = act.getDinrat();
                        ibl.setDinrat(act.getDinrat());
                        if ("999".equals(act.getDinrat())) {
                            if (ACEnum.CURCDE_001.getStatus().equals(act.getCurcde())) {
                                this.wkdCurirt = act.getDratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                                this.wkdNxtirt = wkdCurirt;
                            } else {
                                this.wkdCurirt = act.getDratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                                this.wkdNxtirt = wkdCurirt;
                            }
                        } else {
                            // TODO CIR-IRTKD1 从未调用
                            CirProp cirProp = mapper.selectCir("0001-01-01", this.cirIrtkd1, this.cirIrtkd2, act.getCurcde());
                            if (cirProp == null) {
                                if ("001".equals(act.getCurcde())) {
                                    this.wkdCurirt = act.getDratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                                    this.wkdNxtirt = wkdCurirt;
                                } else {
                                    this.wkdCurirt = act.getDratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                                    this.wkdNxtirt = wkdCurirt;
                                }
                            } else {
                                this.cirEffdat = cirProp.getEffdat();
                                this.cirModflg = cirProp.getModflg();
                                this.wkdCurirt = cirProp.getCurirt();
                                this.wkdNxtirt = cirProp.getNxtirt();
                                this.wkEffdats = cirProp.getEffdats();
                                if ("Y".equalsIgnoreCase(this.cirModflg) && this.wkEffdats < this.wkNwkdays
                                        && this.wkEffdats >= this.wkCrndats) {
                                    this.wkModflg = "Y";
                                }
                            }
                        }
                        this.cirIrtcde = act.getCinrat();
                        ibl.setCinrat(act.getCinrat());
                        if ("999".equals(act.getCinrat())) {
                            if (ACEnum.CURCDE_001.getStatus().equals(act.getCurcde())) {
                                this.wkcCurirt = act.getCratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                                this.wkcNxtirt = wkcCurirt;
                            } else {
                                this.wkcCurirt = act.getCratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                                this.wkcNxtirt = wkcCurirt;
                            }
                        } else {
                            CirProp cirProp = mapper.selectCir("0001-01-01", this.cirIrtkd1, this.cirIrtkd2, act.getCurcde());
                            if (cirProp == null) {
                                if ("001".equals(act.getCurcde())) {
                                    this.wkcCurirt = act.getCratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                                    this.wkcNxtirt = wkcCurirt;
                                } else {
                                    this.wkcCurirt = act.getCratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                                    this.wkcNxtirt = wkcCurirt;
                                }
                            } else {
                                this.cirEffdat = cirProp.getEffdat();
                                this.cirModflg = cirProp.getModflg();
                                this.wkcCurirt = cirProp.getCurirt();
                                this.wkcNxtirt = cirProp.getNxtirt();
                                this.wkEffdats = cirProp.getEffdats();
                                if ("Y".equalsIgnoreCase(this.cirModflg) && this.wkEffdats < this.wkNwkdays
                                        && this.wkEffdats >= this.wkCrndats) {
                                    this.wkModflg = "Y";
                                }
                            }
                        }
                        //
                        ibl.setCrirat(act.getCratsf());
                        ibl.setDrirat(act.getDratsf());
                        if ("Y".equals(this.wkModflg)) {
                            ibl.setNwkday((short) (this.wkEffdats - this.wkCrndats));
                            ibl.setIbldat(sct.getCrndat());
                            ibl.setDdrirt(this.wkdCurirt);
                            ibl.setDcrirt(this.wkcCurirt);
                            insertIbl(act, ibl);//  5211
                            ibl.setNwkday((short) (this.wkNwkdays - this.wkEffdats));
                            ibl.setIbldat(this.cirEffdat);
                            ibl.setDdrirt(this.wkdNxtirt);
                            ibl.setDcrirt(this.wkcNxtirt);
                            insertIbl(act, ibl);//  5211
                        } else {
                            ibl.setDdrirt(this.wkdCurirt);
                            ibl.setDcrirt(this.wkcCurirt);
                            ibl.setNwkday((short) (this.wkNwkdays - this.wkCrndats));
                            ibl.setIbldat(sct.getCrndat());
                            insertIbl(act, ibl);//  5211
                        }
                    }
                }
            } else {
                logger.error("actsct is null");
                return;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8542 同业存放类帐户计息 END");
    }
    // 5211

    private void insertIbl(Actact act, Actibl ibl) {
        this.cxrCurrat = mapper.qryCurrat(act.getCurcde());
        mapper.insertIbl(act.getSysidt(), act.getOrgidt(), act.getCusidt(), act.getApcode(), act.getCurcde(),
                ibl.getIbldat(), ibl.getCinbal(), ibl.getDinbal(), ibl.getCinrat(), this.cxrCurrat, ibl.getDinrat(),
                ibl.getCrirat(), ibl.getDrirat(), ibl.getDcrirt(), ibl.getDdrirt(), ibl.getNwkday());
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    private void initProps() {
        this.sct = mapper.selectSctByNum("0001-01-01", Short.parseShort(systemService.getSysidtAC()));
    }
}