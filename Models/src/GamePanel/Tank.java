package GamePanel;

import GamePanel.maps.GameMap;

public class Tank {
    int x;
    int y;
    int blood;
    int direction;
    boolean isHost;
    public static final int MOVE_UP = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_LEFT = 3;
    public static final int MOVE_RIGHT = 4;
    GameMap map;

    public Tank(int x, int y, boolean isHost, GameMap map) {
        this.x = x;
        this.y = y;
        this.isHost = isHost;
        this.map = map;
        blood = 5;
        if(isHost){
            direction = MOVE_DOWN;
        } else{
            direction = MOVE_UP;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBlood() {
        return blood;
    }

    public void moveUp(){
        direction = MOVE_UP;
        judge();
    }

    public void moveDown(){
        direction = MOVE_DOWN;
        judge();
    }

    public void moveLeft(){
        direction = MOVE_LEFT;
        judge();
    }

    public void moveRight(){
        direction = MOVE_RIGHT;
        judge();
    }

    public void judge(){
        int[][] judgeMap = map.getMap();
        switch (direction) {
            case MOVE_UP:
                if(x%25 == 0){
                    if(judgeMap[(y-5)/25][x/25] == 0){
                        y -= 5;
                    }
                } else if(judgeMap[(y-5)/25][x/25] == 0 && judgeMap[(y-5)/25][x/25+1] == 0){
                    y -= 5;
                }
                break;
            case MOVE_DOWN:
                if(x%25 == 0){
                    if(judgeMap[y/25+1][x/25] == 0){
                        y += 5;
                    }
                } else if(judgeMap[y/25+1][x/25] == 0 && judgeMap[y/25+1][x/25+1] == 0){
                    y += 5;
                }
                break;
            case MOVE_LEFT:
                if(y%25 == 0){
                    if(x%25 == 0){
                        if(judgeMap[y/25][x/25-1] == 0){
                            x -= 5;
                        }
                    } else{
                        if(judgeMap[y/25][x/25] == 0){
                            x -= 5;
                        }
                    }
                } else{
                    if(x%25 == 0){
                        if(judgeMap[y/25][x/25] == 0 && judgeMap[y/25+1][x/25] == 0){
                            x -= 5;
                        }
                    } else{
                        if(judgeMap[y/25][x/25] == 0 && judgeMap[y/25+1][x/25] == 0){
                            x -= 5;
                        }
                    }
                }
                break;
            case MOVE_RIGHT:
                if(y%25 == 0){
                    if(judgeMap[y/25][x/25+1] == 0){
                        x += 5;
                    }
                } else if(judgeMap[y/25][x/25+1] == 0 && judgeMap[y/25+1][x/25+1] == 0){
                    x += 5;
                }
                break;
            default:
                break;
        }
    }

    public void getHit(){
        blood -= 1;
    }
}
