package client.model;

import java.io.*;

public class GameMap {
    int[][] map = new int[20][20];

    public GameMap(int gameMode) {
        if(gameMode == 0){
            try {
                readDataFromFile("/sample/map1.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            try {
                readDataFromFile("/sample/map2.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readDataFromFile(String name) throws IOException {
        InputStream inputStream=this.getClass().getResourceAsStream(name);
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

        String line;
        int i = 0;
        while((line = reader.readLine()) != null){
            for (int j = 0; j < 20; ++j){
                map[i][j] = Integer.parseInt(line.substring(j,j+1));
            }
            ++i;
        }
        reader.close();
    }

    public int[][] getMap() {
        return map;
    }
}

