package com.example.testlogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.ImageLoader;
import com.example.MyAdapter.MyAdapter_bbs_reply_list;
import com.example.entity.ReplyPost;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 回帖界面
 */
public class Activity_bbs_reply_list extends AppCompatActivity {
    private String TAG="lzh";

    private int tag=0;//0表示初始化，1表示刷新

    private ImageLoader imageLoader;
    private Intent intent;
    private Bundle bundle;
    private Toolbar toolbar;
    /********************************************************************************************/
    private ImageView postUserPhoto;
    private TextView postUserNameView;
    private TextView postTimeView;//发帖时间
    private TextView postTitleView;//标题
    private TextView postContentView;//内容

    private EditText editText;//要发表的内容
    private Button send_btn;//发表
    /********************************************************************************************/
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    /********************************************************************************************/
    private int postID=0;
    private String postUserEmail="";
    private String postUserName="";
    private String postTime="";
    private String postTitle="";
    private String postContent="";
    /********************************************************************************************/
    List<ReplyPost> replyPostList=new ArrayList<>();
    private MyAdapter_bbs_reply_list myAdapter_bbs_reply_list;

    MyBroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_reply_list);
        toolbar=findViewById(R.id.reply_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        };
        /*****************************************注册广播*************************************************/
        mReceiver = new Activity_bbs_reply_list.MyBroadcastReceiver();
        IntentFilter counterActionFilter = new IntentFilter("refreshReply");
        registerReceiver(mReceiver, counterActionFilter);
        /******************************************************************************************/
        initView();
        initBaseData();
        initEvent();
        initData();//通关网络获取回帖信息
    }

    private void initData() {

        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        paras.add(new BasicNameValuePair("operation","findReplyList"));
        paras.add(new BasicNameValuePair("postID",String.valueOf(postID)));
        replyPostList.removeAll(replyPostList);
        new MyAsyncTask().execute(paras);
    }

    private void initEvent() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0){
                    send_btn.setEnabled(true);
                }else {
                    send_btn.setEnabled(false);
                }
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReply();
            }
        });
    }

    private void sendReply() {
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        paras.add(new BasicNameValuePair("operation","sendReply"));
        paras.add(new BasicNameValuePair("postID",String.valueOf(postID)));
        String content=editText.getText().toString();
        paras.add(new BasicNameValuePair("replyContent",content));
        paras.add(new BasicNameValuePair("userID",String.valueOf(GlobalParam.user.getUserId())));
        paras.add(new BasicNameValuePair("userName",GlobalParam.user.getUsername()));
        paras.add(new BasicNameValuePair("userEmail",GlobalParam.user.getEmail()));

        new MyAsyncTask_send().execute(paras);
    }

    private void initView() {
        postUserPhoto=findViewById(R.id.reply_activity_user_photo);
        postUserNameView=findViewById(R.id.reply_activity_user_name);
        postTimeView=findViewById(R.id.reply_activity_post_send_time);
        postTitleView=findViewById(R.id.reply_activity_post_title);
        postContentView=findViewById(R.id.reply_activity_post_content);

        editText=findViewById(R.id.reply_activity_reply_content);
        send_btn=findViewById(R.id.reply_activity_send_btn);
        send_btn.setEnabled(false);
        recyclerView=findViewById(R.id.reply_activity_recycler_list);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        /*************************************为recyclerview添加分割线************ *****************/
        DividerItemDecoration divider=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.my_divider));
        recyclerView.addItemDecoration(divider);
        /*************************************recyclerview禁止滑动************ *****************/
        recyclerView.setNestedScrollingEnabled(false);//必须加上这句，否则滑动将会卡顿

    }
    private void initBaseData() {
        bundle=getIntent().getExtras();
        postID=bundle.getInt("postID");
        postUserEmail=bundle.getString("postUserEmail");
        postUserName=bundle.getString("postUserName");
        postTime=bundle.getString("postTime");
        postTitle=bundle.getString("postTitle");
        postContent=bundle.getString("postContent");

        //设置发帖人头像
        String url= ConstsUrl.BASE_URL+"res/user/"+postUserEmail+"/pic.jpg";
        postUserPhoto.setTag(url);
        imageLoader=new ImageLoader();
        imageLoader.showImageByAsyncTask(postUserPhoto ,url);//设置头像

        postUserNameView.setText(postUserName);
        postTimeView.setText(postTime);
        postTitleView.setText(postTitle);
        postContentView.setText(postContent);
    }
    /****************************************通过网络获取回帖********************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String> {

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.BBS_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                Log.e(TAG, "onPostExecute: 接收到的回帖" + s);
                JSONArray replyArray = jsonObject.getJSONArray("replyList");
                for(int i=0;i<replyArray.length();i++){
                    ReplyPost replyPost=new ReplyPost();
                    replyPost.replyID=replyArray.getJSONObject(i).getInt("replyID");
                    replyPost.postID=replyArray.getJSONObject(i).getInt("postID");
                    replyPost.userName=replyArray.getJSONObject(i).getString("userName");
                    replyPost.replyContent=replyArray.getJSONObject(i).getString("replyContent");
                    replyPost.replyTime=replyArray.getJSONObject(i).getString("replyTime");
                    replyPost.userStatus=replyArray.getJSONObject(i).getInt("userStatus");
                    replyPost.starNum=replyArray.getJSONObject(i).getInt("starNum");
                    replyPost.userEmail=replyArray.getJSONObject(i).getString("userEmail");
                    replyPost.userID=replyArray.getJSONObject(i).getInt("userID");
                    replyPost.signature=replyArray.getJSONObject(i).getString("signature");
                    replyPostList.add(replyPost);
                    Log.e(TAG,replyPost.toString());
                }
                if (tag==0){//初始化
                    Log.e(TAG,"初始化了");
                    myAdapter_bbs_reply_list=new MyAdapter_bbs_reply_list(replyPostList);
                    recyclerView.setAdapter(myAdapter_bbs_reply_list);
                }else if (tag==1){//刷新
                    Log.e(TAG,"刷新了");
                    myAdapter_bbs_reply_list.notifyDataSetChanged();
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
    /****************************************通过网络发表回帖********************************************/
    public class MyAsyncTask_send extends  AsyncTask<List<NameValuePair>,Void,String>{

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
                    Toast.makeText(Activity_bbs_reply_list.this,"回帖成功",Toast.LENGTH_SHORT).show();
                    refreshReplyList();//刷新列表
                    editText.setText("");
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshReplyList() {
        tag=1;
        initData();
        tag=0;
    }

    /****************************************返回箭头****************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /******************************************与广播相关*****************************************************/
    public class MyBroadcastReceiver extends BroadcastReceiver {//用于更新侧滑菜单的头像、昵称、签名

        @Override
        public void onReceive(Context context, Intent intent) {
            refreshReplyList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
