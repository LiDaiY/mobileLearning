package com.example.MyAdapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.entity.Homework;
import com.example.testlogin.Activity_homework;
import com.example.testlogin.R;

import java.util.List;

import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * Created by 李宗豪 on 2018/4/28.
 */

public class MyAdapter_course_hw extends RecyclerView.Adapter<MyAdapter_course_hw.ViewHolder>{
    public String TAG="lzh";
    private List<Homework> homeworkList;

    public MyAdapter_course_hw(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }

    public AlertDialog alertDialog=null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homework_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.hwView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                final Homework homework=homeworkList.get(position);
                showContent(v,homework);
            }
        });
        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                final Homework homework=homeworkList.get(position);
                Intent intent=new Intent(Activity_homework.UPLOAD_HW);
                intent.putExtra("hwID",homework.getHwID());
                v.getContext().sendBroadcast(intent);
                Log.e(TAG, "发送广播上传作业" );
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Homework homework=homeworkList.get(position);
        holder.hwTitle.setText(homework.getHwTitle());
        holder.hwTime.setText("布置时间："+homework.getPublishTime());

    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }
    /**********************************viewholder*******************************************************/
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView hwTitle;
        TextView hwTime;
        Button subBtn;
        View hwView;//用于添加点击事件
        public ViewHolder(View itemView) {
            super(itemView);

            hwView=itemView;
            hwTitle=itemView.findViewById(R.id.hw_title);
            hwTime=itemView.findViewById(R.id.hw_time);
            subBtn=itemView.findViewById(R.id.hw_sub_btn);
        }
    }

/**********************************显示内容的对话框*******************************************************/
    private void showContent(View v,Homework homework) {
        View dialogView= LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_hw_content,null);

        JustifyTextView title=dialogView.findViewById(R.id.dialog_hw_title);
        JustifyTextView content=dialogView.findViewById(R.id.dialog_hw_content);

        title.setText("标题："+homework.getHwTitle()+"\n");
        content.setText("内容："+homework.getHwContent()+"\n");

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog=builder.create();
        alertDialog.show();
    }
}
