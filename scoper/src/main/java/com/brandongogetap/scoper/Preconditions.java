package com.brandongogetap.scoper;

final class Preconditions {

    private Preconditions() {

    }

    static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
