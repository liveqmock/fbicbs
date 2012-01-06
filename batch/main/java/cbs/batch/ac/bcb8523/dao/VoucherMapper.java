package cbs.batch.ac.bcb8523.dao;

import cbs.batch.ac.bcb8523.ActblhParaBean;
import cbs.repository.account.maininfo.model.Actvch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-12-2
 * Time: 17:19:21
 * To change this template use File | Settings | File Templates.
 */
public interface VoucherMapper {

    /**
     * @return
     */
    List<Actvch> selectActvchByRecsts(@Param("recsts") String recsts);

    /**
     * 获取辅币位数
     * @return
     */
    @Select("select decpos from actccy where curcde = #{curcde}")
    int selectDecposFromActccy(@Param("curcde") String curcde);

    /**
     * 根据差异天数计算计息金额
     * @param wkaccm
     * @param lindth
     * @param sysidt
     * @return
     */
    @Select("select #{wkaccm} * (crndat - to_date(#{lindth},'YYYY-MM-DD')) from actsct where sctnum = #{sysidt}")
    long selectAccmByLindth(@Param("wkaccm") long wkaccm, @Param("lindth") String lindth, @Param("sysidt") int sysidt);

    @Select("select #{wkaccm} * (crndat - to_date(#{valdat},'YYYYMMDD')) from actsct where sctnum = #{sysidt}")
    long selectAccmByValdat(@Param("wkaccm") long wkaccm, @Param("valdat") String valdat, @Param("sysidt") int sysidt);

    /**
     * 根据核算码得到总帐码
     * @param apcode
     * @return
     */
    @Select("select glcode  from actapc   where apcode = #{apcode} and recsts = ' '")
    String selectGlcodeFromActapc(@Param("apcode") String apcode);

    /**
     * 余额方向
     * @param glcode
     * @return
     */
    @Select("select glcbal from actglc  where glcode = #{glcode} and   recsts = ' ' ")
    String selectGlcbalFromActglc(@Param("glcode") String glcode);


    /**
     * 超前起息 积数历史 更新
     * @param para
     * @return
     */
    @Update("UPDATE ACTBLH " +
            "   SET DRACCM = DRACCM + #{wkTxnamt} *(ERYDAT-BNGDAT) " +
            "        WHERE ORGIDT = #{actOrgidt} " +
            "        AND APCODE = #{actApcode} " +
            "        AND CUSIDT = #{actCusidt} " +
            "        AND CURCDE = #{actCurcde} " +
            "        AND #{tmpValdat} <= BNGDAT" +
            "        AND #{actLintdt} < BNGDAT" +
            "        AND (#{wktGlcbal} = 'D'" +
            "              OR ( #{wktGlcbal} = 'B'" +
            "              AND ACTBAL < 0 ) )")
    int updateActblhDraccm1(ActblhParaBean para);
    
    @Update(" UPDATE ACTBLH   " +
            "       SET DRACCM = DRACCM + #{wkTxnamt} * (ERYDAT-TO_DATE(#{vchValdat},'YYYYMMDD'))" +
            "        WHERE ORGIDT = #{actOrgidt} " +
            "        AND APCODE = #{actApcode} " +
            "        AND CUSIDT = #{actCusidt} " +
            "        AND CURCDE = #{actCurcde} " +
            "        AND ( ( BNGDAT < #{tmpValdat}" +
            "                  AND #{tmpValdat} < ERYDAT" +
            "                  AND #{actLintdt} < #{tmpValdat})" +
            "                 OR ( BNGDAT = #{actLintdt} " +
            "                   AND #{tmpValdat} <= #{actLintdt} ) )" +
            "                   AND ( #{wktGlcbal} = 'D' " +
            "                          OR ( #{wktGlcbal} = 'B' " +
            "                            AND ACTBAL < 0 ) )")
    int updateActblhDraccm2(ActblhParaBean para);


    @Update("UPDATE ACTBLH " +
            "   SET CRACCM = CRACCM + #{wkTxnamt} *(ERYDAT-BNGDAT) " +
            "        WHERE ORGIDT = #{actOrgidt} " +
            "        AND APCODE = #{actApcode} " +
            "        AND CUSIDT = #{actCusidt} " +
            "        AND CURCDE = #{actCurcde} " +
            "        AND #{tmpValdat} <= BNGDAT" +
            "        AND #{actLintdt} < BNGDAT" +
            "        AND (#{wktGlcbal} = 'C' " +
            "              OR ( #{wktGlcbal} = 'B'" +
            "              AND ACTBAL >= 0 ) )")
    int updateActblhCraccm1(ActblhParaBean para);

    @Update(" UPDATE ACTBLH   " +
            "       SET CRACCM = CRACCM + #{wkTxnamt} * (ERYDAT-TO_DATE(#{vchValdat},'YYYYMMDD'))" +
            "        WHERE ORGIDT = #{actOrgidt} " +
            "        AND APCODE = #{actApcode} " +
            "        AND CUSIDT = #{actCusidt} " +
            "        AND CURCDE = #{actCurcde} " +
            "        AND ( ( BNGDAT < #{tmpValdat}" +
            "                  AND #{tmpValdat} < ERYDAT" +
            "                  AND #{actLintdt} < #{tmpValdat})" +
            "                 OR ( BNGDAT = #{actLintdt} " +
            "                   AND #{tmpValdat} <= #{actLintdt} ) )" +
            "                   AND ( #{wktGlcbal} = 'C' " +
            "                          OR ( #{wktGlcbal} = 'B' " +
            "                            AND ACTBAL >= 0 ) )")
    int updateActblhCraccm2(ActblhParaBean para);



}
