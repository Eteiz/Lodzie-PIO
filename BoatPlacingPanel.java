import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Panel allows user to click on the type of ship they want to place on board
public class BoatPlacingPanel extends JLabel implements ActionListener {

    /** TBA:
     * - MAKE VARIABLES PRIVATE AND CREATE SETTERS AND GETTERS (chosenShip)
     * - CONSISTENT NAMING
     *
     */

    // Buttons for picking ship type, H stands for horizontal, V stands for vertical
    JButton shipOneH, shipTwoH, shipThreeH, shipFourH, shipTwoV, shipThreeV, shipFourV;
    // Variable that determines which ship is chosen, if no ship is chosen the variable is null
    String chosenShip;

    BoatPlacingPanel() {

        chosenShip = null;

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

        shipOneH = new JButton(ship1ImageH);
        shipOneH.setBounds(10,10,40,40);

        Border border = BorderFactory.createLineBorder(Color.RED,3);
        shipOneH.setBorder(border);
        shipOneH.setBorderPainted(false);

        shipOneH.addActionListener(this);
        this.add(shipOneH);

        // Size 2
        ImageIcon ship2ImageH = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship2_h.png"));
        image = ship2ImageH.getImage();
        scaledImage = image.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ship2ImageH = new ImageIcon(scaledImage);

        shipTwoH = new JButton(ship2ImageH);
        shipTwoH.setBounds(10,55,80,40);

        shipTwoH.setBorder(border);
        shipTwoH.setBorderPainted(false);

        shipTwoH.addActionListener(this);
        this.add(shipTwoH);

        // Size 3
        ImageIcon ship3ImageH = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship3_h.png"));
        image = ship3ImageH.getImage();
        scaledImage = image.getScaledInstance(120, 40, Image.SCALE_SMOOTH);
        ship3ImageH = new ImageIcon(scaledImage);

        shipThreeH = new JButton(ship3ImageH);
        shipThreeH.setBounds(10,100,120,40);

        shipThreeH.setBorder(border);
        shipThreeH.setBorderPainted(false);

        shipThreeH.addActionListener(this);
        this.add(shipThreeH);

        // Size 4
        ImageIcon ship4ImageH = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship4_h.png"));
        image = ship4ImageH.getImage();
        scaledImage = image.getScaledInstance(160, 40, Image.SCALE_SMOOTH);
        ship4ImageH = new ImageIcon(scaledImage);

        shipFourH = new JButton(ship4ImageH);
        shipFourH.setBounds(10,145,160,40);

        shipFourH.setBorder(border);
        shipFourH.setBorderPainted(false);

        shipFourH.addActionListener(this);
        this.add(shipFourH);

        // Verically
        // Size 2
        ImageIcon ship2ImageV = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship2_v.png"));
        image = ship2ImageV.getImage();
        scaledImage = image.getScaledInstance(40, 80, Image.SCALE_SMOOTH);
        ship2ImageV = new ImageIcon(scaledImage);

        shipTwoV = new JButton(ship2ImageV);
        shipTwoV.setBounds(95,10,40,80);

        shipTwoV.setBorder(border);
        shipTwoV.setBorderPainted(false);

        shipTwoV.addActionListener(this);
        this.add(shipTwoV);

        // Size 3
        ImageIcon ship3ImageV = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship3_v.png"));
        image = ship3ImageV.getImage();
        scaledImage = image.getScaledInstance(40, 120, Image.SCALE_SMOOTH);
        ship3ImageV = new ImageIcon(scaledImage);

        shipThreeV = new JButton(ship3ImageV);
        shipThreeV.setBounds(140,10,40,120);

        shipThreeV.setBorder(border);
        shipThreeV.setBorderPainted(false);

        shipThreeV.addActionListener(this);
        this.add(shipThreeV);

        // Size 4
        ImageIcon ship4ImageV = new ImageIcon(this.getClass().getResource("/src/img/ship/Ship4_v.png"));
        image = ship4ImageV.getImage();
        scaledImage = image.getScaledInstance(40, 160, Image.SCALE_SMOOTH);
        ship4ImageV = new ImageIcon(scaledImage);

        shipFourV = new JButton(ship4ImageV);
        shipFourV.setBounds(185,10,40,160);

        shipFourV.setBorder(border);
        shipFourV.setBorderPainted(false);

        shipFourV.addActionListener(this);
        this.add(shipFourV);
    }

    // Methods makes all ship types not highlighted
    void ResetPlacingPanel() {
        shipOneH.setBorderPainted(false);
        shipTwoH.setBorderPainted(false);
        shipThreeH.setBorderPainted(false);
        shipFourH.setBorderPainted(false);
        shipTwoV.setBorderPainted(false);
        shipThreeV.setBorderPainted(false);
        shipFourV.setBorderPainted(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(shipOneH)) {
            // If no ship is picked or other ship is picked, the chosenShip becomes this
            // The button becomes highlighted
            ResetPlacingPanel();

            if(chosenShip == null || !chosenShip.contains("1")) {
                shipOneH.setBorderPainted(true);
                chosenShip = "1";
            }
            // If this ship has already been chosen, the chosenShip becomes null
            // The button stops being highlighted
            else {
                shipOneH.setBorderPainted(false);
                chosenShip = null;
            }
            // Code for test purposes
            //System.out.println(chosenShip);
        }
        // The rest of the buttons follow the same pattern
        // Horizontal
        if(e.getSource().equals(shipTwoH)) {
            ResetPlacingPanel();
            if(chosenShip == null || !chosenShip.contains("2H")) {
                shipTwoH.setBorderPainted(true);
                chosenShip = "2H";
            }
            else {
                shipTwoH.setBorderPainted(false);
                chosenShip = null;
            }
        }
        if(e.getSource().equals(shipThreeH)) {
            ResetPlacingPanel();
            if(chosenShip == null || !chosenShip.contains("3H")) {
                shipThreeH.setBorderPainted(true);
                chosenShip = "3H";
            }
            else {
                shipThreeH.setBorderPainted(false);
                chosenShip = null;
            }
        }
        if(e.getSource().equals(shipFourH)) {
            ResetPlacingPanel();
            if(chosenShip == null || !chosenShip.contains("4H")) {
                shipFourH.setBorderPainted(true);
                chosenShip = "4H";
            }
            else {
                shipFourH.setBorderPainted(false);
                chosenShip = null;
            }
        }

        // Vertical
        if(e.getSource().equals(shipTwoV)) {
            ResetPlacingPanel();
            if(chosenShip == null || !chosenShip.contains("2V")) {
                shipTwoV.setBorderPainted(true);
                chosenShip = "2V";
            }
            else {
                shipTwoV.setBorderPainted(false);
                chosenShip = null;
            }
        }
        if(e.getSource().equals(shipThreeV)) {
            ResetPlacingPanel();
            if(chosenShip == null || !chosenShip.contains("3V")) {
                shipThreeV.setBorderPainted(true);
                chosenShip = "3V";
            }
            else {
                shipThreeV.setBorderPainted(false);
                chosenShip = null;
            }
        }
        if(e.getSource().equals(shipFourV)) {
            ResetPlacingPanel();
            if(chosenShip == null || !chosenShip.contains("4V")) {
                shipFourV.setBorderPainted(true);
                chosenShip = "4V";
            }
            else {
                shipFourV.setBorderPainted(false);
                chosenShip = null;
            }
        }
    }

    String GetChosenShipStatus() {
        return chosenShip;
    }
}
