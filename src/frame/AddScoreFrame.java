package frame;

import bean.Student;
import dao.ManageHelper;
import model.StudentModel;
import utils.WindowUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

//添加成绩界面
public class AddScoreFrame extends JDialog {
    private final ManageHelper helper;
    private final ArrayList<String> courses;
    private final JDialog jd;
    private final HashMap<String, JTextField> jtextFieldHashMap;//管理文本域的集合
    private final HashMap<String, String> scores;    //学生所有成绩

    /**
     * @param owner 它的父窗口
     * @param title 窗口名
     * @param modal 指定的模式窗口，还有非模式窗口
     */
    public AddScoreFrame(JDialog owner, String title, boolean modal, StudentModel sm, int rowNum) {
        super(owner, title, modal);
        this.jd = this;
        this.setLayout(null);

        helper = new ManageHelper();
        jtextFieldHashMap = new HashMap<>();
        courses = helper.getCourse(helper.getAllCollege().get(sm.getValueAt(rowNum, 2)));//获得所有课程
        scores = helper.getStudentScore(sm.getValueAt(rowNum, 0));//根据学号获得该学生的所有科目成绩
        int vgap = 0;    //垂直间距
        if (!scores.isEmpty()) {
            JOptionPane.showMessageDialog(jd, "该学生已经有成绩了！", "", JOptionPane.WARNING_MESSAGE);
            jd.dispose();
            return;
        }
        for (String cours : courses) {
            JLabel jLabel = new JLabel(cours + ":");
            jLabel.setBounds(78, 48 + vgap, 120, 20);
            JTextField field = new JTextField();
            field.setBounds(206, 48 + vgap, 150, 20);
            jtextFieldHashMap.put(cours, field);    //添加入管理文本域的集合
            this.add(jLabel);
            this.add(field);
            vgap += 30;
        }
        //添加按钮
        JButton add_button = new JButton("添加");
        add_button.setBounds(120, 48 + vgap + 5, 60, 20);
        //注册"添加"按钮事件监听
        add_button.addActionListener(arg0 -> {
            // TODO Auto-generated method stub
            for (String cours : courses) {
                JTextField field = jtextFieldHashMap.get(cours);
                String score = field.getText().trim();
                if (score.equals("")) {
                    JOptionPane.showMessageDialog(jd, "成绩不能为空！", "", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (Double.parseDouble(score) < 0 || Double.parseDouble(score) > 100) {
                    JOptionPane.showMessageDialog(jd, "请输入0~100之间的成绩", "", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            Student student = new Student();
            student.setStudentID(sm.getValueAt(rowNum, 0));
            student.setStudentName(sm.getValueAt(rowNum, 1));
            for (String cours : courses) {
                JTextField field = jtextFieldHashMap.get(cours);
                String score = field.getText().trim();
                scores.put(cours, score);
            }
            student.setScores(scores);
            boolean b = helper.addStudentScore(student, courses);
            if (!b) {
                JOptionPane.showMessageDialog(jd, "添加失败！", "", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(jd, "添加成功！", "", JOptionPane.WARNING_MESSAGE);
                jd.dispose();
            }

        });
        this.add(add_button);
        //取消
        JButton cancel_button = new JButton("取消");
        cancel_button.setBounds(280, 48 + vgap + 5, 60, 20);
        cancel_button.addActionListener(e -> jd.dispose());
        this.add(cancel_button);
        this.setSize(450, 48 + vgap + 78);
        WindowUtil.setFrameCenter(this);
        this.setVisible(true);

    }
}
