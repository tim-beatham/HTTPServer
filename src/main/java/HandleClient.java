import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class which handles
 * each client connection.
 *
 * @author tcb1
 */
public class HandleClient implements Runnable{

    // The socket which is connected to the given client.
    private Socket clientSocket;

    // The regex to extract the URL that the client is requesting.
    private static String URL_REGEX = "GET (/.+) HTTP/1.1";

    public HandleClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Listens out for the
     * client's request
     * and handles the given
     * request.
     */
    private void getRequest() {
        try (
                // Do not set the second parameter to true. We want to flush at the very end.
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {

            String request = in.readLine();
            System.out.println(request);

            out.print(readFile(request));

            out.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads a given
     * file provided the path
     * to the file.
     * @param request the path to
     *                 the file that
     *                 we are reading.
     */
    public HTTPResponse readFile(String request) throws IOException {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(request);

        if (matcher.find()) {
            String path = matcher.group(1);


            return new HTTPResponse(HTTPCode.NOT_FOUND, "text/html",
                    Files.readString(Path.of("server_directory" + path)));

        }

        return new HTTPResponse(HTTPCode.NOT_FOUND,
                "text/html", Files.readString(Path.of("server_directory/BadRequest.html")));


    }

    @Override
    public void run() {
        getRequest();
    }
}
