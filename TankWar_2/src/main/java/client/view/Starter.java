package client.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Zhang Hao
 *
 * 游戏客户端的启动器
 *
 * 该类主方法会创建一个开始窗口，可选择开始游戏或退出程序，并播放背景音乐.
 * */
public class Starter extends JFrame {
    private CardLayout cardLayout;
    private MainPanel mainPanel;
    private SubPanel subPanel;
    private SelectionPanel selectionPanel;
    private JPanel centerPanel;
    private boolean isMainPanelEnabled;
    private boolean isSubPanelEnable;
    private boolean isSelectionPanelEnable;

    public Starter() throws HeadlessException {
        cardLayout = new CardLayout();
        mainPanel = new MainPanel(this);
        subPanel = new SubPanel(this);
        centerPanel = new JPanel(cardLayout);
        selectionPanel = new SelectionPanel(this);
        centerPanel.add(mainPanel,"main");
        centerPanel.add(subPanel,"sub");
        centerPanel.add(selectionPanel,"select");
        this.add(centerPanel);
        cardLayout.show(centerPanel,"main");

        isMainPanelEnabled = true;
        isSubPanelEnable = false;
        isSelectionPanelEnable = false;

        this.addKeyListener(mainPanel);
        this.addKeyListener(subPanel);
        this.addKeyListener(selectionPanel);

        playMusic();

        this.setBounds(200,100,500,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * @return boolean
     *
     * 方法用于检查主面板是否处于相应状态，以避免监听器同时相应的情况.
     * */
    public boolean isMainPanelEnabled() {
        return isMainPanelEnabled;
    }

    public void setMainPanelEnabled(boolean mainPanelEnabled) {
        isMainPanelEnabled = mainPanelEnabled;
    }

    public boolean isSubPanelEnable() {
        return isSubPanelEnable;
    }

    public void setSubPanelEnable(boolean subPanelEnable) {
        isSubPanelEnable = subPanelEnable;
    }

    public boolean isSelectionPanelEnable() {
        return isSelectionPanelEnable;
    }

    public void setSelectionPanelEnable(boolean selectionPanelEnable) {
        isSelectionPanelEnable = selectionPanelEnable;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }



    public void playMusic() {
        String musicLocation = new String("sample/u8b9y-vadlx.wav");
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

    public static void main(String[] args) {
        var frame = new Starter();
    }
}
