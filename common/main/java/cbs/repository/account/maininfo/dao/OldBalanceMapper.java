package cbs.repository.account.maininfo.dao;

import cbs.repository.account.maininfo.model.ActBalance;
import cbs.repository.platform.model.Ptdept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-4-21
 * Time: ÏÂÎç5:21
 * To change this template use File | Settings | File Templates.
 */
public interface OldBalanceMapper {

    @Select("select act.orgidt,ani.oldacn,act.actnam,act.bokbal from actact act" +
            " join actani ani on act.sysidt = ani.sysidt" +
            " and act.orgidt = ani.orgidt  and act.cusidt = ani.cusidt" +
            " and act.apcode = ani.apcode  and act.curcde = ani.curcde" +
            " where act.orgidt = #{orgidt} and ani.oldacn not in(${notNeedAcn}) and act.bokbal <> 0" +
            " order by ani.oldacn")
    List<ActBalance> qryActBalance(@Param("notNeedAcn") String notNeedAcn, @Param("orgidt") String orgidt);

    @Select("select * from ptdept where deptid is not null")
    List<Ptdept> qryAllPtdept();
}
