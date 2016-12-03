package com.brandongogetap.scoper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.brandongogetap.scoper.Preconditions.checkNotNull;
import static com.brandongogetap.scoper.ScoperContext.getScoperContext;

final class ScoperCache {

    private final Logger logger;

    private Map<String, Object> componentMap;
    private Map<String, Set<String>> parentChildMap;
    private boolean replaceExisting;

    ScoperCache(Logger logger) {
        this.logger = logger;
        componentMap = new LinkedHashMap<>();
        parentChildMap = new LinkedHashMap<>();
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

    Object initComponent(String parentTag, String childTag, Object component) {
        if (!parentChildMap.containsKey(parentTag)) {
            parentChildMap.put(parentTag, new HashSet<String>());
        }
        if (!parentTag.equals(childTag)) {
            if (!parentChildMap.get(parentTag).contains(childTag)) {
                logger.d("Adding child scope: '" + childTag + "' to parent scope: " + parentTag);
            }
            synchronized (this) {
                parentChildMap.get(parentTag).add(childTag);
            }
        }
        return initComponent(childTag, component);
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
        logger.d("Destroying scope: " + tag);
        componentMap.remove(tag);
        removeChildScopeFromParentMap(tag);

        Set<String> childScopes = parentChildMap.get(tag);
        if (childScopes != null && childScopes.size() > 0) {
            Set<String> iterableSet = new HashSet<>(childScopes);
            logger.d("------ Destroying child scopes of: " + tag + " ------");
            for (String childScope : iterableSet) {
                destroyScope(childScope);
            }
            parentChildMap.remove(tag);
            logger.d("------ Finished destroying child scopes of: " + tag + " ------");
        }
    }

    private void removeChildScopeFromParentMap(String tag) {
        for (String parent : parentChildMap.keySet()) {
            if (parentChildMap.get(parent) != null && parentChildMap.get(parent).size() > 0) {
                Set<String> iterableSet = new HashSet<>(parentChildMap.get(parent));
                for (String childScope : iterableSet) {
                    if (childScope.equals(tag)) {
                        synchronized (this) {
                            parentChildMap.get(parent).remove(childScope);
                        }
                    }
                }
            }
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
