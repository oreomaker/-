package NetManager.Client;

import java.io.*;
import java.net.Socket;

public class NetThread extends Thread{

    BufferedWriter writer;
    BufferedReader reader;

    public NetThread(BufferedWriter writer,BufferedReader reader) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        super.run();
        while(true){
            try {
                String message = reader.readLine();
                //test
                System.out.println(message);
                dealMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void dealMessage(String message){
        if(message.startsWith("login")){
            String[] string = message.split("\\|");
        } else if(message.startsWith("canPlay")){
            //如何设置？
        }else if(message.startsWith("ask")){

        } else if(message.startsWith("accept")){

        } else if(message.startsWith("reject")){

        } else if(message.startsWith("playWith")){

        } else if(message.startsWith("move")){

        }
    }

    public void sendMessage(String message) throws IOException {
        //能向服务器写出
        System.out.println(message);
        writer.write(message);
        writer.flush();
    }
}
