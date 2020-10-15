import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.InetSocketAddress;

/**
 * An example of how to
 * respond to service a HTTP response in Java.
 * Implements the HttpHandler which allows
 * us to define what happens when the user
 * request an end point.
 *
 * @author tcb1
 */
public class Handler implements HttpHandler {

    // The HTML that we want to send to the user as a response.
    private static final File file = new File("response.html");

    // Use this to read the file line by line
    private static final StringBuilder contents = new StringBuilder();

    /**
     * Handles a given request at a
     * certain end point.
     * @param httpExchange encapsulates a HTTP request received
     *                     and a response to be generated in one exchange.
     * @throws IOException thrown by the OutputStream if an error is
     * experienced.
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        OutputStream os = httpExchange.getResponseBody();
        Headers h = httpExchange.getResponseHeaders();

        System.out.println(httpExchange.getRequestMethod());

        if (httpExchange.getRequestMethod().equals("GET")) {
            String response = contents.toString();

            h.add("Content-Type", "text/html");

            // Need to add the number bytes in the response.
            httpExchange.sendResponseHeaders(200, response.length());

            os.write(response.getBytes());
        } else {
            String response = "This server can only handle GET requests!";

            h.add("Content-Type", "text/html");
            httpExchange.sendResponseHeaders(200, response.length());

            os.write(response.getBytes());
        }

        // Send the data to the output stream and close the output sream.
        os.flush();
        os.close();
    }

    /**
     * Read the contents of the given html
     * file line by line and put it into a
     * String.
     */
    public static void getContents() {
        try (
            BufferedReader br = new BufferedReader(new FileReader(file))
        ) {
            String currentLine;
            while((currentLine = br.readLine()) != null) {
                contents.append(currentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        getContents();
        HttpServer server = HttpServer.create(new InetSocketAddress(4444), 0);

        // Create the end point at jeremy.
        server.createContext("/jeremy", new Handler());
        server.setExecutor(null); // Default executor.
        server.start();
    }
}
