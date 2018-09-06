package com.wb.unionpay.qrcodescanner.Util;

import android.content.Entity;
import android.util.Log;

import com.wb.unionpay.qrcodescanner.Beans.UqrcsMessage;
import com.wb.unionpay.qrcodescanner.Beans.UqrcsMessageTest;
import com.wb.unionpay.qrcodescanner.callback.NetworkListner;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private  String uri;
    private NetworkListner networkListner;
    private final String msgType = "UQRCSAPP";

    public HttpUtil(String uri, NetworkListner networkListner) {
        this.uri = uri;
        this.networkListner = networkListner;
    }

    public void sendMsgHandler(){
        UqrcsMessage uqrcsMsgtoback = new UqrcsMessage();
        UqrcsMessageTest uqrcsMsgTesttoback = new UqrcsMessageTest();
        uqrcsMsgtoback.setMsgType(msgType);
        String json = JsonUtil.toJson(uqrcsMsgtoback);
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request request = new Request.Builder().url(uri).post(requestBody).build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            //将结果返回给主线程进行处理
            networkListner.onResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
