package cbs.batch.ac.bcb8535.dao;

import cbs.batch.ac.bcb8535.SumAct;
import cbs.repository.account.maininfo.model.Actcgl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-2
 * Time: 12:44:33
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8535Mapper {

    @Select("SELECT ORGIDT, GLCODE, CURCDE, RECTYP, DRAMNT, CRAMNT, DRCUNT, "+
            " CRCUNT, DRBALA,CRBALA  FROM ACTCGL" +
            "  WHERE ACTCGL.RECSTS = #{recsts} AND ACTCGL.RECTYP = #{rectyp}"+
            " ORDER BY ORGIDT, CURCDE, GLCODE")
    List<Actcgl> qryCgl(@Param("recsts")String recsts,@Param("rectyp")String rectyp);

    @Select("SELECT ORGIDT, ACTGLC, CURCDE, " +
            "SUM(DDRAMT) as sumDdramt, SUM(DCRAMT) as sumDcramt," +
            "SUM(DDRCNT) as sumDdrcnt, SUM(DCRCNT) as sumDcrcnt," +
            "SUM(BOKBAL) as sumBokbal, SUM(MDRAMT) as sumMdramt, " +
            "SUM(MCRAMT) as sumMcramt,SUM(MDRCNT) as sumMdrcnt, " +
            "SUM(MCRCNT) as sumMcrcnt,SUM(YDRAMT) as sumYdramt," +
            "SUM(YCRAMT) as sumYcramt,SUM(YDRCNT) as sumYdrcnt, " +
            "SUM(YCRCNT) as sumYcrcnt   FROM ACTACT" +
            " WHERE RECSTS = #{recsts} AND SYSIDT = #{sysidt}" +
            "  GROUP BY ORGIDT, ACTGLC, CURCDE" +
            "  ORDER BY ORGIDT, CURCDE, ACTGLC")
    List<SumAct> qrySumAct(@Param("recsts")String recsts,@Param("sysidt")String sysidt);

    @Select("SELECT DECPOS FROM  ACTCCY  WHERE CURCDE = #{curcde}")
    short selectDecpos(@Param("curcde")String curcde);
}