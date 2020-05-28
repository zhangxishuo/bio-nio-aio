package im.spent.socket;

import im.spent.constant.SocketConstant;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SocketConstant.DEFAULT_PORT);
            System.out.println("服务器启动于: " + SocketConstant.DEFAULT_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端[" + socket.getPort() + "]发起连接");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
