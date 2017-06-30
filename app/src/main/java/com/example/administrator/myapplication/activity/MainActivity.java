package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;


import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.ExamInfo;
import com.example.administrator.myapplication.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
    }




    public void test(View view) {
        OkHttpUtils<EditorInfo>utils= new OkHttpUtils<>(getApplication());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";

        /*utils.url(uri).targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<EditorInfo>() {
                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result="+result);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });*/
      startActivity(new Intent(MainActivity.this,ExamActivity.class));

    }

    public void exit(View view) {
        finish();
    }
}
