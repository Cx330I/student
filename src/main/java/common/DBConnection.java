package common;

import java.sql.*;

public class DBConnection {
    static Connection con = null;

    /**获取数据库连接*/
    public static Connection getConnection() {
        if (con == null) {
            try { //加载驱动程序
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("加载MYSQL数据库驱动失败！");
            }
            try { //通过驱动程序管理器获取数据库连接
                //mysql如果你的字符集没有设置，或者没有设置好的话，默认是latin1
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8", "root", "abc123");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("获取数据库连接失败！");
            }
        }
        return con; 
    }

    /** 关闭数据库的操作 */
    public static void closeConnection() {
        if (con != null)
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("关闭con对象失败！");
            }
    }

}