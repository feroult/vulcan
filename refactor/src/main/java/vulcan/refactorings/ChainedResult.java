package vulcan.refactorings;

import vulcan.refactorings.core.RenameType;

import java.util.List;
import java.util.Optional;

public class ChainedResult {
    private final List<AbstractRefactoringBuilder> refactorings;
    private final List<RefactoringResult> refactoringResults;
    private boolean hasMoved = false;
    private String newLocation;

    public ChainedResult(List<AbstractRefactoringBuilder> refactorings, List<RefactoringResult> refactoringResults) {
        this.refactorings = refactorings;
        this.refactoringResults = refactoringResults;
        configureIfRenamedType();
    }

    private void configureIfRenamedType() {
        Optional<AbstractRefactoringBuilder> renameType = refactorings.stream()
                .filter(RenameType.Builder.class::isInstance)
                .findFirst();

        if (renameType.isPresent()) {
            RenameType.Builder builder = (RenameType.Builder) renameType.get();
            this.hasMoved = true;
            String prevFullyQualifiedName = builder.getFullyQualifiedName();
            String packageName = prevFullyQualifiedName.substring(0, prevFullyQualifiedName.lastIndexOf("."));
            this.newLocation = builder.projectName + "/" + packageName + "." + builder.getNewName();
        }

    }

    public List<RefactoringResult> getRefactoringResults() {
        return refactoringResults;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public String getNewLocation() {
        return newLocation;
    }
}
