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

      startActivity(new Intent(MainActivity.this,ExamActivity.class));

    }

    public void exit(View view) {
        finish();
    }
}
