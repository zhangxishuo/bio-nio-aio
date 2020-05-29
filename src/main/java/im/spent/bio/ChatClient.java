package im.spent.bio;

import im.spent.constant.SocketConstant;

import java.io.*;
import java.net.Socket;

public class ChatClient {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }

    public void send(String msg) throws IOException {
        if (!socket.isOutputShutdown()) {
            writer.write(msg);
            writer.newLine();
            writer.flush();
        }
    }

    public String receive() throws IOException {
        String msg = null;
        if (!socket.isInputShutdown()) {
            msg = reader.readLine();
        }
        return msg;
    }

    public boolean readyToExit(String msg) {
        return SocketConstant.EXIT.equals(msg);
    }

    public void start() {
        try {
            this.socket = new Socket(SocketConstant.HOST, SocketConstant.DEFAULT_PORT);

            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            new Thread(new UserInputHandler(this)).start();

            String msg;
            while ((msg = this.receive()) != null) {
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    public void close() {
        if (writer != null) {
            try {
                writer.close();
                System.out.println("客户端关闭");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
