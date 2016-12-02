package com.brandongogetap.scoper.lint;

import com.android.tools.lint.detector.api.Issue;

import java.util.Collections;
import java.util.List;

import static com.brandongogetap.scoper.lint.IncorrectChildScopeBuilding.INCORRECT_CHILD_SCOPE_BUILDING;

public final class IssueRegistry extends com.android.tools.lint.client.api.IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        return Collections.singletonList(INCORRECT_CHILD_SCOPE_BUILDING);
    }
}
