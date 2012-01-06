package cbs.batch.ac.bcb8526.dao;

import cbs.batch.ac.bcb8526.PleccyBean;
import cbs.repository.account.maininfo.model.Actact;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-2-27
 * Time: 14:19:48
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8526Mapper {

    @Select("SELECT ACTPLE.ORGIDT, ACTPLE.CUSIDT, ACTPLE.APCODE," +
            "  ACTPLE.CURCDE, SUM(ACTPLE.AVABAL) as sumAvabal, ACTCCY.DECPOS" +
            "  FROM ACTPLE, ACTCCY" +
            "  WHERE ACTPLE.CURCDE = ACTCCY.CURCDE  AND ACTPLE.RECSTS = #{recsts1}" +
            "  AND ACTCCY.RECSTS = #{recsts2} AND ACTPLE.SYSIDT = #{sysidt}" +
            "  GROUP BY ACTPLE.ORGIDT, ACTPLE.CUSIDT, ACTPLE.APCODE, ACTPLE.CURCDE," +
            "  ACTCCY.DECPOS ORDER BY ACTPLE.ORGIDT, ACTPLE.CUSIDT,ACTPLE.APCODE, ACTPLE.CURCDE")
    List<PleccyBean> qryPleccy(@Param("recsts1")String recsts1,@Param("recsts2")String recsts2,@Param("sysidt")String sysidt);

    @Select("SELECT ACTACT.ORGIDT, ACTACT.CUSIDT, ACTACT.APCODE, ACTACT.CURCDE, ACTACT.BOKBAL " +
                  "  FROM ACTACT " +
                  " WHERE ACTACT.RECSTS = #{recsts} AND ACTACT.SYSIDT = #{sysidt}" +
            " AND ACTACT.ACTTYP = #{acttyp} ORDER BY ACTACT.ORGIDT, ACTACT.CUSIDT," +
            "  ACTACT.APCODE, ACTACT.CURCDE")
    List<Actact> qryActByTyp(@Param("recsts")String recsts,@Param("sysidt")String sysidt,@Param("acttyp")String acttyp);
}
