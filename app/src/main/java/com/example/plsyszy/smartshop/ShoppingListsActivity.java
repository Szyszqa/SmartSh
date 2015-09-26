package com.example.plsyszy.smartshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.plsyszy.smartshop.db.DBManager;
import com.example.plsyszy.smartshop.db.ShoppingList;

import java.util.List;

public class ShoppingListsActivity extends AppCompatActivity {

    private ListView shoppingListsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        shoppingListsView = (ListView)findViewById(R.id.shoppingLists_view);
        shoppingListsView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)   {
            Intent intent = new Intent(ShoppingListsActivity.this, ShoppingListActivity.class);
            intent.putExtra(ShoppingListActivity.EXTRA_SHOPPING_LIST_ID, id);
            startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        List<ShoppingList> shoppingLists = DBManager.getInstance(this).getShoppingListDao().queryForAll();

        ShoppingListAdapter adapter = new ShoppingListAdapter(this, android.R.layout.simple_list_item_1, shoppingLists);
        shoppingListsView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_lists, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_addList) {
            Intent intent = new Intent(this, ListCreationActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private class ShoppingListAdapter extends ArrayAdapter<ShoppingList> {

        public ShoppingListAdapter(Context context, int resource, List<ShoppingList> objects) {
            super(context, resource, objects);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }
    }


}
