package com.wb.unionpay.qrcodescanner;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.WriterException;
import com.wb.unionpay.qrcodescanner.Beans.UqrcsMessage;
import com.wb.unionpay.qrcodescanner.Beans.UqrcsMessageTest;
import com.wb.unionpay.qrcodescanner.Util.HttpUtil;
import com.wb.unionpay.qrcodescanner.Util.JsonUtil;
import com.wb.unionpay.qrcodescanner.Util.QRCodeUtil;
import com.wb.unionpay.qrcodescanner.callback.NetworkListner;



public class ContentActivity extends AppCompatActivity implements NetworkListner {
    //请求的uri地址
    private String uri;

    private TextView textView;
    private ImageView imageView;
    private Bitmap bitmap;
    private static UqrcsMessage uqrcsMessage;
    private static UqrcsMessageTest uqrcsMessageTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

        uri = "http://172.21.22.15:11006/webAPP/webApp";
        sendMessage(uri, ContentActivity.this);
        String getStr = getIntent().getExtras().getString("url");
        textView.setText(getStr);
        QRCodeUtil qrCodeUtil = new QRCodeUtil();
        try {
            //无logo的二维码
            //bitmap = qrCodeUtil.createQRImage(getStr, 400, 400, null);
            //
            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            bitmap = qrCodeUtil.createQRImage(getStr, 400, 400, logo);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private void sendMessage(final String uri, final NetworkListner listner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil httpUtil = new HttpUtil(uri, listner);
                httpUtil.sendMsgHandler();
            }
        }).start();
    }
    private void openSuccessView(Object obj){
        Intent intent = new Intent("com.unionpay.qrcodescanner.ACTION_START");
        intent.putExtra("Result", "SUCCESS");
        intent.putExtra("msg", uqrcsMessageTest);
        startActivity(intent);
    }

    @Override
    public void onError() {

    }

    public Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    openSuccessView(msg.obj);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onResult(String str) {
        Log.i("onResult：{}", str);

        if(str.equals("noTrade")){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendMessage(uri, ContentActivity.this);
                }
            }, 3000);
        }else {
            uqrcsMessageTest = JsonUtil.fromJson(str,UqrcsMessageTest.class);
            Message message = new Message();
            message.what = 1;
            message.obj = uqrcsMessageTest;
            handler.sendMessage(message);
            finish();
        }

    }
}
