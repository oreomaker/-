package GamePanel.maps;

import java.io.*;

public class GameMap {
    int[][] map = new int[20][20];

    public GameMap() {
        readDataFromFile("D:\\JAVA_learn\\Models\\src\\GamePanel\\maps\\map1.txt");
    }

    public void readDataFromFile(String filename){
        File mapFile = new File(filename);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapFile));
            String line;
            int i = 0;
            while((line = reader.readLine()) != null){
                for (int j = 0; j < 20; ++j){
                    map[i][j] = Integer.parseInt(line.substring(j,j+1));
                }
                ++i;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] getMap() {
        return map;
    }
}
