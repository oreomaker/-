package client.view;

import client.view.Data;
import client.view.Music;
import client.view.Starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Zhang Hao
 *
 * */
public class MainPanel extends JPanel implements KeyListener, ActionListener {
    private Starter starter;
    private int selectedIndex;
    private Timer timer = new Timer(20,this);
    private boolean isFirstEnter = false;

    public MainPanel(Starter starter) {
        this.starter = starter;
        this.setBounds(0,0,500,600);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                if(150<x && x<340 && y>300 && y<379){
                    selectedIndex = 0;
                    play();
                    Music.playMusic("fire.wav");
                }
                if(150<x && x<340 && y>400 && y<479){
                    selectedIndex = 1;
                    exit();
                    Music.playMusic("fire.wav");
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0,0,500,600);
        g.setColor(Color.DARK_GRAY);
        switch (selectedIndex){
            case 0:
                g.fillRect(150,300,190,79);
                break;
            case 1:
                g.fillRect(150,400,190,79);
                break;
        }
        Data.hello.paintIcon(this,g,50,50);
        Data.play.paintIcon(this,g,150,300);
        Data.exit.paintIcon(this,g,150,400);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> {
                selectedIndex = (selectedIndex+1) % 2;
                Music.playMusic("fire.wav");
            }
            case KeyEvent.VK_DOWN -> {
                selectedIndex = (selectedIndex+3) % 2;
                Music.playMusic("fire.wav");
            }
            case KeyEvent.VK_ENTER -> {
                if(!isFirstEnter){
                    if(selectedIndex == 0){
                        play();
                    } else{
                        exit();
                    }
                } else isFirstEnter = false;
            }
        }
    }

    public void play(){
        if(starter.isMainPanelEnabled()){
            isFirstEnter = true;
            starter.setMainPanelEnabled(false);
            starter.setSubPanelEnable(true);
            starter.getCardLayout().show(starter.getCenterPanel(),"sub");
        }
    }

    public void exit(){
        if(starter.isMainPanelEnabled()){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        timer.start();
    }
}
