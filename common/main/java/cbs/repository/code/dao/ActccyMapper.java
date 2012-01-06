package cbs.repository.code.dao;

import cbs.repository.code.model.Actccy;
import cbs.repository.code.model.ActccyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ActccyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActccyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActccyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByPrimaryKey(String curcde);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Actccy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Actccy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Actccy> selectByExample(ActccyExample example);
    List<Actccy> selectForActact();
    /**
     * createBy: haiyuhuang
     * date:     2011-01-05
     * 日（月）计表*/
    List<Actccy> selectForActcgl(@Param("orgidt") String orgidt,@Param("rectyp") String rectyp);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    Actccy selectByPrimaryKey(String curcde);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Actccy record, @Param("example") ActccyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Actccy record, @Param("example") ActccyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByPrimaryKeySelective(Actccy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByPrimaryKey(Actccy record);

    /*获取年利率值 建立客户信息时用
    * 账户结清时用*/
    double selectByParam(@Param("curcde") String curcde,@Param("irtkd1") String irtkd1,@Param("irtkd2") String irtkd2);

    @Select("select * from actccy")
    List<Actccy> selectAll();
}