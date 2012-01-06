package cbs.batch.ac.bcb8543.dao;

import cbs.batch.ac.bcb8543.*;
import cbs.repository.account.maininfo.model.Actact;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-10
 * Time: 9:53:27
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8543Mapper {
  @Select("SELECT   CRNDAT, NXTDDS, BMMDDS,BSSDDS, BYYDDS, IPYMAK, IPYDAT, NWKDAY, " +
            "IPYDAT - TO_DATE(#{baseDate},'YYYY-MM-DD') as ipydats," +
            "NWKDAY - TO_DATE(#{baseDate},'YYYY-MM-DD') as nwkdays," +
            "CRNDAT - TO_DATE(#{baseDate},'YYYY-MM-DD') as crndats" +
            " FROM ACTSCT  WHERE SCTNUM = #{sctnum}")
  SctPojoBean selectSctByNum(@Param("baseDate") String baseDate,@Param("sctnum") short sctnum);
  @Select("SELECT   SYSIDT, ORGIDT, CUSIDT, APCODE, CURCDE," +
          "         BOKBAL, DIFBAL, CIFBAL, MAVBAL, YAVBAL, DDRAMT," +
          "         DCRAMT, DDRCNT, DCRCNT, DRATSF, CRATSF," +
          "         DACINT, CACINT, INTFLG, DEPNUM, BALLIM," +
          "         LINTDT, DRACCM, CRACCM,  DINRAT," +
          "         CINRAT, LINDTH FROM  ACTACT" +
          "        WHERE    INTFLG IN ('1','3','4','5')" +
          "        AND RECSTS = #{recsts}  " +
          "   FOR  UPDATE OF  DACINT, CACINT,DRACCM, CRACCM,LINDTH")
    List<Actact> qryActsBySts(@Param("recsts")String recsts);

    @Select("SELECT  EFFDAT, MODFLG, CURIRT,NXTIRT,IRTVAL,EFFDAT - TO_DATE(#{baseDate},'YYYY-MM-DD'), " +
            "EFFDAT -1   FROM   ACTCIR" +
            "  WHERE  (IRTKD1 = #{irtkd1} ) AND  (IRTKD2 = #{irtkd2} ) AND  (CURCDE = #{curcde})")
    CirPojo qryCir(@Param("baseDate")String baseDate,@Param("irtkd1")String irtkd1,
                   @Param("irtkd2")String irtkd2,@Param("curcde")String curcde);

    @Select("SELECT DECPOS  FROM ACTCCY WHERE CURCDE =#{curcde} AND RECSTS = #{recsts}")
    short selectDecposByCde(@Param("curcde")String curcde,@Param("recsts")String recsts);

    @Select("SELECT SUM(ROUND(DRACCM * DDRIRT)), SUM(ROUND(CRACCM*DCRIRT))" +
            " FROM ACTBLH WHERE ORGIDT = #{orgidt}   AND CUSIDT = #{cusidt}   AND " +
            " APCODE = #{apcode}  AND  CURCDE = #{curcde}  AND  ERYDAT >= #{lintdt}" +
            "  GROUP BY  ORGIDT,CUSIDT,APCODE,CURCDE")
    BlhSumIrt selectBlhSumIrt(@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                              @Param("apcode")String apcode,@Param("curcde")String curcde,
                              @Param("lintdt")Date lintdt);

    @Select("SELECT SUM(ROUND(CRACCM * CRIRAT)) as sumCraRat, SUM(ROUND(DRACCM * DRIRAT)) as sumDraRat" +
            " FROM ACTCBH WHERE ORGIDT = #{orgidt}   AND CUSIDT = #{cusidt}   AND " +
            " APCODE = #{apcode}  AND  CURCDE = #{curcde}  AND  ERYDAT >= #{lintdt}" +
            "  GROUP BY  ORGIDT,CUSIDT,APCODE,CURCDE")
    CbhSumRat selectCbhSumIrt(@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                              @Param("apcode")String apcode,@Param("curcde")String curcde,
                              @Param("lintdt")Date lintdt);

    @Select("SELECT (#{lindth}+1) FROM ACTSCT " +
            "  WHERE SCTNUM = #{sctnum}")
    Date lindthAddOne(@Param("lindth")Date lindth,@Param("sctnum") short sctnum);

    @Insert("INSERT INTO ACTBLH (ORGIDT, CUSIDT, APCODE, CURCDE, ERYDAT, DEPNUM, BNGDAT," +
            "ACTBAL, DRACCM, CRACCM,DRIRAT, CRIRAT, DDRIRT,DCRIRT, RECSTS)" +
            "  VALUES (#{orgidt}, #{cusidt}, #{apcode},#{curcde},#{lindth},#{depnum}" +
            "  #{date},#{intbal},#{draccm},#{craccm},#{ddrirt},#{dcrirt} ,#{dirval}," +
            "  #{cirval}, #{recsts})")
    int insertBlh(@Param("orgidt")String orgidt,@Param("cusidt") String cusidt,@Param("apcode")String apcode,
                  @Param("curcde")String curcde,@Param("erydat") Date erydat,@Param("depnum")String depnum,
                  @Param("date")Date date,@Param("intbal") long intbal,@Param("draccm")long draccm,
                  @Param("craccm")long craccm,@Param("ddrirt") BigDecimal ddrirt,@Param("dcrirt")BigDecimal dcrirt,
                  @Param("dirval")BigDecimal dirval,@Param("cirval") BigDecimal cirval,@Param("recsts")String recsts);
    
    @Select("SELECT SUM((TRUNC(CINBAL / #{suefex}) * #{suefex}) * NWKDAY) as sumCinbal," +
            "             SUM((TRUNC(DINBAL / #{suefex}) * #{suefex}) * NWKDAY) as sumDinbal," +
            "             SUM((TRUNC(CINBAL / #{suefex}) * #{suefex}) * NWKDAY * DCRIRT) as sumCinbalIrt," +
            "             SUM((TRUNC(DINBAL / #{suefex}) * #{suefex}) * NWKDAY * DDRIRT) as sumDinbalIrt" +
            "             FROM ACTIBL WHERE SYSIDT = #{sysidt}   AND ORGIDT = #{orgidt}   " +
            " AND CUSIDT = #{cusidt}   AND APCODE = #{apcode}   AND CURCDE = #{curcde}  " +
            " AND IBLDAT >= #{lintdt}  GROUP BY  SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE")
    IblPojo qryIblSum(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt") String cusidt,
                      @Param("apcode")String apcode,@Param("curcde")String curcde,@Param("lintdt")Date lintdt);

    @Update("UPDATE ACTACT SET DACINT = #{dacint},  CACINT = #{cacint}," +
            " DRACCM = #{draccm}, CRACCM = #{craccm},LINDTH = #{lindth}" +
            " WHERE SYSIDT = #{sysidt}   AND ORGIDT = #{orgidt}   " +
            " AND CUSIDT = #{cusidt}   AND APCODE = #{apcode}   AND CURCDE = #{curcde}")
    int updateAct(@Param("dacint") BigDecimal dacint,@Param("cacint") BigDecimal cacint,@Param("draccm") long draccm,
                      @Param("craccm")long craccm,@Param("lindth")Date lindth,
            @Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt") String cusidt,
                      @Param("apcode")String apcode,@Param("curcde")String curcde);
}