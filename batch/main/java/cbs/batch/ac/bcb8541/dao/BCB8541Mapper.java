package cbs.batch.ac.bcb8541.dao;

import cbs.batch.ac.bcb8541.BvaBean;
import cbs.repository.code.model.Actirt;
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
public interface BCB8541Mapper {

    @Update("UPDATE ACTACT SET DIFBAL = 0 , CIFBAL = 0" +
            " WHERE INTFLG = #{intflg} AND ACTSTS = #{actsts}" +
            "  AND RECSTS = #{recsts}")
    int updateAct(@Param("intflg")String intflg,@Param("actsts")String actsts,
                  @Param("recsts")String recsts);

    @Select("SELECT ORGIDT,CUSIDT,APCODE,CURCDE, SUM(BVAAMT) as sumBvaamt" +
            " FROM ACTBVA  WHERE BVADAT > to_date(#{crndat},'yyyy-MM-dd')" +
            " AND BVAFLG = #{bvaflg} AND RECSTS = #{recsts}" +
            " AND BVAAMT >  0  GROUP BY ORGIDT,CUSIDT,APCODE,CURCDE")
    List<BvaBean> selectBvasUp(@Param("crndat")String crndat,@Param("bvaflg")String bvaflg,
                             @Param("recsts")String recsts);

    @Select("SELECT ORGIDT,CUSIDT,APCODE,CURCDE, SUM(BVAAMT) as sumBvaamt" +
            " FROM ACTBVA  WHERE BVADAT > to_date(#{crndat},'yyyy-MM-dd')" +
            " AND BVAFLG = #{bvaflg} AND RECSTS = #{recsts}" +
            " AND BVAAMT <  0  GROUP BY ORGIDT,CUSIDT,APCODE,CURCDE")
    List<BvaBean> selectBvasDown(@Param("crndat")String crndat,@Param("bvaflg")String bvaflg,
                             @Param("recsts")String recsts);

    @Update("UPDATE  ACTACT SET  CIFBAL = #{amt}" +
            " WHERE  SYSIDT = #{sysidt}  AND  ORGIDT = #{orgidt}" +
            " AND  CUSIDT = #{cusidt}  AND  APCODE = #{apcode}" +
            " AND  CURCDE = #{curcde} AND  ACTSTS = #{actsts}" +
            " AND  RECSTS = #{recsts}")
    int updateActCifbal(@Param("amt")long amt,@Param("sysidt")String sysidt,
                             @Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde,
                             @Param("actsts")String actsts,@Param("recsts")String recsts);

    @Update("UPDATE  ACTACT SET  DIFBAL = #{amt}" +
            " WHERE  SYSIDT = #{sysidt}  AND  ORGIDT = #{orgidt}" +
            " AND  CUSIDT = #{cusidt}  AND  APCODE = #{apcode}" +
            " AND  CURCDE = #{curcde} AND  ACTSTS = #{actsts}" +
            " AND  RECSTS = #{recsts}")
    int updateActDifbal(@Param("amt")long amt,@Param("sysidt")String sysidt,
                             @Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
                             @Param("apcode")String apcode,@Param("curcde")String curcde,
                             @Param("actsts")String actsts,@Param("recsts")String recsts);
}