package vulcan._tests.refactorings.core;

import org.junit.Test;
import vulcan._tests.AbstractTestCase;
import vulcan.refactorings.RefactoringResult;
import vulcan.refactorings.core.RenameMethod;
import vulcan.utils.ResourceUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RenameMethodTest extends AbstractTestCase {

    @Test
    public void renameMethodUpdatesReferences() throws Exception {
        RenameMethod action = new RenameMethod.Builder().setProjectName("sample-app")
                .setFullyQualifiedName("vulcan.Product")
                .setOldName("renameMethodTest")
                .setNewName("renameMethodTestXPTO")
                .build();

        RefactoringResult result = action.execute()
                .get(0);

        assertEquals("Rename Method", result.getName());
        assertEquals(0, result.getChanges()
                .size());

        String productSource = ResourceUtils.readFileAsString(
                "target/vulcan-test-workspace/sample-app/src/main/java/vulcan/Product.java");
        assertTrue(productSource.indexOf("public void renameMethodTestXPTO()") != -1);
        assertTrue(productSource.indexOf("public void renameMethodTestXPTO(String arg)") != -1);

        String orderSource = ResourceUtils.readFileAsString(
                "target/vulcan-test-workspace/sample-app/src/main/java/vulcan/Order.java");
        assertTrue(orderSource.indexOf(" product.renameMethodTestXPTO()") != -1);
    }
}
