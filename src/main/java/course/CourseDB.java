package course;

import common.DBConnection;
import student.StudentInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseDB {
    private Connection con = null;
    //获取所有课程信息
    public ArrayList<CourseInfo> getAllCourses() {
        ResultSet rs=null;
        Statement sql=null;
        ArrayList<CourseInfo> courseList=new ArrayList<CourseInfo>();
        try {
            con= DBConnection.getConnection();
            sql=con.createStatement();
            rs=sql.executeQuery("SELECT * from t_course");
            while(rs.next()){
                CourseInfo course=new CourseInfo();
                course.setStID(rs.getInt("N_COURSE_ID"));
                course.setStName(rs.getString("VC_COURSE_NAME"));
                course.setType(rs.getInt("N_TYPE"));
                course.setCredit(rs.getFloat("F_CREDIT"));
                course.setGrade(rs.getInt("N_GRADE"));
                course.setMajor(rs.getInt("N_MAJOR"));
                course.setDetail(rs.getString("VC_DETAIL"));
                courseList.add(course);
//                System.out.println(course);
            }
            rs.close();
            sql.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取所有课程信息失败！");
        } finally{
            DBConnection.closeConnection();
        }
//        System.out.println(courseList);
        return courseList;
    }
    /*用id获取课程信息*/
    public CourseInfo getCourseById(int id) {
        ResultSet rs=null;
        PreparedStatement pStmt=null;
        CourseInfo course=null;
        try {
            con=DBConnection.getConnection();
            pStmt = con.prepareStatement("SELECT * FROM t_course where N_COURSE_ID=?");
            pStmt.setInt(1,id);
            rs = pStmt.executeQuery();
            if(rs.next()){
                course=new CourseInfo();
                course.setStID(rs.getInt("N_COURSE_ID"));
                course.setStName(rs.getString("VC_COURSE_NAME"));
                course.setType(rs.getInt("N_TYPE"));
                course.setCredit(rs.getFloat("F_CREDIT"));
                course.setGrade(rs.getInt("N_GRADE"));
                course.setMajor(rs.getInt("N_MAJOR"));
                course.setDetail(rs.getString("VC_DETAIL"));
                System.out.println(course.getCredit());

            }
            rs.close();
            pStmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取指定学生信息失败！");
        } finally{
            DBConnection.closeConnection();
        }
        return course;
    }
    /*更新课程信息*/
    public int updateCourse(CourseInfo course) {
        PreparedStatement pStmt=null;
        System.out.println(course.getMajor());
        System.out.println(course.getCredit());
        int count=0;
        try {
            con=DBConnection.getConnection();
            pStmt = con.prepareStatement("update t_course set VC_COURSE_NAME=?,N_TYPE=?,F_CREDIT=?,N_GRADE=?,N_MAJOR=?,VC_DETAIL=? where N_COURSE_ID=?");
            pStmt.setString(1,course.getStName());
            pStmt.setInt(2,course.getType());
            pStmt.setFloat(3,course.getCredit());
            pStmt.setInt(4,course.getGrade());
            pStmt.setInt(5,course.getMajor());
            pStmt.setString(6,course.getDetail());
            pStmt.setInt(7,course.getStID());
            count=pStmt.executeUpdate();
            pStmt.close();
            System.out.println(course.getCredit());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("修改课程信息失败！");
        } finally{
            DBConnection.closeConnection();
        }
        return count;
    }
    /*插入课程信息*/
    public int insertCourse(CourseInfo course) {
        PreparedStatement pStmt=null;
        int count=0;
        try {
            con=DBConnection.getConnection();
            pStmt = con.prepareStatement("insert into t_course (VC_COURSE_NAME,N_TYPE,F_CREDIT,N_GRADE,N_MAJOR,VC_DETAIL) values (?,?,?,?,?,?)");
            pStmt.setString(1,course.getStName());
            pStmt.setInt(2,course.getType());
            pStmt.setFloat(3,course.getCredit());
            pStmt.setInt(4,course.getGrade());
            pStmt.setInt(5,course.getMajor());
            pStmt.setString(6,course.getDetail());
            count=pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加学生失败！");
        } finally{
            DBConnection.closeConnection();
        }
        return count;
    }
    /* 删除学生信息 */
    public int deleteCourse(int id) {
        PreparedStatement pStmt=null;
        int count=0;
        try {
            con=DBConnection.getConnection();
            pStmt = con.prepareStatement("delete from  t_course  where N_COURSE_ID=?");
            pStmt.setInt(1,id);
            count=pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除学生信息失败！");
        } finally{
            DBConnection.closeConnection();
        }
        return count;
    }
}
