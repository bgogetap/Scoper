package com.brandongogetap.scoper;

import android.support.annotation.NonNull;

public interface Scoped<T> {

    @NonNull String getScopeName();
}
