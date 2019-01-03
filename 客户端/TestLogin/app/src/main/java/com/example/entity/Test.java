package com.example.entity;

import java.util.List;

/**
 * Created by 李宗豪 on 2018/4/13.
 */

public class Test {
    public int testID;
    public int courseID;
    public int teacherID;
    public String testContent;
    public String testAnswer;//正确的回答
    public String testOption;
    public String userAnswer="未作答";//用户的回答，默认情况未作答

    public Test() {
    }

    public Test(int testID, int courseID, int teacherID, String testContent, String testAnswer, String testOption, String userAnswer) {
        this.testID = testID;
        this.courseID = courseID;
        this.teacherID = teacherID;
        this.testContent = testContent;
        this.testAnswer = testAnswer;
        this.testOption = testOption;
        this.userAnswer = userAnswer;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    public String getTestAnswer() {
        return testAnswer;
    }

    public void setTestAnswer(String testAnswer) {
        this.testAnswer = testAnswer;
    }

    public String getTestOption() {
        return testOption;
    }

    public void setTestOption(String testOption) {
        this.testOption = testOption;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
