package com.szxb.task;

/**
 * 作者: Tangren on 2017/7/31
 * 包名：com.szxb.task
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class TaskEntity {

    String result;

    public TaskEntity(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "result='" + result + '\'' +
                '}';
    }
}
