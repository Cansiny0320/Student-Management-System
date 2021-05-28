package frame;

import bean.User;
import dao.ManageHelper;
import utils.WindowUtil;

import javax.swing.*;
import java.awt.*;


//登陆界面
public class RegisterFrame extends JFrame {

    private final JTextField usernameText;    //用户名文本域。
    private final JPasswordField passwordText;    //密码文本域。
    private final JPasswordField repasswordText;    //密码文本域。
    private final JFrame jf;    //当前窗口 。

    public RegisterFrame() {
        super("学生成绩管理系统登录界面");
        this.jf = this;
        this.setLayout(null);//设置为空布局。
        this.setSize(400, 300);//设置大小。
        Container c = this.getContentPane();
        c.setBackground(Color.WHITE);//设置背景颜色。
        //用户名标签。
        JLabel usernameLabel = new JLabel("用户名:");    //创建"用户名"标签。
        usernameLabel.setBounds(100, 60, 50, 20);    //设置"用户名"标签位置。
        c.add(usernameLabel);    //添加"用户名"标签。

        usernameText = new JTextField();    //创建"用户名"文本域。
        usernameText.setBounds(160, 60, 120, 20);    //设置"用户名"文本域位置。
        usernameText.grabFocus();//获得光标。
        c.add(usernameText);    //添加"用户名"文本域。

        //密码标签。
        JLabel passwordLabel = new JLabel("密码:");    //创建"密码"标签。
        passwordLabel.setBounds(100, 110, 50, 20);
        c.add(passwordLabel);    //添加"密码"标签。


        passwordText = new JPasswordField();    //创建"密码"文本域。
        passwordText.setBounds(160, 110, 120, 20);    //设置"密码"文本域位置。
        c.add(passwordText);    //添加"密码"文本域。

        JLabel repasswordLabel = new JLabel("确认密码");    //创建"确认密码"标签。
        repasswordLabel.setBounds(100, 162, 70, 20);    //设置"确认密码"标签位置。
        c.add(repasswordLabel);    //添加"确认密码"标签。

        repasswordText = new JPasswordField();    //创建"确认密码"文本域。
        repasswordText.setBounds(160, 162, 120, 20);    //设置"确认密码"文本域位置。
        c.add(repasswordText); //添加"确认密码"文本域。


        //确认按钮。
        JButton submitButton = new JButton("确认");
        submitButton.setBounds(100, 210, 60, 20);
        submitButton.addActionListener(e -> {
            String username = usernameText.getText().trim();//获得用户名。
            String password = new String(passwordText.getPassword());    //获得密码。
            String repassword = new String(repasswordText.getPassword());    //获得确认密码
            if (username.equals("")) {
                JOptionPane.showMessageDialog(jf, "用户名不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (password.equals("")) {
                JOptionPane.showMessageDialog(jf, "密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (repassword.equals("")) {
                JOptionPane.showMessageDialog(jf, "确认密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!password.equals(repassword)) {
                JOptionPane.showMessageDialog(jf, "两次密码不一致！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //注册业务处理。
            User user = new User();//创建用户对象
            user.setUsername(username);
            user.setPassword(password);
            ManageHelper helper = new ManageHelper();//创建数据库业务处理对象
            if (helper.register(user)) {
                JOptionPane.showMessageDialog(jf, "注册成功！");
                jf.dispose();//关闭当前窗口
                LoginFrame frame = new LoginFrame();//返回登陆页面。
            } else {
                JOptionPane.showMessageDialog(jf, "注册失败！");
                reset();    //重置
            }
        });
        c.add(submitButton);
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(250, 210, 60, 20);
        cancelButton.addActionListener(e -> {
            jf.dispose();
            new LoginFrame();
        });
        c.add(cancelButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);    //设置大小不可改变。
        WindowUtil.setFrameCenter(this);//设置窗口居中。
        this.setVisible(true);

    }

    //重置
    public void reset() {
        usernameText.setText("");
        passwordText.setText("");
    }

}

