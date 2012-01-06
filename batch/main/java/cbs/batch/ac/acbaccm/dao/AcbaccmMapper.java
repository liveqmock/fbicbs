package cbs.batch.ac.acbaccm.dao;

import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.tempinfo.model.Actcir;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;


public interface AcbaccmMapper {

    @Select("SELECT  SYSIDT,ORGIDT, CUSIDT,APCODE,CURCDE, ACTGLC, ACTNAM,AVABAL," +
            " DINRAT,CINRAT, INTTRA,DEPNUM,CACINT,DACINT, CRATSF,DRATSF," +
            " INTFLG,INTCYC, DRACCM,CRACCM,LINTDT,LINDTH,OPNDAT" +
            "  FROM ACTACT WHERE (INTFLG = #{flg1}  OR  INTFLG = #{flg2} )" +
            "  AND (DRACCM <> 0 OR CRACCM <>0) AND (ACTGLC <> '8272')" +
            "  AND  APCODE <> '9999' AND  RECSTS =  #{recsts}" +
            "  ORDER BY ORGIDT,CURCDE,ACTGLC,APCODE,CUSIDT")
    List<Actact> selectActs(@Param("flg1")String flg1,@Param("flg2")String flg2,@Param("recsts")String recsts);

    @Select("SELECT ORGNAM  FROM  ACTORG  WHERE  ORGIDT = #{orgidt}")
    String selectOrgnamByIdt(@Param("orgidt")String orgidt);

    @Select("SELECT DECPOS FROM ACTCCY WHERE CURCDE = #{curcde}")
    int selectPosByCde(@Param("curcde")String curcde);

    @Select("SELECT   CURIRT,IRTVAL  FROM    ACTCIR" +
            " WHERE   CURCDE = #{curcde}  AND   IRTKD1 = #{irtkd1} " +
            " AND IRTKD2 = #{irtkd2}")
    Actcir selectCirByIrtkd(@Param("curcde")String curcde,@Param("irtkd1")String irtkd1,@Param("irtkd2")String irtkd2);

    @Select("SELECT NVL(MAX(YINTSQ),0)   FROM   ACTCIT  WHERE  SYSIDT = #{sysidt} AND " +
            " ORGIDT = #{orgidt} AND CUSIDT = #{cusidt} AND   APCODE = #{apcode} AND" +
            "  CURCDE = #{curcde} AND TO_CHAR(IPYDAT,'YYYY') = #{year}")
    int selectmaxYintsq(@Param("sysidt")String sysidt,@Param("orgidt")String orgidt,@Param("cusidt")String cusidt,
    @Param("apcode")String apcode,@Param("curcde")String curcde,@Param("year")String year);
}