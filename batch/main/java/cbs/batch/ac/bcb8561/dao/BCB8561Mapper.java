package cbs.batch.ac.bcb8561.dao;

import cbs.batch.ac.bcb8561.SctDatBean;
import cbs.repository.code.model.Actsct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface BCB8561Mapper {
    
    @Select("SELECT   *  FROM   ACTSCT WHERE  SCTNUM = #{sctnum} AND    RECSTS = #{recsts}")
    Actsct selectSct(@Param("sctnum")Short sctnum,@Param("recsts")String recsts);

    @Select("SELECT   DISTINCT   NXTDAT  FROM     ACTHOL WHERE    CURCDE = #{curcde}" +
                 "AND    PREDAT = #{date} AND    RECSTS = #{recsts}")
    Date selectNxtdat(@Param("curcde")String curcde,@Param("date")Date date,@Param("recsts")String recsts);

    @Select("SELECT   TO_DATE(#{crndat},'YYYY-MM-DD') - CRNDAT,"
            +"TO_DATE(#{nwkday},'YYYY-MM-DD') - TO_DATE(#{crndat},'YYYY-MM-DD'),"+
            " TO_DATE(#{crndat},'YYYY-MM-DD') - TO_DATE(#{dn_yy1},'YYYY-MM-DD') + 1"+
            "  FROM     ACTSCT WHERE    SCTNUM = #{sctnum}  AND    RECSTS = #{recsts}")
    SctDatBean selectDatSub(@Param("crndat")String crndat,@Param("nwkday")String nwkday,
                            @Param("dn_yy1")String dn_yy1,@Param("sctnum")Short sctnum,@Param("recsts")String recsts);

    @Select("SELECT TO_DATE(#{crndat},'YYYY-MM-DD') - TO_DATE(#{dn_yy1},'YYYY-MM-DD') as days FROM  ACTSCT" +
            "  WHERE  SCTNUM = #{sctnum} AND    RECSTS = #{recsts}")
    short selectDays(@Param("crndat")String crndat,@Param("dn_yy1")String dn_yy1,@Param("sctnum")Short sctnum,@Param("recsts")String recsts);

     @Update("UPDATE ACTTLR  SET TXNCNT = 0 , VCHSET = 0  WHERE RECSTS = #{recsts}")
    int updateActtlr(@Param("recsts")String recsts);

    @Update("UPDATE SCTTLR  SET TXNCNT = 0 , VCHSET = 0 ")
    int updateScttlr();

    @Update("UPDATE SCTTLR   SET TLRSTS = '0'  WHERE TLRSTS = '2'")
    int updateScttlr2();

    /*@Update("UPDATE SCTAPP  SET APPSTS = '2' WHERE APPIDT = 'AC'")
    int updateSctapp();*/

    /*@Update("UPDATE ACTSEV    SET  RECSTS = #{recsts}")
    int updateActsev(@Param("recsts")String recsts);

    @Delete("DELETE FROM ACTDLZ")
    int deleteActdlz();

    @Delete("DELETE FROM ACTCLZ")
    int deleteActclz();*/
}
