package vulcan._tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Before;
import vulcan.utils.ResourceUtils;

public abstract class AbstractTestCase {

    public static final String TMP_VULCAN_TEST_WORKSPACE = "target/vulcan-test-workspace";
    public static final String SAMPLE_WORKSPACE = "src/test/resources/sample-workspace";

    @Before
    public void setUp() {
        ResourceUtils.deleteFolder(TMP_VULCAN_TEST_WORKSPACE);
        ResourceUtils.copyFolder(SAMPLE_WORKSPACE, TMP_VULCAN_TEST_WORKSPACE);
    }

    @After
    public void tearDown() {
        for (IProject project : ResourcesPlugin.getWorkspace()
                .getRoot()
                .getProjects()) {
            try {
                project.delete(true, null);
            } catch (CoreException e) {
            }
        }
//        ResourceUtils.deleteFolder(TMP_VULCAN_TEST_WORKSPACE);
    }
}
