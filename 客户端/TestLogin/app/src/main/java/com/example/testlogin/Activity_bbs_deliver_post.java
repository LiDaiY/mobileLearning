package com.example.testlogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_bbs_deliver_post extends AppCompatActivity {
    private String TAG="lzh";

    private int courseID=0;//课程ID由上一个activity传入
    private int studentID;
    private String studentEmail;
    private String studentName;
    private Toolbar toolbar;
    private String title,content;//由editview上获取的标题和内容

    int flag=0;//菜单上是否显示发送按钮，0表示不显示，1表示显示

    private Menu myMenu;
    private MenuItem menuItem;
    private EditText bbs_title_edit;
    private EditText bbs_content_eidt;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_deliver_post);
        /***********************************************************************************/
        toolbar=findViewById(R.id.bbs_deliver_post_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        /***********************************************************************************/
        bbs_title_edit=findViewById(R.id.bbs_title_view);
        bbs_content_eidt=findViewById(R.id.bbs_content_view);
        linearLayout=findViewById(R.id.bbs_deliver_linear_layout);
        /***********************************************************************************/
        Bundle bundle=getIntent().getExtras();
        courseID=bundle.getInt("courseID");
        studentID= GlobalParam.user.getUserId();
        studentEmail=GlobalParam.user.getEmail();
        studentName=GlobalParam.user.getUsername();
        /***********************************************************************************/
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbs_content_eidt.setFocusable(true);
                bbs_content_eidt.requestFocus();
            }
        });
        /***********************************************************************************/
        //标题为空，不显示发送按钮
        bbs_title_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0){
                    flag=1;
                    onPrepareOptionsMenu(myMenu);
                }else {
                    flag=0;
                    onPrepareOptionsMenu(myMenu);
                }
            }
        });
        /***********************************************************************************/
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bbs_deliver_btn:
                        sendPost();
                        break;
                }
                return false;
            }
        });
        /***********************************************************************************/
    }

    private void sendPost() {

        title=bbs_title_edit.getText().toString();
        content=bbs_content_eidt.getText().toString();


        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","sendPost");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("courseID",String.valueOf(courseID));
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",String.valueOf(studentID));
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentEmail",studentEmail);
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentName",studentName);
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("title",title);
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("content",content);
        paras.add(nameValuePair);
//        Log.e(TAG,courseID+" "+studentID+" "+studentEmail+" "+studentName+" "+title+" "+content);
        new MyAsyncTask().execute(paras);
    }
    /*************************************通过网络发帖**********************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.BBS_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.e(TAG,"发帖返回的消息"+s);
                JSONObject jsonObject = new JSONObject(s);
                int code = jsonObject.getInt("code");
                if (code==1){
                    Toast.makeText(Activity_bbs_deliver_post.this,"发帖成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }catch (JSONException e) {
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
        getMenuInflater().inflate(R.menu.activity_bbs_deliver,menu);
        myMenu=menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuItem=menu.findItem(R.id.bbs_deliver_btn);
        if (flag==0) {
            menuItem.setVisible(false);
        }else if (flag==1){
            menuItem.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
