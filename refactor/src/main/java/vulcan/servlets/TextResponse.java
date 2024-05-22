package vulcan.servlets;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TextResponse implements ResponseWrapper {
    private final String content;

    public TextResponse(String content) {
        this.content = content;
    }

    @Override
    public void response(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter()
                .write(content);
    }
}
