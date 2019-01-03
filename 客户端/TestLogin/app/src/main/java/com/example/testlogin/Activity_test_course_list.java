package com.example.testlogin;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.MyAdapter.MyAdapter_Notice;
import com.example.MyAdapter.MyAdapter_test_course_list;
import com.example.entity.Course;
import com.example.entity.Notice;
import com.example.entity.StudentCourse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_test_course_list extends AppCompatActivity {
    private int tag=1;//标志，1表示初始化，2表示刷新
    private Toolbar toolbar;
    private List<StudentCourse> relationList=new ArrayList<>();
    private MyAdapter_test_course_list myAdapter_test_course_list;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefresh;//下拉刷新
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_course_list);
        toolbar=findViewById(R.id.test_course_list_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        layoutManager=new LinearLayoutManager(this);
        recyclerView=findViewById(R.id.test_course_list_recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        //下拉刷新
        swipeRefresh=findViewById(R.id.test_course_list_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNoticeLst();//自己写的方法
            }
        });

        initData();
    }

    private void refreshNoticeLst() {
        tag=2;//表示刷新
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findCourseTestScore");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",String.valueOf(GlobalParam.user.getUserId()));
        paras.add(nameValuePair);
        relationList.removeAll(relationList);
        new MyAsyncTask().execute(paras);
    }

    private void initData() {
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findCourseTestScore");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",String.valueOf(GlobalParam.user.getUserId()));
        paras.add(nameValuePair);

        new MyAsyncTask().execute(paras);
    }

    /********************************通过网络获取课程************************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.TEST_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray relationArray=jsonObject.getJSONArray("relationList");
                for(int i=0;i<relationArray.length();i++){
                    StudentCourse studentCourse=new StudentCourse();
                    studentCourse.courseID=relationArray.getJSONObject(i).getInt("courseID");
                    studentCourse.relationID=relationArray.getJSONObject(i).getInt("relationID");
                    studentCourse.studentID=relationArray.getJSONObject(i).getInt("studentID");
                    studentCourse.studentGrade=relationArray.getJSONObject(i).getInt("studentGrade");
                    studentCourse.studentAnswer=relationArray.getJSONObject(i).getString("studentAnswer");
                    relationList.add(studentCourse);
                }

                if (tag==1) {//初始化
                    myAdapter_test_course_list=new MyAdapter_test_course_list(relationList);
                    recyclerView.setAdapter(myAdapter_test_course_list);
                }else if (tag==2){
                    //刷新
                    myAdapter_test_course_list.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshNoticeLst();
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
