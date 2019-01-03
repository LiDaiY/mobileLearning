package com.example.testlogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.StreamTool;
import com.example.entity.Course;


import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Activity_homework extends AppCompatActivity {

    private String TAG="lzh";
    public static String UPLOAD_HW="UPLOAD_HW";
    private Toolbar toolbar;
    private Course course;
    private Intent intent;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragmentList;
    private MyAdapter adapter;
    private String[] titles = {"作业列表", "我的提交"};

    private Bundle bundle;//用于向fragment传值

    private int hwID=0;//作业id，由广播发送过来

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        toolbar=findViewById(R.id.homework_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        intent=getIntent();
        course= (Course) intent.getSerializableExtra("course");
        toolbar.setTitle(course.getCourseName()+"作业");
        initView();
    }

    private void initView() {
        tabLayout=findViewById(R.id.homework_tab);
        viewPager=findViewById(R.id.homework_view_pager);
        fragmentList=new ArrayList<>();

        bundle=new Bundle();
        bundle.putSerializable("course",course);
        Fragment f1=new Fragment_course_hw();
        Fragment f2=new Fragment_my_hw();
        f1.setArguments(bundle);
        f2.setArguments(bundle);
        fragmentList.add(f1);
        fragmentList.add(f2);


        adapter=new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //注册广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(Activity_homework.UPLOAD_HW);
        registerReceiver(mReceiver,filter);
    }


    /*********************************************************************************************/
    class MyAdapter extends FragmentPagerAdapter{
        public MyAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
             return titles[position];
        }
    }
    /***********************************注册广播*********************************************/
    BroadcastReceiver mReceiver=new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Activity_homework.UPLOAD_HW.equals(intent.getAction())) {
                //打开文件管理器找文件
               int id= intent.getIntExtra("hwID",0);
                Log.e(TAG, "收到上传作业的信息，作业ID为："+id);
                hwID=id;
                openFileManager();//打开文件管理器，用于上传作业，自己写的方法
            }
        }
    };
    /********************************打开文件管理器，用于上传作业****************************************************/
    private void openFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
          intent.setType("*/*");//无类型限制
          intent.addCategory(Intent.CATEGORY_OPENABLE);
          startActivityForResult(intent, 1);
    }
    /********************************获取文件路径****************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path="";
        if (requestCode==1&&resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            path = getPath(this, uri);
            String[] a=path.split("/");
            String fileName=a[a.length-1];
            Log.e(TAG,"文件的路径是："+path+"文件名是："+fileName);
            List<String> s=new ArrayList<>();
            new MyAsyncTask().execute(path);
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
    /********************************注销广播****************************************************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
    /********************************异步线程上传文件************************************************/
    public class MyAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String path=strings[0];
            String[] a=path.split("/");


            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            String newName =a[a.length-1] ;//文件名

            String pathString=ConstsUrl.HW_URL+"?operation=upHomework&studentID="+ GlobalParam.user.getUserId()+
                    "&hwID="+hwID+"&stuWorkTitle="+newName;

            HttpURLConnection conn;
            try{
                conn = (HttpURLConnection) new URL(pathString)
                        .openConnection();
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Content-Type",
                        "multipart/form-data;boundary=" + boundary);
                DataOutputStream ds = new DataOutputStream(
                        conn.getOutputStream());
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; "
                        + "name=\"file1\";filename=\"" + newName
                        + "\"" + end);
                ds.writeBytes(end);

                FileInputStream fStream = new FileInputStream(path);
                /* 设置每次写入1024bytes */
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
            /* 从文件读取数据至缓冲区 */
                while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);//结束
                fStream.close();
                ds.flush();

                InputStream inputStream = conn.getInputStream();
//            Log.e(TAG, "7");
                byte[] read = StreamTool.read(inputStream);
                Log.e(TAG, "接收到服务器信息");
                String result = new String(read, 0, read.length, "UTF-8");
                return result;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.e(TAG,"发帖返回的消息"+s);
                JSONObject jsonObject = new JSONObject(s);
                int code = jsonObject.getInt("code");
                if (code==1){
                    Toast.makeText(Activity_homework.this, "上传作业成功", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /************************************以下全为gePath相关******************************************/
    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
