package vulcan.refactorings.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import vulcan.refactorings.AbstractRenameJavaElementRefactoring;
import vulcan.utils.ProjectUtils;

public class RenameType extends AbstractRenameJavaElementRefactoring {
    public static class Builder extends AbstractRenameJavaElementRefactoring.Builder<RenameType, Builder> {
        @Override
        protected RenameType newAction() {
            return new RenameType();
        }
    }

    @Override
    public RefactoringDescriptor createRefactoringDescriptor() throws CoreException {
        IJavaProject javaProject = ProjectUtils.getJavaProject(projectName);
        IType type = javaProject.findType(fullyQualifiedName);
        return createtRenameJavaElementDescriptor(type, IJavaRefactorings.RENAME_TYPE, type);
    }

}
