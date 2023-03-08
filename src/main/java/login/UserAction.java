package login;


import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.mysql.cj.Session;
import common.MyTools;
import course.CourseDB;
import course.CourseInfo;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAction extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAction() {
        super();
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionType=request.getParameter("action");
        if(actionType.equals("check")){
            checkUser(request, response);
        }else if(actionType.equals("getUser")){
            getUser(request, response);
        }


    }
    protected void checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDB userDB = new UserDB();
        String path = request.getContextPath();
        HttpSession session = request.getSession();
        String username = "";
        String password = "";
        //1、获取客户端提交数据
        username = request.getParameter("user");
        password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        //2、处理客户端提交数据   TODO 2 使用UserDB对象的方法，获取指定用户名的用户对象
        UserInfo user =  new UserInfo();
        user = userDB.getUserByName(username);
//        System.out.println(user.getUserPwd());
        int flag = 0;  //登录成功0 用户不存在 1 密码错误 2
        //TODO 3 通过获取用户信息和客户端提交信息进行比较，设置flag的值
        if(user == null){
            flag = 1;
        }else {
            if(!password.equals(user.getUserPwd())){
                flag = 2;
            }
        }
        PrintWriter out = response.getWriter();	//获取输出流对象
        out.println(flag);
        out.flush();
        out.close();	//关闭输出流对象
        //3、向客户端做出响应
        if(flag==0) {
            session.setAttribute("user", username);
            System.out.println(username);
        }
    }
    protected void getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDB userDB = new UserDB();
        ArrayList<UserInfo> users = userDB.getAllUser();
        response.setContentType("application/json;charset=UTF-8");	//设置响应的内容类型及编码方式
        PrintWriter out = response.getWriter();	//获取输出流对象
        out.println("[");
        /*******************获取用户信息****************************/
        String jsonData="";
        for(int i=0;i<users.size();i++){
            UserInfo user=users.get(i);


            jsonData+="{\"id\":"+user.getUserID()+",\"name\":\""+user.getUserName()+"\",\"password\":\""+user.getUserPwd()+"\"},";
        }
        if(!jsonData.equals("")) {
            out.println(jsonData.substring(0, jsonData.length()-1));	//去除最后一个逗号
        }
        out.println("]");
        out.flush();
        out.close();	//关闭输出流对象
    }
}