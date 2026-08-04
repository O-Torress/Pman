import javax.swing.JFrame;

public class app {
  
  public static void main(String[]arg) throws Exception {
    int rowCount = 21;
    int columnCount = 19;
    int tileSize = 32;
    int boardWidth = columnCount * tileSize;
    int boardHeight = rowCount * tileSize;


    JFrame frame = new JFrame("Pac Man");
    
    frame.setSize(boardHeight, boardWidth);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Pman pmanGame = new Pman();
    frame.add(pmanGame);
    frame.pack();
    pmanGame.requestFocus();
    frame.setVisible(true);
    
  }
}
