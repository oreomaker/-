package client.greedysnake;

import javax.swing.*;

public class Snake {
    public Snake() {
        JFrame frame=new JFrame("GreedySnake");
        frame.setBounds(10,10,900,720);
        frame.setResizable(false);
        frame.add(new gamepanel());
        frame.setVisible(true);
    }
}