package cbs.batch.ac.bcb8529.dao;

import cbs.repository.account.maininfo.model.Actfrz;
import cbs.repository.code.model.Actsct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

import java.util.Date;

public interface BCB8529Mapper {

    @Select("SELECT   TO_CHAR(CRNDAT,'YYYY-MM-dd')  FROM   ACTSCT WHERE  SCTNUM = #{sctnum} AND    RECSTS = #{recsts}")
    String selectCrndat(@Param("sctnum")Short sctnum,@Param("recsts")String recsts);

    @Update("UPDATE ACTACT  SET AVABAL = BOKBAL, FRZSTS = '0'"+
                   "  WHERE RECSTS = #{recsts}")
    int updateAct(@Param("recsts")String recsts);

    @Select("SELECT   SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE, FRZAMT,FRZFLG,FRZMOD,FRZBDT,FRZEDT,PDNSEQ" +
            "  FROM     ACTFRZ  WHERE     FRZBDT <= TO_DATE(#{crndat},'yyyy-MM-dd')" +
            "    AND     SYSIDT = #{sysidt}  AND     RECSTS = #{recsts}" +
            "    ORDER BY  SYSIDT,ORGIDT,CURCDE,APCODE,CUSIDT,PDNSEQ")
    List<Actfrz> selectActfrzs(@Param("crndat")String crndat,@Param("sysidt")String sysidt,@Param("recsts")String recsts);

    @Select(" SELECT BOKBAL  FROM   ACTACT" +
            " WHERE  SYSIDT = #{sysidt}  AND  ORGIDT = #{orgidt}" +
            "  AND  CUSIDT = #{cusidt}  AND  APCODE = #{apcode}" +
            "  AND  CURCDE = #{curcde}")
    Long selectBokbal(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                      @Param("apcode")String apcode,@Param("curcde")String curcde);

    @Select("SELECT DECPOS   FROM ACTCCY WHERE CURCDE = #{curcde}")
    short selectDecpos(@Param("curcde")String curcde);

    @Update("UPDATE ACTACT  SET AVABAL = 0, FRZSTS = #{frzsts}" +
             " WHERE  SYSIDT = #{sysidt}  AND  ORGIDT = #{orgidt}" +
            "  AND  CUSIDT = #{cusidt}  AND  APCODE = #{apcode}" +
            "  AND  CURCDE = #{curcde} AND RECSTS = #{recsts}")
    int updateAct1(@Param("frzsts")String frzsts,@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,
                   @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde,
                   @Param("recsts")String recsts);

    @Update("UPDATE ACTACT  SET AVABAL = BOKBAL - #{frzamt}, FRZSTS = #{frzsts}" +
             " WHERE  SYSIDT = #{sysidt}  AND  ORGIDT = #{orgidt}" +
            "  AND  CUSIDT = #{cusidt}  AND  APCODE = #{apcode}" +
            "  AND  CURCDE = #{curcde} AND RECSTS = #{recsts}")
    int updateAct2(@Param("frzamt")long frzamt,@Param("frzsts")String frzsts,@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,
                   @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde,
                   @Param("recsts")String recsts);

    @Update("UPDATE ACTACT  SET AVABAL = BOKBAL, FRZSTS = #{frzsts}" +
             " WHERE  SYSIDT = #{sysidt}  AND  ORGIDT = #{orgidt}" +
            "  AND  CUSIDT = #{cusidt}  AND  APCODE = #{apcode}" +
            "  AND  CURCDE = #{curcde} AND RECSTS = #{recsts}")
    int updateAct3(@Param("frzsts")String frzsts,@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,
                   @Param("cusidt")String cusidt,@Param("apcode")String apcode,@Param("curcde")String curcde,
                   @Param("recsts")String recsts);
}
