package server;

public class Players {
    private int playerNumber;
    private String name1;
    private String name2;
    private String name3;
    private String name4;

    public Players(int playerNumber,String name1,String name2) {
        this.playerNumber=playerNumber;
        this.name1=name1;
        this.name2=name2;
    }
    public Players(int playerNumber,String name1,String name2,String name3,String name4){
        this.playerNumber=playerNumber;
        this.name1=name1;
        this.name2=name2;
        this.name3=name3;
        this.name4=name4;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
