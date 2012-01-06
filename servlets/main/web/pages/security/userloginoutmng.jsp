<%@ page contentType="text/html; charset=GBK" %>

<%
  if ( 1==1 ) {
      String path = request.getContextPath();
      out.println("<script language=\"javascript\">alert ('您已被系统管理员强制下线！'); if(top){ top.location.href='"+path+"/pages/security/loginPage.jsp'; } else { location.href = '"+path+"/pages/security/loginPage.jsp';} </script>");
      return;
  }%>
