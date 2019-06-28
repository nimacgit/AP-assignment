package view;

import model.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    public static int width = 2000;
    public static int height = 1500;
    public static int posX = 700;
    public static int posY = 200;


    public static WelcomeLable welcomeLabel;
    public static ViewPanel viewPanel;
    public static ShapePanel shapePanel;
    public static AnimationPanel animationPanel;
    public static MenuPanel menuPanel;


    public MainFrame() {

        initialFrame();
        JPanel panel = (JPanel) getContentPane();


        welcomeLabel = new WelcomeLable("Welcome to Nimac AnimationTool :)");
        panel.add(welcomeLabel, BorderLayout.NORTH);

        viewPanel = new ViewPanel(this);
        panel.add(viewPanel, BorderLayout.CENTER);

        shapePanel = new ShapePanel(this);
        panel.add(shapePanel, BorderLayout.EAST);

        animationPanel = new AnimationPanel();
        panel.add(animationPanel, BorderLayout.WEST);

        menuPanel = new MenuPanel(this);
        panel.add(menuPanel, BorderLayout.SOUTH);




        this.pack();
        setVisible(true);
    }

    void initialFrame()
    {
        setTitle("MainFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setLocation(new Point(posX, posY));
        setLayout(new BorderLayout());
    }




}
