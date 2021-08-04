package client.model;

import java.awt.*;

public class Bullet {
    int x,y;
    int direction;
    GameMap map;
    public static final int MOVE_UP = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_LEFT = 3;
    public static final int MOVE_RIGHT = 4;

    public Bullet(int x, int y, int direction, GameMap map) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.map = map;
    }

    //TODO:子弹与墙相遇需要消失
    public boolean move(){
        int[][] judgeMap = map.getMap();
        if(judgeMap[y/25][x/25] == 1 || judgeMap[y/25][(x+3)/25] == 1 || judgeMap[(y+3)/25][x/25] == 1 || judgeMap[(y+3)/25][(x+3)/25] == 1){
            return false;
        }
        switch (direction) {
            case MOVE_UP:
                y -= 15;
                break;
            case MOVE_DOWN:
                y += 15;
                break;
            case MOVE_LEFT:
                x -= 15;
                break;
            case MOVE_RIGHT:
                x += 15;
                break;
            default:
                break;
        }
        return true;
    }

    public boolean isHit(Tank p1,Tank p2){
        Rectangle bulletRec = new Rectangle(x,y,3,3);
        Rectangle tankRec1 = new Rectangle(p1.getX(),p1.getY(),25,25);
        Rectangle tankRec2 = new Rectangle(p2.getX(),p2.getY(),25,25);
        if(tankRec1.contains(bulletRec)){
            p1.getHit();
            return true;
        } else if(tankRec2.contains(bulletRec)){
            p2.getHit();
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
