package cbs.repository.code.dao;

import cbs.repository.code.model.Actorg;
import cbs.repository.code.model.ActorgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActorgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTORG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int countByExample(ActorgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTORG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int deleteByExample(ActorgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTORG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insert(Actorg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTORG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int insertSelective(Actorg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTORG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    List<Actorg> selectByExample(ActorgExample example);
    /**
     * This method was created by haiyuhuang
     * date:2010-12-02*/
    List<Actorg> selectForActact();
     /**
     * This method was created by haiyuhuang
     * date:2010-12-13*/
    List<Actorg> selectForActvch();

    /**
     * createBy: haiyuhuang
     * date:     2010-12-15*/
    List<Actorg> selectForActcgl();

    /**
     * createBy: haiyuhuang
     * date:     2011-03-24*/
    List<Actorg> selectForActlgc();

    /**
     * createBy: haiyuhuang
     * date:     2010-12-21*/
    List<Actorg> selectForActglf();

    /**
     * createBy: haiyuhuang
     * date:     2011-03-24*/
    List<Actorg> selectForActnsm();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTORG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExampleSelective(@Param("record") Actorg record, @Param("example") ActorgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTORG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    int updateByExample(@Param("record") Actorg record, @Param("example") ActorgExample example);
}