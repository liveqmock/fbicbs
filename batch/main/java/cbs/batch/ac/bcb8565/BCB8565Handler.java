package cbs.batch.ac.bcb8565;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.dao.ActbahMapper;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.ActactExample;
import cbs.repository.account.maininfo.model.Actbah;
import cbs.repository.account.tempinfo.dao.ActcirMapper;
import cbs.repository.account.tempinfo.model.Actcir;
import cbs.repository.account.tempinfo.model.ActcirExample;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 写ACTBAH历史数据(按日记录ACTACT变化历史)
 * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8565Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8565Handler.class);
    @Inject
    private ActsctMapper sctMapper;
    @Inject
    private ActsctExample sctExample;
    @Inject
    private ActactMapper actMapper;
    @Inject
    private ActactExample actExample;
    @Inject
    private ActcirMapper cirMapper;
    @Inject
    private ActcirExample cirExample;
    @Inject
    private ActbahMapper bahMapper;
    @Inject
    private BatchSystemService systemService;
    private Actsct actsct;
    private Actcir cir;
    private List<Actact> actList;
    private BigDecimal ddrirt;
    private BigDecimal dcrirt;
    private BigDecimal curirt;
    private String modflg;
    private String irt = "   ";
    private Date effdat;
    private Date wk_date;
    private String dodflg;
    private String codflg;
    private BigDecimal dirval;
    private BigDecimal cirval;
    private BigDecimal next_dirval;
    private BigDecimal next_cirval;

    protected void initBatch(final BatchParameterData batchParam) {

    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            this.actsct = sctInit();
            if (actsct == null) {
                throw new RuntimeException("未找到ACTSCT记录");
            } else {
                this.actList = initActList();
                if (actList == null || actList.size() == 0) return;
            }
        } catch (Exception e) {
            logger.error("初始化错误！", e);
            return;
        }

        try {
            for (Actact act : actList) {
                if ("0".equals(act.getIntflg())) {
                    ddrirt = new BigDecimal(0);
                    dcrirt = new BigDecimal(0);
                    return;
                }

                if ("999".equals(act.getDinrat())) {
                    this.ddrirt = act.getDratsf();
                } else {

                    this.irt = act.getDinrat();
                    if (this.irt == null || " ".equals(this.irt)) {
                        this.irt = "   ";
                    }
                    // TODO 需保证irt长度至少为3
                    cir = selectCir(act.getCurcde());
                    if (cir == null) {
                        this.ddrirt = new BigDecimal(0);
                    } else {
                        this.effdat = cir.getEffdat();
                        this.dodflg = cir.getModflg();
                        this.dirval = cir.getCurirt();
                        this.next_dirval = cir.getNxtirt();
                        this.ddrirt = cir.getIrtval();
                    }
                }
                if ("999".equals(act.getCinrat())) {
                    this.dcrirt = act.getCratsf();
                } else {
                    this.irt = act.getCinrat();
                    if (this.irt == null || " ".equals(this.irt)) {
                        this.irt = "   ";
                    }
                    cir = selectCir(act.getCurcde());
                    if (cir == null) {
                        this.dcrirt = new BigDecimal(0);
                    } else {
                        this.effdat = cir.getEffdat();
                        this.codflg = cir.getModflg();
                        this.cirval = cir.getCurirt();
                        this.next_cirval = cir.getNxtirt();
                        this.dcrirt = cir.getIrtval();
                    }
                }
                if ("Y".equals(this.dodflg) || "Y".equals(this.codflg)) {
                    this.modflg = "Y";
                }
                if (actsct.getCrndat().before(actsct.getIpydat()) || actsct.getIpydat().before(actsct.getNwkday())) {
                    act.setDdrcnt(0);
                    act.setDdramt(0L);
                    act.setDcrcnt(0);
                    act.setDcramt(0L);
                    this.wk_date = actsct.getIpydat();
                    insertActbahByAct(act);
                }
                if (act.getDdrcnt() != 0 || act.getDcrcnt() != 0 || "Y".equals(this.modflg)
                        || actsct.getCrndat().equals(actsct.getIpydat())) {
                    this.wk_date = actsct.getCrndat();
                    insertActbahByAct(act);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Actsct sctInit() {
        return sctMapper.selectByPrimaryKey((short) 8);
    }

    private List<Actact> initActList() {
        actExample.createCriteria().andSysidtEqualTo(systemService.getSysidtAC())
                .andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
        return actMapper.selectByExample(actExample);
    }

    private Actcir selectCir(String curcde) {
        cirExample.createCriteria().andIrtkd1EqualTo(irt.substring(0, 1))
                .andIrtkd2EqualTo(irt.substring(1, 3)).andCurcdeEqualTo(curcde);
        List<Actcir> cirList = cirMapper.selectByExample(cirExample);
        return (cirList != null && cirList.size() > 0) ? cirList.get(0) : null;
    }

    private int insertActbahByAct(Actact act) {
        int rtnCnt = 0;
        act.setUpddat(this.wk_date);
        Actbah actbah = new Actbah();
        actbah.setSysidt(act.getSysidt());
        actbah.setOrgidt(act.getOrgidt());
        actbah.setCusidt(act.getCusidt());
        actbah.setApcode(act.getApcode());
        actbah.setCurcde(act.getCurcde());
        actbah.setBahdat(act.getUpddat());
        actbah.setBokbal(act.getBokbal());
        actbah.setDdrcnt(act.getDdrcnt());
        actbah.setDcramt(act.getDcramt());
        actbah.setDcrcnt(act.getDcrcnt());
        actbah.setDcramt(act.getDcramt());
        actbah.setRecsts(" ");
        actbah.setDratsf(this.ddrirt);
        actbah.setCratsf(this.dcrirt);
        actbah.setIntsts(" ");
        actbah.setIntflg(act.getIntflg());
        actbah.setDinrat(act.getDinrat());
        actbah.setCinrat(act.getCinrat());
        rtnCnt = bahMapper.insert(actbah);
        return rtnCnt;
    }
}
