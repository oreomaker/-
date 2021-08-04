package NetManager.Client;

import GamePanel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public Client() {
        try {
            this.socket = new Socket("127.0.0.1",4400);
            if(socket!=null){
                this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                NetThread netThread = new NetThread(writer,reader);
                netThread.start();
                LoginFrame loginFrame = new LoginFrame(netThread);
                loginFrame.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
