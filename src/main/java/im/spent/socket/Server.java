package im.spent.socket;

import im.spent.constant.SocketConstant;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SocketConstant.DEFAULT_PORT);
            System.out.println("服务器启动于: " + SocketConstant.DEFAULT_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端[" + socket.getPort() + "]发起连接");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String msg;
                while ((msg = reader.readLine()) != null) {
                    System.out.println("客户端[" + socket.getPort() + "]: " + msg);
                    writer.write("服务器: " + msg);
                    writer.newLine();
                    writer.flush();

                    if (SocketConstant.EXIT.equals(msg)) {
                        System.out.println("客户端[" + socket.getPort() + "]断开连接");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("关闭ServerSocket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
