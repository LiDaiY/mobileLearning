package com.example.MyAdapter;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.entity.Notice;
import com.example.entity.StudentCourse;
import com.example.testlogin.Activity_test_answer;
import com.example.testlogin.Activity_test_question;
import com.example.testlogin.R;

import java.util.List;

/**
 * Created by 李宗豪 on 2018/4/13.
 * 这是在线测试显示课程的适配器
 */

public class MyAdapter_test_course_list extends RecyclerView.Adapter<MyAdapter_test_course_list.ViewHolder>{
    private List<StudentCourse> relationList;
    private ImageLoader imageLoader;
    private Intent intent;//用于跳转到测试题activity
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView coverImage;
        TextView testCourseState;
        TextView testCourseGrade;
        TextView testCourseName;

        View testView;//用于添加点击事件

        public ViewHolder(View itemView) {
            super(itemView);

            testView=itemView;

            coverImage=itemView.findViewById(R.id.test_course_cover_view);
            testCourseState=itemView.findViewById(R.id.test_course_state);
            testCourseGrade=itemView.findViewById(R.id.test_course_grade);
            testCourseName=itemView.findViewById(R.id.test_course_name_view);
        }
    }

    public MyAdapter_test_course_list(List<StudentCourse> relationList){
        this.relationList=relationList;
    }

    @Override
    public MyAdapter_test_course_list.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_course_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                StudentCourse sc=relationList.get(position);
//                int score=sc.getStudentGrade();
                String ans=sc.getStudentAnswer();
                GlobalParam.courseID=sc.getCourseID();//修改全局变量中的课程id
                if (ans.equals("")){//没有回答，跳转到答题部分
                    intent=new Intent(v.getContext(),Activity_test_question.class);
                    v.getContext().startActivity(intent);
                }else{//已作答，跳转到答案部分
                    intent =new Intent(v.getContext(), Activity_test_answer.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("studentAnswer",sc.getStudentAnswer());
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter_test_course_list.ViewHolder holder, int position) {
        StudentCourse studentCourse=relationList.get(position);
        String path="res/course/"+String.valueOf(studentCourse.getCourseID())+"/cover.jpg";
        String url= ConstsUrl.BASE_URL+path;
        holder.coverImage.setTag(url);
        imageLoader=new ImageLoader();
        imageLoader.showImageByAsyncTask(holder.coverImage,url);//设置封面

        int courseID=studentCourse.getCourseID();
        int studentGrade=studentCourse.getStudentGrade();
        String studentAnswer=studentCourse.getStudentAnswer();
        String courseName= GlobalParam.cNameMap.get(courseID);//通过课程id在全局变量的键值对中拿到课程名

        holder.testCourseName.setText(courseName);
        if (studentAnswer.equals("")){//0说明没考
            holder.testCourseState.setText("测试状态：未测试");
        }else {
            holder.testCourseState.setText("测试状态：已测试");
        }

        holder.testCourseGrade.setText(studentGrade+"");
    }

    @Override
    public int getItemCount() {
        return relationList.size();
    }
}
