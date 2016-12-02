package com.brandongogetap.scoper;

public interface ChildBuilder<T> {

    Object build(T parent);
}
