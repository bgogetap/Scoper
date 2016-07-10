package com.brandongogetap.scoper;

import android.content.Context;

import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;

final class ScoperCacheRobot {

    private final ScoperCache cache;

    ScoperCacheRobot() {
        this.cache = new ScoperCache();
    }

    ScoperCacheRobot initComponent(String tag, Object component) {
        cache.initComponent(getScoperContextForTag(tag), component);
        return this;
    }

    ScoperCacheRobot checkComponentEquals(String tag, Object component) {
        Object cachedComponent = cache.getComponentForTag(tag);
        assertEquals(component, cachedComponent);
        return this;
    }

    ScoperCacheRobot checkCacheCountEquals(int count) {
        assertEquals(count, cache.getCacheCount());
        return this;
    }

    ScoperCacheRobot destroyComponent(String tag) {
        cache.destroyScope(getScoperContextForTag(tag));
        return this;
    }

    ScoperCacheRobot getComponent(String tag) {
        cache.getComponent(getScoperContextForTag(tag));
        return this;
    }

    ScoperCacheRobot putComponent(String tag, Object component) {
        cache.put(tag, component);
        return this;
    }

    ScoperCacheRobot getWithoutScoperContextInstance() {
        cache.getComponent(Mockito.mock(Context.class));
        return this;
    }

    private ScoperContext getScoperContextForTag(String tag) {
        return new ScoperContext(Mockito.mock(Context.class), tag);
    }
}
