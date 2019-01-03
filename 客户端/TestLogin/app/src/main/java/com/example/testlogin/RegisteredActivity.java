package com.example.testlogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
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

public class RegisteredActivity extends AppCompatActivity {
    private EditText registerEmail,registerName,passwordOne,passwordTwo;
    private AppCompatButton registerBtn;
    private TextView login;
    private String rEmail,rName,rPassOne,rPassTwo;
    private TextInputLayout textInputLayoutEmail,textInputLayoutPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_registered);
        init();
    }

    private void init() {
        registerEmail=findViewById(R.id.input_register_email);
        registerName=findViewById(R.id.input_register_name);
        passwordOne=findViewById(R.id.input_register_password);
        passwordTwo=findViewById(R.id.input_register_password_confirm);
        registerBtn=findViewById(R.id.btn_register);
        login=findViewById(R.id.link_login);
        textInputLayoutEmail=findViewById(R.id.input_layout_register_email);
        textInputLayoutPass=findViewById(R.id.input_layout_register_password);
        getInputInfo();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textInputLayoutEmail.setError("");
                }else{
                    getInputInfo();
                    if (!rEmail.contains("@")){
                        textInputLayoutEmail.setError("邮箱格式有误");
                    }
                }
            }
        });
        passwordTwo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textInputLayoutPass.setError("");
                }else{
                    getInputInfo();
                    if (!rPassOne.equals(rPassTwo)){
                        textInputLayoutPass.setError("两次密码不一样");
                    }
                }
            }
        });
    }
    private void getInputInfo(){
        rEmail=registerEmail.getText().toString();
        rName=registerName.getText().toString();
        rPassOne=passwordOne.getText().toString();
        rPassTwo=passwordTwo.getText().toString();
    }
    private void attemptRegister() {
        getInputInfo();
        textInputLayoutEmail.setError("");
        textInputLayoutPass.setError("");
        if (!rEmail.contains("@")){
            textInputLayoutEmail.setError("邮箱格式有误");
            return;
        }
        if(!rPassOne.equals(rPassTwo)){
            textInputLayoutPass.setError("两次密码不一样");
            return;
        }
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("email",rEmail);
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("password",rPassOne);
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("operation","register");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("username",rName);
        paras.add(nameValuePair);
        new AsyncTask<List<NameValuePair>,Void,String>(){
            @Override
            protected String doInBackground(List<NameValuePair>[] lists) {
                return AskForInternet.post(ConstsUrl.LOGIN_URL,lists[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int code = jsonObject.getInt("code");
                    if(code==0){
                        Toast.makeText(getApplicationContext(),"该邮箱已被注册",Toast.LENGTH_SHORT).show();
                    }else if(code==1){
                        Toast.makeText(getApplicationContext(), "注册成功",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(paras);
    }

}
