package com.example.entity;

/**
 * Created by 李宗豪 on 2018/4/28.
 */

public class Homework {
    public int hwID;
    public int teacherID;
    public int courseID;
    public String hwContent;
    public String publishTime;
    public String hwTitle;

    public Homework() {
    }

    public Homework(int hwID, int teacherID, int courseID, String hwContent, String publishTime, String hwTitle) {
        this.hwID = hwID;
        this.teacherID = teacherID;
        this.courseID = courseID;
        this.hwContent = hwContent;
        this.publishTime = publishTime;
        this.hwTitle = hwTitle;
    }

    public int getHwID() {
        return hwID;
    }

    public void setHwID(int hwID) {
        this.hwID = hwID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getHwContent() {
        return hwContent;
    }

    public void setHwContent(String hwContent) {
        this.hwContent = hwContent;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getHwTitle() {
        return hwTitle;
    }

    public void setHwTitle(String hwTitle) {
        this.hwTitle = hwTitle;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "hwID=" + hwID +
                ", teacherID=" + teacherID +
                ", courseID=" + courseID +
                ", hwContent='" + hwContent + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", hwTitle='" + hwTitle + '\'' +
                '}';
    }
}
