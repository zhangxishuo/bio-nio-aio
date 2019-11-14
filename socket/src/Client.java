import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        // 初始化
        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_PORT = 8888;
        Socket socket = null;

        try {
            // 创建Socket实例，连接服务器
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_PORT);

            // 构造读写对象
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 等待用户输入信息
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String input = consoleReader.readLine();

            // 向服务器发送信息
            writer.write(input + "\n");
            writer.flush();

            // 读取服务器返回消息
            String msg = reader.readLine();
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
