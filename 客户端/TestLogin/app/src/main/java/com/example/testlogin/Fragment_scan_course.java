package com.example.testlogin;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.MyAdapter.MyAdaper_course_use_recycler;
import com.example.entity.Course;
import com.example.entity.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_scan_course extends Fragment {

//    private View view;
//    private ListView listView_course;
    private List<Course> courses=new ArrayList<Course>();
    private Intent intent;//用于页面的跳转
    private User user;

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MyAdaper_course_use_recycler myAdaper_courses;

    public Fragment_scan_course() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_scan_course, container, false);
//        listView_course=view.findViewById(R.id.list_view_all_course);
        user=((MainActivity)getActivity()).getActivityUser();//获得用户，用于选课或退课时传值
        initView(view);
        initFunc();
        return view;
    }

    private void initFunc() {
//        listView_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Course course= (Course) parent.getItemAtPosition(position);
////                Toast.makeText(getActivity(),course.toString(),Toast.LENGTH_SHORT).show();
//                intent=new Intent(getActivity(),Activity_course_detail.class);
//        intent.putExtra("course",course);
//        intent.putExtra("user",user);
//        startActivity(intent);
//            }
//        });
    }

    private void initView(View view) {

        layoutManager=new LinearLayoutManager(getContext());
        recyclerView=view.findViewById(R.id.list_view_all_course);
        recyclerView.setLayoutManager(layoutManager);

        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findAllCourse");
        paras.add(nameValuePair);
        courses.removeAll(courses);
        new MyAsyncTask().execute(paras);

    }
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String>{

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.COURSE_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray courseArray=jsonObject.getJSONArray("courses");
                for(int i=0;i<courseArray.length();i++){
                    Course course=new Course();
                    course.courseID=courseArray.getJSONObject(i).optInt("courseID");
                    course.courseName=courseArray.getJSONObject(i).optString("courseName");
                    course.teacherName=courseArray.getJSONObject(i).optString("teacherName");
                    course.courseUrl=courseArray.getJSONObject(i).optString("courseUrl");
                    course.courseAbstract=courseArray.getJSONObject(i).optString("courseAbstract");
                    course.type=courseArray.getJSONObject(i).optInt("type");
                    course.teacherID=courseArray.getJSONObject(i).optInt("teacherID");
                    course.detailInfo=courseArray.getJSONObject(i).optString("detailInfo");
                    Log.i("lzh",course.toString());
//                        Toast.makeText(getActivity(),course.toString(), Toast.LENGTH_SHORT).show();
                    courses.add(course);
                    GlobalParam.cNameMap.put(course.getCourseID(),course.getCourseName());
                }
//                listView_course.setAdapter(new MyAdapter_course(getActivity(),courses));
                myAdaper_courses=new MyAdaper_course_use_recycler(courses);
                recyclerView.setAdapter(myAdaper_courses);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
