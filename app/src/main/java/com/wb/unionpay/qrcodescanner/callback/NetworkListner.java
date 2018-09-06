package com.wb.unionpay.qrcodescanner.callback;

public interface NetworkListner {

    void onResult(String string);

    void onError();
}
