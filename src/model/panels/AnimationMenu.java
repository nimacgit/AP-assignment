package model.panels;

import javax.swing.*;
import java.awt.*;

public class AnimationMenu extends JPanel {

    int width = 500;
    int height = 400;
    int fontSize = 40;
    public static AnimationAttribPanel animationAttribPanel;
    public static AnimationScrollPanel animationScrollPanel;
    JPanel scrollPanelContent = new JPanel();

    public AnimationMenu() {
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        //setBackground(Color.MAGENTA);

        scrollPanelContent.setLayout(new BoxLayout(scrollPanelContent, BoxLayout.PAGE_AXIS));
        //scrollPanelContent.setLayout(new GridLayout(1,1));
        animationScrollPanel = new AnimationScrollPanel(scrollPanelContent);
        animationAttribPanel = new AnimationAttribPanel();
        add(animationAttribPanel);
        add(animationScrollPanel);
    }
}
