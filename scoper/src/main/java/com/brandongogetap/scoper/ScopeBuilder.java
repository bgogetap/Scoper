package com.brandongogetap.scoper;

import android.content.Context;

import static com.brandongogetap.scoper.ScoperContext.getScoperContext;

public final class ScopeBuilder<T> {

    private String parentScopeTag;
    private T parentScope;

    private ScopeBuilder(Context parentContext) {
        this(getScoperContext(parentContext).getTag());
    }

    private ScopeBuilder(String parentScopeTag) {
        this.parentScopeTag = parentScopeTag;
        parentScope = Scoper.getComponentForName(parentScopeTag);
    }

    static <T> ScopeBuilder<T> start(Context parentContext) {
        return new ScopeBuilder<>(parentContext);
    }

    static <T> ScopeBuilder<T> start(String parentScopeTag) {
        return new ScopeBuilder<>(parentScopeTag);
    }

    public <C> C createChild(Scoped<?> scoped, ChildBuilder<T> childBuilder) {
        Preconditions.checkNotNull(parentScope, "Cannot create child without calling Scoper#withParent first");
        C child = (C) childBuilder.build(parentScope);
        return Scoper.createChild(parentScopeTag, scoped, child);
    }
}
