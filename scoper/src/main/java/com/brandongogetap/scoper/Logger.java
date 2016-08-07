package com.brandongogetap.scoper;

import android.util.Log;

final class Logger {

    private boolean enabled = false;

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    void w(String message) {
        if (enabled) {
            Log.w("Scoper", message);
        }
    }

    void d(String message) {
        if (enabled) {
            Log.d("Scoper", message);
        }
    }
}
