public enum HTTPCode {
    OK (200, "HTTP/1.1 200 OK"),
    NOT_FOUND(200, "HTTP/1.1 404 Not Found");

    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    HTTPCode(int code, String message) {
        this.code = code;
        this.msg = message;
    }
}
