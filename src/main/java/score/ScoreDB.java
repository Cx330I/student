package score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import common.DBConnection;

public class ScoreDB {
    private Connection con = null;
    /* 获取所有学生信息 */
    public ArrayList<ScoreInfo> getStudentScoresByAjax(String stCode) {
        ResultSet rs=null;
        Statement stmt=null;
        ArrayList<ScoreInfo> list=new ArrayList<ScoreInfo>();
        try {
        	con=DBConnection.getConnection();
            stmt=con.createStatement();
            String sql="select c.N_STUDENT_ID,c.VC_STUDENT_CODE,c.VC_STUDENT_NAME,b.N_COURSE_ID,b.VC_COURSE_NAME,a.F_SCORE "
            +"from t_score a,t_course b ,t_student c "
            +"where c.VC_STUDENT_CODE='"+stCode+"' and a.N_STUDENT_ID=c.N_STUDENT_ID and a.N_COURSE_ID=b.N_COURSE_ID ";
            //System.out.println("sql="+sql);
	    	rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		ScoreInfo score=new ScoreInfo();
	    		score.setStID(rs.getInt("N_STUDENT_ID"));
	    		score.setStCode(rs.getString("VC_STUDENT_CODE"));
	    		score.setStName(rs.getString("VC_STUDENT_NAME"));
	    		score.setCourseID(rs.getInt("N_COURSE_ID"));
	    		score.setCourseName(rs.getString("VC_COURSE_NAME"));
	    		score.setScore(rs.getFloat("F_SCORE"));
	    		list.add(score);	    		
	    	}  
			rs.close();
			stmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("获取学生分数信息失败！");
        } finally{
        	DBConnection.closeConnection();
		}		
        return list;
    }
    public ArrayList<ScoreInfo> getGradesByAjax(int major) {
        ResultSet rs=null;
        Statement stmt=null;
        ArrayList<ScoreInfo> list=new ArrayList<ScoreInfo>();
        try {
        	con=DBConnection.getConnection();
            stmt=con.createStatement();
            String sql="select distinct N_GRADE  from  t_course where N_MAJOR="+major;	
           // System.out.println("sql="+sql);
	    	rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		ScoreInfo score=new ScoreInfo();
	    		score.setGrade(rs.getInt("N_GRADE"));
	    		list.add(score);	    		
	    	}  
			rs.close();
			stmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("获取指定专业的年级信息失败！");
        } finally{
        	DBConnection.closeConnection();
		}		
        return list;
    }
    public ArrayList<ScoreInfo> getCoursesByAjax(int major,int grade) {
        ResultSet rs=null;
        Statement stmt=null;
        ArrayList<ScoreInfo> list=new ArrayList<ScoreInfo>();
        try {
        	con=DBConnection.getConnection();
            stmt=con.createStatement();
            String sql="select distinct N_COURSE_ID,VC_COURSE_NAME  from  t_course where N_MAJOR="+major +" and N_GRADE="+grade;	
            //System.out.println("sql="+sql);
	    	rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		ScoreInfo score=new ScoreInfo();
	    		score.setCourseID(rs.getInt("N_COURSE_ID"));
	    		score.setCourseName(rs.getString("VC_COURSE_NAME"));
	    		list.add(score);	    		
	    	}  
			rs.close();
			stmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("获取指定专业指定年级的课程信息失败！");
        } finally{
        	DBConnection.closeConnection();
		}		
        return list;
    }
    public ArrayList<ScoreInfo> getCourseScoresByAjax(int courseID) {
        ResultSet rs=null;
        Statement stmt=null;
        ArrayList<ScoreInfo> list=new ArrayList<ScoreInfo>();
        try {
        	con=DBConnection.getConnection();
            stmt=con.createStatement();
            String sql="select a.N_STUDENT_ID,b.VC_STUDENT_CODE,b.VC_STUDENT_NAME,a.N_COURSE_ID,a.F_SCORE "
            +"from t_score a,t_student b "
            +"where a.N_COURSE_ID="+courseID+" and a.N_STUDENT_ID=b.N_STUDENT_ID ";
            System.out.println("sql="+sql);
	    	rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		ScoreInfo score=new ScoreInfo();
	    		score.setStID(rs.getInt("N_STUDENT_ID"));
	    		score.setStCode(rs.getString("VC_STUDENT_CODE"));
	    		score.setStName(rs.getString("VC_STUDENT_NAME"));
	    		score.setCourseID(rs.getInt("N_COURSE_ID"));
	    		score.setScore(rs.getFloat("F_SCORE"));
	    		list.add(score);	    		
	    	}  
			rs.close();
			stmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("获取课程成绩信息失败！");
        } finally{
        	DBConnection.closeConnection();
		}		
        return list;
    }
    public ArrayList<ScoreInfo> getCourseAllScoresByAjax(int courseID) {
        ResultSet rs=null;
        Statement stmt=null;
        ArrayList<ScoreInfo> list=new ArrayList<ScoreInfo>();
        try {
        	con=DBConnection.getConnection();
            stmt=con.createStatement();
            String sql="select a.N_STUDENT_ID,a.VC_STUDENT_CODE,a.VC_STUDENT_NAME,b.N_COURSE_ID, "
            +"(select F_SCORE from t_score where N_COURSE_ID=b.N_COURSE_ID and N_STUDENT_ID=a.N_STUDENT_ID) F_SCORE "
            +"from t_student a,t_course b "
            +"where b.N_COURSE_ID="+courseID+" and a.N_MAJOR =b.N_MAJOR and a.N_GRADE =b.N_GRADE  ";
            //System.out.println("sql="+sql);
	    	rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		ScoreInfo score=new ScoreInfo();
	    		score.setStID(rs.getInt("N_STUDENT_ID"));
	    		score.setStCode(rs.getString("VC_STUDENT_CODE"));
	    		score.setStName(rs.getString("VC_STUDENT_NAME"));
	    		score.setCourseID(rs.getInt("N_COURSE_ID"));
	    		score.setScore(rs.getFloat("F_SCORE"));
	    		list.add(score);	    		
	    	}  
			rs.close();
			stmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("获取课程所有成绩信息失败！");
        } finally{
        	DBConnection.closeConnection();
		}		
        return list;
    }
    public int delCourseAllScores(int courseID) {
        int count=0;
        Statement stmt=null;
        try {
        	con=DBConnection.getConnection();
            stmt=con.createStatement();
            String sql="delete from t_score where  N_COURSE_ID="+courseID;	
            //System.out.println("sql="+sql);
            count=stmt.executeUpdate(sql);	    	
			stmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("删除课程所有成绩信息失败！");
        } finally{
        	DBConnection.closeConnection();
		}		
        return count;
    }
    public int insertCourseAllScores(ScoreInfo score) {
    	PreparedStatement pStmt=null; 
    	int count=0;
        try {
        	con=DBConnection.getConnection();
    		pStmt = con.prepareStatement("INSERT INTO t_score (N_STUDENT_ID,N_COURSE_ID,F_SCORE) VALUES (?,?,?)");
    		pStmt.setInt(1,score.getStID());		
    		pStmt.setInt(2,score.getCourseID());		
    		pStmt.setFloat(3,score.getScore());		
     		count=pStmt.executeUpdate();  
			pStmt.close();
        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("添加成绩信息失败！");
        } finally{
        	DBConnection.closeConnection();
		}		
        return count;
    }
}
