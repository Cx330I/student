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
		if(actionType.equals("list")) {			//��ȡ����ѧ����Ϣ
			GetAllStudents(request, response);
		}else if(actionType.equals("get")) {	//��ȡѧ����Ϣ
			GetStudent (request, response);
		}else if(actionType.equals("update")) {	//��ӻ��߸���ѧ����Ϣ
			UpdateStudent (request, response);
		}else if(actionType.equals("del")) {	//ɾ��ѧ����Ϣ
			DelStudent(request, response);
		}else if(actionType.equals("code")) {	//��֤ѧ���Ƿ��ظ�
			CheckCode(request, response);
		}
	}
	protected void GetAllStudents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentDB beanDB=new StudentDB();
		ArrayList<StudentInfo> list=beanDB.getAllStudents();
		response.setContentType("application/json;charset=UTF-8");	//������Ӧ���������ͼ����뷽ʽ
		PrintWriter out = response.getWriter();	//��ȡ���������
		out.println("[");
		/*********************��ȡѧ����Ϣ****************************/
		String jsonData="";
		for(int i=0;i<list.size();i++){
			StudentInfo student=list.get(i);
			String major=(student.getMajor()==1?"�������":"�ռ���Ϣ");
			
			jsonData+="{\"id\":"+student.getStID()+",\"code\":\""+student.getStCode()+"\",\"name\":\""+student.getStName()+"\",\"sex\":"+student.getSex()+",\"grade\":"+student.getGrade()+",\"major\":"+student.getMajor()+"},";
		}
		if(!jsonData.equals("")) {
			out.println(jsonData.substring(0, jsonData.length()-1));	//ȥ�����һ������
		}
		out.println("]");
		//System.out.println(jsonData);	
		out.flush();
		out.close();	//�ر����������
	
	}
	protected void GetStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentDB beanDB=new StudentDB();
		//1����ȡ�ͻ�������
		int id=Integer.parseInt(request.getParameter("id"));
		//2���������ݴ���
		StudentInfo student=beanDB.getStudentById(id);
	 	//3����ͻ���������Ӧ
		response.setContentType("application/json;charset=UTF-8");	//������Ӧ���������ͼ����뷽ʽ
		PrintWriter out = response.getWriter();	//��ȡ���������
		/*********************��ȡѧ����Ϣ****************************/
		String jsonData="";	
		jsonData="{\"id\":"+student.getStID()+",\"code\":\""+student.getStCode()+"\",\"name\":\""+student.getStName()+"\",\"sex\":"+student.getSex()+",\"grade\":"+student.getGrade()+",\"major\":\""+student.getMajor()+"\",\"detail\":\""+student.getDetail()+"\"}";
		//System.out.println(jsonData);	
		out.println(jsonData);	
		out.flush();
		out.close();	//�ر����������	
	}
	protected void UpdateStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentDB beanDB=new StudentDB();
		//1����ȡ�ͻ�������
	   StudentInfo student=new StudentInfo();
	   student.setStID(MyTools.strToint(request.getParameter("id")));
	   student.setStCode(request.getParameter("code"));
	   student.setStName(request.getParameter("name"));
	   student.setSex(MyTools.strToint(request.getParameter("sex")));
	   student.setGrade(MyTools.strToint(request.getParameter("grade")));
	   student.setMajor(MyTools.strToint(request.getParameter("major")));
	   student.setDetail(request.getParameter("detail"));
		//2���������ݴ���
	 	int count=0;
	 	if(student.getStID()==-1) {
	 		count=beanDB.insertStudent(student);		
	 	}else {
	 		count=beanDB.updateStudent(student);			//����ҵ����Bean�ķ�����������	 		
	 	}
	 	//3����ͻ���������Ӧ
		response.setContentType("text/html;charset=UTF-8");	//����text 
		PrintWriter out = response.getWriter();
	    if(count!=0){
	    	out.print("1");//out.println("1");  
	    	//����ֱ�ӱȽϵļ����ݲ�����out.println���������صĻ��з�
	    }else{
	    	out.print("0");
	    }	
	 }
	protected void DelStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StudentDB beanDB=new StudentDB();
		//1����ȡ�ͻ�������
		int id=Integer.parseInt(request.getParameter("id"));		//��ȡ�ͻ����ύ����
		//2���������ݴ���
	 	int count=beanDB.deleteStudent(id);							//����ҵ����Bean�ķ�����������
	 	//3����ͻ���������Ӧ
		response.setContentType("text/html;charset=UTF-8");	//����text 
		PrintWriter out = response.getWriter();
	    if(count!=0){
	    	out.print("1");//out.println("1");  
	    	//����ֱ�ӱȽϵļ����ݲ�����out.println���������صĻ��з�
	    }else{
	    	out.print("0");
	    }	
	}
	protected void CheckCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=MyTools.strToint(request.getParameter("id"));
		String code=request.getParameter("code");
		StudentDB beanDB=new StudentDB();	
		int count=beanDB.getStudentCountByCode(code, id);
		response.setContentType("text/html;charset=UTF-8");	//����text 
		PrintWriter out = response.getWriter();
	    if(count!=0){
	    	out.print("1");//out.println("1");  
	    	//����ֱ�ӱȽϵļ����ݲ�����out.println���������صĻ��з�
	    }else{
	    	out.print("0");
	    }	
	}
}
