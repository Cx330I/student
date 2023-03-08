<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2023-03-02
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="../css/student.css">
</head>
<script type="text/javascript">

    $(document).ready(function () {
        let username = "<%=session.getAttribute("user")%>";
        // alert(username);
        // console.log(username);
        if("admin" === username){
            getList();
        }
    });

    function getList() {
        $.get("../UserAction?action=getUser",					//servlet
            function (data) {
                console.log(data);
                WriteList(data);
            },		//JSON数据处理函数
            "JSON");										//JSON
    }

    function WriteList(data) {
        let users = data;
        let strHtml = "";
        // strHtml += "<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">	";
        strHtml += "<table width=\"20%\" style=\" border-collapse: collapse;\"  > ";
        strHtml += "<tr class=\"td_header\"> <td>用户名称</td> <td>用户密码</td>    </tr>"
        for (let i = 0; i < users.length; i++) {
            strHtml += "<tr class=\"td_" + (i % 2 + 1) + "\">";
            strHtml += "<td >" + users[i]["name"] + "</td>";
            strHtml += "<td >" + users[i]["password"] + "</td></tr>";
        }
        strHtml += "</table>";
        // console.log(strHtml);
        $("#usersSpan").html(strHtml);
    }
</script>
<body>
<div id="usersSpan">
<%--    <table width="20%" style="border: 1px solid black; border-collapse: collapse;"  >--%>
    <table width="20%" style=" border-collapse: collapse;"  >
        <tr class="td_header">
            <td>用户名称</td>
            <td>用户密码</td>
        </tr>
    </table>
</div>
</body>
</html>
