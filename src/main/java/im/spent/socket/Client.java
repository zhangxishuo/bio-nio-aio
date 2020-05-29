package im.spent.socket;

import im.spent.constant.SocketConstant;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        Socket socket;
        BufferedWriter writer = null;
        try {
            socket = new Socket(SocketConstant.HOST, SocketConstant.DEFAULT_PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                String input = consoleReader.readLine();
                writer.write(input);
                writer.newLine();
                writer.flush();

                String msg = reader.readLine();
                System.out.println(msg);

                if (SocketConstant.EXIT.equals(input)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    System.out.println("关闭Socket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
