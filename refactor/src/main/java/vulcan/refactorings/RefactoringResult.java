package vulcan.refactorings;

import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.ltk.core.refactoring.Change;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RefactoringResult {
    private String name;
    private List<String> changes;

    public static RefactoringResult fromChange(Change change) {
        RefactoringResult result = new RefactoringResult();
        result.name = change.getName();

        if (change instanceof CompilationUnitChange) {
            result.changes = Arrays.stream(((CompilationUnitChange) change).getChangeGroups())
                    .map(e -> e.getName())
                    .collect(Collectors.toList());
        } else {
            result.changes = Arrays.asList();
        }
        return result;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getChanges() {
        return this.changes;
    }
}
