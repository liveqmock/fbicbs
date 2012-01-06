package cbs.batch.ac.bcb85518sbl.dao;

import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.code.model.Actsct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-3
 * Time: 9:32:32
 * To change this template use File | Settings | File Templates.
 */
public interface BCB85518SBLMapper {

    @Select(" SELECT      CRNDAT,      TDYMAK,      MONMAK," +
            " QTRMAK,      YERMAK,      WDYMAK," +
            " FNTMAK,      HYRMAK,      NWKDAY" +
            " FROM     ACTSCT" +
            " WHERE     SCTNUM = #{sctnum}")
    Actsct selectActsctByPK(@Param("sctnum") short sctnum);

    @Select(" SELECT  SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,SECCCY," +
            " STMBAL,SECSBL,NSTMLL,NSTMNY,NSTMPG,FSTPAG," +
            " STMPLN,STMCYC,STMCDT,STMFMT,STMSHT,RECSTS,STMDEP" +
            " FROM  ACTSBL" +
            " ORDER BY  SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE")
    List<Actsbl> selectActsbl();

    @Update(" UPDATE ACTSBL" +
            " SET FSTPAG = 'Y'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND SECCCY = #{secccy}")
    int updateActsblFstpag(@Param("sysidt") String sysidt,
                          @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                          @Param("curcde") String curcde,@Param("secccy") String secccy);


    @Update(" UPDATE ACTSBL" +
            " SET NSTMPG = NSTMPG+1,STMPLN = 1" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND SECCCY = #{secccy}" +
            "    AND STMPLN <> 1")
    int updateActsblNstmpg(@Param("sysidt") String sysidt,
                          @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                          @Param("curcde") String curcde,@Param("secccy") String secccy);

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
            "    AND CURCDE = #{curcde}" +
            "    AND STMDEP = #{stmdep}")
    int updateActlsmRecsts(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde,@Param("stmdep") String stmdep);
}
