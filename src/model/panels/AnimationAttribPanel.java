package model.panels;

import javax.swing.*;
import java.awt.*;

public class AnimationAttribPanel extends JPanel {

    public JScrollPane imageScrollablePanel;
    public JPanel imageAnimationAtrribPanel = new JPanel();
    public JPanel scrollPanelContentPane = new JPanel();

    AnimationAttribPanel()
    {
        scrollPanelContentPane.setLayout(new GridLayout());
        imageScrollablePanel = new JScrollPane(scrollPanelContentPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //scrollPanelContentPane.setLayout(new BoxLayout(scrollPanelContentPane, BoxLayout.PAGE_AXIS));
    }
}
