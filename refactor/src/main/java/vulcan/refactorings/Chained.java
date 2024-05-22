package vulcan.refactorings;

import vulcan.refactorings.core.RenameType;
import vulcan.utils.ProjectUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Chained {

    private String projectName;

    private String fullyQualifiedName;

    private List<AbstractRefactoringBuilder> refactorings;

    public String getProjectName() {
        return projectName;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public List<AbstractRefactoringBuilder> getRefactorings() {
        return refactorings;
    }


    public static class Builder {
        private String projectName;

        private String fullyQualifiedName;

        private List<AbstractRefactoringBuilder> refactorings;

        public Builder() {
        }

        public Builder setProjectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder setFullyQualifiedName(String fullyQualifiedName) {
            this.fullyQualifiedName = fullyQualifiedName;
            return this;
        }

        public Builder setRefactorings(List<AbstractRefactoringBuilder> refactorings) {
            this.refactorings = refactorings;
            return this;
        }

        private void sequenceRefactorings() {
            List<String> sortOrder = Arrays.asList("ExtractMethod", "RenameMethod", "RenameField",
                    "RenameLocalVariable", "RenameType");

            Collections.sort(refactorings, (class1, class2) -> {
                int index1 = sortOrder.indexOf(getBuilderClassName(class1));
                int index2 = sortOrder.indexOf(getBuilderClassName(class2));
                return index1 - index2;
            });
        }

        private static String getBuilderClassName(AbstractRefactoringBuilder class1) {
            return class1.getClass()
                    .getName()
                    .replaceAll(".*\\.(.*?)\\$.*", "$1");
        }

        public Chained build() {
            Chained action = new Chained();
            sequenceRefactorings();
            action.projectName = this.projectName;
            action.fullyQualifiedName = this.fullyQualifiedName;
            action.refactorings = this.refactorings;
            return action;
        }
    }

    public ChainedResult execute() {
        List<RefactoringResult> refactoringResults = refactorings.stream()
                .map(builder -> {
                    if (builder.getProjectName() == null) {
                        builder.setProjectName(projectName);
                    }
                    if (builder.getFullyQualifiedName() == null) {
                        builder.setFullyQualifiedName(fullyQualifiedName);
                    }
                    return builder.build();
                })
                .map(AbstractRefactoring::execute)
                .flatMap(List::stream)
                .collect(Collectors.toList());


        return new ChainedResult(refactorings, refactoringResults);
    }

}
