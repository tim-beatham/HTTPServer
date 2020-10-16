/**
 * Represents a Server
 * response in the
 * HTTP 1.1 format.
 *
 * @author tcb1
 */
public class HTTPResponse {

    private final String content;
    private final String contentType;

    private final HTTPCode code;

    public HTTPResponse(HTTPCode code, String contentType, String content) {
        this.contentType = contentType;
        this.content = content;
        this.code = code;
    }

    @Override
    public String toString() {
        return code.getMsg() + "\n" + "Content-Type: " + contentType + "\n" +
                "Connection: closed\n" + content;
    }
}

