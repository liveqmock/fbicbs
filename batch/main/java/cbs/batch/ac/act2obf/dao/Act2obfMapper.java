package cbs.batch.ac.act2obf.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface Act2obfMapper {

    @Delete("DELETE FROM ACTOBF")
    int deleteActobf();
    
    @Insert("INSERT INTO ACTOBF( SYSIDT, ORGIDT, CUSIDT, APCODE, CURCDE, BOKBAL," +
            "  AVABAL, DIFBAL, CIFBAL, ACTSTS, FRZSTS, REGSTS,RECSTS, OVELIM, OVEEXP, CQEFLG, GLCBAL )" +
            "  SELECT   SYSIDT, ORGIDT, CUSIDT, APCODE, CURCDE, BOKBAL, AVABAL, DIFBAL, CIFBAL, ACTSTS," +
            "   FRZSTS, REGSTS, ACTACT.RECSTS,OVELIM, TO_CHAR(OVEEXP,'YYYYMMDD'), CQEFLG, ACTGLC.GLCBAL" +
            "   FROM   ACTACT,ACTGLC   WHERE   ACTACT.ACTGLC = ACTGLC.GLCODE "+
            "   AND   ACTACT.RECSTS = #{recsts1} AND   ACTGLC.RECSTS = #{recsts2}")
    int insertActobf(@Param("recsts1")String recsts1,@Param("recsts2")String recsts2);
}
