package com.example.plsyszy.smartshop.db;

/**
 * Created by PLSYSZY on 2015-09-23.
 */
public enum Unit {

    PIECE("szt."), GRAMS("g"), KILOGRAMS("kg"), LITERS("l"), MILLILITERS("ml");

    private final String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
