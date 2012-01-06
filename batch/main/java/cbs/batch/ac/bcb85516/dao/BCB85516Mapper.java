package cbs.batch.ac.bcb85516.dao;

import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.billinfo.model.Actsbl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-2
 * Time: 16:59:35
 * To change this template use File | Settings | File Templates.
 */
public interface BCB85516Mapper {

    @Select(" SELECT  SYSIDT, ORGIDT, CUSIDT, APCODE, CURCDE," +
            " STMFMT, RECSTS, SECCCY" +
            " FROM  ACTSBL" +
            " WHERE  RECSTS = 'C'" +
            "   OR   RECSTS = 'U'" +
            " FOR UPDATE  OF RECSTS")
    List<Actsbl> selectActsbl();

    @Select(" SELECT  SYSIDT, ORGIDT, CUSIDT, APCODE, CURCDE," +
            " LEGFMT, RECSTS, SECCCY" +
            " FROM  ACTLBL" +
            " WHERE  RECSTS = 'C'" +
            "   OR   RECSTS = 'U'" +
            " FOR UPDATE  OF RECSTS")
    List<Actlbl> selectActlbl();

    /*
    *更新更新ACTNSM數據 RECSTS = 'P' */

    @Update(" UPDATE ACTNSM SET" +
            " RECSTS = 'P'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}")
    int updateActnsmRecsts(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde);

        /*
    *更新更新ACTLSM數據 RECSTS = 'P' */

    @Update(" UPDATE ACTLSM SET" +
            " RECSTS = 'P'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}")
    int updateActlsmRecsts(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde);

    /*
    * 更新ACTSBL FOR 5100*/

    @Update(" UPDATE ACTSBL  SET" +
            " NSTMPG  =  NSTMPG + 1," +
            " STMPLN  =  1," +
            " FSTPAG  =  'Y'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND STMPLN <> 1")
    int updateActsbl(@Param("sysidt") String sysidt,
                      @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                      @Param("curcde") String curcde);

    @Update(" UPDATE ACTSBL" +
            " SET RECSTS = 'I'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND SECCCY = #{secccy}")
    int updateActsblRecsts(@Param("sysidt") String sysidt,
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
            "    AND RECSTS = ' '")
    int updateActlgfRecsts(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde);

    /*
    * 更新ACTLBL FOR 5200*/

    @Update(" UPDATE ACTLBL  SET" +
            " NLEGPG  =  NLEGPG + 1," +
            " LEGPLN  =  1," +
            " FSTPAG  =  'Y'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND LEGPLN <> 1")
    int updateActlbl(@Param("sysidt") String sysidt,
                      @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                      @Param("curcde") String curcde);

    @Update(" UPDATE ACTLBL" +
            " SET RECSTS = 'I'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND SECCCY = #{secccy}")
    int updateActlblRecsts(@Param("sysidt") String sysidt,
                          @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                          @Param("curcde") String curcde,@Param("secccy") String secccy);
}
