package com.example.administrator.myapplication.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.view.QuestionAdapter;
import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.bean.ExamInfo;
import com.example.administrator.myapplication.biz.ExamBiz;
import com.example.administrator.myapplication.biz.IExamBiz;
import com.example.administrator.myapplication.utils.OkHttpUtils;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvTime,tvExamInfo,tv_ExamTitle,tv0p1,tv0p2,tv0p3,tv0p4,tv_load,tv_NO;
     CheckBox cb01,cb02,cb03,cb04;
    CheckBox[] cbs=new  CheckBox[4];
    LinearLayout layoutLoading,layout03,layout04;
    ImageView mImageView;
    ProgressBar dialog;
    Gallery mGallery;
    IExamBiz biz;
    QuestionAdapter mAdapter;
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
        biz=new ExamBiz();
        loadData();
        
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
                Log.e("123","biz="+biz);
                biz.beginExam();
            }
        } ).start();
    }

    private void intView() {
        tv_NO = (TextView) findViewById(R.id.tv_exam_no);
        layoutLoading = (LinearLayout) findViewById(R.id.layout_loading);
        layout03=(LinearLayout)findViewById(R.id.layout_03);
        layout04=(LinearLayout)findViewById(R.id.layout_04);
        dialog = (ProgressBar) findViewById(R.id.load_dialog);
        tvExamInfo = (TextView) findViewById(R.id.tv_examinfo);
        tv_ExamTitle = (TextView) findViewById(R.id.tv_exam_title);
        tv_ExamTitle = (TextView) findViewById(R.id.tv_exam_title);
        mGallery=(Gallery) findViewById(R.id.galley);
        tv0p1 = (TextView) findViewById(R.id.tv_op1);
        tv0p2 = (TextView) findViewById(R.id.tv_op2);
        tv0p3 = (TextView) findViewById(R.id.tv_op3);
        tv0p4 = (TextView) findViewById(R.id.tv_op4);
        tv_load = (TextView) findViewById(R.id.tv_load);
        cb01 = (CheckBox) findViewById(R.id.cb_01);
        cb02 = (CheckBox) findViewById(R.id.cb_02);
        cb03 = (CheckBox) findViewById(R.id.cb_03);
        cb04 = (CheckBox) findViewById(R.id.cb_04);
        cbs[0] = cb01;
        cbs[1] = cb02;
        cbs[2] = cb03;
        cbs[3] = cb04;
        mImageView = (ImageView) findViewById(R.id.im_exam);
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
        cb01.setOnCheckedChangeListener(listener);
        cb02.setOnCheckedChangeListener(listener);
        cb03.setOnCheckedChangeListener(listener);
        cb04.setOnCheckedChangeListener(listener);
    }
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener(){

            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int userAnswer = 0;
                    switch (buttonView.getId()) {
                        case R.id.cb_01:
                            userAnswer=1;
                            break;
                        case R.id.cb_02:
                            userAnswer=2;
                            break;
                        case R.id.cb_03:
                            userAnswer=3;
                            break;
                        case R.id.cb_04:
                            userAnswer=4;
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
    private void initData() {
        if(isLoadExamInfoReceiver && isLoadQuestionReceiver){
            if (isExamInfo && isQuestion){
                layoutLoading.setVisibility(View.GONE);
                ExamInfo examInfo=ExamApplication.getInstance().getMExamInfo();
                if(examInfo!=null) {
                    showData(examInfo);
                    //倒计时方法
                   initTime(examInfo);
                }
                initGallery();
                showExam(biz.getInExam());


            }else{
                layoutLoading.setEnabled(true);
                tv_load.setText("下载失败,点击重新下载");
                dialog.setVisibility(View.GONE);


            }

        }

        }

    private void initGallery() {


        mAdapter=new QuestionAdapter(this);
        mGallery.setAdapter(mAdapter);
        //Log.e("gallery","gallery item position="+position);
       mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Log.e("gallery","gallery item position="+position);
               saveUserAnswer();;
               showExam(biz.getInExam(position));
           }
       });

    }

    private void initTime(ExamInfo examInfo) {
    int sumTime =examInfo.getLimitTime()*60*1000;
        //int sumTime =60*1000;
      Log.e("time","sumTime="+sumTime);
        final  long overTime =sumTime+System.currentTimeMillis();
        final Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
           long l=overTime-System.currentTimeMillis();
                Log.e("time","l="+l);
                final  long min=l/1000/60;
                final  long sec=l/1000%60;
            // Log.e("Time","min="+min+"sec="+sec);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("剩余时间:"+min+"分"+sec+"秒");
                    }
                });


            }
        },0,1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        },sumTime);

    }

    private void showExam(Exam exam) {
         Log.e("showExam","showExam,exam="+exam);
        if (exam != null) {
            tv_NO.setText(biz.getExamIndex());
            tv_ExamTitle.setText(exam.getQuestion());
            tv0p1.setText(exam.getItem1());
            tv0p2.setText(exam.getItem2());
           // Log.e("123","tv0p3="+tv0p3);
           // Log.e("123","exam.getItem3()="+exam.getItem3());
            tv0p3.setText(exam.getItem3());
            tv0p4.setText(exam.getItem4());
            tvTime=(TextView) findViewById(R.id.tv_time);
            layout03.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
            cb03.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
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

          resetOptions();
            String userAnswer=exam.getAnswer();
            if(userAnswer!=null && userAnswer.equals("")){
                int userCB=Integer.parseInt(userAnswer)-1;
                cbs[userCB].setChecked(true);

            }
        }
    }

    private void resetOptions() {
        for (CheckBox cb: cbs) {
            cb.setChecked(false);
            
        }
    }

  private void saveUserAnswer(){

      for (int i=0;i<cbs.length;i++){
        if(cbs[i].isChecked()){
            biz.getInExam().setUserAnswer(String.valueOf(i+1));
            return;
        }
      }
      Log.e("save","saveUserAvswer,biz.getExam()"+biz.getInExam());
      biz.getInExam().setUserAnswer("");
      mAdapter.notifyDataSetChanged();
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
        saveUserAnswer();
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        saveUserAnswer();
        showExam(biz.nextQuestion());

    }

    public void commit(View view) {
        saveUserAnswer();
        int s=biz.commitExam();
        View inflate=View .inflate(this,R.layout.result,null);
        TextView tvResult=(TextView)inflate.findViewById(R.id.tv_result);
         tvResult.setText("你的分数\n"+s+"分！");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                //.setMessage("你的分数为\n"+s+"分！")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    finish();

                    }
                });
        builder.create().show();

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
