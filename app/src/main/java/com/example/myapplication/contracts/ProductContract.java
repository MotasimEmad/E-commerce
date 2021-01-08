package com.example.myapplication.contracts;

import android.provider.BaseColumns;

public class ProductContract {

    public ProductContract() {}

    public static final class ProductEntry implements BaseColumns {
        public static final String TableName = "product_table";
        public static final String CoulmnNum = "product_id";
        public static final String CoulmnUserID = "user_id";
        public static final String CoulmnName = "name";
        public static final String CoulmnPrice = "price";
        public static final String CoulmnAmount = "amount";
        public static final String CoulmnTime = "timestamp";
    }
}
