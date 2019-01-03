package com.example.MyAdapter;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.ImageLoader;
import com.example.entity.Notice;
import com.example.testlogin.R;

import java.util.List;

/**
 * Created by 李宗豪 on 2018/4/10.
 */

public class MyAdapter_Notice extends RecyclerView.Adapter<MyAdapter_Notice.ViewHolder> {


    private List<Notice> mNotice;
    private ImageLoader imageLoader;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView coverImage;
        TextView noticeCourseNameView;
        TextView noticeTitleView;
        TextView noticeTimeView;

        View noticeView;//用于添加点击事件

        public ViewHolder(View itemView) {
            super(itemView);

            noticeView=itemView;

            coverImage=itemView.findViewById(R.id.coverImage);
            noticeCourseNameView=itemView.findViewById(R.id.noticeCourseNameView);
            noticeTitleView=itemView.findViewById(R.id.noticeTitleView);
            noticeTimeView=itemView.findViewById(R.id.noticeTimeView);
        }
    }
    public MyAdapter_Notice(List<Notice> notice){
        mNotice=notice;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_item,parent,false);
       final ViewHolder holder=new ViewHolder(view);
       holder.noticeView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int position=holder.getAdapterPosition();
               Notice notice =mNotice.get(position);
               showNoticeDialog(v,notice);
           }

       });
        return holder;
    }

    private void showNoticeDialog(View v,Notice notice) {
        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
        builder.setTitle(notice.getCourseName());
        String mes=notice.getNoticeTime()+"\n\n"+"标题：  "+notice.getNoticeTitle()+"\n\n"
                +"内容：  "+notice.getNoticeContent() +"\n";
        builder.setMessage(mes);
        builder.create().show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notice notice=mNotice.get(position);
        String path="res/course/"+String.valueOf(notice.getCourseID())+"/cover.jpg";
        String url= ConstsUrl.BASE_URL+path;
        holder.coverImage.setTag(url);
        imageLoader=new ImageLoader();
        imageLoader.showImageByAsyncTask(holder.coverImage,url);

        holder.noticeCourseNameView.setText(notice.getCourseName());
        holder.noticeTitleView.setText(notice.getNoticeTitle());
        holder. noticeTimeView.setText(notice.getNoticeTime());
    }

    @Override
    public int getItemCount() {
        return mNotice.size();
    }
}
