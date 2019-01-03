package com.example.MyAdapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.ImageLoader;
import com.example.entity.Course;
import com.example.testlogin.Activity_course_detail;
import com.example.testlogin.R;

import java.util.List;

/**
 * Created by 李宗豪 on 2018/4/30.
 */

public class MyAdaper_course_use_recycler extends RecyclerView.Adapter<MyAdaper_course_use_recycler.ViewHolder> {
    private ImageLoader imageLoader;
    private List<Course> courses;

    public MyAdaper_course_use_recycler(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Course course=courses.get(position);
                Intent intent=new Intent(v.getContext(),Activity_course_detail.class);
                intent.putExtra("course",course);
                intent.putExtra("user", GlobalParam.user);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course=courses.get(position);
        String coverUrl= ConstsUrl.BASE_URL+course.getCourseUrl();
        holder.cCover.setTag(coverUrl);
        imageLoader=new ImageLoader();
        imageLoader.showImageByAsyncTask(holder.cCover,coverUrl);
        Log.e("lzh","加载图片"+courses.get(position).getCourseName());
        holder.cName.setText(course.getCourseName());
        holder.teacherName.setText(course.getTeacherName());
        holder.cAbs.setText(course.getCourseAbstract());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
/**************************************************************************************/
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView cName,teacherName,cAbs;
        ImageView cCover;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;

            cName=itemView.findViewById(R.id.course_name_view);
            teacherName=itemView.findViewById(R.id.course_teacher_view);
            cAbs=itemView.findViewById(R.id.course_abstract_view);
            cCover=itemView.findViewById(R.id.course_cover_view);
        }
    }
}
