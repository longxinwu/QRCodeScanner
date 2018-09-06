package com.wb.unionpay.qrcodescanner.Beans;

import java.io.Serializable;

public class UqrcsMessage implements Serializable{
    private String msgType;
    private UqrcsMessageTest uqrcsMessageTest;
    private String responseCode;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public UqrcsMessageTest getUqrcsMessageTest() {
        return uqrcsMessageTest;
    }

    public void setUqrcsMessageTest(UqrcsMessageTest uqrcsMessageTest) {
        this.uqrcsMessageTest = uqrcsMessageTest;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
