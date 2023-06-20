import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
public class TileGUI extends JButton {

    public enum TileValue {
        EMPTY, SHIP_PLACED, MISS, HIT
    }

    TileValue tileValue;

    ImageIcon emptyBig, emptySmall, shipPlacedBig, shipPlacedSmall, missBig, missSmall, hitBig, hitSmall;

    private boolean bigSize;

    TileGUI(boolean bigSize) {

        tileValue = TileValue.EMPTY;

        // Icons for player tiles
        emptyBig = new ImageIcon(this.getClass().getResource("src/img/Field_71x71.png"));
        shipPlacedBig = new ImageIcon(this.getClass().getResource("src/img/ship/Ship1.png"));
        missBig = new ImageIcon(this.getClass().getResource("src/img/Miss_71x71.png"));
        hitBig = new ImageIcon(this.getClass().getResource("src/img/Hit_71x71.png"));

        // Icons for enemy player tiles
        emptySmall = new ImageIcon(this.getClass().getResource("src/img/Field_36x36.png"));
        shipPlacedSmall = new ImageIcon(this.getClass().getResource("src/img/ship/sm_Ship1.png"));
        missSmall = new ImageIcon(this.getClass().getResource("src/img/Miss_36x36.png"));
        hitSmall = new ImageIcon(this.getClass().getResource("src/img/Hit_36x36.png"));

        this.bigSize = bigSize;
        // Setting initial status of tile
        if(bigSize) setIcon(emptyBig);
        else setIcon(emptySmall);

        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(true);
        setContentAreaFilled(false);
    }
    void ChangeTile(TileValue status) {
        tileValue = status;
        if(status == TileValue.EMPTY) {
            if(bigSize) setIcon(emptyBig);
            else setIcon(emptySmall);
        }
        else if(status == TileValue.SHIP_PLACED) {
            if(bigSize) setIcon(shipPlacedBig);
            else setIcon(shipPlacedSmall);
        }
        else if(status == TileValue.MISS) {
            if(bigSize) setIcon(missBig);
            else setIcon(missSmall);
        }
        else if(status == TileValue.HIT) {
            if(bigSize) setIcon(hitBig);
            else setIcon(hitSmall);
        }
    }
}
