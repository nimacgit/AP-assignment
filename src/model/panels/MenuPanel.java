package model.panels;

import view.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import static model.panels.ViewPanel.isAnimating;


public class MenuPanel extends JPanel {


    public static AttribMenu attribMenu;
    public static AnimationMenu animationMenu;
    public static JButton actionButton;
    int width = 1500;
    int height = 400;
    int fontSize = 40;
    Image playIcon;
    String playIconDir = "src/view/icons/Play.png";
    MainFrame frame;


    public MenuPanel(MainFrame frame)
    {
        this.frame = frame;
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        //setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setLayout(new GridLayout());
        attribMenu = new AttribMenu();
        animationMenu = new AnimationMenu();
        actionButton = new JButton();

        try {
            playIcon = ImageIO.read(new File(playIconDir));
            actionButton.setIcon(new ImageIcon(playIcon));
        } catch (Exception e) {
            System.out.println(e);
        }
        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isAnimating = !isAnimating;
                if(isAnimating) {
                    actionButton.setBackground(Color.green);
                }
                else {
                    actionButton.setBackground(Color.red);
                    for(int i = 0; i < frame.viewPanel.shapes.size(); i++) {
                        frame.viewPanel.shapes.get(i).curAnim = 0;
                        frame.viewPanel.shapes.get(i).reset();
                    }
                }
            }
        });


        this.add(attribMenu);
        this.add(animationMenu);
        this.add(actionButton);




    }




}
