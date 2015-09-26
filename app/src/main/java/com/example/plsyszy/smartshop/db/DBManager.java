package com.example.plsyszy.smartshop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;


/**
 * Created by PLSYSZY on 2015-09-23.
 */
public class DBManager extends OrmLiteSqliteOpenHelper{

    private static DBManager instance;

    private static final String DATABASE_NAME = "smartShopDB";
    private static final int DATABASE_VERSION=9;

    //from package object
    private RuntimeExceptionDao<ShoppingList, Long> shoppingListDao;
    private RuntimeExceptionDao<ShoppingListItem, Long> shoppingListItemDao;
    private RuntimeExceptionDao<Receipt, Long> receiptDao;
    private RuntimeExceptionDao<ReceiptItem, Long> receiptItemDao;


    private DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        shoppingListDao=getRuntimeExceptionDao(ShoppingList.class);
        shoppingListItemDao=getRuntimeExceptionDao(ShoppingListItem.class);
        receiptDao=getRuntimeExceptionDao(Receipt.class);
        receiptItemDao=getRuntimeExceptionDao(ReceiptItem.class);
    }


    public static DBManager getInstance(Context context) {
        if (instance == null) {
            instance = new DBManager(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.d("database", "onCreate start");
        recreateTables();
        Log.d("database", "onCreate end");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.d("database", "onUpgrade start");
        recreateTables();
        Log.d("database", "onUpgrade end");
    }

    private void recreateTables() {
        try {
            TableUtils.dropTable(connectionSource, ShoppingList.class, true);
            TableUtils.dropTable(connectionSource, ShoppingListItem.class, true);

            TableUtils.createTableIfNotExists(connectionSource, ShoppingList.class);
            TableUtils.createTableIfNotExists(connectionSource, ShoppingListItem.class);

            TableUtils.dropTable(connectionSource, Receipt.class, true);
            TableUtils.dropTable(connectionSource, ReceiptItem.class, true);

            TableUtils.createTableIfNotExists(connectionSource, Receipt.class);
            TableUtils.createTableIfNotExists(connectionSource, ReceiptItem.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<ShoppingList, Long> getShoppingListDao() {
        return shoppingListDao;
    }

    public RuntimeExceptionDao<ShoppingListItem, Long> getShoppingListItemDao() {
        return shoppingListItemDao;
    }

    public RuntimeExceptionDao<Receipt, Long> getReceiptDao() {
        return receiptDao;
    }

    public RuntimeExceptionDao<ReceiptItem, Long> getReceiptItemDao() {
        return receiptItemDao;
    }

}
