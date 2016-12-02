package com.brandongogetap.scoper.lint;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiNewExpression;

import java.util.Collections;
import java.util.List;

public final class IncorrectChildScopeBuilding extends Detector implements Detector.JavaPsiScanner {

    @Override
    public List<String> getApplicableMethodNames() {
        return Collections.singletonList("getComponent");
    }

    @Override
    public void visitMethod(JavaContext context, JavaElementVisitor visitor, PsiMethodCallExpression call, PsiMethod method) {
        super.visitMethod(context, visitor, call, method);
        PsiElement errorLocation = isBuildingChildScope(call);
        if (errorLocation != null) {
            context.report(INCORRECT_CHILD_SCOPE_BUILDING, context.getLocation(errorLocation), "Scoper pattern error");
        }
    }

    // TODO Actually learn the PSI API
    private PsiElement isBuildingChildScope(PsiMethodCallExpression call) {
        try {
            // Dot
            PsiElement nextSibling = call.getNextSibling();
            // Parenthesis
            nextSibling = nextSibling.getNextSibling();
            // Chained method name
            nextSibling = nextSibling.getParent();
            // Chained method
            PsiElement chainedMethod = nextSibling.getParent();
            if (chainedMethod instanceof PsiMethodCallExpression) {
                PsiExpressionList list = ((PsiMethodCallExpression) chainedMethod).getArgumentList();
                // If chained method is taking an argument that returns an instance of something
                // (such as a new Module), it is probably safe to assume that the user is building a
                // child scope
                if (list.getFirstChild() != null
                        && list.getFirstChild().getNextSibling() instanceof PsiNewExpression) {
                    return chainedMethod;
                }
            }
        } catch (Exception e) {
            // A for effort
        }
        return null;
    }

    public static final Issue INCORRECT_CHILD_SCOPE_BUILDING = Issue.create(
            "IncorrectChildScopeBuilding",
            "Building a child component on top of a parent retrieved with Scoper#getComponent is not recommended",
            "Building a child component on top of a parent retrieved with Scoper#getComponent will not allow the child scope to be automatically destroyed when the parent scope is destroyed" +
                    "Use Scoper#withParent...#createChild instead",
            Category.CORRECTNESS, 5, Severity.WARNING,
            new Implementation(IncorrectChildScopeBuilding.class, Scope.JAVA_FILE_SCOPE));
}
