package vulcan.refactorings;

import vulcan.servlets.*;

public class RefactoringServlet extends AbstractServlet {
    private final RefactoringInvoker refactoringInvoker;
    private final BrowseInvoker browseInvoker;

    public RefactoringServlet() {
        this.refactoringInvoker = new RefactoringInvoker();
        this.browseInvoker = new BrowseInvoker();
    }

    @Override
    protected ResponseWrapper doPost(String path, String body) {
        RefactoringArgs args = RefactoringArgs.parseRefactoringArgs(path);
        return invokeRefactoring(args, body);
    }

    private JsonResponse invokeRefactoring(RefactoringArgs args, String body) {
        ChainedResult result = refactoringInvoker.getChained(args, body)
                .execute();

        if (result.hasMoved()) {
            return JsonResponse.movedPermanently(refactoringInvoker.getGson(), result.getRefactoringResults(),
                    result.getNewLocation());
        }

        return JsonResponse.success(refactoringInvoker.getGson(), result.getRefactoringResults());
    }

    @Override
    protected ResponseWrapper doGet(String path) {
        RefactoringArgs args = RefactoringArgs.parseRefactoringArgs(path);
        return new TextResponse(browseInvoker.getSource(args));
    }

}
