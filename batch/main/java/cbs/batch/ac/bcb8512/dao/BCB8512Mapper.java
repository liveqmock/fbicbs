package cbs.batch.ac.bcb8512.dao;

import cbs.repository.code.model.Actirt;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-10
 * Time: 9:53:27
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8512Mapper {

    @Delete("DELETE FROM ACTCIR")
    int deleteCir();

    @Select("SELECT CURCDE,IRTKD1,IRTKD2,IRTVAL,IRTTRM,MODFLG,TRMUNT,CURFLG,EFFDAT" +
            "  FROM ACTIRT WHERE ACTIRT.CURFLG = #{curflg}")
    List<Actirt> selectIrtsByFlg(@Param("curflg") String curflg);

    @Insert("INSERT INTO ACTCIR (CURCDE,IRTKD1,IRTKD2,CURIRT,EFFDAT,MODFLG,NXTIRT,IRTVAL )" +
                 " VALUES (  #{curcde} ,#{irtkd1} ,#{irtkd2} ,#{nxtval} ,#{effdat} ,#{modflg} ,0 ,#{irtval} )")
    int insertCir(@Param("curcde") String curcde,@Param("irtkd1") String irtkd1,@Param("irtkd2") String irtkd2,
                  @Param("nxtval") BigDecimal nxtval,@Param("effdat") Date effdat,
                  @Param("modflg") String modflg,@Param("irtval") BigDecimal irtval);

    @Select("SELECT (IRTVAL / IRTTRM / #{ditcst}) FROM  ACTIRT " +
            " WHERE  CURCDE = #{curcde}   AND   IRTKD1 = #{irtkd1} " +
            " AND   IRTKD2 = #{irtkd2} AND   EFFDAT = " +
            " ( SELECT MIN(EFFDAT) FROM ACTIRT  WHERE EFFDAT >to_date(#{crndat},'yyyy-MM-dd')" +
            " AND CURCDE = #{curcde}  AND IRTKD1 = #{irtkd1}  AND IRTKD2 = #{irtkd2} )")
    BigDecimal selectNxtirt(@Param("ditcst") int ditcst,@Param("curcde") String curcde,
                            @Param("irtkd1") String irtkd1,@Param("irtkd2") String irtkd2,@Param("crndat") String crndat);

    @Update("UPDATE ACTCIR SET NXTIRT = #{nxtval},CURIRT = #{nxtirt}, MODFLG = 'Y'"+
            "  WHERE  CURCDE = #{curcde}   AND   IRTKD1 = #{irtkd1} " +
            "  AND   IRTKD2 = #{irtkd2}")
    int updateCir(@Param("nxtval") BigDecimal nxtval,@Param("nxtirt") BigDecimal nxtirt,
            @Param("curcde") String curcde,@Param("irtkd1") String irtkd1,@Param("irtkd2") String irtkd2);
}
