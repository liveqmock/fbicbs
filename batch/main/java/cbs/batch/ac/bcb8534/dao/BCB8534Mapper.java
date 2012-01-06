package cbs.batch.ac.bcb8534.dao;

import cbs.batch.ac.bcb8532.CglccyBean;
import cbs.batch.ac.bcb8534.CglGlcCcy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-2
 * Time: 10:44:33
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8534Mapper {

    @Select("SELECT   ORGIDT,ACTCGL.GLCODE, ACTCGL.CURCDE, RECTYP,GLDATE,DRAMNT," +
            " CRAMNT,DRBALA,CRBALA,GLCCCY,GLCBAL,GLCOCC,RSVFG1,RSVFG2,RSVFG3,"+
            " RSVFG4,RSVFG5,DECPOS  FROM   ACTCGL,ACTGLC, ACTCCY" +
            " WHERE  ACTCGL.GLCODE = ACTGLC.GLCODE AND  ACTCGL.CURCDE = ACTCCY.CURCDE"+
            "  AND  ACTGLC.RECSTS = #{recsts1}  AND  ACTCCY.RECSTS = #{recsts2}")
    List<CglGlcCcy> qryCglGlcCcyBySts(@Param("recsts1")String recsts1,@Param("recsts2")String recsts2);
    
}