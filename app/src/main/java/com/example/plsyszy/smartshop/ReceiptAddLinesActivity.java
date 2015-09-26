package com.example.plsyszy.smartshop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.plsyszy.smartshop.db.DBManager;
import com.example.plsyszy.smartshop.db.Receipt;
import com.example.plsyszy.smartshop.db.ReceiptItem;

import java.util.List;

public class ReceiptAddLinesActivity extends AppCompatActivity {

    public static final String EXTRA_RECEIPT_ID = "extra.shopping.list.id";

    private Receipt receipt;
    private ListView list;
    private ReceiptLinesAdapter receiptAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_add_lines);

        list = (ListView) findViewById(R.id.receiptAddLinesView);

        long receipt_id = getIntent().getLongExtra(EXTRA_RECEIPT_ID, -1);
        receipt = DBManager.getInstance(this).getReceiptDao().queryForId(receipt_id);

        List<ReceiptItem> r_items = DBManager.getInstance(this).getReceiptItemDao().queryForEq("receipt_id", receipt.getId());
        receiptAdapter = new ReceiptLinesAdapter(this, android.R.layout.simple_list_item_1, r_items);
        list.setAdapter(receiptAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReceiptAddLinesActivity.this);

                final EditText editText = new EditText(ReceiptAddLinesActivity.this);
                editText.setHint("Wprowadź cenę");
                float price = receiptAdapter.getItem(position).getPrice();
                if (price > 0) {
                    editText.setText(String.format("%.2f", price));
                }

                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                builder.setView(editText);

                builder.setTitle("Cena");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReceiptItem item = receiptAdapter.getItem(position);
                        item.setPrice(Float.parseFloat(editText.getText().toString()));

                        DBManager.getInstance(ReceiptAddLinesActivity.this).getReceiptItemDao().update(item);
                    }
                });
                builder.show();
            }
        });
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