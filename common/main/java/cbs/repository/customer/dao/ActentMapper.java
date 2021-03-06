package cbs.repository.customer.dao;

import cbs.repository.customer.model.Actent;
import cbs.repository.customer.model.ActentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ActentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTENT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTENT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTENT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Actent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTENT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Actent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTENT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Actent> selectByExample(ActentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTENT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Actent record, @Param("example") ActentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTENT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Actent record, @Param("example") ActentExample example);

    @Update("update actent set recsts = #{recsts} where cusidt =#{cusidt}")
    int deleteEntByCusidt(@Param("recsts")String recsts,@Param("cusidt")String cusidt);
}