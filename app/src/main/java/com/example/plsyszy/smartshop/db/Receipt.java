package com.example.plsyszy.smartshop.db;

import android.text.Editable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PLSYSZY on 2015-09-23.
 */

@DatabaseTable
public class Receipt {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private Date date=new Date();

    @DatabaseField
    private String name;

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    @DatabaseField
    private String shop;


    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "["+formatDate(date)+"] "+name +"@" + shop;
    }

    private String formatDate(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm", Locale.getDefault());
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }


}
