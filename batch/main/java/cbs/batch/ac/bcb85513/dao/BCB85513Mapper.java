package cbs.batch.ac.bcb85513.dao;

import cbs.batch.ac.bcb85511.model.ActsctForDate;
import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.account.maininfo.model.Actvth;
import cbs.repository.code.model.Actani;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-28
 * Time: 16:53:09
 * To change this template use File | Settings | File Templates.
 */
public interface BCB85513Mapper {

    /*
   * 更新ACTLBL下次出帐数据字段*/

    @Update(" UPDATE ACTLBL" +
            " SET NLEGNY = #{nlegny}," +
            "    NLEGPG = 1," +
            "    LEGPLN = 1," +
            "    FSTPAG = 'Y'" +
            " WHERE  LEGFMT = 'C'" +
            "    OR  LEGFMT = 'I'")
    int updateActlblForNlegny(@Param("nlegny") short nlegny);

    /*
   * 获取ACTSCT系统控制表数据*/

    @Select(" SELECT TO_CHAR(CRNDAT,'YYYY') AS crndatYear,TO_CHAR(LWKDAY,'YYYY') AS lwkdayYear," +
            " YERMAK, CRNDAT, TDYMAK,QTRMAK" +
            " FROM   ACTSCT WHERE  SCTNUM = #{sctnum}")
    ActsctForDate selectActsctByPK(@Param("sctnum") short sctnum);

    /*
    * 获取actvth表所有数据*/

    @Select(" SELECT ORGID3,CUSIDT,APCODE,CURCDE,TXNAMT," +
            " TLRNUM,VCHSET,SETSEQ,ORGID2,PRDCDE," +
            " VALDAT,RVSLBL,ERYDAT,(ANACDE||' '||FURINF) AS FURINF,FXRATE," +
            " CRNYER,PRDSEQ,THRREF,SECCCY,SECAMT," +
            " ERYTIM,ORGIDT,FXEFLG" +
            " FROM ACTVTH" +
            " ORDER BY  ORGID3,APCODE,CURCDE,CUSIDT,SECCCY,ERYDAT,ERYTIM")
    List<Actvth> selectActvth();

    /*
   * 通过主键获取ACTACT*/

    @Select(" SELECT  DRACCM,  CRACCM FROM  ACTACT T " +
            " WHERE T.SYSIDT = #{sysidt}" +
            "             AND T.ORGIDT = #{orgidt}" +
            "             AND T.CUSIDT = #{cusidt}" +
            "             AND T.APCODE = #{apcode}" +
            "             AND T.CURCDE = #{curcde}")
    Actact selectActactByPK(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                            @Param("apcode") String apcode, @Param("curcde") String curcde);

    /*
   * 更新ACTLGC數據*/

    @Update(" UPDATE ACTLGC SET" +
            " DRACCM = #{draccm},CRACCM = #{craccm},DAYEND = 'Y'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND NLEGPG = #{nlegpg}" +
            "    AND PAGLIN = #{paglin}")
    int updateActlgcByActact(@Param("draccm") long draccm, @Param("craccm") long craccm, @Param("sysidt") String sysidt,
                             @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                             @Param("curcde") String curcde, @Param("nlegpg") int nlegpg, @Param("paglin") short paglin);

    /*
*更新更新ACTLGC數據 RECSTS = 'P' */

    @Update(" UPDATE ACTLGC SET" +
            " RECSTS = 'P'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND NLEGPG <= #{nlegpg}")
    int updateActlgcRecsts(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde, @Param("nlegpg") int nlegpg);
    /*
    * 更新ACTLGC數據 FSTPAG = 'Y'*/

    @Update(" UPDATE ACTLGC SET" +
            " FSTPAG = 'Y'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND NLEGPG = #{nlegpg}" +
            "    AND RECSTS = ' '")
    int updateActlgcFstpag(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde, @Param("nlegpg") int nlegpg);

    /*
    * 更新ACTSBL*/

    @Update(" UPDATE ACTLBL" +
            " SET LEGBAL = #{legbal}," +
            "     SECLBL = 0," +
            "     NLEGLL = 0," +
            "     NLEGNY = #{nlegny}," +
            "     NLEGPG = #{nlegpg}," +
            "     LEGPLN = #{legpln}," +
            "     FSTPAG = #{fstpag}" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND (LEGFMT = 'C' OR LEGFMT = 'I')")
    int updateLastActlbl(@Param("legbal") long legbal, @Param("nlegny") short nlegny, @Param("nlegpg") int nlegpg,
                         @Param("legpln") short legpln, @Param("fstpag") String fstpag, @Param("sysidt") String sysidt,
                         @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                         @Param("curcde") String curcde);

    /*
    * 读取Actlbl
    * */

    @Select(" SELECT  LEGFMT,     LEGDEP,       SYSIDT," +
            " ORGIDT,     CUSIDT,       APCODE," +
            " CURCDE,     SECCCY,       LEGCYC," +
            " NLEGNY,     NLEGPG,       LEGPLN," +
            " FSTPAG,     LEGBAL,       SECLBL," +
            " RECSTS FROM ACTLBL" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND RECSTS <> #{recsts}" +
            "    AND trim(SECCCY) is null ")
    Actlbl selectLblByPKandRecsts(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                                  @Param("apcode") String apcode, @Param("curcde") String curcde, @Param("recsts") String recsts);

    /*
   * 讀取ACTOAC數據*/

    @Select(" SELECT LEGCYC,LEGCDT,LEGFMT," +
            " LEGADD,LEGZIP,LEGSHT,LEGDEP,ACTNAM" +
            " FROM ACTOAC" +
            " WHERE ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}")
    Actoac selectOacByPK(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                         @Param("apcode") String apcode, @Param("curcde") String curcde);

    @Select(" SELECT OLDACN FROM ACTANI" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND RECSTS <> #{recsts}")
    Actani selectActaniByPK(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                            @Param("apcode") String apcode, @Param("curcde") String curcde, @Param("recsts") String recsts);

    @Insert(" INSERT INTO ACTLBL(" +
            " SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,SECCCY," +
            " OLDACN,ACTNAM,LEGBAL,SECLBL,NLEGLL,NLEGNY," +
            "        NLEGPG,LEGPLN,LEGCYC,LEGCDT,LEGFMT," +
            " LEGSHT,LEGDEP,LEGADD,LEGZIP,FSTPAG,RECSTS)" +
            " VALUES(" +
            " #{sysidt},#{orgidt},#{cusidt},#{apcode},#{curcde}," +
            " #{secccy},#{oldacn},#{actnam},#{legbal},#{seclbl}," +
            " #{nlegll},#{nlegny},#{nlegpg},#{legpln},#{legcyc}," +
            " #{legcdt},#{legfmt},#{legsht},#{legdep},#{legadd}," +
            " #{legzip},'Y',' ')")
    int insertActlbl(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                     @Param("apcode") String apcode, @Param("curcde") String curcde, @Param("secccy") String secccy,
                     @Param("oldacn") String oldacn, @Param("actnam") String actnam, @Param("legbal") long legbal,
                     @Param("seclbl") long seclbl, @Param("nlegll") int nlegll, @Param("nlegny") short nlegny,
                     @Param("nlegpg") int nlegpg, @Param("legpln") short legpln, @Param("legcyc") String legcyc,
                     @Param("legcdt") String legcdt, @Param("legfmt") String legfmt, @Param("legsht") short legsht,
                     @Param("legdep") String legdep, @Param("legadd") String legadd, @Param("legzip") String legzip);

    @Insert(" INSERT INTO ACTLGC(" +
            " SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,LEGDAT," +
            " LEGTIM,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            " VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            " THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            " LEGPNY,NLEGPG,PAGLIN,RECSTS,FSTPAG,DAYEND,FILLER," +
            " LEGDEP,SECLBA,LASBAL,ORGID3,DRACCM,CRACCM)" +
            " VALUES(" +
            " #{sysidt},#{orgidt},#{cusidt},#{apcode},#{curcde}," +
            " #{legdat},#{legtim},#{tlrnum},#{vchset},#{setseq}," +
            " #{txnamt},#{actbal},#{valdat},#{rvslbl},#{orgid2}," +
            " #{prdcde},#{crnyer},#{prdseq},#{thrref},#{furinf}," +
            " #{fxrate},#{secccy},#{secpmt},#{secbal},#{legpny}," +
            " #{nlegpg},#{paglin},#{recsts},#{fstpag},#{dayend}," +
            " #{filler},#{legdep},#{seclba},#{lasbal},#{orgid3},0,0)")
    int insertActlgc(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                     @Param("apcode") String apcode, @Param("curcde") String curcde, @Param("legdat") Date legdat,
                     @Param("legtim") String legtim, @Param("tlrnum") String tlrnum, @Param("vchset") short vchset,
                     @Param("setseq") short setseq, @Param("txnamt") long txnamt, @Param("actbal") long actbal,
                     @Param("valdat") Date valdat, @Param("rvslbl") String rvslbl, @Param("orgid2") String orgid2,
                     @Param("prdcde") String prdcde, @Param("crnyer") short crnyer, @Param("prdseq") int prdseq,
                     @Param("thrref") String thrref, @Param("furinf") String furinf, @Param("fxrate") BigDecimal fxrate,
                     @Param("secccy") String secccy, @Param("secpmt") long secpmt, @Param("secbal") long secbal,
                     @Param("legpny") short legpny, @Param("nlegpg") int nlegpg, @Param("paglin") short paglin,
                     @Param("recsts") String recsts, @Param("fstpag") String fstpag, @Param("dayend") String dayend,
                     @Param("filler") String filler, @Param("legdep") String legdep, @Param("seclba") long seclba,
                     @Param("lasbal") long lasbal, @Param("orgid3") String orgid3);
}
