package NetManager.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

public class Server {
    private ServerSocket serverSocket;
    private static Vector<String> players = new Vector<>();

    public Server() {
        try {
            serverSocket = new ServerSocket(4400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try{
            while (true) {
                Socket socket = server.serverSocket.accept();
                //处理多个用户连接
                if(socket.isConnected()){
                    System.out.println("A new thread");
                }
                Thread thread = new Thread(new NetManager(socket));
                thread.start();
            }
        } catch (SocketException exception){
            System.out.println("a user log out");
        } catch (IOException e){
            //ignore
        }
    }

    static class NetManager implements Runnable{
        private Socket socket;
        BufferedReader reader;
        BufferedWriter writer;

        public NetManager(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                while(true){
                    String message = reader.readLine();
                    dealMessage(message);
                }
            } catch (SocketException exception){
                //TODO:need to modify
                System.out.println("a user log out");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void dealMessage(String message) throws IOException {
            if(message.startsWith("login")){
                String[] strings = message.split("\\|");
                players.add(strings[1]);
                //如何告知客户端所有用户？
                writer.write("canPlay\n");
                writer.flush();
                System.out.println(strings[1]+"加入");
            } else if(message.startsWith("play")){

            }
            //TODO:用户退出前处理
        }
    }
}
