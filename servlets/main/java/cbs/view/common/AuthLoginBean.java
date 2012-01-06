package cbs.view.common;

import cbs.common.utils.MessageUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-30
 * Time: 10:50:50
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean (name="authLoginBean")
@RequestScoped
public class AuthLoginBean {
    @Length (max = 4)
    private String username;
    private String password;
    private String workuser; //被授权用户


    //TODO? 不能调用？
    public void authLogin(ActionEvent e) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;

        if (username != null && username.equals("admin") && password != null && password.equals("admin")) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
    }
    
    //TODO  读数据库  判断状态
    public String authLogin() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;

        if (StringUtils.isEmpty(workuser)) {
            loggedIn = false;
            MessageUtil.addError("未提供当前交易柜员号！");
            return null;
        }
        if (workuser.equals(username)) {
            loggedIn = false;
            MessageUtil.addError("授权主管不能与交易柜员相同！");
            return null;
        }

//        if (username != null && username.equals("9999") && password != null && password.equals("1111")) {
        if (username != null && password != null ) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "已通过主管授权，主管号：", username);
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "用户校验错误", "密码错误！");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);

        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkuser() {
        return workuser;
    }

    public void setWorkuser(String workuser) {
        this.workuser = workuser;
    }
}
