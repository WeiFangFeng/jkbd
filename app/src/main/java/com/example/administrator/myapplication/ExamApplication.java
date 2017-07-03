package com.example.administrator.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.bean.ExamInfo;
import com.example.administrator.myapplication.bean.Result;
import com.example.administrator.myapplication.biz.ExamBiz;
import com.example.administrator.myapplication.biz.IExamBiz;
import com.example.administrator.myapplication.utils.OkHttpUtils;
import com.example.administrator.myapplication.utils.ResultUtils;

import java.util.List;

/**
 * Created by weifangfeng on 2017/7/2.
 */

public class ExamApplication extends Application {
   public  static String LOAD_EXAM_INFO="load_exam_info";
    public  static String LOAD_EXAM_QUESTION="load_exam_question";
    public static String LOAD_EXAM_SUCCESS="load_exam_success";
    ExamInfo mExamInfo;
    List<Exam> mExamList;
   private static ExamApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
       instance=this;

    }

    public static ExamApplication getInstance() {
        return instance;
    }

    public static void setInstance(ExamApplication instance) {
        ExamApplication.instance = instance;
    }



    public ExamInfo getMExamInfo() {
        return mExamInfo;
    }

    public void setMExamInfo(ExamInfo mExamInfo) {
        this.mExamInfo = mExamInfo;
    }

    public List<Exam> getMExamList() {
        return mExamList;
    }

    public void setMExamList(List<Exam> mExamList) {
        this.mExamList = mExamList;
    }
}
