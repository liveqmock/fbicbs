package cbs.batch.ac.bcb8561;

import cbs.batch.ac.bcb8561.dao.BCB8561Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.dao.ActcmtMapper;
import cbs.repository.account.maininfo.model.Actcmt;
import cbs.repository.account.maininfo.model.ActcmtExample;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * User: zhangxiaobo
 * Date: 2010-2-22
 */
@Service
public class BCB8561Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8561Handler.class);
    @Inject
    private BCB8561Mapper mapper;
    @Inject
    private ActcmtMapper cmtMapper;
    @Inject
    private ActcmtExample cmtExample;
    @Inject
    private ActsctMapper sctMapper;
    @Inject
    private ActsctExample sctExample;
    @Inject
    private ActsctExample actsctExample;
    private Actsct sct;
    private SctDatBean sctdat;
    private Actcmt actcmt;

    private static final String RECSTS_VALID_STATUS = ACEnum.RECSTS_VALID.getStatus();
    private static final String RECSTS_TABLE_STATUS = ACEnum.RECSTS_TABLE.getStatus();
    private static final String CMTNUM_NORMAL_STATUS = ACEnum.CMTNUM_NORMAL.getStatus();
    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");

    private Date fjsdate;
    private Date wk_inp_crndat;
    private Date hol_nxtdat;
    
    private Date wk_crndat1;
    private Date wk_nwkday1;
    
    private Date wk_crndat;
    private String wk_crnyer;
    private String wk_crnmon;
    private String wk_crnday;

    private Date wk_nwkday;
    private String wk_nxtyer;
    private String wk_nxtmon;
    private String wk_nxtday;

    private Date wk_lwkday;
    private String wk_lwkyer;
    private String wk_lwkmon;
    private String wk_lwkdat;

    private Date wk_work1;
    private Date wk_work2;
    private Date wk_work3;
    private Date wk_work4;
    private Date wk_work5;
    private Date wk_work6;
    private Date wk_work7;
    
    private String wk_year1;
    private String wk_year2;
    private String wk_year3;
    private String wk_year4;
    private String wk_year5;
    private String wk_year6;
    private String wk_year7;

    private String wk_mon1;
    private String wk_mon2;
    private String wk_mon3;
    private String wk_mon4;
    private String wk_mon5;
    private String wk_mon6;
    private String wk_mon7;

    private String wk_day1;
    private String wk_day2;
    private String wk_day3;
    private String wk_day4;
    private String wk_day5;
    private String wk_day6;
    private String wk_day7;

    private String dn_yy;
    private String dn_yer;
    private String dn_mm = "01";
    private String dn_dd = "01";

    private String dn_yy1;
    private short wk_days;
    private String wk_mark1 = "N";
    
    private Date wk_date;
    private String wk_md;
    private String wk_mm;
    private String wk_dd;

    protected void initBatch(final BatchParameterData batchParam) {

    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            sct = initSct();
            if (sct == null) {
                logger.info("ACTSCT查询结果为空！");
                return;
            }
            this.fjsdate = this.getAfterDate(sct.getNwkday(), 1);
            sct.setPremak(ACEnum.PREMAK_FALSE.getStatus());
            sct.setMonmak(ACEnum.MONMAK_FALSE.getStatus());
            sct.setYermak(ACEnum.YERMAK_FALSE.getStatus());
            sct.setQtrmak(ACEnum.QTRMAK_FALSE.getStatus());
            sct.setIpymak(ACEnum.IPYMAK_FALSE.getStatus());
            sct.setFntmak(ACEnum.FNTMAK_FALSE.getStatus());
            sct.setHyrmak(ACEnum.HYRMAK_FALSE.getStatus());
            sct.setTaxmak(ACEnum.TAXMAK_FALSE.getStatus());
            this.wk_mark1 = "Y";

            if ("Y".equalsIgnoreCase(this.wk_mark1)) {
                this.wk_crndat = sct.getNwkday();
                this.wk_inp_crndat = sct.getNwkday();
                this.wk_crnmon = sdfdate.format(wk_crndat).substring(5, 7);
                this.wk_crnday = sdfdate.format(wk_crndat).substring(8, 10);
            } else if ("N".equalsIgnoreCase(this.wk_mark1)) {
                logger.error("SCT-CRNDAT IS NOT RIGHT");
                return;
            } else {
                logger.info("SYSTEM DATE IS :" + sdfdate.format(sct.getNwkday()));                
            }

            this.hol_nxtdat = mapper.selectNxtdat(ACEnum.CURCDE_001.getStatus(),
                    this.wk_inp_crndat, RECSTS_VALID_STATUS);
            if (this.hol_nxtdat == null) {
                hol_nxtdat = this.fjsdate;
            }else{
                logger.info("this.hol_nxtdat :  "+this.hol_nxtdat );                
            }
            sct.setNwkday(this.hol_nxtdat);
            this.wk_lwkday = sct.getCrndat();
            this.wk_lwkyer = sdfdate.format(wk_lwkday).substring(0,4);
            this.wk_lwkmon = sdfdate.format(wk_lwkday).substring(5,7);
            sct.setLwkday(sct.getCrndat());
            this.wk_crnyer = sdfdate.format(wk_crndat).substring(0, 4);
            this.dn_yer = this.wk_crnyer;
            this.dn_yy = this.dn_yer +"-"+this.dn_mm +"-"+ this.dn_dd;
            this.dn_yy1 = this.dn_yy;
            this.wk_nwkday = sct.getNwkday();
            this.wk_crndat1 = this.wk_crndat;
            this.wk_nwkday1 = this.wk_nwkday;

            this.wk_nxtmon = sdfdate.format(wk_nwkday).substring(5, 7);
            this.wk_nxtday = sdfdate.format(wk_nwkday).substring(8, 10);

            this.sctdat = mapper.selectDatSub(sdfdate.format(wk_crndat1), sdfdate.format(wk_nwkday1),
                    dn_yy1, (short) 8, RECSTS_VALID_STATUS);
            cmtExample.createCriteria().andCmtnumEqualTo(CMTNUM_NORMAL_STATUS).andRecstsEqualTo(RECSTS_VALID_STATUS);
            List<Actcmt> cmtList = cmtMapper.selectByExample(cmtExample);
            actcmt = (cmtList != null && cmtList.size() > 0) ? cmtList.get(0) : null;
            this.wk_year1 = wk_crnyer;
            this.wk_year2 = wk_crnyer;
            this.wk_year3 = wk_crnyer;
            this.wk_year4 = wk_crnyer;
            this.wk_year5 = wk_crnyer;
            this.wk_year6 = wk_crnyer;
            this.wk_year7 = wk_crnyer;
            this.wk_md = actcmt.getIpydt1();
            this.wk_mm = wk_md.substring(0, 2);
            this.wk_mon1 = this.wk_mm;
            this.wk_dd = wk_md.substring(2, 4);
            this.wk_day1 = this.wk_dd;

            this.wk_md = actcmt.getIpydt2();
            this.wk_mm = wk_md.substring(0, 2);
            this.wk_mon2 = this.wk_mm;
            this.wk_dd = wk_md.substring(2, 4);
            this.wk_day2 = this.wk_dd;

            this.wk_md = actcmt.getIpydt3();
            this.wk_mm = wk_md.substring(0, 2);
            this.wk_mon3 = this.wk_mm;
            this.wk_dd = wk_md.substring(2, 4);
            this.wk_day3 = this.wk_dd;

            this.wk_md = actcmt.getIpydt4();
            this.wk_mm = wk_md.substring(0, 2);
            this.wk_mon4 = this.wk_mm;
            this.wk_dd = wk_md.substring(2, 4);
            this.wk_day4 = this.wk_dd;

            this.wk_md = actcmt.getTaxedt();
            this.wk_mm = wk_md.substring(0, 2);
            this.wk_mon5 = this.wk_mm;
            this.wk_dd = wk_md.substring(2, 4);
            this.wk_day5 = this.wk_dd;

            this.wk_md = actcmt.getFinedt();
            this.wk_mm = wk_md.substring(0, 2);
            this.wk_mon6 = this.wk_mm;
            this.wk_dd = wk_md.substring(2, 4);
            this.wk_day6 = this.wk_dd;

            this.wk_work7 = actcmt.getPrestl();
            this.wk_work1 = sdfdate.parse(wk_year1 + "-" + wk_mon1 + "-" + wk_day1);
            this.wk_work2 = sdfdate.parse(wk_year2 + "-" + wk_mon2 + "-" + wk_day2);
            this.wk_work3 = sdfdate.parse(wk_year3 + "-" + wk_mon3 + "-" + wk_day3);
            this.wk_work4 = sdfdate.parse(wk_year4 + "-" + wk_mon4 + "-" + wk_day4);
            this.wk_work5 = sdfdate.parse(wk_year5 + "-" + wk_mon5 + "-" + wk_day5);
            this.wk_work6 = sdfdate.parse(wk_year6 + "-" + wk_mon6 + "-" + wk_day6);
            if (!wk_crndat.after(wk_work6) && wk_nwkday.after(wk_work6)) {
                sct.setYermak(ACEnum.YERMAK_TRUE.getStatus());
            } else {
                sct.setYermak(ACEnum.YERMAK_FALSE.getStatus());
            }
            if ((wk_crnday.compareToIgnoreCase("10") <= 0
                    && wk_nxtday.compareToIgnoreCase("10") > 0)
                    || (wk_crnday.compareToIgnoreCase("20") <= 0
                    && wk_nxtday.compareToIgnoreCase("20") > 0)) {
                sct.setTdymak(ACEnum.TDYMAK_TRUE.getStatus());
                sct.setFntmak(ACEnum.FNTMAK_FALSE.getStatus());
            } else if (wk_crnday.compareToIgnoreCase("15") <= 0
                    && wk_nxtday.compareToIgnoreCase("15") > 0) {
                sct.setTdymak(ACEnum.TDYMAK_FALSE.getStatus());
                sct.setFntmak(ACEnum.FNTMAK_TRUE.getStatus());
            } else {
                sct.setTdymak(ACEnum.TDYMAK_FALSE.getStatus());
                sct.setFntmak(ACEnum.FNTMAK_FALSE.getStatus());
            }
            if (!this.wk_crnmon.equalsIgnoreCase(wk_nxtmon)) {
                sct.setTdymak(ACEnum.TDYMAK_TRUE.getStatus());
                sct.setFntmak(ACEnum.FNTMAK_TRUE.getStatus());
                sct.setMonmak(ACEnum.MONMAK_TRUE.getStatus());
                if (wk_crnmon.equalsIgnoreCase("03") || wk_crnmon.equalsIgnoreCase("09")) {
                    sct.setQtrmak(ACEnum.QTRMAK_TRUE.getStatus());
                    sct.setHyrmak(ACEnum.HYRMAK_FALSE.getStatus());
                } else if (wk_crnmon.equalsIgnoreCase("06") || wk_crnmon.equalsIgnoreCase("12")) {
                    sct.setQtrmak(ACEnum.QTRMAK_TRUE.getStatus());
                    sct.setHyrmak(ACEnum.HYRMAK_TRUE.getStatus());
                } else {
                    sct.setQtrmak(ACEnum.QTRMAK_FALSE.getStatus());
                    sct.setHyrmak(ACEnum.HYRMAK_FALSE.getStatus());
                }
            } else {
                sct.setMonmak(ACEnum.MONMAK_FALSE.getStatus());
            }
            if (!wk_crndat.after(wk_work5) && wk_nwkday.after(wk_work5)) {
                sct.setTaxmak(ACEnum.TAXMAK_TRUE.getStatus());
                sct.setTaxdat(wk_work5);
            } else {
                sct.setTaxmak(ACEnum.TAXMAK_FALSE.getStatus());
            }
            processHandle();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processHandle() {
        if (!wk_crndat.after(wk_work1) && wk_nwkday.after(wk_work1)) {
            sct.setIpymak(ACEnum.IPYMAK_TRUE.getStatus());
            sct.setIpydat(wk_work1);
            pp1();
            return;
        } else {
            sct.setIpymak(ACEnum.IPYMAK_FALSE.getStatus());
        }
        if (!wk_crndat.after(wk_work2) && wk_nwkday.after(wk_work2)) {
            sct.setIpymak(ACEnum.IPYMAK_TRUE.getStatus());
            sct.setIpydat(wk_work2);
            pp1();
            return;
        } else {
            sct.setIpymak(ACEnum.IPYMAK_FALSE.getStatus());
        }
        if (!wk_crndat.after(wk_work3) && wk_nwkday.after(wk_work3)) {
            sct.setIpymak(ACEnum.IPYMAK_TRUE.getStatus());
            sct.setIpydat(wk_work3);
            pp1();
            return;
        } else {
            sct.setIpymak(ACEnum.IPYMAK_FALSE.getStatus());
        }
        if (!wk_crndat.after(wk_work4) && wk_nwkday.after(wk_work4)) {
            sct.setIpymak(ACEnum.IPYMAK_TRUE.getStatus());
            sct.setIpydat(wk_work4);
            pp1();
            return;
        } else {
            sct.setIpymak(ACEnum.IPYMAK_FALSE.getStatus());
        }
        pp1();
    }

    private void pp1() {
        if (!wk_crndat.after(wk_work7) && wk_nwkday.after(wk_work7)) {
            sct.setPremak(ACEnum.PREMAK_TRUE.getStatus());
        } else {
            sct.setPremak(ACEnum.PREMAK_FALSE.getStatus());
        }
        if (!this.wk_crnyer.equalsIgnoreCase(this.wk_lwkyer)) {
            this.dn_yy1 = dn_yy;
            this.wk_days = mapper.selectDays(sdfdate.format(wk_crndat1),this.dn_yy1, (short) 8, RECSTS_VALID_STATUS);
            sct.setBmmdds(wk_days);
            sct.setBssdds(sct.getBmmdds());
            sct.setByydds(sct.getBmmdds());
            sct.setMwkdds((short) 1);
            sct.setYwkdds((short) 1);
        } else{
            if (!this.wk_crnmon.equalsIgnoreCase(wk_lwkmon)) {
            this.dn_mm = this.wk_crnmon;
            this.dn_yy = this.dn_yer +"-"+this.dn_mm +"-"+ this.dn_dd;
            this.dn_yy1 = dn_yy;
            this.wk_days = mapper.selectDays(sdfdate.format(wk_crndat1), this.dn_yy1,(short) 8, RECSTS_VALID_STATUS);
            sct.setBmmdds(this.wk_days);
            sct.setByydds((short) (sct.getByydds() + sct.getLwkdds()));
            sct.setMwkdds((short) 1);
            sct.setYwkdds((short) (sct.getYwkdds() + 1));
            if (wk_lwkmon.equalsIgnoreCase("03") || wk_lwkmon.equalsIgnoreCase("06")
                    || wk_lwkmon.equalsIgnoreCase("09") || wk_lwkmon.equalsIgnoreCase("12")) {
                this.dn_mm = wk_crnmon;
                this.dn_yy = this.dn_yer +"-"+this.dn_mm +"-"+ this.dn_dd;
                this.dn_yy1 = dn_yy;
                this.wk_days = mapper.selectDays(sdfdate.format(wk_crndat1),this.dn_yy1, (short) 8, RECSTS_VALID_STATUS);
                sct.setBssdds(wk_days);
            } else {
                sct.setBssdds((short) (sct.getBssdds() + sct.getLwkdds()));
            }
        } else {
            sct.setByydds((short) (sct.getByydds() + sct.getLwkdds()));
            sct.setBssdds((short) (sct.getBssdds() + sct.getLwkdds()));
            sct.setBmmdds((short) (sct.getBmmdds() + sct.getLwkdds()));
            sct.setMwkdds((short) (sct.getMwkdds() + 1));
            sct.setYwkdds((short) (sct.getYwkdds() + 1));
        }
    }
        sct.setWdymak(ACEnum.WDYMAK_TRUE.getStatus());
        if ("Y".equalsIgnoreCase(sct.getIpymak())) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sct.getIpydat());
            cal.add(Calendar.DAY_OF_MONTH, 1);
            sct.setIpydat(cal.getTime());
            sct.setCrndat(this.wk_crndat1);
            sct.setSyssts(RECSTS_TABLE_STATUS);
            sctExample.createCriteria().andSctnumEqualTo((short) 8).andRecstsEqualTo(RECSTS_VALID_STATUS);
            // int cnt = sctMapper.countByExample(sctExample);
            sctMapper.updateByExampleSelective(sct, sctExample);
        } else {
            sct.setCrndat(this.wk_crndat1);
            sct.setSyssts(RECSTS_TABLE_STATUS);
            actsctExample.createCriteria().andSctnumEqualTo((short) 8).andRecstsEqualTo(RECSTS_VALID_STATUS);
            // int cnt = sctMapper.countByExample(actsctExample);
            sctMapper.updateByExampleSelective(sct, actsctExample);
        }
        mapper.updateActtlr(RECSTS_VALID_STATUS);
        mapper.updateScttlr();
        // 将已平帐标记置为可交易标记
        mapper.updateScttlr2();
        //mapper.updateSctapp();
        //mapper.updateActsev(RECSTS_TABLE_STATUS);
        //mapper.deleteActdlz();
        //mapper.deleteActclz();
    }

    private Actsct initSct() {
        return mapper.selectSct((short) 8, RECSTS_VALID_STATUS);
    }

    private Date getAfterDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
        return calendar.getTime();
    }
}