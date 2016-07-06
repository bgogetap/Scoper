package com.brandongogetap.scoper;

import android.content.Context;
import android.support.annotation.NonNull;

@SuppressWarnings("WrongConstant") public final class Scoper {

    /**
     * Adds a component to the map, with the given scope tag, or returns the existing cached
     * component for the given scope.
     *
     * @param context   Context associated with the scope
     * @param component Component for given scope
     * @param <T>       The component type
     * @return The passed in component, or the cached component for given scope, if one exists
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public static <T> T createComponent(Context context, Object component) {
        return (T) ScoperCache.get(context).initComponent(context, component);
    }

    /**
     * Get the component for a given context. This will throw a {@link NullPointerException} if
     * there is no component for the given context, and so is guaranteed to be non-null.
     * <p>
     * This is usually used to retrieve another scope's component to use in building a subcomponent
     * for the current scope.
     *
     * @param context Context associated with the scope
     * @param <T>     The component type
     * @return The cached component for the given context scope
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public static <T> T getComponent(Context context) {
        return (T) ScoperCache.get(context).getComponent(context);
    }

    /**
     * Get the component for a given tag. This will throw a {@link NullPointerException} if there
     * is not component for the given tag, and so is guaranteed to be non-null.
     * <p>
     * This is convenient to use when building nested scopes and you do not have access to the
     * desired component's scoped context, or if you are manually managing scope caching yourself.
     *
     * @param tag     The scope tag
     * @param context Context
     * @param <T>     The component type
     * @return The cached component for the given tag
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public static <T> T getComponentForTag(String tag, Context context) {
        return (T) ScoperCache.get(context).getComponentForTag(tag);
    }

    /**
     * Allows you to manually cache a component with a given tag.
     * <p>
     * Generally, if you're following the {@link ScoperContext} pattern, you shouldn't have to use
     * this. However, if you want to manually manage scopes, this method may be useful.
     *
     * @param context   Any context (used to retrieve {@link ScoperCache} instance)
     * @param tag       Tag associated with the component
     * @param component Component to cache
     */
    public static void cacheComponent(Context context, String tag, Object component) {
        ScoperCache.get(context).put(tag, component);
    }

    /**
     * Removes the component for the given Context's scope
     *
     * @param context The context associated with the scope that should be destroyed
     */
    public static void destroyScope(Context context) {
        ScoperCache.get(context).destroyScope(context);
    }
}
