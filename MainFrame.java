import javax.swing.*;
import java.awt.Color;

public class MainFrame extends JFrame {
    MainFrame(){
        this.setTitle("Łodzie");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1165,800);

        ImageIcon icon = new ImageIcon("src/img/LodzIco.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(new Color(0xCCCCCC));
        this.setLayout(null);
    }
}
