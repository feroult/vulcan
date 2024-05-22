package vulcan.servlets;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResponseWrapper {
    void response(HttpServletResponse resp) throws IOException;
}
