package com.example.testlogin;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.GlobalParams.GlobalParam;
import com.example.HttpUtil.AskForInternet;
import com.example.HttpUtil.ConstsUrl;
import com.example.MyAdapter.MyAdapter_course_hw;
import com.example.entity.Course;
import com.example.entity.Homework;

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
public class Fragment_course_hw extends Fragment {
    private String TAG="lzh";

    private Course course;

    private int tag=1;//标志，1表示初始化，2表示刷新

    private RecyclerView recyclerView;
    private List<Homework> homeworkList=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private MyAdapter_course_hw myAdapter_course_hw;


    private SwipeRefreshLayout swipeRefresh;//下拉刷新
    public Fragment_course_hw() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_course_hw, container, false);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        course= (Course) bundle.getSerializable("course");
        initView(view);
        tag=1;
        initData();
        return view;
    }

    private void initData() {
        List<NameValuePair> paras=new ArrayList<NameValuePair>();
        paras.add(new BasicNameValuePair("operation","findCourseHw"));
        paras.add(new BasicNameValuePair("courseID",String.valueOf(course.getCourseID())));
        homeworkList.removeAll(homeworkList);
        new MyAsyncTask().execute(paras);
    }

    private void initView(View view) {
        layoutManager=new LinearLayoutManager(this.getContext());
        recyclerView=view.findViewById(R.id.course_hw_recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        //下拉刷新
        swipeRefresh=view.findViewById(R.id.course_hw_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHw();
            }
        });
    }
    /********************************通过网络获取作业************************************************/
    public class MyAsyncTask extends AsyncTask<List<NameValuePair>,Void,String> {

        @Override
        protected String doInBackground(List<NameValuePair>[] lists) {
            return AskForInternet.post(ConstsUrl.HW_URL,lists[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray hwArray = jsonObject.getJSONArray("hList");
                for (int i=0;i<hwArray.length();i++){
                    Homework homework;
                    int hwID=0;
                    int teacherID=0;
                    int courseID=0;
                    String hwContent=null;
                    String publishTime=null;
                    String hwTitle=null;
                    hwID=hwArray.getJSONObject(i).getInt("hwID");
                    teacherID=hwArray.getJSONObject(i).getInt("teacherID");
                    courseID=hwArray.getJSONObject(i).getInt("courseID");
                    hwContent=hwArray.getJSONObject(i).getString("hwContent");
                    publishTime=hwArray.getJSONObject(i).getString("publishTime");
                    hwTitle=hwArray.getJSONObject(i).getString("hwTitle");
                    if (hwID==0||teacherID==0||courseID==0||hwContent==null||publishTime==null||hwTitle==null){
                        Log.e(TAG, "onPostExecute: 作业列表有信息为空" );
                    }
                    homework=new Homework(hwID,teacherID,courseID,hwContent,publishTime,hwTitle);
                    homeworkList.add(homework);
                }
                if (tag==1) {//初始化
                    myAdapter_course_hw=new MyAdapter_course_hw(homeworkList);
                    recyclerView.setAdapter(myAdapter_course_hw);
                }else if (tag==2){
                    myAdapter_course_hw.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
    /********************************下拉刷新************************************************/
    private void refreshHw() {
        tag=2;
        initData();
    }

}
