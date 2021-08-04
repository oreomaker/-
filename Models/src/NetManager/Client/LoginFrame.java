package NetManager.Client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LoginFrame extends JFrame {
    JLabel nameLabel;
    JTextField nameField;
    JButton logButton;
    JButton quitButton;
    JPanel middlePanel;
    JButton playButton;
    JComboBox nameBox;
    NetThread thread;

    public LoginFrame(NetThread thread) {
        this.thread = thread;

        this.setBounds(new Rectangle(400,300));
        nameLabel = new JLabel("输入用户名：");
        nameField = new JTextField();
        logButton = new JButton("Log in");
        playButton = new JButton("Play");
        middlePanel = new JPanel();
        quitButton = new JButton("Quit");
        nameBox = new JComboBox();

        playButton.setEnabled(false);

        this.setLayout(new GridLayout(6,1));
        middlePanel.setLayout(new GridLayout(1,2));
        middlePanel.add(logButton);
        middlePanel.add(playButton);
        this.add(nameLabel);
        this.add(nameField);
        this.add(nameBox);
        this.add(middlePanel);
        this.add(quitButton);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        playMusic();
        //this.setVisible(true);

        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                nameField.setText("");
                if(name.equals("")||name.contains("|")){

                } else{
                    try {
                        thread.sendMessage("login|"+name+"\n");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playWith = nameBox.getName();
                if(playWith.equals("")){
                    //未选择
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //write and exit
            }
        });
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    public void playMusic() {
        String musicLocation = new String("u8b9y-vadlx.wav");
        try {
            File musicPath = new File(musicLocation);

            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {

            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void canPlay(){
        logButton.setEnabled(false);
        playButton.setEnabled(true);
    }
}
