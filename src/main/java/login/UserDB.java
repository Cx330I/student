package login;

import common.DBConnection;
import course.CourseDB;
import course.CourseInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDB {
    private Connection con = null;
    public ArrayList<UserInfo> getAllUser(){
        ResultSet rs=null;
        Statement sql=null;
        ArrayList<UserInfo> userList=new ArrayList<UserInfo>();
        try {
            con= DBConnection.getConnection();
            sql=con.createStatement();
            rs=sql.executeQuery("SELECT * from t_user where VC_LOGIN_NAME != 'admin' ");

            while(rs.next()){
                UserInfo user = new UserInfo();
                user.setUserID(rs.getInt("N_USER_ID"));
                user.setUserName(rs.getString("VC_LOGIN_NAME"));
                user.setUserPwd(rs.getString("VC_PASSWORD"));
                userList.add(user);
//                System.out.println(course);
            }
            rs.close();
            sql.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取所有用户信息失败！");
        } finally{
            DBConnection.closeConnection();
        }
//        System.out.println(courseList);
        return userList;
    }
    public UserInfo getUserByName(String userName){
        UserInfo user = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Statement sql = null;
        try {
            //TODO 1、使用通用方法获取数据库连接
            con = DBConnection.getConnection();
            //TODO 2、使用预编译语句对象查询数据
            pStmt = con.prepareStatement("SELECT * FROM t_user where VC_LOGIN_NAME = ?");
            //TODO 3、将查询道德用户信息封装到user对象
            pStmt.setString(1,userName);
            rs = pStmt.executeQuery();
            while(rs.next()){
                user = new UserInfo();
                user.setUserID(rs.getInt("N_USER_ID"));
                user.setUserName(rs.getString("VC_LOGIN_NAME"));
                user.setUserPwd(rs.getString("VC_PASSWORD"));
            }
            //TODO 4、释放对象
            rs.close();
            pStmt.close();
        } catch (Exception e) {
            System.out.println("获取指定用户信息失败！");
        }finally {
            DBConnection.closeConnection();
        }
        return user;
    }

}
