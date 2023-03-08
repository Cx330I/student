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
<%--    <link rel="stylesheet" type="text/css"--%>
<%--          href="../css/student.css">--%>
    <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
</head>
    <script type="text/javascript">
        function submitData() {
            console.log(document.getElementById("user").value);
            if(document.getElementById("user").value == ""){
                alert("用户名不能为空！");
                return;
            }
            if(document.getElementById("password").value == ""){
                alert("密码不能为空！");
                return;
            }
            $.ajax({
                cache: true,
                type: "POST",
                url: "../UserAction?action=check",
                data: $("#userFrm").serialize(),
                async: false,
                error: function (request) {
                    alert("Connection error");
                },
                success: function (data) {
                    console.log(data);
                    if (data == 0) {
                        alert("登录成功");
                        window.location.href="login/user_info.jsp?loginflag=0";
                    } else if (data == 1){
                        let msg = "用户【";
                        msg += $("#user").val();
                        msg += "】不存在！";
                        alert(msg);
                    }else {
                        alert("密码错误！");
                    }
                }
            });

        }
        function clearData(){
            $("#user").val("")
            $("#password").val("")
        }
    </script>
<body>
<form name="frm" id="userFrm" method="post" action="../servlet/UserLoginCheckAction" length="30%" style="margin-left:43%; margin-top:18%;">
    <table style="border: 1px solid black; border-collapse: collapse;">
        <tr>
            <td style = "border: 1px solid black;">
                用户登录信息，<span style="color:red">*</span>为必填项
            </td>
        </tr>
        <tr>
            <td  style="border: 1px solid black;">
                账号 <input name="user" id="user" type="text"><span style="color:red">*</span>
            </td>
        </tr>
        <tr>
            <td style="border: 1px solid black;">
                密码 <input name="password" id="password" type="password"><span style="color:red">*</span>
            </td>
        </tr>
        <tr>
            <td style="text-align: center;"><input type="button" value="提交" onclick="submitData();"> <input type="button" value="重置" onclick="clearData();"></td>
        </tr>
    </table>
</form>
</body>
</html>
