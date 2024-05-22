package vulcan.servlets;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonResponse implements ResponseWrapper {

    private final Gson gson;

    private final int status;

    private final Map<String, String> headers;

    private final Object object;

    private JsonResponse(Gson gson, int status, Map<String, String> headers, Object object) {
        this.gson = gson;
        this.status = status;
        this.headers = headers;
        this.object = object;
    }

    public static JsonResponse movedPermanently(Gson gson, Object object, String newLocation) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", newLocation);
        return new JsonResponse(gson, HttpServletResponse.SC_MOVED_PERMANENTLY, headers, object);
    }

    public static JsonResponse success(Gson gson, Object object) {
        return new JsonResponse(gson, HttpServletResponse.SC_OK, null, object);
    }

    @Override
    public void response(HttpServletResponse resp) throws IOException {
        resp.setStatus(status);
        if (headers != null) {
            for (String s : headers.keySet()) {
                resp.setHeader(s, headers.get(s));
            }
        }
        resp.setContentType("application/json");
        resp.getWriter()
                .write(gson.toJson(object));
    }
}
