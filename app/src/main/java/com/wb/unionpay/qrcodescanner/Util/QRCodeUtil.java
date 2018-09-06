package com.wb.unionpay.qrcodescanner.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class QRCodeUtil {

    public static Bitmap createQRImage(String content, int widthPix, int heightPix, Bitmap logo) throws WriterException{


        if(TextUtils.isEmpty(content)){
            return null;
        }
        /*偏移量*/
        int offsetX = widthPix/2;
        int offsetY = heightPix/2;

        /*生成logo*/
        Bitmap logoBitmap = null;
        if(logo!=null){
            Matrix matrix = new Matrix();
            float scaleFactor = Math.min(widthPix * 1.0f / 5 /logo.getWidth(), heightPix * 1.0f / 5 / logo.getHeight());
            matrix.postScale(scaleFactor, scaleFactor);
            logoBitmap = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(), logo.getHeight(), matrix, true);
        }
        /*如果log不为null,重新计算偏移量*/
        int logoW = 0;
        int logoH = 0;
        if (logoBitmap != null) {
            logoW = logoBitmap.getWidth();
            logoH = logoBitmap.getHeight();
            offsetX = (widthPix - logoW) / 2;
            offsetY = (heightPix - logoH) / 2;
        }

        /*指定位UTF-8*/
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        //容错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置空白边距的宽度
        hints.put(EncodeHintType.MARGIN, 0);
        //生成二维矩阵，编码时指定大小，不要生成了图片以后在进行缩放，这样会导致模糊导致识别失败

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);

        int[] pixels = new int[widthPix * heightPix];
        for(int y = 0; y<heightPix; y++){
            for(int x = 0; x<widthPix; x++){
                if(x >= offsetX && x < offsetX + logoW && y>= offsetY && y < offsetY + logoH){
                    int pixel = logoBitmap.getPixel(x-offsetX,y-offsetY);
                    if(pixel == 0){
                        //bitMatrix.get(x,y)如果返回true,填充黑色，否则，填充白色
                        if(bitMatrix.get(x, y)){
                            pixel = 0xff000000;
                        }else{
                            pixel = 0xffffffff;
                        }
                    }
                    pixels[y * widthPix + x] = pixel;
                }else{
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000; //黑色
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff; //白色
                    }
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);

        return bitmap;
    }
}
