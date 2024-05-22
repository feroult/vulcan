package vulcan.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;

public class ProjectUtils {

    public static IJavaProject getJavaProject(String projectName) throws CoreException {
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

        if (!project.exists()) {
            project.create(null);
        }
        project.open(null);


        /*
 TODO: right now we're using mvn eclipse:eclipse - but maybe we not it it
        IProjectDescription description = project.getDescription();
        description.setNatureIds(new String[] { JavaCore.NATURE_ID });
        project.setDescription(description, null);
*/

        IJavaProject javaProject = JavaCore.create(project);

        javaProject.setOption(DefaultCodeFormatterConstants.FORMATTER_TAB_SIZE, "4");
        javaProject.setOption(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);

        /*
 TODO: right now we're using mvn eclipse:eclipse - but maybe we not it it
        IClasspathEntry[] buildPath = { JavaCore.newSourceEntry(project.getFullPath().append("src")) };
        javaProject.setRawClasspath(buildPath, project.getFullPath().append("bin"), null);
*/
        return javaProject;
    }
}
