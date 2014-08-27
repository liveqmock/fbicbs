package cbs.batch.ac.bcb8522.dao;

import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.code.model.Actapc;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BCB8522Mapper {
    
    @Select("SELECT  OACFLG, ORGIDT, CUSIDT, APCODE, CURCDE,ACTNAM, DINRAT, CINRAT, DRATSF, CRATSF," +
                  " INTFLG, INTCYC, INTTRA, STMCYC, STMCDT,STMFMT, STMADD, STMZIP, STMSHT, STMDEP," +
                  " LEGCYC, LEGCDT, LEGFMT, LEGADD, LEGZIP,LEGSHT, LEGDEP, ACTTYP, DEPNUM, OPNDAT," +
            "CLSDAT, CQEFLG, BALLIM, OVELIM, OVEEXP,CRETLR, CREDAT, RECSTS " +
            "FROM  ACTOAC WHERE  RECSTS = #{recsts} AND (OACFLG = #{openflg} " +
            "OR OACFLG = #{updateflg})")
    List<Actoac> selectOac(@Param("recsts")String recsts,@Param("openflg")String openflg,@Param("updateflg")String updateflg);

    @Select("SELECT   CRNDAT FROM   ACTSCT WHERE  SCTNUM = #{sctnum}")
    Date selectCrndat(@Param("sctnum")Short sctnum);

    @Select("SELECT  *  FROM  ACTAPC  WHERE  APCODE = #{apcode}")
    Actapc selectApcByCode(@Param("apcode")String apcode);

    @Select("SELECT  * FROM  ACTACT   WHERE  SYSIDT = #{sysidt}" +
            "  AND  ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}   AND  APCODE = #{apcode}" +
            "  AND  CURCDE = #{curcde}")
    Actact selectActByOac(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                                  @Param("apcode")String apcode,@Param("curcde")String curcde);

    @Delete("DELETE  FROM  ACTACT   WHERE  SYSIDT = #{sysidt}" +
            "  AND  ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}   AND  APCODE = #{apcode}" +
            "  AND  CURCDE = #{curcde}")
    int deleteActByOac(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                                  @Param("apcode")String apcode,@Param("curcde")String curcde);

    @Insert("INSERT INTO ACTACT( SYSIDT,      ORGIDT,      CUSIDT,APCODE,      CURCDE,      ACTNAM," +
            "  BOKBAL,      AVABAL,      DIFBAL,CIFBAL,      MAVBAL,      YAVBAL," +
            "  DDRAMT,      DCRAMT,      DDRCNT, DCRCNT,      MDRAMT,      MCRAMT," +
            "  MDRCNT,      MCRCNT,      YDRAMT, YCRAMT,      YDRCNT,      YCRCNT," +
            "  DINRAT,      CINRAT,      DRATSF, CRATSF,      DACINT,      CACINT," +
            "  DAMINT,      CAMINT,      INTFLG, INTCYC,      INTTRA,      LINTDT," +
            "  ACTTYP,      ACTGLC,      ACTPLC, DEPNUM,      LENTDT,      DRACCM," +
            "  CRACCM,      CQEFLG,      BALLIM,  OVELIM,      OVEEXP,      OPNDAT," +
            "  CLSDAT,      REGSTS,      FRZSTS, ACTSTS,      AMDTLR,      UPDDAT," +
            "  RECSTS,      LINDTH)   VALUES" +
            " (#{sysidt},    #{orgidt},  #{cusidt}, #{apcode},     #{curcde},       #{actnam}," +
            "            0.00,                0.00,               0.00,      0.00,                0.00,          0.00," +
            "            0.00,                0.00,               0,       0,                   0.00,               0.00," +
            "            0,                   0,                  0.00,    0.00,                0,                  0," +
            "           #{dinrat,jdbcType=VARCHAR},         #{cinrat,jdbcType=VARCHAR},        #{dratsf},  #{cratsf},          0.00,               0.00," +
            "            0.00,                0.00,              #{intflg},  #{intcyc,jdbcType=VARCHAR},         #{inttra},       #{opndat}," +
            "           #{acttyp},         #{glcode},        #{plcode},   #{depnum},     #{opndat},         0.00," +
            "            0.00,              #{cqeflg,jdbcType=VARCHAR},       #{ballim},  #{ovelim},       #{oveexp},        #{opndat}," +
            "            #{clsdat}, #{regsts},#{frzsts},  #{actsts},  #{cretlr},  #{credat}," +
            "           #{recsts},  #{opndat})")
    int insertActByOac(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                                  @Param("apcode")String apcode,@Param("curcde")String curcde,@Param("actnam")String actnam,
                                  @Param("dinrat")String dinrat,@Param("cinrat")String cinrat,@Param("dratsf") BigDecimal dratsf,
                                  @Param("cratsf")BigDecimal cratsf,@Param("intflg")String intflg,@Param("intcyc")String intcyc,
                                  @Param("inttra")String inttra,@Param("opndat")Date opndat,@Param("acttyp")String acttyp,
                                  @Param("glcode")String glcode,@Param("plcode")String plcode,@Param("depnum")String depnum,
                                  @Param("cqeflg")String cqeflg,@Param("ballim")long ballim,@Param("ovelim")long ovelim,
                                  @Param("oveexp")Date oveexp,@Param("clsdat")Date clsdat,@Param("regsts")String regsts,
                                  @Param("frzsts")String frzsts,@Param("actsts")String actsts,@Param("cretlr")String cretlr,
                                  @Param("credat")Date credat,@Param("recsts")String recsts);

    @Update("UPDATE ACTACT SET ACTNAM = #{actnam}, " +
            " CQEFLG = #{cqeflg}, BALLIM = #{ballim},OVELIM =  #{ovelim}, OVEEXP = #{oveexp}," +
            " DINRAT = #{dinrat}, CINRAT = #{cinrat},INTFLG = #{intflg}, INTTRA = #{inttra}," +
            " INTCYC = #{intcyc}, DRATSF = #{dratsf}, CRATSF = #{cratsf}" +
            " UPDDAT = #{crndat}, DEPNUM = #{depnum}" +
            "  WHERE SYSIDT = #{sysidt} AND ORGIDT = #{orgidt} AND  CUSIDT = #{cusidt}  "+
            " AND  APCODE = #{apcode}  AND  CURCDE = #{curcde}")
    int updateActByOac(@Param("actnam")String actnam,@Param("cqeflg")String cqeflg,@Param("ballim") Long ballim,
                       @Param("ovelim") Long ovelim,@Param("oveexp")String oveexp,@Param("dinrat")String dinrat,
                       @Param("cinrat")String cinrat,@Param("intflg")String intflg,@Param("inttra")String inttra,
                       @Param("intcyc")String intcyc,@Param("dratsf")BigDecimal dratsf,@Param("cratsf")BigDecimal cratsf,
                       @Param("crndat")Date crndat,@Param("depnum")String depnum,
                       @Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                                  @Param("apcode")String apcode,@Param("curcde")String curcde);

    @Select("SELECT DISTINCT STMCYC,      STMCDT,      STMFMT FROM  ACTSBL " +
            "  WHERE SYSIDT = #{sysidt} AND ORGIDT = #{orgidt} AND  CUSIDT = #{cusidt}  "+
            " AND  APCODE = #{apcode}  AND  CURCDE = #{curcde}")
    Actsbl selectSblByOac(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                                  @Param("apcode")String apcode,@Param("curcde")String curcde);

    @Update("UPDATE ACTSBL  SET STMSHT = #{stmsht}," +
            " STMDEP = #{stmdep},STMADD = #{stmadd},STMZIP = #{stmzip}," +
            " STMCYC = #{stmcyc}, STMCDT = #{stmcdt},STMFMT = #{stmfmt}," +
            " ACTNAM = #{actnam}" +
           "  WHERE SYSIDT = #{sysidt} AND ORGIDT = #{orgidt} AND  CUSIDT = #{cusidt}  "+
            " AND  APCODE = #{apcode}  AND  CURCDE = #{curcde}")
    int updateSblByOac(@Param("stmsht")short stmsht,@Param("stmdep")String stmdep,@Param("stmadd")String stmadd,@Param("stmzip")String stmzip,
                       @Param("stmcyc")String stmcyc,@Param("stmcdt")String stmcdt,@Param("stmfmt")String stmfmt,
                       @Param("actnam")String actnam,@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,
                       @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde);

    @Select("SELECT DISTINCT LEGCYC,      LEGCDT,      LEGFMT  FROM  ACTLBL" +
            "  WHERE SYSIDT = #{sysidt} AND ORGIDT = #{orgidt} AND  CUSIDT = #{cusidt}  "+
            " AND  APCODE = #{apcode}  AND  CURCDE = #{curcde}")
    Actlbl selectLblByOac(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                                  @Param("apcode")String apcode,@Param("curcde")String curcde);

    @Update("UPDATE ACTLBL SET LEGSHT = #{legsht}," +
            " LEGDEP = #{legdep},  LEGADD = #{legadd}, LEGZIP = #{legzip},"+
            " LEGCYC =#{legcyc}, LEGCDT = #{legcdt},  LEGFMT = #{legfmt}," +
            "  ACTNAM = #{actnam}" +
            "  WHERE SYSIDT = #{sysidt} AND ORGIDT = #{orgidt} AND  CUSIDT = #{cusidt}  "+
            " AND  APCODE = #{apcode}  AND  CURCDE = #{curcde}")
    int updateLblByOac(@Param("legsht")short legsht,@Param("legdep")String legdep,@Param("legadd")String legadd,@Param("legzip")String legzip,
                       @Param("legcyc")String legcyc,@Param("legcdt")String legcdt,@Param("legfmt")String legfmt,
                       @Param("actnam")String actnam,@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,
                       @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde);

    @Update("UPDATE ACTOAC  SET RECSTS = #{recsts}"+
                   "WHERE ORGIDT = #{orgidt} AND  CUSIDT = #{cusidt} "+
                   " AND  APCODE = #{apcode} AND  CURCDE = #{curcde}")
    int updateOacSts(@Param("recsts")String recsts,@Param("orgidt")String orgidt,
                       @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde);
}
