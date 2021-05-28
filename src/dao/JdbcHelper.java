package dao;


import bean.AnalyzeResult;
import bean.Student;
import bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public HashMap<String, String> getAllCollege() {
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("", "");//添加一个空的元素
        try {
            ps = ct.prepareStatement("select * from college order by college_id");
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString(2), rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public boolean addStudent(Student student) {
        boolean b = true;
        try {
            ps = ct.prepareStatement("insert into student values(?,?,?,?)");
            ps.setString(1, student.getStudentID());
            ps.setString(2, student.getStudentName());
            ps.setString(3, student.getCollegeID());
            ps.setString(4, student.getCollegeName());
            if (ps.executeUpdate() != 1) {
                b = false;
            }
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }

    public int getStudentNum() {
        int result = 0;
        try {
            ps = ct.prepareStatement("select count(*) from student");
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Student> getStudent() {
        ArrayList<Student> result = new ArrayList<>();
        try {
            ps = ct.prepareStatement("select * from student");
            rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStudentID(rs.getString(1));
                student.setStudentName(rs.getString(2));
                student.setCollegeID(rs.getString(3));
                student.setCollegeName(rs.getString(4));
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Student> getStudentByCollege(String collegeID) {
        ArrayList<Student> result = new ArrayList<>();
        try {
            ps = ct.prepareStatement("select * from student where college_id=?");
            ps.setString(1, collegeID);
            rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStudentID(rs.getString(1));
                student.setStudentName(rs.getString(2));
                student.setCollegeID(rs.getString(3));
                student.setCollegeName(rs.getString(4));
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteStudent(String studentID) {
        try {
            ps = ct.prepareStatement("delete from student where student_id=?");
            ps.setString(1, studentID);
            if (ps.executeUpdate() != 1) {    //执行sql语句
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<String> getCourse(String collegeID) {
        ArrayList<String> result = new ArrayList<>();
        try {
            ps = ct.prepareStatement("select course_name from course where college_id = ?");
            ps.setString(1, collegeID);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return result;
    }

    public HashMap<String, String> getStudentScore(String StudentID) {
        HashMap<String, String> result = new HashMap<>();
        try {
            ps = ct.prepareStatement("select course_name,score from score where student_id=? order by student_id asc");
            ps.setString(1, StudentID);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return result;
    }

    public boolean addStudentScore(Student student, ArrayList<String> courses) {
        HashMap<String, String> map = student.getScores();
        try {
            for (int i = 0; i < map.size(); i++) {
                ps = ct.prepareStatement("insert into score(course_name,student_id,student_name,score) values (?,?,?,?)");
                ps.setString(1, courses.get(i));
                ps.setString(2, student.getStudentID());
                ps.setString(3, student.getStudentName());
                ps.setString(4, map.get(courses.get(i)));
                if (ps.executeUpdate() != 1) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<AnalyzeResult> analyzeScore(String collegeID) {
        ArrayList<AnalyzeResult> result = new ArrayList<>();
        try {
            ps = ct.prepareStatement("select  student_id,student_name,sum(score),avg(score) from score where student_id in (select student_id from student where college_id=?)GROUP BY student_id order by score desc");
            ps.setString(1, collegeID);
            rs = ps.executeQuery();
            while (rs.next()) {
                AnalyzeResult r = new AnalyzeResult();
                r.setStudentId(rs.getString(1));
                r.setStudentName(rs.getString(2));
                r.setSumScore(rs.getString(3));
                r.setAvgScore(rs.getString(4));
                result.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
