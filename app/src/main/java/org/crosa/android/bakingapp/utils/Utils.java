package org.crosa.android.bakingapp.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    // Credits to https://bezkoder.com/java-android-read-json-file-assets-gson/
    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.d(TAG, "Unable to read file", e);
            return null;
        }
        return jsonString;
    }
}
