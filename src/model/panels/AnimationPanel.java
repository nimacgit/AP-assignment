package model.panels;

import javax.swing.*;
import java.awt.*;

public class AnimationPanel extends JPanel {


    int width = 400;
    int height = 1500;
    int fontSize = 40;


    public AnimationPanel() {
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setLayout(new GridLayout(3,1));
        setBackground(Color.WHITE);
    }


}
