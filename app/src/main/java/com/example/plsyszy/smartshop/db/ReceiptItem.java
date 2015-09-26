package com.example.plsyszy.smartshop.db;
import android.content.Intent;
import android.view.MenuItem;

import com.example.plsyszy.smartshop.ListCreationActivity;
import com.example.plsyszy.smartshop.R;
import com.example.plsyszy.smartshop.ReceiptDetailsActivity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by PLSYSZY on 2015-09-23.
 */
@DatabaseTable
public class ReceiptItem {

    @DatabaseField(generatedId = true)
    private long id;

      @DatabaseField
    private long receipt_id;

    @DatabaseField
    private String product;

    @DatabaseField
    private double qty;

    @DatabaseField
    private Unit unit;

    @DatabaseField
    private float price;

    //z jakiej listy zostal wygenerowany paragon
    @DatabaseField
    private long shoppingListId;


    public long getId() {
        return id;
    }


    public long getReceipt_id() {
        return receipt_id;
    }

    public String getProduct() {
        return product;
    }

    public void setReceipt_id(long receipt_id) {
        this.receipt_id = receipt_id;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public long getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(long shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    @Override
    public String toString() {
        return product + " (" + qty + " " + unit+ ") - " + price + " zł";
    }
    public String toStringexP() {
        return product + ", " + qty + " " + unit;
    }




}
