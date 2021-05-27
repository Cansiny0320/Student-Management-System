package model;

import bean.Student;
import dao.ManageHelper;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

//rowData用来存放行数据
//columnNames存放列名
public class StudentModel extends AbstractTableModel {
    private final ArrayList<String> columnNames;    //列名
    private final ArrayList<ArrayList<String>> rowData;    //行数据


    public StudentModel(JDialog jd) {
        ManageHelper helper = new ManageHelper();
        ArrayList<Student> students = helper.getStudent();
        columnNames = new ArrayList<>();
        rowData = new ArrayList<>();
        columnNames.add("学号");
        columnNames.add("姓名");
        columnNames.add("性别");
        columnNames.add("年级");
        columnNames.add("学院");
        columnNames.add("专业");
        for (Student student : students) {
            ArrayList<String> row = new ArrayList<>();
            row.add(student.getStudentID());
            row.add(student.getStudentName());
            row.add(student.getSex());
            row.add(student.getGrade());
            row.add(student.getCollegeName());
            row.add(student.getMajorName());
            rowData.add(row);
        }
        if (getRowCount() == 0) {
            JOptionPane.showMessageDialog(jd, "没有任何记录！");
            jd.dispose();
        }
    }

    //得到共有多少行
    @Override
    public int getRowCount() {

        return this.rowData.size();
    }

    //得到共有多少列
    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    //得到某行某列的数据
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (this.rowData.get(rowIndex)).get(columnIndex);
    }

    //重写方法 getColumnName
    @Override
    public String getColumnName(int column) {
        return this.columnNames.get(column);
    }

}
