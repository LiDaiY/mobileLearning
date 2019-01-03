package com.example.testlogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.ImageLoader;
import com.example.entity.User;
import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private Intent intent;
    private User user;
    private TextView nameView;
    private TextView SignaturelView;
    private View view2;
    private RoundedImageView userPhoto;
    private  Fragment_scan_course fragment_scan_course;//浏览课程fragment
    private  Fragment_scan_my_course fragment_scan_my_course;//浏览课程fragment
    MyBroadcastReciever mReciever;

    private String state="";//这是用于区分资源和作业的标志
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**************************状态栏透明*********************************************************/
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
        /***********************************************************************************/
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /***********************************************************************************/
        intent=getIntent();
//        user=(User)intent.getSerializableExtra("user");
        user=GlobalParam.user;
        navigationView=findViewById(R.id.nav_view);
        view2=navigationView.getHeaderView(0);
        nameView=view2.findViewById(R.id.username_view_id);
        SignaturelView=view2.findViewById(R.id.email_view_id);
        userPhoto=view2.findViewById(R.id.user_photo);
        // Toast.makeText(this, "aaa"+user.toString()+"qqq", Toast.LENGTH_SHORT).show();
        nameView.setText(user.getUsername().toString());
        String str=user.getSignature().toString();
        if (str.equals(""))
            SignaturelView.setText("在个人信息界面可修改签名哦");
        else
            SignaturelView.setText(str);

        String url= ConstsUrl.BASE_URL+user.getPhoto();
        ImageLoader imageLoader=new ImageLoader();
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        imageLoader.showImageByAsyncTask(userPhoto,url);

        initFirstFragment();
/*****************************************注册广播*************************************************/
        mReciever = new MyBroadcastReciever();

        IntentFilter counterActionFilter = new IntentFilter("update_info");
        registerReceiver(mReciever, counterActionFilter);
    }
    private void initFirstFragment() {
        toolbar.setTitle("课程浏览");
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        fragment_scan_course = new Fragment_scan_course();
        transaction.replace(R.id.ui_scan_all_course,fragment_scan_course);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //点击第一个选项
            initFirstFragment();
        } else if (id == R.id.nav_gallery) {
            toolbar.setTitle("课程资源");
            state="resource";
            fragment_scan_my_course=new Fragment_scan_my_course();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.ui_scan_all_course,fragment_scan_my_course);
            transaction.commit();
        } else if (id == R.id.nav_slideshow) {//作业
            toolbar.setTitle("作业");
            state="homework";
            fragment_scan_my_course=new Fragment_scan_my_course();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.ui_scan_all_course,fragment_scan_my_course);
            transaction.commit();
        } else if (id == R.id.nav_manage) {//在线测试
            intent=new Intent(MainActivity.this,Activity_test_course_list.class);
            startActivity(intent);
        }  else if (id == R.id.t_s_communication) {//师生交流
            intent=new Intent(MainActivity.this,Activity_bbs_course_list.class);
            startActivity(intent);
        } else if (id == R.id.notice_message) {//通知
            intent=new Intent(MainActivity.this,Activity_notice.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {//个人信息
            intent=new Intent(MainActivity.this,Activity_personal_info.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/******************************************与fragment相关的方法*****************************************************/
//    private void hideFragment() {
//        if (fragment_scan_course!=null)
//            transaction.hide(fragment_scan_course);
//    }
    //这两个方法在fragment_scan_my_course中调用
    public User getActivityUser(){
        return user;
    }
    public String getState(){return state;}
/******************************************与广播相关*****************************************************/
public class MyBroadcastReciever extends BroadcastReceiver {//用于更新侧滑菜单的头像、昵称、签名

    @Override
    public void onReceive(Context context, Intent intent) {
        user=GlobalParam.user;
        // TODO Auto-generated method stub
        nameView.setText(user.getUsername());
        SignaturelView.setText(user.getSignature());
        String url= ConstsUrl.BASE_URL+user.getPhoto();
        ImageLoader imageLoader=new ImageLoader();
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        imageLoader.showImageByAsyncTask(userPhoto,url);
    }
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReciever);
    }
}
