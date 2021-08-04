package client.greedysnake;

import javax.swing.*;
import java.net.URL;

public class GreedySnakeData {
    //头部：上下左右
    public static URL upUrl = GreedySnakeData.class.getResource("/sample/up.png");
    public static URL downUrl = GreedySnakeData.class.getResource("/sample/down.png");
    public static URL leftUrl = GreedySnakeData.class.getResource("/sample/left.png");
    public static URL rightUrl = GreedySnakeData.class.getResource("/sample/right.png");
    public static ImageIcon up = new ImageIcon(upUrl);
    public static ImageIcon down = new ImageIcon(downUrl);
    public static ImageIcon left = new ImageIcon(leftUrl);
    public static ImageIcon right = new ImageIcon(rightUrl);
    //身体
    public static URL bodyUrl = GreedySnakeData.class.getResource("/sample/body.png");
    public static ImageIcon body = new ImageIcon(bodyUrl);
    //食物
    public static URL foodUrl = GreedySnakeData.class.getResource("/sample/food.png");
    public static ImageIcon food = new ImageIcon(foodUrl);
}

