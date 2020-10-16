import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Runs the main loop of the
 * server. On each
 * connection it creates
 * a new thread to
 * service the client.
 *
 * @author tcb1
 */
public class Server {


    // When true the program stops listening.
    private static boolean stop = false;


    /**
     * Listens for clients connecting on
     * a given port number. Serves the
     * given request.
     * @param port - the port we
     *             are listening for the client
     *             on.
     */
    public static void listen(int port) {
        while (!stop) {

            try (
                ServerSocket socket = new ServerSocket(port);
            ) {
                Socket clientSocket = socket.accept();
                (new Thread(new HandleClient(clientSocket))).start();
            } catch (IOException e) {}

        }
    }


    public static void main(String[] args) {
        listen(4444);
    }

}
