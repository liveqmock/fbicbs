package cbs.batch.ac.bcb8564.dao;

import cbs.batch.ac.bcb8564.SctDat;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface BCB8564Mapper {

    @Select("SELECT CRNDAT, NWKDAY,CRNDAT - BMMDDS as mDate, CRNDAT - BSSDDS as sDate " +
            ",CRNDAT - BYYDDS  as yDate FROM   ACTSCT WHERE  SCTNUM = #{sctnum}")
    SctDat qryDatBySctnum(@Param("sctnum")short sctnum);
    
     @Delete("DELETE FROM ACTGLF WHERE  RECTYP = #{rectyp} AND GLDATE = #{date}")
     int delGlf(@Param("rectyp")String rectyp,@Param("date") Date date);

    @Insert("INSERT INTO ACTGLF (ORGIDT,GLCODE,CURCDE,RECTYP,GLDATE," +
            " DRAMNT,CRAMNT,DRCUNT,CRCUNT,DRBALA, CRBALA,DAVBAL,CAVBAL,CREDAT,RECSTS)" +
            "  SELECT ORGIDT,GLCODE,CURCDE,RECTYP,GLDATE,  DRAMNT,CRAMNT,DRCUNT,CRCUNT,DRBALA," +
            "  CRBALA,DAVBAL,CAVBAL,CREDAT,RECSTS  FROM ACTCGL")
    int insertCglToGlf();
}