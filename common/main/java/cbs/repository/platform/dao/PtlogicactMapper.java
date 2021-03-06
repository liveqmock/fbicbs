package cbs.repository.platform.dao;

import cbs.repository.platform.model.Ptlogicact;
import cbs.repository.platform.model.PtlogicactExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PtlogicactMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTLOGICACT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTLOGICACT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTLOGICACT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Ptlogicact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTLOGICACT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Ptlogicact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTLOGICACT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Ptlogicact> selectByExample(PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTLOGICACT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Ptlogicact record, @Param("example") PtlogicactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTLOGICACT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Ptlogicact record, @Param("example") PtlogicactExample example);
}