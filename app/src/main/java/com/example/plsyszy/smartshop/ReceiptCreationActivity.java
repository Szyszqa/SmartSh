package com.example.plsyszy.smartshop;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.plsyszy.smartshop.db.DBManager;
import com.example.plsyszy.smartshop.db.Receipt;
import com.example.plsyszy.smartshop.db.ReceiptItem;
import com.example.plsyszy.smartshop.db.ShoppingList;
import com.example.plsyszy.smartshop.db.ShoppingListItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class ReceiptCreationActivity extends AppCompatActivity {

    public static final String EXTRA_SHOPPING_LIST_ID = "extra.shopping.list.id";
    private ShoppingList shoppingList;
    private Receipt receipt;
    private TextView dateTextView;
    private Date currentDate;
    private long shoppingListId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_creation);
        dateTextView = (TextView) findViewById(R.id.receipt_date);
        shoppingListId = getIntent().getLongExtra(EXTRA_SHOPPING_LIST_ID, -1);
        shoppingList = DBManager.getInstance(this).getShoppingListDao().queryForId(shoppingListId);

        currentDate=new Date();
        receipt=new Receipt();
        receipt.setDate(currentDate);
        receipt.setName(shoppingList.getName());
        currentDate=receipt.getDate();
        resetDate();

    }

    public void changeDateClicked(View view) {
        final Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(receipt.getDate());

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                receipt.setDate(calendar.getTime());
                resetDate();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

    public void changeHourClicked(View view) {
        final Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(receipt.getDate());

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                receipt.setDate(calendar.getTime());
                resetDate();
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        dialog.show();


    }

    public void saveClicked(View view){
      EditText shop =  (EditText)findViewById(R.id.shop);
      receipt.setShop(shop.getText().toString());
      DBManager.getInstance(this).getReceiptDao().create(receipt);
      long receiptId= DBManager.getInstance(this).getReceiptDao().extractId(receipt);

      List<ShoppingListItem> preItems = DBManager.getInstance(this).getShoppingListItemDao().queryForEq("shoppingListId", shoppingListId);
        ReceiptItem r_item = new ReceiptItem();
         for (ShoppingListItem item : preItems) {
            if (item.isChecked()) {
                r_item.setReceipt_id(receiptId);
                r_item.setProduct(item.getName());
                r_item.setQty(item.getQty());
                r_item.setUnit(item.getUnit());
                r_item.setPrice(0);
                DBManager.getInstance(this).getReceiptItemDao().create(r_item);
            }
        }

      Intent intent = new Intent(this, ReceiptAddLinesActivity.class);
      intent.putExtra(ReceiptAddLinesActivity.EXTRA_RECEIPT_ID, receiptId);
      startActivity(intent);
    }


    private void resetDate() {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm", Locale.getDefault());
        String formattedDate = simpleDateFormat.format(receipt.getDate());
        dateTextView.setText(formattedDate);
    }
}

