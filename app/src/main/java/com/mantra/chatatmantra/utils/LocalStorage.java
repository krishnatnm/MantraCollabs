package com.mantra.chatatmantra.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TaNMay on 3/2/2016.
 */
public class LocalStorage {

    private static LocalStorage instance = null;
    SharedPreferences sharedPreferences;

    public LocalStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("Reg", 0);
    }

    public static LocalStorage getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalStorage.class) {
                if (instance == null) {
                    instance = new LocalStorage(context);
                }
            }
        }
        return instance;
    }

    public String getToken() {
        if (sharedPreferences.contains("TOKEN")) {
            return sharedPreferences.getString("TOKEN", null);
        } else {
            return null;
        }
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TOKEN", token);
        editor.commit();
    }
}
