package cbs.repository.platform.dao;

import cbs.repository.platform.model.Ptoper;
import cbs.repository.platform.model.PtoperExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PtoperMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(PtoperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(PtoperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByPrimaryKey(String operid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Ptoper record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Ptoper record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Ptoper> selectByExample(PtoperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    Ptoper selectByPrimaryKey(String operid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Ptoper record, @Param("example") PtoperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Ptoper record, @Param("example") PtoperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByPrimaryKeySelective(Ptoper record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPER
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByPrimaryKey(Ptoper record);
}