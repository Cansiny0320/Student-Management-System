package utils;

import dao.ManageHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class Tools {
    //获取年级
    public static String[] createGrade() {
        ArrayList<String> arrayList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        for (int i = 0; i < 4; i++) {
            arrayList.add(String.valueOf(Year--));
        }
        return arrayList.toArray(new String[0]);
    }

    //生成学生学号的方法(学号：grade+collegeID+uid)
    public static String createID(String grade, String collegeID) {
        ManageHelper helper = new ManageHelper();
        int count = helper.getStudentNum();
        String uid;
        if (count < 10) {
            uid = "00" + count;
        } else if (count < 100) {
            uid = "0" + count;
        } else {
            uid = "" + count;
        }
        return grade + collegeID + uid;
    }
}
