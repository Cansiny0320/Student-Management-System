package frame;

import dao.ManageHelper;
import model.StudentModel;
import utils.WindowUtil;

import javax.swing.*;
import java.awt.*;


//删除学生界面
public class DeleteStudentFrame extends JDialog {
    private final JTable jt;    //表格。
    private final JDialog jd;    //当前窗口。
    private StudentModel studentModel;    //学生数据模型


    /**
     * @param owner 它的父窗口
     * @param title 窗口名
     * @param modal 指定的模式窗口，还有非模式窗口
     */
    public DeleteStudentFrame(JFrame owner, String title, boolean modal) {
        super(owner, title, modal);
        this.jd = this;
        Container c = this.getContentPane();


        JPanel jp2 = new JPanel();
        jt = new JTable();

        studentModel = new StudentModel(jd);//构建新的数据模型类，并更新
        jt.setModel(studentModel);
        //滚动条。
        JScrollPane jsp = new JScrollPane(jt);
        jp2.add(jsp);
        c.add(jp2, BorderLayout.CENTER);    //添加面板

        //面板。
        JPanel jp3 = new JPanel();
        //"删除"按钮。
        JButton delete_Button = new JButton("删除");
        //注册"删除"按钮事件监听
        delete_Button.addActionListener(e -> {
            int rowNum = jt.getSelectedRow();
            if (rowNum == -1) {
                JOptionPane.showMessageDialog(jd, "请选择一行！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String studentID = jt.getValueAt(rowNum, 0).toString();
            ManageHelper helper = new ManageHelper();
            if (helper.deleteStudent(studentID)) {
                JOptionPane.showMessageDialog(jd, "删除成功！");
                //更新
                studentModel = new StudentModel(jd);//构建新的数据模型类，并更新
                jt.setModel(studentModel);
            } else {
                JOptionPane.showMessageDialog(jd, "删除失败！", "", JOptionPane.WARNING_MESSAGE);
            }
        });
        jp3.add(delete_Button);
        c.add(jp3, BorderLayout.SOUTH);
        this.setSize(600, 540);
        this.setResizable(false);
        WindowUtil.setFrameCenter(this);//设置窗体居中。
        this.setVisible(true);
    }
}
