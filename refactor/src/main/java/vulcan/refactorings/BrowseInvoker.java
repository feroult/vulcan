package vulcan.refactorings;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IType;
import vulcan.servlets.NotFoundException;
import vulcan.utils.ProjectUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BrowseInvoker {
    public String getSource(RefactoringArgs args) {

        try {
            IType type = ProjectUtils.getJavaProject(args.projectName())
                    .findType(args.fullyQualifiedName());
            return Files.readString(Paths.get(type.getUnderlyingResource()
                    .getRawLocation()
                    .toString()));
        } catch (CoreException e) {
            throw new NotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
