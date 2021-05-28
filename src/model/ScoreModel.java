package model;

import bean.AnalyzeResult;
import dao.ManageHelper;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class ScoreModel extends AbstractTableModel {
    private final ArrayList<String> columnNames;    //列名
    private final ArrayList<ArrayList<String>> rowData;    //行数据


    public ScoreModel(String collegeID) {
        ManageHelper helper = new ManageHelper();
        ArrayList<String> courses = helper.getCourse(collegeID);//获得课程
        ArrayList<AnalyzeResult> results = helper.analyzeScore(collegeID);    //得到分析的结果集合

        columnNames = new ArrayList<>();
        rowData = new ArrayList<>();
        columnNames.add("排名");
        columnNames.add("学号");
        columnNames.add("姓名");
        columnNames.addAll(courses);
        columnNames.add("总成绩");
        columnNames.add("平均成绩");
        for (AnalyzeResult analyzeResult : results) {
            ArrayList<String> row = new ArrayList<>();
            row.add(getRowCount() + 1 + "");
            row.add(analyzeResult.getStudentId());
            row.add(analyzeResult.getStudentName());
            HashMap<String, String> scores = helper.getStudentScore(analyzeResult.getStudentId());    //得到所有课程
            for (int j = 0; j < scores.size(); j++) {
                String score = scores.get(courses.get(j));    //得到成绩
                row.add(score);
            }
            row.add(analyzeResult.getSumScore());
            String avg = analyzeResult.getAvgScore();
            row.add(avg.charAt(0) + "" + avg.charAt(1) + "" + avg.charAt(2) + "" + avg.charAt(3));
            rowData.add(row);
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
    public String getValueAt(int rowIndex, int columnIndex) {
        return (this.rowData.get(rowIndex)).get(columnIndex);
    }

    //重写方法 getColumnName
    @Override
    public String getColumnName(int column) {
        return this.columnNames.get(column);
    }
}
