package score;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.MyTools;
import student.StudentInfo;

/**
 * Servlet implementation class ScoreAction
 */
@WebServlet("/ScoreAction")
public class ScoreAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ScoreDB beanDB=null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreAction() {
        super();
        // TODO Auto-generated constructor stub
        beanDB=new ScoreDB();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("Action");
		System.out.println("action="+action);
		if(action==null){
			return;
		}else if(action.equals("GetStudentScoresByAjax")){//ͨ��Ajax��ȡѧ���ɼ�
			getStudentScoresByAjax(request,response);	
		}else if(action.equals("GetGradesByAjax")){//ͨ��Ajax��ȡָ��רҵ���꼶��Ϣ
			getGradesByAjax(request,response);	
		}else if(action.equals("GetCoursesByAjax")){//ͨ��Ajax��ȡָ��רҵָ���꼶�Ŀγ���Ϣ
			getCoursesByAjax(request,response);	
		}else if(action.equals("GetCourseScoresByAjax")){//ͨ��Ajax��ȡָ��רҵָ���꼶�Ŀγ���Ϣ
			getCourseScoresByAjax(request,response);	
		}else if(action.equals("GetCourseAllScoresByAjax")){//ͨ��Ajax��ȡָ���γ̵ĳɼ���Ϣ
			getCourseAllScoresByAjax(request,response);	
		}else if(action.equals("UpdateCourseScores")){//����γ̵ĳɼ���Ϣ
			updateCourseScores(request,response);	
		}
	}
	private void getStudentScoresByAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stCode=new String(request.getParameter("code"));
		//System.out.println("stCode="+stCode);
		ArrayList<ScoreInfo> list=beanDB.getStudentScoresByAjax(stCode);

		response.setContentType("application/json;charset=UTF-8");	//������Ӧ���������ͼ����뷽ʽ
		PrintWriter out = response.getWriter();	//��ȡ���������
		out.println("[");
		/*********************��ȡ�ɼ���Ϣ****************************/
		String jsonData="";
		for(int i=0;i<list.size();i++){
			ScoreInfo score=list.get(i);		
			jsonData+="{\"courseName\":\""+score.getCourseName()+"\",\"score\":"+score.getScore()+"},";
		}
		out.println(jsonData.substring(0, jsonData.length()-1));	//ȥ�����һ������
		out.println("]");
		//System.out.println(jsonData);
		out.flush();
		out.close();	//�ر����������
	}
	private void getGradesByAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int major=Integer.parseInt(request.getParameter("major"));
		//System.out.println("stCode="+stCode);
		ArrayList<ScoreInfo> list=beanDB.getGradesByAjax(major);
	    
		response.setContentType("application/json;charset=UTF-8");	//������Ӧ���������ͼ����뷽ʽ
		PrintWriter out = response.getWriter();	//��ȡ���������
		out.println("[");
		/*********************��ȡ�ɼ���Ϣ****************************/
		String jsonData="";
		for(int i=0;i<list.size();i++){
			ScoreInfo score=list.get(i);		
			jsonData+="{\"grade\":"+score.getGrade()+"},";
		}
		out.println(jsonData.substring(0, jsonData.length()-1));	//ȥ�����һ������
		//System.out.println(jsonData);
		out.println("]");
		out.flush();
		out.close();	//�ر����������
	}
	private void getCoursesByAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int major=Integer.parseInt(request.getParameter("major"));
		int grade=Integer.parseInt(request.getParameter("grade"));
		//System.out.println("stCode="+stCode);
		ArrayList<ScoreInfo> list=beanDB.getCoursesByAjax(major,grade);
	    
		response.setContentType("application/json;charset=UTF-8");	//������Ӧ���������ͼ����뷽ʽ
		PrintWriter out = response.getWriter();	//��ȡ���������
		out.println("[");
		/*********************��ȡ�ɼ���Ϣ****************************/
		String jsonData="";
		for(int i=0;i<list.size();i++){
			ScoreInfo score=list.get(i);		
			jsonData+="{\"courseID\":"+score.getCourseID()+",\"courseName\":\""+score.getCourseName()+"\"},";
		}
		out.println(jsonData.substring(0, jsonData.length()-1));	//ȥ�����һ������
		System.out.println(jsonData);
		out.println("]");
		out.flush();
		out.close();	//�ر����������
	}
	private void getCourseScoresByAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		//System.out.println("stCode="+stCode);
		ArrayList<ScoreInfo> list=beanDB.getCourseScoresByAjax(courseID);

		response.setContentType("application/json;charset=UTF-8");	//������Ӧ���������ͼ����뷽ʽ
		PrintWriter out = response.getWriter();	//��ȡ���������
		out.println("[");
		/*********************��ȡ�ɼ���Ϣ****************************/
		String jsonData="";
		for(int i=0;i<list.size();i++){
			ScoreInfo score=list.get(i);		
			jsonData+="{\"stCode\":\""+score.getStCode()+"\",\"stName\":\""+score.getStName()+"\",\"score\":"+score.getScore()+"\"},";
		}
		out.println(jsonData.substring(0, jsonData.length()-1));	//ȥ�����һ������
		out.println("]");
		out.flush();
		out.close();	//�ر����������

	}
	private void getCourseAllScoresByAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		//System.out.println("stCode="+stCode);
		ArrayList<ScoreInfo> list=beanDB.getCourseAllScoresByAjax(courseID);

		response.setContentType("application/json;charset=UTF-8");	//������Ӧ���������ͼ����뷽ʽ
		PrintWriter out = response.getWriter();	//��ȡ���������
		out.println("[");
		/*********************��ȡ�ɼ���Ϣ****************************/
		String jsonData="";
		for(int i=0;i<list.size();i++){
			ScoreInfo score=list.get(i);		
			jsonData+="{\"stCode\":\""+score.getStCode()+"\",\"stName\":\""+score.getStName()+"\",\"score\":"+score.getScore()+",\"stID\":"+score.getStID()+",\"courseID\":"+score.getCourseID()+"},";
		}
		out.println(jsonData.substring(0, jsonData.length()-1));	//ȥ�����һ������
		//System.out.println(jsonData);
		out.println("]");
		out.flush();
		out.close();	//�ر����������

	}
	private void updateCourseScores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   //1����ȡ�ͻ����ύ����
		//System.out.println(request.getParameter("courseID"));
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		String stIDs[] = request.getParameterValues("stID");
		String stCodes[] = request.getParameterValues("stCode");
		String scores[] = request.getParameterValues("score");
		//System.out.println(courseID);
		 //2������ͻ����ύ����
		int count=0;
		int flag=0;
		String resulInfo="";
		
		if(stIDs!=null) {
			beanDB.delCourseAllScores(courseID);//��ɾ�����гɼ������������
			ScoreInfo score=new ScoreInfo();
			score.setCourseID(courseID);
			for(int i = 0; i < stIDs.length ; i++) {
				score.setStID(MyTools.strToint(stIDs[i]));
				score.setScore(Float.parseFloat(scores[i]));
				flag=beanDB.insertCourseAllScores(score);
				if(flag==0) {
					resulInfo+="ѧ�ţ�"+stCodes[i]+"\n";
				}else {
					count++;
				}
				
			}
		}
	
	   //3����ͻ���������Ӧ 
		response.setContentType("text/html;charset=UTF-8");	//����text 
		PrintWriter out = response.getWriter();
		if(resulInfo.equals("")) {
			resulInfo="�ɹ�¼��"+count+"��������";
		}else {
			resulInfo="�ɹ�¼��"+count+"��������\n����ѧ�����������⣺"+resulInfo;
		}
		out.print(resulInfo);
		out.flush();
		out.close();	//�ر����������

	}

}
