package vulcan.refactorings;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;

public abstract class AbstractRenameJavaElementRefactoring extends AbstractRefactoring {

    protected String projectName;
    protected String fullyQualifiedName;
    protected String newName;
    protected boolean updateReferences;
    protected boolean updateQualifiedNames;
    protected boolean updateTextualOccurrences;
    protected boolean updateSimilarDeclarations;

    public abstract static class Builder<A extends AbstractRenameJavaElementRefactoring, B extends Builder> extends AbstractRefactoringBuilder<A, B> {

        private String newName;
        private boolean updateReferences = true;
        private boolean updateQualifiedNames = true;
        private boolean updateTextualOccurrences = true;
        private boolean updateSimilarDeclarations = true;

        public Builder() {
        }

        public B setNewName(String newName) {
            this.newName = newName;
            return (B) this;
        }

        public B setUpdateReferences(boolean updateReferences) {
            this.updateReferences = updateReferences;
            return (B) this;
        }

        public B setUpdateQualifiedNames(boolean updateQualifiedNames) {
            this.updateQualifiedNames = updateQualifiedNames;
            return (B) this;
        }

        public B setUpdateTextualOccurrences(boolean updateTextualOccurrences) {
            this.updateTextualOccurrences = updateTextualOccurrences;
            return (B) this;
        }

        public B setUpdateSimilarDeclarations(boolean updateSimilarDeclarations) {
            this.updateSimilarDeclarations = updateSimilarDeclarations;
            return (B) this;
        }

        public String getNewName() {
            return newName;
        }

        @Override
        public A build() {
            AbstractRenameJavaElementRefactoring action = newAction();
            action.projectName = this.projectName;
            action.fullyQualifiedName = this.fullyQualifiedName;
            action.newName = this.newName;
            action.updateReferences = this.updateReferences;
            action.updateQualifiedNames = this.updateQualifiedNames;
            action.updateTextualOccurrences = this.updateTextualOccurrences;
            action.updateSimilarDeclarations = this.updateSimilarDeclarations;
            return (A) action;
        }

        protected abstract A newAction();
    }

    protected RenameJavaElementDescriptor createtRenameJavaElementDescriptor(IType type, String refactoringId,
                                                                             IJavaElement javaElement) {
        RefactoringContribution contribution = RefactoringCore.getRefactoringContribution(refactoringId);
        RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
        descriptor.setProject(type.getResource()
                .getProject()
                .getName());
        descriptor.setJavaElement(javaElement);
        descriptor.setNewName(newName);
        descriptor.setUpdateReferences(updateReferences);
        descriptor.setUpdateQualifiedNames(updateQualifiedNames);
        descriptor.setUpdateTextualOccurrences(updateTextualOccurrences);
        descriptor.setUpdateSimilarDeclarations(updateSimilarDeclarations);
        return descriptor;
    }

}
