package cbs.batch.ac.bcb8543;

import cbs.batch.ac.bcb8543.dao.BCB8543Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-10
 * Time: 14:10:35
 * 计息
 */
// TODO 5140-COM-INT 从未调用
@Service
public class BCB8543Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8543Handler.class);
    @Inject
    private BCB8543Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    private SctPojoBean sct;
    private List<Actact> actList;
    private String recTyp;
    private Date wkLindth;
    private String wkDodFlg = "";
    private String wkModFlg = "";
    private String wkCodFlg = "";
    private short wkNxtdds;
    private Date wkNwkday;
    private Date wkIpydat;
    private int effdats;
    private Date cirEffdat;
    private BigDecimal wkDirval  = new BigDecimal(0);
    private BigDecimal wkNxtDirval = new BigDecimal(0);
    private BigDecimal wkCirval = new BigDecimal(0);
    private BigDecimal wkNxtCirval = new BigDecimal(0);
    private BigDecimal wkDdrirt = new BigDecimal(0);
    private String wkIrt;
    private BigDecimal wkDcrirt = new BigDecimal(0);
    private Date wkEffdat1;
    private short decpos;
    private long wkIntbal;
    private BigDecimal wkSumDraIrt = new BigDecimal(0);
    private BigDecimal wkSumCraIrt = new BigDecimal(0);
    //private int wkEndFlag = 0;
    private Date wkLintdt;
    private BigDecimal wkDacint = new BigDecimal(0);
    private BigDecimal wkCacint = new BigDecimal(0);
    private BigDecimal wkSumDraInt = new BigDecimal(0);
    private BigDecimal wkSumCraInt = new BigDecimal(0);
    private Date wkDate;
    private long wkSuefex;
    private String wkBaseDate = "0001-01-01";

    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8543 计息 BEGIN");
        try {
            if(parameterData.getCommandLine() == null || parameterData.getCommandLine().length < 1){
                 logger.error("命令行参数不能为空！");
                throw new RuntimeException("命令行参数不能为空！");
            }
            this.recTyp = parameterData.getCommandLine()[0].toUpperCase();

            if ("Y".equalsIgnoreCase(recTyp) && "N".equalsIgnoreCase(sct.getIpymak())) {
                return;
            }
            initProps();
            int actIndex = 0;
            if (actList != null && !actList.isEmpty()) {
                while (actIndex < actList.size()) {
                    Actact act = actList.get(actIndex);
                    this.wkLindth = act.getLindth();
                    logger.info("ACT-INTFLG=" + act.getIntflg());
                    this.wkLintdt = act.getLintdt();
                    if ("1".equals(act.getIntflg())) {
                        comInt_1(act);
                        updAct(act);//  5410
                    }
                    if ("3".equals(act.getIntflg())) {
                        comInt_1(act);
                        updAct(act); //   5410
                    }
                    if ("4".equals(act.getIntflg())) {
                        comInt_2(act);
                        updAct(act);// 5410
                    }
                    if ("5".equals(act.getIntflg())) {
                        comInt_3(act);
                        updAct(act);//  5410
                    }
                    logger.info("xxxx " + act.getCusidt() + "    " + act.getApcode() + "    " + act.getCurcde());
                    actIndex++;
                }
            } else {
                logger.info("ACTACT NOT FOUND");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8543 计息 END");
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    private void initProps() {
        this.sct = mapper.selectSctByNum(wkBaseDate, Short.parseShort(systemService.getSysidtAC()));
        this.actList = mapper.qryActsBySts(ACEnum.RECSTS_VALID.getStatus());
    }
    // 5110

    private void comInt_1(Actact act) {
        if ("Y".equalsIgnoreCase(recTyp)) {
            sct.setIpymak("N");
            sct.setCrndat(sct.getIpydat());
            sct.setNxtdds((short) (sct.getNwkdays() - sct.getIpydats()));
        }
        selIrt(act);  // 5210
        if ("Y".equals(this.wkDodFlg) || "Y".equals(this.wkCodFlg)) {
            this.wkModFlg = "Y";
            logger.info("wkModFlg=" + this.wkModFlg);
        }
        if (!"Y".equalsIgnoreCase(sct.getIpymak()) && !"Y".equalsIgnoreCase(this.wkModFlg)) {
            this.wkNxtdds = sct.getNxtdds();
            this.wkNwkday = sct.getNwkday();
            comAcc(act); // 5310
        }
        if ("Y".equalsIgnoreCase(sct.getIpymak()) && !"Y".equalsIgnoreCase(this.wkModFlg)) {
            this.wkNxtdds = (short) (sct.getIpydats() - sct.getCrndats());
            this.wkNwkday = sct.getIpydat();
            comAcc(act);  // 5310
        }
        if (!"Y".equalsIgnoreCase(sct.getIpymak()) && "Y".equalsIgnoreCase(this.wkModFlg)) {
            this.wkNxtdds = (short) (effdats - sct.getCrndats());
            this.wkNwkday = this.cirEffdat;
            comAcc(act);  // 5310
            insBlh(act);     // 5320
            this.wkNxtdds = (short) (sct.getNwkdays() - effdats);
            this.wkNwkday = sct.getNwkday();
            this.wkDirval = this.wkNxtDirval;
            this.wkCirval = this.wkNxtCirval;
            comAcc(act);  // 5310
        }
        if ("Y".equalsIgnoreCase(sct.getIpymak()) && "Y".equalsIgnoreCase(this.wkModFlg)) {
            if (!sct.getIpydat().after(this.cirEffdat)) {      // Not >
                this.wkNxtdds = (short) (sct.getIpydats() - sct.getCrndats());
                this.wkNwkday = sct.getIpydat();
                comAcc(act);  // 5310
            } else {
                this.wkNxtdds = (short) (effdats - sct.getCrndats());
                this.wkNwkday = this.cirEffdat;
                comAcc(act);   // 5310
                insBlh(act);// 5320
                this.wkNxtdds = (short) (sct.getIpydats() - effdats);
                this.wkNwkday = sct.getIpydat();
                this.wkDirval = this.wkNxtDirval;
                this.wkCirval = this.wkNxtCirval;
                comAcc(act);  // 5310
            }
        }

    }

    // 5120

    private void comInt_2(Actact act) {
        if ("Y".equalsIgnoreCase(recTyp)) {
            sct.setIpymak("N");
            sct.setCrndat(sct.getIpydat());
            sct.setNxtdds((short) (sct.getNwkdays() - sct.getIpydats()));
        }
        selIrt(act);// 5210
        if (!"Y".equals(sct.getIpymak())) {
            this.wkNxtdds = sct.getNxtdds();
            this.wkNwkday = sct.getNwkday();
            comAcc(act);
        }
        if ("Y".equals(sct.getIpymak())) {
            act.setLintdt(sct.getIpydat());
            this.wkNxtdds = (short) (sct.getIpydats() - sct.getCrndats());
            this.wkNwkday = sct.getIpydat();
            comAcc(act);   // 5310
        }
    }

    // 5130

    private void comInt_3(Actact act) {
        if ("Y".equalsIgnoreCase(recTyp)) {
            sct.setIpymak("N");
            sct.setCrndat(sct.getIpydat());
            sct.setNxtdds((short) (sct.getNwkdays() - sct.getIpydats()));
        }
        if (!"Y".equals(sct.getIpymak())) {
            this.wkNxtdds = sct.getNxtdds();
            this.wkNwkday = sct.getNwkday();
            balInt(act); //  5330
        }
        if ("Y".equals(sct.getIpymak())) {
            this.wkNxtdds = (short) (sct.getIpydats() - sct.getCrndats());
            this.wkNwkday = sct.getIpydat();
            balInt(act);//  5330
        }
    }

    //5210

    private void selIrt(Actact act) {
        this.wkDodFlg = "N";
        this.wkCodFlg = "N";
        if ("999".equals(act.getDinrat())) {
            this.wkDdrirt = act.getDratsf();
            if ("001".equals(act.getCurcde().trim())) {
                this.wkDirval = act.getDratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
            } else {
                this.wkDirval = act.getDratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
            }
        } else {
            this.wkIrt = act.getDinrat();
            //    act.getDinrat() 为空字符串
            if (this.wkIrt == null || this.wkIrt.length() < 3) {
                this.wkIrt = "   ";
            }
            CirPojo cir = mapper.qryCir(wkBaseDate, this.wkIrt.substring(0, 1), this.wkIrt.substring(1, 3), act.getCurcde());
            if (cir == null) {
                this.wkDdrirt = act.getDratsf();
                if ("001".equals(act.getCurcde().trim())) {
                    this.wkDirval = act.getDratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                } else {
                    this.wkDirval = act.getDratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                }
            } else {
                this.cirEffdat = cir.getEffdat();
                this.wkDodFlg = cir.getModflg();
                this.wkDirval = cir.getCurirt();
                this.wkNxtDirval = cir.getNxtirt();
                this.wkDdrirt = cir.getIrtval();
                this.effdats = cir.getEffdats();
                this.wkEffdat1 = cir.getEffdat1();
            }
        }
        if ("999".equals(act.getCinrat())) {
            this.wkDcrirt = act.getCratsf();
            if ("001".equals(act.getCurcde().trim())) {
                this.wkCirval = act.getCratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
            } else {
                this.wkCirval = act.getCratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
            }
        } else {
            this.wkIrt = act.getCinrat();
            logger.info("ACT-CINRAT=" + act.getCinrat());
            CirPojo cir = mapper.qryCir(wkBaseDate, this.wkIrt.substring(0, 1), this.wkIrt.substring(1, 3), act.getCurcde());
            if (cir == null) {
                this.wkDcrirt = act.getCratsf();
                if ("001".equals(act.getCurcde().trim())) {
                    this.wkCirval = act.getCratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                } else {
                    this.wkCirval = act.getCratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                }
            } else {
                this.cirEffdat = cir.getEffdat();
                this.wkCodFlg = cir.getModflg();
                this.wkCirval = cir.getCurirt();
                this.wkNxtCirval = cir.getNxtirt();
                this.wkDcrirt = cir.getIrtval();
                this.effdats = cir.getEffdats();
                this.wkEffdat1 = cir.getEffdat1();
            }
        }
    }

    // 5310

    private void comAcc(Actact act) {
        this.decpos = mapper.selectDecposByCde(act.getCurcde(), ACEnum.RECSTS_VALID.getStatus());
        if (act.getBokbal() < 0) {
            this.wkIntbal = act.getBokbal() - act.getDifbal();
            act.setDraccm(act.getDraccm() + this.wkIntbal);
        }
        if (act.getBokbal() >= 0) {
            this.wkIntbal = act.getBokbal() - act.getCifbal();
            act.setCraccm(act.getCraccm() + this.wkIntbal);
        }
        BlhSumIrt blhSumIrt = mapper.selectBlhSumIrt(act.getOrgidt(), act.getCusidt(), act.getApcode(),
                act.getCurcde(), this.wkLintdt);
        if (blhSumIrt != null) {
            this.wkSumDraIrt = blhSumIrt.getSumDraIrt();
            this.wkSumCraIrt = blhSumIrt.getSumCraIrt();
        } else {
            this.wkSumDraIrt = new BigDecimal(0);
            this.wkSumCraIrt = new BigDecimal(0);
        }
        //ROUNDED
        this.wkDacint = this.wkSumDraIrt.add(new BigDecimal(act.getDraccm()).multiply(this.wkDirval));
        //ROUNDED

        BigDecimal tempBD = new BigDecimal(act.getCraccm()).multiply(this.wkCirval);
        this.wkCacint = this.wkSumCraIrt.add(tempBD);

        CbhSumRat cbhSumRat = mapper.selectCbhSumIrt(act.getOrgidt(), act.getCusidt(), act.getApcode(),
                act.getCurcde(), this.wkLintdt);
        if (cbhSumRat != null) {
            this.wkSumDraInt = cbhSumRat.getSumDraRat();
            this.wkSumCraInt = cbhSumRat.getSumCraRat();
        } else {
            this.wkSumDraInt = new BigDecimal(0);
            this.wkSumCraInt = new BigDecimal(0);
        }

        this.wkCacint = this.wkCacint.add(this.wkSumCraInt);
        this.wkDacint = this.wkDacint.add(this.wkSumDraInt);

        logger.info("ACT-CUSIDT=" + act.getCusidt());
        logger.info("ACT-APCODE=" + act.getApcode());
        logger.info("ACT-CURCDE=" + act.getCurcde());
        logger.info("ACT-DRACCM=" + act.getDraccm());
        logger.info("WK-DIRVAL=" + this.wkDirval);
        logger.info("ACT-CRACCM=" + act.getCraccm());
        logger.info("WK-CIRVAL=" + this.wkCirval);
        logger.info("WK-CACINT=" + this.wkCacint);
        logger.info("WK-DACINT=" + this.wkDacint);

    }
    // 5320

    private void insBlh(Actact act) {
        this.wkDate = mapper.lindthAddOne(this.wkLindth, Short.parseShort(systemService.getSysidtAC()));
        this.wkLindth = this.cirEffdat;
        mapper.insertBlh(act.getOrgidt(), act.getCusidt(), act.getApcode(), act.getCurcde(),
                this.wkLindth, act.getDepnum(), this.wkDate, this.wkIntbal, act.getDraccm(),
                act.getCraccm(), this.wkDdrirt, this.wkDcrirt, this.wkDirval, this.wkCirval, ACEnum.RECSTS_VALID.getStatus());
        this.wkDacint = new BigDecimal(0);
        this.wkCacint = new BigDecimal(0);
        act.setDraccm(0L);
        act.setCraccm(0L);

    }

    // 5330

    private void balInt(Actact act) {
        this.decpos = mapper.selectDecposByCde(act.getCurcde(), ACEnum.RECSTS_VALID.getStatus());
        this.wkSuefex = 10 * this.decpos;
        IblPojo ibl = mapper.qryIblSum(systemService.getSysidtAC(), act.getOrgidt(), act.getCusidt(), act.getApcode(), act.getCurcde(), this.wkLintdt);
        if (ibl == null) {
            act.setCraccm(0L);
            act.setDraccm(0L);
            this.wkCacint = new BigDecimal(0);
            this.wkDacint = new BigDecimal(0);

        } else {
            act.setCraccm(ibl.getSumCinbal());
            act.setDraccm(ibl.getSumDinbal());
             this.wkCacint = ibl.getSumCinbalIrt();
            this.wkDacint = ibl.getSumDinbalIrt();
        }
    }
    // 5410

    private void updAct(Actact act) {
        logger.info("UPDATE ACT...");
        logger.info("ACT-CUSIDT=" + act.getCusidt());
        logger.info("ACT-APCODE=" + act.getApcode());
        logger.info("ACT-CURCDE=" + act.getCurcde());
        logger.info("WK-DACINT=" + this.wkDacint);
        logger.info("WK-CACINT=" + this.wkCacint);
        logger.info("ACT-DRACCM=" + act.getDraccm());
        logger.info("ACT-CRACCM=" + act.getCraccm());
        logger.info("WK-LINDTH=" + this.wkLindth);
        // 利息值×100
       /* if(true){
            throw new RuntimeException("aaa");
        }*/
        int updCnt =  mapper.updateAct(wkDacint, wkCacint, act.getDraccm(), act.getCraccm(), this.wkLindth,
                systemService.getSysidtAC(), act.getOrgidt(), act.getCusidt(), act.getApcode(), act.getCurcde());
        if(updCnt >= 1){
            logger.info("计算利息更新ACTACT 成功 : CUSIDT : "+ act.getCusidt() +" 利息: "+wkCacint);
        }else{
            logger.info("计算利息更新ACTACT失败");
        }
        this.wkCacint = new BigDecimal(0);
        this.wkDacint = new BigDecimal(0);
    }
}