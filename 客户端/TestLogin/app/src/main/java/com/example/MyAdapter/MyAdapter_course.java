package com.example.MyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HttpUtil.ConstsUrl;
import com.example.HttpUtil.ImageLoader;
import com.example.entity.Course;
import com.example.testlogin.R;

import java.util.List;

/**
 * Created by 李宗豪 on 2018/4/2.
 */

public class MyAdapter_course extends BaseAdapter{
    private List<Course> courses;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    public MyAdapter_course(Context context, List<Course> courses){
        this.courses=courses;
        layoutInflater=LayoutInflater.from(context);
//        imageLoader=new ImageLoader();//保证了只有一个缓存
    }
    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        Course course=courses.get(position);
        return course;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.course_item,null);
            viewHolder.cName=convertView.findViewById(R.id.course_name_view);
            viewHolder.teacherName=convertView.findViewById(R.id.course_teacher_view);
            viewHolder.cAbs=convertView.findViewById(R.id.course_abstract_view);
            viewHolder.cCover=convertView.findViewById(R.id.course_cover_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cName.setText(courses.get(position).getCourseName());
        viewHolder.teacherName.setText(courses.get(position).getTeacherName());
        viewHolder.cAbs.setText(courses.get(position).getCourseAbstract());

        String coverUrl= ConstsUrl.BASE_URL+courses.get(position).getCourseUrl();
        viewHolder.cCover.setTag(coverUrl);
        imageLoader=new ImageLoader();
        imageLoader.showImageByAsyncTask(viewHolder.cCover,coverUrl);
        Log.e("lzh","加载图片"+courses.get(position).getCourseName());
        return convertView;
    }
    class ViewHolder{
        public TextView cName,teacherName,cAbs;
        public ImageView cCover;
    }
}
