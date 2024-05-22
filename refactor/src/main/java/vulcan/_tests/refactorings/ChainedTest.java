package vulcan._tests.refactorings;

import org.junit.Test;
import vulcan._tests.AbstractTestCase;
import vulcan.refactorings.ChainedResult;
import vulcan.refactorings.RefactoringResult;
import vulcan.refactorings.Chained;
import vulcan.refactorings.core.ExtractMethod;
import vulcan.refactorings.core.RenameType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChainedTest extends AbstractTestCase {

    @Test
    public void simpleChain() {

        String snippet = "if (p != null) {\n            System.out.println(p);\n        }".formatted();

        ExtractMethod.Builder extractMethodBuilder = new ExtractMethod.Builder().setFullyQualifiedName("vulcan.Product")
                .setMethodName("printProduct")
                .setSnippet(snippet);

        RenameType.Builder renameTypeBuilder = new RenameType.Builder().setFullyQualifiedName("vulcan.Product")
                .setNewName("ProductX");

        Chained action = new Chained.Builder().setProjectName("sample-app")
                .setRefactorings(Arrays.asList(extractMethodBuilder, renameTypeBuilder))
                .build();

        ChainedResult result = action.execute();

        assertTrue(result.hasMoved());
        assertEquals("sample-app/vulcan.ProductX", result.getNewLocation());

        List<RefactoringResult> refactoringResults = result.getRefactoringResults();

        assertEquals("Extract Method", refactoringResults.get(0)
                .getName());
        assertEquals("Rename Type", refactoringResults.get(1)
                .getName());

    }
}