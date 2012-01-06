<%@ page import="java.util.List" %>
<%@ page import="pub.platform.security.OnLineOpersManager" %>
<%@ page contentType="text/html; charset=GBK" %>
<%
    String sessionKey = request.getParameter("killsession");
    OnLineOpersManager.removeOperFromServer(sessionKey, application);
    request.getRequestDispatcher("onlineuser.jsp").forward(request, response);
%>
