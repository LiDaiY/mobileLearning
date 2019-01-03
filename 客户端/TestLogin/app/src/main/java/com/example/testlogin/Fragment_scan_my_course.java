package com.example.testlogin;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.MyAdapter.MyAdapter_course;
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
public class Fragment_scan_my_course extends Fragment {
    private View view;
    private ListView listView_my_course;
    private List<Course> courses=new ArrayList<Course>();
    private Intent intent;//用于页面的跳转
    private User user;
    private String studentID;

    private String state="";

    public Fragment_scan_my_course() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_scan_my_course, container, false);
        initView();
        initFunc();
        return view;
    }

    private void initFunc() {
        listView_my_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course= (Course) parent.getItemAtPosition(position);
                if (state.equals("resource")) {
                    intent = new Intent(getActivity(), Activity_course_res_list.class);
                    intent.putExtra("course", course);
                    startActivity(intent);
                }else if (state.equals("homework")){
                    intent = new Intent(getActivity(), Activity_homework.class);
                    intent.putExtra("course", course);
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        listView_my_course=view.findViewById(R.id.list_view_my_course);
        user=((MainActivity)getActivity()).getActivityUser();//获得用户，用于查看选课情况
        state=((MainActivity)getActivity()).getState();//获得用户点击的是资源还是作业
        studentID=String.valueOf(user.getUserId());
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        NameValuePair nameValuePair=new BasicNameValuePair("operation","findMyCourse");
        paras.add(nameValuePair);
        nameValuePair=new BasicNameValuePair("studentID",studentID);
        paras.add(nameValuePair);
        new MyAsyncTask().execute(paras);
    }
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String> {

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

                    courses.add(course);
                }
                listView_my_course.setAdapter(new MyAdapter_course(getActivity(),courses));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
