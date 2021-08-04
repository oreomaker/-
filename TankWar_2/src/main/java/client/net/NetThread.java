package client.net;

import client.view.LoginFrame;
import client.view.OperatingFrame;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;

public class NetThread extends Thread{
    LoginFrame loginFrame;
    OperatingFrame operatingFrame;
    private String userName;
    private Vector<String > players=new Vector<>();
    BufferedWriter writer;
    BufferedReader reader;

    public NetThread(BufferedWriter writer,BufferedReader reader) {
        this.reader = reader;
        this.writer = writer;
    }

    public Vector<String> getPlayers() {
        return players;
    }

    public String getUserName() {
        return userName;
    }

    public void setFrame(LoginFrame loginFrame){
        this.loginFrame=loginFrame;
    }

    @Override
    public void run() {
        super.run();
        while(true){
            try {
                String message = reader.readLine();
                dealMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void dealMessage(String message){//接收server传来的信息并处理
        String[] strings=message.split("\\|");
        switch (strings[0]){
            case "move"://move|name|x|y|dir
                if (!strings[1].equals(userName)&&operatingFrame!=null){
                    operatingFrame.getGamePanel().p2Movement(strings[2],strings[3],strings[4]);
                }
                break;
            case "bullet"://bullet|name|x|y|dir
                if (!strings[1].equals(userName)){
                    operatingFrame.getGamePanel().P2Bullet(strings[2],strings[3],strings[4]);
                }
                break;
            case "supply"://supply|x|y
                operatingFrame.getGamePanel().createSupply(Integer.valueOf(strings[1]),Integer.valueOf(strings[2]));
                break;
            case "end":
                int ok=1;
                ok=JOptionPane.showConfirmDialog(this.operatingFrame,strings[1]+"赢了！\n点击确定结束","游戏结束",JOptionPane.PLAIN_MESSAGE);
                if (ok==0){
                    try {
                        sendMessage("backLogin|"+userName+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    loginFrame.setVisible(true);
                    operatingFrame.setVisible(false);
                }
                break;
            case "success":
                loginFrame.showSuccessFrame(Integer.parseInt(strings[1]),Integer.parseInt(strings[2]));
                break;
            case "ask":
                //生成对应的弹窗提示
                loginFrame.askNotice(strings[1]);
                players.add(strings[1]);
                break;
            case "accept":
                //生成同意弹框
                loginFrame.resultNotice(1);
                break;
            case "reject":
                //生成拒绝弹框
                if (strings[1].equals("reject")){
                    loginFrame.resultNotice(0);
                }else {
                    loginFrame.resultNotice(2);
                }
                break;
            case "create":
                //创建operatingPanel，关闭logInFrame
                createOperatingFrame();
                break;
            case "name":
                loginFrame.setNameBox(strings.length,strings);
                break;
            case "invalid":
                //新建弹窗提示
                loginFrame.invalidNotice();
                break;
            case "signup":
                if(strings[1].equals("repeat")){
                    JOptionPane.showMessageDialog(loginFrame,"用户名已注册");
                } else{
                    JOptionPane.showMessageDialog(loginFrame,"成功注册，请登录");
                }
                break;
            case "relog":
                JOptionPane.showMessageDialog(loginFrame,"用户名或密码错误，请重新登陆或注册");
                break;
            case "login":
                userName=strings[1];
                loginFrame.logIn();
                break;
        }
    }

    public void sendMessage(String message) throws IOException {
        //向服务器写出信息
        writer.write(message);
        writer.flush();
    }

    public void createOperatingFrame(){
        loginFrame.setVisible(false);
        operatingFrame=new OperatingFrame(this);
    }
}
