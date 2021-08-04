package client.model;

import java.util.Random;

public class Tank {
    int x;
    int y;
    int blood;
    int ammunition;
    int direction;
    int[] dirAva={1,0,1,0};
    int[] dirList={2,1};
    boolean isHost;
    boolean isRobot=false;
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
        ammunition = 5;
        if(isHost){
            direction = MOVE_DOWN;
        } else{
            direction = MOVE_UP;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRobot(boolean robot) {
        isRobot = robot;
    }

    public boolean isRobot() {
        return isRobot;
    }

    public int getDirection() {
        return direction;
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

    public int getAmmunition() {
        return ammunition;
    }

    public void getHit(){
        blood -= 1;
    }

    public void fire(){
        ammunition -= 1;
    }

    public void getSupplied(){
        ammunition = 5;
    }

    public void setP2Direction(int direction) {
        if (direction==MOVE_UP){
            this.direction=MOVE_DOWN;
        }else if (direction==MOVE_DOWN){
            this.direction=MOVE_UP;
        }else if (direction==MOVE_LEFT){
            this.direction=MOVE_RIGHT;
        }else if (direction==MOVE_RIGHT){
            this.direction=MOVE_LEFT;
        }
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
                        if(judgeMap[y/25][x/25-1] == 0 && judgeMap[y/25+1][x/25-1] == 0){
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

    //人机对战简单模式的坦克随机转向
    public void randomDirection(int clock){
        int dir=getDirection();
        Random random=new Random();
        checkDir();//先检查四个方向有哪些可以走
        if (dirAva[getDirection()-1]==1&&clock!=1000){//若当前方向可走，直接前进
            dir=getDirection();
        }else {//否则随机转向
            if (clock%250==0) {
                while (true) {
                    dir = random.nextInt(4)+1;
                    if (dirAva[dir-1] == 1&&dirList[0]!=dir) {
                        dirList[0]=dirList[1];
                        dirList[1]=dir;
                        break;
                    }
                }
            }
        }
        switch (dir){
            case 1:
                moveUp();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveLeft();
                break;
            case 4:
                moveRight();
                break;
        }
    }

    //人机对战电脑检验行进
    public void checkDir(){
        int[][] judgeMap = map.getMap();
        //向上
        if(x%25 == 0){
            if(judgeMap[(y-5)/25][x/25] == 0){
                dirAva[0]=1;
            }else dirAva[0]=0;
        } else if(judgeMap[(y-5)/25][x/25] == 0 && judgeMap[(y-5)/25][x/25+1] == 0){
            dirAva[0]=1;
        }else dirAva[0]=0;
        //向下
        if(x%25 == 0){
            if(judgeMap[y/25+1][x/25] == 0){
                dirAva[1]=1;
            }else dirAva[1]=0;
        } else if(judgeMap[y/25+1][x/25] == 0 && judgeMap[y/25+1][x/25+1] == 0){
            dirAva[1]=1;
        }else dirAva[1]=0;
        //向左
        if(y%25 == 0){
            if(x%25 == 0){
                if(judgeMap[y/25][x/25-1] == 0){
                    dirAva[2]=1;
                }else dirAva[2]=0;
            } else{
                if(judgeMap[y/25][x/25] == 0){
                    dirAva[2]=1;
                }else dirAva[2]=0;
            }
        } else{
            if(x%25 == 0){
                if(judgeMap[y/25][x/25-1] == 0 && judgeMap[y/25+1][x/25-1] == 0){
                    dirAva[2]=1;
                }else dirAva[2]=0;
            } else{
                if(judgeMap[y/25][x/25] == 0 && judgeMap[y/25+1][x/25] == 0){
                    dirAva[2]=1;
                }else dirAva[2]=0;
            }
        }
        //向右
        if(y%25 == 0){
            if(judgeMap[y/25][x/25+1] == 0){
                dirAva[3]=1;
            }else dirAva[3]=0;
        } else if(judgeMap[y/25][x/25+1] == 0 && judgeMap[y/25+1][x/25+1] == 0){
            dirAva[3]=1;
        }else dirAva[3]=0;
    }
}
