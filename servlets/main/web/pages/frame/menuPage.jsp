<%@ page contentType="text/html; charset=GBK" %>
<%--<%@ include file="/global.jsp"%>--%>
<%@ include file="/pages/security/online.jsp" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="pub.platform.form.config.SystemAttributeNames" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="pub.platform.utils.Basic" %>
<%
    response.setContentType("text/html; charset=GBK");
    String contextPath = request.getContextPath();

%>
<script type="text/javascript">
    var g_jsContextPath = "<%=contextPath%>";
</script>

<html xmlns:hGui>
<head>
    <link href="<%=contextPath%>/css/ccb.css" type="text/css" rel="stylesheet">
    <script language="javascript" src="<%=contextPath%>/js/xmlHttp.js"></script>
    <script language="javascript" src="<%=contextPath%>/js/basic.js"></script>
    <script language="javascript" src="<%=contextPath%>/js/menuPage.js"></script>
    <script language="javascript" src="<%=contextPath%>/js/tree.js"></script>
    <script language="javascript" src="<%=contextPath%>/js/dbutil.js"></script>

    <LINK href="<%=contextPath%>/dhtmlx/codebase/dhtmlxlayout.css" type="text/css" rel="stylesheet">
    <LINK href="<%=contextPath%>/dhtmlx/codebase/skins/dhtmlxlayout_dhx_skyblue.css" type="text/css" rel="stylesheet">
    <script language="javascript" src="<%=contextPath%>/dhtmlx/codebase/dhtmlxcommon.js"></script>
    <script language="javascript" src="<%=contextPath%>/dhtmlx/codebase/dhtmlxlayout.js"></script>

    <LINK href="<%=contextPath%>/dhtmlx/codebase/skins/dhtmlxaccordion_dhx_skyblue.css" type="text/css" rel="stylesheet">
    <script language="javascript" src="<%=contextPath%>/dhtmlx/codebase/dhtmlxaccordion.js"></script>
    <script language="javascript" src="<%=contextPath%>/dhtmlx/codebase/dhtmlxcontainer.js"></script>

    <LINK href="<%=contextPath%>/dhtmlx/codebase/dhtmlxtree.css" type="text/css" rel="stylesheet">
    <script language="javascript" src="<%=contextPath%>/dhtmlx/codebase/dhtmlxtree.js"></script>
    <script language="javascript" src="<%=contextPath%>/dhtmlx/codebase/ext/dhtmlxtree_json.js"></script>

    <script language="javascript">
       <%
             String jsonDefaultMenu = null;
             String jsonSystemMenu = null;
             String jsonCustomerMenu = null;
             String jsonVoucherMenu = null;
             String jsonCheckMenu = null;
             String jsonExtsysMenu = null;
             String jsonBizparamMenu = null;
             OperatorManager om = (OperatorManager)session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
             try {
                 //xmlString =om.getXmlString();
                 jsonDefaultMenu = om.getJsonString("default");
                 jsonCustomerMenu = om.getJsonString("customer");
                 jsonVoucherMenu = om.getJsonString("voucher");
                 jsonCheckMenu = om.getJsonString("check");
                 jsonExtsysMenu = om.getJsonString("extsys");
                 jsonBizparamMenu = om.getJsonString("bizparam");
                 jsonSystemMenu = om.getJsonString("system");
              } catch(Exception e) {
                 System.out.println("jsp" +e +"\n");
              }
        %>
        var defaultMenuStr = '<%=jsonDefaultMenu%>';
        var customerMenuStr = '<%=jsonCustomerMenu%>';
        var voucherMenuStr = '<%=jsonVoucherMenu%>';
        var checkMenuStr = '<%=jsonCheckMenu%>';
        var extsysMenuStr = '<%=jsonExtsysMenu%>';
        var bizparamMenuStr = '<%=jsonBizparamMenu%>';
        var systemMenuStr = '<%=jsonSystemMenu%>';

        var dhxAccord;
        function doOnLoad() {
            document.all("accordObj").style.height = document.body.clientHeight - 2;
            dhxAccord = new dhtmlXAccordion("accordObj");
            dhxAccord.setSkin("dhx_skyblue");
            dhxAccord.setIconsPath("<%=contextPath%>/dhtmlx/codebase/icons/");
            dhxAccord.addItem("a1", "会计核算管理");
            dhxAccord.addItem("a2", "客户信息管理");
            dhxAccord.addItem("a3", "凭证管理");
            dhxAccord.addItem("a4", "支票管理");
            dhxAccord.addItem("a5", "外围系统管理");
            dhxAccord.addItem("a6", "业务参数控制");
            dhxAccord.addItem("a7", "系统管理");
            dhxAccord.openItem("a1");
            dhxAccord._enableOpenEffect = true;

            dhxAccord.cells("a1").setIcon("accord_biz.png");
            dhxAccord.cells("a2").setIcon("accord_biz.png");
            dhxAccord.cells("a3").setIcon("accord_biz.png");
            dhxAccord.cells("a4").setIcon("accord_biz.png");
            dhxAccord.cells("a5").setIcon("editor.gif");
            dhxAccord.cells("a6").setIcon("editor.gif");
            dhxAccord.cells("a7").setIcon("accord_manage.png");
            var biztree = dhxAccord.cells("a1").attachTree();
            var customertree = dhxAccord.cells("a2").attachTree();
            var vouchertree = dhxAccord.cells("a3").attachTree();
            var checktree = dhxAccord.cells("a4").attachTree();
            var extsystree = dhxAccord.cells("a5").attachTree();
            var bizparamtree = dhxAccord.cells("a6").attachTree();
            var managetree = dhxAccord.cells("a7").attachTree();


            initMenuItems(biztree, defaultMenuStr);
            initMenuItems(customertree, customerMenuStr);
            initMenuItems(vouchertree, voucherMenuStr);
            initMenuItems(checktree, checkMenuStr);
            initMenuItems(extsystree, extsysMenuStr);
            initMenuItems(bizparamtree, bizparamMenuStr);
            initMenuItems(managetree, systemMenuStr);

        }

        function initMenuItems(tree, itemStr){
            var treeJson = eval('(' + itemStr + ')');
            tree.setSkin('dhx_skyblue');
            tree.setImagePath("<%=contextPath%>/dhtmlx/codebase/imgs/csh_books/");
            tree.loadJSONObject(treeJson);
            tree.attachEvent("onClick", function(id) {
                var action = (tree.getUserData(id, "url"));
                if (action == "#") {
                    tree.openItem(id);
                } else {
                    parent.window.workFrame.location.replace("<%=contextPath%>" + action);
                }
                return true;
            });


        }

        function doOnResize() {
            var parentObj = document.getElementById("accordObj");
            parentObj.style.height = document.body.clientHeight - 2;
            dhxAccord.setSizes();
        }
    </script>

</head>


<body onload="doOnLoad();" onResize="doOnResize();">

<div id="accordObj" style="width: 194px; height: 500px; margin-left:6px;"></div>

</body>
</html>
