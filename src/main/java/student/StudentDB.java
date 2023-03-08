package student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.DBConnection;

public class StudentDB {
    private Connection con = null;
    /* ��ȡ����ѧ����Ϣ */
    public ArrayList<StudentInfo> getAllStudents() {
        ResultSet rs=null;
        Statement sql=null;
        ArrayList<StudentInfo> studentList=new ArrayList<StudentInfo>();
        try {
        	con=DBConnection.getConnection();
            sql=con.createStatement();
	    	rs=sql.executeQuery("SELECT * from t_student");
	    	while(rs.next()){
	    		StudentInfo student=new StudentInfo();
	    		student.setStID(rs.getInt("N_STUDENT_ID"));
	    		student.setStCode(rs.getString("VC_STUDENT_CODE"));
	    		student.setStName(rs.getString("VC_STUDENT_NAME"));
	    		student.setSex(rs.getInt("N_SEX"));
	    		student.setGrade(rs.getInt("N_GRADE"));
	    		student.setMajor(rs.getInt("N_MAJOR"));
	    		student.setDetail(rs.getString("VC_DETAIL"));
	    		studentList.add(student);	    		
	    	}  
			rs.close();
			sql.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("��ȡ����ѧ����Ϣʧ�ܣ�");
        } finally{
        	DBConnection.closeConnection();
		}		
        return studentList;
    }
    /* ��ȡָ��ѧ����Ϣ */
    public StudentInfo getStudentById(int id) {
				ResultSet rs=null;
				PreparedStatement pStmt=null;
				StudentInfo student=null;
				try {
					con=DBConnection.getConnection();
					pStmt = con.prepareStatement("SELECT * FROM t_student where N_STUDENT_ID=?");
					pStmt.setInt(1,id);
					rs = pStmt.executeQuery();
					if(rs.next()){
						student=new StudentInfo();
						student.setStID(rs.getInt("N_STUDENT_ID"));
						student.setStCode(rs.getString("VC_STUDENT_CODE"));
						student.setStName(rs.getString("VC_STUDENT_NAME"));
						student.setSex(rs.getInt("N_SEX"));
						student.setGrade(rs.getInt("N_GRADE"));
						student.setMajor(rs.getInt("N_MAJOR"));
						student.setDetail(rs.getString("VC_DETAIL"));
					}
					rs.close();
					pStmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("��ȡָ��ѧ����Ϣʧ�ܣ�");
        } finally{
        	DBConnection.closeConnection();
		}		
        return student;
    }

    /* ���ѧ����Ϣ */
    public int insertStudent(StudentInfo student) {
    	PreparedStatement pStmt=null; 
    	int count=0;
        try {
        	con=DBConnection.getConnection();
    		pStmt = con.prepareStatement("insert into t_student (VC_STUDENT_CODE,VC_STUDENT_NAME,N_SEX,N_GRADE,N_MAJOR,VC_DETAIL) values (?,?,?,?,?,?)");
    		pStmt.setString(1,student.getStCode());		
    		pStmt.setString(2,student.getStName());		
    		pStmt.setInt(3,student.getSex());		
    		pStmt.setInt(4,student.getGrade());		
    		pStmt.setInt(5,student.getMajor());		
    		pStmt.setString(6,student.getDetail());		
    		count=pStmt.executeUpdate();  
			pStmt.close();
        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("���ѧ��ʧ�ܣ�");
        } finally{
        	DBConnection.closeConnection();
		}		
        return count;
    }    
    /* �޸�ѧ����Ϣ */
    public int updateStudent(StudentInfo student) {
    	PreparedStatement pStmt=null; 
    	int count=0;
        try {
        	con=DBConnection.getConnection();
    		pStmt = con.prepareStatement("update t_student set VC_STUDENT_CODE=?,VC_STUDENT_NAME=?,N_SEX=?,N_GRADE=?,N_MAJOR=?,VC_DETAIL=? where N_STUDENT_ID=?");
    		pStmt.setString(1,student.getStCode());		
    		pStmt.setString(2,student.getStName());		
    		pStmt.setInt(3,student.getSex());		
    		pStmt.setInt(4,student.getGrade());		
    		pStmt.setInt(5,student.getMajor());	
    		pStmt.setString(6,student.getDetail());		    		
    		pStmt.setInt(7,student.getStID());		
    		count=pStmt.executeUpdate();
			pStmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("�޸�ѧ����Ϣʧ�ܣ�");
        } finally{
        	DBConnection.closeConnection();
		}		
        return count;
    }  
    /* ɾ��ѧ����Ϣ */
    public int deleteStudent(int id) {
     	PreparedStatement pStmt=null; 
    	int count=0;
        try {
        	con=DBConnection.getConnection();
    		pStmt = con.prepareStatement("delete from  t_student   where N_STUDENT_ID=?");
    		pStmt.setInt(1,id);		
    		count=pStmt.executeUpdate();
    		pStmt.close();    		
        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("ɾ��ѧ����Ϣʧ�ܣ�");
        } finally{
        	DBConnection.closeConnection();
		}		
        return count;
    } 
    /* ��ȡָ��ѧ������ */
    public int getStudentCountByCode(String code,int id) {
        ResultSet rs=null;
    	PreparedStatement pStmt=null; 
    	int count=0;
        try {
        	con=DBConnection.getConnection();
        	if(id!=0){//�޸�ѧ��ѧ��ʱ
	    		pStmt = con.prepareStatement("SELECT count(*) C FROM t_student where  VC_STUDENT_CODE=? and N_STUDENT_ID<>?");
	    		pStmt.setString(1,code);
	    		pStmt.setInt(2,id);	
        	}else{//�����ѧ��
	    		pStmt = con.prepareStatement("SELECT count(*) C FROM t_student where  VC_STUDENT_CODE=?");
	    		pStmt.setString(1,code);	       		
        	}
    		rs = pStmt.executeQuery();
	    	if(rs.next()){
	    		count=rs.getInt("C");
	    	} 
			rs.close();
			pStmt.close();

        } catch (Exception e) {
           	e.printStackTrace();
            System.out.println("��ȡָ��ѧ��������Ϣʧ�ܣ�");
        } finally{
        	DBConnection.closeConnection();
		}		
        return count;
    }
    
}
