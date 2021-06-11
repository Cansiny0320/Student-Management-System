package frame;

import dao.ManageHelper;
import model.ScoreModel;
import utils.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ScoreAnalyzeOption extends JDialog {
    String collegeID = "";
    JTable jt;
    ScoreModel scoreModel;
    JLabel totalAvgLabel = new JLabel();
    JLabel goodLabel = new JLabel();
    JLabel passLabel = new JLabel();
    JLabel nopassLabel = new JLabel();

    /**
     * @param owner 它的父窗口
     * @param title 窗口名
     * @param modal 对话框操作模式，当modal为true时，代表用户必须结束对话框才能回到原来所属的窗口。当modal为 false时，代表对话框与所属窗口可以互相切换，彼此之间在操作上没有顺序性。
     */
    public ScoreAnalyzeOption(JFrame owner, String title, boolean modal) {
        super(owner, title, modal);
        ManageHelper helper = new ManageHelper();
        HashMap<String, String> colleges = helper.getAllCollege();    //获得所有学院

        //当前窗口。
        JDialog jd = this;
        Container c = this.getContentPane();
        JPanel jp3 = new JPanel();
        JButton exportBtn = new JButton("导出成绩");
        exportBtn.setVisible(false);
        JComboBox<String> collegeBox = new JComboBox<>(colleges.keySet().toArray(new String[0]));
        collegeBox.addActionListener(e -> {
            String collegeName;
            if (collegeBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(jd, "学院不能为空！", "", JOptionPane.WARNING_MESSAGE);
            } else {
                exportBtn.setVisible(true);
                collegeName = collegeBox.getSelectedItem().toString();    //获得院系名称
                collegeID = colleges.get(collegeName);    //获得院系编号
                scoreModel = new ScoreModel(collegeID);//构建新的数据模型类，并更新
                jt.setModel(scoreModel);
                int stuNum = scoreModel.getRowCount();
                double good = 0;
                double pass = 0;
                double totalAvg = 0;
                for (int i = 0; i < stuNum; i++) {
                    double avg = Double.parseDouble(scoreModel.getValueAt(i, 6));
                    totalAvg += avg;
                    if (avg >= 80) {
                        good++;
                        pass++;
                    } else if (avg >= 60) {
                        pass++;
                    }
                }
                totalAvg = totalAvg / stuNum;
                totalAvgLabel.setText("总平均成绩：" + totalAvg);
                goodLabel.setText("优秀率：" + (good / stuNum) * 100 + "%");
                passLabel.setText("及格率：" + (pass / stuNum) * 100 + "%");
                nopassLabel.setText("不及格率：" + (1 - (pass / stuNum)) * 100 + "%");
            }
        });
        jp3.add(collegeBox);
        jp3.add(exportBtn);
        exportBtn.addActionListener(e -> {
            try {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < scoreModel.getColumnCount(); i++) {
                    if (i == scoreModel.getColumnCount() - 1) {
                        result.append(scoreModel.getColumnName(i)).append("\r\n");
                    } else {
                        result.append(scoreModel.getColumnName(i)).append(",");
                    }
                }
                for (int j = 0; j < scoreModel.getRowCount(); j++) {
                    for (int i = 0; i < scoreModel.getColumnCount(); i++) {
                        if (i == scoreModel.getColumnCount() - 1) {
                            result.append(scoreModel.getValueAt(j, i)).append("\r\n");
                        } else {
                            result.append(scoreModel.getValueAt(j, i)).append(",");
                        }
                    }
                }
                BufferedWriter out = new BufferedWriter(new FileWriter("src/data/" + collegeBox.getSelectedItem() + "成绩表.csv"));
                out.write('\ufeff');
                out.write(result.toString());
                out.close();
                System.out.println("文件创建成功！");
            } catch (IOException err) {
                err.printStackTrace();
            }
        });
        c.add(jp3, BorderLayout.NORTH);
        JPanel jp1 = new JPanel();
        //表格。
        jt = new JTable();
        //学生数据模型
        scoreModel = new ScoreModel(collegeID);//构建新的数据模型类，并更新
        jt.setModel(scoreModel);
        //滚动条。
        JScrollPane jsp = new JScrollPane(jt);
        jp1.add(jsp);
        c.add(jp1, BorderLayout.WEST);    //添加面板
        Panel jp4 = new Panel();
        jp4.setLayout(new GridLayout(4, 1, 5, 5)); //5行1列
        jp4.add(totalAvgLabel);
        jp4.add(goodLabel);
        jp4.add(passLabel);
        jp4.add(nopassLabel);
        c.add(jp4, BorderLayout.CENTER);
        this.setSize(800, 540);
        this.setResizable(false);
        WindowUtil.setFrameCenter(this);//设置窗体居中。
        this.setVisible(true);
    }
}

