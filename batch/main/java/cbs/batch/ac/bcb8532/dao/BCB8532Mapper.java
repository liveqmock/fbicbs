package cbs.batch.ac.bcb8532.dao;

import cbs.batch.ac.bcb8532.CglccyBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-1
 * Time: 10:44:33
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8532Mapper {
  
    @Select("SELECT   CRNDAT  FROM   ACTSCT WHERE  SCTNUM = #{sctnum} AND  RECSTS =#{recsts}")
    Date selectCrndat(@Param("sctnum")Short sctnum,@Param("recsts")String recsts);

    @Select("SELECT   ACTCGL.CURCDE,SUM(DRBALA) AS SUMDRBALA, SUM(CRBALA) AS SUMCRBALA," +
            " SUM(DRAMNT) AS SUMDRAMNT, SUM(CRAMNT) AS SUMCRAMNT,DECPOS" +
            " FROM   ACTCGL,ACTCCY  WHERE  ACTCGL.RECSTS = #{recsts1}"+
            " AND   ACTCCY.RECSTS = #{recsts2} AND   ACTCCY.CURCDE = ACTCGL.CURCDE" +
            " GROUP    BY   ACTCGL.CURCDE,ACTCCY.DECPOS")
    List<CglccyBean> qryCglCcy(@Param("recsts1")String recsts1,@Param("recsts2")String recsts2);
}