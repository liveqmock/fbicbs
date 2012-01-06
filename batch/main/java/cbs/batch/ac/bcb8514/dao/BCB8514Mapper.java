package cbs.batch.ac.bcb8514.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface BCB8514Mapper {

    @Update("UPDATE ACTCGL SET DLSBAL = DRBALA,CLSBAL = CRBALA,DRBALA = 0,CRBALA = 0," +
            "  GLDATE = to_date(#{nwkday},'yyyy-MM-dd'),DRAMNT = 0, CRAMNT=0,DRCUNT = 0, CRCUNT=0," +
            "  DAVBAL = 0, CAVBAL=0 WHERE RECTYP = #{rectyp} AND RECSTS = #{recsts}")
    int updateCgl(@Param("nwkday")String nwkday,@Param("rectyp")String rectyp,@Param("recsts")String recsts);
}
