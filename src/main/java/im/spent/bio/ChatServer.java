package im.spent.bio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    private ServerSocket serverSocket;
    private Map<Integer, Writer> connectedClients;

    public ChatServer() {
        this.connectedClients = new HashMap<Integer, Writer>();
    }

    public void addClient(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.connectedClients.put(port, bufferedWriter);
            System.out.println("客户端[" + port + "]已连接到服务器");
        }
    }

    public void removeClient(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            if (this.connectedClients.containsKey(port)) {
                this.connectedClients.get(port).close();
            }
            this.connectedClients.remove(port);
            System.out.println("客户端[" + port + "]已断开连接");
        }
    }

    public void forwardMessage(Socket socket, String msg) throws IOException {
        for (Map.Entry<Integer, Writer> entry : connectedClients.entrySet()) {
            if (!entry.getKey().equals(socket.getPort())) {
                Writer writer = entry.getValue();
                writer.write(msg);
                writer.flush();
            }
        }
    }
}
