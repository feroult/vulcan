package vulcan.refactorings;

import org.junit.Test;
import vulcan.refactorings.core.ExtractMethod;
import vulcan.refactorings.core.RenameType;

import static org.junit.Assert.assertEquals;

public class RefactoringServletTest {

    @Test
    public void simpleTest() {
        var body = """ 
                   [
                       {
                         "type": "refactorings/core/rename-type",
                         "newName": "ProductX"
                       },             
                       {
                         "type": "refactorings/core/extract-method",
                         "methodName": "extractedPrintProduct",
                         "offset": 123,
                         "length": 61
                       }
                   ]
                   """;

        RefactoringInvoker invoker = new RefactoringInvoker();

        RefactoringArgs args = RefactoringArgs.parseRefactoringArgs("/sample-app/vulcan.Product");
        Chained chained = invoker.getChained(args, body);

        assertEquals("sample-app", chained.getProjectName());
        assertEquals("vulcan.Product", chained.getFullyQualifiedName());
        assertEquals(ExtractMethod.Builder.class, chained.getRefactorings()
                .get(0)
                .getClass());
        assertEquals(RenameType.Builder.class, chained.getRefactorings()
                .get(1)
                .getClass());
    }
}
