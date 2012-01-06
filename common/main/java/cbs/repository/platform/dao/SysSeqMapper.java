package cbs.repository.platform.dao;

import cbs.repository.platform.model.SysSeq;
import cbs.repository.platform.model.SysSeqExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysSeqMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(SysSeqExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(SysSeqExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(SysSeq record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(SysSeq record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<SysSeq> selectByExample(SysSeqExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") SysSeq record, @Param("example") SysSeqExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") SysSeq record, @Param("example") SysSeqExample example);
}