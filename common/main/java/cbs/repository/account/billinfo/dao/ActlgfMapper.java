package cbs.repository.account.billinfo.dao;

import cbs.repository.account.billinfo.model.Actlgf;
import cbs.repository.account.billinfo.model.ActlgfExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActlgfMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTLGF
     *
     * @mbggenerated Wed Mar 02 17:20:04 CST 2011
     */
    int countByExample(ActlgfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTLGF
     *
     * @mbggenerated Wed Mar 02 17:20:04 CST 2011
     */
    int deleteByExample(ActlgfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTLGF
     *
     * @mbggenerated Wed Mar 02 17:20:04 CST 2011
     */
    int insert(Actlgf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTLGF
     *
     * @mbggenerated Wed Mar 02 17:20:04 CST 2011
     */
    int insertSelective(Actlgf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTLGF
     *
     * @mbggenerated Wed Mar 02 17:20:04 CST 2011
     */
    List<Actlgf> selectByExample(ActlgfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTLGF
     *
     * @mbggenerated Wed Mar 02 17:20:04 CST 2011
     */
    int updateByExampleSelective(@Param("record") Actlgf record, @Param("example") ActlgfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTLGF
     *
     * @mbggenerated Wed Mar 02 17:20:04 CST 2011
     */
    int updateByExample(@Param("record") Actlgf record, @Param("example") ActlgfExample example);
}