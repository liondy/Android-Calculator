package com.example.calculatingwombat.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class CommaSettings {
    private final static int[] map = { 2, 3, 5 };

    protected SharedPreferences sharedPreferences;
    protected final static String NAME = "AKU_GANTENG_SEKALI";
    protected final static String KEY = "COMMA_PREFERENCES";

    public CommaSettings(Context context) {
        this.sharedPreferences = context.getSharedPreferences(NAME, 0);
    }

    public void saveSettings(int idx) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(KEY, idx);
        editor.commit();
    }

    public int loadSettings() {
        if (!this.sharedPreferences.contains(KEY)) {
            this.saveSettings(0);
        }

        return map[sharedPreferences.getInt(KEY, 0)];
    }
}
