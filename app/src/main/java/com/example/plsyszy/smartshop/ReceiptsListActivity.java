package com.example.plsyszy.smartshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.plsyszy.smartshop.db.DBManager;
import com.example.plsyszy.smartshop.db.Receipt;
import com.example.plsyszy.smartshop.db.ShoppingList;

import java.util.List;

public class ReceiptsListActivity extends AppCompatActivity {


    private ListView receiptsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts_list);

        receiptsListView = (ListView)findViewById(R.id.receiptsList_view);
        receiptsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)   {
                Intent intent = new Intent(ReceiptsListActivity.this, ReceiptDetailsActivity.class);
                intent.putExtra(ReceiptDetailsActivity.EXTRA_RECEIPT_ID, id);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        List<Receipt> receiptsList = DBManager.getInstance(this).getReceiptDao().queryForAll();

        ReceiptsListAdapter adapter = new ReceiptsListAdapter(this, android.R.layout.simple_list_item_1, receiptsList);
        receiptsListView.setAdapter(adapter);
    }

    private class ReceiptsListAdapter extends ArrayAdapter<Receipt> {

        public ReceiptsListAdapter(Context context, int resource, List<Receipt> objects) {
            super(context, resource, objects);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }
    }

}
