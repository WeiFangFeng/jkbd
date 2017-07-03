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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.bean.ExamInfo;
import com.example.administrator.myapplication.biz.ExamBiz;
import com.example.administrator.myapplication.biz.IExamBiz;
import com.example.administrator.myapplication.utils.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Properties;


/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_ExamTitle,tv0p1,tv0p2,tv0p3,tv0p4,tv_load,tv_NO;
     CheckBox cb01,cb02,cb03,cb04;
    CheckBox[] cbs=new  CheckBox[4];
    LinearLayout layoutLoading,layout03,layout04;
    ProgressBar dialog;
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
        biz=new ExamBiz();
        
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));


    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tv_load.setText("下载数据");
        new Thread(new Runnable(){
            @Override
            public void run() {

                biz.beginExam();
            }
        } ).start();
    }

    private void intView() {
          tv_NO=(TextView) findViewById(R.id.tv_exam_no);
          layoutLoading=(LinearLayout)findViewById(R.id.layout_loading);
        layoutLoading=(LinearLayout)findViewById(R.id.layout_03);
        layoutLoading=(LinearLayout)findViewById(R.id.layout_04);
        dialog=(ProgressBar)findViewById(R.id.load_dialog);
          tvExamInfo=(TextView) findViewById(R.id.tv_examinfo);
          tv_ExamTitle=(TextView) findViewById(R.id.tv_exam_title);
          tv_ExamTitle =(TextView)findViewById(R.id.tv_exam_title);
          tv0p1=(TextView) findViewById(R.id.tv_op1);
          tv0p2=(TextView)findViewById(R.id.tv_op2);
          tv0p3=(TextView)findViewById(R.id.tv_op3);
          tv0p4=(TextView)findViewById(R.id.tv_op4);
          tv_load=(TextView)findViewById(R.id.tv_load);
          cb01=(CheckBox)findViewById( R.id.cb_01);
          cb02=(CheckBox)findViewById( R.id.cb_02);
          cb03=(CheckBox)findViewById( R.id.cb_03);
          cb04=(CheckBox)findViewById( R.id.cb_04);
         cbs[0]=cb01;
        cbs[1]=cb02;
        cbs[2]=cb03;
        cbs[3]=cb04;
          mImageView=(ImageView)findViewById(R.id.im_exam);
          layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
              /*cb01.setOnCheckedChangeListener(listener);
              cb02.setOnCheckedChangeListener(listener);
              cb03.setOnCheckedChangeListener(listener);
              cb04.setOnCheckedChangeListener(listener);

*/
          });
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener(){

            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int userAnswer = 0;
                    switch (buttonView.getId()) {
                        case R.id.cb_01:
                            break;
                        case R.id.cb_02:
                            break;
                        case R.id.cb_03:
                            break;
                        case R.id.cb_04:
                            break;
                    }

                    Log.e("checkChanged", "userAnswer=" + userAnswer + "isCheck=" + isChecked);
                    if (userAnswer > 0) {
                        for (CheckBox cb : cbs) {
                            cb.setChecked(false);
                        }
                        cbs[userAnswer - 1].setChecked(true);

                    }
                }
            }
        };


    }
    private void initData() {
        if(isLoadExamInfoReceiver && isLoadQuestionReceiver){
            if (isExamInfo && isQuestion){
                layoutLoading.setVisibility(View.GONE);

                ExamInfo examInfo=ExamApplication.getInstance().getMExamInfo();
                if(examInfo!=null) {
                    showData(examInfo);
                    showExam(biz.getInExam());
                }

            }else{
                layoutLoading.setEnabled(true);
                tv_load.setText("下载失败,点击重新下载");
                dialog.setVisibility(View.GONE);


            }

        }

        }

    private void showExam(Exam exam) {
         Log.e("showExam","showExam,exam="+exam);
        if (exam != null) {
            tv_NO.setText(biz.getExamIndex());
            tv_ExamTitle.setText(exam.getQuestion());
            tv0p1.setText(exam.getItem1());
            tv0p2.setText(exam.getItem2());
            tv0p3.setText(exam.getItem3());
            tv0p4.setText(exam.getItem4());
            layout03.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            cb03.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            layout04.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            cb04.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            if(exam.getUrl()!=null) {
                Picasso.with(ExamActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
            }
            else{
                mImageView.setVisibility(View.GONE);

            }


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

    public void preExam(View view) {
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        showExam(biz.nextQuestion());
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
