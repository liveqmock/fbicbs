package cbs.batch.ac.bcb8524.dao;

import cbs.batch.ac.bcb8524.ActArgsBean;
import cbs.repository.account.maininfo.model.Actfrz;
import cbs.repository.code.model.Actccy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BCB8524Mapper {

    @Select("SELECT ORGIDT, CURCDE,  SUM(BOKBAL) as sumBokbal," +
            " SUM(DDRAMT) as sumDdramt, SUM(DCRAMT) as sumDcramt," +
            " SUM(MDRAMT) as sumMdramt, SUM(MCRAMT) as sumMcramtl," +
            " SUM(YDRAMT) as sumYdramt, SUM(YCRAMT) as sumYcramt" +
            " FROM ACTACT " +
            " WHERE RECSTS = #{recsts} AND SYSIDT = #{sysidt} "+
            " GROUP BY ORGIDT,CURCDE")
    List<ActArgsBean> selectActArgs(@Param("sysidt")String sysidt,@Param("recsts")String recsts);

    @Select("SELECT DECPOS FROM ACTCCY" +
                 " WHERE CURCDE = #{curcde}")
    Actccy selectCcyByCurcde(@Param("curcde")String curcde);
}