package cbs.batch.ac.bcb8513.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface BCB8513Mapper {

    @Update("UPDATE ACTACT  SET DDRAMT = 0,DCRAMT = 0,"
               +" DDRCNT = 0,DCRCNT = 0,DACINT = 0,CACINT = 0")
    int updateActDdr();

    /*@Update("UPDATE ACTFXE  SET DDRAMT = 0,DCRAMT = 0,"
               +" DDRCNT = 0,DCRCNT = 0")
    int updateFxeDdr();*/

    @Update("UPDATE ACTPLE  SET DDRAMT = 0,DCRAMT = 0,"
               +" DDRCNT = 0,DCRCNT = 0")
    int updatePleDdr();

    @Update("UPDATE ACTACT  SET MDRAMT = 0,MCRAMT = 0,"
               +" MAVBAL = 0,MDRCNT = 0,MCRCNT = 0")
    int updateActMdr();

    /*@Update("UPDATE ACTFXE  SET MDRAMT = 0,MCRAMT = 0,"
               +"MAVBAL = 0,MDRCNT = 0,MCRCNT = 0")
    int updateFxeMdr();*/

    @Update("UPDATE ACTPLE  SET MDRAMT = 0,MCRAMT = 0,"
               +"MAVBAL = 0,MDRCNT = 0,MCRCNT = 0")
    int updatePleMdr();

    @Update("UPDATE ACTACT  SET YDRAMT = 0,YCRAMT = 0,"
               +"YAVBAL = 0,YDRCNT = 0,YCRCNT = 0")
    int updateActYdr();

    /*@Update("UPDATE ACTFXE  SET YDRAMT = 0,YCRAMT = 0,"
               +"YAVBAL = 0,YDRCNT = 0,YCRCNT = 0")
    int updateFxeYdr();*/

    @Update("UPDATE ACTPLE  SET YDRAMT = 0,YCRAMT = 0,"
               +"YAVBAL = 0,YDRCNT = 0,YCRCNT = 0")
    int updatePleYdr();
}
