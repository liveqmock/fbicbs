package cbs.batch.ac.cbs0001.dao;

import org.apache.ibatis.annotations.Select;

public interface CBS0001Mapper {

    @Select("SELECT SUM(TXNAMT) FROM ACTVCH WHERE RECSTS=' '")
    String qrySumAmt();
}
