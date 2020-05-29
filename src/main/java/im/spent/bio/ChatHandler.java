package im.spent.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatHandler implements Runnable {

    private ChatServer server;
    private Socket socket;

    public ChatHandler(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        try {
            this.server.addClient(this.socket);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msg;
            while ((msg = reader.readLine()) != null) {
                String forward = "客户端[" + socket.getPort() + "]: " + msg;
                System.out.println(forward);
                this.server.forwardMessage(this.socket, forward);

                if (this.server.readyToExit(msg)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.server.removeClient(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
