package dao;


import bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//与数据库通信的类


public class JdbcHelper implements JdbcConfig {
    //定义连接数据库所需要的对象
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Connection ct = null;

    //无参构造函数
    public JdbcHelper() {
        this.init();
    }

    private void init() {
        try {
            Class.forName(DRIVER);
            ct = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 获得数据库连接
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户对象
     *
     * @param username 根据传入的用户名，获取对应的用户，并返回用户对象
     * @return 用户对象
     */
    public User getUser(String username) {
        User user = new User();
        try {
            ps = ct.prepareStatement("select * from user where username=?");
            // ? 为占位符 , setString 为占位符赋值
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setUsername(rs.getString(1));    //设置用户名
                user.setPassword(rs.getString(2));    //设置密码
                user.setIsLogin(rs.getInt(3));    //设置是否登陆
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void setIsLogin(User user) {
        try {
            ps = ct.prepareStatement("update user set isLogin=? where username=?");
            ps.setInt(1, user.getIsLogin());
            ps.setString(2, user.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean register(User user) {
        try {
            ps = ct.prepareStatement("insert into user(username,password) values(?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            if (ps.executeUpdate() != 1) {    //执行sql语句
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updatePassword(User user, String newPassword) {
        try {
            ps = ct.prepareStatement("update user set password=? where username=?");
            ps.setString(1, newPassword);
            ps.setString(2, user.getUsername());
            if (ps.executeUpdate() != 1) {    //执行sql语句
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //关闭数据库资源
    public void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (ct != null) ct.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
