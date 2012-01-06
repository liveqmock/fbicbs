package cbs.batch.ac.bcb8531.dao;

import cbs.batch.ac.bcb8531.SctBean;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actcgl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-1
 * Time: 10:44:33
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8531Mapper {
    @Select("SELECT CRNDAT, NXTDDS, BMMDDS, BSSDDS, BYYDDS, IPYMAK, IPYDAT, NWKDAY," +
            "(CRNDAT - BMMDDS) as workMDate ,(CRNDAT - BSSDDS) as workSDate," +
            "(CRNDAT - BYYDDS) as workYDate" +
            " FROM   ACTSCT WHERE  SCTNUM = #{sctnum}")
    SctBean selectSctByNum(@Param("sctnum")short sctnum);
    
    @Select("SELECT    SYSIDT, ORGIDT, CUSIDT, APCODE, CURCDE, BOKBAL, MAVBAL, YAVBAL, DDRAMT,DCRAMT," +
            " DDRCNT, DCRCNT, DEPNUM, BALLIM, ACTGLC   FROM ACTACT" +
            " WHERE  RECSTS = #{recsts}  AND  ACTSTS = #{actsts}" +
            " ORDER BY ORGIDT,ACTGLC,CURCDE")
    List<Actact> selectActBySts(@Param("recsts")String recsts,@Param("actsts")String actsts);

    @Select("SELECT DRBALA, CRBALA, DRAMNT, CRAMNT, DRCUNT,CRCUNT, GLDATE"+
                 " FROM ACTCGL WHERE  ORGIDT = #{orgidt}  AND  GLCODE = #{glcode}" +
                 " AND  CURCDE = #{curcde} AND  RECTYP = #{rectyp}")
    Actcgl selectCgl(@Param("orgidt")String orgidt,@Param("glcode")String glcode,
                     @Param("curcde")String curcde,@Param("rectyp")String rectyp);

    @Update("UPDATE  ACTCGl SET DRBALA = #{drbala}," +
            " CRBALA = #{crbala}, DRAMNT = #{dramnt}, CRAMNT = #{cramnt}, DRCUNT = #{drcunt}," +
            " CRCUNT = #{crcunt}, CREDAT = #{credat}, GLDATE = #{gldate}, RECSTS = ' ' " +
            " WHERE  ORGIDT = #{orgidt}  AND  GLCODE = #{glcode}" +
            " AND  CURCDE = #{curcde} AND  RECTYP = #{rectyp}")
    int updateCgl(@Param("drbala")long drbala,@Param("crbala")long crbala,@Param("dramnt")long dramnt,
                  @Param("cramnt")long cramnt,@Param("drcunt")long drcunt,@Param("crcunt")long crcunt,
                  @Param("credat")Date credat,@Param("gldate") Date gldate,
                  @Param("orgidt")String orgidt,@Param("glcode")String glcode,
                     @Param("curcde")String curcde,@Param("rectyp")String rectyp);

    @Update("UPDATE  ACTCGl SET DRBALA = #{drbala}," +
            " CRBALA = #{crbala}, DRAMNT = DRAMNT + #{dramnt}, CRAMNT = CRAMNT + #{cramnt}, "+
            "DRCUNT = DRCUNT + #{drcunt}, CRCUNT = CRCUNT + #{crcunt}, "+
            "CREDAT = #{credat}, GLDATE = #{gldate}, RECSTS = ' ' " +
            " WHERE  ORGIDT = #{orgidt}  AND  GLCODE = #{glcode}" +
            " AND  CURCDE = #{curcde} AND  RECTYP = #{rectyp}")
    int updateCglForAdd(@Param("drbala")long drbala,@Param("crbala")long crbala,@Param("dramnt")long dramnt,
                  @Param("cramnt")long cramnt,@Param("drcunt")long drcunt,@Param("crcunt")long crcunt,
                  @Param("credat")Date credat,@Param("gldate") Date gldate,
                  @Param("orgidt")String orgidt,@Param("glcode")String glcode,
                     @Param("curcde")String curcde,@Param("rectyp")String rectyp);
}
