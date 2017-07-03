package com.example.administrator.myapplication.biz;

import com.example.administrator.myapplication.dao.ExamDao;
import com.example.administrator.myapplication.dao.IExamDao;

/**
 * Created by weifangfeng on 2017/7/2.
 */

public class ExamBiz  implements IExamBiz{
    IExamDao dao;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        dao.loadExamInfo();
        dao.loadQusetionLists();

    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
