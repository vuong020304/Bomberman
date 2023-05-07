package uet.oop.bomberman.map;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Map {
  public char[][] map_info;
  public int height;
  public int width;
  public int level;
  public String path;
  
  public static Map level1 = new Map("/levels/Level1.txt");
  public static Map level2 = new Map("/levels/Level2.txt");
  public static Map level3 = new Map("/levels/Level3.txt");
  
  public Map(String _path) {
    path = _path;
    loadMap();
  }
  
  private void loadMap() {
    try {
      URL url = Map.class.getResource(path);
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

//      InputStream fileInputStream = getClass().getResourceAsStream(path);
//      BufferedReader reader = new BufferedReader(new InputStreamReader(Map.class.getResourceAsStream(path)));
      
      String line;
      ArrayList<Integer> data = new ArrayList();
      
      if ((line = reader.readLine()) != null) {
        String[] input = line.split(" ");
        data.add(Integer.parseInt(input[0]));
        data.add(Integer.parseInt(input[1]));
        data.add(Integer.parseInt(input[2]));
      }
      
      height = data.get(1);
      width = data.get(2);
      level = data.get(0);
      map_info = new char[height][width];
      
      int t = 0;
      while ((line = reader.readLine()) != null && t < data.get(1)) {
        // Đọc dữ liệu từ mảng vào map.
        for (int i = 0; i < line.length(); i++) {
          map_info[t][i] = line.charAt(i);
        }
        t++;
      }
      reader.close();
      
    } catch (FileNotFoundException e) {
      System.out.println("Loi doc file cho map");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
