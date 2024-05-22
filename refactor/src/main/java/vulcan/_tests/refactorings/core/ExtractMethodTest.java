package vulcan._tests.refactorings.core;

import org.junit.Test;
import vulcan._tests.AbstractTestCase;
import vulcan.refactorings.RefactoringResult;
import vulcan.refactorings.core.ExtractMethod;
import vulcan.utils.ResourceUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExtractMethodTest extends AbstractTestCase {

    @Test
    public void simpleTest() throws Exception {
        String prevProductSource = ResourceUtils.readFileAsString(
                "target/vulcan-test-workspace/sample-app/src/main/java/vulcan/Product.java");

        String snippet = "if (p != null) {\n            System.out.println(p);\n        }".formatted();

        ExtractMethod action = new ExtractMethod.Builder().setProjectName("sample-app")
                .setFullyQualifiedName("vulcan.Product")
                .setMethodName("printProduct")
                .setOffset(prevProductSource.indexOf(snippet))
                .setLength(snippet.length())
                .build();

        RefactoringResult result = action.execute()
                .get(0);

        assertEquals("Extract Method", result.getName());

        List<String> changes = result.getChanges();
        assertEquals(3, changes.size());
        assertEquals("Substitute statements with call to printProduct", changes.get(0));
        assertEquals("Create new method 'printProduct' from selected statements", changes.get(1));
        assertEquals("Organize Imports", changes.get(2));

        String productSource = ResourceUtils.readFileAsString(
                "target/vulcan-test-workspace/sample-app/src/main/java/vulcan/Product.java");
        assertTrue(productSource.indexOf("private void printProduct") != -1);
    }

    @Test
    public void extractBySnippet() {
        String snippet = "if (p != null) {\n" + "            System.out.println(p);\n" + "        }";

        ExtractMethod action = new ExtractMethod.Builder().setProjectName("sample-app")
                .setFullyQualifiedName("vulcan.Product")
                .setMethodName("printProduct")
                .setSnippet(snippet)
                .build();

        action.execute();

        String productSource = ResourceUtils.readFileAsString(
                "target/vulcan-test-workspace/sample-app/src/main/java/vulcan/Product.java");
        assertTrue(productSource.indexOf("private void printProduct") != -1);
    }
}
