package vulcan.refactorings.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import vulcan.refactorings.AbstractRenameJavaElementRefactoring;
import vulcan.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

public class RenameLocalVariable extends AbstractRenameJavaElementRefactoring {

    private String oldName;
    private IType type;
    private List<IJavaElement> variables;

    public static class Builder extends AbstractRenameJavaElementRefactoring.Builder<RenameLocalVariable, Builder> {

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
        protected RenameLocalVariable newAction() {
            RenameLocalVariable action = new RenameLocalVariable();
            action.oldName = this.oldName;
            return action;
        }
    }


    @Override
    protected void prepareForExecution() throws CoreException {
        IJavaProject javaProject = ProjectUtils.getJavaProject(projectName);
        type = javaProject.findType(fullyQualifiedName);
    }

    @Override
    protected boolean hasMoreRefactoringDescriptors() {
        variables = getVariables(type);
        return variables.size() > 0;
    }

    @Override
    protected RefactoringDescriptor nextRefactoringDescriptor() throws CoreException {
        IJavaElement v = variables.get(0);
        return createtRenameJavaElementDescriptor(type, IJavaRefactorings.RENAME_LOCAL_VARIABLE, v);
    }

    private List<IJavaElement> getVariables(IType type) {
        ASTParser parser = ASTParser.newParser(AST.JLS_Latest);
        parser.setSource(type.getCompilationUnit());
        parser.setResolveBindings(true);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        List<IJavaElement> variables = new ArrayList<>();
        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(VariableDeclarationStatement node) {
                for (Object obj : node.fragments()) {
                    if (obj instanceof VariableDeclarationFragment variableDeclaration) {
                        IJavaElement javaElement = variableDeclaration.resolveBinding()
                                .getJavaElement();
                        if (javaElement.getElementName()
                                .equals(oldName)) {
                            variables.add(javaElement);
                        }
                    }
                }
                return super.visit(node);
            }
        });

        return variables;
    }
}
