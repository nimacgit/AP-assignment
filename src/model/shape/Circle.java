package model.shape;


import model.shape.animation.Animation;
import model.shape.animation.Move;
import model.shape.animation.CircleScale;
import model.shape.animation.Drawable;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.panels.ViewPanel.isAnimating;

public class Circle extends Shape implements Drawable {

    protected double radius=100;
    JButton setButton;
    JButton moveButton;
    JButton scaleButton;
    JLabel attribRadiusLable;
    JTextField attribRadius;
    int fontSize = 40;
    Animation scaling;
    Animation moving;
    MainFrame frame;
    double initRad;

    class CircleActionListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e) {
            if(!isAnimating) {
                radius = Double.parseDouble(attribRadius.getText());
                initRad = radius;
                frame.viewPanel.repaint();
            }
        }
    }

    public Circle(Point center, double radius, MainFrame frame) {
        this(center,radius,Color.GREEN,Color.PINK, frame);
    }

    protected Circle(Point location, double radius, Color solidColor, Color borderColor, MainFrame frame) {
        super(location, solidColor, borderColor, frame);
        this.radius=radius;
        initRad = radius;

        attribRadius = new JTextField();
        setButton = new JButton("set it");
        scaleButton = new JButton();
        moveButton = new JButton();
        attribRadiusLable = new JLabel("Radius:");

        attribRadiusLable.setHorizontalAlignment(SwingConstants.CENTER);
        attribRadius.setHorizontalAlignment(SwingConstants.CENTER);
        attribRadius.setBackground(Color.YELLOW);
        attribRadius.setSelectedTextColor(Color.BLUE);
        attribRadius.setFont(new Font(null,0,fontSize));
        attribRadiusLable.setFont(new Font(null,0,fontSize));
        setButton.addMouseListener(new CircleActionListener());
        setButton.setFont(new Font(null,0,fontSize));
        scaleButton.setIcon(new ImageIcon(scaleImage));
        moveButton.setIcon(new ImageIcon(moveImage));

        if(frame.menuPanel.attribMenu != null) {
            attribRadius.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 2, frame.menuPanel.attribMenu.getSize().height / 3));
            setButton.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 3, frame.menuPanel.attribMenu.getSize().height / 3));
        }
        if(frame.animationPanel != null) {
            scaleButton.setSize(new Dimension(frame.animationPanel.getWidth() / 2, frame.animationPanel.getHeight()/6));
            moveButton.setSize(new Dimension(frame.animationPanel.getWidth() / 2, frame.animationPanel.getHeight()/6));
        }


        scaleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!isAnimating) {
                    animations.add(new CircleScale(5, Circle.this, 2));
                    setAnimationMenu();
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });
        moveButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if(!isAnimating) {
                    animations.add(new Move(5, Circle.this));
                    setAnimationMenu();
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });

    }

    @Override
    public void render(Graphics2D G) {
        G.setColor(solidColor);
        G.fillOval((int)(location.x-radius),(int)(location.y-radius),(int)radius*2,(int)radius*2);

        Stroke stroke=G.getStroke();
        G.setStroke(new BasicStroke(thickness));
        G.setColor(borderColor);
        G.drawOval((int)(location.x-radius),(int)(location.y-radius),(int)radius*2,(int)radius*2);
        G.setStroke(stroke);
    }

    public boolean isIn(Point p) {
        double dist=location.subtract(p).getRad();
        return dist<radius;
    }

    @Override
    public double getArea() {
        return Math.PI*radius*radius;
    }

    @Override
    public void setAtrribPanel() {

        attribRadius.setText(String.valueOf((int)radius));
        frame.menuPanel.attribMenu.add(attribRadiusLable);
        frame.menuPanel.attribMenu.add(attribRadius);
        frame.menuPanel.attribMenu.add(setButton);
        frame.menuPanel.attribMenu.add(new JLabel());
        frame.menuPanel.attribMenu.add(new JLabel());
        frame.menuPanel.attribMenu.add(new JLabel());

    }

    @Override
    public void setAnimationPanel() {


        frame.animationPanel.add(scaleButton);
        frame.animationPanel.add(moveButton);

    }

    @Override
    public void resetShape() {
        location = initLoc;
        radius = initRad;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
