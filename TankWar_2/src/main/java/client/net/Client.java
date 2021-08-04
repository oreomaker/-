package client.net;

import client.view.LoginFrame;
import client.view.Starter;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Starter starter;

    public Client(Starter starter) {
        this.starter = starter;
        try {
            this.socket = new Socket("192.168.43.123",5555);
            //192.168.43.123
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            NetThread netThread = new NetThread(writer,reader);
            netThread.start();
            LoginFrame loginFrame = new LoginFrame(netThread, starter);
            netThread.setFrame(loginFrame);
            loginFrame.setVisible(true);
        } catch(ConnectException exception){
            JOptionPane.showMessageDialog(starter,"请检查网络连接,重新启动","连接失败",JOptionPane.OK_OPTION);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
