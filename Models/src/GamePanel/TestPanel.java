package GamePanel;

import javax.swing.*;

public class TestPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.add(new GamePanel());
        frame.setBounds(100,200,700,550);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
