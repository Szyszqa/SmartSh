package com.example.plsyszy.smartshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plsyszy.smartshop.db.DBManager;
import com.example.plsyszy.smartshop.db.ShoppingList;
import com.example.plsyszy.smartshop.db.ShoppingListItem;
import com.example.plsyszy.smartshop.db.Unit;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    public static final String EXTRA_SHOPPING_LIST_ID = "extra.shopping.list.id";
    private ShoppingList shoppingList;

    private EditText productNameEditText;
    private EditText productQtyEditText;
    private Spinner unitSpinner;
    private ShoppingListAdapter adapter;
    private ListView productListView;
    private boolean hideSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        long shoppingListId = getIntent().getLongExtra(EXTRA_SHOPPING_LIST_ID, -1);
        shoppingList = DBManager.getInstance(this).getShoppingListDao().queryForId(shoppingListId);

        TextView listName = (TextView) findViewById(R.id.list_name);
        listName.setText(shoppingList.getName());
        productNameEditText = (EditText) findViewById(R.id.product_name);
        productQtyEditText = (EditText) findViewById(R.id.qty);
        productListView = (ListView) findViewById(R.id.product_list);

        ArrayAdapter unitArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Unit.values());
        unitSpinner = (Spinner) findViewById(R.id.unit_spinner);
        unitSpinner.setAdapter(unitArrayAdapter);
        resetAdapter();

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingListItem item = adapter.getItem(position);
                adapter.getItem(position).setIsChecked(!item.isChecked());
                DBManager.getInstance(getApplicationContext()).getShoppingListItemDao().update(item);
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void resetAdapter() {
        List<ShoppingListItem> preItems = DBManager.getInstance(this).getShoppingListItemDao().queryForEq("shoppingListId", shoppingList.getId());

        List<ShoppingListItem> filteredItems = new ArrayList<>();

        if (hideSelected) {
            for (ShoppingListItem item : preItems) {
                if (!item.isChecked()) {
                    filteredItems.add(item);
                }
            }
        } else {
            filteredItems = preItems;
        }

        adapter = new ShoppingListAdapter(this, android.R.layout.simple_list_item_multiple_choice, filteredItems);
        productListView.setAdapter(adapter);
    }

    public void productAddClicked(View view) {
        String name = productNameEditText.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Nazwa nie może być pusta", Toast.LENGTH_LONG).show();
        }
        else {
            ShoppingListItem item = new ShoppingListItem();
            item.setName(name);

            float qty = Float.parseFloat(productQtyEditText.getText().toString());
            item.setQty(qty);

            item.setUnit((Unit) unitSpinner.getSelectedItem());
            item.setShoppingListId(shoppingList.getId());

            DBManager.getInstance(this).getShoppingListItemDao().create(item);

            productNameEditText.setText("");

            resetAdapter();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_share) {

            StringBuilder builder = new StringBuilder();
            builder.append("Lista: " + shoppingList);

            List<ShoppingListItem> items = DBManager.getInstance(this).getShoppingListItemDao().queryForEq("shoppingListId", shoppingList.getId());

            for (ShoppingListItem shoppingListItem : items) {
                builder.append("\n" + shoppingListItem.toString());
            }

            Uri uri = Uri.parse("smsto:");
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", builder.toString());
            startActivity(intent);

            return true;
        }

        if (item.getItemId() == R.id.menu_delete) {
            DBManager.getInstance(this).getShoppingListDao().delete(shoppingList);
            finish();
            return true;
        }

        if(item.getItemId() == R.id.menu_receipt) {
         //   Toast.makeText(this, "Opcja niedostępna w darmowej wersji", Toast.LENGTH_LONG).show();
           Intent intent = new Intent(this, ReceiptCreationActivity.class);
            intent.putExtra(ReceiptCreationActivity.EXTRA_SHOPPING_LIST_ID, shoppingList.getId());
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    public void toggleButtonClicked(View view) {

        hideSelected = !hideSelected;

        Button button = (Button) view;
        if (hideSelected) {
            button.setText("Pokaż schowane");
        } else {
            button.setText("Ukryj kupione");
        }
        resetAdapter();
    }



    private class ShoppingListAdapter extends ArrayAdapter<ShoppingListItem> {

        public ShoppingListAdapter(Context context, int resource, List<ShoppingListItem> objects) {
            super(context, resource, objects);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            CheckedTextView checkedTextView = (CheckedTextView) view;
            checkedTextView.setChecked(getItem(position).isChecked());

            return view;
        }


    }
}
