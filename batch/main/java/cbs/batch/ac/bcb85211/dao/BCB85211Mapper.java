package cbs.batch.ac.bcb85211.dao;

import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actobf;
import cbs.repository.code.model.Actccy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-24
 * Time: 13:24:52
 * remark: 核对 ACT 与 OBF
 * To change this template use File | Settings | File Templates.
 */
public interface BCB85211Mapper {
    /**
     * 获取actact表的数据*/
    @Select(" SELECT SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,BOKBAL," +
            "       AVABAL,CIFBAL,DIFBAL,ACTSTS,FRZSTS," +
            "       OVELIM,OVEEXP" +
            " FROM ACTACT" +
            " WHERE SYSIDT = #{sysidt}" +
            "    AND RECSTS = #{recsts}" +
            "    AND ACTSTS = #{actsts}" +
            " ORDER BY SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE")
    List<Actact> selectActact(@Param("sysidt") String sysidt,@Param("recsts") String recsts,@Param("actsts") String actsts);

    /**
     * 获取actccy表的DECPOS(辅币位数)*/
    @Select(" SELECT DECPOS FROM   ACTCCY WHERE  CURCDE = #{curcde}")
    Actccy selectActccyByPK(@Param("curcde") String curcde);

    /**
     * 获取actobf一条记录*/
    @Select(" SELECT * FROM ACTOBF T WHERE T.SYSIDT = #{sysidt}" +
            " AND T.ORGIDT = #{orgidt}" +
            " AND T.CUSIDT = #{cusidt}" +
            " AND T.APCODE = #{apcode}" +
            " AND T.CURCDE = #{curcde}")
    Actobf selectActobfByPK(@Param("sysidt") String sysidt,@Param("orgidt") String orgidt,@Param("cusidt") String cusidt,
                                  @Param("apcode") String apcode,@Param("curcde") String curcde);
}
