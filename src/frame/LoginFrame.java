package frame;

import bean.User;
import dao.ManageHelper;
import utils.WindowUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;


//登陆界面
public class LoginFrame extends JFrame {

    private final JTextField usernameText;    //用户名文本域。
    private final JPasswordField passwordText;    //密码文本域。
    private final JFrame jf;    //当前窗口 。

    public LoginFrame() {
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
        passwordLabel.setBounds(100, 140, 50, 20);
        c.add(passwordLabel);    //添加"密码"标签。

        passwordText = new JPasswordField();    //创建"密码"文本域。
        passwordText.setBounds(160, 140, 120, 20);    //设置"密码"文本域位置。
        c.add(passwordText);    //添加"密码"文本域。

        //登陆按钮。
        JButton loginButton = new JButton("登录");    //创建"登录"按钮。
        loginButton.setBounds(100, 210, 60, 20);    //设置"登录"按钮位置。
        //注册"登录"按钮事件监听。
        loginButton.addActionListener(e -> {
            String username = usernameText.getText().trim();
            String password = new String(passwordText.getPassword());
            if (username.equals("")) {
                JOptionPane.showMessageDialog(jf, "用户名不能为空", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (password.equals("")) {
                JOptionPane.showMessageDialog(jf, "密码不能为空", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //登录业务处理。
            User user = new User();//创建用户对象
            user.setUsername(username);
            user.setPassword(password);
            ManageHelper helper = new ManageHelper();//创建数据库业务处理对象
            if (helper.login(user)) {    //登陆业务处理
                if (helper.checkIsLogin(user)) {
                    JOptionPane.showMessageDialog(jf, "重复登陆！", "", JOptionPane.WARNING_MESSAGE);
                } else {
                    jf.dispose();//关闭当前窗口。
                    //修改登陆情况
                    user.setIsLogin(1);//修改成为已经登陆。
                    helper.updateIsLogin(user);
                    //打开主界面
                    user.setPassword("");//重置密码
                    new MainFrame(user);
                }
            } else {
                JOptionPane.showMessageDialog(jf, "用户名或密码错误！");
                reset();    //重置
            }

        });
        c.add(loginButton);    //添加"登录"按钮 。


        //注册按钮。
        JButton registerButton = new JButton("注册");    //创建"注册"按钮。
        registerButton.setBounds(250, 210, 60, 20);    //设置"注册"按钮位置。
        //注册"注册"按钮事件监听。
        registerButton.addActionListener(e -> {
            jf.dispose();    //当前窗口关闭。
            RegisterFrame registerFrame = new RegisterFrame();    //打开注册界面

        });
        c.add(registerButton);    //添加"注册"按钮。

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);    //设置大小不可改变。
        WindowUtil.setFrameCenter(this);//设置窗口居中。
        try {
            Image img = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/assets/school.jpg")));
            this.setIconImage(img);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        this.setVisible(true);    //设置窗体可见。
    }

    //重置
    public void reset() {
        usernameText.setText("");
        passwordText.setText("");
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
