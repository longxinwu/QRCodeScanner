package com.wb.unionpay.qrcodescanner.Beans;

import java.io.Serializable;

public class UqrcsMessageTest implements Serializable {
    //金额
    private String AMT;
    //币种
    private String CUY;
    //商户类型
    private String MCT;
    //收单机构代码
    private String RMC;
    //发送机构代码
    private String SMC;
    //终端代码
    private String TIC;
    //商户代码
    private String MCC;
    //商户名称
    private String MCN;

    private String transDateTime;

    public String getAMT() {
        return AMT;
    }

    public void setAMT(String AMT) {
        this.AMT = AMT;
    }

    public String getCUY() {
        return CUY;
    }

    public void setCUY(String CUY) {
        this.CUY = CUY;
    }

    public String getMCT() {
        return MCT;
    }

    public void setMCT(String MCT) {
        this.MCT = MCT;
    }

    public String getRMC() {
        return RMC;
    }

    public void setRMC(String RMC) {
        this.RMC = RMC;
    }

    public String getSMC() {
        return SMC;
    }

    public void setSMC(String SMC) {
        this.SMC = SMC;
    }

    public String getTIC() {
        return TIC;
    }

    public void setTIC(String TIC) {
        this.TIC = TIC;
    }

    public String getMCC() {
        return MCC;
    }

    public void setMCC(String MCC) {
        this.MCC = MCC;
    }

    public String getMCN() {
        return MCN;
    }

    public void setMCN(String MCN) {
        this.MCN = MCN;
    }

    public String getTransDateTime() {
        return transDateTime;
    }

    public void setTransDateTime(String transDateTime) {
        this.transDateTime = transDateTime;
    }
}
