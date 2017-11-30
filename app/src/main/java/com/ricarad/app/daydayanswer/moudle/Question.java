package com.ricarad.app.daydayanswer.moudle;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by dongdong on 2017/11/28.
 */

public class Question extends BmobObject implements Serializable {
    private String result;
    private String content;

    private String analysis;
    private String itemA;
    private String itemB;
    private String itemC;
    private String itemD;
    public Question(){
        setTableName("Question");
    }
    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getItemA() {
        return itemA;
    }

    public void setItemA(String itemA) {
        this.itemA = itemA;
    }

    public String getItemB() {
        return itemB;
    }

    public void setItemB(String itemB) {
        this.itemB = itemB;
    }

    public String getItemC() {
        return itemC;
    }

    public void setItemC(String itemC) {
        this.itemC = itemC;
    }

    public String getItemD() {
        return itemD;
    }

    public void setItemD(String itemD) {
        this.itemD = itemD;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
