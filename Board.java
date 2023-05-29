import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.*;
import javax.swing.*;

public class Board{
    public JPanel Board = new JPanel();
    private ArrayList<Tile> Fields = new ArrayList<Tile>();

    private int FieldSize;
    private int BoardSize;
    private int pos_x, pos_y;

    Board(int x, int y, int BoardSize, boolean Big){
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
}
