import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Board extends JLabel {
    ImageIcon boardImg = new ImageIcon("src/img/board/SmallBoard.png");
    String PlayerNick="Gracz";
    boolean active;

    Board(){
        this.setText(PlayerNick);
        this.setIcon(boardImg);


        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.TOP);


        this.setBorder(BorderFactory.createLineBorder(Color.CYAN,3));
        this.setIconTextGap(-5);


        this.setFont(new Font("MV Boli", Font.PLAIN, 20));

    };
}
