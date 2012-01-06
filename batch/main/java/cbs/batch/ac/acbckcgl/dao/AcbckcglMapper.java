package cbs.batch.ac.acbckcgl.dao;

import cbs.batch.ac.acbckcgl.CglGlc;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actcgl;
import cbs.repository.account.tempinfo.model.Actcir;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


public interface AcbckcglMapper {

    @Select("SELECT ORGIDT,ACTGLC.GLCODE,CURCDE,RECTYP,GLDATE,DLSBAL,CLSBAL,DRAMNT,CRAMNT,DRBALA,CRBALA" +
            " FROM ACTCGL,ACTGLC WHERE ACTGLC.GLCODE = ACTCGL.GLCODE AND ACTGLC.GLCCAT = #{glccat}" +
            " AND ACTGLC.GLCODE LIKE #{likeGlcode}  AND ACTGLC.RECSTS = #{recsts1} AND ACTCGL.RECSTS = #{recsts2} " +
            " AND ACTCGL.RECTYP = #{rectyp1}" +
            " UNION" +
            " SELECT ORGIDT,GLCODE, CURCDE,RECTYP,GLDATE,DLSBAL,CLSBAL,  DRAMNT,CRAMNT,DRBALA,CRBALA" +
            " FROM ACTCGL  WHERE GLCODE = #{glcode}  AND RECSTS = #{recsts3} AND RECTYP = #{rectyp2}")
    List<CglGlc> qryCgls(@Param("glccat")String glccat,@Param("likeGlcode")String likeGlcode,@Param("recsts1")String recsts1,
                                     @Param("recsts2")String recsts2,@Param("rectyp1")String rectyp1,@Param("glcode")String glcode,
                         @Param("recsts3")String recsts3,@Param("rectyp2")String rectyp2);

     @Select("SELECT DLSBAL,CLSBAL,DRAMNT,CRAMNT,DRBALA,CRBALA" +
                  "FROM ACTCGL WHERE ORGIDT = #{orgidt} AND GLCODE = #{glcode}" +
                  "AND CURCDE = #{curcde} AND RECTYP = #{rectyp}   AND GLDATE = #{gldate}")
     Actcgl qryUniqueCgl(@Param("orgidt")String orgidt,@Param("glcode")String glcode,@Param("curcde")String curcde,
                         @Param("rectyp")String rectyp,@Param("gldate") Date gldate);

    @Select("SELECT  ORGNAM  FROM  ACTORG   WHERE  ORGIDT = #{orgidt}")
    String qryOrgnam(@Param("orgidt")String orgidt);
}