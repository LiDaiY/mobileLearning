package com.example.testlogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.MyAdapter.MyAdapter_res_list;
import com.example.entity.Course;
import com.example.entity.MaterialInfo;
import com.example.services.DownLoadService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程资源，用于下载
 */
public class Activity_course_res_list extends AppCompatActivity {
    private String TAG="lzh";
    private int tag=1;//标志，1表示初始化，2表示刷新

    private Toolbar toolbar;
    private Course course;
    private Intent intent;

    /*****************************************************************************************/
    private RecyclerView recyclerView;
    private MyAdapter_res_list adapter;
    private List<MaterialInfo> materialInfos=new ArrayList<>();
    private LinearLayoutManager layoutManager;

    private long time=System.currentTimeMillis();
    /*****************************************************************************************/
//    private TextView mTvFileName=null;
//    private ProgressBar mPbProgress=null;
//    private Button mBtStart=null;
//    private Button mBtStop=null;
    /*****************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_res_list);
        toolbar=findViewById(R.id.res_list_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        intent=getIntent();
        course= (Course) intent.getSerializableExtra("course");
        toolbar.setTitle(course.getCourseName()+"资源");
        initView();
        initData();
        initFunc();
    }

    private void initFunc() {
        //注册广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(DownLoadService.ACTION_UPDATE);
        registerReceiver(mReceiver,filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    private void initData() {
        List<NameValuePair> paras=new ArrayList<>();
        paras.add(new BasicNameValuePair("operation","findResByCourseID"));
        paras.add(new BasicNameValuePair("courseID",String.valueOf(course.getCourseID())));
        new MyAsyncTask().execute(paras);
    }
    private void initView(){
        recyclerView=findViewById(R.id.res_list_recycler_view);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    /********************************通过网络获取资源列表********************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.RES_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(TAG,"传来的信息"+s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray resArray = jsonObject.getJSONArray("resList");
                for(int i=0;i<resArray.length();i++){
                    MaterialInfo materialInfo=null;
                    int resID=0,courseID=0,teacherID=0;
                    String publishTime=null,resTitle=null,resUrl=null;
                    long size=0;
                    resID=resArray.getJSONObject(i).getInt("resID");
                    courseID=resArray.getJSONObject(i).getInt("courseID");
                    publishTime=resArray.getJSONObject(i).getString("publishTime");
                    resTitle=resArray.getJSONObject(i).getString("resTitle");
                    resUrl=resArray.getJSONObject(i).getString("resUrl");
                    teacherID=resArray.getJSONObject(i).getInt("teacherID");
                    size=resArray.getJSONObject(i).getLong("size");
                    if (resID==0||courseID==0||publishTime==null||resTitle==null||resUrl==null||teacherID==0||size==0){
                        Log.e(TAG,"资源中有信息为空");
                    }
                    materialInfo=new MaterialInfo(resID,courseID,publishTime,resTitle,resUrl,teacherID,size);
                    String dir= Environment.getExternalStorageDirectory().getAbsolutePath()+
                            "/1/bysj/course/"+courseID+"/res";
                    materialInfo.setSaveDir(dir);
                    materialInfo.setSavePath(dir+"/"+resTitle);
                    materialInfo.setDownloadUrl(ConstsUrl.BASE_URL+resUrl);

                    materialInfos.add(materialInfo);
                }
                adapter=new MyAdapter_res_list(materialInfos,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /********************************注册广播更新进度条********************************************/
    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (adapter==null){
                return;
            }
            if(DownLoadService.ACTION_UPDATE.equals(intent.getAction()))
            {
                boolean isFinished=intent.getBooleanExtra("isFinish",false);
                if(!isFinished) {//如果没有下载完
                    long progress = intent.getLongExtra("progress", 0);
                    int position = intent.getIntExtra("position", 0);
//                Log.e(TAG, "onReceive: progress:"+progress+"  position:"+position );
                    adapter.updateProgress(position, (int) progress);
                }else{
                    int position = intent.getIntExtra("position", 0);
                    adapter.updateProgress(position, 100);
                }
            }
        }
    };

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
