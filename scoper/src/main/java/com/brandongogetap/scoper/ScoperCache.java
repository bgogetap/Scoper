package com.brandongogetap.scoper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.brandongogetap.scoper.Preconditions.checkNotNull;

/**
 * This is the object that holds a map of your Scopes to Components. This needs to be set up in
 * your Application subclass.
 * <p>
 * You can either instantiate a new instance in your Application class or Provide it with a
 * Dependency Injection framework.
 * <p>
 * <pre>
 *     {@code
 *
 *     @literal @Inject ScoperCache componentCache;
 *
 *     @Override public Object getSystemService(String name) {
 *         if (name.equals(ScoperCache.SERVICE_NAME)) {
 *           return componentCache;
 *         }
 *         return super.getSystemService(name);
 *      }
 *     }
 * </pre>
 */
public final class ScoperCache {

    public static final String SERVICE_NAME = ScoperCache.class.getName();

    private Map<String, Object> componentMap;

    public ScoperCache() {
        componentMap = new LinkedHashMap<>();
    }

    @SuppressWarnings("WrongConstant") Object initComponent(Context context, Object component) {
        ScoperContext scoperContext = getScoperContext(context);
        if (componentMap.get(scoperContext.getTag()) != null) {
            return componentMap.get(scoperContext.getTag());
        }
        put(scoperContext, component);
        return component;
    }

    @NonNull Object getComponent(Context context) {
        String tag = getScoperContext(context).getTag();
        return getComponentForTag(tag);
    }

    @NonNull Object getComponentForTag(String tag) {
        return checkNotNull(componentMap.get(tag), "No Component for: " + tag);
    }

    @SuppressWarnings("WrongConstant") private void put(Context context, Object component) {
        componentMap.put(
                getScoperContext(context).getTag(), checkNotNull(component, "component == null"));
    }

    void put(String tag, Object component) {
        componentMap.put(tag, component);
    }

    void destroyScope(Context context) {
        componentMap.remove(getScoperContext(context).getTag());
    }

    private static ScoperContext getScoperContext(Context context) {
        //noinspection WrongConstant
        return (ScoperContext) context.getSystemService(ScoperContext.SERVICE_NAME);
    }

    @SuppressWarnings("WrongConstant") static ScoperCache get(Context context) {
        return checkNotNull(
                (ScoperCache) context.getApplicationContext().getSystemService(SERVICE_NAME),
                "ScoperCache == null\nIt is either not instantiated, " +
                        "or is not being returned in Application#getSystemService");
    }

    @VisibleForTesting
    int getCacheCount() {
        return componentMap.size();
    }
}
