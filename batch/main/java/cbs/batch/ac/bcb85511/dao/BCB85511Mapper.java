package cbs.batch.ac.bcb85511.dao;

import cbs.batch.ac.bcb85511.model.ActsctForDate;
import cbs.repository.account.billinfo.model.Actsbl;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.account.maininfo.model.Actvth;
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
 * Date: 2011-2-25
 * Time: 15:05:01
 * To change this template use File | Settings | File Templates.
 */
public interface BCB85511Mapper {
    /*
    * 获取ACTSCT系统控制表数据*/

    @Select(" SELECT TO_CHAR(CRNDAT,'YYYY') AS crndatYear,TO_CHAR(LWKDAY,'YYYY') AS lwkdayYear," +
            " YERMAK, CRNDAT, TDYMAK,QTRMAK" +
            " FROM   ACTSCT WHERE  SCTNUM = #{sctnum}")
    ActsctForDate selectActsctByPK(@Param("sctnum") short sctnum);

    /*
    * 更新ACTSBL下次出帐数据字段*/

    @Update(" UPDATE ACTSBL" +
            " SET NSTMNY = #{nstmny}," +
            "    NSTMPG = 1," +
            "    STMPLN = 1," +
            "    FSTPAG = 'Y'" +
            " WHERE  STMFMT = 'C'" +
            "    OR  STMFMT = 'S'")
    int updateActsblForNst(@Param("nstmny") short nstmny);

    /*
    * 获取actvth表所有数据*/

    @Select(" SELECT ORGID3,CUSIDT,APCODE,CURCDE,TXNAMT," +
            " TLRNUM,VCHSET,SETSEQ,ORGID2,PRDCDE," +
            " VALDAT,RVSLBL,ERYDAT,(ANACDE||' '||FURINF) AS FURINF,FXRATE," +
            " CRNYER,PRDSEQ," +
            " (CASE WHEN ANACDE='114' THEN (CASE WHEN TXNAMT<0 THEN '利息支出' ELSE '利息收入' END)" +
            "                    ELSE (" +
            "                      CASE WHEN RVSLBL='*' THEN '冲正'||to_char(VALDAT,'yyMMdd')" +
            "                        WHEN RVSLBL='T' THEN (CASE WHEN TXNAMT<0 THEN '转账借' ELSE '转账贷' END)" +
            "                        WHEN RVSLBL='C' THEN (CASE WHEN TXNAMT<0 THEN '现金借' ELSE '现金贷' END)" +
            "                        WHEN RVSLBL='B' THEN '补账'||to_char(VALDAT,'yyMMdd')" +
            "                        ELSE '其它' END" +
            "                        ) END" +
            "               ) AS THRREF," +
            "SECCCY,SECAMT," +
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
    * 更新ACTNSM數據*/

    @Update(" UPDATE ACTNSM SET" +
            " DRACCM = #{draccm},CRACCM = #{craccm},DAYEND = 'Y'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND NSTMPG = #{nstmpg}" +
            "    AND PAGLIN = #{paglin}")
    int updateActnsmByActact(@Param("draccm") long draccm, @Param("craccm") long craccm, @Param("sysidt") String sysidt,
                             @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                             @Param("curcde") String curcde, @Param("nstmpg") int nstmpg, @Param("paglin") short paglin);

    /*
    *更新更新ACTNSM數據 RECSTS = 'P' */

    @Update(" UPDATE ACTNSM SET" +
            " RECSTS = 'P'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND NSTMPG <= #{nstmpg}")
    int updateActnsmRecsts(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde, @Param("nstmpg") int nstmpg);

    /*
    * 更新ACTNSM數據 FSTPAG = 'Y'*/

    @Update(" UPDATE ACTNSM SET" +
            " FSTPAG = 'Y'" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND NSTMPG = #{nstmpg}" +
            "    AND RECSTS = ' '")
    int updateActnsmFstpag(@Param("sysidt") String sysidt,
                           @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                           @Param("curcde") String curcde, @Param("nstmpg") int nstmpg);

    /*
    * 更新ACTSBL*/

    @Update(" UPDATE ACTSBL" +
            " SET STMBAL = #{stmbal}," +
            "     SECSBL = 0," +
            "     NSTMLL = 0," +
            "     NSTMNY = #{nstmny}," +
            "     NSTMPG = #{nstmpg}," +
            "     STMPLN = #{stmpln}," +
            "     FSTPAG = #{fstpag}" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}" +
            "    AND (STMFMT = 'C' OR STMFMT = 'S')")
    int updateLastActsbl(@Param("stmbal") long stmbal, @Param("nstmny") short nstmny, @Param("nstmpg") int nstmpg,
                         @Param("stmpln") short stmpln, @Param("fstpag") String fstpag, @Param("sysidt") String sysidt,
                         @Param("orgidt") String orgidt, @Param("cusidt") String cusidt, @Param("apcode") String apcode,
                         @Param("curcde") String curcde);

    /*
    * 讀取ACTSBL*/

    @Select(" SELECT  STMFMT,     STMDEP,       SYSIDT," +
            " ORGIDT,     CUSIDT,       APCODE," +
            " CURCDE,     SECCCY,       STMCYC," +
            " NSTMNY,     NSTMPG,       STMPLN," +
            " FSTPAG,     STMBAL,       SECSBL," +
            " RECSTS" +
            " FROM ACTSBL" +
            " WHERE SYSIDT = #{sysidt}" +
            "   AND ORGIDT = #{orgidt}" +
            "   AND CUSIDT = #{cusidt}" +
            "   AND APCODE = #{apcode}" +
            "   AND CURCDE = #{curcde}" +
            "   AND RECSTS <> #{recsts}")
    Actsbl selectsblByPKandRecsts(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                                  @Param("apcode") String apcode, @Param("curcde") String curcde, @Param("recsts") String recsts);

    /*
    * 讀取ACTOAC數據*/

    @Select(" SELECT STMCYC,STMCDT,STMFMT," +
            " STMADD,STMZIP,STMSHT,STMDEP,ACTNAM" +
            " FROM ACTOAC" +
            " WHERE ORGIDT = #{orgidt}" +
            "    AND CUSIDT = #{cusidt}" +
            "    AND APCODE = #{apcode}" +
            "    AND CURCDE = #{curcde}")
    Actoac selectOacByPK(@Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                         @Param("apcode") String apcode, @Param("curcde") String curcde);

    /*
   * 插入ACTSBL*/

    @Insert(" INSERT INTO ACTSBL" +
            " (SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,SECCCY," +
            " OLDACN,ACTNAM,STMBAL,SECSBL,NSTMLL,NSTMNY," +
            " NSTMPG,STMPLN,STMCYC,STMCDT,STMFMT," +
            " STMSHT,STMDEP,STMADD,STMZIP,FSTPAG,RECSTS)" +
            " VALUES" +
            " (#{sysidt},#{orgidt},#{cusidt},#{apcode},#{curcde}," +
            "  #{secccy},#{oldacn},#{actnam},#{stmbal},#{secsbl}," +
            "  #{nstmll},#{nstmny},#{nstmpg},#{stmpln},#{stmcyc}," +
            "  #{stmcdt},#{stmfmt},#{stmsht},#{stmdep},#{stmadd}," +
            "  #{stmzip},'Y',' ')")
    int insertActsbl(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                     @Param("apcode") String apcode, @Param("curcde") String curcde, @Param("secccy") String secccy,
                     @Param("oldacn") String oldacn, @Param("actnam") String actnam, @Param("stmbal") long stmbal,
                     @Param("secsbl") long secsbl, @Param("nstmll") int nstmll, @Param("nstmny") short nstmny,
                     @Param("nstmpg") int nstmpg, @Param("stmpln") short stmpln, @Param("stmcyc") String stmcyc,
                     @Param("stmcdt") String stmcdt, @Param("stmfmt") String stmfmt, @Param("stmsht") short stmsht,
                     @Param("stmdep") String stmdep, @Param("stmadd") String stmadd, @Param("stmzip") String stmzip);

    /*
    * 插入ACTNSM
    * */

    @Insert(" INSERT INTO ACTNSM" +
            " (SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,STMDAT," +
            "  STMTIM,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            "  VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            "  THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "  STMPNY,NSTMPG,PAGLIN,RECSTS,FSTPAG,DAYEND,FILLER," +
            "  STMDEP,SECLBA,LASBAL,ORGID3,DRACCM,CRACCM)" +
            " VALUES (" +
            "  #{sysidt},#{orgidt},#{cusidt},#{apcode},#{curcde}," +
            "  #{stmdat},#{stmtim},#{tlrnum},#{vchset},#{setseq}," +
            "  #{txnamt},#{actbal},#{valdat},#{rvslbl},#{orgid2}," +
            "  #{prdcde},#{crnyer},#{prdseq},#{thrref},#{furinf}," +
            "  #{fxrate},#{secccy},#{secpmt},#{secbal},#{stmpny}," +
            "  #{nstmpg},#{paglin},#{recsts},#{fstpag},#{dayend}," +
            "  #{filler},#{stmdep},#{seclba},#{lasbal},#{orgid3},0,0" +
            " )")
    int insertActnsm(@Param("sysidt") String sysidt, @Param("orgidt") String orgidt, @Param("cusidt") String cusidt,
                     @Param("apcode") String apcode, @Param("curcde") String curcde, @Param("stmdat") Date stmdat,
                     @Param("stmtim") String stmtim, @Param("tlrnum") String tlrnum, @Param("vchset") short vchset,
                     @Param("setseq") short setseq, @Param("txnamt") long txnamt, @Param("actbal") long actbal,
                     @Param("valdat") Date valdat, @Param("rvslbl") String rvslbl, @Param("orgid2") String orgid2,
                     @Param("prdcde") String prdcde, @Param("crnyer") short crnyer, @Param("prdseq") int prdseq,
                     @Param("thrref") String thrref, @Param("furinf") String furinf, @Param("fxrate") BigDecimal fxrate,
                     @Param("secccy") String secccy, @Param("secpmt") long secpmt, @Param("secbal") long secbal,
                     @Param("stmpny") short stmpny, @Param("nstmpg") int nstmpg, @Param("paglin") short paglin,
                     @Param("recsts") String recsts, @Param("fstpag") String fstpag, @Param("dayend") String dayend,
                     @Param("filler") String filler, @Param("stmdep") String stmdep, @Param("seclba") long seclba,
                     @Param("lasbal") long lasbal, @Param("orgid3") String orgid3);
}