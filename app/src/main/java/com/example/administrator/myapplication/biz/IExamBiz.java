package com.example.administrator.myapplication.biz;

/**
 * Created by weifangfeng on 2017/7/2.
 */

public interface IExamBiz {
    void beginExam();
    void nextQuestion();
    void preQuestion();
    void commitExam();
}
