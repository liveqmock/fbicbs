package cbs.repository.code.dao;

import cbs.repository.code.model.Acttsc;
import cbs.repository.code.model.ActtscExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActtscMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActtscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActtscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Acttsc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Acttsc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Acttsc> selectByExample(ActtscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Acttsc record, @Param("example") ActtscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTSC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Acttsc record, @Param("example") ActtscExample example);
}