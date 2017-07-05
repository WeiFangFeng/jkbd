package com.example.administrator.myapplication.biz;

import com.example.administrator.myapplication.bean.Exam;

/**
 * Created by weifangfeng on 2017/7/2.
 */

public interface IExamBiz {

    void beginExam();
    Exam getInExam();
    Exam getInExam(int index);
    Exam nextQuestion();
    Exam preQuestion();
    int commitExam();
    String getExamIndex();
}
