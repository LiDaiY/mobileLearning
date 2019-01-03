package com.example.testlogin;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.MyAdapter.MyAdapter_Notice;
import com.example.MyAdapter.MyAdapter_course;
import com.example.entity.Course;
import com.example.entity.Notice;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_notice extends AppCompatActivity {
    private int tag=1;//标志，1表示初始化，2表示刷新

    private Toolbar toolbar;
    private List<Notice> noticeList=new ArrayList<>();
    private MyAdapter_Notice adapter_notice;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefresh;//下拉刷新
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        toolbar=findViewById(R.id.notice_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        layoutManager=new LinearLayoutManager(this);
        recyclerView=findViewById(R.id.notice_recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        //下拉刷新
        swipeRefresh=findViewById(R.id.notice_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNoticeLst();//自己写的方法
            }
        });

        initData();
    }


    private void initData() {
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findMyNotice");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",String.valueOf(GlobalParam.user.getUserId()));
        paras.add(nameValuePair);

        new MyAsyncTask().execute(paras);
    }

    /********************************刷新通知列表************************************************/
    private void refreshNoticeLst() {
        tag=2;
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findMyNotice");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",String.valueOf(GlobalParam.user.getUserId()));
        paras.add(nameValuePair);
        noticeList.removeAll(noticeList);
        new MyAsyncTask().execute(paras);
    }

    /********************************通过网络获取通知************************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.NOTICE_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray noticeArray=jsonObject.getJSONArray("notices");
                for(int i=0;i<noticeArray.length();i++){
                    Notice notice=new Notice();
                    notice.noticeID=noticeArray.getJSONObject(i).getInt("noticeID");
                    notice.teacherID=noticeArray.getJSONObject(i).getInt("teacherID");
                    notice.courseID=noticeArray.getJSONObject(i).getInt("courseID");
                    notice.noticeTitle=noticeArray.getJSONObject(i).getString("noticeTitle");
                    notice.noticeContent=noticeArray.getJSONObject(i).getString("noticeContent");
                    notice.noticeTime=noticeArray.getJSONObject(i).getString("noticeTime");
                    notice.courseName=noticeArray.getJSONObject(i).getString("courseName");
                        noticeList.add(notice);
                }

                if (tag==1) {//初始化
                    adapter_notice = new MyAdapter_Notice(noticeList);
                    recyclerView.setAdapter(adapter_notice);
                }else if (tag==2){
                    //刷新
                    adapter_notice.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /********************************点击箭头返回****************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home://返回箭头
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
