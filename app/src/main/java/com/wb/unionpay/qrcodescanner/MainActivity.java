package com.wb.unionpay.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



/**
 * 主界面
 * btnScanner调扫码界面
 * btnConvert进入二维码展示界面
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //请求代码
    private int REQUEST_CODE_SCAN = 111;

    private EditText transactionAmount;
    private EditText transactionCurrency;
    private EditText mechantType;
    private EditText receiveMechanismCode;
    private EditText sentMechanismCode;
    private EditText terminalCode;
    private EditText merchantCode;
    private EditText merChantName;

    private Button btnScanner;
    private Button btnConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){

        transactionAmount = (EditText) findViewById(R.id.et_url1);
        transactionCurrency = (EditText) findViewById(R.id.et_url2);
        mechantType = (EditText) findViewById(R.id.et_MCT);
        receiveMechanismCode = (EditText) findViewById(R.id.et_url3);
        sentMechanismCode = (EditText) findViewById(R.id.et_url4);
        terminalCode = (EditText) findViewById(R.id.et_url5);
        merchantCode = (EditText) findViewById(R.id.et_url6);
        merChantName = (EditText) findViewById(R.id.et_url7);

        btnConvert = (Button) findViewById(R.id.btn_convert);

        btnConvert.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_convert:
                String finalUrl = "https://qr.95516.com?" +
                        "AMT="+transactionAmount.getText().toString()
                        +"&CUY="+transactionCurrency.getText().toString()
                        +"&MCT="+mechantType.getText().toString()
                        +"&RMC="+receiveMechanismCode.getText().toString()
                        +"&SMC="+sentMechanismCode.getText().toString()
                        +"&TIC="+terminalCode.getText().toString()
                        +"&MCC="+merchantCode.getText().toString()
                        +"&MCN="+merChantName.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("url", finalUrl);

                Intent con_intent = new Intent(MainActivity.this, ContentActivity.class);
                con_intent.putExtras(bundle);
                startActivity(con_intent);
                break;
        }
    }
}
