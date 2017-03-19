package com.example.ganesh.shoppinglist.utils;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Utils {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private Context mContext = null;

    public Utils(Context con) {
        mContext = con;
    }

    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}
