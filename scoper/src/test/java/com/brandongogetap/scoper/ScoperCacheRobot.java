package com.brandongogetap.scoper;

import android.content.Context;

import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;

final class ScoperCacheRobot {

    private final ScoperCache cache;

    ScoperCacheRobot() {
        this.cache = new ScoperCache(new Logger());
    }

    ScoperCacheRobot initComponent(String tag, Object component) {
        cache.initComponent(tag, component);
        return this;
    }

    ScoperCacheRobot checkComponentEquals(String tag, Object component) {
        assertEquals(component, cache.getComponentForTag(tag));
        return this;
    }

    ScoperCacheRobot checkComponentEquals(Context context, Object component) {
        assertEquals(component, cache.getComponent(context));
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

    ScoperCacheRobot getWithContext(Context context) {
        cache.getComponent(context);
        return this;
    }

    ScoperCacheRobot replaceExisting(boolean replaceExisting) {
        cache.replaceExisting(replaceExisting);
        return this;
    }

    ScoperCacheRobot putReturnsCreated(String tag, Object component) {
        assertEquals(cache.put(tag, component), component);
        return this;
    }

    private ScoperContext getScoperContextForTag(String tag) {
        return new ScoperContext(Mockito.mock(Context.class), tag);
    }

}
