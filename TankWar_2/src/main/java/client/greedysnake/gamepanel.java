package client.greedysnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class gamepanel extends JPanel implements KeyListener, ActionListener {
    int length;
    int[] snakex = new int[600];
    int[] snakey = new int[500];
    String direction;//将方向转为变量，方便后面判断
    Boolean isstart = false;
    Timer timer = new Timer(100,this);//定时器，需要启动
    int foodx,foody;
    Random random = new Random();
    Boolean isFailed = false;
    int score;

    public gamepanel(){
        init();
        this.setFocusable(true);
        this.addKeyListener(this);
        this.timer.start();
    }

    public void init(){
        length = 3;
        direction = "R";
        snakex[0]=100; snakey[0]=100;
        snakex[1]=75; snakey[1]=100;
        snakex[2]=50; snakey[2]=100;
        foodx = 25+25*random.nextInt(34);
        foody = 75+25*random.nextInt(24);
        score = 0;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        g.fillRect(25,75,850,600);

        //head
        if(direction.equals("R")){
            GreedySnakeData.right.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(direction.equals("L")){
            GreedySnakeData.left.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(direction.equals("U")){
            GreedySnakeData.up.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(direction.equals("D")){
            GreedySnakeData.down.paintIcon(this,g,snakex[0],snakey[0]);
        }

        //body
        for (int i = 1; i < length; i++) {
            GreedySnakeData.body.paintIcon(this,g,snakex[i],snakey[i]);
        }
        //food
        GreedySnakeData.food.paintIcon(this,g,foodx,foody);

        //sore
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度："+length,400,30);
        g.drawString("分数: "+score,400,50);
//暂停和结束均以boolean变量判断
        //pause
        if(!isstart){
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始",300,300);
        }

        //Failure notice
        if(isFailed){
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏结束，按下空格重新开始",200,300);
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    //key listener
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_SPACE){
            if(isFailed){
                isFailed = false;
                init();
            }else{
                isstart = !isstart;
            }
            repaint();//刷新
        }
        //direction change
        if(keyCode == KeyEvent.VK_LEFT && !Objects.equals(direction, "R")){
            direction = "L";
        }else if(keyCode == KeyEvent.VK_RIGHT && !Objects.equals(direction, "L")){
            direction = "R";
        }else if(keyCode == KeyEvent.VK_UP && !Objects.equals(direction, "D")){
            direction = "U";
        }else if(keyCode == KeyEvent.VK_DOWN && !Objects.equals(direction, "U")){
            direction = "D";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //timer
    @Override
    public void actionPerformed(ActionEvent e) {
        //game start
        if(isstart&&(!isFailed)){
            //跟进
            for (int i = length - 1; i > 0; i--) {
                snakex[i] = snakex[i - 1];
                snakey[i] = snakey[i - 1];
            }
            //头部移动
            if(direction.equals("R")) {
                snakex[0] = snakex[0]+25;
                //border judge
                if(snakex[0]>875){
                    snakex[0] = 25;
                }
            }else if(direction.equals("L")){
                snakex[0] = snakex[0]-25;
                //border judge
                if(snakex[0]<25){
                    snakex[0] = 875;
                }
            }else if(direction.equals("U")){
                snakey[0] = snakey[0]-25;
                //border judge
                if(snakey[0]<75){
                    snakey[0] = 650;
                }
            }else if(direction.equals("D")){
                snakey[0] = snakey[0]+25;
                //border judge
                if(snakey[0]>650){
                    snakey[0] = 75;
                }
            }
            //eat
            if(snakex[0]==foodx&&snakey[0]==foody){
                length++;

                score+=10;
                foodx = 25+25*random.nextInt(34);
                foody = 75+25*random.nextInt(24);
            }
            //failure judge
            for(int i=1;i<length;i++){
                if (snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
                    isFailed = true;
                    break;
                }
            }
            repaint();
        }
        timer.start();//时间开始
    }
}