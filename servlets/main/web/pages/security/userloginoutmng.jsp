<%@ page contentType="text/html; charset=GBK" %>

<%
  if ( 1==1 ) {
      String path = request.getContextPath();
      out.println("<script language=\"javascript\">alert ('���ѱ�ϵͳ����Աǿ�����ߣ�'); if(top){ top.location.href='"+path+"/pages/security/loginPage.jsp'; } else { location.href = '"+path+"/pages/security/loginPage.jsp';} </script>");
      return;
  }%>
