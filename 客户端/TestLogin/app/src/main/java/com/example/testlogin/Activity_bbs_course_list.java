package com.example.testlogin;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.MyAdapter.MyAdapter_bbs_course_list;
import com.example.MyAdapter.MyAdapter_test_course_list;
import com.example.entity.StudentCourse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Activity_bbs_course_list extends AppCompatActivity {

    private String TAG="lzh";

    private int tag=1;//标志，1表示初始化，2表示刷新
    private Toolbar toolbar;

    private List<StudentCourse> relationList=new ArrayList<>();
    private HashMap<Integer,Integer> stuMap=new HashMap<>(),noteMap=new HashMap<>();//键值对，键位课程id，值分别为学生数和帖子数

    private MyAdapter_bbs_course_list myAdapter_bbs_course_list;
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefresh;//下拉刷新
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bbs_course_list);

        toolbar=findViewById(R.id.bbs_course_list_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        layoutManager=new GridLayoutManager(this,2);
        recyclerView=findViewById(R.id.bbs_course_list_recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefresh=findViewById(R.id.bbs_course_list_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCourseList();//自己写的方法
            }
        });

        initData();
    }

    private void initData() {
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findCourseList");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",String.valueOf(GlobalParam.user.getUserId()));
        paras.add(nameValuePair);
        new MyAsyncTask().execute(paras);
    }

    private void refreshCourseList() {
        tag=2;
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findCourseList");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",String.valueOf(GlobalParam.user.getUserId()));
        paras.add(nameValuePair);
        relationList.removeAll(relationList);
        stuMap.clear();
        noteMap.clear();
        new MyAsyncTask().execute(paras);
    }
    /********************************通过网络获取课程************************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String> {

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.BBS_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject=new JSONObject(s);
                JSONArray relationArray=jsonObject.getJSONArray("relationList");
                JSONObject stuObj=jsonObject.getJSONObject("stuNum");
                JSONObject noteObj=jsonObject.getJSONObject("noteNum");
                for (int i=0;i<relationArray.length();i++){
                    StudentCourse studentCourse=new StudentCourse();
                    studentCourse.courseID=relationArray.getJSONObject(i).getInt("courseID");
                    studentCourse.relationID=relationArray.getJSONObject(i).getInt("relationID");
                    studentCourse.studentID=relationArray.getJSONObject(i).getInt("studentID");
                    relationList.add(studentCourse);
//                    Log.e(TAG,"哈哈哈"+studentCourse.toString());
                }
                Iterator iterator=stuObj.keys();
                while (iterator.hasNext()){
                    String id=(String)iterator.next();
                    int courseID=Integer.parseInt(id);
                    int num=stuObj.getInt(id);
//                    Log.e(TAG,"哈哈哈"+courseID+"   哈哈哈"+num);
                    stuMap.put(courseID,num);
                }

                Iterator iterator2=noteObj.keys();
                while (iterator2.hasNext()){
                    String id=(String)iterator2.next();
                    int courseID=Integer.parseInt(id);
                    int num=noteObj.getInt(id);
//                    Log.e(TAG,"哈哈哈"+courseID+"   哈哈哈"+num);
                    noteMap.put(courseID,num);
                }
            /*******************************传值给适配器***************************************/
                if (tag==1){//初始化
                    myAdapter_bbs_course_list =new MyAdapter_bbs_course_list(relationList,stuMap,noteMap);
                    recyclerView.setAdapter(myAdapter_bbs_course_list);
                }else if (tag==2){
                    //刷新
                    myAdapter_bbs_course_list.notifyDataSetChanged();
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
