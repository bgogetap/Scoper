package com.brandongogetap.scoper;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

public final class ScoperContext extends ContextWrapper {

    static final String SERVICE_NAME = "scoped_context_wrapper";

    private String tag;

    private LayoutInflater layoutInflater;

    public ScoperContext(Context base, Scoped scoped) {
        super(base);
        tag = scoped.getScopeName();
    }

    public ScoperContext(Context base, String tag) {
        super(base);
        this.tag = tag;
    }

    String getTag() {
        return tag;
    }

    @Override public Object getSystemService(String name) {
        if (name.equals(SERVICE_NAME)) {
            return this;
        }
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (layoutInflater == null) {
                layoutInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
            }
            return layoutInflater;
        }
        return super.getSystemService(name);
    }

    static ScoperContext getScoperContext(Context context) {
        //noinspection WrongConstant
        Object scoperContext = context.getSystemService(ScoperContext.SERVICE_NAME);
        if (scoperContext instanceof ScoperContext) {
            return (ScoperContext) scoperContext;
        } else {
            throw new IllegalArgumentException("Context is not instance of ScoperContext: " +
                    context.getClass().getName());
        }
    }
}
