package vulcan.refactorings.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import vulcan.refactorings.AbstractRenameJavaElementRefactoring;
import vulcan.utils.ProjectUtils;

public class RenameField extends AbstractRenameJavaElementRefactoring {

    private String oldName;

    public static class Builder extends AbstractRenameJavaElementRefactoring.Builder<RenameField, Builder> {

        public Builder() {
            this.setUpdateQualifiedNames(false);
            this.setUpdateSimilarDeclarations(false);
        }

        private String oldName;

        public Builder setOldName(String oldName) {
            this.oldName = oldName;
            return this;
        }

        @Override
        protected RenameField newAction() {
            RenameField action = new RenameField();
            action.oldName = this.oldName;
            return action;
        }
    }

    @Override
    public RefactoringDescriptor createRefactoringDescriptor() throws CoreException {
        IJavaProject javaProject = ProjectUtils.getJavaProject(projectName);
        IType type = javaProject.findType(fullyQualifiedName);
        return createtRenameJavaElementDescriptor(type, IJavaRefactorings.RENAME_FIELD, type.getField(oldName));
    }

}
