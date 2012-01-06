<%@ page contentType="text/html; charset=GBK" %>

<%@ page import="java.util.*" %>
<%@ page import="pub.platform.form.config.SystemAttributeNames" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="pub.platform.security.OnLineOpersManager" %>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String cookieFlag = request.getParameter("DropExpiration");
    String path = request.getContextPath();
    Cookie cookies[] = request.getCookies();
    int cookieMaxAge = 0;
    if ("Month".equalsIgnoreCase(cookieFlag)) {
        cookieMaxAge = 30 * 24 * 60 * 60;
    } else if ("Year".equalsIgnoreCase(cookieFlag)) {
        cookieMaxAge = 365 * 24 * 60 * 60;
    }
    if (!"None".equalsIgnoreCase(cookieFlag)) {
        boolean isFound = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (("usernamecookie").equals(cookie.getName())) {
                    if (!username.equals(cookie.getValue())) {
                        cookie.setValue(username);
                        cookie.setPath(request.getContextPath() + "/pages");
                        cookie.setMaxAge(cookieMaxAge);
                        response.addCookie(cookie);
                    }
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            Cookie cookie = new Cookie("usernamecookie", username);
            cookie.setMaxAge(cookieMaxAge);
            cookie.setPath(request.getContextPath() + "/pages");
            response.addCookie(cookie);
        }
    }

%>

<%
    // 新增判断是否重复登录 zxb 2011-3-30
    if (OnLineOpersManager.isUserOnline(username, application)) {
        out.println("<script language=\"javascript\">" +
                "alert ('该用户已登录，请勿重复登录！');" +
                " if(top){ top.location.href='" +
                path + "/pages/security/loginPage.jsp'; } else { location.href = '" +
                path + "/pages/security/loginPage.jsp';} </script>");
    } else  // 检测是否是同一session
        if (OnLineOpersManager.isSessionOnline(session.getId(), application)) {
            out.println("<script language=\"javascript\">" +
                    "alert ('请联系系统管理员修改IE设置或打开不同的浏览器窗口登录系统！');" +
                    " window.open('', '_parent', '');" +
                    " window.close(); </script>");
        } else {
                OperatorManager om = new OperatorManager();
                session.setAttribute(SystemAttributeNames.USER_INFO_NAME, om);

            //String imgsign = request.getParameter("imgsign");
            boolean isLogin = false;
            try {
                //if(!om.ImgSign(imgsign))
                //	out.println("<script language=\"javascript\">alert ('输入校验码有误！'); if(top){ top.location.href='/index.jsp'; } else { location.href = '/index.jsp';} </script>");
                om.setRemoteAddr(request.getRemoteAddr());
                om.setRemoteHost(request.getRemoteHost());
                isLogin = om.login(username, password);
                if (!isLogin) {
                    out.println("<script language=\"javascript\">alert ('输入用户名或密码有误！'); if(top){ top.location.href='" + path + "/pages/security/loginPage.jsp'; } else { location.href = '" + path + "/pages/security/loginPage.jsp';} </script>");
                } else {
                    if (!OnLineOpersManager.isHasUserList(application)) {
                        OnLineOpersManager.setUserListToServer(application);
                        OnLineOpersManager.addOperToServer(session.getId(), om, application);
                    } else {
                        OnLineOpersManager.addOperToServer(session.getId(), om, application);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
%>

