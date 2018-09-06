package com.wb.unionpay.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wb.unionpay.qrcodescanner.Beans.UqrcsMessageTest;


public class ResultActivity extends AppCompatActivity {

    private TextView textScanOver;
    private TextView textAmt;
    private TextView textDate;
    private TextView textTime;
    private Button btnConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String str = intent.getStringExtra("Result");
        UqrcsMessageTest msg = (UqrcsMessageTest) intent.getSerializableExtra("msg");

        textScanOver = findViewById(R.id.text_scan_over);
        textScanOver.setText(str);
        textAmt = findViewById(R.id.text_amount);
        textAmt.setText(msg.getAMT());
        textDate = findViewById(R.id.text_date);
        textTime = findViewById(R.id.text_time);
        String dateTime = msg.getTransDateTime();
        String date = dateTime.substring(0, 2)+":"+dateTime.substring(2, 4);
        String time = dateTime.substring(4,6)+":"+dateTime.substring(6, 8);
        textDate.setText(date);
        textTime.setText(time);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
