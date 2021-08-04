package client.view;

import client.net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SubPanel extends JPanel implements KeyListener, ActionListener {
    Starter starter;
    int selectedIndex;
    Timer timer = new Timer(20,this);
    boolean isFirstEnter = true;

    public SubPanel(Starter starter) {
        this.starter = starter;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();

                if(150<x && x<350 && y>100 && y<175){
                    pvcPlay();
                    Music.playMusic("fire.wav");
                    selectedIndex = 0;
                }
                if(150<x && x<350 && y>200 && y<275){
                    pvpPlay();
                    Music.playMusic("fire.wav");
                    selectedIndex = 1;
                }
                if(150<x && x<350 && y>350 && y<425){
                    returnToMain();
                    Music.playMusic("fire.wav");
                    selectedIndex = 2;
                }
            }
        });
        this.timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0,0,500,600);

        g.setColor(Color.DARK_GRAY);
        switch (selectedIndex){
            case 0:
                g.fillRect(150,100,200,75);
                break;
            case 1:
                g.fillRect(150,200,200,75);
                break;
            case 2:
                g.fillRect(150,350,200,75);
                break;
        }
        g.setColor(Color.RED);
        g.setFont(new Font("华光综艺_CNKI",Font.BOLD,45));
        g.drawString("人机对战",150,150);
        g.drawString("双人对战",150,250);
        g.drawString("RETURN",150,400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> {
                selectedIndex = (selectedIndex+2) % 3;
                Music.playMusic("fire.wav");
            }
            case KeyEvent.VK_DOWN -> {
                selectedIndex = (selectedIndex+4) % 3;
                Music.playMusic("fire.wav");
            }
            case KeyEvent.VK_ENTER -> {
                if(!isFirstEnter){
                    switch(selectedIndex){
                        case 0:
                            pvcPlay();
                            break;
                        case 1:
                            pvpPlay();
                            break;
                        case 2:
                            returnToMain();
                            break;
                    }
                } else if(starter.isSubPanelEnable())  isFirstEnter=false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void pvcPlay(){
        if (starter.isSubPanelEnable()){
            starter.setSubPanelEnable(false);
            starter.setSelectionPanelEnable(true);
            isFirstEnter = true;
            starter.getCardLayout().show(starter.getCenterPanel(),"select");
        }
    }

    public void pvpPlay(){
        if (starter.isSubPanelEnable()){
            var client = new Client(starter);
            if(client != null) starter.setVisible(false);
        }
    }

    public void returnToMain(){
        if(starter.isSubPanelEnable()){
            isFirstEnter = true;
            starter.setMainPanelEnabled(true);
            starter.getCardLayout().show(starter.getCenterPanel(),"main");
        }
    }
}
