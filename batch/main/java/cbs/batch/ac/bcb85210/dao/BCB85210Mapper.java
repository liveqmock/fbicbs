package cbs.batch.ac.bcb85210.dao;

import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actoac;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface BCB85210Mapper {

    @Select("SELECT ORGIDT,CUSIDT,APCODE,CURCDE,CLSDAT,RECSTS " +
            "FROM ACTOAC WHERE ( RECSTS = #{recsts}" +
            " AND OACFLG = #{oacflg} )  OR (     RECSTS = 'A' " +
            " AND  OACFLG = 'B' AND  CLSDAT = #{crndat} )")
    List<Actoac> qryOac(@Param("recsts")String recsts,@Param("oacflg")String oacflg,@Param("crndat") Date crndat);

    @Select("SELECT  NVL(SUM(AVABAL),0) FROM  ACTPLE " +
            "   WHERE  SYSIDT = #{sysidt}" +
            "    AND  ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}" +
            "    AND  APCODE = #{apcode} AND  CURCDE = #{curcde}" +
            "    AND  RECSTS = #{recsts}")
    long qrySumAvabalFromPle(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde,@Param("recsts")String recsts);

    @Update("UPDATE  ACTACT SET  ACTSTS = #{actsts1}, RECSTS = #{actsts2}," +
            "     CLSDAT = #{clsdat}, LENTDT = #{lentdt}" +
             "   WHERE  SYSIDT = #{sysidt}" +
            "    AND  ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}" +
            "    AND  APCODE = #{apcode} AND  CURCDE = #{curcde}" +
            "    AND  BOKBAL =  0 AND  RECSTS = #{recsts}" +
            "    AND  ACTSTS = #{actsts3}  AND  REGSTS = #{regsts}" +
            "    AND  FRZSTS = #{frzsts}")
    int updateAct(@Param("actsts1")String actsts1,@Param("actsts2")String actsts2,@Param("clsdat")Date clsdat,
                  @Param("lentdt")Date lentdt, @Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde,@Param("recsts")String recsts,
                             @Param("actsts3")String actsts3,@Param("regsts")String regsts,@Param("frzsts")String frzsts);

    @Select("SELECT   BOKBAL,      ACTSTS,      REGSTS,  FRZSTS FROM  ACTACT" +
            "   WHERE  SYSIDT = #{sysidt}" +
            "    AND  ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}" +
            "    AND  APCODE = #{apcode} AND  CURCDE = #{curcde}" +
            "    AND  RECSTS = #{recsts}")
    Actact qryUniqueAct(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde,@Param("recsts")String recsts);

     @Update("UPDATE ACTOAC SET RECSTS = #{recsts} WHERE ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}" +
             "  AND  APCODE = #{apcode} AND  CURCDE = #{curcde}")
    int updateOac(@Param("recsts")String recsts,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde);

    @Update("UPDATE  ACTPLE SET  RECSTS = #{recsts1} WHERE  SYSIDT = #{sysidt}" +
            "    AND  ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}" +
            "    AND  APCODE = #{apcode} AND  CURCDE = #{curcde}" +
            "    AND  RECSTS = #{recsts2}")
    int updatePle(@Param("recsts1")String recsts1,@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,
                  @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde,
                  @Param("recsts2")String recsts2);

    @Update("UPDATE ACTSBL SET RECSTS = 'C' " +
            "WHERE  SYSIDT = #{sysidt} AND  ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}"+
            " AND  APCODE = #{apcode} AND  CURCDE = #{curcde}")
    int updateSbl(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,
                  @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde);

    @Update("UPDATE ACTLBL SET RECSTS = 'C' " +
                "WHERE  SYSIDT = #{sysidt} AND  ORGIDT = #{orgidt}  AND  CUSIDT = #{cusidt}"+
                " AND  APCODE = #{apcode} AND  CURCDE = #{curcde}")
        int updateLbl(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,
                      @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde);

}