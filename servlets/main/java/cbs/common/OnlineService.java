package cbs.common;

import org.apache.ibatis.session.SqlSessionFactory;
import pub.platform.form.config.SystemAttributeNames;
import pub.platform.security.OperatorManager;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-16
 * Time: 20:58:24
 * To change this template use File | Settings | File Templates.
 */
/*
 * updated by zhangxiaobo
 * Date: 2011-03-10
 * 新增变量 sdfdate10
  * 新增方法 getBizDate10
  * 新增金额格式化函数
 */
//@ManagedBean(eager = true)
public class OnlineService {

    static private SqlSessionFactory sessionFactory = IbatisFactory.ORACLE.getInstance();
    public static OperatorManager getOperatorManager(){
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
        if (om == null) {
            throw new RuntimeException("用户未登录！");
        }
        return om;
    }

/*

    public static String getOperatorID(){
        try {
            return getOperatorManager().getOperatorId();
        } catch (Exception e) {
            throw new RuntimeException("获取用户ID错误！");
        }
    }
    public static String getOperatorName(){
        try {
            return getOperatorManager().getOperatorName();
        } catch (Exception e) {
            throw new RuntimeException("获取用户名称错误！");
        }
    }

*/
}
