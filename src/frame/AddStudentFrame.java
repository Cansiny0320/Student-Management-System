package frame;

import bean.Student;
import dao.ManageHelper;
import utils.Tools;
import utils.WindowUtil;

import javax.swing.*;
import java.util.HashMap;

public class AddStudentFrame extends JDialog {
    /**
     * @param owner 它的父窗口
     * @param title 窗口名
     * @param modal 对话框操作模式，当modal为true时，代表用户必须结束对话框才能回到原来所属的窗口。当modal为 false时，代表对话框与所属窗
     *              口可以互相切换，彼此之间在操作上没有顺序性。
     */
    public AddStudentFrame(JFrame owner, String title, boolean modal) {
        super(owner, title, modal);

        ManageHelper helper = new ManageHelper();

        HashMap<String, String> colleges = helper.getAllCollege();    //获得所有学院
        //当前窗口。
        JDialog jd = this;
        this.setSize(350, 429);    //设置窗体大小。
        this.setLayout(null);    //设置空布局。

        //"姓名"标签。
        JLabel studentName = new JLabel("姓名:");
        studentName.setBounds(78, 88, 30, 20);
        this.add(studentName);

        //"姓名"文本域。
        JTextField studentNameText = new JTextField();
        studentNameText.setBounds(116, 88, 150, 20);
        this.add(studentNameText);

        //"所属学院"标签。
        JLabel collegeLabel = new JLabel("学院:");
        collegeLabel.setBounds(78, 208, 30, 20);
        this.add(collegeLabel);

        JComboBox<String> collegeBox = new JComboBox<>(colleges.keySet().toArray(new String[0]));
        collegeBox.setBounds(116, 208, 150, 20);
        this.add(collegeBox);

        //"添加"按钮。
        JButton add_Button = new JButton("添加");
        add_Button.setBounds(70, 330, 60, 25);

        //注册"确认"按钮事件监听
        add_Button.addActionListener(e -> {
            Student student = new Student();
            String name = studentNameText.getText().trim();
            String collegeID;
            String collegeName;
            //数据校验部分
            if (name.equals("")) {
                JOptionPane.showMessageDialog(jd, "姓名不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (collegeBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(jd, "学院不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                collegeName = collegeBox.getSelectedItem().toString();    //获得院系名称
                collegeID = colleges.get(collegeName);    //获得院系编号
            }
            if (collegeID.equals("")) {
                JOptionPane.showMessageDialog(jd, "学院不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = Tools.createID(collegeID);
            JOptionPane.showMessageDialog(jd, "该学生的学号为:" + id);
            student.setStudentID(id);
            student.setStudentName(name);
            student.setCollegeName(collegeName);
            student.setCollegeID(collegeID);
            if (helper.addStudent(student)) {
                JOptionPane.showMessageDialog(jd, "添加成功！");
                jd.dispose();    //关闭当前窗口
            } else {
                JOptionPane.showMessageDialog(jd, "添加失败！", "", JOptionPane.WARNING_MESSAGE);
                jd.dispose();    //关闭当前窗口
            }


        });
        this.add(add_Button);

        //"取消"按钮。
        JButton cancel_Button = new JButton("取消");
        cancel_Button.setBounds(230, 330, 60, 25);
        //注册"取消"按钮事件监听
        cancel_Button.addActionListener(e -> jd.dispose());
        this.add(cancel_Button);

        WindowUtil.setFrameCenter(this);
        this.setResizable(false);
        this.setVisible(true);

    }
}
