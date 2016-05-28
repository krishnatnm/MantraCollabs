package com.mantra.chatatmantra.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mantra.chatatmantra.Chat.User;

/**
 * Created by TaNMay on 3/2/2016.
 */
public class LocalStorage {

    private String TAG = LocalStorage.class.getSimpleName();
    private static LocalStorage instance = null;
    SharedPreferences sharedPreferences;
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_NOTIFICATIONS = "notifications";

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

    public String getGoogleToken() {
        if (sharedPreferences.contains("G_TOKEN")) {
            return sharedPreferences.getString("G_TOKEN", null);
        } else {
            return null;
        }
    }

    public void setGoogleToken(String googleToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("G_TOKEN", googleToken);
        editor.commit();
    }

    public void storeUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.commit();

        Log.e(TAG, "User is stored in shared preferences. " + user.getName() + ", " + user.getEmail());
    }

    public User getUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getString(KEY_USER_ID, null) != null) {
            String id, name, email;
            id = sharedPreferences.getString(KEY_USER_ID, null);
            name = sharedPreferences.getString(KEY_USER_NAME, null);
            email = sharedPreferences.getString(KEY_USER_EMAIL, null);

            User user = new User(id, name, email);
            return user;
        }
        return null;
    }

    public void addNotification(String notification) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // get old notifications
        String oldNotifications = getNotifications();

        if (oldNotifications != null) {
            oldNotifications += "|" + notification;
        } else {
            oldNotifications = notification;
        }

        editor.putString(KEY_NOTIFICATIONS, oldNotifications);
        editor.commit();
    }

    public String getNotifications() {
        return sharedPreferences.getString(KEY_NOTIFICATIONS, null);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
