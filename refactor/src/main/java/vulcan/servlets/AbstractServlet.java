package vulcan.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public abstract class AbstractServlet extends HttpServlet {
    private static String getRequestBody(HttpServletRequest req) throws IOException {
        return req.getReader()
                .lines()
                .collect(Collectors.joining());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String requestBody = getRequestBody(req);
        String path = req.getPathInfo();

        try {
            ResponseWrapper wrapper = doPost(path, requestBody);
            wrapper.response(resp);
        } catch (NotImplementedException e) {
            super.doPost(req, resp);
        } catch (NotFoundException e) {
            notFound(resp);
        }
    }

    protected ResponseWrapper doPost(String path, String body) {
        throw new NotImplementedException();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            ResponseWrapper wrapper = doGet(path);
            wrapper.response(resp);
        } catch (NotImplementedException e) {
            super.doGet(req, resp);
        } catch (NotFoundException e) {
            notFound(resp);
        }
    }

    protected ResponseWrapper doGet(String path) {
        throw new NotImplementedException();
    }

    private static void notFound(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.setContentLength(0);
        resp.flushBuffer();
    }
}
