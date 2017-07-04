package com.example.administrator.myapplication.activity.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Exam;

import java.util.List;

/**
 * Created by weifangfeng on 2017/7/4.
 */

public class QuestionAdapter  extends BaseAdapter{

    Context mContext;
    List<Exam> examList;

    public QuestionAdapter(Context mContext) {
        this.mContext = mContext;
        List<Exam> examList= ExamApplication.getInstance().getMExamList();

    }

    @Override
    public int getCount() {

        return examList==null?0:examList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view=View .inflate(mContext, R.layout.item_question,null);
      TextView tvNum=(TextView) view.findViewById(R.id.tv_Num);
        ImageView ivQuestion=(ImageView)view.findViewById(R.id.iv_question);
       tvNum.setText("第"+(position+1)+"题");

        return view ;
    }
}
