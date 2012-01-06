package cbs.batch.ac.bcb85519.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface BCB85519Mapper {
    @Update("UPDATE ACTSBL  SET  NSTMPG  =  NSTMPG + 1,  STMPLN  =  1"
            +"  WHERE SYSIDT = #{sysidt} AND RECSTS = #{recsts} AND STMPLN <> 1")
    int updateSblNextPageMsg(@Param("sysidt")String sysidt,@Param("recsts")String recsts);

    @Update("UPDATE (SELECT SBL.CUSIDT,SBL.STMCYC,SBL.STMFMT,OAC.STMCYC AS OAC_STMCYC,"
                +"OAC.STMFMT AS OAC_STMFMT,SBL.FSTPAG,SBL.RECSTS  FROM ACTSBL SBL JOIN ACTOAC OAC "
            +" ON OAC.ORGIDT = SBL.ORGIDT AND OAC.APCODE = SBL.APCODE AND OAC.CURCDE = SBL.CURCDE "
            +"AND OAC.CUSIDT = SBL.CUSIDT  WHERE SBL.SYSIDT = #{sysidt}  AND SBL.RECSTS = #{recsts} " +
            "  )SET STMCYC = OAC_STMCYC,STMFMT = OAC_STMFMT,FSTPAG='Y',RECSTS=' '")
    int updateSblMsg(@Param("sysidt")String sysidt,@Param("recsts")String recsts);
    
    @Update("UPDATE ACTLBL  SET   NLEGPG  =  NLEGPG + 1,  LEGPLN  =  1  WHERE SYSIDT = #{sysidt}" +
            "                      AND RECSTS = #{recsts} AND LEGPLN <> 1")
    int updateLblNextPageMsg(@Param("sysidt")String sysidt,@Param("recsts")String recsts);

    @Update("UPDATE " +
            "       (SELECT LBL.CUSIDT,LBL.LEGCYC,LBL.LEGFMT,OAC.LEGCYC AS OAC_LEGCYC,OAC.LEGFMT AS OAC_LEGFMT,LBL.FSTPAG,LBL.RECSTS \n" +
            "        FROM ACTLBL LBL   JOIN ACTOAC OAC   ON OAC.ORGIDT = LBL.ORGIDT AND OAC.APCODE = LBL.APCODE  AND OAC.CURCDE = LBL.CURCDE  AND OAC.CUSIDT = LBL.CUSIDT" +
            "        WHERE LBL.SYSIDT = #{sysidt}  AND LBL.RECSTS = #{recsts})" +
            "       SET LEGCYC = OAC_LEGCYC,LEGFMT = OAC_LEGFMT,FSTPAG='Y',RECSTS=' '")
    int updateLblMsg(@Param("sysidt")String sysidt,@Param("recsts")String recsts);
}
