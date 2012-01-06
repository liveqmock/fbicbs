<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="pub.platform.form.config.SystemAttributeNames" %>
<%@page import="java.util.Enumeration" %>
<%@ page import="java.util.List" %>
<%@ page import="pub.platform.security.OnLineOpersManager" %>
<!-- ÐÂÔö zxb 2011-3-30-->
<%--
<jsp:useBean id="onLineOpers" class="pub.platform.security.OnLineOpers" scope="application"/>
--%>

<html>
<head>
    <title>Logout</title>
</head>
<body bgcolor="#cccccc" link="#3366cc" vlink="#9999cc" alink="#0000cc">
<table border=0 cellspacing="18" cellpadding="0">
    <tr>
        <td valign="top">
            <%
                OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
                if (om != null) {
                    OnLineOpersManager.removeOperFromServer(session.getId(),application);
                    om.logout();
                }
                Enumeration p_enum = session.getAttributeNames();
                while (p_enum.hasMoreElements()) {
                    String removeStr = (String) p_enum.nextElement();
                    session.removeAttribute(removeStr);
                }
                if(session != null){
                    session.invalidate();
                }

            %>
            <%
                String isClose = request.getParameter("isclose");
                if (isClose == null || isClose.trim().equals("")) {
            %>
            <jsp:forward page="/pages/security/loginPage.jsp"/>
            <%
                } else {
                    out.println("<script>window.close();</script>");
                }
            %>
        </td>
    </tr>
</table>
</body>
</html>
