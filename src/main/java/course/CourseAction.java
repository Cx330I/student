package course;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import common.MyTools;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class CourseAction
 */
@WebServlet("/CourseAction")
public class CourseAction extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseAction() {
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
        if(actionType.equals("list")) {			//获取所有课程信息
            GetAllCourses(request, response);
        }else if(actionType.equals("get")) {	//获取课程信息
            GetCourse (request, response);
        }else if(actionType.equals("update")) {	//添加或者更新课程信息
            UpdateCourse (request, response);
        }else if(actionType.equals("del")){ //删除课程信息
            DelCourse(request, response);
        }
    }
    protected void GetAllCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDB beanDB=new CourseDB();
        ArrayList<CourseInfo> list = beanDB.getAllCourses();
        response.setContentType("application/json;charset=UTF-8");	//设置响应的内容类型及编码方式
        PrintWriter out = response.getWriter();	//获取输出流对象
        out.println("[");
        /*********************获取课程信息****************************/
        String jsonData="";
        for(int i=0;i<list.size();i++){
            CourseInfo course=list.get(i);
            String major=(course.getMajor()==1?"软件工程":"空间信息");

            jsonData+="{\"id\":"+course.getStID()+",\"name\":\""+course.getStName()+"\",\"type\":\""+course.getType()+"\",\"credit\":"+course.getCredit()+",\"grade\":"+course.getGrade()+",\"major\":"+course.getMajor()+"},";
        }
        if(!jsonData.equals("")) {
            out.println(jsonData.substring(0, jsonData.length()-1));	//去除最后一个逗号
        }
        out.println("]");
//        System.out.println(jsonData);
        out.flush();
        out.close();	//关闭输出流对象

    }
    protected void GetCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDB beanDB=new CourseDB();
        //1、获取客户端请求
        int id=Integer.parseInt(request.getParameter("id"));
        //2、进行数据处理
        CourseInfo course = beanDB.getCourseById(id);
        //3、向客户端做出响应
        response.setContentType("application/json;charset=UTF-8");	//设置响应的内容类型及编码方式
        PrintWriter out = response.getWriter();	//获取输出流对象
        /*********************获取学生信息****************************/
        String jsonData="";
        jsonData+="{\"id\":"+course.getStID()+",\"name\":\""+course.getStName()+"\",\"type\":\""+course.getType()+"\",\"credit\":"+course.getCredit()+",\"grade\":"+course.getGrade()+",\"major\":"+course.getMajor()+"}";
//        System.out.println(jsonData);
        out.println(jsonData);
        out.flush();
        out.close();	//关闭输出流对象
    }
    protected void UpdateCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDB beanDB=new CourseDB();
        //1、获取客户端请求
        CourseInfo course=new CourseInfo();
        course.setStID(MyTools.strToint(request.getParameter("id")));
        course.setStName(request.getParameter("name"));
        course.setType(MyTools.strToint(request.getParameter("type")));
        course.setCredit(MyTools.strToFloat(request.getParameter("credit")));
        course.setGrade(MyTools.strToint(request.getParameter("grade")));
        course.setMajor(MyTools.strToint(request.getParameter("major")));
        course.setDetail(request.getParameter("detail"));
        //2、进行数据处理
        int count=0;
        if(course.getStID()==-1) {
            count=beanDB.insertCourse(course);
        }else {
            count = beanDB.updateCourse(course);			//调用业务处理Bean的方法处理数据
        }
        System.out.println(course.getMajor());
        System.out.println(course.getCredit());
        //3、向客户端做出响应
        response.setContentType("text/html;charset=UTF-8");	//返回text
        PrintWriter out = response.getWriter();
        if(count!=0){
            out.print("1");//out.println("1");
            //用于直接比较的简单数据不能用out.println，包含隐藏的换行符
        }else{
            out.print("0");
        }
    }
    protected void DelCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDB beanDB=new CourseDB();
        //1、获取客户端请求
        int id=Integer.parseInt(request.getParameter("id"));		//获取客户端提交数据
        //2、进行数据处理
        int count=beanDB.deleteCourse(id);							//调用业务处理Bean的方法处理数据
        //3、向客户端做出响应
        response.setContentType("text/html;charset=UTF-8");	//返回text
        PrintWriter out = response.getWriter();
        if(count!=0){
            out.print("1");//out.println("1");
            //用于直接比较的简单数据不能用out.println，包含隐藏的换行符
        }else{
            out.print("0");
        }
    }
}
