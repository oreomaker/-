package client.view;

import client.model.*;
import client.net.NetThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Vector;

//实现简单的坦克移动界面
public class GamePanel extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(50,this);
    Vector<Bullet> bullets = new Vector<>();
    Vector<Explosion> explosions = new Vector<>();
    Vector<Supply> supplies = new Vector<>();
    GameMap gameMap;
    Tank p1,p2;
    boolean isOver,isPlayBomb,canAddSupply = true;
    OperatingFrame operatingFrame;
    NetThread netThread;
    int gameMode;
    RobotControl robotControl;

    //人机对战构造方法
    public GamePanel(OperatingFrame operatingFrame,int mode){
        this.gameMode=mode;
        this.operatingFrame=operatingFrame;
        robotControl = new RobotControl(this,gameMode);
        init();
        p2.setRobot(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        Music.playMusic("bomb.wav");
        this.timer.start();
    }
    //联机对战构造方法
    public GamePanel(OperatingFrame operatingFrame) {
        this.operatingFrame=operatingFrame;
        this.netThread=operatingFrame.netThread;//实现GamePanel和netThread连接
        init();
        p2.setRobot(false);
        this.setFocusable(true);
        this.addKeyListener(this);
        Music.playMusic("bomb.wav");
        this.timer.start();
    }
    //初始化panel数据
    public void init(){
        if(gameMode == 0){
            gameMap = new GameMap(0);
        } else {
            gameMap = new GameMap(1);
        }
        p1 = new Tank(25,25,true,gameMap);
        p2 = new Tank(450,450,false,gameMap);
        isOver = false;
    }

    public Vector<Bullet> getBullets() {
        return bullets;
    }

    public Vector<Supply> getSupplies() {
        return supplies;
    }

    public Vector<Explosion> getExplosions(){
        return explosions;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Tank getP1() {
        return p1;
    }

    public Tank getP2() {
        return p2;
    }

    public boolean isCanAddSupply() {
        return canAddSupply;
    }

    //解析服务器传入对方坦克移动数据
    public void p2Movement(String  X, String  Y, String  DIRECTION){
        int x=Integer.valueOf(X);
        int y=Integer.valueOf(Y);
        int dir=Integer.valueOf(DIRECTION);

        p2.setX((500-x)-25);
        p2.setY((500-y)-25);
        p2.setP2Direction(dir);
    }

    //解析服务器传入对方炮弹移动数据
    public void P2Bullet(String  X, String  Y, String  DIRECTION){
        int x=Integer.valueOf(X);
        int y=Integer.valueOf(Y);
        int dir=Integer.valueOf(DIRECTION);
        //反向
        if (dir==Bullet.MOVE_UP){
            dir=Bullet.MOVE_DOWN;
        }else if (dir==Bullet.MOVE_DOWN){
            dir=Bullet.MOVE_UP;
        }else if (dir==Bullet.MOVE_LEFT){
            dir=Bullet.MOVE_RIGHT;
        }else if (dir==Bullet.MOVE_RIGHT){
            dir=Bullet.MOVE_LEFT;
        }
        bullets.add(new Bullet(497-x,497-y,dir,gameMap));
        p2.fire();
    }

    //将本机发出的炮弹传给服务器
    public void sendBullet(int x,int y,int direction){
        try {
            netThread.sendMessage("bullet|"+netThread.getUserName()+"|"+x+"|"+y+"|"+direction+"\n");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    //添加Supply对象
    public void createSupply(int x,int y){
        supplies.add(new Supply(x,y));
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
        int direction1 = p1.getDirection();
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
        int direction2 = p2.getDirection();
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
        //paint bullets
        for (Bullet bullet: bullets) {
            g.setColor(Color.BLACK);
            g.fillRect(bullet.getX(), bullet.getY(), 3,3);
        }
        //paint explosion
        for(int i = 0; i < explosions.size();++i){
            Explosion e = explosions.get(i);
            e.drawExplosion(g);
        }
        //paint the supply
        for (Supply supply: supplies) {
            g.setColor(Color.BLACK);
            g.fillRect(supply.getX(),supply.getY(),10,10);
        }
        //paint the notice
        g.setColor(Color.BLUE);
        g.setFont(new Font("黑体",Font.BOLD,18));
        g.drawString("本机xp：",550,50);
        for (int i = 0; i < p1.getBlood();++i){
            g.fillRect(550+12*i,60,10,10);
        }
        g.setColor(Color.RED);
        g.drawString("对手xp：",550,200);
        for (int j = 0; j < p2.getBlood();++j){
            g.fillRect(550+12*j,210,10,10);
        }
        g.setColor(Color.BLACK);
        g.drawString("弹药：",550,90);
        for (int i = 0; i < p1.getAmmunition();++i){
            g.fillRect(550+12*i,100,10,10);
        }
        g.drawString("弹药：",550,240);
        for (int i = 0; i < p2.getAmmunition();++i){
            g.fillRect(550+12*i,250,10,10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isOver && !p2.isRobot()){//联机对战事件
            //子弹移动和击中判断
            for (int i=0; i < bullets.size(); ++i){
                Bullet bullet = bullets.get(i);
                if(bullet.isHit(p1,p2)){
                    explosions.add(new Explosion(bullet.getX()-30,bullet.getY()-30,this));
                    isPlayBomb = true;
                    bullets.remove(i);
                } else if(!bullet.move()){
                    bullets.remove(i);
                }
            }

            for (int i = 0; i < supplies.size(); ++i) {
                Supply supply = supplies.get(i);
                if(supply.canBeSupplied(p1,p2)){
                    canAddSupply = true;
                    supplies.remove(i);
                    try {
                        System.out.println("p1 bullet:"+p1.getAmmunition());
                        System.out.println("p2 bullet:"+p2.getAmmunition());
                        netThread.sendMessage("remove|"+(p1.getAmmunition()+p2.getAmmunition())+"\n");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

            if(isPlayBomb){
                Music.playMusic("bomb.wav");
                isPlayBomb = false;
            }
            repaint();
        } else if (!isOver&&p2.isRobot()){//单人模式事件
            //电脑坦克控制
            if (gameMode==1) {
                robotControl.basicMovement(p1, p2);
            }else if (gameMode==2){
                robotControl.advancedMovement(p1,p2);
            }
            //检查炮弹击中
            for (int i=0; i < bullets.size(); ++i){
                Bullet bullet = bullets.get(i);
                if(bullet.isHit(p1,p2)){
                    explosions.add(new Explosion(bullet.getX()-30,bullet.getY()-30,this));
                    isPlayBomb = true;
                    bullets.remove(i);
                } else if(!bullet.move()){
                    bullets.remove(i);
                }
            }
            //检查补给生成
            if(canAddSupply && p1.getAmmunition()+p2.getAmmunition()<6){
                supplies.add(new Supply(gameMap));
                canAddSupply = false;
            }
            //检查补给获得
            for (int i = 0; i < supplies.size(); ++i) {
                Supply supply = supplies.get(i);
                if(supply.canBeSupplied(p1,p2)){
                    canAddSupply = true;
                    supplies.remove(i);
                }
            }
            //播放爆炸音效
            if(isPlayBomb){
                Music.playMusic("bomb.wav");
                isPlayBomb = false;
            }
            repaint();
        }

        //发送坦克移动数据
        if(gameMode == 0){
            try {
                netThread.sendMessage("move|"+netThread.getUserName()+"|"+p1.getX()+"|"+p1.getY()+"|"+p1.getDirection()+"\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }//将向上移动的信息传给netThread
        }

        //检查游戏是否结束
        if(p1.getBlood() <= 0 || p2.getBlood() <= 0){
            isOver = true;
            if (p2.getBlood()<=0&&gameMode==0){//联机对战
                try {
                    netThread.sendMessage("end|"+netThread.getUserName()+"\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else {//人机对战
                int ok;
                if (p1.getBlood()==0){//失败信息
                    ok=JOptionPane.showConfirmDialog(this,"你输了","游戏结束",JOptionPane.PLAIN_MESSAGE);
                }else {
                    ok=JOptionPane.showConfirmDialog(this,"你赢了","游戏结束",JOptionPane.PLAIN_MESSAGE);
                }
                if (ok==0){
                    operatingFrame.selectionPanel.starter.setVisible(true);
                    operatingFrame.setVisible(false);
                }
            }
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
        } else if(key == KeyEvent.VK_F){//生成子弹
            if(p1.getAmmunition()>0){
                p1.fire();
                int direction = p1.getDirection();
                switch(direction){
                    case Tank.MOVE_UP:
                        if(gameMode == 0) sendBullet(p1.getX()+11,p1.getY()-3, direction);
                        bullets.add(new Bullet(p1.getX()+11, p1.getY()-3, direction,gameMap));
                        break;
                    case Tank.MOVE_DOWN:
                        if(gameMode == 0) sendBullet(p1.getX()+11, p1.getY()+25, direction);
                        bullets.add(new Bullet(p1.getX()+11, p1.getY()+25, direction,gameMap));
                        break;
                    case Tank.MOVE_LEFT:
                        if(gameMode == 0) sendBullet(p1.getX()-3, p1.getY()+11, direction);
                        bullets.add(new Bullet(p1.getX()-3, p1.getY()+11, direction,gameMap));
                        break;
                    case Tank.MOVE_RIGHT:
                        if(gameMode == 0) sendBullet(p1.getX()+25, p1.getY()+11, direction);
                        bullets.add(new Bullet(p1.getX()+25, p1.getY()+11, direction,gameMap));
                        break;
                    default:
                        break;
                }
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
