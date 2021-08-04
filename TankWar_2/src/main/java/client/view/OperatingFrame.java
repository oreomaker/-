package client.view;

import client.net.NetThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatingFrame extends JFrame {//OperatingFrame要作为沟通GamePanel和netThread的工具
    NetThread netThread;
    GamePanel gamePanel;
    SelectionPanel selectionPanel;
    //人机对战构造
    public OperatingFrame(SelectionPanel selectionPanel,int mode){
        this.selectionPanel=selectionPanel;
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("menu1");
        JMenu menu2 = new JMenu("menu2");
        JMenuItem item1 = new JMenuItem("指南");
        JMenuItem item2 = new JMenuItem("关于");
        menu1.add(item1);
        menu2.add(item2);
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.setBounds(0,0,700,20);
        this.setJMenuBar(menuBar);

        gamePanel=new GamePanel(this,mode);
        gamePanel.setBounds(0,50,700,500);
        this.add(gamePanel);
        this.setBounds(100,200,700,560);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GuideFrame();
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutFrame();
            }
        });
    }
    //双人对战构造
    public OperatingFrame(NetThread netThread) {
        this.netThread=netThread;

        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("menu1");
        JMenu menu2 = new JMenu("menu2");
        JMenuItem item1 = new JMenuItem("指南");
        JMenuItem item2 = new JMenuItem("关于");
        menu1.add(item1);
        menu2.add(item2);
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.setBounds(0,0,700,20);
        this.setJMenuBar(menuBar);

        gamePanel=new GamePanel(this);
        gamePanel.setBounds(0,50,700,500);
        this.add(gamePanel);
        this.setBounds(100,200,700,560);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GuideFrame();
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutFrame();
            }
        });
    }
    //TODO:获取GamePanel传来的用户操作信息，将其传给Thread
    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
