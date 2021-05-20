package dao;

import bean.Student;
import bean.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageHelper {
    private JdbcHelper helper;    //与数据库通信的对象

    /**
     * 登陆业务处理
     *
     * @param user 用户对象
     * @return 返回是否成功登陆
     */
    public boolean login(User user) {
        helper = new JdbcHelper();    //创建与数据库通信的对象
        User newUser = helper.getUser(user.getUsername());    //获得用户数据
        if (!user.getPassword().equals(newUser.getPassword())) {    //比对密码与数据库中的对应密码是否一致
            return false;
        }
        helper.close();//关闭资源
        return true;
    }

    /**
     * 检查是否重复登陆的方法
     *
     * @param user 用户对象
     * @return 是否重复登陆
     */
    public boolean checkIsLogin(User user) {
        helper = new JdbcHelper();
        User newUser = helper.getUser(user.getUsername());
        if (newUser.getIsLogin() == 0) {
            return false;
        }
        helper.close();//关闭资源
        return true;
    }

    /**
     * 更新登录状态
     */
    public void updateIsLogin(User user) {
        helper = new JdbcHelper();
        helper.setIsLogin(user);
        helper.close();
    }

    /**
     * 注册
     */
    public boolean register(User user) {
        helper = new JdbcHelper();//创建与数据库通信的对象
        boolean result = helper.register(user);
        helper.close();//关闭资源
        return result;
    }

    public boolean updatePassword(User user, String newPassword) {
        helper = new JdbcHelper();//创建与数据库通信的对象
        boolean result = helper.updatePassword(user, newPassword);
        helper.close();//关闭资源
        return result;
    }

    public HashMap<String, String> getAllCollege() {
        helper = new JdbcHelper();
        HashMap<String, String> map;
        map = helper.getAllCollege();
        helper.close();//关闭资源
        return map;
    }

    public HashMap<String, String> getAllMajor() {
        helper = new JdbcHelper();
        HashMap<String, String> map;
        map = helper.getAllMajor();
        helper.close();//关闭资源
        return map;
    }


    public ArrayList<String> getMajor(String collegeID) {
        helper = new JdbcHelper();
        ArrayList<String> arrayList;
        arrayList = helper.getMajor(collegeID);
        helper.close();
        return arrayList;
    }

    public boolean addStudent(Student student) {
        boolean b;
        helper = new JdbcHelper();
        b = helper.addStudent(student);
        helper.close();//关闭资源
        return b;
    }
}
