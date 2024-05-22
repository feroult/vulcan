package vulcan.refactorings.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import vulcan.refactorings.AbstractRenameJavaElementRefactoring;
import vulcan.utils.ProjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RenameMethod extends AbstractRenameJavaElementRefactoring {

    private String oldName;
    private List<IMethod> methods;
    private IType type;

    public static class Builder extends AbstractRenameJavaElementRefactoring.Builder<RenameMethod, Builder> {

        public Builder() {
            this.setUpdateTextualOccurrences(false);
            this.setUpdateQualifiedNames(false);
            this.setUpdateSimilarDeclarations(false);
        }

        private String oldName;

        public Builder setOldName(String oldName) {
            this.oldName = oldName;
            return this;
        }

        @Override
        protected RenameMethod newAction() {
            RenameMethod action = new RenameMethod();
            action.oldName = this.oldName;
            return action;
        }
    }

    @Override
    protected void prepareForExecution() throws CoreException {
        IJavaProject javaProject = ProjectUtils.getJavaProject(projectName);
        type = javaProject.findType(fullyQualifiedName);
        methods = Arrays.stream(type.getMethods())
                .filter(e -> e.getElementName()
                        .equals(oldName))
                .collect(Collectors.toList());
    }

    @Override
    protected boolean hasMoreRefactoringDescriptors() {
        return methods.size() > 0;
    }

    @Override
    protected RefactoringDescriptor nextRefactoringDescriptor() throws CoreException {
        IMethod m = methods.remove(0);
        return createtRenameJavaElementDescriptor(type, IJavaRefactorings.RENAME_METHOD, m);
    }

}
