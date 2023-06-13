import java.awt.Color;
import java.util.*;
import javax.swing.*;

public class Board_GUI {

    public JPanel Board = new JPanel();
    private ArrayList<Tile> Fields = new ArrayList<Tile>();

    private int FieldSize;
    private int BoardSize;
    private int pos_x, pos_y;

    Board_GUI(int x, int y, int BoardSize, boolean Big){
        this.pos_x=x;
        this.pos_y=y;

        this.BoardSize=BoardSize;
        this.FieldSize=36;
        if(Big)
            this.FieldSize=71;

        Board.setBounds(0, 0, this.BoardSize*FieldSize, this.BoardSize*FieldSize);
        Board.setLayout(null);


        for(int i = 0; i < BoardSize; ++i){
            for(int j = 0; j < BoardSize; ++j){
                Fields.add(i*j+j, new Tile(Big, j*FieldSize,i*FieldSize));
                Board.add(Fields.get(i*j+j).layeredPane);
            }
        }

        Board.setLocation(pos_x, pos_y);
        Board.setVisible(true);
        Board.setOpaque(true);
        Board.setBackground(Color.green);
    }

    public Tile getTile(int x, int y){
        return Fields.get(x*y+y);
    }

    public void setTile(int x, int y, Tile.ShotValue val){
        getTile(x, y).Field.get(0).setVisible(false);
        getTile(x, y).Field.get(1).setVisible(false);
        getTile(x, y).Field.get(2).setVisible(false);

        if(val == Tile.ShotValue.NO_SHOT)
            getTile(x, y).Field.get(0).setVisible(true);
        else if (val == Tile.ShotValue.MISS)
            getTile(x, y).Field.get(1).setVisible(true);
        else if (val == Tile.ShotValue.HIT)
            getTile(x, y).Field.get(2).setVisible(true);

    }
}
