package cbs.batch.ac.acbint.dao;

import cbs.batch.ac.acbint.ActAndGlc;
import cbs.batch.ac.acbint.ApcGlc;
import cbs.batch.ac.acbint.SctBean;
import cbs.batch.ac.acbint.TranDebitVoucher;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actblh;
import cbs.repository.account.maininfo.model.Actobf;
import cbs.repository.account.tempinfo.model.Actcir;
import cbs.repository.code.model.*;
import cbs.repository.platform.model.Ptdept;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 利息Mapper
 */
public interface AcbintMapper {

    // 机构
    @Select("select * from ptdept where deptid is not null")
    List<Ptdept> qryAllPtdept();

    @Select("SELECT IPYMAK FROM ACTSCT" +
            "  WHERE SCTNUM = #{sctnum}")
    String qryIpymakByNum(@Param("sctnum") int sctnum);

    @Select("SELECT CRNDAT,IPYDAT,NWKDAY,  (IPYDAT - 1) as ipydat1 , (IPYDAT + 1) as ipydat2 FROM ACTSCT" +
            "  WHERE SCTNUM = #{sctnum}")
    SctBean qrySctByNum(@Param("sctnum") int sctnum);

    // 自动转账
    @Select("SELECT PRDCDE,EVTCDE,CTPCDE,CATCDE,CURRAG,APCODE" +
            " FROM ACTATR WHERE ATRCDE = #{atrcde}")
    Actatr qryAtrByCde(@Param("atrcde") String atrcde);

    @Select("SELECT  ACTACT.SYSIDT,ACTACT.ORGIDT," +
            "                            ACTACT.CUSIDT,ACTACT.APCODE,ACTACT.CURCDE," +
            "                            ACTACT.ACTGLC,ACTGLC.GLCBAL," +
            "                            ACTACT.ACTNAM,ACTACT.AVABAL," +
            "                            ACTACT.DINRAT,ACTACT.CINRAT," +
            "                            ACTACT.INTTRA,ACTACT.DEPNUM," +
            "                            ACTACT.CACINT,ACTACT.DACINT," +
            "                            ACTACT.CRATSF,ACTACT.DRATSF," +
            "                            ACTACT.INTFLG,ACTACT.INTCYC," +
            "                            ACTACT.DRACCM,ACTACT.CRACCM," +
            "                            ACTACT.LINTDT,ACTACT.OPNDAT" +
            "                       FROM ACTACT,ACTGLC" +
            "                      WHERE ( ACTACT.APCODE IN ( " +
            "                              SELECT SUBSTR(TRFNUM,1,4) FROM" +
            "                                ACTTRF WHERE ATRCDE = #{atrcde}" +
            "                                         AND TRFKID = #{trfkid3})" +
            "                           OR ACTACT.ACTGLC IN (" +
            "                              SELECT SUBSTR(TRFNUM,1,4) FROM" +
            "                                ACTTRF WHERE ATRCDE = #{atrcde}" +
            "                                         AND TRFKID = #{trfkid1} )" +
            "                           OR ACTACT.ACTGLC IN ( SELECT GLCODE FROM" +
            "                              ACTGLC WHERE GLCGRP IN ( " +
            "                                SELECT SUBSTR(TRFNUM,1,4) FROM ACTTRF " +
            "                                  WHERE ATRCDE = #{atrcde}" +
            "                                    AND TRFKID = #{trfkid7} ) ) )" +
            "                       AND  ACTACT.CURCDE IN ( SELECT CURCDE FROM" +
            "                              ACTCYT WHERE CYTCDE = #{currag} )" +
            "                       AND  ACTACT.RECSTS =  #{recsts}" +
            "                       AND  ( INTFLG =#{intflg1}   OR INTFLG =#{intflg2} )" +
            "                      AND  ACTACT.ACTGLC = ACTGLC.GLCODE" +
            "                      AND ACTACT.ORGIDT = #{orgidt} "+
            "            ORDER BY ACTACT.CURCDE,ACTACT.CUSIDT," +
            "                     ACTACT.ACTGLC,ACTACT.APCODE,ACTACT.DEPNUM")
    List<ActAndGlc> qryActAndGlcs(@Param("atrcde") String atrcde, @Param("trfkid3") String trfkid3,
                                  @Param("trfkid1") String trfkid1, @Param("trfkid7") String trfkid7,
                                  @Param("currag") String currag, @Param("recsts") String recsts,
                                  @Param("intflg1") String intflg1, @Param("intflg2") String intflg2,
                                  @Param("orgidt") String orgidt);

    @Select("SELECT ACTGLC.GLCOPN,ACTGLC.GLCCAT,ACTGLC.GLCBAL FROM ACTAPC, ACTGLC" +
            "  WHERE ACTAPC.GLCODE = ACTGLC.GLCODE AND ACTAPC.APCODE = #{apcode}")
    Actglc qryGlcByApcode(@Param("apcode") String apcode);

    @Select("SELECT BOKBAL, AVABAL, DIFBAL, CIFBAL, ACTSTS, FRZSTS," +
            " REGSTS, RECSTS, OVELIM, OVEEXP, CQEFLG, GLCBAL  FROM ACTOBF" +
            "  WHERE SYSIDT = #{sysidt} AND ORGIDT = #{orgidt} " +
            "  AND CUSIDT = #{cusidt} AND APCODE = #{apcode} " +
            "  AND CURCDE = #{curcde}")
    Actobf qryUniqueObf(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                        @Param("apcode") String apcode, @Param("curcde") String curcde);

    @Update("UPDATE    ACTPDN  SET       PDNSEQ = #{pdnseq}" +
            " WHERE     ORGIDT = #{orgidt}   AND     PRDCDE = #{prdcde}")
    int updatePdn(@Param("pdnseq") int pdnseq, @Param("orgidt") String orgidt, @Param("prdcde") String prdcde);

    @Update(" UPDATE    ACTTLR  SET   VCHSET = #{vchset}" +
            "   WHERE     ORGIDT = #{orgidt}  AND  TLRNUM = #{tlrnum}")
    int updateTlr(@Param("vchset") int vchset, @Param("orgidt") String orgidt, @Param("tlrnum") String tlrnum);

    @Select("SELECT ORGNAM,SUPBAK FROM ACTORG WHERE ORGIDT = #{orgidt}")
    Actorg qryOrgByOrgidt(@Param("orgidt") String orgidt);

    @Select("SELECT VCHSET FROM   ACTTLR WHERE  ORGIDT = #{orgidt} AND  TLRNUM = #{tlrnum}")
    Integer qryVchset(@Param("orgidt") String orgidt, @Param("tlrnum") String tlrnum);

    @Insert("INSERT INTO ACTTLR ( ORGIDT, TLRNUM,VCHSET, RECSTS )" +
            "  VALUES ( #{orgidt}, #{tlrnum},#{vchset},#{recsts})")
    int insertTlr(@Param("orgidt") String orgidt, @Param("tlrnum") String tlrnum,
                  @Param("vchset") int vchset, @Param("recsts") String recsts);

    @Select("SELECT PDNSEQ FROM   ACTPDN WHERE  ORGIDT = #{orgidt}" +
            "                      AND  PRDCDE = #{prdcde}")
    Integer qryPdnseq(@Param("orgidt") String orgidt, @Param("prdcde") String prdcde);

    @Insert("INSERT INTO ACTPDN ( ORGIDT, PRDCDE,PDNSEQ, PDNYER, RECSTS )" +
            "    VALUES (#{orgidt}, #{prdcde},#{pdnseq},#{year},#{recsts} )")
    int insertPdn(@Param("orgidt") String orgidt, @Param("prdcde") String prdcde,
                  @Param("pdnseq") int pdnseq, @Param("year") String year, @Param("recsts") String recsts);

    @Select("SELECT DECPOS FROM ACTCCY WHERE CURCDE = #{curcde}")
    int qryDecposByCurcde(@Param("curcde") String curcde);

    @Select("SELECT   CURIRT,IRTVAL FROM         ACTCIR" +
            "   WHERE   CURCDE = #{curcde}  AND IRTKD1 = #{lilu1}     AND IRTKD2 = #{lilu2}")
    Actcir qryCirByIrtkd(@Param("curcde") String curcde, @Param("lilu1") String lilu1, @Param("lilu2") String lilu2);

    @Select("SELECT NVL(SUM(DRACCM),0) as DRACCM,NVL(SUM(CRACCM),0) as CRACCM " +
            "  FROM ACTBLH WHERE ORGIDT = #{orgidt}  AND CUSIDT = #{cusidt} " +
            "  AND   APCODE = #{apcode}  AND  CURCDE = #{curcde}  AND   ERYDAT = '1999-10-31' " +
            "  AND ERYDAT >= #{lintdt}")
    Actblh qryBlh(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde,
                  @Param("lintdt") Date lintdt);

    @Insert("INSERT INTO ACTBLH (ORGIDT, CUSIDT, APCODE, CURCDE, ERYDAT, DEPNUM," +
            " ACTBAL, DRACCM, CRACCM, BNGDAT, DRIRAT, CRIRAT, DDRIRT, DCRIRT, RECSTS)" +
            " VALUES (#{orgidt}, #{cusidt},  #{apcode},#{curcde},#{erydat}, #{depnum}," +
            " #{actbal},#{draccm},#{craccm}, #{bngdat}, #{drirat},#{crirat},#{ddrirt},#{dcrirt},#{recsts})")
    int insertBlh(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde,
                  @Param("erydat") Date erydat, @Param("depnum") String depnum,
                  @Param("actbal") Long actbal, @Param("draccm") Long draccm,
                  @Param("craccm") Long craccm, @Param("bngdat") Date bngdat,
                  @Param("drirat") BigDecimal drirat, @Param("crirat") BigDecimal crirat,
                  @Param("ddrirt") BigDecimal ddrirt, @Param("dcrirt") BigDecimal dcrirt,
                  @Param("recsts") String recsts);

    @Update("UPDATE  ACTACT SET     DRACCM = 0,CRACCM = 0,  LINDTH = #{lindth},LINTDT = #{lintdt}" +
            "      WHERE ORGIDT = #{orgidt}  AND CUSIDT = #{cusidt} " +
            "  AND   APCODE = #{apcode}  AND  CURCDE = #{curcde}  AND  SYSIDT =#{sysidt}")
    int updateAct(@Param("lindth") Date lindth, @Param("lintdt") Date lintdt,
                  @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde,
                  @Param("sysidt") String sysidt);

    @Select("SELECT NVL(MAX(YINTSQ),0)  FROM   ACTCIT WHERE  SYSIDT = #{sysidt} AND" +
            " ORGIDT = #{orgidt}  AND CUSIDT = #{cusidt}  AND   APCODE = #{apcode}  AND" +
            "  CURCDE = #{curcde} AND   TO_CHAR(IPYDAT,'YYYY') = #{year}")
     short qryYintsq(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                     @Param("apcode") String apcode, @Param("curcde") String curcde,
                     @Param("year") String year);
    
     @Insert("INSERT  INTO  ACTCIT ( SYSIDT, ORGIDT, CUSIDT, APCODE,  CURCDE, IPYDAT, YINTSQ, BEGDAT," +
             "  ENDDAT, INTCDE, INTRAT, INTAMT, VCHSET, REASON, INTFLG, RECSTS )" +
             "  VALUES  (#{sysidt}, #{orgidt}, #{cusidt},  #{apcode},#{curcde},#{ipydat}," +
             "#{yintsq}, #{begdat}, #{enddat},  #{intcde},#{intrat},#{intamt},"+
             "#{vchset}, #{reason}, #{intflg},  #{recsts})")
    int insertCit(@Param("sysidt") String sysid, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde,
                  @Param("ipydat") Date ipydat, @Param("yintsq") int yintsq, @Param("begdat") Date begdat,
                  @Param("enddat") Date enddat, @Param("intcde") String intcde,
                  @Param("intrat") BigDecimal intrat, @Param("intamt") Long intamt, @Param("vchset") int vchset,
                  @Param("reason") String reason, @Param("intflg") String intflg, @Param("recsts") String recsts);

    @Select("SELECT    OLDACN FROM    ACTANI WHERE   ORGIDT = #{orgidt}  AND" +
                   "  CUSIDT = #{cusidt}  AND  APCODE = #{apcode}  AND" +
                   "   CURCDE = #{curcde}  AND  SYSIDT = #{sysidt}")
     String qryOldacn(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                      @Param("apcode") String apcode, @Param("curcde") String curcde,
                      @Param("sysidt") String sysidt);

    @Select("SELECT NVL(SUM(DRACCM),0) as DRACCM,NVL(SUM(CRACCM),0) as CRACCM " +
            "  FROM ACTBLH WHERE ORGIDT = #{orgidt}  AND CUSIDT = #{cusidt} " +
            "  AND   APCODE = #{apcode}  AND  CURCDE = #{curcde}  AND   ERYDAT >= #{lintdt}")
    Actblh selectBlh(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                     @Param("apcode") String apcode, @Param("curcde") String curcde,
                     @Param("lintdt") Date lintdt);

    @Select("SELECT INTEXP,INTINC FROM   ACTAPC  WHERE  APCODE = #{apcode}")
    Actapc qryApcByCode(@Param("apcode") String apcode);
    
    @Select("SELECT AVABAL,ACTSTS,RECSTS,DEPNUM " +
            "  FROM ACTACT WHERE   ORGIDT = #{orgidt}  AND" +
                   "  CUSIDT = #{cusidt}  AND  APCODE = #{apcode}  AND" +
                   "   CURCDE = #{curcde}  AND  SYSIDT = #{sysidt}")
    Actact qryAct(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde,
                  @Param("sysidt") String sysidt);

    @Select("SELECT ACTSTS,RECSTS  FROM ACTOBF" +
            "  WHERE   ORGIDT = #{orgidt}  AND CUSIDT = #{cusidt}  AND  " +
            "APCODE = #{apcode}  AND CURCDE = #{curcde}  AND  SYSIDT = #{sysidt}")
    Actobf qryObf(@Param("sysidt") String sysidt,@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde);

    @Update("UPDATE ACTOBF SET BOKBAL = #{bokbal}, AVABAL = #{avabal}," +
            "                    DIFBAL = #{difbal}, CIFBAL = #{cifbal}, ACTSTS = #{actsts}, FRZSTS = #{frzsts}," +
            "                    REGSTS = #{regsts}, RECSTS = #{recsts}, OVELIM = #{ovelim}, OVEEXP = #{oveexp}," +
            "                    CQEFLG = #{cqeflg}, GLCBAL = #{glcbal}" +
            "              WHERE SYSIDT = #{sysidt} AND ORGIDT = #{orgidt}" +
            "                AND CUSIDT = #{cusidt}  AND APCODE = #{apcode}" +
            "                AND CURCDE = #{curcde}")
    int updateObf(@Param("bokbal") long bokbal, @Param("avabal") long avabal, @Param("difbal") long difbal,
                  @Param("cifbal") long cifbal, @Param("actsts") String actsts, @Param("frzsts") String frzsts,
                  @Param("regsts") String regsts, @Param("recsts") String recsts, @Param("ovelim") long ovelim,
                  @Param("oveexp") String oveexp, @Param("cqeflg") String cqeflg, @Param("glcbal") String glcbal,
                  @Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde);

     @Insert("INSERT INTO ACTVCH(ORGIDT, TLRNUM, VCHSET, SETSEQ, ORGID2, PRDCDE, CRNYER, PRDSEQ," +
            "  FXEFLG, ORGID3, CUSIDT, APCODE, CURCDE, TXNAMT, VALDAT, RVSLBL," +
            " ANACDE, FURINF, FXRATE, SECCCY, SECAMT, CORAPC, VCHATT, THRREF," +
            "  VCHAUT, VCHANO, DEPNUM, TXNBAK, ACTBAK, CLRBAK, ORGID4, ERYTYP," +
            " ERYDAT, ERYTIM, RECSTS)" +
            " VALUES( #{orgidt},#{tlrnum},#{vchset},#{setseq},#{orgid2},#{prdcde},#{crnyer},#{prdseq},"+
             " #{fxeflg},#{orgid3},#{cusidt},#{apcode}, #{curcde},#{txnamt},#{valdat},#{rvslbl},"+
             " #{anacde},#{furinf},#{fxrate},#{secccy}, #{secamt},#{corapc},#{vchatt},#{thrref},"+
             " #{vchaut},#{vchano},#{depnum},#{txnbak}, #{actbak},#{clrbak},#{orgid4},#{erytyp},"+
             " #{erydat},#{erytim},#{recsts})")
    int insertActvch(@Param("orgidt") String orgidt, @Param("tlrnum") String tlrnum, @Param("vchset") int vchset,
                     @Param("setseq") short setseq, @Param("orgid2") String orgid2, @Param("prdcde") String prdcde,
                     @Param("crnyer") Short crnyer, @Param("prdseq") int prdseq, @Param("fxeflg") String fxeflg,
                     @Param("orgid3") String orgid3, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                     @Param("curcde") String curcde, @Param("txnamt") long txnamt, @Param("valdat") String valdat,
                     @Param("rvslbl") String rvslbl, @Param("anacde") String anacde, @Param("furinf") String furinf,
                     @Param("fxrate") BigDecimal fxrate, @Param("secccy") String secccy, @Param("secamt") long secamt,
                     @Param("corapc") String corapc, @Param("vchatt") long vchatt, @Param("thrref") String thrref,
                     @Param("vchaut") String vchaut, @Param("vchano") String vchano, @Param("depnum") String depnum,
                     @Param("txnbak") String txnbak, @Param("actbak") String actbak, @Param("clrbak") String clrbak,
                     @Param("orgid4") String orgid4, @Param("erytyp") String erytyp, @Param("erydat") String erydat,
                     @Param("erytim") String erytim, @Param("recsts") String recsts);

    @Select("SELECT APCTYP FROM ACTAPC WHERE APCODE = #{apcode} AND RECSTS = ' '")
    String qryApctypByCde(@Param("apcode") String apcode);

    @Select("SELECT  ERYSEQ, ORGIDT, CUSIDT, APCODE, CURCDE, ERYAMT," +
            "     CDRFLG, TOTNUM, CORCUR, CORAPC FROM  ACTERY" +
            "    WHERE  PRDCDE = #{prdcde} AND EVTCDE = #{evtcde} AND" +
            "   CTPCDE = #{ctpcde} AND  CATCDE = #{catcde}  ORDER  BY  ERYSEQ")
    List<Actery> qryErys(@Param("prdcde") String prdcde, @Param("evtcde") String evtcde,
                         @Param("ctpcde") String ctpcde, @Param("catcde") String catcde);

    @Delete("DELETE FROM ACTOBF" +
            "  WHERE SYSIDT = #{sysidt} AND ORGIDT = #{orgidt}" +
            "   AND CUSIDT = #{cusidt}  AND APCODE = #{apcode} AND CURCDE = #{curcde}")
    int deleteObf(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde);

    @Select("SELECT RECSTS FROM ACTOAC WHERE ORGIDT = #{orgidt} " +
            " AND CUSIDT = #{cusidt}  AND APCODE = #{apcode} AND CURCDE = #{curcde}")
    List<String> qryOacRecsts(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                              @Param("apcode") String apcode, @Param("curcde") String curcde);

    @Delete("DELETE  FROM ACTOAC WHERE ORGIDT = #{orgidt} " +
            " AND CUSIDT = #{cusidt}  AND APCODE = #{apcode} AND CURCDE = #{curcde}")
    int deleteOac(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde);

    @Insert("INSERT INTO ACTOBF(SYSIDT, ORGIDT, CUSIDT, APCODE, CURCDE," +
            " BOKBAL, AVABAL, DIFBAL, CIFBAL, ACTSTS,  FRZSTS, REGSTS, RECSTS, OVELIM, OVEEXP," +
            " CQEFLG, GLCBAL) VALUES(#{sysidt},#{orgidt} , #{cusidt}," +
            "#{apcode}, #{curcde}, #{bokbal},#{avabal},#{difbal},#{cifbal}," +
            " #{actsts}, #{frzsts}, #{regsts}, #{recsts}, #{ovelim},#{oveexp}," +
            " #{cqeflg},#{glcbal})")
    int insertObf(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                  @Param("apcode") String apcode, @Param("curcde") String curcde, @Param("bokbal") long bokbal,
                  @Param("avabal") long avabal, @Param("difbal") long difbal, @Param("cifbal") long cifbal,
                  @Param("actsts") String actsts, @Param("frzsts") String frzsts, @Param("regsts") String regsts,
                  @Param("recsts") String recsts, @Param("ovelim") long ovelim, @Param("oveexp") String oveexp,
                  @Param("cqeflg") String cqeflg, @Param("glcbal") String glcbal);

    @Select("SELECT ACTGLC.GLCBAL,ACTGLC.GLCOPN,ACTAPC.APCTYP" +
            " FROM ACTAPC, ACTGLC WHERE ACTAPC.GLCODE = ACTGLC.GLCODE AND" +
            "   ACTAPC.APCODE = #{apcode}")
    ApcGlc qryApcGlc(@Param("apcode") String apcode);

    @Select("select oldacn,orgidt,cusidt,apcode,curcde from actani where recsts = ' '")
    List<Actani> qryActanis();

    @Select("Select apcode,intexp from actapc where recsts = ' '")
    List<Actapc> qryApcs();

    /**
     * 按机构号查询计息传票信息，统计各科目利息总金额和笔数
     */
    @Select("select orgidt,'852' as bebglc,substr(corapc,0,3) as corglc,count(*) as txncnt,sum(txnamt) as sumint " +
            "        from actvch where substr(apcode,0,3) = '852'" +
            "        and orgidt = #{orgidt}" +
            "        group by orgidt,substr(apcode,0,3),substr(corapc,0,3)")
    List<TranDebitVoucher> qryVchForInterestInfos(@Param("orgidt")String orgidt);

    @Select("select glcnam from actglc where glcode = #{glcode}")
    String qryGlcnamByCode(@Param("glcode")String glcode);
}
