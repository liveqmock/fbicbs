package cbs.repository.code.dao;

import cbs.repository.code.model.Acttbc;
import cbs.repository.code.model.ActtbcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActtbcMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTBC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActtbcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTBC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActtbcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTBC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Acttbc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTBC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Acttbc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTBC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Acttbc> selectByExample(ActtbcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTBC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Acttbc record, @Param("example") ActtbcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTBC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Acttbc record, @Param("example") ActtbcExample example);
}