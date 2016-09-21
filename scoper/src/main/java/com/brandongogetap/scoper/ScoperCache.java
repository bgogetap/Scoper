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
final class ScoperCache {

    private final Logger logger;

    private Map<String, Object> componentMap;
    private boolean replaceExisting;

    ScoperCache(Logger logger) {
        this.logger = logger;
        componentMap = new LinkedHashMap<>();
        replaceExisting = false;
    }

    Object initComponent(String scopeName, Object component) {
        if (componentMap.get(scopeName) != null && !replaceExisting) {
            logger.d("Existing component for scope: '" + scopeName + "' present. " +
                    "Returning existing instance");
            return componentMap.get(scopeName);
        }
        put(scopeName, component);
        logger.d("No existing component for scope: '" + scopeName + "'. " +
                "Returning provided instance.");
        return component;
    }

    @NonNull Object getComponent(Context context) {
        String tag = getScoperContext(context).getTag();
        return getComponentForTag(tag);
    }

    @NonNull Object getComponentForTag(String tag) {
        return checkNotNull(componentMap.get(tag), "No Component for: " + tag);
    }

    @NonNull Object put(String tag, @NonNull Object component) {
        if (componentMap.containsKey(tag) && !replaceExisting) {
            logger.w("CacheComponent: Component already exists for given scope: " + tag +
                    ". It will be replaced with the new component.");
        }
        componentMap.put(tag, checkNotNull(component, "component == null"));
        return component;
    }

    void destroyScope(Context context) {
        destroyScope(getScoperContext(context).getTag());
    }

    void destroyScope(String tag) {
        if (!componentMap.containsKey(tag)) {
            logger.w("DestroyComponent: No component to destroy for given scope: " + tag + ".");
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

    void replaceExisting(boolean replaceExisting) {
        this.replaceExisting = replaceExisting;
    }

    @VisibleForTesting
    int getCacheCount() {
        return componentMap.size();
    }
}
