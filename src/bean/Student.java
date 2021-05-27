package bean;

import java.util.HashMap;

public class Student {
    String studentID;
    String studentName;
    String collegeName;
    String CollegeID;
    private HashMap<String, String> scores;

    public HashMap<String, String> getScores() {
        return scores;
    }

    public void setScores(HashMap<String, String> scores) {
        this.scores = scores;
    }


    public String getCollegeID() {
        return CollegeID;
    }

    public void setCollegeID(String collegeID) {
        CollegeID = collegeID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
