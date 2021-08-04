package server.model;

import java.io.*;

public class ServerGameMap {
    int[][] map = new int[20][20];

    public ServerGameMap() {
        try {
            readDataFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDataFromFile() throws IOException {
        InputStream inputStream=this.getClass().getResourceAsStream("/sample/map1.txt");
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
