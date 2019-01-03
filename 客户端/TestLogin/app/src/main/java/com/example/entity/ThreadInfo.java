package com.example.entity;

/**
 *
 Thread Information
 */
public class ThreadInfo {
    private int id;
    private String url;
    private long start;//开始的位置，在一个线程下载一个文件中，都为0，故这个成员是没用的
    private long end;//需要下载长度
    private long finished;//已完成的量

    public ThreadInfo() {
        super();
    }

    public ThreadInfo(long end, long finished, int id, long start, String url) {
        super();
        this.end = end;
        this.finished = finished;
        this.id = id;
//        this.start = start;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "end=" + end +
                ", id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", finished=" + finished +
                '}';
    }
}
