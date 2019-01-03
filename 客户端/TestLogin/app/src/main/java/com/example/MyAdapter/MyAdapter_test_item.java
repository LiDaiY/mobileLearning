package com.example.MyAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.StudentCourse;
import com.example.entity.Test;
import com.example.testlogin.Activity_test_question;
import com.example.testlogin.R;

import java.util.List;

/**
 * Created by 李宗豪 on 2018/4/13.
 */

public class MyAdapter_test_item extends RecyclerView.Adapter<MyAdapter_test_item.ViewHolder>{

    private List<Test> testList;



    /*************************************构造函数*************************************************/
    public MyAdapter_test_item(List<Test> testList){
        this.testList=testList;
    }
    /*************************************实现方法*************************************************/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_item,parent,false);
        final MyAdapter_test_item.ViewHolder holder=new MyAdapter_test_item.ViewHolder(view);

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position=holder.getAdapterPosition();
                Test test=testList.get(position);

                switch (checkedId){
                    case R.id.test_ans_a:
                        test.userAnswer="A";
                        break;
                    case R.id.test_ans_b:
                        test.userAnswer="B";
                        break;
                    case R.id.test_ans_c:
                        test.userAnswer="C";
                        break;
                    case R.id.test_ans_d:
                        test.userAnswer="D";
                        break;
                }
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Test test=testList.get(position);
        if (test.userAnswer.equals("")){//用户未作答则清空选项,避免显示错乱
            holder.radioGroup.clearCheck();
        }
        String question=test.getTestContent();
        String op=test.getTestOption();
        String[] option = op.split(";");

        holder.testQuestion.setText(question);
        holder.testAnsA.setText("A:"+option[0]);
        holder.testAnsB.setText("B:"+option[1]);
        holder.testAnsC.setText("C:"+option[2]);
        holder.testAnsD.setText("D:"+option[3]);

        if (!test.userAnswer.equals("未作答")){//如果显示的不是未作答，则是查看答案，则radiobutton显示用户的答案
            switch (test.userAnswer){
                case "A":
                    holder.btnA.setChecked(true);
                    break;
                case "B":
                    holder.btnB.setChecked(true);
                    break;
                case "C":
                    holder.btnC.setChecked(true);
                    break;
                case "D":
                    holder.btnD.setChecked(true);
                    break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    @Override
    public int getItemViewType(int position) {//必须重载此方法才能避免错乱
        return position;
    }

    /*************************************ViewHolder*************************************************/
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView testQuestion;
        TextView testAnsA;
        TextView testAnsB;
        TextView testAnsC;
        TextView testAnsD;
        RadioGroup radioGroup;
        RadioButton btnA,btnB,btnC,btnD;


        View cutLine;//分割线
        TextView showResult;//显示用户答案

        View testView;//用于添加点击事件

        public ViewHolder(View itemView) {
            super(itemView);
            testView=itemView;

            testQuestion=itemView.findViewById(R.id.test_question_view);
            testAnsA=itemView.findViewById(R.id.test_ans_a);
            testAnsB=itemView.findViewById(R.id.test_ans_b);
            testAnsC=itemView.findViewById(R.id.test_ans_c);
            testAnsD=itemView.findViewById(R.id.test_ans_d);

            radioGroup=itemView.findViewById(R.id.test_ans_group);
            btnA=itemView.findViewById(R.id.test_ans_a);
            btnB=itemView.findViewById(R.id.test_ans_b);
            btnC=itemView.findViewById(R.id.test_ans_c);
            btnD=itemView.findViewById(R.id.test_ans_d);

            cutLine=itemView.findViewById(R.id.test_sec_line);
            showResult=itemView.findViewById(R.id.test_result_view);
        }
    }
}
