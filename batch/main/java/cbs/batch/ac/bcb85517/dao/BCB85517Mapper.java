package cbs.batch.ac.bcb85517.dao;

import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.code.model.Actccy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-2
 * Time: 12:49:56
 * To change this template use File | Settings | File Templates.
 */
public interface BCB85517Mapper {

    @Select(" SELECT SYSIDT,ORGIDT, CUSIDT, APCODE," +
            " CURCDE, SUM(STMBAL) AS STMBAL" +
            " FROM ACTSBL" +
            " WHERE STMFMT IN ('C','S','L')" +
            "     AND RECSTS <> 'I'" +
            " GROUP BY SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE")
    List<Actsbl> selectActsbl();

    @Select(" SELECT SYSIDT,ORGIDT, CUSIDT, APCODE," +
            " CURCDE, SUM(LEGBAL) AS LEGBAL" +
            " FROM ACTLBL" +
            " WHERE LEGFMT IN ('C','F','I')" +
            "     AND RECSTS <> 'I'" +
            " GROUP BY SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE")
    List<Actlbl> selectActlbl();

    @Select(" SELECT  BOKBAL" +
            " FROM   ACTACT" +
            " WHERE SYSIDT = #{sysidt}" +
            "  AND ORGIDT = #{orgidt}" +
            "  AND CUSIDT = #{cusidt}" +
            "  AND APCODE = #{apcode}" +
            "  AND CURCDE = #{curcde}")
    Actact selectActactByPK(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                            @Param("apcode") String apcode, @Param("curcde") String curcde);

    @Select(" SELECT DECPOS,INTCUR" +
            " FROM   ACTCCY" +
            " WHERE  CURCDE = #{curcde}")
    Actccy selectActccyByPK(@Param("curcde") String curcde);
}
