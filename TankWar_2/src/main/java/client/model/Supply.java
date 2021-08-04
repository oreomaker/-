package client.model;

import java.awt.*;
import java.util.Random;

public class Supply {
    GameMap gameMap;
    int x;
    int y;

    public Supply(int x,int y) {
        this.x=x;
        this.y=y;
    }

    public Supply(GameMap gameMap){
        this.gameMap = gameMap;
        int[][] map = gameMap.getMap();
        Random random = new Random();
        x = random.nextInt(500);
        y = random.nextInt(500);
        while(true){
            if(map[y/25][x/25]==1||map[(y+10)/25][x/25]==1||map[y/25][(x+10)/25]==1||map[(y+10)/25][(x+10)/25]==1){
                x = random.nextInt(500);
                y = random.nextInt(500);
            } else break;
        }
    }

    public boolean canBeSupplied(Tank p1,Tank p2){//用于判断坦克是否吃到补给，并补满弹药，返回布尔值以提示可以生成补给
        Rectangle supplyRec = new Rectangle(x,y,10,10);
        Rectangle tankRec1 = new Rectangle(p1.getX(),p1.getY(),25,25);
        Rectangle tankRec2 = new Rectangle(p2.getX(),p2.getY(),25,25);
        if(tankRec1.contains(supplyRec)){
            p1.getSupplied();
            return true;
        } else if(tankRec2.contains(supplyRec)){
            p2.getSupplied();
            return true;
        }
        return false;
    }
    public boolean removeSupply(Tank p1,Tank p2){//判断坦克有没有吃到supply
        Rectangle supplyRec = new Rectangle(x,y,10,10);
        Rectangle tankRec1 = new Rectangle(p1.getX(),p1.getY(),25,25);
        Rectangle tankRec2 = new Rectangle(p2.getX(),p2.getY(),25,25);
        if(tankRec1.contains(supplyRec)||tankRec2.contains(supplyRec)){
            return true;
        }
        return false;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
