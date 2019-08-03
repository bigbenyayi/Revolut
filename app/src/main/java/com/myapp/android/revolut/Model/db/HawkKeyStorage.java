package com.myapp.android.revolut.Model.db;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class HawkKeyStorage {

    private static final String CURRENCY_KEY = "CURRENCY_KEY";
    private static final String MAIN_CURRENCY_KEY = "MAIN_CURRENCY_KEY";

    static String getCurrencyKey() {
        return CURRENCY_KEY;
    }

    static String getMainCurrecyKey() {
        return MAIN_CURRENCY_KEY;
    }

}