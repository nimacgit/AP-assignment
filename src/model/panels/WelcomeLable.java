package model.panels;

import javax.swing.*;
import java.awt.*;

public class WelcomeLable extends JLabel {

    int width = 1500;
    int height = 100;
    int fontSize = 40;

    public WelcomeLable(String text)
    {
        super(text, SwingConstants.CENTER);
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setFont(new Font(null,0,fontSize));

    }


}
