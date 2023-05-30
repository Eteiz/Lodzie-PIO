import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BoatPanel extends JLabel {
    BoatPanel() {

        // 451 x 401 - original image size
        // 336 x 328 - preferred image size
        this.setSize(336,328);
        this.setLayout(null);

        // TBA: CHANGE THE SIZE OF PANEL

        // Setting the main panel
        this.setBackground(Color.decode("#D0D0D2"));
        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(panelBorder);
        this.setOpaque(true);

        // Placing ships shapes
        Image image;
        Image scaledImage;

        // Horizontally
        // Size 1
        ImageIcon ship1ImageH = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship1.png"));
        image = ship1ImageH.getImage();
        scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ship1ImageH = new ImageIcon(scaledImage);

        JLabel shipOneH = new JLabel(ship1ImageH);
        shipOneH.setBounds(10,10,40,40);
        this.add(shipOneH);

        // Size 2
        ImageIcon ship2ImageH = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship2_h.png"));
        image = ship2ImageH.getImage();
        scaledImage = image.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ship2ImageH = new ImageIcon(scaledImage);

        JLabel shipTwoH = new JLabel(ship2ImageH);
        shipTwoH.setBounds(10,55,80,40);
        this.add(shipTwoH);

        // Size 3
        ImageIcon ship3ImageH = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship3_h.png"));
        image = ship3ImageH.getImage();
        scaledImage = image.getScaledInstance(120, 40, Image.SCALE_SMOOTH);
        ship3ImageH = new ImageIcon(scaledImage);

        JLabel shipThreeH = new JLabel(ship3ImageH);
        shipThreeH.setBounds(10,100,120,40);
        this.add(shipThreeH);

        // Size 4
        ImageIcon ship4ImageH = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship4_h.png"));
        image = ship4ImageH.getImage();
        scaledImage = image.getScaledInstance(160, 40, Image.SCALE_SMOOTH);
        ship4ImageH = new ImageIcon(scaledImage);

        JLabel shipFourH = new JLabel(ship4ImageH);
        shipFourH.setBounds(10,145,160,40);
        this.add(shipFourH);

        // Verically
        // Size 2
        ImageIcon ship2ImageV = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship2_v.png"));
        image = ship2ImageV.getImage();
        scaledImage = image.getScaledInstance(40, 80, Image.SCALE_SMOOTH);
        ship2ImageV = new ImageIcon(scaledImage);

        JLabel shipTwoV = new JLabel(ship2ImageV);
        shipTwoV.setBounds(95,10,40,80);
        this.add(shipTwoV);

        // Size 3
        ImageIcon ship3ImageV = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship3_v.png"));
        image = ship3ImageV.getImage();
        scaledImage = image.getScaledInstance(40, 120, Image.SCALE_SMOOTH);
        ship3ImageV = new ImageIcon(scaledImage);

        JLabel shipThreeV = new JLabel(ship3ImageV);
        shipThreeV.setBounds(140,10,40,120);
        this.add(shipThreeV);

        // Size 4
        ImageIcon ship4ImageV = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship4_v.png"));
        image = ship4ImageV.getImage();
        scaledImage = image.getScaledInstance(40, 160, Image.SCALE_SMOOTH);
        ship4ImageV = new ImageIcon(scaledImage);

        JLabel shipFourV = new JLabel(ship4ImageV);
        shipFourV.setBounds(185,10,40,160);
        this.add(shipFourV);

        // Potential place for buttons
    }

}
