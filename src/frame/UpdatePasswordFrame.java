package frame;

import bean.User;
import dao.ManageHelper;
import utils.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePasswordFrame extends JDialog {

    private final JPasswordField password_Text;    //原密码文本域。
    private final JPasswordField newpassword_Text;    //新密码文本域。
    private final JPasswordField repassword_Text;    //确认密码文本域。
    private final JDialog jd;    //当前窗口 。

    /**
     * @param owner 它的父窗口
     * @param title 窗口名
     * @param modal 对话框操作模式，当modal为true时，代表用户必须结束对话框才能回到原来所属的窗口。当modal为 false时，代表对话框与所属窗口可以互相切换，彼此之间在操作上没有顺序性。
     */
    public UpdatePasswordFrame(JFrame owner, String title, boolean modal, User user) {
        super(owner, title, modal);
        this.jd = this;
        this.setLayout(null);//设置为空布局。
        this.setSize(400, 300);//设置大小。
        Container c = this.getContentPane();
        c.setBackground(Color.WHITE);//设置背景颜色。
        //原密码标签。
        JLabel password_Label = new JLabel("原密码:");    //创建"原密码"标签。
        password_Label.setBounds(100, 60, 50, 20);    //设置"原密码"标签位置。
        c.add(password_Label);    //添加"密码"标签。

        password_Text = new JPasswordField();    //创建"原密码"文本域。
        password_Text.setBounds(160, 60, 120, 20);    //设置"原密码"文本域位置。
        password_Text.grabFocus();//获得光标。
        c.add(password_Text);    //添加"密码"文本域。

        //新密码标签。
        JLabel newpassword_Label = new JLabel("新密码:");    //创建"新密码"标签。
        newpassword_Label.setBounds(100, 110, 50, 20);
        c.add(newpassword_Label);    //添加"新密码"标签。

        newpassword_Text = new JPasswordField();    //创建"新密码"文本域。
        newpassword_Text.setBounds(160, 110, 120, 20);    //设置"新密码"文本域位置。
        c.add(newpassword_Text);    //添加"新密码"文本域。

        //确认密码标签。
        JLabel repassword_Label = new JLabel("确认密码");    //创建"确认密码"标签。
        repassword_Label.setBounds(100, 162, 70, 20);    //设置"确认密码"标签位置。
        c.add(repassword_Label);    //添加"确认密码"标签。

        repassword_Text = new JPasswordField();    //创建"确认密码"文本域。
        repassword_Text.setBounds(160, 162, 120, 20);    //设置"确认密码"文本域位置。
        c.add(repassword_Text); //添加"确认密码"文本域。

        //确认按钮。
        JButton confirm_Button = new JButton("确认");    //创建"确认"按钮。
        confirm_Button.setBounds(90, 210, 60, 20);    //设置"确认"按钮位置。
        //注册"确认"按钮事件监听。
        confirm_Button.addActionListener(e -> {
            String password = new String(password_Text.getPassword());
            String repassword = new String(repassword_Text.getPassword());
            String newpassword = new String(newpassword_Text.getPassword());
            if (password.equals("")) {
                JOptionPane.showMessageDialog(jd, "原密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newpassword.equals("")) {
                JOptionPane.showMessageDialog(jd, "新密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (repassword.equals("")) {
                JOptionPane.showMessageDialog(jd, "确认密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (repassword.equals(newpassword)) {
                //检查原密码是否正确
                ManageHelper helper = new ManageHelper();
                user.setPassword(password);
                if (helper.login(user)) {//检查原密码是否正确
                    if (helper.updatePassword(user, newpassword)) {
                        JOptionPane.showMessageDialog(jd, "修改密码成功！");
                        jd.dispose();//关闭当前窗口
                    } else {
                        JOptionPane.showMessageDialog(jd, "修改密码失败！");
                    }
                } else {
                    JOptionPane.showMessageDialog(jd, "原密码不正确！", "", JOptionPane.WARNING_MESSAGE);
                    reset();    //重置
                }
            } else {
                JOptionPane.showMessageDialog(jd, "两次密码不一致", "", JOptionPane.WARNING_MESSAGE);
                reset();    //重置
            }

        });
        c.add(confirm_Button);    //添加"确认"按钮。


        //取消按钮。
        JButton cancel_Button = new JButton("取消");    //创建"取消"按钮。
        cancel_Button.setBounds(250, 210, 60, 20);    //设置"取消"按钮位置。
        //注册"取消"按钮事件监听。
        cancel_Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jd.dispose();//关闭当前页面。

            }
        });
        c.add(cancel_Button);    //添加"取消"按钮。

        this.setResizable(false);    //设置大小不可改变。
        WindowUtil.setFrameCenter(this);//设置窗口居中。
        this.setVisible(true);    //设置窗体可见。

    }

    //重置
    public void reset() {
        password_Text.setText("");
        repassword_Text.setText("");
        newpassword_Text.setText("");
    }
}
