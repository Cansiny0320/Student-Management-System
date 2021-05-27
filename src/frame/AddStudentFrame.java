package frame;

import bean.Student;
import dao.ManageHelper;
import utils.Tools;
import utils.WindowUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddStudentFrame extends JDialog {
    ArrayList<String> collegeMajors;    //专业名称集合
    private JComboBox<String> majorBox;    //"专业"选项。

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
        HashMap<String, String> majors = helper.getAllMajor();    //所有专业

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

        //"性别"标签。
        JLabel sexLabel = new JLabel("性别:");
        sexLabel.setBounds(78, 128, 30, 20);
        this.add(sexLabel);

        //"性别"选项。
        JComboBox<String> sexBox = new JComboBox<>(new String[]{"", "男", "女"});
        sexBox.setBounds(116, 128, 60, 20);
        this.add(sexBox);

        //"年级标签"。
        JLabel gradeLabel = new JLabel("年级:");
        gradeLabel.setBounds(78, 168, 30, 20);
        this.add(gradeLabel);

        //"年级"文本域。
        JComboBox<String> gradeBox = new JComboBox<>(Tools.createGrade());    //需要获得获得年级选项
        gradeBox.setBounds(116, 168, 150, 20);
        this.add(gradeBox);

        //"所属学院"标签。
        JLabel collegeLabel = new JLabel("学院:");
        collegeLabel.setBounds(78, 208, 30, 20);
        this.add(collegeLabel);

        JComboBox<String> collegeBox = new JComboBox<>(colleges.keySet().toArray(new String[0]));
        //注册"学院"选项事件监听
        collegeBox.addActionListener(e -> {
            if (collegeBox.getSelectedItem() != null) {
                majorBox.removeAllItems();//移除"专业选项"的内容
                String option = collegeBox.getSelectedItem().toString();//获得选项名称
                String collegeID = colleges.get(option);    //获得学院id
                if (!collegeID.equals("")) {
                    collegeMajors = helper.getMajor(collegeID);    //获得学院专业
                    for (String s : collegeMajors) {
                        majorBox.addItem(s);
                    }
                }
            }
        });
        collegeBox.setBounds(116, 208, 150, 20);
        this.add(collegeBox);

        //"专业"标签。
        JLabel major_Label = new JLabel("专业:");
        major_Label.setBounds(78, 248, 30, 20);
        this.add(major_Label);

        majorBox = new JComboBox<>(new String[]{""});
        //注册"专业"选项事件监听
        majorBox.addActionListener(e -> {
            if (majorBox.getSelectedItem() != null) {
                if (!majorBox.getSelectedItem().toString().equals("")) {
                    if (majorBox.getSelectedItem().toString().equals("") || gradeBox.getSelectedItem() == null || gradeBox.getSelectedItem().toString().equals("")) {
                        JOptionPane.showMessageDialog(jd, "年级不能为空", "", JOptionPane.WARNING_MESSAGE);
                        majorBox.setSelectedIndex(0);    //设置为空选项
                    }
                }
            }
        });
        majorBox.setBounds(116, 248, 150, 20);
        this.add(majorBox);

        //"添加"按钮。
        JButton add_Button = new JButton("添加");
        add_Button.setBounds(70, 330, 60, 25);

        //注册"确认"按钮事件监听
        add_Button.addActionListener(e -> {
            Student student = new Student();
            String name = studentNameText.getText().trim();
            String sex = Objects.requireNonNull(sexBox.getSelectedItem()).toString();
            String grade = Objects.requireNonNull(gradeBox.getSelectedItem()).toString();
            String collegeID;
            String majorID;
            String collegeName;
            String majorName;
            //数据校验部分
            if (name.equals("")) {
                JOptionPane.showMessageDialog(jd, "姓名不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (sex.equals("")) {
                JOptionPane.showMessageDialog(jd, "性别不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (grade.equals("")) {
                JOptionPane.showMessageDialog(jd, "年级不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (collegeBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(jd, "院系不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                collegeName = collegeBox.getSelectedItem().toString();    //获得院系名称
                collegeID = colleges.get(collegeName);    //获得院系编号
            }
            if (collegeID.equals("")) {
                JOptionPane.showMessageDialog(jd, "学院不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (majorBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(jd, "专业不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                majorName = majorBox.getSelectedItem().toString();//获得专业名称
                majorID = majors.get(majorName);    //获得专业编号
            }
            if (majorID.equals("")) {
                JOptionPane.showMessageDialog(jd, "专业不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = Tools.createID(grade, collegeID);
            JOptionPane.showMessageDialog(jd, "该学生的学号为:" + id);
            student.setStudentID(id);
            student.setStudentName(name);
            student.setSex(sex);
            student.setGrade(grade);
            student.setMajorName(majorName);
            student.setCollegeName(collegeName);
            student.setMajorID(majorID);
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
