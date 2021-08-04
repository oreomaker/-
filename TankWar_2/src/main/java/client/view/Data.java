package client.view;

import javax.swing.*;
import java.net.URL;

public class Data {
    public static URL helloUrl = Data.class.getResource("/sample/hello.jpg");
    public static ImageIcon hello = new ImageIcon(helloUrl);
    public static URL playUrl = Data.class.getResource("/sample/play.png");
    public static ImageIcon play = new ImageIcon(playUrl);
    public static URL exitUrl = Data.class.getResource("/sample/exit.png");
    public static ImageIcon exit = new ImageIcon(exitUrl);
}
