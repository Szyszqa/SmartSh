package com.example.plsyszy.smartshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void shoppingListClicked(View view) {
        Intent intent = new Intent(this, ShoppingListsActivity.class);
        startActivity(intent);
    }

    public void receiptListClicked(View view) {
        Intent intent = new Intent(this, ReceiptsListActivity.class);
        startActivity(intent);
    }

    public void cookListClicked(View view) {
        //Intent intent = new Intent(this, CookListActivity.class);
        //startActivity(intent);
        Toast.makeText(this, "Opcja niedostępna w darmowej wersji", Toast.LENGTH_LONG).show();

    }

    public void reportListClicked(View view) {
        // Intent intent = new Intent(this, ReportsActivity.class);
        // startActivity(intent);
        Toast.makeText(this, "Opcja niedostępna w darmowej wersji", Toast.LENGTH_LONG).show();
    }
}
