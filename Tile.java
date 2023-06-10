import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
public class Tile {
    public enum ShotValue {
        NO_SHOT, MISS, HIT;
    }

    private boolean Big;
    private int pos_x, pos_y;

    public ArrayList<JButton> Field = new ArrayList<JButton>();
    private ArrayList<ImageIcon> FieldImg = new ArrayList<ImageIcon>();

    public JLayeredPane layeredPane = new JLayeredPane();

    Tile(boolean big, int pos_x, int pos_y){
        FieldImgInit();
        this.Big = big;

        this.pos_x = pos_x;
        this.pos_y = pos_y;

        int FieldSize = 36, PlusSize = 0;

        if(this.Big){
            FieldSize = 71;
            PlusSize = 3;
        }

        FieldInit(pos_x, pos_y, FieldSize);

        layeredPane.setBounds(pos_x, pos_y,FieldSize, FieldSize);
        layeredPane.setOpaque(true);
        layeredPane.setBackground(Color.black); //set background color



        for(int i = 0; i < Field.size(); ++i)
            Field.get(i).setIcon(FieldImg.get(i+PlusSize));

        for (int i = 0; i < Field.size(); i++)
            layeredPane.add(Field.get(i), i);

    }

    /* Initialise Array of img for Field */
    private void FieldImgInit(){
        FieldImg.add(0, new ImageIcon("src/img/Field_36x36.png"));
        FieldImg.add(1, new ImageIcon("src/img/Miss_36x36.png"));
        FieldImg.add(2, new ImageIcon("src/img/Hit_36x36.png"));

        FieldImg.add(3, new ImageIcon("src/img/Field_71x71.png"));
        FieldImg.add(4, new ImageIcon("src/img/Miss_71x71.png"));
        FieldImg.add(5, new ImageIcon("src/img/Hit_71x71.png"));
    }

    /* Initialising Array of JLabels */
    private void FieldInit(int pos_x, int pos_y, int FieldSize){

        /* Empty Field */
        JButton label1= new JButton();
        label1.setBounds(0, 0, FieldSize, FieldSize);

        /* Miss */
        JButton label2= new JButton();
        label2.setBounds(0, 0, FieldSize, FieldSize);

        /* Hit */
        JButton label3= new JButton();
        label3.setBounds(0, 0, FieldSize, FieldSize);

        /* Adding to Array */
        Field.add(0, label1);
        Field.add(1, label2);
        Field.add(2, label3);
    }

    public JLayeredPane getJLayeredPane(){
        return layeredPane;
    }
}
