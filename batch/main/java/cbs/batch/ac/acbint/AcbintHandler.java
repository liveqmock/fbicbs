package cbs.batch.ac.acbint;

import cbs.batch.ac.acbint.dao.AcbintMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.PropertyManager;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.model.Actblh;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.account.maininfo.model.Actobf;
import cbs.repository.account.tempinfo.model.Actcir;
import cbs.repository.code.model.*;
import cbs.repository.platform.model.Ptdept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活期计息
 * 活期存款按季计息，并生成下列报表
 * 存款计息清单      8910lst.010
 * 存款计息传票      8910vch.010
 * 存款利息通知单    8910out.010----  格式按CCB计算利息清单
 * 存款未计息清单    8910err.010
 */
// TODO  obf 的初始化和多引用指向同一对象
//  TODO 大小变量在调用时需再次赋值
@Service("AcbintHandler")
public class AcbintHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(AcbintHandler.class);
    @Inject
    private AcbintMapper mapper;
    @Inject
    private ActoacMapper oacMapper;

    private String busDate = SystemService.getBizDate();
    private String busDate10 = SystemService.getBizDate10();
    private BigDecimal taxTaxrat;
    private String wkPrdcde;
    private String wkCurrag;
    private String wkTaxapc;
    private List<ActAndGlc> aos;
    private String shouKuanMing;
    private String huMing;
    private String wkLOrgidt = "";
    private String orgOrgidt;
    private String shouRuOrg;
    private String ysxvchOrg;
    private String ysxerrOrg;
    private String staRu = "00";
    private String firstTlr = "AX10";
    private String wkTlr;
    private String wkSysidt;
    private String wkOrgidt;
    private String wkCusidt;
    private String wkApcode;
    private String wkCurcde;
    private Long wkInts;
    private int rtnEryCode = 0;
    private String errBuf;
    private Long wkCraccm = 0L;
    private Long wkDraccm;
    private BigDecimal wkInttax;
    private static SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfdate8 = new SimpleDateFormat("yyyyMMdd");
    private String wkIpydatMonth;
    private BatchResultFileHandler outFileHandler;
    private BatchResultFileHandler vchFileHandler;
    private InterestErrTemplate errFileHandler;
    private InterestLstTemplate lstFileHandler;
    private BatchResultFileHandler voucherHandler;

    private String actActglc;
    private String glcGlcbal;
    private String actActnam;
    private String actDinrat;
    private String actCinrat;
    private String actInttra;
    private Long actCacint;
    private Long actDacint;

    private String orgOrgnam;
    private String orgSupbak;
    private Date sctCrndat;
    private Date sctIpydat;
    private Date sctNwkday;
    private Date revErydat;
    private Date sctIpydat1;
    private String wkOrgnam;
    private String wkEffdat;
    private String wkTmpdatYY;
    private String jixiDate2;
    private String ccyCurcde;
    private int ccyDecpos;
    private BigDecimal wkCratsf;
    private BigDecimal wkDratsf;
    private String liluNo;
    private BigDecimal cirCurirt;
    private BigDecimal cirIrtval;
    private BigDecimal wkBefTaxInt;
    private String citSysidt;
    private String citOrgidt;
    private String citCusidt;
    private String citApcode;
    private String citCurcde;
    private Date citIpydat;
    private Date wkIpyDate;
    private String wkIpyYY;
    private int citYintsq;
    private Date citEnddat;
    private Date citBegdat;
    private String citIntcde;
    private BigDecimal citIntrat;
    private Long citIntamt;
    private String citReason;
    private int citVchset;
    private String citIntflg;
    private String citRecsts;
    private String accCusidt;
    private String accApcode;
    private String accCurcde;
    private String account;
    private String accountOut;
    private String oldAccount;
    private String oldActShouKuan = "";
    private String rpVchTlrnum;
    private short rpVchVchset;
    private int shoukuanNo;
    private int pageNo;
    private int errPage;
    private int head4;
    private String outOrgidt;
    private String outBranch;
    private Date outDate;
    private int lineNo;
    private String wkLApcode;
    private int tlrNum;
    private int tlrTail;
    private String tmpTlr;
    private int wkSetseq;

    private BigDecimal actCratsf;
    private BigDecimal actDratsf;
    private String actIntflg;
    private String actIntcyc;
    private Long actDraccm;
    private Long actCraccm;
    private Date actLintdt;
    private Date actOpndat;
    private String apcIntinc;
    private String apcIntexp;
    private String tmpInttra;
    private String actActkey;
    private String wkActkey;
    private String actOrgidt;
    private String actCusidt;
    private String actApcode;
    private String actCurcde;
    private String actSysidt;
    private Long actAvabal;
    private String actActsts;
    private String actRecsts;
    private String tmpDepnum;

    private static final String CNST_CUSIDT_H1 = "9";
    private static final String CNST_CUSIDT_D1 = "0";
    private String wkCusid1;
    private String wkCusid2;
    private String wkCusid3;
    private String wkCusid4;
    private String wkCusidt1;
    private String wkLegOrgidt;
    private String wkLegDepnum;
    private String actDepnum;
    private String wkCAct;
    private String wkAutoCode = "0";
    private String wkDAct;
    private String wkTaxAct;
    private String wkDOrgidt;
    private String wkDCusidt;
    private String inttraOrgidt;
    private String wkDApcode;
    private String inttraApcode;
    private String inttraCusidt;
    private String inttraCurcde;
    private String wkTaxOrgidt;
    private String wkTaxApcode;
    private String wkTaxCurcde;
    private String wkTaxCusidt;
    private BigDecimal vchTaxamt;
    private String wkCOrgidt;
    private String wkCCusidt;
    private String wkCApcode;
    private String wkCCurcde;

    private Vch vch = new Vch();


    private String jixiDate1;
    private BigDecimal tmpInts;
    private Long shoukuanFInts;
    private Long shoukuanInts;
    private BigDecimal shengxiInts;
    private int shoukuanVchno;
    private String wkYear;
    private String wkVchdat;
    private String errActSysidt;
    private String errActOrgidt;
    private String errActCusidt;
    private String errActApcode;
    private String errActCurcde;
    private String errActnam;
    private String errInttra;
    private String errActno;
    private Long errAccum = 0L;
    private String errReason;
    private BigDecimal errRat;
    private Long errInts;
    private String errOrgidt;
    private String errDate;
    private String errPageNo;
    private Long avabalOut;
    private BigDecimal cratsfOut;
    private Long intsOut;
    private String rpVchAcno1;
    private BigDecimal rpVchAmt1;
    private String rpVchAcno2;
    private BigDecimal rpVchAmt2;

    private InterestOutTemplate outTemplate;
    private InterestVchTemplate vchTemplate;

    private int vchUnitNo = 0;
    private String atrPrdcde;
    private String atrEvtcde;
    private String atrCtpcde;
    private String atrCatcde;
    private String atrCurrag;
    private String shouKuanName;
    private int vchNo = 0;
    private String shoukuanBank;
    private String ysxlstOrg;
    private String sctIpydat62;
    private String apcApcode;
    private String wkGlcopn;
    private String wkGlccat;
    private String wkGlcbal;
    private int tlrVchset;
    // OBF
    private Obf obf = new Obf();

    private int pdnPdnseq;
    // private int rpVLine;

    private int outrecVchset = 0;
    private String outrecTlrnum = "";
    private int tmpSetseq;
    private int wkEryvchTot;

    private static final String newPageFlag = "\f";
    private Long blhDraccm;
    private Long blhCraccm;

    // MERY
    private String meryTxnorg;
    private String meryClrorg;
    private String merySuporg;
    private String meryValdat;
    private String meryRvslbl;
    private String meryAnacde;
    private String meryFurinf;
    private int meryVchatt;
    private String meryThrref;
    private String meryVchaut;
    private String meryVchano;
    private String meryFstccy;
    private int meryFstamt;
    private String merySecccy;
    private int merySecamt;
    private int meryImmamt;
    private int meryFxrat1;
    private int meryFxrat2;
    private String meryFxflag;
    private String meryCrnyer;
    private String meryErydat;
    private String meryErytim;
    private String meryPrdcde;
    private String meryEvtcde;
    private String meryCatcde;
    private String meryCtpcde;
    private int meryPrdseq;
    private String meryTlrnum;
    private String meryActorg;
    private int meryVchset;
    private String meryCusid1;
    private String meryCusid2;
    private String meryCusid3;
    private String meryCusid4;
    private String meryCusid5;
    private String meryCusid6;
    private BigDecimal meryTxamt1;
    private BigDecimal meryTxamt2;
    private BigDecimal meryTxamt3;
    private BigDecimal meryTxamt4;
    private BigDecimal meryTxamt5;
    private BigDecimal meryTxamt6;
    private String meryCurcd1;
    private String meryCurcd2;
    private String meryCurcd3;
    private String meryCurcd4;
    private String meryCurcd5;
    private String meryCurcd6;
    private String meryCorapc;
    private String meryApcde1;
    private String meryApcde2;
    private String meryApcde3;
    private String meryApcde4;
    private String meryApcde5;
    private String meryApcde6;
    private long meryActbl1;
    private long meryActbl2;
    private long meryActbl3;
    private long meryActbl4;
    private long meryActbl5;
    private long meryActbl6;
    private long meryActoc1;
    private long meryActoc2;
    private long meryActoc3;
    private long meryActoc4;
    private long meryActoc5;
    private long meryActoc6;
    private long meryComam1;
    private long meryComam2;
    private long meryComam3;
    private long meryComam4;
    private long meryComam5;
    private long meryComam6;
    private long meryIntam1;
    private long meryIntam2;
    private long meryIntam3;
    private long meryIntam4;
    private long meryIntam5;
    private long meryIntam6;
    private long meryTaxam1;
    private long meryTaxam2;
    private long meryTaxam3;
    private long meryTaxam4;
    private long meryTaxam5;
    private long meryTaxam6;
    private long meryFeeam1;
    private long meryFeeam2;
    private long meryFeeam3;
    private long meryFeeam4;
    private long meryFeeam5;
    private long meryFeeam6;
    private String meryDepid1;

    // ACKOERY
    // OERY
    private int oeryMaxCnt = 10;
    private int oeryCnt = 0;
    private String oeryCatcde = "";
    private OeryT[] oeryTable = new OeryT[10];
    // OCCY
    private int occyMaxCnt = 50;
    private int occyCnt = 0;
    private OccyT[] occyTable = new OccyT[50];
    // OOBF-BUFFER
    private int oobfMaxCnt = 10;
    private int oobfCnt = 0;
    private Obf[] oobfTable = new Obf[10];
    // OVCH-BUFFER
    private int ovchMaxCnt = 10;
    private int ovchCnt = 0;
    private Vch[] ovchTable = new Vch[10];
    // OAPC
    private int oapcMaxCnt = 50;
    private int oapcCnt = 0;
    private OapcT[] oapcTable = new OapcT[50];

    // ERY
    private String eryErykey;
    private String eryPrdcde;
    private String eryEvtcde;
    private String eryCtpcde;
    private String eryCatcde;
    private Short eryEryseq;
    private String eryOrgidt = "   ";
    private String eryCusidt = "       ";
    private String eryCusid1;
    private String idtSysidt;
    private String idtOrgidt;
    private String idtDepnum;
    private String idtFiller = "";
    private String eryApcode;
    private String eryCurcde;
    private String eryEryamt;
    private Short eryCdrflg;
    private String eryVchpar;
    private Short eryTotnum;
    private String eryCorcur;
    private String eryCorapc;
    private String eryEryrem;
    private String eryAmdtlr;
    private String eryUpddat;
    private String eryRecsts;
    private static final String CNST_M_SYSIDT = "8";
    private String CNST_M_CHK_BAL = "";
    private static final String CNST_M_CRBAL_FLAG = "C";
    private static final String CNST_M_DRBAL_FLAG = "D";
    private static final String CNST_M_BBBAL_FLAG = "B";
    private String apcApctyp;
    private int readeryEndFlg;
    private String glcGlcopn;
    private Actoac oac;
    private static final String CNST_M_OACFLG_OPEN = "0";
    private static final String CNST_M_LEGFMT_F = "F";
    private static final String CNST_M_LEGFMT_I = "I";
    private String autoTlrnum = "AX10";
    private static final String CNST_M_RECSTS_NORMAL = " ";
    private static final String CNST_VCH_ERYTYP_AUTO = "A";
    private String CNST_M_ORGIDT = "";
    private static final String CNST_M_ORGIDT_TXN = "TXN";
    private static final String CNST_M_ORGIDT_ACT = "ACT";
    private static final String CNST_M_ORGIDT_CLR = "CLR";
    private static final String CNST_M_ORGIDT_SUP = "SUP";
    private String CNST_M_CUSIDT = "";
    private String CNST_M_CUSIDT_ITG = "9     0";
    private String CNST_M_CUSIDT_FST = "9";
    private String CNST_M_CUSIDT_ORG = "   ";
    private String CNST_M_CUSIDT_DEP = "  ";
    private String CNST_M_CUSIDT_CHK = "0";
    private String CNST_M_CUSIDT_IBK = "86     ";
    private String CNST_M_CUSIDT_BNK = "     ";
    private static final String CNST_M_CUSIDT_BNK_ACT = "ACIBK";
    private static final String CNST_M_CUSIDT_BNK_SUP = "SUIBK";
    private static final String CNST_M_CUSIDT_CUSIDT1 = "CUSIDT1";
    private static final String CNST_M_CUSIDT_CUSIDT2 = "CUSIDT2";
    private static final String CNST_M_CUSIDT_CUSIDT3 = "CUSIDT3";
    private static final String CNST_M_CUSIDT_CUSIDT4 = "CUSIDT4";
    private static final String CNST_M_CUSIDT_CUSIDT5 = "CUSIDT5";
    private static final String CNST_M_CUSIDT_CUSIDT6 = "CUSIDT6";
    private static final String CNST_M_CUSIDT_ORG_TXN = "TXN";
    private static final String CNST_M_CUSIDT_ORG_ACT = "ACT";
    private static final String CNST_M_CUSIDT_ORG_CLR = "CLR";
    private static final String CNST_M_CUSIDT_ORG_SUP = "SUP";
    private String CNST_M_APCODE = "    ";
    private static final String CNST_M_APCODE_APC1 = "APC1";
    private static final String CNST_M_APCODE_APC2 = "APC2";
    private static final String CNST_M_APCODE_APC3 = "APC3";
    private static final String CNST_M_APCODE_APC4 = "APC4";
    private static final String CNST_M_APCODE_APC5 = "APC5";
    private static final String CNST_M_APCODE_APC6 = "APC6";
    private static final String CNST_M_APCODE_CAPC = "CAPC";
    private static final String CNST_M_APCODE_9990 = "9991";
    private static final String CNST_M_APCODE_7010 = "7011";
    private static final String CNST_M_APCODE_9300 = "9301";
    private String CNST_M_CURCDE = "   ";
    private static final String CNST_M_CURCDE_CC1 = "CC1";
    private static final String CNST_M_CURCDE_CC2 = "CC2";
    private static final String CNST_M_CURCDE_CC3 = "CC3";
    private static final String CNST_M_CURCDE_CC4 = "CC4";
    private static final String CNST_M_CURCDE_CC5 = "CC5";
    private static final String CNST_M_CURCDE_CC6 = "CC6";
    private static final String CNST_M_CURCDE_FST = "FST";
    private static final String CNST_M_CURCDE_SEC = "SEC";
    private String CNST_M_ERYAMT = "  ";
    private static final String CNST_M_ERYAMT_11 = "11";
    private static final String CNST_M_ERYAMT_12 = "12";
    private static final String CNST_M_ERYAMT_13 = "13";
    private static final String CNST_M_ERYAMT_14 = "14";
    private static final String CNST_M_ERYAMT_15 = "15";
    private static final String CNST_M_ERYAMT_16 = "16";
    private static final String CNST_M_ERYAMT_21 = "21";
    private static final String CNST_M_ERYAMT_22 = "22";
    private static final String CNST_M_ERYAMT_23 = "23";
    private static final String CNST_M_ERYAMT_24 = "24";
    private static final String CNST_M_ERYAMT_25 = "25";
    private static final String CNST_M_ERYAMT_26 = "26";
    private static final String CNST_M_ERYAMT_31 = "31";
    private static final String CNST_M_ERYAMT_32 = "32";
    private static final String CNST_M_ERYAMT_33 = "33";
    private static final String CNST_M_ERYAMT_34 = "34";
    private static final String CNST_M_ERYAMT_35 = "35";
    private static final String CNST_M_ERYAMT_36 = "36";
    private static final String CNST_M_ERYAMT_41 = "41";
    private static final String CNST_M_ERYAMT_42 = "42";
    private static final String CNST_M_ERYAMT_43 = "43";
    private static final String CNST_M_ERYAMT_44 = "44";
    private static final String CNST_M_ERYAMT_45 = "45";
    private static final String CNST_M_ERYAMT_46 = "46";
    private static final String CNST_M_ERYAMT_51 = "51";
    private static final String CNST_M_ERYAMT_52 = "52";
    private static final String CNST_M_ERYAMT_53 = "53";
    private static final String CNST_M_ERYAMT_54 = "54";
    private static final String CNST_M_ERYAMT_55 = "55";
    private static final String CNST_M_ERYAMT_56 = "56";
    private static final String CNST_M_ERYAMT_61 = "61";
    private static final String CNST_M_ERYAMT_62 = "62";
    private static final String CNST_M_ERYAMT_63 = "63";
    private static final String CNST_M_ERYAMT_64 = "64";
    private static final String CNST_M_ERYAMT_65 = "65";
    private static final String CNST_M_ERYAMT_66 = "66";
    private static final String CNST_M_ERYAMT_71 = "71";
    private static final String CNST_M_ERYAMT_72 = "72";
    private static final String CNST_M_ERYAMT_73 = "73";
    private static final String CNST_M_ERYAMT_74 = "74";
    private static final String CNST_M_ERYAMT_75 = "75";
    private static final String CNST_M_ERYAMT_76 = "76";
    private String CNST_M_FXFLAG = "";
    private static final String CNST_M_FXFLAG_0 = "0";
    private static final String CNST_M_FXFLAG_1 = "1";
    private static final String CNST_WK_RMB = "001";
    private   String CNST_M_CUSIDT_HED = "86";

    private Map<String,String> oldacnMaps;   // 新旧账号Map<内部账号,Oldacn>
    private Map<String,String> apcodeMaps;  //  核算码和利息支出核算码

    // 3000 5000
    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {

            String Ipymak = null;
            int sctnum = Integer.parseInt(ACEnum.SYSIDT_AC.getStatus());
            Ipymak = mapper.qryIpymakByNum(sctnum);
            if ("N".equalsIgnoreCase(Ipymak)) {
                logger.info("Ipymak = "+Ipymak+" 不计息.");
                return;
            }
            // 生成intflag标志文件，批量shell脚本中判断文件是否存在
           createIntflagFile();
            // 初始化几个对象数组变量
            initObjectArray();
            // 初始化新旧账号对照Map
            initOldactMaps();
            // 初始化核算码和利息支出核算码
             initApcodeMaps();

            SctBean sct = mapper.qrySctByNum(sctnum);
            if (sct == null) {
                logger.error("NOT FOUNDT ACTSCT ");
                return;
            }
            sctCrndat = sct.getCrndat();
            sctIpydat = sct.getIpydat();
            sctNwkday = sct.getNwkday();
            revErydat = sct.getIpydat1();
            sctIpydat1 = sct.getIpydat2();
            sctIpydat62 = sdfdate10.format(sctIpydat).substring(5, 7);
            // 自动转账
            Actatr atr = mapper.qryAtrByCde(ACEnum.ATRCDE_A10.getStatus());
            if (atr == null) {
                logger.error("READ ATRATR ERROR ! ATRCDE:  " + ACEnum.ATRCDE_A10.getStatus());
                return;
            }
            atrPrdcde = atr.getPrdcde();
            atrEvtcde = atr.getEvtcde();
            atrCtpcde = atr.getCtpcde();
            atrCatcde = atr.getCatcde();
            atrCurrag = atr.getCurrag();
            wkTaxapc = atr.getApcode();
            //  增加按机构处理
            List<Ptdept> deptList = mapper.qryAllPtdept();
            if (deptList != null && !deptList.isEmpty()) {
                for (Ptdept dept : deptList) {
                    initFilePathByDept(dept);
                    aos = mapper.qryActAndGlcs(ACEnum.ATRCDE_A10.getStatus(), ACEnum.TRFKID_3.getStatus(),
                            ACEnum.TRFKID_1.getStatus(), ACEnum.TRFKID_7.getStatus(), atrCurrag, ACEnum.RECSTS_VALID.getStatus(),
                            ACEnum.INTFLG_AIF.getStatus(), ACEnum.INTFLG_AIF_4.getStatus(), dept.getDeptid());
                    if (aos != null && !aos.isEmpty()) {
                        wkLOrgidt = "";
                        for (ActAndGlc ao : aos) {
                            actSysidt = ao.getSysidt();
                            actOrgidt = ao.getOrgidt();
                            actCusidt = ao.getCusidt();
                            actApcode = ao.getApcode();
                            actCurcde = ao.getCurcde();
                            actActglc = ao.getActglc();
                            glcGlcbal = ao.getGlcbal();
                            actActnam = ao.getActnam();
                            actAvabal = ao.getAvabal();
                            actDinrat = ao.getDinrat();
                            actCinrat = ao.getCinrat();
                            actInttra = ao.getInttra();
                            actDepnum = ao.getDepnum();
                            actCacint = ao.getCacint();
                            if(actCacint < 0) {
                            logger.info("ACTACT ====  "+actInttra +"   :    "+actCacint);
                            }
                            actDacint = ao.getDacint();
                            actCratsf = ao.getCratsf();
                            actDratsf = ao.getDratsf();
                            actIntflg = ao.getIntflg();
                            actIntcyc = ao.getIntcyc();
                            actDraccm = ao.getDraccm();
                            actCraccm = ao.getCraccm();
                            actLintdt = ao.getLintdt();
                            actOpndat = ao.getOpndat();
                            shouKuanName = actActnam;
                            huMing = actActnam;
                            //   5100-PROCESS-EVERY-ORGIDT
                            processEveryOrgidt();
                        }
                    }
                    //  5106-UPDATE-PDNTLR
                    updatePdntlr();
                    //   5213-ERR-TAIL-RTN
                    errTailRtn();
                    //  5222-REPORT-TAIL
                    reportTail();
                    //  5232-SHOUKUAN-BOT
                    shoukuanBot();
                    // 5243-CHG-PAGE
                    chgPage();
                    // 生成转账借方凭证  （sbs中没有）
                     generateTranDebitVoucherByOrg(dept);
                    outFileHandler.writeToFile();
                    errFileHandler.writeToReport();
                    lstFileHandler.writeToReport();
                    vchFileHandler.writeToFile();
                }
            }
/*
             if(true){
                        throw new RuntimeException("");
                    }*/

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 生成转账借方凭证  （sbs中没有）
    private void generateTranDebitVoucherByOrg(Ptdept dept) {
        List<TranDebitVoucher> glcintList = mapper.qryVchForInterestInfos(dept.getDeptid());
        if(glcintList != null && !glcintList.isEmpty()) {
            int index = 1;
            for(TranDebitVoucher voucher : glcintList) {
                if(index != 1 && index % 2 != 0) {
                  voucherHandler.appendToBody(newPageFlag);
                }
                String glcnam  = mapper.qryGlcnamByCode(voucher.getCorglc());
                voucher.setOrgnam(dept.getDeptname());
                voucher.setCorglcnam(glcnam);
                voucher.setDate(busDate10);
                voucher.setIndex(index++);
                voucherHandler.appendToBody(voucher.generateRptUnit());
            }
            voucherHandler.writeToFile();
        }
    }

    private void initApcodeMaps() {
      apcodeMaps = new HashMap<String,String>();
      List<Actapc> apcList = mapper.qryApcs();
      if(apcList != null && !apcList.isEmpty()) {
         for(Actapc apc : apcList) {
             apcodeMaps.put(apc.getApcode(),apc.getIntexp());
         }
      }
    }

    private void initOldactMaps() {
        oldacnMaps = new HashMap<String,String>();
        List<Actani> aniList = mapper.qryActanis();
        if(aniList != null && !aniList.isEmpty()) {
            StringBuilder keyBuilder = null;
            for(Actani ani : aniList) {
                keyBuilder = new StringBuilder();
                keyBuilder.append(ani.getOrgidt()).append(ani.getCusidt()).append(ani.getApcode()).append(ani.getCurcde());
                oldacnMaps.put(keyBuilder.toString(),ani.getOldacn());
            }
        }
    }

    // 初始化数组变量
    private void initObjectArray() {
        int i = 0;
        for(i = 0 ; i < oeryTable.length;i++) {
            oeryTable[i] = new OeryT();
        }
         for(i = 0 ; i < occyTable.length;i++) {
            occyTable[i] = new OccyT();
        }
     /*  for(i = 0 ; i < oobfTable.length;i++) {
            oobfTable[i] = new Obf();
        }
        for(i = 0 ; i < ovchTable.length;i++) {
            ovchTable[i] = new Vch();
        }*/
         for(i = 0 ; i < oapcTable.length;i++) {
            oapcTable[i] = new OapcT();
        }

    }

    // 生成intflag利息标志文件
    private void createIntflagFile() throws IOException {
         File tempFile = new File(PropertyManager.getProperty("ACBINT_FLAGPATH"),
                    PropertyManager.getProperty("ACBINT_FLAG_FILENAME"));
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
    }

    //   5100-PROCESS-EVERY-ORGIDT
    private void processEveryOrgidt() {

        if (!wkLOrgidt.equals(actOrgidt)) {
            if (!"".equals(wkLOrgidt)) {
                //  5106-UPDATE-PDNTLR
                updatePdntlr();
                //   5213-ERR-TAIL-RTN
                errTailRtn();
                //  5222-REPORT-TAIL
                reportTail();
                //  5232-SHOUKUAN-BOT
                shoukuanBot();
                // 5243-CHG-PAGE
                chgPage();
                //   CLOSE    SHOURU, YSXLST, YSXVCH,YSXERR
            }
            wkTlr = firstTlr;
            // 5107-SELECT-ACTPDN
            selectActpdn();
            //   5108-SELECT-ACTTLR
            selectActtlr();
            //  5109-SELECT-ORGNAM
            selectOrgnam();
            shoukuanNo = 0;
            vchNo = 0;
            pageNo = 1;
            errPage = 1;
            wkLOrgidt = actOrgidt;
            shouRuOrg = actOrgidt;
            ysxlstOrg = actOrgidt;
            ysxvchOrg = actOrgidt;
            ysxerrOrg = actOrgidt;
            shoukuanBank = orgOrgnam;
            jixiDate2 = sdfdate10.format(sctCrndat);
            //    5212-ERR-HEAD-RTN
            errHeadRtn();
            //  5221-REPORT-HEAD
            reportHead();
        }
        if ("D".equals(glcGlcbal)) {
            wkInts = actDacint;
            actCacint = 0L;
        } else {
            wkInts = actDacint + actCacint;
        }
        if ((((!"8272".equals(actApcode) || !"001".equals(actCurcde)) &&
                (("M".equals(actIntcyc) || "S".equals(actIntcyc)) && (
                        "03".equals(sctIpydat62) || "06".equals(sctIpydat62)
                                || "09".equals(sctIpydat62) || "12".equals(sctIpydat62)) ||
                        "H".equals(actIntcyc) && ("06".equals(sctIpydat62) ||
                                "12".equals(sctIpydat62)) ||
                        "Y".equals(actIntcyc) && ("12".equals(sctIpydat62)))))
                && wkInts != 0) {
            //   9901-READ-CCY-CIR-RTN
            readCcyCirRtn();
            //  5200-WRITE-VCH-RTN
            writeVchRtn();
            // 5300-INSERT-ACTBLH
            insertActblh();
            // 5400-SQL-INSERT-CIT
            sqlInsertCit();
        }
    }

    //   5230-PRINT-SHOUKUAN
    private void printShouKuan() {
        // 每页出3个通知
        if (shoukuanNo != 0 && shoukuanNo % 3 == 0) {
           // shoukuanNo = 0;
           // shoukuanBot();
            outFileHandler.appendToBody(newPageFlag); // 换页
        }
        shoukuanBank = orgOrgnam;
        outTemplate = null;
        outTemplate = new InterestOutTemplate();
        outTemplate.setTitleName("               利息通知单");
        outTemplate.setOrgidt(actOrgidt+orgOrgnam);
        outTemplate.setDate(sdfdate10.format(sctIpydat));
        outTemplate.setActno(oldacnMaps.get(actOrgidt + actCusidt + actApcode + actCurcde));
        //outTemplate.setFromActno(nbActToOldacn(actInttra.substring(1)));
        outTemplate.setFromActno(nbActToOldacn(actOrgidt + actCusidt + actApcode + actCurcde));
        outTemplate.setActnam(actActnam);
        if ("D".equals(glcGlcbal)) {
            shoukuanInts = actDacint;
            outTemplate.setInterest(SystemService.formatStrAmt(String.format("%.2f", shoukuanInts/100.0)));
            //outTemplate.setFints("");
        } else {
            shoukuanInts = actCacint;
            shoukuanFInts = actDacint;
            outTemplate.setInterest(SystemService.formatStrAmt(String.format("%.2f", shoukuanInts/100.0)));
            //outTemplate.setFints(String.valueOf(shoukuanFInts));
        }
        jixiDate1 = sdfdate10.format(actLintdt);
        outTemplate.setStartDate(jixiDate1);
        outTemplate.setEndDate(jixiDate2);
        if (actCacint != 0) {
            outTemplate.setCraccm(String.format("%.2f", actCraccm/100.0));
            outTemplate.setRate(String.valueOf(actCratsf)+"%");
            if ("8272".equals(actApcode) && "001".equals(actCurcde)) {
                tmpInts = new BigDecimal(actCacint + "").subtract(wkInttax);
               // shengxiInts = tmpInts;
               // outTemplate.setSxInterest(String.valueOf(shengxiInts));
            } else {
                //shengxiInts = new BigDecimal(actCacint);
               // outTemplate.setSxInterest(String.valueOf(shengxiInts));
            }
            if ("8272".equals(actApcode) && "001".equals(actCurcde)) {
                tmpInts = new BigDecimal(wkCraccm - wkDraccm);
                //  没有应付利息和代扣税款
                /*aftTaxInt = actCacint;
              intTax = wkInttax;*/
            }
        }
        if (actDacint != 0) {
            outTemplate.setCraccm(String.format("%.2f", actDraccm/100.0));
            outTemplate.setRate(String.valueOf(actDratsf));
           // shengxiInts = new BigDecimal(actDacint);
           // outTemplate.setSxInterest(String.valueOf(shengxiInts));
        }
        //outTemplate.setVchset(String.valueOf(wkTlr + tlrVchset));
        outFileHandler.appendToBody("第"+(shoukuanNo/3 +1)+"页, 序号:" +(shoukuanNo+1));
        outFileHandler.appendToBody(outTemplate.toString());
        shoukuanNo++;
    }

    protected void initBatch(final BatchParameterData batchParam) {
    }

    private void initFilePathByDept(Ptdept dept) {
        outFileHandler = new BatchResultFileHandler("存款计息通知单8910out.txt");
        vchFileHandler = new BatchResultFileHandler("存款计息传票8910vch.txt");
        errFileHandler = new InterestErrTemplate("存款未计息清单8910err.txt");
        lstFileHandler = new InterestLstTemplate("存款计息清单8910lst.txt");
        voucherHandler = new BatchResultFileHandler("转账借方凭证.txt");
        String filePath = PropertyManager.getProperty("REPORT_ROOTPATH") + busDate + "/" +  dept.getDeptid() + "/";
        lstFileHandler.setFilePath(filePath);
        lstFileHandler.setTitle("                                                    存款计息清单");
        outFileHandler.setFilePath(filePath);
        vchFileHandler.setFilePath(filePath);
        voucherHandler.setFilePath(filePath);

        errFileHandler.setFilePath(filePath);
        errFileHandler.setTitle("                                                  存款未计息清单");
    }

    //    5200-WRITE-VCH-RTN
    private void writeVchRtn() {
        // 5250-SELECT-INTEXP
        selectIntexp();
        //  5210-CHECK-INTTRA-RTN
        checkInttraRtn();
        if (rtnEryCode != 0) {
            return;
        }
        apcApcode = inttraApcode;
        Actglc glc = mapper.qryGlcByApcode(apcApcode);
        if (glc != null) {
            wkGlcopn = glc.getGlcopn();
            wkGlccat = glc.getGlccat();
            wkGlcbal = glc.getGlcbal();
        } else {
            errReason = "转息账户核算码错误";
            errActSysidt = actSysidt;
            errActOrgidt = actOrgidt;
            errActCusidt = actCusidt;
            errActApcode = actApcode;
            errActCurcde = actCurcde;
            errActnam = actActnam;
            errInttra = actInttra;
            //  5211-PRT-ERR-RTN
            prtErrTrn();
            return;
        }
        obf.obfkey = actInttra;
        if (obf.obfkey == null || obf.obfkey.trim().length() != 18) {
            throw new RuntimeException("OBF-OBFKEY:" + obf.obfkey + " 长度不等于18");
        }
        obf.sysidt = obf.obfkey.substring(0, 1);
        obf.orgidt = obf.obfkey.substring(1, 4);
        obf.cusidt = obf.obfkey.substring(4, 11);
        obf.apcode = obf.obfkey.substring(11, 15);
        obf.curcde = obf.obfkey.substring(15, 18);
        Actobf actobf = mapper.qryUniqueObf(obf.sysidt, obf.orgidt, obf.cusidt, obf.apcode, obf.curcde);
        if (actobf != null) {
            obf.bokbal = actobf.getBokbal();
            obf.avabal = actobf.getAvabal();
            obf.difbal = actobf.getDifbal();
            obf.cifbal = actobf.getCifbal();
            obf.actsts = actobf.getActsts();
            obf.frzsts = actobf.getFrzsts();
            obf.regsts = actobf.getRegsts();
            obf.recsts = actobf.getRecsts();
            obf.ovelim = actobf.getOvelim();
            obf.oveexp = actobf.getOveexp();
            obf.cqeflg = actobf.getCqeflg();
            obf.glcbal = actobf.getGlcbal();
        }
        if (actobf == null || (!" ".equals(actobf.getActsts()) || !" ".equals(actobf.getRecsts()))) {
            if ("0".equals(wkGlcopn)) {
                errReason = "转息账号不存在";
                errActSysidt = actSysidt;
                errActOrgidt = actOrgidt;
                errActCusidt = actCusidt;
                errActApcode = actApcode;
                errActCurcde = actCurcde;
                errActnam = actActnam;
                errInttra = actInttra;
                //  5211-PRT-ERR-RTN
                prtErrTrn();
                return;
            } else {
                obf.glcbal = wkGlcbal;
            }
        }

        if (tlrVchset >= 9999) {
            // 5106 -UPDATE-PDNTLR
            updatePdntlr();
            tlrNum += 1;
            tlrTail = tlrNum;
            wkTlr = tmpTlr;
            //  5108-SELECT-ACTTLR
            selectActtlr();
        }
        meryTxnorg = actOrgidt;
        meryClrorg = actOrgidt;
        wkEffdat = sdfdate10.format(sctIpydat);
        wkVchdat = wkEffdat;
        meryValdat = wkVchdat;
        meryRvslbl = " ";
        meryAnacde = "ZSAA";
        meryFurinf = ACEnum.A10_NAME.getStatus();
        meryVchatt = 0;
        meryThrref = "";
        meryVchaut = "";
        meryVchano = "";
        meryFstccy = "";
        meryFstamt = 0;
        merySecccy = "";
        merySecamt = 0;
        meryImmamt = 0;
        meryFxrat1 = 0;
        meryFxrat2 = 0;
        meryFxflag = "0";
        wkYear = wkEffdat.substring(0, 4);
        meryCrnyer = wkYear.substring(2, 4);
        wkEffdat = sdfdate10.format(sctNwkday);
        wkVchdat = wkEffdat;
        meryErydat = wkVchdat;
        meryErytim = "00:00:00";
        vch.depnum = actDepnum;
        if (actCacint != 0) {
            pdnPdnseq += 1;
            tlrVchset += 1;
            meryPrdcde = atrPrdcde;
            meryEvtcde = atrEvtcde;
            meryCtpcde = atrCtpcde;
            meryCatcde = "11";
            meryPrdseq = pdnPdnseq;
            meryTlrnum = wkTlr;
            meryVchset = tlrVchset;
            meryActorg = actOrgidt;
            meryCusid1 = inttraCusidt;
            meryApcde1 = inttraApcode;
            meryCurcd1 = actCurcde;
            meryApcde2 = apcIntexp;
            meryTxamt1 = new BigDecimal(actCacint);
            if(!"2".equals(meryApcde1.substring(0,1))){
            logger.info("meryApcde1 == " + meryApcde1.toString());
            logger.info("meryApcde2 == " + meryApcde2.toString());
            logger.info("meryTxamt1 == " + meryTxamt1.toString());
            }

            meryTxamt2 = wkInttax;
            if ("8272".equals(actApcode) && "001".equals(actCurcde) && !new BigDecimal(0).equals(wkInttax)) {
                wkActkey = wkSysidt + wkOrgidt + wkCusidt + wkApcode + wkCurcde;
                meryFurinf = wkActkey;
            }
            // 9951-IFA-PROC-RTN
            ifaProcRtn();
            if (rtnEryCode == 0) {
                // 5224-SELECT-ACTBLH
                selectActblh();
                // 5220-REPORT-RECORD
                reportRecord();
                // 5230-PRINT-SHOUKUAN
                printShouKuan();
                // 5240-PRINT-VCH-REC
                printVchRec();
            } else if (rtnEryCode < 10) {
                errReason = "账户透支";
                errActSysidt = actSysidt;
                errActOrgidt = actOrgidt;
                errActCusidt = actCusidt;
                errActApcode = actApcode;
                errActCurcde = actCurcde;
                errActnam = actActnam;
                errInttra = obf.obfkey;
                //  5211-PRT-ERR-RTN
                prtErrTrn();
            } else if (rtnEryCode == 96) {
                errReason = "自动开户不成功";
                errActSysidt = actSysidt;
                errActOrgidt = actOrgidt;
                errActCusidt = actCusidt;
                errActApcode = actApcode;
                errActCurcde = actCurcde;
                errActnam = actActnam;
                errInttra = obf.obfkey;
                //  5211-PRT-ERR-RTN
                prtErrTrn();
            } else {
                // 99515-IFA-ERY-CHK
                ifaEryChk();
            }
        }
        /*  if (rpVLine != 0) {
            logger.info("WRITE VCH,OBF ERROR!");
            logger.info("账号:" + actCusidt + actApcode + actCurcde);
        }*/
        if (actDacint != 0) {
            pdnPdnseq += 1;
            tlrVchset += 1;
            meryPrdseq = pdnPdnseq;
            meryTlrnum = wkTlr;
            meryVchset = tlrVchset;
            meryActorg = actOrgidt;
            if ("0790".equals(inttraApcode)) {
                apcIntinc = "0890";
            }
            meryApcde2 = apcIntinc;
            meryCurcd1 = actCurcde;
            meryCorapc = inttraApcode;
            meryCusid1 = inttraCusidt;
            meryApcde1 = inttraApcode;
            meryCurcd1 = inttraCurcde;
            meryTxamt1 = new BigDecimal(actDacint);
            meryCatcde = "12";
            meryPrdcde = atrPrdcde;
            meryEvtcde = atrEvtcde;
            meryCtpcde = atrCtpcde;
            // 9951-IFA-PROC-RTN
            ifaProcRtn();
            if (rtnEryCode == 0) {
                // 5224-SELECT-ACTBLH
                selectActblh();
                // 5220-REPORT-RECORD
                reportRecord();
                // 5230-PRINT-SHOUKUAN
                printShouKuan();
                // 5240-PRINT-VCH-REC
                printVchRec();
            } else if (rtnEryCode < 10) {
                errReason = "账户透支";
                errActSysidt = actSysidt;
                errActOrgidt = actOrgidt;
                errActCusidt = actCusidt;
                errActApcode = actApcode;
                errActCurcde = actCurcde;
                errActnam = actActnam;
                errInttra = obf.obfkey;
                //  5211-PRT-ERR-RTN
                prtErrTrn();
            } else if (rtnEryCode == 96) {
                errReason = "自动开户不成功";
                errActSysidt = actSysidt;
                errActOrgidt = actOrgidt;
                errActCusidt = actCusidt;
                errActApcode = actApcode;
                errActCurcde = actCurcde;
                errActnam = actActnam;

                errInttra = obf.obfkey;
                //  5211-PRT-ERR-RTN
                prtErrTrn();
            } else {
                // 99515-IFA-ERY-CHK
                ifaEryChk();
            }
        }
        /* if (rpVLine != 0) {
            logger.info("WRITE VCH,OBF ERROR!");
            logger.info("账号:" + actCusidt + actApcode + actCurcde);
        }*/
    }


    //  5240-PRINT-VCH-REC
    private void printVchRec() {
        if (vchNo != 0 && vchNo % 4 == 0) {
            chgPage();
            vchFileHandler.appendToBody(newPageFlag);
        }
        //  5241-HEAD-LINE
        headLine();
        //   99518-PRINT-VCH-REC
        printVchRec2();
        //    5242-BOT-LINE
        botLine();

         if (vchTemplate != null) {
            vchFileHandler.appendToBody("页码: "+(vchNo/4+1)+" 序号:"+(vchNo+1));
            vchFileHandler.appendToBody(vchTemplate.toString());
             vchNo++;
        }
    }

    //  99518-PRINT-VCH-REC
    private void printVchRec2() {
        ovchCnt = 0;
        while (ovchCnt <= ovchMaxCnt) {
            // PRT-VCH
            prtVch();
        }
        /*while(rpVLine > 0) {
            // PRT-EMPTY-LINE
            prtEmptyLine();
        }*/
    }

    //  PRT-EMPTY-LINE
    private void prtEmptyLine() {
    }

    //   PRT-VCH
    private void prtVch() {
        ovchCnt++;
        if (ovchCnt > ovchMaxCnt || "".equals(ovchTable[ovchCnt].apcode)) {
            ovchCnt = 99;
            return;
        }
        vch = ovchTable[ovchCnt];
        //rpVLine--;
        ccyCurcde = vch.curcde;
        //  99513-READ-CCY-RTN
        readCcyRtn();
        rpVchAmt1 = new BigDecimal(vch.txnamt);
        vchTemplate.setAmt(SystemService.formatStrAmt(String.format("%.2f", rpVchAmt1.divide(new BigDecimal(100.0)))));
        if (vch.txnamt < 0) {
            vch.actnum = vch.orgidt + vch.cusidt + vch.apcode + vch.curcde;
            rpVchAcno1 = vch.actnum;
            vchTemplate.setActno(oldacnMaps.get(rpVchAcno1));
        } else if (vch.txnamt > 0) {
            vch.actnum = vch.orgidt + vch.cusidt + vch.apcode + vch.curcde;
            rpVchAcno2 = vch.actnum;
            rpVchAmt2 = rpVchAmt1;
            vchTemplate.setFromActno(oldacnMaps.get(rpVchAcno2));
            vchTemplate.setNavAmt(SystemService.formatStrAmt(String.format("%.2f", rpVchAmt2.divide(new BigDecimal(100.0)))));
        } else {
            // rpVLine ++;
        }

    }

    //  99513-READ-CCY-RTN
    private void readCcyRtn() {
        occyCnt = 1;
        while (occyCnt <= occyMaxCnt && !"".equals(occyTable[occyCnt].occyCurcde)) {
            //  READ-OCCY-RTN
            readOccyRtn();
        }
        if (occyCnt == 99) {
            return;
        }
        try {
            ccyDecpos = mapper.qryDecposByCurcde(ccyCurcde);
        } catch (Exception e) {
            throw new RuntimeException("READ ACTCCY ERROR!  CURCDE :" + ccyCurcde);
        }
        if (occyCnt > occyMaxCnt) {
            occyCnt = occyMaxCnt;
        }
        occyTable[occyCnt].occyCurcde = ccyCurcde;
        occyTable[occyCnt].occyDecpos = ccyDecpos;
    }

    //   99514-READ-OCCY-RTN
    private void readOccyRtn() {
        if (ccyCurcde != null && ccyCurcde.equals(occyTable[occyCnt].occyCurcde)) {
            ccyDecpos = occyTable[occyCnt].occyDecpos;
            occyCnt = 99;
        } else {
            occyCnt++;
        }
    }

    // 5242-BOT-LINE
    private void botLine() {

    }

    // 5241-HEAD-LINE
    private void headLine() {

        vchTemplate = new InterestVchTemplate();
        vchTemplate.setTitleName("              存款计息传票");
        vchTemplate.setOrgidt(actOrgidt);
        vchTemplate.setDate(sdfdate10.format(sctCrndat));
        vchTemplate.setVchset(wkTlr + tlrVchset);
    }

    //  5210-CHECK-INTTRA-RTN
    private void checkInttraRtn() {
        rtnEryCode = 0;
        tmpInttra = actInttra;
        inttraOrgidt = tmpInttra.substring(1,4);
        inttraCusidt = tmpInttra.substring(4,11);
        inttraApcode = tmpInttra.substring(11,15);
        inttraCurcde = tmpInttra.substring(15,18);
        if ("".equals(actInttra.trim())) {
            errReason = "转息账号没输入";
            rtnEryCode = 1;
            errActSysidt = actSysidt;
            errActOrgidt = actOrgidt;
            errActCusidt = actCusidt;
            errActApcode = actApcode;
            errActCurcde = actCurcde;
            errActnam = actActnam;
            errInttra = actInttra;
            //  5211-PRT-ERR-RTN
            prtErrTrn();
            return;
        }
        if (!inttraCurcde.equals(actCurcde) || !inttraOrgidt.equals(actOrgidt)
                || "0890".equals(inttraApcode)) {
            errReason = "转息账号错误";
            rtnEryCode = 1;
            errActSysidt = actSysidt;
            errActOrgidt = actOrgidt;
            errActCusidt = actCusidt;
            errActApcode = actApcode;
            errActCurcde = actCurcde;
            errActnam = actActnam;
            errInttra = actInttra;
            //  5211-PRT-ERR-RTN
            prtErrTrn();
            return;
        }
        if (actDacint != 0 && "".equals(apcIntinc.trim()) && !"0790".equals(inttraApcode)) {
            errReason = "利息收入核算码未维护";
            rtnEryCode = 1;
            errActSysidt = actSysidt;
            errActOrgidt = actOrgidt;
            errActCusidt = actCusidt;
            errActApcode = actApcode;
            errActCurcde = actCurcde;
            errActnam = actActnam;
            errInttra = actInttra;
            //  5211-PRT-ERR-RTN
            prtErrTrn();
            return;
        }
        if (actCacint != 0 && "".equals(apcIntexp)) {
            errReason = "利息支出核算码未维护";
            rtnEryCode = 1;
            errActSysidt = actSysidt;
            errActOrgidt = actOrgidt;
            errActCusidt = actCusidt;
            errActApcode = actApcode;
            errActCurcde = actCurcde;
            errActnam = actActnam;
            errInttra = actInttra;
            //  5211-PRT-ERR-RTN
            prtErrTrn();
            return;
        }
    }

    //   5211-PRT-ERR-RTN
    private void prtErrTrn() {
        // 5212-ERR-HEAD-RTN
        errHeadRtn();
        if (actCacint != 0) {
            errAccum = wkCraccm;
            errInts = actCacint;
            errRat = actCratsf;
            errActno = errActOrgidt+errActCusidt+errActApcode+errActCurcde;
            //   "账号", "户名", "积数", "利率", "利息", "原因","转息账号"
            errFileHandler.appendFieldValues(new String[]{oldacnMaps.get(errActno) +"   ", errActnam, String.format("%.2f", errAccum/100.0),
                    String.valueOf(errRat)+"%",SystemService.formatStrAmt(String.format("%.2f", errInts/100.0)),errReason, errInttra});
            //errLine++;
        }
        if (actDacint != 0) {
            errAccum = wkDraccm;
            errInts = actDacint;
            errRat = actDratsf;
            errActno = errActOrgidt+errActCusidt+errActApcode+errActCurcde;
            errFileHandler.appendFieldValues(new String[]{oldacnMaps.get(errActno)+"   ", errActnam, String.format("%.2f", errAccum/100.0),
                    String.valueOf(errRat)+"%", SystemService.formatStrAmt(String.format("%.2f", errInts/100.0)), errReason, errInttra});
        }
    }

    //   5213-ERR-TAIL-RTN
    // 报表中添加分割线
    private void errTailRtn() {

    }

    //  5243-CHG-PAGE
    // 换页
    private void chgPage() {
    }

    //  5232-SHOUKUAN-BOT
    private void shoukuanBot() {

    }

    //   5250-SELECT-INTEXP
    private void selectIntexp() {
        apcApcode = actApcode;
        Actapc apc = mapper.qryApcByCode(apcApcode);
        if (apc == null) {
            throw new RuntimeException("在ACTAPC 无此科目 ：" + actApcode);
        } else {
            apcIntexp = apc.getIntexp();
            apcIntinc = apc.getIntinc();
        }
    }

    //    5212-ERR-HEAD-RTN
    private void errHeadRtn() {
        errOrgidt = actOrgidt;
        errDate = sdfdate10.format(sctCrndat);
        errPageNo = String.valueOf(errPage);
        errPage += 1;
        errFileHandler.setCrndat(errDate);
        errFileHandler.setOrgidt(errOrgidt);
        errFileHandler.setOrgnam(orgOrgnam);

    }

    //   5220-REPORT-RECORD
    private void reportRecord() {
        huMing = actActnam;
        accCusidt = actCusidt;
        accApcode = actApcode;
        accCurcde = actCurcde;
        account = accCusidt + accApcode + accCurcde;
        accountOut = account;
        outrecTlrnum = wkTlr;
        outrecVchset = tlrVchset;
        // 5221-REPORT-HEAD
        reportHead();
        if (actCacint != 0) {
            avabalOut = wkCraccm;
            cratsfOut = actCratsf;
            intsOut = actCacint;
            // "账号", "户名", "积数", "年利率", "利息", "传票套号"
            lstFileHandler.appendFieldValues(new String[]{oldacnMaps.get(actOrgidt+accountOut) +"   ", huMing, String.format("%.2f", avabalOut/100.0),
                    String.valueOf(cratsfOut)+"%", SystemService.formatStrAmt(String.format("%.2f", intsOut/100.0)), outrecTlrnum+outrecVchset});
        }
        if (actDacint != 0) {
            avabalOut = wkDraccm;
            cratsfOut = actDratsf;
            intsOut = actDacint;
            lstFileHandler.appendFieldValues(new String[]{oldacnMaps.get(actOrgidt+accountOut)+"   ", huMing, String.format("%.2f", avabalOut/100.0),
                    String.valueOf(cratsfOut)+"%", SystemService.formatStrAmt(String.format("%.2f", intsOut/100.0)), outrecTlrnum+outrecVchset});

        }
    }

    //    5221-REPORT-HEAD
    private void reportHead() {
        head4 = pageNo;
        pageNo += 1;
        outOrgidt = orgOrgidt;
        outBranch = orgOrgnam;
        outDate = sctNwkday;
        //  报表
        lstFileHandler.setOrgidt(outOrgidt);
        lstFileHandler.setOrgnam(orgOrgnam);
        lstFileHandler.setCrndat(sdfdate10.format(outDate));
        //lineNo = 0;
    }

    //  5300-INSERT-ACTBLH
    private void insertActblh() {
        logger.info(" INSERT BLH.");
        mapper.insertBlh(actOrgidt, actCusidt, actApcode, actCurcde, revErydat, actDepnum,
                actAvabal, actDraccm, actCraccm, actLintdt, actDratsf, actCratsf,
                wkDratsf, wkCratsf, ACEnum.RECSTS_VALID.getStatus());
        logger.info(" INSERT ACT.");
        mapper.updateAct(revErydat, sctIpydat, actOrgidt, actCusidt, actApcode, actCurcde, ACEnum.SYSIDT_AC.getStatus());
    }

    //  5400-SQL-INSERT-CIT
    private void sqlInsertCit() {
        citSysidt = ACEnum.SYSIDT_AC.getStatus();
        citOrgidt = actOrgidt;
        citCusidt = actCusidt;
        citApcode = actApcode;
        citCurcde = actCurcde;
        citIpydat = sctIpydat;
        wkIpyDate = citIpydat;
        wkIpyYY = sdfdate10.format(wkIpyDate).substring(0, 4);
        citYintsq = 0;
        citYintsq = mapper.qryYintsq(citSysidt, citOrgidt, citCusidt, citApcode, citCurcde, wkIpyYY);
        if ("1111-11-11".equals(actLintdt)) {
            citBegdat = actOpndat;
        } else {
            citBegdat = actLintdt;
        }
        citEnddat = revErydat;
        if (rtnEryCode != 0) {
            citVchset = 0;
            citReason = errReason;
            citIntflg = "N";
        } else {
            citVchset = tlrVchset;
            citReason = "";
            citIntflg = "Y";
        }
        citRecsts = " ";

        if (actCacint != 0) {
            citYintsq += 1;
            citIntcde = actCinrat;
            citIntrat = actCratsf;
            citIntamt = actCacint;
            mapper.insertCit(citSysidt, citOrgidt, citCusidt, citApcode, citCurcde, citIpydat, citYintsq,
                    citBegdat, citEnddat, citIntcde, citIntrat, citIntamt, citVchset, citReason, citIntflg, citRecsts);
        }
        if (actDacint != 0) {
            citYintsq += 1;
            citIntcde = actDinrat;
            citIntrat = actDratsf;
            citIntamt = actDacint;
            mapper.insertCit(citSysidt, citOrgidt, citCusidt, citApcode, citCurcde, citIpydat, citYintsq,
                    citBegdat, citEnddat, citIntcde, citIntrat, citIntamt, citVchset, citReason, citIntflg, citRecsts);
        }
    }

    //  5106-UPDATE-PDNTLR
    private void updatePdntlr() {
        try {
            mapper.updatePdn(pdnPdnseq, wkLOrgidt, atrPrdcde);
        } catch (Exception e) {
            throw new RuntimeException("UPDATE　ACTPDN ERROR ! orgidt=" + wkLOrgidt + "  prdcde=" + wkPrdcde);
        }
        try {
            mapper.updateTlr(tlrVchset, wkLOrgidt, wkTlr);
        } catch (Exception e) {
            throw new RuntimeException("UPDATE ACTTLR ERROR ! orgidt=" + wkLOrgidt);
        }
    }

    //  5109-SELECT-ORGNAM
    private void selectOrgnam() {
        orgOrgidt = actOrgidt;
        try {
            Actorg org = mapper.qryOrgByOrgidt(orgOrgidt);
            if (org != null) {
                orgOrgnam = org.getOrgnam();
                orgSupbak = org.getSupbak();
            }
        } catch (Exception e) {
            throw new RuntimeException("SELECT ACTORG ERROR ! orgidt=" + orgOrgidt);
        }
    }

    //  5108-SELECT-ACTTLR
    // 取自动柜员传票套号
    private void selectActtlr() {
        Integer tempVchset = mapper.qryVchset(actOrgidt, wkTlr);
        if (tempVchset == null) {
            tlrVchset = 0;
            mapper.insertTlr(actOrgidt, wkTlr, tlrVchset, ACEnum.RECSTS_VALID.getStatus());
        } else {
            tlrVchset = tempVchset;
        }
    }

    //  5107-SELECT-ACTPDN
    // 取该机构年内产品顺序号
    private void selectActpdn() {
        Integer intPdnseq = mapper.qryPdnseq(actOrgidt, atrPrdcde);
        if (intPdnseq == null) {
            pdnPdnseq = 0;
            mapper.insertPdn(actOrgidt, atrPrdcde, pdnPdnseq, wkTmpdatYY, ACEnum.RECSTS_VALID.getStatus());
        } else {
            pdnPdnseq = intPdnseq;
        }
    }

    //   5224-SELECT-ACTBLH
    private void selectActblh() {
        Actblh blh = mapper.selectBlh(actOrgidt, actCusidt, actApcode, actCurcde, actLintdt);
        if (blh != null) {
            blhDraccm = blh.getDraccm();
            blhCraccm = blh.getCraccm();
        }
        wkDraccm = actDraccm + blhDraccm;
        wkCraccm = actCraccm + blhCraccm;
    }

    //  5222-REPORT-TAIL
    // 添加分割线和 复核 记账
    private void reportTail() {
        // vchFileHandler.appendToBody("     ---------------------------------------------------------------------- ");
        //  vchFileHandler.appendToBody("                    复　核:                       记　帐:                   ");
    }

    //  9901-READ-CCY-CIR-RTN
    private void readCcyCirRtn() {
        ccyCurcde = actCurcde;
        try {
            ccyDecpos = mapper.qryDecposByCurcde(ccyCurcde);
        } catch (Exception e) {
            throw new RuntimeException("READ ACTCCY ERROR  CURCDE :" + ccyCurcde);
        }
        wkCratsf = new BigDecimal(0);
        wkDratsf = new BigDecimal(0);
        if (actCacint != 0) {
            if ("999".equals(actCinrat)) {
                if ("001".equals(actCurcde)) {
                    wkCratsf = actCratsf.divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                } else {
                    wkCratsf = actCratsf.divide(new BigDecimal(100)).divide(new BigDecimal(360));
                }
            } else {
                liluNo = actCinrat;
                try {
                    Actcir cir = mapper.qryCirByIrtkd(actCurcde, liluNo.substring(0, 1), liluNo.substring(1, 3));
                    if (cir != null) {
                        cirCurirt = cir.getCurirt();
                        cirIrtval = cir.getIrtval();
                        actCratsf = cirIrtval;
                        wkCratsf = cirCurirt;
                    } else {
                        wkCratsf = new BigDecimal(0);
                        throw new RuntimeException("ACTCIR IS NULL!!!  CURCDE : " + wkCurcde);
                    }
                } catch (Exception e) {
                    wkCratsf = new BigDecimal(0);
                    throw new RuntimeException("SELECT FROM ACTCIR ERROR!!! CURCDE : " + wkCurcde);
                }
            }
        }
        if (actDacint != 0) {
            if ("999".equals(actDinrat)) {
                if ("001".equals(actCurcde)) {
                    wkDratsf = actDratsf.divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                } else {
                    wkDratsf = actDratsf.divide(new BigDecimal(100)).divide(new BigDecimal(360));
                }
            } else {
                liluNo = actDinrat;
                try {
                    Actcir cir = mapper.qryCirByIrtkd(wkCurcde, liluNo.substring(0, 1), liluNo.substring(1, 3));
                    if (cir != null) {
                        cirCurirt = cir.getCurirt();
                        cirIrtval = cir.getIrtval();
                        actDratsf = cirIrtval;
                        wkDratsf = cirCurirt;
                    } else {
                        wkDratsf = new BigDecimal(0);
                        throw new RuntimeException("ACTCIR IS NULL!!!  CURCDE : " + wkCurcde);
                    }
                } catch (Exception e) {
                    wkDratsf = new BigDecimal(0);
                    throw new RuntimeException("SELECT FROM ACTCIR ERROR!!! CURCDE : " + wkCurcde);
                }
            }
        }
    }

    //  9951-IFA-PROC-RTN
    private void ifaProcRtn() {
        wkSetseq = 0;
        tmpSetseq = 0;
        wkEryvchTot = 0;
        rtnEryCode = 0;
        //rpVLine = 6;
        ovchCnt = 0;
        // TODO
        oobfCnt = 0;
        // TODO
        int i = 0;
         for(i = 0 ; i < oobfTable.length;i++) {
            oobfTable[i] = new Obf();
        }
        for(i = 0 ; i < ovchTable.length;i++) {
            ovchTable[i] = new Vch();
        }
        if (meryCatcde != null && !meryCatcde.equals(oeryCatcde)) {
            oeryCatcde = meryCatcde;
            //   9950-ERY-TO-OERY-RTN
            eryToOeryRtn();
        }
        while (wkSetseq < oeryCnt && rtnEryCode == 0) {
            //  99511-READ-MERY-RTN
            readMeryRtn();
        }
        if (rtnEryCode == 0 && wkEryvchTot != 0) {
            //  MOVE     'VCH TOTAL <> 0'    TO   OVCH-FURINF(WK-SETSEQ)
            ovchTable[wkSetseq].furinf = "VCH TOTAL <> 0";
            rtnEryCode = 95;
            return;
        }
        if (rtnEryCode == 0) {
            //   99512-WRITE-REC-RTN
            writeRecRtn();
        }
    }

    //   99512-WRITE-REC-RTN
    private void writeRecRtn() {
        oobfCnt = 0;
        // WRT-OBF
        while (oobfCnt <= oobfMaxCnt) {
            wrtObf();
        }
        ovchCnt = 0;
        // WRT-VCH
        while (ovchCnt <= ovchMaxCnt) {
            wrtVch();
        }
    }

    //  WRT-VCH
    private void wrtVch() {
        ovchCnt++;
        if (ovchCnt > ovchMaxCnt || "".equals(ovchTable[ovchCnt].apcode)) {
            ovchCnt = 99;
            return;
        }
        vch = ovchTable[ovchCnt];
        acrInsertVch();
        apcApcode = ovchTable[ovchCnt].apcode;
        //  99519-READ-APC-RTN
        readApcRtn();
    }

    //  99519-READ-APC-RTN
    private void readApcRtn() {
        oapcCnt = 1;
        while (oapcCnt <= oapcMaxCnt && !"".equals(oapcTable[oapcCnt].oapcApcode.trim())) {
            //  99520-READ-OAPC-RTN
            readOapcRtn();
        }
        if (oapcCnt == 99) return;
        try {
            apcApctyp = mapper.qryApctypByCde(apcApcode);
        } catch (Exception e) {
            throw new RuntimeException("READ ACTAPC ERROR  APCODE :" + apcApcode);
        }
        if (oapcCnt > oapcMaxCnt) {
            oapcCnt = oapcMaxCnt;
        }
        oapcTable[oapcCnt].oapcApcode = apcApcode;
        oapcTable[oapcCnt].oapcApctyp = apcApctyp;
    }

    //  99520-READ-OAPC-RTN
    private void readOapcRtn() {
        if (apcApcode != null && apcApcode.equals(oapcTable[oapcCnt].oapcApcode)) {
            apcApctyp = oapcTable[oapcCnt].oapcApctyp;
            oapcCnt = 99;
        } else {
            oapcCnt++;
        }
    }

    private void acrInsertVch() {
        try {
            vch.valdat = parseDate10ToDate8(vch.valdat);
            vch.erydat = parseDate10ToDate8(vch.erydat);
            /*logger.info("INSERT INTO ACTVCH(ORGIDT, TLRNUM, VCHSET, SETSEQ, ORGID2, PRDCDE, CRNYER, PRDSEQ," +
            "  FXEFLG, ORGID3, CUSIDT, APCODE, CURCDE, TXNAMT, VALDAT, RVSLBL," +
            " ANACDE, FURINF, FXRATE, SECCCY, SECAMT, CORAPC, VCHATT, THRREF," +
            "  VCHAUT, VCHANO, DEPNUM, TXNBAK, ACTBAK, CLRBAK, ORGID4, ERYTYP," +
            " ERYDAT, ERYTIM, RECSTS)" +
            " VALUES( '"+vch.orgidt+"','"+vch.tlrnum+"',"+vch.vchset+","+vch.setseq+",'"+vch.orgid2
            +"','"+vch.prdcde+"','"+vch.crnyer+"',"+vch.prdseq+",'"+vch.fxeflg+"','"+vch.orgid3+"','"+vch.cusidt
            +"','"+vch.apcode+"','"+vch.curcde+"',"+vch.txnamt+",'"+vch.valdat+"','"+vch.rvslbl+"','"+vch.anacde
            +"','"+vch.furinf+"',"+vch.fxrate.doubleValue()+",'"+vch.secccy+"',"+vch.secamt+",'"+vch.corapc+"',"+vch.vchatt
            +",'"+vch.thrref+"','"+vch.vchaut+"','"+vch.vchano+"','"+vch.depnum+"','"+vch.txnbak+"','"+vch.actbak
            +"','"+vch.clrbak+"','"+vch.orgid4+"','"+vch.erytyp+"','"+vch.erydat+"','"+vch.erytim+"','"+vch.recsts+"')");
            logger.info("fxrate = "+vch.fxrate.toString());
            logger.info("depnum = " +vch.depnum);*/
            mapper.insertActvch(vch.orgidt, vch.tlrnum, vch.vchset, vch.setseq, vch.orgid2, vch.prdcde, vch.crnyer,
                    vch.prdseq, vch.fxeflg, vch.orgid3, vch.cusidt, vch.apcode, vch.curcde, vch.txnamt, vch.valdat, vch.rvslbl,
                    vch.anacde, vch.furinf, vch.fxrate, vch.secccy, vch.secamt, vch.corapc, vch.vchatt, vch.thrref,
                    vch.vchaut, vch.vchano, nullToEmptystr(vch.depnum), vch.txnbak, vch.actbak, vch.clrbak, vch.orgid4, vch.erytyp,
                    vch.erydat, vch.erytim, vch.recsts);
        } catch (ParseException pe) {
           throw new RuntimeException(" DATE PARSE ERROR OCCURS WNEN INSERT VCH !    " + vch.orgidt + vch.tlrnum + vch.vchset + vch.setseq);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(" INSERT VCH ERROR!    " + vch.orgidt + vch.tlrnum + vch.vchset + vch.setseq);
        }

    }

    private String nullToEmptystr(String str){
        return (str== null)?"":str;
    }
    private String parseDate10ToDate8(String date10) throws ParseException {
        String date8 = null;
         if(date10 != null && date10.contains("-") && date10.length() == 10) {
            date8 = sdfdate8.format(sdfdate10.parse(date10));
         }
        return date8;
    }

    //  WRT-OBF
    private void wrtObf() {
        oobfCnt++;
        if (oobfCnt > oobfMaxCnt || "".equals(oobfTable[oobfCnt].apcode.trim())) {
            oobfCnt = 99;
            return;
        }
        obf = oobfTable[oobfCnt];
        acrUpdateObf();
    }

    private void acrUpdateObf() {
        try {
            mapper.updateObf(obf.bokbal, obf.avabal, obf.difbal, obf.cifbal, obf.actsts, obf.frzsts, obf.regsts, obf.recsts,
                    obf.ovelim, obf.oveexp, obf.cqeflg, obf.glcbal, obf.sysidt, obf.orgidt, obf.cusidt, obf.apcode, obf.curcde);
        } catch (Exception e) {
            throw new RuntimeException(" UPDATE OBF ERROR! " + obf.obfkey);
        }
    }

    //  99511-READ-MERY-RTN
    private void readMeryRtn() {
        wkSetseq++;
        tmpSetseq++;
        eryEryseq = oeryTable[wkSetseq].oeryEryseq;
        eryOrgidt = oeryTable[wkSetseq].oeryOrgidt;
        eryCusidt = oeryTable[wkSetseq].oeryCusidt;
        eryApcode = oeryTable[wkSetseq].oeryApcode;
        eryCurcde = oeryTable[wkSetseq].oeryCurcde;
        eryEryamt = oeryTable[wkSetseq].oeryEryamt;
        eryCdrflg = oeryTable[wkSetseq].oeryCdrflg;
        eryCorcur = oeryTable[wkSetseq].oeryCorcur;
        eryCorapc = oeryTable[wkSetseq].oeryCorapc;
        // 9905-ERY-IFA-RTN
        eryIfaRtn();
        apcApcode = vch.apcode;
        try {
            Long.parseLong(vch.orgidt);
        } catch (Exception e) {
            logger.error("Actery error orgidt :  " + vch.orgidt);
            rtnEryCode = 97;
            return;
        }
        try {
            Long.parseLong(vch.cusidt);
        } catch (Exception e) {
            logger.error("Actery error cusidt :  " + vch.cusidt);
            rtnEryCode = 97;
            return;
        }
        try {
            Long.parseLong(vch.apcode);
        } catch (Exception e) {
            logger.error("Actery error apcode :  " + vch.apcode);
            rtnEryCode = 97;
            return;
        }
        try {
            Long.parseLong(vch.curcde);
        } catch (Exception e) {
            logger.error("Actery error curcde :  " + vch.curcde);
            rtnEryCode = 97;
            return;
        }
        if (vch.txnamt == 0) {
            tmpSetseq--;
            return;
        }


        obf.sysidt = CNST_M_SYSIDT;
        obf.orgidt = vch.orgid3;
        obf.cusidt = vch.cusidt;
        obf.apcode = vch.apcode;
        obf.curcde = vch.curcde;
        obf.obfkey = obf.sysidt+obf.orgidt+obf.cusidt+obf.apcode+obf.curcde;
        //  99516-READ-OBF-RTN
        readObfRtn();
        if ("*".equals(vch.rvslbl)) {
            oobfTable[oobfCnt].avabal = oobfTable[oobfCnt].avabal - vch.txnamt;
            oobfTable[oobfCnt].bokbal = oobfTable[oobfCnt].bokbal - vch.txnamt;
        } else {
            oobfTable[oobfCnt].avabal = oobfTable[oobfCnt].avabal + vch.txnamt;
            oobfTable[oobfCnt].bokbal = oobfTable[oobfCnt].bokbal + vch.txnamt;
        }
        obf.avabal = oobfTable[oobfCnt].avabal;
        obf.ovelim = oobfTable[oobfCnt].ovelim;
        obf.glcbal = oobfTable[oobfCnt].glcbal;
        CNST_M_CHK_BAL = obf.glcbal;
        if (
                (
                CNST_M_CRBAL_FLAG.equals(CNST_M_CHK_BAL) && (obf.avabal < (-1) * obf.ovelim)
                && (!"*".equals(vch.rvslbl) && vch.txnamt < 0 || "*".equals(vch.rvslbl) && vch.txnamt > 0)
                )
                ||
                (
                        CNST_M_DRBAL_FLAG.equals(CNST_M_CHK_BAL) && (obf.avabal > obf.ovelim)
                        && (!"*".equals(vch.rvslbl) && vch.txnamt > 0 || "*".equals(vch.rvslbl) && vch.txnamt < 0)
                )
         ) {
            rtnEryCode = wkSetseq;
            vch.furinf = "BALANCE OVER";
        }
        ovchTable[tmpSetseq] = vch;
        wkEryvchTot += vch.txnamt;
    }

    //  99516-READ-OBF-RTN
    private void readObfRtn() {
        oobfCnt = 1;
        while (!"NUL".equals(oobfTable[oobfCnt].orgidt) &&
                !oobfTable[oobfCnt].obfkey.equals(obf.obfkey)) {
            // 99517-READ-OOBF-RTN
            readOobfRtn();
        }
        if (!"NUL".equals(oobfTable[oobfCnt].orgidt)) {
            return;
        }
        //    CALL   'ACR-READ-OBF'   USING   OBF  SQL-ERR-CODE
        Actobf actobf = mapper.qryUniqueObf(obf.sysidt, obf.orgidt, obf.cusidt, obf.apcode, obf.curcde);
        if (actobf != null) {
            obf.bokbal = actobf.getBokbal();
            obf.avabal = actobf.getAvabal();
            obf.difbal = actobf.getDifbal();
            obf.cifbal = actobf.getCifbal();
            obf.actsts = actobf.getActsts();
            obf.frzsts = actobf.getFrzsts();
            obf.regsts = actobf.getRegsts();
            obf.recsts = actobf.getRecsts();
            obf.ovelim = actobf.getOvelim();
            obf.oveexp = actobf.getOveexp();
            obf.cqeflg = actobf.getCqeflg();
            obf.glcbal = actobf.getGlcbal();
        }
        if (actobf == null || (!ACEnum.RECSTS_VALID.getStatus().equals(obf.recsts)
                || !ACEnum.RECSTS_VALID.getStatus().equals(obf.actsts))) {
            //   PERFORM  AUTO-CREATE-PROC    THRU  AUTO-CREATE-EXIT
            autoCreateProc();
        }
        oobfTable[oobfCnt] = obf;
    }

    //    PERFORM  AUTO-CREATE-PROC    THRU  AUTO-CREATE-EXIT
    private void autoCreateProc() {
        //  READ-APCODE
        readApcode();
        if ("0".equals(glcGlcopn)) {
            rtnEryCode = 96;
            logger.info("NOT FOUND OBF RECORD,CAN NOT AUTO CREATE,OBF-KEY: " + obf.obfkey);
        }
        if ((!ACEnum.RECSTS_VALID.getStatus().equals(obf.recsts)
                || !ACEnum.RECSTS_VALID.getStatus().equals(obf.actsts))
                && obf.bokbal == 0 && obf.avabal == 0) {
            //  CALL 'ACR-DELETE-OBF'
            mapper.deleteObf(obf.sysidt, obf.orgidt, obf.cusidt, obf.apcode, obf.curcde);
        }
        obf = new Obf();
        obf.sysidt = CNST_M_SYSIDT;
        obf.orgidt = vch.orgid3;
        obf.cusidt = vch.cusidt;
        obf.apcode = vch.apcode;
        obf.curcde = vch.curcde;
        obf.obfkey = obf.sysidt+obf.orgidt+obf.cusidt+obf.apcode+obf.curcde;
        obf.avabal = 0L;
        obf.bokbal = 0L;
        obf.ovelim = 0L;
        obf.actsts = " ";
        obf.frzsts = "0";
        obf.glcbal = glcGlcbal;
        obf.regsts = "0";
        obf.recsts = " ";
        wkVchdat = vch.erydat;
        wkEffdat = wkVchdat;
        oac = new Actoac();
        oac.setOacflg(CNST_M_OACFLG_OPEN);
        oac.setOrgidt(vch.orgidt);
        //oac-actnum = oac-cusidt+oac-apcode+oac-curcde;
        oac.setCusidt(vch.cusidt);
        oac.setApcode(vch.apcode);
        oac.setCurcde(vch.curcde);
        oac.setActnam(autoTlrnum);
        oac.setDinrat("000");
        oac.setCinrat("000");
        oac.setDratsf(new BigDecimal(0.0));
        oac.setCratsf(new BigDecimal(0.0));
        oac.setIntflg("0");
        oac.setIntcyc(" ");
        oac.setInttra(" ");
        if (oac.getCusidt().compareTo("9000000") < 0) {
            oac.setStmcyc("M");
            oac.setStmcdt("0031");
            oac.setStmfmt("C");
        } else {
            oac.setStmcyc(" ");
            oac.setStmcdt("    ");
            oac.setStmfmt("O");
        }
        oac.setStmadd(" ");
        oac.setStmzip(" ");
        oac.setStmsht((short) 1);
        oac.setStmdep(" ");
        oac.setLegcyc("M");
        oac.setLegcdt("0031");
        if ("9301".equals(oac.getApcode())) {
            oac.setLegfmt(CNST_M_LEGFMT_F);
        } else {
            oac.setLegfmt(CNST_M_LEGFMT_I);
        }
        oac.setLegadd(" ");
        oac.setLegzip(" ");
        oac.setLegsht((short) 1);
        oac.setLegdep(" ");

        if ("0".equals(apcApctyp)) {
            oac.setActtyp("3");
        } else if ("2".equals(apcApctyp)) {
            oac.setActtyp("6");
        } else if ("5".equals(apcApctyp) || "6".equals(apcApctyp)) {
            oac.setActtyp("5");
        } else if ("8".equals(apcApctyp) || "9".equals(apcApctyp)) {
            oac.setActtyp("7");
        } else {
            oac.setActtyp("9");
        }
        oac.setDepnum(actDepnum);
        try {
            oac.setOpndat(sdfdate10.parse(wkEffdat));
            oac.setClsdat(sdfdate10.parse("1111-11-11"));
            oac.setOveexp(sdfdate10.parse(wkEffdat));
            oac.setCredat(sdfdate10.parse(wkEffdat));
        } catch (ParseException e) {
            throw new RuntimeException(" 字符串无法转化为时间类型.  " + e);
        }
        oac.setCqeflg(" ");
        oac.setBallim(0L);
        oac.setOvelim(0L);
        oac.setCretlr(autoTlrnum);
        oac.setRecsts(ACEnum.RECSTS_VALID.getStatus());
        if ("7451".equals(oac.getApcode()) || "7452".equals(oac.getApcode())) {
            oac.setActnam(actActnam);
            oac.setDinrat("HAA");
            oac.setCinrat("   ");
            oac.setInttra("8" + oac.getOrgidt() + oac.getCusidt() + "0790" + oac.getCurcde());
            oac.setStmdep(oac.getOrgidt() + oac.getDepnum());
            oac.setLegdep(oac.getStmdep());
            oac.setIntflg("4");
            oac.setIntcyc("M");
        }
        if ("1641".equals(oac.getApcode()) || "1642".equals(oac.getApcode())) {
            oac.setDinrat("JAA");
            oac.setCinrat("JAA");
            oac.setIntflg("4");
            oac.setIntcyc("S");
        }
        List<String> oacRecsts = mapper.qryOacRecsts(oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
        if (oacRecsts != null && !oacRecsts.isEmpty()) {
            mapper.deleteOac(oac.getOrgidt(), oac.getCusidt(), oac.getApcode(), oac.getCurcde());
        }
        try {
            oacMapper.insertSelective(oac);
        } catch (Exception e) {
            throw new RuntimeException("AUTO CREATE ACTOAC-REC ERROR !");
        }
        // CALL   'ACR-INSERT-OBF'
        try {
            acrInsertObf();
        } catch (Exception e) {
            throw new RuntimeException("AUTO CREATE ACFOBF-REC ERROR ! obfkey: " + obf.obfkey);
        }
    }

    //  CALL   'ACR-INSERT-OBF'
    private void acrInsertObf() {
        mapper.insertObf(obf.sysidt, obf.orgidt, obf.cusidt, obf.apcode, obf.curcde, obf.bokbal,
                obf.avabal, obf.difbal, obf.cifbal, obf.actsts, obf.frzsts, obf.regsts, obf.recsts,
                obf.ovelim, obf.oveexp, obf.cqeflg, obf.glcbal);
    }

    //  READ-APCODE
    private void readApcode() {
        ApcGlc apcglc = mapper.qryApcGlc(apcApcode);
        if (apcglc != null) {
            glcGlcbal = apcglc.getGlcbal();
            glcGlcopn = apcglc.getGlcopn();
            apcApctyp = apcglc.getApctyp();
        } else {
            throw new RuntimeException("READ APCODE,GLCODE ERROR ! apcode=" + apcApcode);
        }
        CNST_M_CHK_BAL = glcGlcbal;
    }

    //    99517-READ-OOBF-RTN
    private void readOobfRtn() {
        if (oobfCnt > oobfMaxCnt) {
            throw new RuntimeException("99517     ->   OOBF-CNT OVER   STOP RUN!");
        } else if ("".equals(oobfTable[oobfCnt].obfkey.trim())) {
            oobfTable[oobfCnt].orgidt = "NUL";
        } else {
            oobfCnt++;
        }
    }

    //  9905-ERY-IFA-RTN
    private void eryIfaRtn() {
        vch = new Vch();
        vch.orgidt = meryTxnorg;
        vch.tlrnum = meryTlrnum;
        vch.vchset = meryVchset;
        vch.setseq = (short) tmpSetseq;
        vch.orgid2 = meryActorg;
        vch.orgid3 = meryActorg;
        vch.orgid4 = meryClrorg;
        vch.prdcde = meryPrdcde;
        vch.crnyer = Short.parseShort(meryCrnyer);
        vch.prdseq = meryPrdseq;
        vch.filler = "";
        vch.fxeflg = "0";
        vch.valdat = meryValdat;
        vch.rvslbl = meryRvslbl;
        vch.anacde = meryAnacde;
        vch.furinf = meryFurinf;
        vch.vchatt = meryVchatt;
        vch.thrref = meryThrref;
        vch.vchaut = meryVchaut;
        vch.vchano = meryVchano;
        vch.erydat = meryErydat;
        vch.erytim = meryErytim;
        vch.erytyp = CNST_VCH_ERYTYP_AUTO;
        vch.recsts = CNST_M_RECSTS_NORMAL;
        CNST_M_ORGIDT = eryOrgidt;
        if (CNST_M_ORGIDT_TXN.equals(CNST_M_ORGIDT)) {
            vch.orgid3 = meryTxnorg;
        } else if (CNST_M_ORGIDT_ACT.equals(CNST_M_ORGIDT)) {
            vch.orgid3 = meryActorg;
        } else if (CNST_M_ORGIDT_CLR.equals(CNST_M_ORGIDT)) {
            vch.orgid3 = meryClrorg;
        } else if (CNST_M_ORGIDT_SUP.equals(CNST_M_ORGIDT)) {
            vch.orgid3 = merySuporg;
        } else {
            vch.orgid3 = eryOrgidt;
        }
        CNST_M_CUSIDT = eryCusidt;
        CNST_M_CUSIDT_ITG = eryCusidt;
        CNST_M_CUSIDT_IBK = eryCusidt;

        CNST_M_CUSIDT_FST = CNST_M_CUSIDT_ITG.substring(0, 1);
        CNST_M_CUSIDT_ORG = CNST_M_CUSIDT_ITG.substring(1, 4);
        CNST_M_CUSIDT_DEP = CNST_M_CUSIDT_ITG.substring(4, 6);
        CNST_M_CUSIDT_CHK = CNST_M_CUSIDT_ITG.substring(6, 7);
        CNST_M_CUSIDT_HED = CNST_M_CUSIDT_IBK.substring(0,2);
        CNST_M_CUSIDT_BNK = CNST_M_CUSIDT_IBK.substring(2, 7);
        if (CNST_M_CUSIDT_CUSIDT1.equals(CNST_M_CUSIDT)) {
            vch.cusidt = meryCusid1;
        } else if (CNST_M_CUSIDT_CUSIDT2.equals(CNST_M_CUSIDT)) {
            vch.cusidt = meryCusid2;
        } else if (CNST_M_CUSIDT_CUSIDT3.equals(CNST_M_CUSIDT)) {
            vch.cusidt = meryCusid3;
        } else if (CNST_M_CUSIDT_CUSIDT4.equals(CNST_M_CUSIDT)) {
            vch.cusidt = meryCusid4;
        } else if (CNST_M_CUSIDT_CUSIDT5.equals(CNST_M_CUSIDT)) {
            vch.cusidt = meryCusid5;
        } else if (CNST_M_CUSIDT_CUSIDT6.equals(CNST_M_CUSIDT)) {
            vch.cusidt = meryCusid6;
        } else if (CNST_M_CUSIDT_ORG_TXN.equals(CNST_M_CUSIDT_ORG)) {
            CNST_M_CUSIDT_ORG = meryTxnorg;
            CNST_M_CUSIDT_CHK = "0";
            CNST_M_CUSIDT_ITG = CNST_M_CUSIDT_FST+CNST_M_CUSIDT_ORG+CNST_M_CUSIDT_DEP+CNST_M_CUSIDT_CHK;
            vch.cusidt = CNST_M_CUSIDT_ITG;
        } else if (CNST_M_CUSIDT_ORG_ACT.equals(CNST_M_CUSIDT_ORG)) {
            CNST_M_CUSIDT_ORG = meryActorg;
            CNST_M_CUSIDT_CHK = "0";
            CNST_M_CUSIDT_ITG = CNST_M_CUSIDT_FST+CNST_M_CUSIDT_ORG+CNST_M_CUSIDT_DEP+CNST_M_CUSIDT_CHK;
            vch.cusidt = CNST_M_CUSIDT_ITG;
        } else if (CNST_M_CUSIDT_ORG_CLR.equals(CNST_M_CUSIDT_ORG)) {
            CNST_M_CUSIDT_ORG = meryClrorg;
            CNST_M_CUSIDT_CHK = "0";
            CNST_M_CUSIDT_ITG = CNST_M_CUSIDT_FST+CNST_M_CUSIDT_ORG+CNST_M_CUSIDT_DEP+CNST_M_CUSIDT_CHK;
            vch.cusidt = CNST_M_CUSIDT_ITG;
        } else if (CNST_M_CUSIDT_ORG_SUP.equals(CNST_M_CUSIDT_ORG)) {
            CNST_M_CUSIDT_ORG = merySuporg;
            CNST_M_CUSIDT_CHK = "0";
            CNST_M_CUSIDT_ITG = CNST_M_CUSIDT_FST+CNST_M_CUSIDT_ORG+CNST_M_CUSIDT_DEP+CNST_M_CUSIDT_CHK;
            vch.cusidt = CNST_M_CUSIDT_ITG;
        } else if (CNST_M_CUSIDT_BNK_ACT.equals(CNST_M_CUSIDT_BNK)) {
            CNST_M_CUSIDT_BNK = meryCusid5;
            CNST_M_CUSIDT_IBK = CNST_M_CUSIDT_HED + CNST_M_CUSIDT_BNK;
            vch.cusidt = CNST_M_CUSIDT_IBK;
            CNST_M_CUSIDT_ITG = vch.cusidt;
        } else if (CNST_M_CUSIDT_BNK_SUP.equals(CNST_M_CUSIDT_BNK)) {
            CNST_M_CUSIDT_BNK = meryCusid6;
            CNST_M_CUSIDT_IBK = CNST_M_CUSIDT_HED + CNST_M_CUSIDT_BNK;
            vch.cusidt = CNST_M_CUSIDT_IBK;
        } else {
            vch.cusidt = eryCusidt;
        }

        CNST_M_CUSIDT_ITG = vch.cusidt;
        CNST_M_CUSIDT_FST = CNST_M_CUSIDT_ITG.substring(0, 1);
        CNST_M_CUSIDT_ORG = CNST_M_CUSIDT_ITG.substring(1, 4);
        CNST_M_CUSIDT_DEP = CNST_M_CUSIDT_ITG.substring(4, 6);
        CNST_M_CUSIDT_CHK = CNST_M_CUSIDT_ITG.substring(6, 7);
        if ("9".equals(CNST_M_CUSIDT_FST)) {
            vch.depnum = CNST_M_CUSIDT_DEP;
        } else {
            vch.depnum = meryDepid1;
        }
        CNST_M_APCODE = eryApcode;
        if (CNST_M_APCODE_APC1.equals(CNST_M_APCODE)) {
            vch.apcode = meryApcde1;
        } else if (CNST_M_APCODE_APC2.equals(CNST_M_APCODE)) {
            vch.apcode = meryApcde2;
        } else if (CNST_M_APCODE_APC3.equals(CNST_M_APCODE)) {
            vch.apcode = meryApcde3;
        } else if (CNST_M_APCODE_APC4.equals(CNST_M_APCODE)) {
            vch.apcode = meryApcde4;
        } else if (CNST_M_APCODE_APC5.equals(CNST_M_APCODE)) {
            vch.apcode = meryApcde5;
        } else if (CNST_M_APCODE_APC6.equals(CNST_M_APCODE)) {
            vch.apcode = meryApcde6;
        } else {
            vch.apcode = eryApcode;
        }
        CNST_M_CURCDE = eryCurcde;
        if (CNST_M_CURCDE_CC1.equals(CNST_M_CURCDE)) {
            vch.curcde = meryCurcd1;
        } else if (CNST_M_CURCDE_CC2.equals(CNST_M_CURCDE)) {
            vch.curcde = meryCurcd2;
        } else if (CNST_M_CURCDE_CC3.equals(CNST_M_CURCDE)) {
            vch.curcde = meryCurcd3;
        } else if (CNST_M_CURCDE_CC4.equals(CNST_M_CURCDE)) {
            vch.curcde = meryCurcd4;
        } else if (CNST_M_CURCDE_CC5.equals(CNST_M_CURCDE)) {
            vch.curcde = meryCurcd5;
        } else if (CNST_M_CURCDE_CC6.equals(CNST_M_CURCDE)) {
            vch.curcde = meryCurcd6;
        } else {
            vch.curcde = eryCurcde;
        }
        CNST_M_ERYAMT = eryEryamt;
        if (CNST_M_ERYAMT_11.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTxamt1.longValue();
        } else if (CNST_M_ERYAMT_12.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTxamt2.longValue();
        } else if (CNST_M_ERYAMT_13.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTxamt3.longValue();
        } else if (CNST_M_ERYAMT_14.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTxamt4.longValue();
        } else if (CNST_M_ERYAMT_15.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTxamt5.longValue();
        } else if (CNST_M_ERYAMT_16.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTxamt6.longValue();
        } else if (CNST_M_ERYAMT_21.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActbl1;
        } else if (CNST_M_ERYAMT_22.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActbl2;
        } else if (CNST_M_ERYAMT_23.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActbl3;
        } else if (CNST_M_ERYAMT_24.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActbl4;
        } else if (CNST_M_ERYAMT_25.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActbl5;
        } else if (CNST_M_ERYAMT_26.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActbl6;
        } else if (CNST_M_ERYAMT_31.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActoc1;
        } else if (CNST_M_ERYAMT_32.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActoc2;
        } else if (CNST_M_ERYAMT_33.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActoc3;
        } else if (CNST_M_ERYAMT_34.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActoc4;
        } else if (CNST_M_ERYAMT_35.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActoc5;
        } else if (CNST_M_ERYAMT_36.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryActoc6;
        } else if (CNST_M_ERYAMT_41.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryComam1;
        } else if (CNST_M_ERYAMT_42.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryComam2;
        } else if (CNST_M_ERYAMT_43.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryComam3;
        } else if (CNST_M_ERYAMT_44.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryComam4;
        } else if (CNST_M_ERYAMT_45.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryComam5;
        } else if (CNST_M_ERYAMT_46.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryComam6;
        } else if (CNST_M_ERYAMT_51.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryIntam1;
        } else if (CNST_M_ERYAMT_52.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryIntam2;
        } else if (CNST_M_ERYAMT_53.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryIntam3;
        } else if (CNST_M_ERYAMT_54.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryIntam4;
        } else if (CNST_M_ERYAMT_55.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryIntam5;
        } else if (CNST_M_ERYAMT_56.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryIntam6;
        } else if (CNST_M_ERYAMT_61.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTaxam1;
        } else if (CNST_M_ERYAMT_62.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTaxam2;
        } else if (CNST_M_ERYAMT_63.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTaxam3;
        } else if (CNST_M_ERYAMT_64.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTaxam4;
        } else if (CNST_M_ERYAMT_65.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTaxam5;
        } else if (CNST_M_ERYAMT_66.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryTaxam6;
        } else if (CNST_M_ERYAMT_71.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryFeeam1;
        } else if (CNST_M_ERYAMT_72.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryFeeam2;
        } else if (CNST_M_ERYAMT_73.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryFeeam3;
        } else if (CNST_M_ERYAMT_74.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryFeeam4;
        } else if (CNST_M_ERYAMT_75.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryFeeam5;
        } else if (CNST_M_ERYAMT_76.equals(CNST_M_ERYAMT)) {
            vch.txnamt = meryFeeam6;
        } else {
            vch.txnamt = 0L;
        }
        if(vch.apcode.startsWith("2") && eryCdrflg < 0) {
            logger.info("vch.apcode(2227)  : "+vch.apcode+"   eryCdrflg : "+eryCdrflg);
        }
        if (eryCdrflg < 0) {
            vch.txnamt = 0 - vch.txnamt;
        }
        if (eryCdrflg == 9 || eryCdrflg == -9) {
            vch.rvslbl = ACEnum.RVSLBL_TRUE.getStatus();
        }
        CNST_M_APCODE = eryCorapc;
        if (CNST_M_APCODE_APC1.equals(CNST_M_APCODE)) {
            vch.corapc = meryApcde1;
        } else if (CNST_M_APCODE_APC2.equals(CNST_M_APCODE)) {
            vch.corapc = meryApcde2;
        } else if (CNST_M_APCODE_APC3.equals(CNST_M_APCODE)) {
            vch.corapc = meryApcde3;
        } else if (CNST_M_APCODE_APC4.equals(CNST_M_APCODE)) {
            vch.corapc = meryApcde4;
        } else if (CNST_M_APCODE_APC5.equals(CNST_M_APCODE)) {
            vch.corapc = meryApcde5;
        } else if (CNST_M_APCODE_APC6.equals(CNST_M_APCODE)) {
            vch.corapc = meryApcde6;
        } else if (CNST_M_APCODE_CAPC.equals(CNST_M_APCODE)) {
            vch.corapc = meryCorapc;
        } else {
            vch.corapc = eryCorapc;
        }
        CNST_M_FXFLAG = meryFxflag;
        if (CNST_M_FXFLAG_1.equals(CNST_M_FXFLAG)) {
            CNST_M_CURCDE = eryCorcur;
            if (CNST_M_CURCDE_CC1.equals(CNST_M_CURCDE)) {
                vch.secccy = meryCurcd1;
            } else if (CNST_M_CURCDE_CC2.equals(CNST_M_CURCDE)) {
                vch.secccy = meryCurcd2;
            } else if (CNST_M_CURCDE_CC3.equals(CNST_M_CURCDE)) {
                vch.secccy = meryCurcd3;
            } else if (CNST_M_CURCDE_CC4.equals(CNST_M_CURCDE)) {
                vch.secccy = meryCurcd4;
            } else if (CNST_M_CURCDE_CC5.equals(CNST_M_CURCDE)) {
                vch.secccy = meryCurcd5;
            } else if (CNST_M_CURCDE_CC6.equals(CNST_M_CURCDE)) {
                vch.secccy = meryCurcd6;
            } else {
                vch.secccy = eryCorcur;
            }
        }
        CNST_M_FXFLAG = meryFxflag;
        if (CNST_M_FXFLAG_1.equals(CNST_M_FXFLAG)) {
            if (CNST_WK_RMB.equals(vch.secccy)) {
                if (vch.curcde != null && vch.curcde.equals(meryFstccy)) {
                    vch.fxrate = new BigDecimal(meryFxrat1);
                } else {
                    if (vch.curcde != null && vch.curcde.equals(merySecccy)) {
                        vch.fxrate = new BigDecimal(meryFxrat2);
                    }
                }
                vch.secamt = meryImmamt;
            } else if (vch.secccy != null && vch.secccy.equals(meryFstccy)) {
                vch.fxrate = new BigDecimal(meryFxrat1);
                vch.secamt = meryFstamt;
            } else if (vch.secccy != null && vch.secccy.equals(merySecccy)) {
                vch.fxrate = new BigDecimal(meryFxrat2);
                vch.secamt = merySecamt;
            }
        }
        if (eryCdrflg < 0) {
            vch.secamt = 0 - vch.secamt;
        }
    }

    //   9950-ERY-TO-OERY-RTN
    private void eryToOeryRtn() {
        oeryCnt = 0;
        eryPrdcde = meryPrdcde;
        eryEvtcde = meryEvtcde;
        eryCtpcde = meryCtpcde;
        eryCatcde = meryCatcde;
        List<Actery> eryList = mapper.qryErys(eryPrdcde, eryEvtcde, eryCtpcde, eryCatcde);
        if (eryList != null && !eryList.isEmpty()) {
            for (Actery actery : eryList) {
                // 99501-READ-ERY-RTN   未分离
                eryEryseq = actery.getEryseq();
                eryOrgidt = actery.getOrgidt();
                eryCusidt = actery.getCusidt();
                eryApcode = actery.getApcode();
                eryCurcde = actery.getCurcde();
                eryEryamt = actery.getEryamt();
                eryCdrflg = actery.getCdrflg();
                eryTotnum = actery.getTotnum();
                eryCorcur = actery.getCorcur();
                eryCorapc = actery.getCorapc();
                oeryCnt++;
                oeryTable[oeryCnt].oeryEryseq = eryEryseq;
                oeryTable[oeryCnt].oeryOrgidt = eryOrgidt;
                oeryTable[oeryCnt].oeryCusidt = eryCusidt;
                oeryTable[oeryCnt].oeryApcode = eryApcode;
                oeryTable[oeryCnt].oeryCurcde = eryCurcde;
                oeryTable[oeryCnt].oeryEryamt = eryEryamt;
                oeryTable[oeryCnt].oeryCdrflg = eryCdrflg;
                oeryTable[oeryCnt].oeryTotnum = eryTotnum;
                oeryTable[oeryCnt].oeryCorcur = eryCorcur;
                oeryTable[oeryCnt].oeryCorapc = eryCorapc;
                if (oeryCnt > oeryMaxCnt) {
                    oeryCnt = 0;
                    break;
                }
            }
        }
        if (oeryCnt == 0 || oeryCnt > oeryMaxCnt) {
            throw new RuntimeException("READ ACTERY EMPTY !!!  " + meryPrdcde + meryEvtcde + meryCtpcde + meryCatcde);
        }
    }

    //  99501-READ-ERY-RTN

    //  99515-IFA-ERY-CHK
    private void ifaEryChk() {
        if (rtnEryCode == 0) {
            return;
        }
        logger.info("RTN-ERY-CODE = " + rtnEryCode);
        if (rtnEryCode < 10) {
            logger.info("OBF-BAL OVER   OBFKEY: " + obf.obfkey);
            logger.info("VCHSEQ : " + rtnEryCode);
            return;
        }
        if (rtnEryCode == 97) {
            logger.error("ACTERY OR IFA ERROR  !!! ");
            logger.error("ORGIDT : " + vch.orgid3);
            logger.error("CUSIDT : " + vch.cusidt);
            logger.error("APCODE : " + vch.apcode);
            logger.error("CURCDE: " + vch.curcde);
        } else if (rtnEryCode == 96) {
            logger.error("NOT FOUND OBF RECORD !!!");
            logger.error("CAN NOT AUTO CREATE  ");
            logger.error("OBF-KEY  : " + obf.obfkey);
        } else if (rtnEryCode == 95) {
            logger.error("VCH TOTAL <> 0 !!!      DIFF= " + wkEryvchTot);
            logger.error("OBF-KEY  : " + obf.obfkey);
        }
        throw new RuntimeException("99515系统错误:");
    }

    // 由内部账号计算获取对应的利息支出账号的Oldacn        01090106008521001
    private String nbActToOldacn(String actno) {
        if(actno == null || actno.length() != 17){
            throw new RuntimeException("内部账号为空或长度不等于17！");
        }
        String intActno = actno.substring(0,3)+"9"+actno.substring(0,3)+"600"+
                    apcodeMaps.get(actno.substring(10,14))+actno.substring(14,17);
        String rtnActno =  oldacnMaps.get(intActno);
        if(rtnActno == null) {
            rtnActno = intActno;
        }
        return rtnActno;
    }

    private class OeryT {
        private Short oeryEryseq = 0;
        private String oeryOrgidt = "   ";
        private String oeryCusidt = "       ";
        private String oeryApcode = "    ";
        private String oeryCurcde = "   ";
        private String oeryEryamt = "";
        private Short oeryCdrflg = 1;
        private Short oeryTotnum = 0;
        private String oeryCorcur = "";
        private String oeryCorapc = "";
    }

    private class OccyT {
        private String occyCurcde = "";
        private Integer occyDecpos = 0;
    }

    private class Obf {

        private String obfkey = "";
        private String sysidt = "";
        private String orgidt = "";
        private String cusidt = "";
        private String apcode = "";
        private String curcde = "";
        private Long bokbal = 0L;
        private Long avabal = 0L;
        private Long difbal = 0L;
        private Long cifbal = 0L;
        private String actsts = "";
        private String frzsts = "";
        private String regsts = "";
        private String recsts = "";
        private long ovelim = 0;
        private String oveexp = "";
        private String cqeflg = "";
        private String glcbal = "";
    }

    private class Vch {
        private String vchkey = "";
        private String orgidt = "";
        private String vchnum = "";
        private String tlrnum = "";
        private Integer vchset = 0;
        private Short setseq = 0;
        private String ourref = "";
        private String orgid2 = "";
        private String prdcde = "";
        private short crnyer = 0;
        private int prdseq = 0;
        private String fxeflg = "";
        private String data1 = "";
        private String orgid3 = "";
        private String actnum = "";
        private String cusidt = "       ";
        private String apcode = "";
        private String curcde = "";
        private Long txnamt = 0L;
        private String valdat = "";
        private String rvslbl = "";
        private String anacde = "";
        private String furinf = "";
        private BigDecimal fxrate = new BigDecimal(0);
        private String secccy = "";
        private int secamt = 0;
        private String corapc = "";
        private int vchatt = 0;
        private String thrref = "";
        private String vchaut = "";
        private String vchano = "";
        private String depnum = "";
        private String txnbak = "";
        private String actbak = "";
        private String clrbak = "";
        private String orgid4 = "";
        private String erytyp = "";
        private String erydat = "";
        private String erytim = "";
        private String recsts = " ";
        public String filler = "";
    }

    private class OapcT {
        private String oapcApcode = "";
        private String oapcApctyp = "";
    }

}