package cbs.batch.ac.bcb8521.dao;

import cbs.repository.code.model.Actccy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-24
 * Time: 16:28:57
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8521Mapper {
    /**
     * 获取actccy表的DECPOS(辅币位数)*/
    @Select(" SELECT DECPOS FROM   ACTCCY WHERE  CURCDE = #{curcde}")
    Actccy selectActccyByPK(@Param("curcde") String curcde);
}
