package com.example.administrator.myapplication.biz;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.bean.Exam;
import com.example.administrator.myapplication.dao.ExamDao;
import com.example.administrator.myapplication.dao.IExamDao;

import java.util.List;

/**
 * Created by weifangfeng on 2017/7/2.
 */

public class ExamBiz  implements IExamBiz {
    IExamDao dao;
    int examIndex = 0;
    List<Exam> examList = null;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        examIndex = 0;
        dao.loadExamInfo();
        dao.loadQusetionLists();


    }

    @Override
    public Exam getInExam() {
        examList = ExamApplication.getInstance().getMExamList();
        if (examList!= null) {
            return examList.get(examIndex);
        } else {
            return null;
        }
    }

    @Override
    public Exam nextQuestion() {
        if (examList != null && examIndex < examList.size() - 1) {
            examIndex++;
            return  examList.get(examIndex);
        } else {
            return null;
        }
    }



    @Override
    public  Exam preQuestion() {

        if (examList != null && examIndex>0) {
            examIndex--;
            return  examList.get(examIndex);
        } else {
            return null;
        }
    }

    @Override
    public void commitExam() {

    }

    @Override
    public String  getExamIndex() {
        return (examIndex+1)+".";
    }
}
