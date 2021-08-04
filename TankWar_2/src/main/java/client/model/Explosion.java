package client.model;

import client.view.GamePanel;

import java.awt.*;

public class Explosion {
    private int x, y;//爆炸的坐标
    private GamePanel gamePanel;
    private boolean live = true;//爆炸的生命

    public Explosion(int x, int y, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int step = 0;//播放图片的计数器
    private static boolean init = false;//在正式画出爆炸之前先在其他地方画出一次爆炸, 确保爆炸的图片加入到内存中
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] images = {
            tk.getImage(Explosion.class.getClassLoader().getResource("sample/explode1.png")),
            tk.getImage(Explosion.class.getClassLoader().getResource("sample/explode2.png")),
            tk.getImage(Explosion.class.getClassLoader().getResource("sample/explode3.png")),
            tk.getImage(Explosion.class.getClassLoader().getResource("sample/explode4.png")),
            tk.getImage(Explosion.class.getClassLoader().getResource("sample/explode5.png")),
            tk.getImage(Explosion.class.getClassLoader().getResource("sample/explode6.png")),
            tk.getImage(Explosion.class.getClassLoader().getResource("sample/explode7.png"))
    };

    public void drawExplosion(Graphics g) {
        if(!init){//先在其他地方画一次爆炸
            for(int i = 0; i < images.length; i++){
                g.drawImage(images[i], -100, -100, null);
            }
            init = true;
        }
        if(!live) {//爆炸炸完了就从容器移除
            gamePanel.getExplosions().remove(this);
            return;
        }
        if(step == images.length){//把爆炸数组中的图片都画一次
            live = false;
            step = 0;
            return;
        }
        g.drawImage(images[step++], x, y, null);
    }
}
