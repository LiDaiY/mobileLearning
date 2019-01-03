package com.example.testlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.ImageLoader;
import com.example.HttpUtil.StreamTool;
import com.example.entity.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Activity_personal_info extends AppCompatActivity implements View.OnClickListener{

    private static int RESULT_LOAD_IMAGE = 1;//读取照片的标志，将其设为1
    private String pathString;//这是上传图片的url

    private Toolbar toolbar;
    private User user;
    private String email,name,sex,age,signature,photo_url,userID;
    private ImageView photo_view;
    private TextView email_view,name_view,sex_view,age_view,signature_view;
    private ImageLoader imageLoader;
    private Intent intent;

    private Bitmap bitmap;

    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        toolbar=findViewById(R.id.personal_info_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        intiview();
        intiEvent();
    }

    private void intiEvent() {
        photo_view.setOnClickListener(this);
        name_view.setOnClickListener(this);
        sex_view.setOnClickListener(this);
        age_view.setOnClickListener(this);
        signature_view.setOnClickListener(this);
    }

    private void intiview() {
//        intent=getIntent();
//        user= (User) intent.getSerializableExtra("user");
        user= GlobalParam.user;
        email=user.getEmail();
        name=user.getUsername();
        sex=user.getSex();
        age=user.getBirth();
        signature=user.getSignature();
        userID=String.valueOf(user.getUserId());
        photo_url=ConstsUrl.BASE_URL+user.getPhoto();

        photo_view=findViewById(R.id.personal_image);
        email_view=findViewById(R.id.personal_email);
        name_view=findViewById(R.id.personal_name);
        sex_view=findViewById(R.id.personal_sex) ;
        age_view=findViewById(R.id.personal_age);
        signature_view=findViewById(R.id.personal_signature);

        photo_view.setTag(photo_url);
        imageLoader=new ImageLoader();
        imageLoader.showImageByAsyncTask(photo_view,photo_url);

        email_view.setText(email);
        name_view.setText(name);
        sex_view.setText(sex);
        age_view.setText(age);
        signature_view.setText(signature);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home://返回箭头
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_image:
                choosePhoto();
                break;
            case R.id.personal_name:
                showEditDialog("username");
                break;
            case R.id.personal_sex:
                showSexDialog();
                break;
            case R.id.personal_age:
                break;
            case R.id.personal_signature:
                showEditDialog("signature");
                break;
        }
    }

    //选择照片
    private void choosePhoto() {
        intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    private void showEditDialog(final String str) {
        final EditText editText=new EditText(Activity_personal_info.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (str.equals("username")) {
            editText.setText(name);
            builder.setTitle("修改你的昵称");
        }else if (str.equals("signature")){
            editText.setText(signature);
            builder.setTitle("修改签名");
        }
        builder.setView(editText);
        builder.setCancelable(false);
        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String me=editText.getText().toString();
                List<NameValuePair> paras=new ArrayList<NameValuePair>();
                paras.add(new BasicNameValuePair("operation","upuser"));
                paras.add(new BasicNameValuePair("attribute",str));
                paras.add(new BasicNameValuePair("value",me));
                paras.add(new BasicNameValuePair("userID",userID));
                new MyAsyncTask().execute(paras);
                if (str.equals("username")) {
                    name_view.setText(me);
                    GlobalParam.user.setUsername(me);
                }else if (str.equals("signature")){
                    signature_view.setText(me);
                    GlobalParam.user.setSignature(me);
                }
                refresh();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog=builder.create();
        alertDialog.show();
    }

    private void showSexDialog() {
        final String item[]={"男","女"};
        int index=0;//默认性别为男
        if (sex!=null)
            index=sex.equals("男")? 0:1;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(item,index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<NameValuePair> paras=new ArrayList<NameValuePair>();
                paras.add(new BasicNameValuePair("operation","upuser"));
                paras.add(new BasicNameValuePair("attribute","sex"));
                paras.add(new BasicNameValuePair("value",item[which].toString()));
                paras.add(new BasicNameValuePair("userID",userID));
                new MyAsyncTask().execute(paras);
                sex_view.setText(item[which].toString());
                GlobalParam.user.setSex(item[which]);
                refresh();
                alertDialog.dismiss();
            }
        });
        alertDialog=builder.create();
        alertDialog.show();
    }
    /************************************照片相关**************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                File selectbook = new File(picturePath);
                Intent i = new Intent("com.android.camera.action.CROP");
                i.setType("image/*");
                i.putExtra("data", picturePath);
                i.setDataAndType(Uri.fromFile(selectbook), "image/jpeg");
                i.putExtra("crop", "true");
                i.putExtra("aspectX", 1);
                i.putExtra("aspectY", 1);
                i.putExtra("outputX", 100);
                i.putExtra("outputY", 100);
                i.putExtra("return-data", true);

                this.startActivityForResult(i, 7);
            }
        if (requestCode == 7) {
            bitmap = data.getParcelableExtra("data");
            if (bitmap==null)//如果用户取消了，就返回
                return;
            photo_view.setImageBitmap(bitmap);
            pathString = ConstsUrl.upLoad_photo_URL + "&userID="
                    + String.valueOf(user.getUserId()) + "&value=true";
//
            new AsyncTask<Bitmap, Void, String>() {

                @Override
                protected String doInBackground(Bitmap... params) {

                    String end = "\r\n";
                    String twoHyphens = "--";
                    String boundary = "*****";
                    String newName = "pic.jpg";

                    HttpURLConnection conn;
                    String result = null;
                    try {
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

                        Bitmap bmpCompressed = Bitmap.createScaledBitmap(
                                params[0], 640, 480, true);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bmpCompressed.compress(Bitmap.CompressFormat.JPEG, 100,
                                bos);
                        byte[] data = bos.toByteArray();

                        ds.write(data);

                        ds.writeBytes(end);
                        ds.writeBytes(twoHyphens + boundary + twoHyphens
                                + end);

                        InputStream inputStream = conn.getInputStream();
                        byte[] read;
                        try {
                            read = StreamTool.read(inputStream);
                            result = new String(read, 0, read.length,
                                    "UTF-8");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    return result;//获得返回的信息
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Toast.makeText(Activity_personal_info.this,s.toString()+"成功",Toast.LENGTH_SHORT).show();
                    String url= ConstsUrl.BASE_URL+user.getPhoto();
                    GlobalParam.mCaches.remove(url);//清除原先的缓存
                    refresh();//发送广播修改主界面
                }
            }.execute(bitmap);


        }

    }

    /************************************刷新信息**************************************************/
    private void refresh(){
        intiview();
        //发送广播，此广播注册在MainActivity中
        Intent intent =new Intent("update_info");
        sendBroadcast(intent);
    }
    /************************************网络相关**************************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.LOGIN_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                int code = jsonObject.getInt("code");
                if (code==0)
                    Toast.makeText(Activity_personal_info.this, "修改失败",
                            Toast.LENGTH_SHORT).show();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
