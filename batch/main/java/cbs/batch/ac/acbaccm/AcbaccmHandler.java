package cbs.batch.ac.acbaccm;

import cbs.batch.ac.acbaccm.dao.AcbaccmMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.dao.ActblhMapper;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.ActactExample;
import cbs.repository.account.maininfo.model.Actblh;
import cbs.repository.account.other.dao.ActcitMapper;
import cbs.repository.account.other.model.Actcit;
import cbs.repository.account.tempinfo.model.Actcir;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 清未结息户积数(INSERT ACTBLH,UPDATE ACTACT)
 * User: zhangxiaobo
 * Date: 2010-2-22
 */
//@Service("AcbaccmHandler")
@Service
public class AcbaccmHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(AcbaccmHandler.class);
    @Inject
    private AcbaccmMapper mapper;
    @Inject
    private ActactMapper actMapper;
    @Inject
    private ActblhMapper blhMapper;
    @Inject
    private ActcitMapper citMapper;
    @Inject
    private ActsctMapper sctMapper;
    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
    private final BigDecimal zeroBD = new BigDecimal(0);
    private Actsct actsct;
    private Date sct_ipydat;
    private Date rev_erydat;
    private Date sct_ipydat1;
    private String crndat;
    private List<Actact> actList;

    private String oldOrgidt = "";
    private String orgnam = "";
    private String ccy_curcde;
    private int ccy_decpos;
    private BigDecimal wk_cratsf;
    private BigDecimal wk_dratsf;
    private Long wk_craccm;
    private Long wk_draccm;
    private String wk_irtkd = "";
    private int cit_yintsq;
    private Actcit actcit;
    private final String err_reason = "未计息";

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            actsct = initSct();
            if (actsct == null) {
                logger.info("ACTSCT查询结果为空！");
                return;
            }
            sct_ipydat = actsct.getIpydat();
            this.sct_ipydat1 = dayAdd(actsct.getIpydat(), 1);
            rev_erydat = dayAdd(actsct.getIpydat(), -1);
            crndat = sdfdate.format(actsct.getCrndat());
            actList = initActList();
            if (actList == null) {
                logger.error("ACTACT查询结果为空！");
                throw new RuntimeException("ACTACT查询结果为空!");
            }
        } catch (Exception e) {
            logger.error("初始化错误！", e);
            return;
        }

        try {
            for (Actact act : actList) {
                if (!this.oldOrgidt.equals(act.getOrgidt())) {
                    if (!"".equals(this.oldOrgidt)) {
                        this.oldOrgidt = act.getOrgidt();
                    }
                    this.orgnam = mapper.selectOrgnamByIdt(act.getOrgidt());
                    this.oldOrgidt = act.getOrgidt();
                }
                String intcyc = act.getIntcyc();
                String IpydatMon = sdfdate.format(this.sct_ipydat).substring(5, 7);
                if ("M".equals(intcyc) || "S".equals(intcyc) || "H".equals(intcyc)
                        && ("06".equalsIgnoreCase(IpydatMon) || "12".equalsIgnoreCase(IpydatMon))
                        || "Y".equals(intcyc) && "12".equalsIgnoreCase(IpydatMon)) {
                    this.ccy_curcde = act.getCurcde();
                    this.ccy_decpos = mapper.selectPosByCde(this.ccy_curcde);
                    this.wk_cratsf = zeroBD;
                    this.wk_dratsf = zeroBD;
                    if ("999".equals(act.getCinrat())) {
                        if ("001".equals(act.getCurcde())) {
                            this.wk_cratsf = act.getCratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                        } else {
                            this.wk_cratsf = act.getCratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                        }
                    } else {
                        this.wk_irtkd = act.getCinrat();
                        if(" ".equals(this.wk_irtkd)){
                            this.wk_irtkd = "   ";
                        }
                        Actcir cir = mapper.selectCirByIrtkd(this.ccy_curcde, wk_irtkd.substring(0, 1), wk_irtkd.substring(1, 3));
                        if (cir == null) {
                            this.wk_cratsf = zeroBD;
                        } else {
                            act.setCratsf(cir.getIrtval());
                            this.wk_cratsf = cir.getCurirt();
                        }
                    }
                    if ("999".equals(act.getDinrat())) {
                        if ("001".equals(act.getCurcde())) {
                            this.wk_dratsf = act.getDratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                        } else {
                            this.wk_dratsf = act.getDratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                        }
                    } else {
                        this.wk_irtkd = act.getDinrat();
                        if(this.wk_irtkd == null || "".equals(this.wk_irtkd.trim())){
                            this.wk_irtkd = "   ";
                        }
                        Actcir cir = mapper.selectCirByIrtkd(this.ccy_curcde, wk_irtkd.substring(0, 1), wk_irtkd.substring(1, 3));
                        if (cir == null) {
                            this.wk_dratsf = zeroBD;
                        } else {
                            act.setDratsf(cir.getIrtval());
                            this.wk_dratsf = cir.getCurirt();
                        }
                    }
                    if (this.wk_dratsf.compareTo(zeroBD) != 0 && act.getDacint() != 0
                            || this.wk_cratsf.compareTo(zeroBD) != 0 && act.getCacint() != 0
                            || this.wk_dratsf.compareTo(zeroBD) == 0 || this.wk_cratsf.compareTo(zeroBD) == 0) {
                        if ("1111-11-11".equals(act.getLindth())) {
                            act.setLindth(act.getOpndat());
                        }
                        Actblh blh = new Actblh();
                        blh.setOrgidt(act.getOrgidt());
                        blh.setCusidt(act.getCusidt());
                        blh.setApcode(act.getApcode());
                        blh.setCurcde(act.getCurcde());
                        blh.setErydat(rev_erydat);
                        blh.setDepnum(act.getDepnum());
                        blh.setActbal(act.getAvabal());
                        blh.setDraccm(act.getDraccm());
                        blh.setCraccm(act.getCraccm());
                        blh.setBngdat(act.getLindth());
                        blh.setDrirat(this.wk_dratsf);
                        blh.setCrirat(this.wk_cratsf);
                        blh.setRecsts(ACEnum.RECSTS_VALID.getStatus());
                        blhMapper.insert(blh);
                        this.wk_draccm = act.getDraccm();
                        this.wk_craccm = act.getCraccm();
                        if (this.wk_dratsf.compareTo(zeroBD) != 0 && act.getDacint() != 0
                                || this.wk_dratsf.compareTo(zeroBD) == 0) {
                            this.wk_draccm = 0L;
                        }
                        if (this.wk_cratsf.compareTo(zeroBD) != 0 && act.getCacint() != 0
                                || this.wk_cratsf.compareTo(zeroBD) == 0) {
                            this.wk_craccm = 0L;
                        }
                        Actact tempAct = new Actact();
                        tempAct.setDraccm(this.wk_draccm);
                        tempAct.setCraccm(this.wk_craccm);
                        tempAct.setDacint(0L);
                        tempAct.setCacint(0L);
                        tempAct.setLindth(rev_erydat);
                        tempAct.setLintdt(this.sct_ipydat);
                        ActactExample tempExample = new ActactExample();
                        tempExample.createCriteria().andOrgidtEqualTo(act.getOrgidt())
                                .andCusidtEqualTo(act.getCusidt()).andApcodeEqualTo(act.getApcode())
                                .andCurcdeEqualTo(act.getCurcde()).andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus());
                        actMapper.updateByExampleSelective(tempAct, tempExample);
                        actcit = new Actcit();
                        actcit.setSysidt(ACEnum.SYSIDT_AC.getStatus());
                        actcit.setOrgidt(act.getOrgidt());
                        actcit.setCusidt(act.getCusidt());
                        actcit.setApcode(act.getApcode());
                        actcit.setCurcde(act.getCurcde());
                        actcit.setIpydat(this.sct_ipydat);
                        String year = sdfdate.format(this.sct_ipydat).substring(0, 4);
                        this.cit_yintsq = mapper.selectmaxYintsq(actcit.getSysidt(), actcit.getOrgidt(), actcit.getCusidt(),
                                actcit.getApcode(), actcit.getCurcde(), year);
                        if ("1111-11-11".equalsIgnoreCase(sdfdate.format(act.getLintdt()))) {
                            actcit.setBegdat(act.getOpndat());
                        } else {
                            actcit.setBegdat(act.getLintdt());
                        }
                        actcit.setEnddat(this.rev_erydat);
                        actcit.setVchset((short) 0);
                        actcit.setReason(this.err_reason);
                        actcit.setIntflg("N");
                        actcit.setRecsts(ACEnum.RECSTS_VALID.getStatus());
                        if (act.getCacint() != 0) {
                            this.cit_yintsq += 1;
                            actcit.setIntcde(act.getCinrat());
                            actcit.setIntrat(act.getCratsf());
                            actcit.setIntamt(act.getCacint());
                            citMapper.insert(actcit);
                        }
                        if (act.getDacint() != 0) {
                            this.cit_yintsq += 1;
                            actcit.setIntcde(act.getDinrat());
                            actcit.setIntrat(act.getDratsf());
                            actcit.setIntamt(act.getDacint());
                            citMapper.insert(actcit);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    private List<Actact> initActList() {
        List<Actact> list = null;
        list = mapper.selectActs(ACEnum.INTFLG_AIF.getStatus(),
                ACEnum.INTFLG_AIF_4.getStatus(), ACEnum.RECSTS_VALID.getStatus());
        return (list != null && list.size() > 0) ? list : null;
    }

    private Date dayAdd(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    private Actsct initSct() {
        return sctMapper.selectByPrimaryKey((short) 8);
    }

    public AcbaccmMapper getMapper() {
        return mapper;
    }

    public void setMapper(AcbaccmMapper mapper) {
        this.mapper = mapper;
    }

    public ActsctMapper getSctMapper() {
        return sctMapper;
    }

    public void setSctMapper(ActsctMapper sctMapper) {
        this.sctMapper = sctMapper;
    }

    public ActcitMapper getCitMapper() {
        return citMapper;
    }

    public void setCitMapper(ActcitMapper citMapper) {
        this.citMapper = citMapper;
    }

    public ActblhMapper getBlhMapper() {
        return blhMapper;
    }

    public void setBlhMapper(ActblhMapper blhMapper) {
        this.blhMapper = blhMapper;
    }

    public ActactMapper getActMapper() {
        return actMapper;
    }

    public void setActMapper(ActactMapper actMapper) {
        this.actMapper = actMapper;
    }
}