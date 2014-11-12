package cbs.repository.account.maininfo.dao;

import cbs.repository.account.maininfo.model.Actvhh;
import cbs.repository.account.maininfo.model.ActvhhExample;
import cbs.repository.account.maininfo.model.ActvhhKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActvhhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int countByExample(ActvhhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int deleteByExample(ActvhhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int deleteByPrimaryKey(ActvhhKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int insert(Actvhh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int insertSelective(Actvhh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    List<Actvhh> selectByExample(ActvhhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    Actvhh selectByPrimaryKey(ActvhhKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int updateByExampleSelective(@Param("record") Actvhh record, @Param("example") ActvhhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int updateByExample(@Param("record") Actvhh record, @Param("example") ActvhhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int updateByPrimaryKeySelective(Actvhh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTVHH
     *
     * @mbggenerated Wed Nov 12 10:29:21 CST 2014
     */
    int updateByPrimaryKey(Actvhh record);
}