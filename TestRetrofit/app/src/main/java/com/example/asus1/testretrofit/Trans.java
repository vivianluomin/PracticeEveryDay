package com.example.asus1.testretrofit;

import java.util.List;

/**
 * Created by asus1 on 2017/8/16.
 */

public class Trans {

    private String type;
    private int errorCode;
    private int elapaedTime;
    private List<List<TranslateBean>> translateResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getElapaedTime() {
        return elapaedTime;
    }

    public void setElapaedTime(int elapaedTime) {
        this.elapaedTime = elapaedTime;
    }

    public List<List<TranslateBean>> getTranslateResult() {
        return translateResult;
    }

    public void setTranslateResult(List<List<TranslateBean>> translateResult) {
        this.translateResult = translateResult;
    }

    public  static  class  TranslateBean{
        public String src;
        public String tgt;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getTgt() {
            return tgt;
        }

        public void setTgt(String tgt) {
            this.tgt = tgt;
        }
    }
}
