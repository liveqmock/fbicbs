package cbs.batch.ac.getirt.dao;

import cbs.repository.code.model.Actirt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface GetirtMapper {

    @Update("UPDATE ACTIRT SET CURFLG = '0'")
    int updateIrtCurflg();

    @Select("SELECT CURCDE, IRTKD1, IRTKD2, IRTVAL ,EFFDAT  FROM   ACTIRT" +
            "                WHERE RECSTS = #{recsts} AND EFFDAT <= #{crndat}" +
            "                ORDER BY CURCDE,IRTKD1,IRTKD2,EFFDAT")
    List<Actirt> qryIrts(@Param("recsts")String recsts,@Param("crndat")Date crndat);

    @Update("UPDATE ACTIRT SET CURFLG = '1'  " +
            "                  WHERE CURCDE =#{curcde} AND IRTKD1=#{irtkd1} " +
            "                   AND  IRTKD2 =#{irtkd2} " +
            "                   AND  EFFDAT=#{effdat}")
    int updateIrtCurflgTo1(@Param("curcde")String curcde,@Param("irtkd1")String irtkd1,
                           @Param("irtkd2")String irtkd2,@Param("effdat")Date effdat);
}
