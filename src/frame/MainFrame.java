package frame;

import bean.User;
import dao.ManageHelper;
import utils.WindowUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Objects;

public class MainFrame extends JFrame {

    public MainFrame(User user) {
        super("学生成绩管理系统,欢迎你! " + user.getUsername());
        JFrame jf = this;
        //应用菜单条。
        JMenuBar menuBar = new JMenuBar();    //创建菜单条。
        this.setJMenuBar(menuBar);    //添加菜单条。

        //"学生管理"菜单。
        JMenu studentManagement = new JMenu("学生管理");    //创建"学生管理"菜单。
        menuBar.add(studentManagement);    //添加"学生管理"菜单。

        //"添加学生"菜单项。
        JMenuItem addStudent = new JMenuItem("添加学生");    //创建"添加学生"菜单项。
        addStudent.addActionListener(e -> new AddStudentFrame(jf, "添加学生", true));
        studentManagement.add(addStudent);

        JMenuItem queryStudent = new JMenuItem("查询学生信息");    //"查询学生信息"菜单项。
        queryStudent.addActionListener(e -> new queryStudentFrame(jf, "查询学生信息", true));
        studentManagement.add(queryStudent);

        //"修改学生信息"菜单项。
        JMenuItem modify_Student = new JMenuItem("修改学生信息");    //创建"修改学生信息"菜单项。
        //注册"修改学生信息"菜单项事件监听
        modify_Student.addActionListener(e -> new ModifyStudentFrame(jf, "修改学生信息", true));
        studentManagement.add(modify_Student);    //添加"修改学生"菜单项。

        //"删除学生"菜单项。
        JMenuItem delete_Student = new JMenuItem("删除学生");    //创建"删除学生"菜单项。
        //注册"删除学生"按钮事件监听
        delete_Student.addActionListener(e -> new DeleteStudentFrame(jf, "删除学生", true));
        studentManagement.add(delete_Student);    //添加"删除学生"菜单项.

        JMenu scoreManagement = new JMenu("成绩管理");    //创建"成绩管理"菜单。
        menuBar.add(scoreManagement);    //添加"成绩管理"菜单。

        JMenuItem addScore = new JMenuItem("添加学生成绩");    //创建"添加学生成绩"菜单项。
        //注册"添加学生成绩"菜单项事件监听
        addScore.addActionListener(e -> new AddStudentScoreFrame(jf, "添加学生成绩", true));
        scoreManagement.add(addScore);    //添加"添加学生成绩"菜单项。

        //"修改学生成绩"菜单项。
        JMenuItem modifyScore = new JMenuItem("修改学生成绩");    //创建"修改学生成绩"菜单项。
        //注册"修改学生成绩"菜单项事件监听
        modifyScore.addActionListener(e -> new UpdateStudentScoreFrame(jf, "修改学生成绩", true));
        scoreManagement.add(modifyScore);    //添加"修改学生成绩"菜单项。

        JMenuItem queryScore = new JMenuItem("成绩查询");    //创建"成绩查询"菜单项。
        //注册"成绩查询"菜单项事件监听
        queryScore.addActionListener(e -> new QueryStudentScoreFrame(jf, "查询学生成绩", true));
        scoreManagement.add(queryScore);    //添加"成绩查询"菜单项。

        JMenuItem scoreStatistics = new JMenuItem("成绩统计");    //创建"成绩统计"菜单项。
        scoreStatistics.addActionListener(e -> new ScoreAnalyzeOption(jf, "成绩分析选项", true));
        scoreManagement.add(scoreStatistics);    //添加"成绩统计"菜单项。

        //"个人管理"菜单。
        JMenu personalManagement = new JMenu("个人管理");    //创建"个人管理"菜单。
        menuBar.add(personalManagement);    //添加"个人管理"菜单。

        JMenuItem changePassword = new JMenuItem("修改密码");    //创建"修改密码"菜单项。
        //注册"修改密码"菜单项事件监听
        changePassword.addActionListener(e -> new UpdatePasswordFrame(jf, "修改密码", true, user));
        personalManagement.add(changePassword);    //添加"修改密码"菜单项。

        JMenuItem logout = new JMenuItem("退出登录");    //创建"退出登录"菜单项。
        //注册"退出登录"菜单项时间监听
        logout.addActionListener(e -> {
            jf.dispose();    //关闭当前窗口
            //修改登陆状态
            ManageHelper helper = new ManageHelper();
            user.setIsLogin(0); //设置登陆状态为未登录。
            helper.updateIsLogin(user);
            new LoginFrame();    //打开登陆界面
        });
        personalManagement.add(logout);    //添加"退出登录"菜单项。

        this.setSize(578, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        WindowUtil.setFrameCenter(this);//设置窗体居中。
        this.getContentPane().add(new ImagePanel());

        try {
            Image img = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/school.jpg")));
            this.setIconImage(img);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                ManageHelper helper = new ManageHelper();
                user.setIsLogin(0);//设置登陆状态为未登录。
                helper.updateIsLogin(user);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
        this.setVisible(true);//设置窗体可见。

    }

}
