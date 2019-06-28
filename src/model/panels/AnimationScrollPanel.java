package model.panels;

import javax.swing.*;
import java.awt.*;

public class AnimationScrollPanel extends JScrollPane {

    public JPanel contentPanel;

    public AnimationScrollPanel(JPanel contentPanel)
    {
        super(contentPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.contentPanel = contentPanel;

    }
}
