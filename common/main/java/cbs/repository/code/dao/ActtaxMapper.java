package cbs.repository.code.dao;

import cbs.repository.code.model.Acttax;
import cbs.repository.code.model.ActtaxExample;
import cbs.repository.code.model.ActtaxKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActtaxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActtaxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActtaxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByPrimaryKey(ActtaxKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Acttax record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Acttax record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Acttax> selectByExample(ActtaxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    Acttax selectByPrimaryKey(ActtaxKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Acttax record, @Param("example") ActtaxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Acttax record, @Param("example") ActtaxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByPrimaryKeySelective(Acttax record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAX
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByPrimaryKey(Acttax record);
}