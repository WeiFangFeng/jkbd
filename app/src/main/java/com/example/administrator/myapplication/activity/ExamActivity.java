package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.bean.ExamInfo;
import com.example.administrator.myapplication.utils.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_ExamTitle,tvop1,tvop2,tvop3,tvop4;
   ImageView mImageView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        intView();
        intData();
        
    }

    private void intView() {
          tvExamInfo=(TextView) findViewById(R.id.tv_examinfo);
          tv_ExamTitle=(TextView) findViewById(R.id.tv_exam_title);
          tv_ExamTitle =(TextView)findViewById(R.id.tv_exam_title);
          tvop1=(TextView) findViewById(R.id.tv_op1);
        tvop2=(TextView)findViewById(R.id.tv_op2);
        tvop3=(TextView)findViewById(R.id.tv_op3);
        tvop4=(TextView)findViewById(R.id.tv_op4);
        mImageView=(ImageView)findViewById(R.id.im_exam);

    };
    private void intData() {
        ExamInfo examInfo=ExamApplication.getInstance().getMExamInfo();
        if(examInfo!=null) {
            showData(examInfo);
        }
       List<Exam>examList= ExamApplication.getInstance().getMExamList();
       if(examList!=null){
           showExam(examList);

       }
        }

    private void showExam(List<Exam> examList) {
       Exam exam=examList.get(0);
        if (exam != null) {

            tv_ExamTitle.setText(exam.getQuestion());
            tvop1.setText(exam.getItem1());
            tvop2.setText(exam.getItem2());
            tvop3.setText(exam.getItem3());
            tvop4.setText(exam.getItem4());
            Picasso.with(ExamActivity.this).load(exam.getUrl()).into(mImageView);

        }
    }


    private void showData(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
         }


}
