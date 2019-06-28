package model.panels;

import javax.swing.*;
import java.awt.*;

public class AttribMenu extends JPanel {

    int width = 500;
    int height = 400;
    int fontSize = 40;





    public AttribMenu() {
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridLayout(3,2));
        setBackground(Color.CYAN);
        setAlignmentX(this.CENTER_ALIGNMENT);
    }
}
