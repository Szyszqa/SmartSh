package com.example.plsyszy.smartshop.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by PLSYSZY on 2015-09-23.
 */

@DatabaseTable
public class ShoppingList {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String name;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
 //   public String toString() {return id + ": " + name;}
    public String toString() {return name;}

}
