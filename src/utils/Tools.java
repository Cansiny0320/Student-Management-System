package utils;

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
    public static String createID(String grade, String collegeID, String uid) {
        return grade + collegeID + uid;
    }
}
