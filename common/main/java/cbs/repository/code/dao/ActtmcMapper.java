package cbs.repository.code.dao;

import cbs.repository.code.model.Acttmc;
import cbs.repository.code.model.ActtmcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActtmcMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTMC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActtmcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTMC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActtmcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTMC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Acttmc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTMC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Acttmc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTMC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Acttmc> selectByExample(ActtmcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTMC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Acttmc record, @Param("example") ActtmcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTMC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Acttmc record, @Param("example") ActtmcExample example);
}