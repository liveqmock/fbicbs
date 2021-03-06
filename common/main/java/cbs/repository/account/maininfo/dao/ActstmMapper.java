package cbs.repository.account.maininfo.dao;

import cbs.repository.account.ext.domain.ActstmModel;
import cbs.repository.account.maininfo.model.Actstm;
import cbs.repository.account.maininfo.model.ActstmExample;
import cbs.repository.account.maininfo.model.ActstmForActnum;
import cbs.repository.account.maininfo.model.ActstmForStmCotent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActstmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTSTM
     *
     * @mbggenerated Mon Nov 29 09:51:25 CST 2010
     */
    int countByExample(ActstmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTSTM
     *
     * @mbggenerated Mon Nov 29 09:51:25 CST 2010
     */
    int deleteByExample(ActstmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTSTM
     *
     * @mbggenerated Mon Nov 29 09:51:25 CST 2010
     */
    int insert(Actstm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTSTM
     *
     * @mbggenerated Mon Nov 29 09:51:25 CST 2010
     */
    int insertSelective(Actstm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTSTM
     *
     * @mbggenerated Mon Nov 29 09:51:25 CST 2010
     */
    List<Actstm> selectByExample(ActstmExample example);

    /**
     * createBy: haiyuhuang
     * date:     2010-12-16
     * 查询账号基本信息*/
    List<ActstmForActnum> selectForActnum(@Param("orgidt") String orgidt);

    /**
     * createBy: haiyuhuang
     * date:     2011-04-22
     * 查询账号基本信息(按条件出账)*/

     List<ActstmForActnum> selectForActnum2(@Param("oldacn") String oldacn);
    /**
         * createBy: haiyuhuang
         * date:     2011-04-22
         * 查询账号基本信息(按条件出账)*/
        List<ActstmForStmCotent> selectForStmContent2(@Param("sysidt") String sysidt,@Param("orgidt") String orgidt
                ,@Param("cusidt") String cusidt,@Param("apcode") String apcode,@Param("curcde") String curcde
                ,@Param("stmpny") short stmpny,@Param("bnstmpg") int bnstmpg,@Param("enstmpg") int enstmpg);

    /**
     * createBy: haiyuhuang
     * date:     2010-12-16
     * 按照账号查询账号传票信息*/
    List<ActstmForStmCotent> selectForStmContent(@Param("sysidt") String sysidt,@Param("orgidt") String orgidt
            ,@Param("cusidt") String cusidt,@Param("apcode") String apcode,@Param("curcde") String curcde);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTSTM
     *
     * @mbggenerated Mon Nov 29 09:51:25 CST 2010
     */
    int updateByExampleSelective(@Param("record") Actstm record, @Param("example") ActstmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTSTM
     *
     * @mbggenerated Mon Nov 29 09:51:25 CST 2010
     */
    int updateByExample(@Param("record") Actstm record, @Param("example") ActstmExample example);

    List<ActstmModel> selectStmByCondition(@Param("tlrnum") String tlrnum,@Param("bgndat") String bgndat,@Param("enddat") String enddat,@Param("accode") String accode,@Param("txnamt") String txnamt);

}