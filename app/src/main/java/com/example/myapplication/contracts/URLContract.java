package com.example.myapplication.contracts;

import android.provider.BaseColumns;

public class URLContract {

    public URLContract() {}

    public static final class URLEntry implements BaseColumns {
        public static final String BASE_UEL = "http://192.168.43.57/store/";
        public static final String BASE_UEL2 = "http://192.168.43.57/";
    }
}
