<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cbs.common.SystemService" %>
<%@ page import="pub.platform.db.ConnectionManager" %>
<%@ page import="pub.platform.db.DatabaseConnection" %>
<%@ page import="pub.platform.db.RecordSet" %>
<%@ page import="pub.platform.form.config.SystemAttributeNames" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%
    OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
    String contextPath = request.getContextPath();

    if (om == null) {
        om = new OperatorManager();
        session.setAttribute(SystemAttributeNames.USER_INFO_NAME, om);
    }

    //联行行号 区分各个生产部署版本  显示主页banner，默认为banner目录
    String ibkcde = "banner";
    String bannerDir1 = "../../images/banner/banner1.jpg";
    String bannerDir2 = "../../images/banner/banner2.png";

    String username = "";
    String deptname = "";
    String operid = "";

    String busDate = SystemService.getBizDate10();
    String sysDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    String rolesall = null;

    if (om != null) {
        if (om.getOperator() != null) {
            username = om.getOperator().getOpername();
            operid = om.getOperator().getOperid();
            if (om.getOperator().getPtDeptBean() != null)
                deptname = om.getOperator().getPtDeptBean().getDeptname();

            //角色
            List roles = new ArrayList();

            DatabaseConnection conn = ConnectionManager.getInstance().get();
            RecordSet rs = conn.executeQuery("select a.roledesc from ptoperrole b right join ptrole a on b.roleid = a.roleid  where b.operid='" + operid + "'");
            while (rs.next()) {
                roles.add(rs.getString("roledesc"));
            }
            //联行行号
            rs = conn.executeQuery("select ibkcde from actorg where orglvl = '1' ");
            if (rs.next()) {
                ibkcde = rs.getString("ibkcde");
            }
            if (ibkcde == null) {
                ibkcde = "banner";
            }
            ibkcde = ibkcde.toLowerCase();

            ConnectionManager.getInstance().release();

            bannerDir1 = "../../images/" + ibkcde + "/banner1.jpg";
            bannerDir2 = "../../images/" + ibkcde + "/banner2.png";
            rolesall = " ";
            for (int i = 0; i < roles.size(); i++) {
                rolesall += roles.get(i) + " ";
            }
        }
    }
%>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <LINK href="<%=contextPath%>/css/ccb.css" type="text/css" rel="stylesheet">
    <script type="text/javascript">

        function Relogin() {
            parent.window.reload = "true";
            parent.window.location.replace("<%=contextPath%>/pages/security/logout.jsp");
            //parent.window.location.replace("
        <%--<%=contextPath%>/index.login");--%>
        }
        function changepwd() {
            var sfeature = "dialogwidth:400px; dialogheight:200px;center:yes;help:no;resizable:no;scroll:no;status:no";
            window.showModalDialog("<%=contextPath%>/UI/system/deptUser/Passwordedit.jsp", "test", sfeature);
        }
        function goFirst() {
            parent.window.workFrame.location.replace("<%=contextPath%>/pages/frame/trackMisc.xhtml");
        }

        function bodyload() {
            var busdt = <%=busDate%>;
            var sysdt = <%=sysDate%>;
            if (busdt == sysdt) {
                alert('当前业务日期：<%=busDate%>');
            } else {
                alert('当前业务日期(<%=busDate%>)与系统当前日期(<%=sysDate%>)不一致，请确认是否需要运行批量！');
            }
        }
    </script>

    <style type="text/css">
        body {
            background-color: #FFF;
        }

        div#nifty1 {
            margin: 0 1px; /*background: #9BD1FA;*/
            /*background: #7A8FA8;*/
            /*background: #0F67A1;*/
            /*background: #B54936;*/
            /*background: #FF6600;*/
            /*background: #999999;*/
            background: #7387A0;
        }

        div#nifty2 {
            margin: 0 1px;
            background: #7387A0;
        }

        div#nifty3 {
            margin: 0 1px;
            background: #7387A0;
        }

        div#nifty4 {
            margin: 0 1px;
            background: #7387A0;
        }

        div#nifty5 {
            margin: 0 1px;
            background: #7387A0;
        }

        b.rtop, b.rbottom {
            display: block;
            background: #FFF
        }

        b.rtop b, b.rbottom b {
            display: block;
            height: 1px;
            overflow: hidden;
            background: #7387A0
        }

        b.r1 {
            margin: 0 5px
        }

        b.r2 {
            margin: 0 3px
        }

        b.r3 {
            margin: 0 2px
        }

        b.rtop b.r4, b.rbottom b.r4 {
            margin: 0 1px;
            height: 2px
        }
    </style>


</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" onbeforeunload="Relogin()" onload="bodyload()">
<input id="hhidOperatorID" type="hidden" value="<%=operid%>">
<table width="100%" cellpadding="0" cellspacing="0">
    <tr width="100%" height="50px" style="margin-bottom: 0px;margin-top:0px;margin:0px;padding:0px">
        <td width="50%" style="height:50" colspan="4">
            &nbsp;&nbsp;
            <image style="top-marign:0px;bottom-margin:0px;height:45" src=<%=bannerDir1%>></image>
            <image style="top-marign:0px;bottom-margin:0px;height:50" src=<%=bannerDir2%>></image>
        </td>
        <td width="50%" style="height:50;text-align:right" colspan="1">
            <font color="#5F6A78"> <span>您好,<%=username%>! </span> </font>
            <font color="#5F6A78"> <span onclick="changepwd()"
                                         onMouseOver="this.style.cursor='hand'">|&nbsp;&nbsp;修改密码</span> </font>
            <font color="#5F6A78"> <span onclick="goFirst() "
                                         onMouseOver="this.style.cursor='hand'">|&nbsp;&nbsp;回首页</span> </font>
            <font color="#5F6A78"> <span onclick="Relogin()"
                                         onMouseOver="this.style.cursor='hand'">|  退出 &nbsp;&nbsp;</span> </font>
        </td>
    </tr>


    <tr>
        <td colspan="5">
            <div id="nifty1">
                <b class="rtop">
                    <b class="r1"></b>
                    <b class="r2"></b>
                    <b class="r3"></b>
                    <b class="r4"></b>
                </b>

                <div style="height:15px;text-align:right">
                    <font color="#ECEFF2">
                        <%--<font color="#FFFFFF">--%>
                        <%=" " + deptname + " | " + operid + " | <" + rolesall + ">" + " | " + busDate%>   &nbsp;&nbsp;&nbsp;
                    </font>
                </div>
                <b class="rbottom">
                    <b class="r4"></b>
                    <b class="r3"></b>
                    <b class="r2"></b>
                    <b class="r1"></b>
                </b>
            </div>
        </td>
    </tr>
</table>


</body>
</html>