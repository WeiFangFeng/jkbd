package com.example.administrator.myapplication.dao;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.activity.ExamActivity;
import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.bean.ExamInfo;
import com.example.administrator.myapplication.bean.Result;
import com.example.administrator.myapplication.utils.OkHttpUtils;
import com.example.administrator.myapplication.utils.ResultUtils;

import java.util.List;

/**
 * Created by weifangfeng on 2017/7/2.
 */

public class ExamDao implements IExamDao {
    @Override
    public void loadExamInfo() {
        Log.e("aa","ww");
        Log.e("aa","ww00000555555555555"+ExamApplication.getInstance());
        OkHttpUtils<ExamInfo> utils= new OkHttpUtils<>(ExamApplication.getInstance());
        Log.e("aa","ww00000"+utils);
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";

        utils.url(uri).targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setMExamInfo(result);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                        .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,true));
                    }

                    @Override
                    public void onError(String error)
                    {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,false));

                    }
                });

    }

    @Override
    public void loadQusetionLists() {

        OkHttpUtils<String>utils1=new OkHttpUtils<String>(ExamApplication.getInstance());
        String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand\n";
        utils1.url(url2)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String jsonStr) {
                       boolean success=false;
                        Result result= ResultUtils.getListResultFromJson(jsonStr);
                        if(result!=null && result.getError_code()==0){
                            List<Exam> list=result.getResult();
                            if(list!=null && list .size()>0){
                                ExamApplication.getInstance().setMExamList(list);
                                success=true;
                            }
                            ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                    .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,success));

                        }

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,false));

                    }
                });
    }




}
