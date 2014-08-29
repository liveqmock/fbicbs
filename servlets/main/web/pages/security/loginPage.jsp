<%@ page import="pub.platform.security.OnLineOpersManager" %>
<%@ page import="cbs.common.utils.PropertyManager" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2010-11-19
  Time: 14:55:37
  To change this template use File | Settings | File Templates.
--%>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=GBK" language="java" %>

<%
    /*  // 检测是否是同一session
    if(OnLineOpersManager.isSessionOnline(session.getId(), application)){
                out.println("<script language=\"javascript\">" +
                 "alert ('请打开新的浏览器窗口登录系统！');" +
                 " window.open('', '_parent', '');" +
                   " window.close(); </script>");
           }*/

    String version = PropertyManager.getProperty("sys.version");
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    if ("0".equals(version)) {  // 试用版本
        String endDate = PropertyManager.getProperty("sys.date");
        Date d1 = new Date();
        Date d2 = format.parse(endDate);

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.after(c2)) {
            c1 = c2;
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }

        if(betweenDays < 1) {
            String path = request.getContextPath();
            out.println("<script language=\"javascript\">"
                    + "alert ('当前系统试用期已过!');" +
                    "if(top){ top.location.href='" + path + "/pages/security/loginPage.jsp'; } else { location.href = '" + path + "/pages/security/loginPage.jsp';} </script>");

        } else if(betweenDays < 15) {
            out.println("<script language=\"javascript\">"
                    + "alert ('当前系统试用期仅剩" + betweenDays + "天，过期系统将无法继续使用!');</script>");
        }

    }

    String contextPath = request.getContextPath();
    String cookieName = "usernamecookie";
    Cookie cookies[] = request.getCookies();
    String username = "";
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                username = cookie.getValue();
            }
        }
    }

%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户登录</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/resources/login/images/User_Login.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/resources/login/images/Default.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/resources/login/images/xtree.css"/>
    <script language="javascript" src="<%=contextPath%>/js/basic.js"></script>
    <script language="javascript" src="<%=contextPath%>/js/login.js"></script>

    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <META content="MSHTML 6.00.6000.16674" name=GENERATOR>
</head>
<body id="userlogin_body" onLoad="FocusUsername();">
<DIV id="user_login">
    <form action="<%=contextPath%>/pages/frame/homePage.jsp" method="post" name="winform" onSubmit="return false;">

        <DL>
            <DD id="user_top">
                <UL>
                    <LI class="user_top_l"></LI>
                    <LI class="user_top_c"></LI>
                    <LI class="user_top_r"></LI>
                </UL>
            </DD>
            <DD id="user_main">
                <UL>
                    <LI class="user_main_l"></LI>
                    <LI class="user_main_c">
                        <DIV class="user_main_box">
                            <UL>
                                <LI class="user_main_text">用户名：</LI>
                                <LI class="user_main_input">
                                    <INPUT class="TxtUserNameCssClass" id="username" value="<%=username%>"
                                           onfocus="this.select()" maxLength="20" name="username"
                                           onKeyPress="return focusNext(this.form, 'password', event)">
                                </LI>
                            </UL>
                            <UL>
                                <LI class="user_main_text">密 码：</LI>
                                <LI class="user_main_input">
                                    <INPUT class="TxtPasswordCssClass" id="password" type="password"
                                           name="password"
                                           onKeyPress="return focusNext(this.form, 'DropExpiration', event)">
                                </LI>
                            </UL>
                            <UL>
                                <LI class="user_main_text">Cookie：</LI>
                                <LI class="user_main_input">
                                    <SELECT id="DropExpiration" name="DropExpiration"
                                            onKeyPress="return submitViaEnter(event)">
                                        <OPTION value="Month" selected>保存一个月</OPTION>
                                        <OPTION value="Year">保存一年</OPTION>
                                        <OPTION value="None">不保存</OPTION>
                                    </SELECT></LI>
                            </UL>
                        </DIV>
                    </LI>
                    <LI class="user_main_r">
                        <INPUT class="IbtnEnterCssClass" id="IbtnEnter"
                               style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"
                               type="image" src="<%=contextPath%>/resources/login/images/user_botton.gif"
                               onclick="if( ValidateLength()){  document.winform.submit();}"
                               name="Submit"></LI>
                </UL>
            </DD>
            <DD id="user_bottom">
                <UL>
                    <LI class="user_bottom_l"></LI>
                    <LI class="user_bottom_c"><SPAN style="MARGIN-TOP: 40px">版权所有 <A
                            href="#about:blank">FBI</A> </SPAN></LI>
                    <LI class="user_bottom_r"></LI>
                </UL>
            </DD>

        </DL>
    </form>
</DIV>

<SPAN id="ValrUserName" style="DISPLAY: none; COLOR: red"></SPAN>
<SPAN id="ValrPassword" style="DISPLAY: none; COLOR: red"></SPAN>
<SPAN id="ValrValidateCode" style="DISPLAY: none; COLOR: red"></SPAN>

<DIV id="ValidationSummary1" style="DISPLAY: none; COLOR: red"></DIV>
</body>
</html>