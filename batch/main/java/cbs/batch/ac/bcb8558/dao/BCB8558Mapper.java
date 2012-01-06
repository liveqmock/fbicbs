package cbs.batch.ac.bcb8558.dao;

import cbs.batch.ac.bcb8558.ActsctParaBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface BCB8558Mapper {
    
    @Select("SELECT LWKDAY, CRNDAT, NXTDDS, BMMDDS,BSSDDS, BYYDDS, MONMAK, QTRMAK," +
            "  YERMAK,CRNDAT - BMMDDS AS wkBmmdat,CRNDAT - BSSDDS AS wkBssdat," +
            " CRNDAT - BYYDDS AS wkByydat,CRNDAT - LWKDAY -1  AS wkDaysBetween" +
            " FROM   ACTSCT WHERE  SCTNUM = #{sctnum} AND  RECSTS = #{recsts}")
    ActsctParaBean selectActsct(@Param("sctnum")Short sctnum,@Param("recsts")String recsts);

    @Update("UPDATE ACTCGL   SET DAVBAL = DRBALA,   CAVBAL = CRBALA " +
                  " WHERE GLDATE = #{dat} AND RECTYP = #{rectyp} AND RECSTS = #{recsts}")
    int updateCglDavCav(@Param("dat") Date dat,@Param("rectyp")String rectyp,@Param("recsts")String recsts);

    @Update("UPDATE ACTCGL " +
            "  SET DAVBAL = (DLSBAL * #{byydds}+ DRBALA ) / (#{byydds} + 1)," +
            "  CAVBAL = (CLSBAL * #{byydds}+ CRBALA ) / (#{byydds} + 1)" +
            "  WHERE GLDATE = #{dat}" +
            "  AND RECTYP = #{rectyp} AND RECSTS = #{recsts}")
    int updateCglCompute(@Param("byydds") Short byydds,@Param("dat")Date dat,
                         @Param("rectyp")String rectyp,@Param("recsts")String recsts);

    @Update("UPDATE ACTCGL " +
            "  SET DAVBAL = (DAVBAL * #{byydds}+ DAVBAL ) / (#{byydds} + 1)," +
            "  CAVBAL = (CAVBAL * #{byydds}+ CRBALA ) / (#{byydds} + 1)" +
            "  WHERE GLDATE = #{dat}" +
            "  AND RECTYP = #{rectyp} AND RECSTS = #{recsts}")
    int updateCglYerEqu(@Param("byydds") Short byydds,@Param("dat")Date dat,
                         @Param("rectyp")String rectyp,@Param("recsts")String recsts);

        //WHERE GLDATE = TO_DATE(substr(#{dat},1,10),'yyyy-MM-dd')
        @Update("UPDATE ACTCGL " +
            "  SET DAVBAL = (DAVBAL * #{byydds}+ DRBALA ) / (#{byydds} + 1)," +
            "  CAVBAL = (CAVBAL * #{byydds}+ CRBALA ) / (#{byydds} + 1)" +
            "  WHERE GLDATE = #{dat}" +
            "  AND RECTYP = #{rectyp} AND RECSTS = #{recsts}")
    int updateCglMonEqu(@Param("byydds") Short byydds,@Param("dat")Date dat,
                         @Param("rectyp")String rectyp,@Param("recsts")String recsts);
    
    @Update("UPDATE ACTACT SET YAVBAL = BOKBAL")
    int updateActYavUseBok();

    @Update(" UPDATE ACTACT SET YAVBAL = ((BOKBAL - DDRAMT - DCRAMT) * #{byydds}+ BOKBAL ) / (#{byydds} + 1)")
    int updateActYavCompute(@Param("byydds") Short byydds);

    @Update("UPDATE ACTACT SET YAVBAL =  (YAVBAL * #{byydds}  + BOKBAL ) / (#{byydds} + 1)")
    int updateActYavEqu(@Param("byydds") Short byydds);

    @Update("UPDATE ACTACT SET MAVBAL = BOKBAL")
    int updateActMavUseBok();

    @Update(" UPDATE ACTACT SET MAVBAL = ((BOKBAL - DDRAMT - DCRAMT) * #{bMMdds}+ BOKBAL ) / (#{bMMdds} + 1)")
    int updateActMavCompute(@Param("bMMdds") Short bMMdds);

    @Update("UPDATE ACTACT SET MAVBAL =  (MAVBAL * #{bMMdds}  + BOKBAL ) / (#{bMMdds} + 1)")
    int updateActMavEqu(@Param("bMMdds") Short bMMdds);
}
