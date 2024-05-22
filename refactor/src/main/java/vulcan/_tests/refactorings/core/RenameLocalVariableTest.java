package vulcan._tests.refactorings.core;

import org.junit.Test;
import vulcan._tests.AbstractTestCase;
import vulcan.refactorings.RefactoringResult;
import vulcan.refactorings.core.RenameLocalVariable;
import vulcan.utils.ResourceUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RenameLocalVariableTest extends AbstractTestCase {

    @Test
    public void renameLocalVariableInMultipleMethods() throws Exception {
        RenameLocalVariable action = new RenameLocalVariable.Builder().setProjectName("sample-app")
                .setFullyQualifiedName("vulcan.Order")
                .setOldName("renameVar")
                .setNewName("xptoVar")
                .build();

        RefactoringResult result = action.execute()
                .get(0);

        assertEquals("Rename Local Variable", result.getName());
        assertEquals(0, result.getChanges()
                .size());

        String productSource = ResourceUtils.readFileAsString(
                "target/vulcan-test-workspace/sample-app/src/main/java/vulcan/Order.java");
        assertTrue(productSource.indexOf("int xptoVar = 0;") != -1);
        assertTrue(productSource.indexOf("int xptoVar = 1;") != -1);
        assertTrue(productSource.indexOf("return xptoVar;") != -1);
    }
}
