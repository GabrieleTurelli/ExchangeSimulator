package client.model.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements AutoCloseable {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public ClientConnection() throws IOException {
        this.socket = new Socket(SERVER_HOST, SERVER_PORT);
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendRequest(String request) {
        writer.println(request);
    }

    public String readResponse() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
