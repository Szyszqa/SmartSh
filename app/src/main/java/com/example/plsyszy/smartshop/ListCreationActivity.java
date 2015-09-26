package com.example.plsyszy.smartshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plsyszy.smartshop.db.DBManager;
import com.example.plsyszy.smartshop.db.ShoppingList;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class ListCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);
    }

    public void saveButtonClicked(View view) {
        EditText listNameEditText = (EditText) findViewById(R.id.newListName);
        String name = listNameEditText.getText().toString().toUpperCase();

        if(name.isEmpty()) {
            Toast.makeText(this, "Musisz wpisać nazwę", Toast.LENGTH_LONG).show();
        } else  {
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.setName(name);

            RuntimeExceptionDao<ShoppingList, Long> shoppingListDao = DBManager.getInstance(this).getShoppingListDao();
            shoppingListDao.create(shoppingList);
            finish();
        }
    }

}
