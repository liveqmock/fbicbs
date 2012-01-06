package cbs.batch.ac.delvch.dao;

import org.apache.ibatis.annotations.Delete;

public interface DelvchMapper {

      @Delete("DELETE FROM ACTVTH")
    int deleteActvth();

    @Delete("DELETE FROM ACTTVC")
    int deleteActtvc();

    @Delete("DELETE FROM ACTCTV")
    int deleteActctv();

    @Delete("DELETE FROM TMPVCH")
    int deleteTmpvch();
}
