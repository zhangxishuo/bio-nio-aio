package im.spent.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputHandler implements Runnable {

    private ChatClient client;

    public UserInputHandler(ChatClient client) {
        this.client = client;
    }

    public void run() {
        try {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String input = consoleReader.readLine();
                this.client.send(input);

                if (this.client.readyToExit(input)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
