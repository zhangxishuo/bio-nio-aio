import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        // 初始化
        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = null;

        try {
            // 创建ServerSocket实例，绑定端口
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("启动服务器，运行于端口: " + DEFAULT_PORT);

            // 等待客户端连接
            Socket socket = serverSocket.accept();
            System.out.println("客户端[" + socket.getPort() + "]连接成功!");

            // 构造读写对象
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String msg = reader.readLine();
            if (msg != null) {
                System.out.println("客户端[" + socket.getPort() + "]发送消息: " + msg);

                // 发送消息，发送所有缓冲区数据
                writer.write("服务器: " + msg + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
