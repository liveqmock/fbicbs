package cbs.batch.ac.bcb8533.dao;

import cbs.batch.ac.bcb8532.CglccyBean;
import cbs.batch.ac.bcb8533.CglccyProp;
import cbs.repository.code.model.Actsct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-1
 * Time: 10:44:33
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8533Mapper {

   @Select("SELECT   ACTCGL.ORGIDT,ACTCGL.GLCODE,ACTCGL.CURCDE," +
           " ACTCGL.DRAMNT,ACTCGL.CRAMNT,ACTCGL.DRBALA," +
           " ACTCGL.CRBALA,ACTCGL.DLSBAL,ACTCGL.CLSBAL," +
           "  ACTCCY.DECPOS,ACTCGL.RECTYP" +
            " FROM   ACTCGL,ACTCCY  WHERE  ACTCGL.RECSTS = #{recsts1}"+
            " AND   ACTCCY.RECSTS = #{recsts2} AND  ACTCCY.CURCDE = ACTCGL.CURCDE" +
            " AND  RECTYP=#{rectyp} ORDER BY ACTCGL.CURCDE,ACTCGL.GLCODE")
    List<CglccyProp> selectCglccy(@Param("recsts1")String recsts1,@Param("recsts2")String recsts2,@Param("rectyp")String rectyp);
}