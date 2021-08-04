package GamePanel;

import GamePanel.maps.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

//实现简单的坦克移动界面
public class GamePanel extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(50,this);
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    GameMap gameMap;
    //TODO:need to change it to p1 and p2
    Tank p1,p2;
    boolean isOver;

    public GamePanel() {
        init();
        this.setFocusable(true);
        this.addKeyListener(this);
        this.timer.start();
    }

    public void init(){
        gameMap = new GameMap();
        //TODO:need to change it to p1 and p2
        p1 = new Tank(25,25,true,gameMap);
        p2 = new Tank(450,450,false,gameMap);
        isOver = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint the map
        g.setColor(Color.WHITE);
        g.fillRect(0,0,500,500);
        int[][] map1 = gameMap.getMap();
        for(int i = 0; i < 20; ++i){
            for(int j = 0; j < 20; ++j){
                if(map1[i][j] == 1){
                    g.setColor(Color.GRAY);
                    g.fillRect(j*25,i*25,25,25);
                }
            }
        }
        //paint the tank
        int direction1 = p1.direction;
        switch(direction1){
            case Tank.MOVE_UP:
                g.setColor(Color.BLACK);
                //左履带
                g.fillRect(p1.getX(), p1.getY(), 7,25);
                //右履带
                g.fillRect(p1.getX()+18,p1.getY(),7,25);
                g.setColor(Color.BLUE);
                //炮塔
                g.fillRect(p1.getX()+7,p1.getY()+10,11,15);
                //炮管
                g.fillRect(p1.getX()+11,p1.getY(),3,10);
                break;
            case Tank.MOVE_DOWN:
                g.setColor(Color.BLACK);
                //左履带
                g.fillRect(p1.getX(), p1.getY(), 7,25);
                //右履带
                g.fillRect(p1.getX()+18,p1.getY(),7,25);
                g.setColor(Color.BLUE);
                //炮塔
                g.fillRect(p1.getX()+7,p1.getY(),11,15);
                //炮管
                g.fillRect(p1.getX()+11,p1.getY()+15,3,10);
                break;
            case Tank.MOVE_LEFT:
                g.setColor(Color.BLACK);
                //左履带
                g.fillRect(p1.getX(), p1.getY(), 25,7);
                //右履带
                g.fillRect(p1.getX(),p1.getY()+18,25,7);
                g.setColor(Color.BLUE);
                //炮塔
                g.fillRect(p1.getX()+10,p1.getY()+7,15,11);
                //炮管
                g.fillRect(p1.getX(),p1.getY()+11,10,3);
                break;
            case Tank.MOVE_RIGHT:
                g.setColor(Color.BLACK);
                //左履带
                g.fillRect(p1.getX(), p1.getY(), 25,7);
                //右履带
                g.fillRect(p1.getX(),p1.getY()+18,25,7);
                g.setColor(Color.BLUE);
                //炮塔
                g.fillRect(p1.getX(),p1.getY()+7,15,11);
                //炮管
                g.fillRect(p1.getX()+15,p1.getY()+11,10,3);
                break;
            default:
                break;
        }
        int direction2 = p2.direction;
        switch(direction2){
            case Tank.MOVE_UP:
                g.setColor(Color.BLACK);
                //左履带
                g.fillRect(p2.getX(), p2.getY(), 7,25);
                //右履带
                g.fillRect(p2.getX()+18,p2.getY(),7,25);
                g.setColor(Color.RED);
                //炮塔
                g.fillRect(p2.getX()+7,p2.getY()+10,11,15);
                //炮管
                g.fillRect(p2.getX()+11,p2.getY(),3,10);
                break;
            case Tank.MOVE_DOWN:
                g.setColor(Color.BLACK);
                //左履带
                g.fillRect(p2.getX(), p2.getY(), 7,25);
                //右履带
                g.fillRect(p2.getX()+18,p2.getY(),7,25);
                g.setColor(Color.RED);
                //炮塔
                g.fillRect(p2.getX()+7,p2.getY(),11,15);
                //炮管
                g.fillRect(p2.getX()+11,p2.getY()+15,3,10);
                break;
            case Tank.MOVE_LEFT:
                g.setColor(Color.BLACK);
                //左履带
                g.fillRect(p2.getX(), p2.getY(), 25,7);
                //右履带
                g.fillRect(p2.getX(),p2.getY()+18,25,7);
                g.setColor(Color.RED);
                //炮塔
                g.fillRect(p2.getX()+10,p2.getY()+7,15,11);
                //炮管
                g.fillRect(p2.getX(),p2.getY()+11,10,3);
                break;
            case Tank.MOVE_RIGHT:
                g.setColor(Color.BLACK);
                //左履带
                g.fillRect(p2.getX(), p2.getY(), 25,7);
                //右履带
                g.fillRect(p2.getX(),p2.getY()+18,25,7);
                g.setColor(Color.RED);
                //炮塔
                g.fillRect(p2.getX(),p2.getY()+7,15,11);
                //炮管
                g.fillRect(p2.getX()+15,p2.getY()+11,10,3);
                break;
            default:
                break;
        }
        //paint the bullet
        for (Bullet bullet: bullets) {
            g.setColor(Color.BLACK);
            g.fillRect(bullet.getX(), bullet.getY(), 3,3);
        }
        //paint the notice
        g.setColor(Color.BLUE);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("本机：",550,50);
        for (int i = 0; i < p1.blood;++i){
            g.fillRect(550+12*i,60,10,10);
        }
        g.setColor(Color.RED);
        g.drawString("对手：",550,100);
        for (int j = 0; j < p2.blood;++j){
            g.fillRect(550+12*j,110,10,10);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isOver){
            bullets.removeIf(bullet -> (!bullet.move() || bullet.isHit(p1,p2)));
            repaint();
        }
        //TODO：游戏状态判定
        if(p1.blood == 0 || p2.blood == 0){
            isOver = true;
            init();

        }
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
            p1.moveUp();
        } else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            p1.moveDown();
        } else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            p1.moveLeft();
        } else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
            p1.moveRight();
        } else if(key == KeyEvent.VK_SPACE){
            int direction = p1.direction;
            switch(direction){
                case Tank.MOVE_UP:
                    bullets.add(new Bullet(p1.getX()+11, p1.getY()-3, direction,gameMap));
                    break;
                case Tank.MOVE_DOWN:
                    bullets.add(new Bullet(p1.getX()+11, p1.getY()+25, direction,gameMap));
                    break;
                case Tank.MOVE_LEFT:
                    bullets.add(new Bullet(p1.getX()-3, p1.getY()+11, direction,gameMap));
                    break;
                case Tank.MOVE_RIGHT:
                    bullets.add(new Bullet(p1.getX()+25, p1.getY()+11, direction,gameMap));
                    break;
                default:
                    break;
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
