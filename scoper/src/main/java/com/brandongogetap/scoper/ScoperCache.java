package com.brandongogetap.scoper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

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
final class ScoperCache {

    private Map<String, Object> componentMap;

    ScoperCache() {
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
        put(getScoperContext(context).getTag(), component);
    }

    void put(String tag, Object component) {
        if (componentMap.containsKey(tag)) {
            Log.w("Scoper", "CacheComponent: Component already exists for given scope: " + tag +
                    ". It will be replaced with the new component.");
        }
        componentMap.put(tag, checkNotNull(component, "component == null"));
    }

    void destroyScope(Context context) {
        destroyScope(getScoperContext(context).getTag());
    }

    void destroyScope(String tag) {
        if (!componentMap.containsKey(tag)) {
            Log.d("Scoper", "DestroyComponent: No component to destroy for given scope: " + tag + ".");
        }
        componentMap.remove(tag);
    }

    private static ScoperContext getScoperContext(Context context) {
        //noinspection WrongConstant
        Object scoperContext = context.getSystemService(ScoperContext.SERVICE_NAME);
        if (scoperContext instanceof ScoperContext) {
            return (ScoperContext) scoperContext;
        } else {
            throw new IllegalArgumentException("Context does not have ScoperContext linked: " +
                    context.getClass().getName());
        }
    }

    @VisibleForTesting
    int getCacheCount() {
        return componentMap.size();
    }
}
