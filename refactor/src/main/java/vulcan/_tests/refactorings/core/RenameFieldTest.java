package vulcan._tests.refactorings.core;

import org.junit.Test;
import vulcan._tests.AbstractTestCase;
import vulcan.refactorings.RefactoringResult;
import vulcan.refactorings.core.RenameField;
import vulcan.utils.ResourceUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RenameFieldTest extends AbstractTestCase {

    @Test
    public void renameMethodUpdatesReferences() throws Exception {
        RenameField action = new RenameField.Builder().setProjectName("sample-app")
                .setFullyQualifiedName("vulcan.Product")
                .setOldName("price")
                .setNewName("priceX")
                .build();

        RefactoringResult result = action.execute()
                .get(0);

        assertEquals("Rename Field", result.getName());
        assertEquals(0, result.getChanges()
                .size());

        String productSource = ResourceUtils.readFileAsString(
                "target/vulcan-test-workspace/sample-app/src/main/java/vulcan/Product.java");
        assertTrue(productSource.indexOf("priceX = 100") != -1);
        assertTrue(productSource.indexOf("priceX is: \" + priceX") != -1);
    }
}
