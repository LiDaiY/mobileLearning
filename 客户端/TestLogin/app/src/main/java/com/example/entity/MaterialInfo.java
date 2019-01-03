package com.example.entity;

import java.io.Serializable;

/**
 * Created by 李宗豪 on 2018/4/24.
 */

public class MaterialInfo implements Serializable{

    public int resID;
    public int courseID;
    public String publishTime;
    public String resTitle;
    public String resUrl;//这是从服务器读取的url
    public int teacherID;
    public long size=0;//大小，这是从服务器读取的大小


    public String state="";
    public int progress=0;
    public long length=0;//长度,之后才会设置

    public String saveDir="";//存储文件夹
    public String savePath="";//存储路径，包括文件名
    public String downloadUrl="";//这是客户端下载的url，由之后设置
    public int position=-1;//这是资源在recyclerview中的位置，由适配器给出，初始化为-1

    public MaterialInfo() {
    }

    public MaterialInfo(int resID, int courseID, String publishTime, String resTitle, String resUrl, int teacherID, long size) {
        this.resID = resID;
        this.courseID = courseID;
        this.publishTime = publishTime;
        this.resTitle = resTitle;
        this.resUrl = resUrl;
        this.teacherID = teacherID;
        this.size=size;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getResTitle() {
        return resTitle;
    }

    public void setResTitle(String resTitle) {
        this.resTitle = resTitle;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "MaterialInfo{" +
                "resID=" + resID +
                ", courseID=" + courseID +
                ", resTitle='" + resTitle + '\'' +
                ", resUrl='" + resUrl + '\'' +
                ", length=" + length +
                ", savePath='" + savePath + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
