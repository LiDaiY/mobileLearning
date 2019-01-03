package com.example.HttpUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.example.GlobalParams.GlobalParam;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 李宗豪 on 2018/4/3.
 */

public class ImageLoader {
    RoundedImageView userPhoto;
    ImageView mImageView;
    String url;
    int tag;
    private LruCache<String, Bitmap> imageCaches;//设置缓存

//    private static ImageLoader instance=new ImageLoader();

    public ImageLoader(){
        //获取最大可用内存
        if (GlobalParam.mCaches==null) {//调用GlobalParam的缓存，保证了只有一个缓存
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxMemory / 4;
            GlobalParam.mCaches = new LruCache<String, Bitmap>(cacheSize) {

                //必须重写
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    //在每次存入缓存的时候调用
                    return value.getByteCount();
                }
            };
        }
        imageCaches=GlobalParam.mCaches;
    }
//    public static ImageLoader getInstance(){
//        return instance;
//    }

        //把图片放入缓存
    public void addBitmapToCache(String url, Bitmap bitmap){
        if(getBitmapFromCache(url)== null ){
            imageCaches.put(url, bitmap);
        }
    }
    //从缓存中获取数据
    public Bitmap getBitmapFromCache(String url){
        return imageCaches.get(url);
    }


//    //异步加载获得图片
//    public void showImageByAsyncTask(ImageView imageView,String url){
////        this.imageView=imageV;
//        this.url=url;
//        tag=2;//2表示加载其他图片
//        //从缓存中取出对应的图片
//        Bitmap bitmap = getBitmapFromCache(url); //从内存中找到图片
//        if(bitmap == null){//如果没有，必须去网络下载
//            new picAsyncTask(imageView).execute(url);
//        }else {
//                imageView.setImageBitmap(bitmap);
//        }
//    }
//    //异步加载获得图片
//    public void showImageByAsyncTask(RoundedImageView roundedImageView,String url){
////        userPhoto=roundedImageView;
//        this.url=url;
//        tag=1;//1表示加载头像
//        //从缓存中取出对应的图片
//        Bitmap bitmap = getBitmapFromCache(url); //从内存中找到图片
//        if(bitmap == null){//如果没有，必须去网络下载
//            new picAsyncTask(roundedImageView).execute(url);
//        }else {
//                roundedImageView.setImageBitmap(bitmap);
//        }
//    }




    public Bitmap getBitmapFromURL(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig= Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeStream(is,null,options);//这么做占用的内存小
//            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if (is!=null)
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }
/***************************使用AsyncTask显示图片******************************************************************/
//    private class picAsyncTask extends AsyncTask<String,Void,Bitmap>{
//        public picAsyncTask(ImageView imageView){
//            mImageView=imageView;
//            tag=2;
//        }
//        public picAsyncTask(RoundedImageView roundedImageView){
//            userPhoto=roundedImageView;
//            tag=1;
//        }
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            String url = strings[0];
//            //从网络获取图片
//            Bitmap bitmap = getBitmapFromURL(strings[0]);
//            if(bitmap != null){
//                //下载到了图片 就保存到Cache中去
//                addBitmapToCache(url, bitmap);
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            if (bitmap==null) return;
//
//            if (tag==1){
//                userPhoto.setImageBitmap(bitmap);
//            }else if(tag==2&&mImageView.getTag().equals(url)){
//                mImageView.setImageBitmap(bitmap);
//            }
//        }
//    }

    /***************************多线程******************************************************************/
    public Handler  mhandler = new Handler(){  //多线程方法中使用的
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (tag==2) {
                if (mImageView.getTag().equals(url)) {
                    mImageView.setImageBitmap((Bitmap) msg.obj);
                }
            }else if (tag==1){
                userPhoto.setImageBitmap((Bitmap) msg.obj);
            }
        };
    };

    public void showImageByAsyncTask(final ImageView imageView, final String url){
        mImageView=imageView;
        this.url=url;
        tag=2;
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap!=null){
            Message message =Message.obtain();
            message.obj = bitmap;
            mhandler.sendMessage(message);
            return;
        }

        new Thread(){
            @Override
            public void run() {
                super.run();
                    //从网络获取图片
                    Bitmap bitmap = getBitmapFromURL(url);
                    if (bitmap != null) {
                        //下载到了图片 就保存到Cache中去
                        addBitmapToCache(url, bitmap);
                    }else if (bitmap==null){
                        //如果没有下载到
                        return;
                    }
                Message message =Message.obtain();
                message.obj = bitmap;
                mhandler.sendMessage(message);
            }
        }.start();
    }

    public void showImageByAsyncTask(final RoundedImageView imageView, final String url){
        userPhoto=imageView;
        this.url=url;
        tag=1;
        new Thread(){
            @Override
            public void run() {
                super.run();
                //从内存中找到图片
                Bitmap bitmap = getBitmapFromCache(url);
                if(bitmap == null) {//如果没有，必须去网络下载
                    //从网络获取图片
                    bitmap = getBitmapFromURL(url);
                    if (bitmap != null) {
                        //下载到了图片 就保存到Cache中去
                        addBitmapToCache(url, bitmap);
                    }else if (bitmap==null){
                        //如果没有下载到
                        return;
                    }
                }
                Message message =Message.obtain();
                message.obj = bitmap;
                mhandler.sendMessage(message);
            }
        }.start();
    }

}
