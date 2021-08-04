package client.model;

import client.view.GamePanel;

import java.util.Vector;

public class RobotControl {
    int gameMode;
    int clock;
    int distanceX;
    int distanceY;
    Dijkstra dijkstra;
    Point pP2;//p2附近最近节点
    Point pSu;//supply附近最近节点
    Point pEn;//敌人附近最近节点
    Vector<Point> points;
    Vector<Point> pointsPath=new Vector<>();
    boolean finish=false;
    GamePanel gamePanel;

    public RobotControl(GamePanel gamePanel,int gameMode) {
        this.gameMode=gameMode;
        this.gamePanel=gamePanel;
        dijkstra=new Dijkstra();
        points=dijkstra.getPointCollection();
        clock=0;
    }
    public void findSupply(Vector<Supply> supplies,Tank p2){
        if (!finish) {
            pP2=findNear(p2.getX(),p2.getY());
            pSu=findNear(supplies.get(0).getX(),supplies.get(0).getY());//找到距离supply最近的节点
            pointsPath = dijkstra.formVector(pP2.getNum(),pSu.getNum());//从该节点找最短路径
            finish=true;
        }
        //记录点顺序,存到pointsPath里面
    }
    public void findEnemy(Tank p1,Tank p2){
        if (!finish) {
            pP2=findNear(p2.getX(),p2.getY());
            pEn=findNear(p1.getX(),p1.getY());//找到距离敌人最近的节点
            pointsPath = dijkstra.formVector(pP2.getNum(),pEn.getNum());//从该节点找最短路径
            finish=true;
        }
    }
    public Point findNear(int x,int y){
        int X=25,Y=25,conx,cony;
        Point p=null;
        int[] boarders={25,125,200,275,350,450};
        conx=Math.abs(25-x);
        cony=Math.abs(25-y);
        for (int i=0;i<5;i++){
            if (conx>Math.abs(boarders[i+1]-x)){
                X=boarders[i+1];//更新X和较短距离
                conx=Math.abs(boarders[i+1]-x);
            }
            if (cony>Math.abs(boarders[i+1]-y)){
                Y=boarders[i+1];//更新Y和较短距离
                cony=Math.abs(boarders[i+1]-y);
            }
        }
        for (int i=0;i<36;i++){//获取节点
            if (points.get(i).getX()==X&&points.get(i).getY()==Y){
                p=points.get(i);
                break;
            }
        }
        return p;
    }

    public void basicMovement(Tank p1,Tank p2){
        clock+=50;
        if (p2.getAmmunition()>0&&gamePanel.isCanAddSupply()) {//自己有子弹且场上没有补给时
            pointsPath=null;
            dijkstra.resetFlag();
            finish=false;
            if (clock<=1000) {
                p2.randomDirection(clock);//有子弹时自由运动,且每隔1秒转向
                if(clock%250==0) shoot(p1, p2);//前方有敌人时射击
            }else {
                clock=0;
            }
        }else{//没子弹或有补给时
            goToSupply(p2);//最短路径吃补给
        }
    }

    public void advancedMovement(Tank p1,Tank p2){
        clock+=50;
            if (!gamePanel.isCanAddSupply()) {//有/无子弹有补给，先补给后敌人
                goToSupply(p2);
                if (clock%250==0) {
                    shoot(p1, p2);//前方有敌人时射击
                }
                if (clock>1000){
                    clock=0;
                }
            } else if (p2.getAmmunition() > 0 && gamePanel.isCanAddSupply()) {//有子弹没补给,找敌人
                goToEnemy(p1, p2);//最短路径找敌人
                if (clock%250==0) {
                    shoot(p1, p2);
                }
                if (clock>1000){
                    clock=0;
                }
            } else if (p2.getAmmunition() == 0 && gamePanel.isCanAddSupply()) {//没子弹没补给，随机运动等补给
                if (clock <= 1000) {
                    p2.randomDirection(clock);//有子弹时自由运动,且每隔1秒转向
                } else {
                    clock = 0;
                }
            }

    }
    public void goToEnemy(Tank p1,Tank p2){
        finish=false;
        findEnemy(gamePanel.getP1(), gamePanel.getP2());
        if(finish){
            int k=0;
            for (int i = 0; i < pointsPath.size(); i++) {
                if (pointsPath.get(i).isFlag()) {//找到路径中第一个没有打卡的点
                    k = i;
                    break;
                }
            }
            if (!pointsPath.get(pointsPath.size()-1).isFlag()){//若路线全走完了
                distanceX=gamePanel.getP1().x-p2.getX();
                distanceY=gamePanel.getP1().y-p2.getY();
                if (distanceX<0){//判断合适范围
                    p2.moveLeft();
                }else if (distanceX>15){
                    p2.moveRight();
                }else {
                    if (distanceY<0){
                        p2.moveUp();
                    }else if (distanceY>15){
                        p2.moveDown();
                    }else {
                        dijkstra.resetFlag();
                    }
                }
            } else {
                distanceX = pointsPath.get(k).getX() - p2.getX();
                distanceY = pointsPath.get(k).getY() - p2.getY();
                if (distanceX>0){
                    p2.moveRight();
                }else if (distanceX<0){
                    p2.moveLeft();
                }else {//x对齐
                    if (distanceY>0){
                        p2.moveDown();
                    }else if(distanceY<0){
                        p2.moveUp();
                    }else {
                        dijkstra.resetFlag();
                        pointsPath.get(k).setFlag(false);
                    }
                }
            }
        }
    }

    public void goToSupply(Tank p2){
        finish=false;
        findSupply(gamePanel.getSupplies(),gamePanel.getP2());
        if(finish){
            int k=0;
            for (int i = 0; i < pointsPath.size(); i++) {
                if (pointsPath.get(i).isFlag()) {//找到路径中第一个没有打卡的点
                    k = i;
                    break;
                }
            }
            if (!pointsPath.get(pointsPath.size()-1).isFlag()){
                distanceX=gamePanel.getSupplies().get(gamePanel.getSupplies().size()-1).getX()-p2.getX();
                distanceY=gamePanel.getSupplies().get(gamePanel.getSupplies().size()-1).getY()-p2.getY();
                if (distanceX<0){
                    p2.moveLeft();
                }else if (distanceX>15){
                    p2.moveRight();
                }else {
                    if (distanceY<0){
                        p2.moveUp();
                    }else if (distanceY>15){
                        p2.moveDown();
                    }else {
                        dijkstra.resetFlag();
                    }
                }
            } else {
                distanceX = pointsPath.get(k).getX() - p2.getX();
                distanceY = pointsPath.get(k).getY() - p2.getY();
                if (distanceX>0){
                    p2.moveRight();
                }else if (distanceX<0){
                    p2.moveLeft();
                }else {//x对齐
                    if (distanceY>0){
                        p2.moveDown();
                    }else if(distanceY<0){
                        p2.moveUp();
                    }else {
                        pointsPath.get(k).setFlag(false);
                    }
                }
            }

        }
    }
    public void shoot(Tank p1,Tank p2){
        if (p2.getAmmunition()>0) {
            switch (p2.getDirection()) {//面前有敌人自动射击
                case 1:
                    if (p2.getX() - p1.getX() >= -10 && p2.getX() - p1.getX() <= 10 && p2.getY() - p1.getY() <= 200 && p2.getY() - p1.getY() >= 0) {
                        gamePanel.getBullets().add(new Bullet(p2.getX() + 11, p2.getY() - 3, 1, gamePanel.getGameMap()));
                        p2.fire();
                    }
                    break;
                case 2:
                    if (p2.getX() - p1.getX() >= -10 && p2.getX() - p1.getX() <= 10 && p1.getY() - p2.getY() <= 200 && p1.getY() - p2.getY() >= 0) {
                        gamePanel.getBullets().add(new Bullet(p2.getX() + 11, p2.getY() + 25, 2, gamePanel.getGameMap()));
                        p2.fire();
                    }
                    break;
                case 3:
                    if (p2.getY() - p1.getY() >= -10 && p2.getY() - p1.getY() <= 10 && p2.getX() - p1.getX() <= 200 && p2.getX() - p1.getX() >= 0) {
                        gamePanel.getBullets().add(new Bullet(p2.getX() - 3, p2.getY() + 11, 3, gamePanel.getGameMap()));
                        p2.fire();
                    }
                    break;
                case 4:
                    if (p2.getY() - p1.getY() >= -10 && p2.getY() - p1.getY() <= 10 && p1.getX() - p2.getY() <= 200 && p1.getX() - p2.getY() >= 0) {
                        gamePanel.getBullets().add(new Bullet(p2.getX() + 25, p2.getY() + 11, 4, gamePanel.getGameMap()));
                        p2.fire();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
