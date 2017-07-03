package com.example.administrator.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.bean.ExamInfo;
import com.example.administrator.myapplication.biz.IExamBiz;
import com.example.administrator.myapplication.utils.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_ExamTitle,tv0p1,tv0p2,tv0p3,tv0p4,tv_load;
     LinearLayout layoutLoading;

    ImageView mImageView;
    IExamBiz biz;
    boolean isLoadExamInfoReceiver=false;
    boolean  isLoadQuestionReceiver=false;

     boolean isExamInfo=false;
    boolean  isQuestion=false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast =new LoadExamBroadcast();
        mLoadQuestionBroadcast=new LoadQuestionBroadcast();
       setListener();

        intView();
        loadData();
        
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));


    }

    private void loadData() {
        new Thread(new Runnable(){
            @Override
            public void run() {

                biz.beginExam();
            }
        } ).start();
    }

    private void intView() {
        layoutLoading=(LinearLayout)findViewById(R.id.layout_loading);
          tvExamInfo=(TextView) findViewById(R.id.tv_examinfo);
          tv_ExamTitle=(TextView) findViewById(R.id.tv_exam_title);
          tv_ExamTitle =(TextView)findViewById(R.id.tv_exam_title);
          tv0p1=(TextView) findViewById(R.id.tv_op1);
        tv0p2=(TextView)findViewById(R.id.tv_op2);
        tv0p3=(TextView)findViewById(R.id.tv_op3);
        tv0p4=(TextView)findViewById(R.id.tv_op4);
        tv_load=(TextView)findViewById(R.id.tv_load);
        mImageView=(ImageView)findViewById(R.id.im_exam);

    };
    private void initData() {
        if(isLoadExamInfoReceiver && isLoadQuestionReceiver){
            if (isExamInfo && isQuestion){
                layoutLoading.setVisibility(View.GONE);

                ExamInfo examInfo=ExamApplication.getInstance().getMExamInfo();
                if(examInfo!=null) {
                    showData(examInfo);
                }
                List<Exam>examList= ExamApplication.getInstance().getMExamList();
                if(examList!=null){
                    showExam(examList);

                }

            }else{
                tv_load.setText("下载失败,点击重新下载");


            }

        }

        }

    private void showExam(List<Exam> examList) {
       Exam exam=examList.get(0);
        if (exam != null) {

            tv_ExamTitle.setText(exam.getQuestion());
            tv0p1.setText(exam.getItem1());
            tv0p2.setText(exam.getItem2());
            tv0p3.setText(exam.getItem3());
            tv0p4.setText(exam.getItem4());
            Picasso.with(ExamActivity.this)
                    .load(exam.getUrl())
                    .into(mImageView);

        }
    }


    private void showData(ExamInfo examInfo) {

        tvExamInfo.setText(examInfo.toString());

         }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);

        }
        if(mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    class LoadExamBroadcast extends BroadcastReceiver {
             @Override
             public void onReceive(Context context, Intent intent) {
                 boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_EXAM_SUCCESS,false);
                 Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
                 if(isSuccess){
                     isExamInfo=true;
                 }
                 isLoadExamInfoReceiver=true;
                 initData();
             }
         }

    class LoadQuestionBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_EXAM_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isSuccess);

            if(isSuccess){
                isQuestion=true;
            }
            isLoadQuestionReceiver=true;
            initData();
        }
    }
}
