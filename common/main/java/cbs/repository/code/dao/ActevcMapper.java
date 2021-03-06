package cbs.repository.code.dao;

import cbs.repository.code.model.Actevc;
import cbs.repository.code.model.ActevcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActevcMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTEVC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActevcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTEVC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActevcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTEVC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Actevc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTEVC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Actevc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTEVC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Actevc> selectByExample(ActevcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTEVC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Actevc record, @Param("example") ActevcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTEVC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Actevc record, @Param("example") ActevcExample example);
}