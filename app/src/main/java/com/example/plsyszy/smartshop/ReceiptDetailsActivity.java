package com.example.plsyszy.smartshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plsyszy.smartshop.db.DBManager;
import com.example.plsyszy.smartshop.db.Receipt;
import com.example.plsyszy.smartshop.db.ReceiptItem;
import com.example.plsyszy.smartshop.db.ShoppingList;
import com.example.plsyszy.smartshop.db.ShoppingListItem;

import java.util.List;

public class ReceiptDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_RECEIPT_ID = "extra.receipt.id";
    private Receipt receipt;
    private ListView receiptLinesView;
    private ReceiptLinesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_details);

        long receiptId = getIntent().getLongExtra(EXTRA_RECEIPT_ID, -1);
        receiptLinesView = (ListView) findViewById(R.id.receiptLines_view);
        receiptLinesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReceiptDetailsActivity.this, ProductReportActivity.class);
                intent.putExtra(ProductReportActivity.EXTRA_RECEIPTITEM_ID, id);
                startActivity(intent);
            }
        });

        receipt = DBManager.getInstance(this).getReceiptDao().queryForId(receiptId);
        List<ReceiptItem> receipt_lines = DBManager.getInstance(this).getReceiptItemDao().queryForEq("receipt_id", receipt.getId());
        adapter = new ReceiptLinesAdapter(this, android.R.layout.simple_list_item_1, receipt_lines);
        receiptLinesView.setAdapter(adapter);

    }


    private class ReceiptLinesAdapter extends ArrayAdapter<ReceiptItem> {

        public ReceiptLinesAdapter(Context context, int resource, List<ReceiptItem> objects) {
            super(context, resource, objects);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }
    }

}




