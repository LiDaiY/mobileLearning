package com.example.testlogin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;


import android.os.Bundle;


import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;


import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.entity.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class LoginActivity extends AppCompatActivity  {
    public static String TAG="lzh";
    private EditText accountText,passText;
    private AppCompatButton loginB;
    private User user;
    private Intent intent;
    private CheckBox checkBox;
    private TextView registerText;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.materil_login_layout);

        init();
    }

    private void init() {
        accountText=findViewById(R.id.input_email);
        passText=findViewById(R.id.input_password);
        loginB=findViewById(R.id.btn_login);
        checkBox=findViewById(R.id.login_checkBox);
        registerText=findViewById(R.id.link_register);
        user=new User();
        preferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        editor = preferences.edit();
        String str1 = preferences.getString("userEmail",null);
        String  str2= preferences.getString("userPass",null);
        if (str1==null||str2==null){
            checkBox.setChecked(false);
        }else{
            accountText.setText(str1);
            passText.setText(str2);
            checkBox.setChecked(true);
        }
        loginB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        registerText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
    }

    private void startRegister() {
        intent =new Intent(LoginActivity.this,RegisteredActivity.class);
        startActivity(intent);
    }

    private void attemptLogin() {
        Log.e(TAG, "点击登陆按钮");
        String email = accountText.getText().toString();
        String password = passText.getText().toString();
        if (checkBox.isChecked()){
            editor.putString("userEmail",email);
            editor.putString("userPass",password);
            editor.commit();
        }else{
            editor.remove("userEmail");
            editor.remove("userPass");
            editor.commit();
        }
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("email",email);
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("password",password);
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("operation","login");
        paras.add(nameValuePair);

        new MyAsyncTask().execute(paras);
        }
    public class MyAsyncTask extends AsyncTask<List<NameValuePair> ,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.LOGIN_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                int code = jsonObject.getInt("code");
                if(code==0){
                    Toast.makeText(getApplicationContext(),"用户不存在或密码错误",Toast.LENGTH_SHORT).show();
                }else if(code==1){
                    GlobalParam.USER_LOGIN_STATE=1;//表示成功登陆
                    Toast.makeText(getApplicationContext(), "登录成功",
                            Toast.LENGTH_LONG).show();
                    //从服务器中获取user的属性 放入 user
                    jsonObject = jsonObject.getJSONObject("user");
                    user.setUserId(jsonObject.optInt("userId"));

                    user.setEmail(jsonObject.optString("email"));
                    user.setPassword(jsonObject
                            .optString("password"));
                    user.setUsername(jsonObject
                            .optString("username"));
                    user.setBirth(jsonObject.optString("birth"));
                    user.setSex(jsonObject.optString("sex"));
                    user.setSignature(jsonObject
                            .optString("signature"));
                    user.setType(jsonObject.optInt("type"));
                    user.setPhoto(jsonObject.optString("photo"));
                    intent=new Intent(LoginActivity.this,MainActivity.class);
//                            intent.putExtra("user",user);
                    GlobalParam.user=user;
                    startActivity(intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

