package cbs.repository.account.maininfo.dao;

import cbs.repository.account.maininfo.model.Actalb;
import cbs.repository.account.maininfo.model.ActalbExample;
import cbs.repository.account.maininfo.model.ActalbKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActalbMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int countByExample(ActalbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int deleteByExample(ActalbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int deleteByPrimaryKey(ActalbKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int insert(Actalb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int insertSelective(Actalb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    List<Actalb> selectByExample(ActalbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    Actalb selectByPrimaryKey(ActalbKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int updateByExampleSelective(@Param("record") Actalb record, @Param("example") ActalbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int updateByExample(@Param("record") Actalb record, @Param("example") ActalbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int updateByPrimaryKeySelective(Actalb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTALB
     *
     * @mbggenerated Sun Nov 21 21:36:05 CST 2010
     */
    int updateByPrimaryKey(Actalb record);
}