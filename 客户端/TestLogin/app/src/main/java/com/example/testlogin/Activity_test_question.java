package com.example.testlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;

import com.example.MyAdapter.MyAdapter_test_item;

import com.example.entity.Test;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_test_question extends AppCompatActivity {
    private Toolbar toolbar;
    private List<Test> testList=new ArrayList<>();
    private MyAdapter_test_item myAdapter_test_item;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Button sub_btn;//提交答案


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_question);
        toolbar=findViewById(R.id.test_start_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sub_btn=findViewById(R.id.test_submit);
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDialog();//确认提交的对话框
            }
        });
        layoutManager=new LinearLayoutManager(this);
        recyclerView=findViewById(R.id.test_question_recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        initData();
    }

    private void submitDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("确认提交吗？");
        builder.setCancelable(false);
        builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    calcScore();//计算分数并提交
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }
    /***************************************计算分数并提交*******************************************/
    private void calcScore() {
        double aveScore=100.0/testList.size();//总分100分，计算平均每一道的分数
        double sum=0;//总分
        String userAns="";
        for (Test test:testList){
            userAns+=test.userAnswer+";";//记录学生的回答，以分号隔开
            if (test.userAnswer.equals(test.testAnswer))
                sum+=aveScore;
        }
        userAns= userAns.substring(0,userAns.length()-1);//去掉最后一个分号
        Log.e("lzh", "总分: "+sum+"每一道分数："+aveScore+"学生回答："+userAns);
        int i=(int)(Math.ceil(sum));//向上取整并转int
        submitScore(i,userAns);
    }

    private void submitScore(int sum,String str) {
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","subScore");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("courseID",String.valueOf(GlobalParam.courseID));
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",String.valueOf(GlobalParam.user.getUserId()));
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentGrade",String.valueOf(sum));
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentAnswer",str);
        paras.add(nameValuePair);
        Log.e("lzh", "课程ID："+GlobalParam.courseID+"学生ID："+GlobalParam.user.getUserId()+"分数："+String.valueOf(sum)+str+"aaaaa");
        new MyAsyncTask_submit().execute(paras);
    }

    /***************************************初始化数据*******************************************/
    private void initData() {
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findTestAccordingCourseID");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("courseID",String.valueOf(GlobalParam.courseID));
        paras.add(nameValuePair);

        new MyAsyncTask().execute(paras);
    }
    /********************************通过网络提交习题************************************************/
    //避免代码混乱，重写一个异步任务
    public class MyAsyncTask_submit extends  AsyncTask<List<NameValuePair>,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.TEST_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                int code=jsonObject.getInt("code");
                if (code==0){
                    Toast.makeText(Activity_test_question.this,"提交失败",Toast.LENGTH_SHORT).show();

                }else if (code==1){
                    Toast.makeText(Activity_test_question.this,"提交成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /********************************通过网络获取习题************************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String> {


        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.TEST_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray testArray=jsonObject.getJSONArray("testList");
                for(int i=0;i<testArray.length();i++){
                    Test test=new Test();
                    test.courseID=testArray.getJSONObject(i).getInt("courseID");
                    test.testID=testArray.getJSONObject(i).getInt("testID");
                    test.teacherID=testArray.getJSONObject(i).getInt("teacherID");
                    test.testContent=testArray.getJSONObject(i).getString("testContent");
                    test.testAnswer=testArray.getJSONObject(i).getString("testAnswer").toUpperCase();//答案转化为大写字母
                    test.testOption=testArray.getJSONObject(i).getString("testOption");
                    testList.add(test);
                }

                    myAdapter_test_item=new MyAdapter_test_item(testList);
                    recyclerView.setAdapter(myAdapter_test_item);
                if (testList.size()==0){
                    sub_btn.setEnabled(false);
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
