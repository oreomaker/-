package client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectionPanel extends JPanel implements ActionListener, KeyListener {
    Starter starter;
    private int selectedIndex;
    private boolean isFirstEnter=true;
    private Timer timer=new Timer(20,this);

    public SelectionPanel(Starter starter) {
        this.starter=starter;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x=e.getX();
                int y=e.getY();

                if (150<x && x<350 && y>100 && y<175){
                    easyMode();
                    Music.playMusic("fire.wav");
                    selectedIndex=0;
                }
                if (150<x && x<350 && y>200 && y<275){
                    plainMode();
                    Music.playMusic("fire.wav");
                    selectedIndex = 1;
                }
                if (150<x && x<350 && y>350 && y<425){
                    returnToSub();
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
        g.drawString("简单模式",150,150);
        g.drawString("一般模式",150,250);
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
        int key=e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP->{
                selectedIndex = (selectedIndex+2) % 3;
                Music.playMusic("fire.wav");
            }
            case KeyEvent.VK_DOWN -> {
                selectedIndex=(selectedIndex+4) % 3;
                Music.playMusic("fire.wav");
            }
            case KeyEvent.VK_ENTER ->{
                if (!isFirstEnter){
                    switch(selectedIndex){
                        case 0:
                            easyMode();
                            break;
                        case 1:
                            plainMode();
                            break;
                        case 2:
                            returnToSub();
                            break;
                    }
                }else if(starter.isSelectionPanelEnable()) isFirstEnter=false;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void easyMode(){
        if (starter.isSelectionPanelEnable()){
            OperatingFrame operatingFrame=new OperatingFrame(this,1);
            starter.setVisible(false);
            operatingFrame.setVisible(true);
        }
    }

    public void plainMode(){
        if (starter.isSelectionPanelEnable()){
            OperatingFrame operatingFrame=new OperatingFrame(this,2);
            starter.setVisible(false);
            operatingFrame.setVisible(true);
        }
    }

    public void returnToSub(){
        if(starter.isSelectionPanelEnable()){
            isFirstEnter=true;
            starter.setSelectionPanelEnable(false);
            starter.setSubPanelEnable(true);
            starter.getCardLayout().show(starter.getCenterPanel(),"sub");
        }
    }
}
