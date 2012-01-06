package cbs.batch.ac.bcb8554.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-3
 * Time: 16:01:14
 * To change this template use File | Settings | File Templates.
 */
public interface BCB8554Mapper {

    @Insert(" INSERT INTO ACTFGL" +
            "   (ORGIDT,GLCODE,CURCDE,RECTYP,GLDATE,DRAMNT,CRAMNT," +
            "   DRCUNT,CRCUNT,DRBALA,CRBALA,DLSBAL,CLSBAL,DAVBAL," +
            "   CAVBAL,RECSTS)" +
            " SELECT ORGIDT,GLCGRP,CURCDE,RECTYP,GLDATE," +
            "   SUM(DRAMNT),SUM(CRAMNT)," +
            "   SUM(DRCUNT),SUM(CRCUNT),SUM(DRBALA),SUM(CRBALA)," +
            "   SUM(DLSBAL),SUM(CLSBAL),SUM(DAVBAL),SUM(CAVBAL)," +
            "   ACTCGL.RECSTS" +
            " FROM ACTCGL,ACTGLC" +
            " WHERE ACTCGL.GLCODE=ACTGLC.GLCODE AND ACTCGL.RECSTS=' '" +
            " GROUP BY ORGIDT,GLCGRP,CURCDE,RECTYP,GLDATE,ACTCGL.RECSTS")
    int insertActfgl();

    @Delete(" DELETE FROM ACTFGL")
    int deleteActfgl();
}
