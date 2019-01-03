package com.example.HttpUtil;

import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by 李宗豪 on 2018/4/1.
 */

public class AskForInternet {
    public static String TAG="lzh";
    public static HttpURLConnection conn;
    public AskForInternet() {

    }
    public static String post(String path, List<NameValuePair> paras) {
        String result = null;
        try {
            Log.e(TAG, "开始请求网络");
            conn = (HttpURLConnection) new URL(path)
                    .openConnection();
//            Log.e(TAG, "1");
            conn.setConnectTimeout(10000);
//            Log.e(TAG, "2");
            conn.setRequestMethod("POST");
//            Log.e(TAG, "3");
            conn.setDoOutput(true);
//            Log.e(TAG, "4");
            String params = parseParas(paras);
//            Log.e(TAG, "5");
            conn.getOutputStream().write(params.getBytes());
            Log.e(TAG, "post请求已发送");
            InputStream inputStream = conn.getInputStream();
//            Log.e(TAG, "7");
            byte[] read = StreamTool.read(inputStream);
            Log.e(TAG, "接收到服务器信息");
            result = new String(read, 0, read.length, "UTF-8");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn!=null){
                conn.disconnect();
            }
        }
        return result;
    }
    private static String parseParas(List<NameValuePair> paras) throws UnsupportedEncodingException{
        StringBuilder sb = new StringBuilder("");
        String name,value;
        if (paras != null) {
            for (NameValuePair nameValuePair : paras) {
                sb.append(nameValuePair.getName()).append("=")
                        .append(URLEncoder.encode(nameValuePair.getValue(),"utf-8"))
                        .append("&");

            }
        }
        return sb.toString();

    }
}
