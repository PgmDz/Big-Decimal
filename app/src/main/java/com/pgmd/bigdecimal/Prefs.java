package com.pgmd.bigdecimal;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;

public class Prefs {

    private static final String LENGTH = "_length";
    private static final String DEFAULT_STRING_VALUE = "";
    private static final int DEFAULT_INT_VALUE = -1;
    private static final double DEFAULT_DOUBLE_VALUE = -1d;
    private static final float DEFAULT_FLOAT_VALUE = -1f;
    private static final long DEFAULT_LONG_VALUE = -1L;
    private static final boolean DEFAULT_BOOLEAN_VALUE = false;
    private static Prefs PrefsHomeInstance;
    private SharedPreferences _sharedPreferences;

    private Prefs(Context context) {
        _sharedPreferences = context.getApplicationContext().getSharedPreferences(
			context.getPackageName() + "_preferences",
			Context.MODE_PRIVATE
        );
    }

    private Prefs(Context context, String preferencesName) {
        _sharedPreferences = context.getApplicationContext().getSharedPreferences(
			preferencesName,
			Context.MODE_PRIVATE
        );
    }

    public static Prefs with(Context context) {
        if (PrefsHomeInstance == null) {
            PrefsHomeInstance = new Prefs(context);
        }
        return PrefsHomeInstance;
    }

    public static Prefs with(Context context, boolean forceInstantiation) {
        if (forceInstantiation) {
            PrefsHomeInstance = new Prefs(context);
        }
        return PrefsHomeInstance;
    }

    public static Prefs with(Context context, String preferencesName) {
        if (PrefsHomeInstance == null) {
            PrefsHomeInstance = new Prefs(context, preferencesName);
        }
        return PrefsHomeInstance;
    }

    public static Prefs with(Context context, String preferencesName,
                             boolean forceInstantiation) {
        if (forceInstantiation) {
            PrefsHomeInstance = new Prefs(context, preferencesName);
        }
        return PrefsHomeInstance;
    }

    // String related methods

    public String read(String what) {
        return _sharedPreferences.getString(what, DEFAULT_STRING_VALUE);
    }

    public String read(String what, String defaultString) {
        return _sharedPreferences.getString(what, defaultString);
    }

    public void write(String where, String what) {
        _sharedPreferences.edit().putString(where, what).apply();
    }

    // int related methods

    public int readInt(String what) {
        return _sharedPreferences.getInt(what, DEFAULT_INT_VALUE);
    }

    public int readInt(String what, int defaultInt) {
        return _sharedPreferences.getInt(what, defaultInt);
    }

    public void writeInt(String where, int what) {
        _sharedPreferences.edit().putInt(where, what).apply();
    }

    // double related methods

    public double readDouble(String what) {
        if (!contains(what))
            return DEFAULT_DOUBLE_VALUE;
        return Double.longBitsToDouble(readLong(what));
    }

    public double readDouble(String what, double defaultDouble) {
        if (!contains(what))
            return defaultDouble;
        return Double.longBitsToDouble(readLong(what));
    }

    public void writeDouble(String where, double what) {
        writeLong(where, Double.doubleToRawLongBits(what));
    }

    // float related methods

    public float readFloat(String what) {
        return _sharedPreferences.getFloat(what, DEFAULT_FLOAT_VALUE);
    }

    public float readFloat(String what, float defaultFloat) {
        return _sharedPreferences.getFloat(what, defaultFloat);
    }

    public void writeFloat(String where, float what) {
        _sharedPreferences.edit().putFloat(where, what).apply();
    }

    // long related methods

    public long readLong(String what) {
        return _sharedPreferences.getLong(what, DEFAULT_LONG_VALUE);
    }

    public long readLong(String what, long defaultLong) {
        return _sharedPreferences.getLong(what, defaultLong);
    }

    public void writeLong(String where, long what) {
        _sharedPreferences.edit().putLong(where, what).apply();
    }

    // boolean related methods

    public boolean readBoolean(String what) {
        return readBoolean(what, DEFAULT_BOOLEAN_VALUE);
    }

    public boolean readBoolean(String what, boolean defaultBoolean) {
        return _sharedPreferences.getBoolean(what, defaultBoolean);
    }

    public void writeBoolean(String where, boolean what) {
        _sharedPreferences.edit().putBoolean(where, what).apply();
    }

    // String set methods

    public void putStringSet(final String key, final Set<String> value) {
        _sharedPreferences.edit().putStringSet(key, value).apply();
    }

    public Set<String> getStringSet(final String key, final Set<String> defValue) {
        return _sharedPreferences.getStringSet(key, defValue);
    }

    // end related methods

    public void remove(final String key) {
        if (contains(key + LENGTH)) {
            // Workaround for pre-HC's lack of StringSets
            int stringSetLength = readInt(key + LENGTH);
            if (stringSetLength >= 0) {
                _sharedPreferences.edit().remove(key + LENGTH).apply();
                for (int i = 0; i < stringSetLength; i++) {
                    _sharedPreferences.edit().remove(key + "[" + i + "]").apply();
                }
            }
        }
        _sharedPreferences.edit().remove(key).apply();
    }

    public boolean contains(final String key) {
        return _sharedPreferences.contains(key);
    }

    public void clear() {
        _sharedPreferences.edit().clear().apply();
    }
}

