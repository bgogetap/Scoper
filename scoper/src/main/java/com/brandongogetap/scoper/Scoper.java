package com.brandongogetap.scoper;

import android.content.Context;
import android.support.annotation.NonNull;

@SuppressWarnings("WrongConstant")
public final class Scoper {

    private Scoper() {

    }

    /**
     * Toggle the caching behavior of components.
     * <p>
     * The default behavior returns existing components for the same Scope when new instances of
     * components are passed into {@link Scoper#createComponent(Scoped, Object)}. This allows
     * components to survive configuration changes.
     * <p>
     * If you would like to overwrite existing components for a your scopes, pass 'true' to this
     * method.
     *
     * @param replaceExisting Enable replacement of cached components if new instance is passed in.
     *                        Default value is false.
     */
    public static void replaceExisting(boolean replaceExisting) {
        CacheHandler.INSTANCE.replaceExisting(replaceExisting);
    }

    /**
     * Adds a component to the map associated with the given {@link Scoped} object, or returns the
     * existing cached component for the given {@link Scoped} object (unless disabled by
     * {@link Scoper#replaceExisting(boolean)}.
     *
     * @param scoped    The {@link Scoped} object to which the component should be linked
     * @param component Component of the given scope
     * @param <T>       The component type
     * @return The provided component
     */
    public static <T> T createComponent(Scoped<?> scoped, Object component) {
        return CacheHandler.INSTANCE.createComponent(scoped, component);
    }

    /**
     * Returns a new {@link ScopeBuilder} instance which will allow you to create a child scope on
     * top of an existing scope.
     * <p>
     * This should be followed by a call to {@link ScopeBuilder#createChild(Scoped, ChildBuilder)}
     *
     * @param parentContext Context of the parent scope
     * @param <T>           Type of the parent scope's Component
     * @return new {@link ScopeBuilder} instance
     */
    public static <T> ScopeBuilder<T> withParent(Context parentContext) {
        return ScopeBuilder.start(parentContext);
    }

    /**
     * Returns a new {@link ScopeBuilder} instance which will allow you to create a child scope on
     * top of an existing scope.
     * <p>
     * This should be followed by a call to {@link ScopeBuilder#createChild(Scoped, ChildBuilder)}
     *
     * @param parentScopeTag Scope name (tag) of the parent scope
     * @param <T>            Type of the parent scope's Component
     * @return new {@link ScopeBuilder} instance
     */
    public static <T> ScopeBuilder<T> withParent(String parentScopeTag) {
        return ScopeBuilder.start(parentScopeTag);
    }

    /**
     * Get the component for a given context. This will throw a {@link NullPointerException} if
     * there is no component for the given context, and so is guaranteed to be non-null.
     * <p>
     * This should <strong>not</strong> be used to retrieve a parent component for building a child scope. You should
     * instead use {@link Scoper#withParent(Context)}.
     * <p>
     * Throws {@link IllegalArgumentException} if Context is not linked to {@link ScoperContext}
     *
     * @param context Context associated with the scope
     * @param <T>     The component type
     * @return The cached component for the given context scope
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public static <T> T getComponent(Context context) {
        return CacheHandler.INSTANCE.getComponent(context);
    }

    /**
     * Get the component for a given tag. This will throw a {@link NullPointerException} if there
     * is not component for the given tag, and so is guaranteed to be non-null.
     * <p>
     * This should <strong>not</strong> be used to retrieve a parent component for building a child scope. You should
     * instead use {@link Scoper#withParent(String)}.
     *
     * @param scopeName The scope name tag
     * @param <T>       The component type
     * @return The cached component for the given tag
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public static <T> T getComponentForName(String scopeName) {
        return CacheHandler.INSTANCE.getComponentForTag(scopeName);
    }

    /**
     * Allows you to manually cache a component with a given tag.
     * <p>
     * Generally, if you're following the {@link ScoperContext} pattern, you shouldn't have to use
     * this. However, if you want to manually manage scopes, this method may be useful.
     *
     * @param scopeName Scope name associated with the component
     * @param component Component to cache
     * @return The provided component
     */
    @NonNull
    public static <T> T cacheComponent(String scopeName, @NonNull Object component) {
        return CacheHandler.INSTANCE.cacheComponent(scopeName, component);
    }

    /**
     * Removes the component for the given {@link Scoped} object
     *
     * @param scoped The {@link Scoped} object associated with the scope to be destroyed
     */
    public static void destroyScope(Scoped<?> scoped) {
        CacheHandler.INSTANCE.destroyScope(scoped.getScopeName());
    }

    /**
     * Removes the component for the given Scope tag
     *
     * @param scopeName The scope to be destroyed
     */
    public static void destroyScope(String scopeName) {
        CacheHandler.INSTANCE.destroyScope(scopeName);
    }

    /**
     * Toggle debug console logging.
     *
     * @param enabled Whether or not to log debug messages. Default is off (false).
     */
    public static void loggingEnabled(boolean enabled) {
        CacheHandler.INSTANCE.loggingEnabled(enabled);
    }

    static <T> T createChild(String parentScopeTag, Scoped<?> scoped, T child) {
        return CacheHandler.INSTANCE.createChild(parentScopeTag, scoped, child);
    }

    @SuppressWarnings("unchecked")
    private enum CacheHandler {
        INSTANCE;

        private Logger logger = new Logger();
        private ScoperCache cache = new ScoperCache(logger);

        <T> T createComponent(Scoped<?> scoped, Object component) {
            return (T) cache.initComponent(scoped.getScopeName(), component);
        }

        <T> T createChild(String parentScopeTag, Scoped<?> scoped, Object component) {
            return (T) cache.initComponent(parentScopeTag, scoped.getScopeName(), component);
        }

        <T> T getComponent(Context context) {
            return (T) cache.getComponent(context);
        }

        <T> T getComponentForTag(String tag) {
            return (T) cache.getComponentForTag(tag);
        }

        <T> T cacheComponent(String tag, Object component) {
            return (T) cache.put(tag, component);
        }

        void destroyScope(Context context) {
            cache.destroyScope(context);
        }

        void destroyScope(String tag) {
            cache.destroyScope(tag);
        }

        void replaceExisting(boolean replaceExisting) {
            cache.replaceExisting(replaceExisting);
        }

        void loggingEnabled(boolean enabled) {
            logger.setEnabled(enabled);
        }
    }
}
