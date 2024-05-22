package vulcan.refactorings;

public abstract class AbstractRefactoringBuilder<A extends AbstractRefactoring, B extends AbstractRefactoringBuilder> {
    protected String projectName;
    protected String fullyQualifiedName;

    public B setProjectName(String projectName) {
        this.projectName = projectName;
        return (B) this;
    }

    public B setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
        return (B) this;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public abstract A build();
}
