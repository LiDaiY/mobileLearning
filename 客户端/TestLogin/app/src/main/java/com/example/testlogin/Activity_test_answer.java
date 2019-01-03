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
import android.view.Menu;
import android.view.MenuItem;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.MyAdapter.MyAdapter_test_item;
import com.example.MyAdapter.MyAdapter_test_result_item;
import com.example.entity.Test;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_test_answer extends AppCompatActivity {
    private String TAG="lzh";

    private Toolbar toolbar;
    private List<Test> testList=new ArrayList<>();
    private MyAdapter_test_result_item myAdapter_test_item;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    private Bundle bundle;
    private String studentAns="";//这是由答案组成的字符串
    private String[] sAns=null;//这是每一题用户的答案
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_answer);
        toolbar=findViewById(R.id.test_ans_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        layoutManager=new LinearLayoutManager(this);
        recyclerView=findViewById(R.id.test_answer_recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        bundle=getIntent().getExtras();
        studentAns=bundle.getString("studentAnswer");
        Log.e(TAG, "用户的答案是"+studentAns );
        sAns=studentAns.split(";");

        initAction();
        initData();

    }

    private void initAction() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.test_again:
                        showTestAgainDialog();
                        break;
                }
                return false;
            }
        });
    }

    private void showTestAgainDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("确定重新测试吗？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Activity_test_answer.this,Activity_test_question.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private void initData() {
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findTestAccordingCourseID");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("courseID",String.valueOf(GlobalParam.courseID));
        Log.e(TAG, "initData: courseID："+GlobalParam.courseID );
        paras.add(nameValuePair);

        new MyAsyncTask().execute(paras);
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
                int len=sAns.length;
                for(int i=0;i<testArray.length();i++){
                    Test test=new Test();
                    test.courseID=testArray.getJSONObject(i).getInt("courseID");
                    test.testID=testArray.getJSONObject(i).getInt("testID");
                    test.teacherID=testArray.getJSONObject(i).getInt("teacherID");
                    test.testContent=testArray.getJSONObject(i).getString("testContent");
                    test.testAnswer=testArray.getJSONObject(i).getString("testAnswer").toUpperCase();//答案转化为大写字母
                    test.testOption=testArray.getJSONObject(i).getString("testOption");
                    if (i<len) {
                        test.userAnswer = sAns[i];
                    }
                    testList.add(test);
                }
//
                myAdapter_test_item=new MyAdapter_test_result_item(testList);
                recyclerView.setAdapter(myAdapter_test_item);

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
    /********************************设置菜单****************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_answer_menu,menu);
        return true;
    }
}
