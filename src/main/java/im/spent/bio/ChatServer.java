package im.spent.bio;

import im.spent.constant.SocketConstant;

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

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }

    public ChatServer() {
        this.connectedClients = new HashMap<Integer, Writer>();
    }

    public synchronized void addClient(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.connectedClients.put(port, bufferedWriter);
            System.out.println("客户端[" + port + "]已连接到服务器");
        }
    }

    public synchronized void removeClient(Socket socket) throws IOException {
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

    public boolean readyToExit(String msg) {
        return SocketConstant.EXIT.equals(msg);
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(SocketConstant.DEFAULT_PORT);
            System.out.println("服务器启动于: " + SocketConstant.DEFAULT_PORT);

            while (true) {
                Socket socket = this.serverSocket.accept();
                new Thread(new ChatHandler(this, socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    public void close() {
        if (this.serverSocket != null) {
            try {
                this.serverSocket.close();
                System.out.println("服务器关闭");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
