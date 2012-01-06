package cbs.batch.ac.bcb8542.dao;

import cbs.batch.ac.bcb8542.CirProp;
import cbs.batch.ac.bcb8542.SctPojo;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actvth;
import cbs.repository.account.tempinfo.model.Actcxr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface BCB8542Mapper {
    @Select("SELECT   CRNDAT,NWKDAY,IPYMAK,IPYDAT, " +
            "IPYDAT - TO_DATE(#{baseDate},'YYYY-MM-DD') as ipydats," +
            "NWKDAY - TO_DATE(#{baseDate},'YYYY-MM-DD') as nwkdays," +
            "CRNDAT - TO_DATE(#{baseDate},'YYYY-MM-DD') as crndats" +
            " FROM ACTSCT  WHERE SCTNUM = #{sctnum}")
    SctPojo selectSctByNum(@Param("baseDate") String baseDate,@Param("sctnum") short sctnum);

    @Select("SELECT ORGID3,CUSIDT,APCODE,CURCDE,TXNAMT,RVSLBL,VALDAT" +
            " FROM ACTVTH WHERE VALDAT < #{crndat}")
    List<Actvth> selectVths(@Param("crndat") Date crndat);

    @Update("UPDATE ACTIBL  SET CINBAL = CINBAL + DINBAL, DINBAL = 0" +
            "  WHERE CINBAL + DINBAL > 0")
    int updateIblcdUp();

    @Update("UPDATE ACTIBL  SET DINBAL = CINBAL + DINBAL, CINBAL = 0" +
            "  WHERE CINBAL + DINBAL <= 0")
    int updateIblcdDown();

    @Select("SELECT SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE, BOKBAL,CINRAT,DINRAT,CRATSF,DRATSF" +
            " FROM ACTACT  WHERE ACTACT.RECSTS = #{recsts} AND SYSIDT = #{sysidt}" +
            " AND ( INTFLG = '3' OR INTFLG = '5' OR INTFLG = '6'  OR INTFLG = '7' " +
            " OR ACTGLC LIKE '802%' OR ACTGLC LIKE '901%' )")
    List<Actact> selectActs(@Param("recsts")String recsts,@Param("sysidt")String sysidt);

    @Update("UPDATE ACTIBL  SET CINBAL = CINBAL + #{txnamt}" +
            " WHERE SYSIDT = #{sysidt}  AND ORGIDT = #{orgid3}" +
            " AND CUSIDT = #{cusidt} AND APCODE =#{apcode}" +
            " AND CURCDE = #{curcde} AND IBLDAT >= #{valdat}")
    int updateIblCinbal(@Param("txnamt")long txnamt,@Param("sysidt")String sysidt,
                             @Param("orgid3")String orgid3,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde,
                             @Param("valdat")Date valdat);

    @Update("UPDATE ACTIBL  SET DINBAL = DINBAL + #{txnamt}" +
            " WHERE SYSIDT = #{sysidt}  AND ORGIDT = #{orgid3}" +
            " AND CUSIDT = #{cusidt} AND APCODE =#{apcode}" +
            " AND CURCDE = #{curcde} AND IBLDAT >= #{valdat}")
    int updateIblDinbal(@Param("txnamt")long txnamt,@Param("sysidt")String sysidt,
                             @Param("orgid3")String orgid3,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde,
                             @Param("valdat")Date valdat);

    @Select("SELECT  EFFDAT,MODFLG,CURIRT,NXTIRT,EFFDAT - TO_DATE(#{baseDate},'YYYY-MM-DD')" +
            " FROM   ACTCIR WHERE   IRTKD1 = #{irtkd1}  AND   IRTKD2 = #{irtkd2}" +
            "  AND   CURCDE = #{curcde}")
    CirProp selectCir(@Param("baseDate")String baseDate,@Param("irtkd1")String irtkd1,
                      @Param("irtkd2")String irtkd2,@Param("curcde")String curcde);

    @Select("SELECT CURRAT FROM  ACTCXR  WHERE  CURCDE = #{curcde}" +
            "   AND  XRTCDE = '4' AND  SECCCY = '001'")
    BigDecimal qryCurrat(@Param("curcde")String curcde);

    @Insert("INSERT INTO ACTIBL(SYSIDT,ORGIDT,CUSIDT, APCODE,CURCDE,IBLDAT,CINBAL,DINBAL,CINRAT," +
            " CINOLD,DINOLD,FXRATE,DINRAT,CRIRAT, DRIRAT, DCRIRT,DDRIRT,NWKDAY ) VALUES(#{sysidt}, " +
            "#{orgidt}, #{cusidt},#{apcode}, #{curcde}, #{ibldat}, #{cinbal}, #{dinbal}, #{cinrat}," +
            " #{cinbal}, #{dinbal}, #{currat}, #{dinrat}, #{crirat}, #{drirat}, #{dcrirt}, #{ddrirt}, #{nwkday} )")
    int insertIbl(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde,@Param("ibldat")Date ibldat,
                             @Param("cinbal")long cinbal,@Param("dinbal")long dinbal,@Param("cinrat")String cinrat,
                             @Param("currat")BigDecimal currat,@Param("dinrat")String dinrat,@Param("crirat")BigDecimal crirat,
                             @Param("drirat")BigDecimal drirat,@Param("dcrirt")BigDecimal dcrirt,@Param("ddrirt")BigDecimal ddrirt,
                             @Param("nwkday")short nwkday);
}