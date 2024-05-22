package vulcan._tests.refactorings.core;

import org.junit.Test;
import vulcan._tests.AbstractTestCase;
import vulcan.refactorings.RefactoringResult;
import vulcan.refactorings.core.RenameType;
import vulcan.utils.ResourceUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RenameTypeTest extends AbstractTestCase {

    @Test
    public void renameMethodUpdatesReferences() throws Exception {
        RenameType action = new RenameType.Builder().setProjectName("sample-app")
                .setFullyQualifiedName("vulcan.Product")
                .setNewName("ProductX")
                .build();

        RefactoringResult result = action.execute()
                .get(0);

        assertEquals("Rename Type", result.getName());
        assertEquals(0, result.getChanges()
                .size());

        assertTrue(ResourceUtils.exists("target/vulcan-test-workspace/sample-app/src/main/java/vulcan/ProductX.java"));

        String orderSource = ResourceUtils.readFileAsString(
                "target/vulcan-test-workspace/sample-app/src/main/java/vulcan/Order.java");
        assertTrue(orderSource.indexOf("private List<ProductX>") != -1);
    }
}
