package vulcan.refactorings.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import vulcan.refactorings.AbstractRefactoring;
import vulcan.refactorings.AbstractRefactoringBuilder;
import vulcan.utils.InternalUtils;
import vulcan.utils.ProjectUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ExtractMethod extends AbstractRefactoring {

    private static final String ATTRIBUTE_SELECTION = "selection";
    private static final String ATTRIBUTE_INPUT = "input";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_VISIBILITY = "visibility";
    private static final String ATTRIBUTE_DESTINATION = "destination";
    private static final String ATTRIBUTE_COMMENTS = "comments";
    private static final String ATTRIBUTE_REPLACE = "replace";
    private static final String ATTRIBUTE_EXCEPTIONS = "exceptions";

    private String projectName;
    private String fullyQualifiedName;
    private String snippet;
    private int offset;
    private int length;
    private String methodName;

    private ExtractMethod() {
    }

    public static class Builder extends AbstractRefactoringBuilder<ExtractMethod, Builder> {
        private String methodName;
        private String snippet;
        private int offset;
        private int length;

        public Builder() {
        }

        public Builder setMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder setSnippet(String snippet) {
            this.snippet = snippet;
            return this;
        }

        public Builder setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder setLength(int length) {
            this.length = length;
            return this;
        }

        @Override
        public ExtractMethod build() {
            ExtractMethod action = new ExtractMethod();
            action.projectName = this.projectName;
            action.fullyQualifiedName = this.fullyQualifiedName;
            action.methodName = this.methodName;
            action.snippet = this.snippet;
            action.offset = this.offset;
            action.length = this.length;
            return action;
        }
    }

    @Override
    protected RefactoringDescriptor createRefactoringDescriptor() throws CoreException {
        IJavaProject javaProject = ProjectUtils.getJavaProject(projectName);
        IType type = javaProject.findType(fullyQualifiedName);


        RefactoringContribution contribution = RefactoringCore.getRefactoringContribution(
                IJavaRefactorings.EXTRACT_METHOD);

        HashMap<String, String> arguments = new HashMap<>();

        setSelection(type, arguments);
        arguments.put(ATTRIBUTE_INPUT, type.getCompilationUnit()
                .getHandleIdentifier());
        arguments.put(ATTRIBUTE_NAME, methodName);
        arguments.put(ATTRIBUTE_VISIBILITY, String.valueOf(InternalUtils.getVisibilityCode("private")));
        arguments.put(ATTRIBUTE_DESTINATION, "0");
        arguments.put(ATTRIBUTE_COMMENTS, "false");
        arguments.put(ATTRIBUTE_REPLACE, "true");
        arguments.put(ATTRIBUTE_EXCEPTIONS, "true");

        return contribution.createDescriptor(IJavaRefactorings.EXTRACT_METHOD, projectName, "extract method",
                "extract method", arguments, 0);
    }

    private void setSelection(IType type, HashMap<String, String> arguments) throws JavaModelException {
        if (snippet == null) {
            arguments.put(ATTRIBUTE_SELECTION, String.format("%d %d", offset, length));
            return;
        }

        String source = readSource(type);
        int snippetOffset = source.indexOf(snippet);
        if (snippetOffset == -1) {
            // After a first refactoing is applied in a chainned refactoring, lines are trimmed and snippets won't match anymore.
            // TODO: think about formatting everything before starting refactoring sessions.
            snippetOffset = source.indexOf(trimmedSnippet());
        }
        arguments.put(ATTRIBUTE_SELECTION, String.format("%d %d", snippetOffset, snippet.length()));
    }

    private String trimmedSnippet() {
        return Arrays.stream(snippet.split("\\n"))
                .map(line -> line.replaceAll("\\s+$", ""))
                .collect(Collectors.joining("\n"))
                .trim();
    }

    private static String readSource(IType type) {
        try {
            return Files.readString(Paths.get(type.getResource()
                    .getRawLocation()
                    .toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
