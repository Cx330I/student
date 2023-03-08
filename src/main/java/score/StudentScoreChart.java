package score;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart ;
import org.jfree.data.category.DefaultCategoryDataset ;
import org.jfree.chart.plot.PlotOrientation ;
import org.jfree.chart.entity.StandardEntityCollection ;
import org.jfree.chart.ChartRenderingInfo ;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.StandardChartTheme;
import java.awt.Font;
/**
 * Servlet implementation class StudentScoreChart
 */
@WebServlet("/StudentScoreChart")
public class StudentScoreChart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ScoreDB beanDB=null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentScoreChart() {
        super();
        // TODO Auto-generated constructor stub
        beanDB=new ScoreDB();
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stCode=new String(request.getParameter("code"));
		//System.out.println("stCode="+stCode);
		ArrayList<ScoreInfo> list=beanDB.getStudentScoresByAjax(stCode);
		
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");		//����������ʽ
		standardChartTheme.setExtraLargeFont(new Font("����", Font.BOLD, 20)); 		//���ñ�������
		standardChartTheme.setRegularFont(new Font("΢���ź�", Font.PLAIN, 15)); 		//����ͼ��������
		standardChartTheme.setLargeFont(new Font("΢���ź�", Font.PLAIN, 15)); 		//�������������
		ChartFactory.setChartTheme(standardChartTheme);							//����������ʽ
		DefaultCategoryDataset dataset1=new DefaultCategoryDataset();
		for(int i=0;i<list.size();i++){
			ScoreInfo score=list.get(i);	
			dataset1.addValue(score.getScore(),score.getCourseName(),score.getCourseName());
		}

		//����JFreeChart�����ͼ�����
		JFreeChart chart=ChartFactory.createBarChart3D(
											"ѧ���ɼ�ͼ",		//ͼ�����
											"ѧ��",		//x�����ʾ����
											"�ɼ�",			//y�����ʾ����
											dataset1,	//���ݼ�
											PlotOrientation.VERTICAL,//ͼ����(��ֱ)
											true,		//�Ƿ����ͼ��
											false,		//�Ƿ������ʾ
											false		//�Ƿ����URL
											);
		//����ͼ����ļ���
		// �̶��÷�
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		String fileName=ServletUtilities.saveChartAsPNG(chart,400,270,info,request.getSession());
		String url=request.getContextPath()+"/servlet/DisplayChart?filename="+fileName;
	
		//3����ͻ���������Ӧ 
		response.setContentType("text/html;charset=UTF-8");	//����text 
		PrintWriter out = response.getWriter();
		out.print(url);
		out.flush();
		out.close();	//�ر����������

	}


}
