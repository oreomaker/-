package NetManager.Client;

import javax.swing.*;
import java.awt.*;

public class OperatingPanel extends JPanel {
    JLabel nameLabel;
    JTextField nameField;
    JButton logButton;
    JButton quitButton;
    JButton playButton;

    public OperatingPanel() {
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20,20,40,20);

        nameField = new JTextField();
        nameField.setBounds(20,40,40,20);

        logButton = new JButton("Log In");
        logButton.setBounds(20,60,40,20);

        quitButton = new JButton("Quit");
        quitButton.setBounds(20,80,40,20);
        playButton = new JButton("Play");
        playButton.setBounds(20,100,40,20);

        this.add(nameLabel);
        this.add(nameField);
        this.add(logButton);
        this.add(quitButton);
        this.add(playButton);
        this.setBounds(0,0,400,300);
    }

    public static void main(String[] args) {
        var frame = new JFrame();
        frame.add(new OperatingPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(100,200,400,300);
        frame.setVisible(true);
    }
}
