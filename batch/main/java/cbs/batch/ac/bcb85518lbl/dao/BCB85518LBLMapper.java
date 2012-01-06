package cbs.batch.ac.bcb85518lbl.dao;

import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.code.model.Actsct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-3
 * Time: 14:12:59
 * To change this template use File | Settings | File Templates.
 */
public interface BCB85518LBLMapper {

    @Select(" SELECT      CRNDAT,      TDYMAK,      MONMAK," +
            " QTRMAK,      YERMAK,      WDYMAK," +
            " FNTMAK,      HYRMAK,      NWKDAY" +
            " FROM     ACTSCT" +
            " WHERE     SCTNUM = #{sctnum}")
    Actsct selectActsctByPK(@Param("sctnum") short sctnum);

    @Select(" SELECT  SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,SECCCY," +
            "    LEGBAL,SECLBL,NLEGLL,NLEGNY,NLEGPG,FSTPAG," +
            "    LEGPLN,LEGCYC,LEGCDT,LEGFMT,LEGSHT,RECSTS," +
            "    LEGDEP" +
            " FROM     ACTLBL" +
            " ORDER BY  SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,SECCCY")
    List<Actlbl> selectActlbl();

    @Update(" UPDATE ACTLBL" +
            " SET FSTPAG = 'Y'" +
            " WHERE  SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND SECCCY = #{secccy}")
    int updateActlblFstpag(@Param("sysidt") String sysidt,
                          @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                          @Param("curcde") String curcde,@Param("secccy") String secccy);

    @Update(" UPDATE ACTLBL" +
            " SET NLEGPG = NLEGPG + 1,LEGPLN = 1" +
            " WHERE  SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND SECCCY = #{secccy}")
    int updateActlblNlegpg(@Param("sysidt") String sysidt,
                          @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                          @Param("curcde") String curcde,@Param("secccy") String secccy);

    @Update(" UPDATE ACTLGC SET" +
            " RECSTS = 'P'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}")
    int updateActlgcRecsts(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde);

    @Update(" UPDATE ACTLGF SET" +
            " RECSTS = 'P'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND SECCCY = #{secccy}")
    int updateActlgfRecsts(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde,@Param("secccy") String secccy);
}
