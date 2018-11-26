package ca.lambton.allan.xlambton.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Set;

/**
 * Shared Preference Utils
 *
 * @author Allan Im
 */
public class SharedPreferencesUtils {

    private static SharedPreferences sharedPreferences;

    private SharedPreferencesUtils(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.d("SharedPreferenceUtils",
                    "Name : " + PreferenceManager.getDefaultSharedPreferencesName(context));
        }
    }

    /**
     * Gets instance
     *
     * @param context Context
     * @return Shared preference utils
     */
    public static SharedPreferencesUtils instance(Context context) {
        return new SharedPreferencesUtils(context);
    }

    /**
     * Gets Editor of shared preference
     *
     * @return Editor
     */
    public SharedPreferences.Editor editor() {
        return sharedPreferences.edit();
    }

    /**
     * To contain key
     *
     * @param key KEY String
     * @return {true:false}
     */
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * Gets value by String
     *
     * @param key KEY String
     * @return value
     */
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    /**
     * Gets value by Integer
     *
     * @param key KEY String
     * @return value
     */
    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * Gets value by Boolean
     *
     * @param key KEY String
     * @return value
     */
    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, Boolean.FALSE);
    }

    /**
     * Gets value by Float
     *
     * @param key KEY Fla
     * @return value
     */
    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, Float.NaN);
    }

    /**
     * Gets value by Long
     *
     * @param key KEY String
     * @return value
     */
    public long getLong(String key) {
        return sharedPreferences.getLong(key, Long.valueOf("0"));
    }

    /**
     * Gets value by StringSet
     *
     * @param key KEY String
     * @return value
     */
    public Set<String> getStringSet(String key) {
        return sharedPreferences.getStringSet(key, null);
    }

}
