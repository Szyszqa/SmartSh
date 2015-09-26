package com.example.plsyszy.smartshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProductReportActivity extends AppCompatActivity {
    public static final String EXTRA_RECEIPTITEM_ID = "extra.receiptitem.id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_report);
    }
}
