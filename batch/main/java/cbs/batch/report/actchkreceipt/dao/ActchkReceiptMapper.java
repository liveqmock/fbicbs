package cbs.batch.report.actchkreceipt.dao;

import cbs.batch.report.actchkreceipt.BalReceipt;
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
public interface ActchkReceiptMapper {

    @Select("select act.orgidt,ani.oldacn,act.actnam,glc.glcnam,act.bokbal from actact act" +
            " join actani ani on act.sysidt = ani.sysidt and act.orgidt = ani.orgidt" +
            " and act.cusidt = ani.cusidt and act.apcode = ani.apcode" +
            " and act.curcde = ani.curcde join actglc glc on substr(act.apcode,1,3)=glc.glcode" +
            " where ani.oldacn not in(${notNeedAcn}) "+
            "and act.orgidt = #{orgidt} and act.actsts<>'I' order by ani.oldacn")
    List<BalReceipt> qryBalReceipts(@Param("notNeedAcn") String notNeedAcn, @Param("orgidt") String orgidt);

    @Select("select * from ptdept where deptid is not null")
    List<Ptdept> qryAllPtdept();
}
