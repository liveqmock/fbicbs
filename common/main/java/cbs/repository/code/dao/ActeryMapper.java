package cbs.repository.code.dao;

import cbs.repository.code.model.Actery;
import cbs.repository.code.model.ActeryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActeryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTERY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActeryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTERY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActeryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTERY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Actery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTERY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Actery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTERY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Actery> selectByExample(ActeryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTERY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Actery record, @Param("example") ActeryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTERY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Actery record, @Param("example") ActeryExample example);
}