package com.example.MyAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.ImageLoader;
import com.example.entity.ReplyPost;
import com.example.testlogin.Activity_bbs_deliver_post;
import com.example.testlogin.Activity_bbs_post_list;
import com.example.testlogin.R;
import com.rey.material.app.BottomSheetDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * Created by 李宗豪 on 2018/4/19.
 */

public class MyAdapter_bbs_reply_list extends RecyclerView.Adapter<MyAdapter_bbs_reply_list.ViewHolder>{
    private ImageLoader imageLoader;
    private List<ReplyPost> replyPosts;

    public MyAdapter_bbs_reply_list(List<ReplyPost> replyPosts) {
        this.replyPosts = replyPosts;
    }

    private Button dialog_del_btn;

    private Context context;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bbs_reply_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context=v.getContext();
                int position=holder.getAdapterPosition();
                ReplyPost replyPost=replyPosts.get(position);
                int userID= GlobalParam.user.getUserId();
                if (replyPost.userID==userID) {//只有删除自己的回复
                    showDialog(v,replyPost.getReplyID(),replyPost.getPostID());
                }
                return false;
            }
        });

        return holder;
    }

    private void showDialog(View v, final int replyID,final int postID) {
        final BottomSheetDialog dialog=new BottomSheetDialog(v.getContext());
        View dialogView= LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_reply_list,null);
        dialog.inDuration(300);
        dialog.outDuration(300);
        dialog.setContentView(dialogView);
        dialog_del_btn=dialogView.findViewById(R.id.dialog_reply_delete_view);
        dialog_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRep(v,replyID,postID);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteRep(View v, final int replyID,final int postID) {
        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
        builder.setMessage("确定删除吗？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<NameValuePair> paras=new ArrayList<NameValuePair>();
                paras.add(new BasicNameValuePair("operation","deleteReply"));
                paras.add(new BasicNameValuePair("replyID",String.valueOf(replyID)));
                paras.add(new BasicNameValuePair("postID",String.valueOf(postID)));
                new MyAsyncTask().execute(paras);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReplyPost replyPost=replyPosts.get(position);
        String email=replyPost.getUserEmail();
        String name=replyPost.getUserName();
        String content=replyPost.getReplyContent();
        String time=replyPost.getReplyTime();
        String signature="";
        int status=replyPost.getUserStatus();

        String url="";
        if (status==1) {//如果是老师
            url = ConstsUrl.BASE_URL + "res/teacher/" + email + "/pic.jpg";
            holder.userSignature.setText("老师");
            holder.userSignature.setTextColor(Color.RED);
        }else if (status==0){//如果是学生
            url = ConstsUrl.BASE_URL + "res/user/" + email + "/pic.jpg";
            signature=replyPost.signature;
            holder.userSignature.setText(signature);
        }
        holder.imageView.setTag(url);
        imageLoader=new ImageLoader();
        imageLoader.showImageByAsyncTask(holder.imageView,url);//设置头像

        holder.userName.setText(name);
        holder.replyTime.setText(time);
        holder.justifyTextView.setText(content+"\n");//必须加上换行，否则最后一行格式不正确
    }

    @Override
    public int getItemCount() {
        return replyPosts.size();
    }
    /************************************网络相关**************************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String> {

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.BBS_URL,lists[0]);
        }
//
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                int code = jsonObject.getInt("code");
                if (code==1){
                    Intent intent =new Intent("refreshReply");
                    context.sendBroadcast(intent);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /************************************ViewHolder******************************************************/
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView userName;
        TextView userSignature;
        TextView replyTime;
        JustifyTextView justifyTextView;//用于显示内容

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.reply_item_user_photo);
            userName=itemView.findViewById(R.id.reply_item_user_name);
            userSignature=itemView.findViewById(R.id.reply_item_user_signature);
            replyTime=itemView.findViewById(R.id.reply_item_send_time);
            justifyTextView=itemView.findViewById(R.id.reply_item_just_content);

            view=itemView;
        }
    }
}
