package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author Du Shimao
 *
 * 该类为服务器启动类.
 *
 * 主方法启动服务器进程，不断接收客户端发送请求，创建专门处理客户端连接和信息处理的NetManager进程.
 * players内为当前登录用户的集合，netManagers为网络线程集合，threadHashMap实现了将用户名和NetManager线程的映射，
 * playergroup将对战玩家存储在Player对象中.
 * @see NetManager
 * @see DBUtil
 * @see Players
 * */
public class Server {
    private ServerSocket serverSocket;
    private static DBUtil dbUtil;
    private static Vector<String> players = new Vector<>();
    private static Vector<NetManager> netManagers=new Vector<>();
    private static HashMap<String,NetManager> threadHashMap=new HashMap<>();
    private static Vector<Players> playerGroup=new Vector<>();//用于保存对战中的游戏玩家名，保存形式是：位置|name1|name2,且name1一般为发起者，name2为接受者

    public Server() {
        try {
            serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbUtil = new DBUtil();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try{
            while (true) {
                Socket socket = server.serverSocket.accept();
                //处理多个用户连接
                if(socket.isConnected()){
                    System.out.println("A new thread");
                    NetManager netManager=new NetManager(socket,netManagers.size(),threadHashMap,players,playerGroup, dbUtil);
                    netManagers.add(netManager);
                    Thread thread = new Thread(netManager);
                    thread.start();
                }
            }
        } catch (SocketException exception){
            System.out.println("a client log out");
        } catch (IOException e){
            //ignore
        }
    }
}
