package student;

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
 * Servlet implementation class StudentAction
 */
@WebServlet("/StudentAction")
public class StudentAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentAction() {
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
		if(actionType.equals("list")) {			//获取所有学生信息
			GetAllStudents(request, response);
		}else if(actionType.equals("get")) {	//获取学生信息
			GetStudent (request, response);
		}else if(actionType.equals("update")) {	//添加或者更新学生信息
			UpdateStudent (request, response);
		}else if(actionType.equals("del")) {	//删除学生信息
			DelStudent(request, response);
		}else if(actionType.equals("code")) {	//验证学号是否重复
			CheckCode(request, response);
		}
	}
	protected void GetAllStudents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentDB beanDB=new StudentDB();
		ArrayList<StudentInfo> list=beanDB.getAllStudents();
		response.setContentType("application/json;charset=UTF-8");	//设置响应的内容类型及编码方式
		PrintWriter out = response.getWriter();	//获取输出流对象
		out.println("[");
		/*********************获取学生信息****************************/
		String jsonData="";
		for(int i=0;i<list.size();i++){
			StudentInfo student=list.get(i);
			String major=(student.getMajor()==1?"软件工程":"空间信息");
			
			jsonData+="{\"id\":"+student.getStID()+",\"code\":\""+student.getStCode()+"\",\"name\":\""+student.getStName()+"\",\"sex\":"+student.getSex()+",\"grade\":"+student.getGrade()+",\"major\":"+student.getMajor()+"},";
		}
		if(!jsonData.equals("")) {
			out.println(jsonData.substring(0, jsonData.length()-1));	//去除最后一个逗号
		}
		out.println("]");
		//System.out.println(jsonData);	
		out.flush();
		out.close();	//关闭输出流对象
	
	}
	protected void GetStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentDB beanDB=new StudentDB();
		//1、获取客户端请求
		int id=Integer.parseInt(request.getParameter("id"));
		//2、进行数据处理
		StudentInfo student=beanDB.getStudentById(id);
	 	//3、向客户端做出响应
		response.setContentType("application/json;charset=UTF-8");	//设置响应的内容类型及编码方式
		PrintWriter out = response.getWriter();	//获取输出流对象
		/*********************获取学生信息****************************/
		String jsonData="";	
		jsonData="{\"id\":"+student.getStID()+",\"code\":\""+student.getStCode()+"\",\"name\":\""+student.getStName()+"\",\"sex\":"+student.getSex()+",\"grade\":"+student.getGrade()+",\"major\":\""+student.getMajor()+"\",\"detail\":\""+student.getDetail()+"\"}";
		//System.out.println(jsonData);	
		out.println(jsonData);	
		out.flush();
		out.close();	//关闭输出流对象	
	}
	protected void UpdateStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentDB beanDB=new StudentDB();
		//1、获取客户端请求
	   StudentInfo student=new StudentInfo();
	   student.setStID(MyTools.strToint(request.getParameter("id")));
	   student.setStCode(request.getParameter("code"));
	   student.setStName(request.getParameter("name"));
	   student.setSex(MyTools.strToint(request.getParameter("sex")));
	   student.setGrade(MyTools.strToint(request.getParameter("grade")));
	   student.setMajor(MyTools.strToint(request.getParameter("major")));
	   student.setDetail(request.getParameter("detail"));
		//2、进行数据处理
	 	int count=0;
	 	if(student.getStID()==-1) {
	 		count=beanDB.insertStudent(student);		
	 	}else {
	 		count=beanDB.updateStudent(student);			//调用业务处理Bean的方法处理数据	 		
	 	}
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
	protected void DelStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentDB beanDB=new StudentDB();
		//1、获取客户端请求
		int id=Integer.parseInt(request.getParameter("id"));		//获取客户端提交数据
		//2、进行数据处理
	 	int count=beanDB.deleteStudent(id);							//调用业务处理Bean的方法处理数据
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
	protected void CheckCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=MyTools.strToint(request.getParameter("id"));
		String code=request.getParameter("code");
		StudentDB beanDB=new StudentDB();	
		int count=beanDB.getStudentCountByCode(code, id);
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
