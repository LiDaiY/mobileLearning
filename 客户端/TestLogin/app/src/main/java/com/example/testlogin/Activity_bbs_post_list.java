package com.example.testlogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.ImageLoader;
import com.example.MyAdapter.MyAdapter_bbs_post_list;
import com.example.entity.BbsTheme;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_bbs_post_list extends AppCompatActivity {

    private String TAG="lzh";

    private int tag=0;//0表示初始化，1表示刷新

    private Intent intent;
    private Bundle bundle;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView courseImageView;
    private ImageLoader imageLoader;
    private TextView textView;

    int courseID;//课程ID，由上一个activity传入

    private MyAdapter_bbs_post_list myAdapter_bbs_post_list;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    private List<BbsTheme> bbsThemeList=new ArrayList<>();
    MyBroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_bbs_post_list);

        toolbar=findViewById(R.id.bbs_post_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        /****************************************************************************************/
        textView=findViewById(R.id.bbs_post_info);
        courseImageView=findViewById(R.id.bbs_post_course_cover_view);
        collapsingToolbarLayout=findViewById(R.id.bbs_post_toolbar_layout);
        /****************************************************************************************/
        bundle=getIntent().getExtras();
        courseID=bundle.getInt("courseID");
        int stuNum=bundle.getInt("stuNum");
        int noteNum=bundle.getInt("noteNum");
        String courseName= GlobalParam.cNameMap.get(courseID);
        /****************************************************************************************/
        collapsingToolbarLayout.setTitle(courseName);
        textView.setText("关注："+stuNum+"   帖子："+noteNum);
        /*************************************设置封面**********************************************/
        String path="res/course/"+String.valueOf(courseID)+"/cover.jpg";
//        String url= ConstsUrl.BASE_URL+path;
//        courseImageView.setTag(url);
//        imageLoader=new ImageLoader();
//        imageLoader.showImageByAsyncTask(courseImageView,url);//设置封面
        /*************************************悬浮按钮**********************************************/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //发帖
                deliverPost();
            }
        });
        /*************************************recyclerview******************************************/
        layoutManager=new LinearLayoutManager(this);
        recyclerView=findViewById(R.id.bbs_post_theme_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        /*************************************为recyclerview添加分割线************ *****************/
//        DividerItemDecoration divider=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.my_divider));
//        recyclerView.addItemDecoration(divider);
        /*************************************recyclerview禁止滑动************ *****************/
        recyclerView.setNestedScrollingEnabled(false);//必须加上这句，否则滑动将会卡顿
        /*****************************************注册广播*************************************************/
        mReceiver = new MyBroadcastReceiver();
        IntentFilter counterActionFilter = new IntentFilter("refreshPost");
        registerReceiver(mReceiver, counterActionFilter);
        initData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    //发帖
    private void deliverPost() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("确定发帖吗？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent =new Intent(Activity_bbs_post_list.this,Activity_bbs_deliver_post.class);
                Bundle bundle=new Bundle();
                bundle.putInt("courseID",courseID);
                intent.putExtras(bundle);
                startActivity(intent);
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
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findPostList");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("courseID",String.valueOf(courseID));
        paras.add(nameValuePair);
        bbsThemeList.removeAll(bbsThemeList);
        new MyAsyncTask().execute(paras);
    }
    /****************************************通过网络获取帖子********************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.BBS_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject=new JSONObject(s);
                Log.e(TAG, "onPostExecute: 接收到的字符串"+s );
                JSONArray postArray=jsonObject.getJSONArray("postList");
                for (int i=0;i<postArray.length();i++){
                    BbsTheme bbsTheme=new BbsTheme();
                    bbsTheme.postID=postArray.getJSONObject(i).getInt("postID");
                    bbsTheme.courseID=postArray.getJSONObject(i).getInt("courseID");
                    bbsTheme.studentID=postArray.getJSONObject(i).getInt("studentID");
                    bbsTheme.studentName=postArray.getJSONObject(i).getString("studentName");
                    bbsTheme.postTitle=postArray.getJSONObject(i).getString("postTitle");
                    bbsTheme.postContent=postArray.getJSONObject(i).getString("postContent");
                    bbsTheme.replyCount=postArray.getJSONObject(i).getInt("replyCount");
                    bbsTheme.postTime=postArray.getJSONObject(i).getString("postTime");
                    bbsTheme.replyTime=postArray.getJSONObject(i).getString("replyTime");
                    bbsTheme.state=postArray.getJSONObject(i).getInt("state");
                    bbsTheme.studentEmail=postArray.getJSONObject(i).getString("studentEmail");
                    bbsThemeList.add(bbsTheme);
                    Log.e(TAG, "onPostExecute: 帖子"+bbsTheme.toString() );
                }
                if (tag==0) {
                    myAdapter_bbs_post_list = new MyAdapter_bbs_post_list(bbsThemeList);
                    recyclerView.setAdapter(myAdapter_bbs_post_list);
                    tag=1;//创建一次之后都为1，即
                }else if (tag==1){
                    myAdapter_bbs_post_list.notifyDataSetChanged();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            tag=1;
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
