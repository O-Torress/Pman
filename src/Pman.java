import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;


public class Pman extends JPanel {
  class Bloque {
    int x;
    int y;
    int width;
    int height;
    Image image;

    int startx;
    int starty;

    Bloque(Image image, int x, int y, int width, int height) {
      this.image = image;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.startx = x;
      this.starty = y;
    }
  }

  private int rowCount = 21;
  private int columnCount = 19;
  private int tileSize = 32;
  private int boardWidth = columnCount * tileSize;
  private int boardHeight = rowCount * tileSize;

  private Image wallImage;
  private Image blueGhostImage;
  private Image orangeGhostImage;
  private Image pinkGhostImage;
  private Image redGhostImage;
  
  private Image pmanUpImage;
  private Image pmanDownImage;
  private Image pmanLeftImage;
  private Image pmanRightImage;

  private Image loadImage(String fileName) {
    java.net.URL url = getClass().getResource("/img/" + fileName);
    if (url == null) {
      java.io.File file = new java.io.File("img/" + fileName);
      if (file.exists()) {
        return new ImageIcon(file.getAbsolutePath()).getImage();
      }
      System.err.println("No se pudo cargar la imagen: " + fileName);
      return null;
    }
    return new ImageIcon(url).getImage();
  }

  //X = pardes, O = espacios en blanco, P = pac man, ' ' = food
  //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };

    HashSet<Bloque> walls;
    HashSet<Bloque> foods;
    HashSet<Bloque> ghosts;
    Bloque pman;

  Pman() {
    setPreferredSize(new Dimension(boardWidth, boardHeight));
    setBackground(Color.BLACK);

    // Carga de imagenes 
    wallImage = loadImage("wall.png");
    blueGhostImage = loadImage("blueGhost.png");
    orangeGhostImage = loadImage("orangeGhost.png");
    pinkGhostImage = loadImage("pinkGhost.png");
    redGhostImage = loadImage("redGhost.png");

    pmanUpImage = loadImage("pacmanUp.png");
    pmanDownImage = loadImage("pacmanDown.png");
    pmanLeftImage = loadImage("pacmanLeft.png");
    pmanRightImage = loadImage("pacmanRight.png");

    loadMap();
    System.out.println(walls.size());
    System.out.println(foods.size());
    System.out.println(ghosts.size());
  }

  public void loadMap(){
    walls = new HashSet<Bloque>();
    foods = new HashSet<Bloque>();
    ghosts = new HashSet<Bloque>();

    for (int r = 0; r <rowCount; r++) {
      for (int c = 0; c <columnCount; c++) {
        String row = tileMap[r];
        char tileMapChar = row.charAt(c);

        int x = c*tileSize;
        int y = r*tileSize;

        if(tileMapChar == 'X') {
          Bloque wall = new Bloque(wallImage, x, y, tileSize, tileSize);
          walls.add(wall);
        }
        else if (tileMapChar == 'b') {
          Bloque ghost = new Bloque(blueGhostImage, x, y, tileSize, tileSize);
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'o') {
          Bloque ghost = new Bloque(orangeGhostImage, x, y, tileSize, tileSize);
          ghosts.add(ghost);
        }else if (tileMapChar == 'p') {
          Bloque ghost = new Bloque(pinkGhostImage, x, y, tileSize, tileSize);
          ghosts.add(ghost);
        }else if (tileMapChar == 'r') {
          Bloque ghost = new Bloque(redGhostImage, x, y, tileSize, tileSize);
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'P') {
          pman = new Bloque(pmanRightImage, x, y, tileSize, tileSize); 
        }
        else if (tileMapChar == ' ') {
          Bloque food = new Bloque(null, x + 14, y + 14, 4, 4);
          foods.add(food);
        }
      }
    }
  }
}
