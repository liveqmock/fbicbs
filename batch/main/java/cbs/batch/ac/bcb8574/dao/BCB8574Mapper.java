package cbs.batch.ac.bcb8574.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface BCB8574Mapper {

    @Delete("DELETE  FROM ACTTJF")
    int deleteTjf();

    @Delete("DELETE  FROM ACTTJF   WHERE TJCODE = #{tjcode}  AND RECTYP = 'T' " +
            " AND RECSTS = ' '")
    int deleteTjfByTjcode(@Param("tjcode")String tjcode);

    /*@Update("UPDATE ACTCAL SET DLSBAL = DRBALA,CLSBAL = CRBALA,DRBALA = 0,CRBALA = 0," +
            "  ALDATE = to_date(#{nwkday},'yyyy-MM-dd'),DRAMNT = 0, CRAMNT=0,DRCUNT = 0, CRCUNT=0," +
            "  DAVBAL = 0, CAVBAL=0 WHERE RECTYP = #{rectyp} AND RECSTS = #{recsts}")
    int updateCal(@Param("nwkday")String nwkday,@Param("rectyp")String rectyp_t,@Param("recsts")String recsts_valid);*/
}
