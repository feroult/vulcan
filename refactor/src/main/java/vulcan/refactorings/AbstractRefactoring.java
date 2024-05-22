package vulcan.refactorings;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRefactoring {

    private boolean consumed = false;

    protected RefactoringResult performChange(RefactoringDescriptor descriptor) {
        try {
            RefactoringStatus status = new RefactoringStatus();
            Refactoring refactoring = descriptor.createRefactoring(status);
            IProgressMonitor monitor = new NullProgressMonitor();
            refactoring.checkInitialConditions(monitor);
            refactoring.checkFinalConditions(monitor);
            Change change = refactoring.createChange(monitor);
            change.perform(monitor);
            return RefactoringResult.fromChange(change);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RefactoringResult> execute() {
        try {
            prepareForExecution();
            List<RefactoringResult> result = new ArrayList<>();
            while (hasMoreRefactoringDescriptors()) {
                result.add(performChange(nextRefactoringDescriptor()));
            }
            return result;
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    protected void prepareForExecution() throws CoreException {

    }

    protected boolean hasMoreRefactoringDescriptors() {
        return !consumed;
    }

    protected RefactoringDescriptor nextRefactoringDescriptor() throws CoreException {
        RefactoringDescriptor refactoringDescriptor = createRefactoringDescriptor();
        consumed = true;
        return refactoringDescriptor;
    }

    protected RefactoringDescriptor createRefactoringDescriptor() throws CoreException {
        return null;
    }
}
