package com.example.testlogin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entity.Course;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_my_hw extends Fragment {

    private Course course;

    public Fragment_my_hw() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();//从activity传过来的Bundle
        course= (Course) bundle.getSerializable("course");
        return inflater.inflate(R.layout.fragment_my_hw, container, false);
    }

}
