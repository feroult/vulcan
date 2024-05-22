package vulcan.refactorings;

import vulcan.servlets.NotFoundException;

record RefactoringArgs(String projectName, String fullyQualifiedName) {
    @Override
    public String projectName() {
        return projectName;
    }

    @Override
    public String fullyQualifiedName() {
        return fullyQualifiedName;
    }

    public static RefactoringArgs parseRefactoringArgs(String path) {
        String[] parts = path.split("/");
        if (parts.length == 3 && parts[0].isEmpty()) {
            return new RefactoringArgs(parts[1], parts[2]);
        } else {
            throw new NotFoundException();
        }
    }
}
