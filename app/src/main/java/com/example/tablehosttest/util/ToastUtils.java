package com.example.tablehosttest.util;

import android.widget.Toast;
import com.example.tablehosttest.application.BaseApplication;

public class ToastUtils {

    private static Toast sToast;

    public static void showToast(String message){
        if(null == sToast){
            sToast = Toast.makeText(BaseApplication.getAppContext(),message,Toast.LENGTH_LONG);
        }else{
            sToast.setText(message);
        }
        sToast.show();
    }
}
