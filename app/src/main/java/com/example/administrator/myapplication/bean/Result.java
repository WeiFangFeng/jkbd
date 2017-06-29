package com.example.administrator.myapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class Result {

    /**
     * error_code : 0
     * reason : ok
     * result : [{"id":41,"question":"这个标志是何含义？","answer":"1","item1":"向右转弯","item2":"单行路","item3":"只准直行","item4":"直行车道","explains":"向右转弯：表示只准一切车辆向右转弯。此标志设在车辆必须向右转弯的路口以前适当位置。","url":"http://images.juheapi.com/jztk/c1c2subject1/41.jpg"}]
     */

    private int error_code;
    private String reason;
    private List<Exam> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Exam> getResult() {
        return result;
    }

    public void setResult(List<Exam> result) {
        this.result = result;
    }

}
