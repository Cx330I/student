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
		
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");		//创建主题样式
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20)); 		//设置标题字体
		standardChartTheme.setRegularFont(new Font("微软雅黑", Font.PLAIN, 15)); 		//设置图例的字体
		standardChartTheme.setLargeFont(new Font("微软雅黑", Font.PLAIN, 15)); 		//设置轴向的字体
		ChartFactory.setChartTheme(standardChartTheme);							//设置主题样式
		DefaultCategoryDataset dataset1=new DefaultCategoryDataset();
		for(int i=0;i<list.size();i++){
			ScoreInfo score=list.get(i);	
			dataset1.addValue(score.getScore(),score.getCourseName(),score.getCourseName());
		}

		//创建JFreeChart组件的图表对象
		JFreeChart chart=ChartFactory.createBarChart3D(
											"学生成绩图",		//图表标题
											"学生",		//x轴的显示标题
											"成绩",			//y轴的显示标题
											dataset1,	//数据集
											PlotOrientation.VERTICAL,//图表方向(垂直)
											true,		//是否包含图例
											false,		//是否包含提示
											false		//是否包含URL
											);
		//设置图表的文件名
		// 固定用法
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		String fileName=ServletUtilities.saveChartAsPNG(chart,400,270,info,request.getSession());
		String url=request.getContextPath()+"/servlet/DisplayChart?filename="+fileName;
	
		//3、向客户端做出响应 
		response.setContentType("text/html;charset=UTF-8");	//返回text 
		PrintWriter out = response.getWriter();
		out.print(url);
		out.flush();
		out.close();	//关闭输出流对象

	}


}
