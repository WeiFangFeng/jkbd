package com.example.administrator.myapplication.biz;

import android.util.Log;

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
        Log.e("examIndex","examIndex="+dao);
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
    public Exam getInExam(int index ) {
        examList = ExamApplication.getInstance().getMExamList();
        examIndex=index;
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
    public int commitExam() {
        int s=0;
        for (Exam exam : examList) {
            String userAnswer=exam.getUserAnswer();
            if(userAnswer!=null && !userAnswer.equals("")){
                if(exam.getAnswer().equals(userAnswer)){
                    s++;
                }
            }
        }
        Log.e("s","s="+s);
        return s;

    }

    @Override
    public String  getExamIndex() {
        return (examIndex+1)+".";
    }
}
