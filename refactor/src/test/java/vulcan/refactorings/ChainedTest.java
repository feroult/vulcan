package vulcan.refactorings;

import org.junit.Test;
import vulcan.refactorings.core.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ChainedTest {

    @Test
    public void testSequenceRefactorings() {
        List<AbstractRefactoringBuilder> refactorings = Arrays.asList(new ExtractMethod.Builder(),
                new RenameType.Builder(), new RenameMethod.Builder(), new RenameField.Builder(),
                new RenameLocalVariable.Builder(), new ExtractMethod.Builder());

        Chained chained = new Chained.Builder().setRefactorings(refactorings)
                .build();

        List<AbstractRefactoringBuilder> orderedRefactorings = chained.getRefactorings();

        assertTrue(ExtractMethod.Builder.class.isInstance(orderedRefactorings.get(0)));
        assertTrue(ExtractMethod.Builder.class.isInstance(orderedRefactorings.get(1)));
        assertTrue(RenameMethod.Builder.class.isInstance(orderedRefactorings.get(2)));
        assertTrue(RenameField.Builder.class.isInstance(orderedRefactorings.get(3)));
        assertTrue(RenameLocalVariable.Builder.class.isInstance(orderedRefactorings.get(4)));
        assertTrue(RenameType.Builder.class.isInstance(orderedRefactorings.get(5)));
    }
}
