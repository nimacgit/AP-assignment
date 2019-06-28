package model.shape;

import model.shape.animation.Animatable;
import model.shape.animation.Move;
import model.shape.animation.RectScale;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.panels.ViewPanel.isAnimating;

public class Rectangle extends Shape implements Animatable {

    double width;
    double height;
    JButton setButton;
    JTextField attribWidth;
    JTextField attribHeight;
    JLabel attribHeightLable;
    JLabel attribWidthLable;
    JButton moveButton;
    JButton scaleButton;
    int fontSize = 40;
    double initWidth, initHeight;


    class RectangleActionListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if(!isAnimating){
                width = Integer.parseInt(attribWidth.getText());
                height = Integer.parseInt(attribHeight.getText());
                initHeight = height;
                initWidth = width;
                frame.viewPanel.repaint();
            }
        }
    }

    public Rectangle(Point upperLeft, int width, int height, MainFrame frame) {

        this(upperLeft,width,height,Color.PINK,Color.BLUE, frame);
        this.width = width;
        this.height=height;
        initHeight = height;
        initWidth = width;
        attribHeight = new JTextField();
        attribWidth = new JTextField();
        setButton = new JButton("set it");
        scaleButton = new JButton();
        moveButton = new JButton();
        attribHeightLable = new JLabel("Height :");
        attribWidthLable = new JLabel("Width :");

        attribHeight.setHorizontalAlignment(SwingConstants.CENTER);
        attribHeight.setBackground(Color.YELLOW);
        attribHeight.setFont(new Font(null,0,fontSize));
        attribHeight.setSelectedTextColor(Color.BLUE);
        attribWidth.setHorizontalAlignment(SwingConstants.CENTER);
        attribWidth.setBackground(Color.YELLOW);
        attribWidth.setFont(new Font(null,0,fontSize));
        attribWidth.setSelectedTextColor(Color.BLUE);
        attribWidthLable.setHorizontalAlignment(SwingConstants.CENTER);
        attribWidthLable.setFont(new Font(null,0,fontSize));
        attribHeightLable.setHorizontalAlignment(SwingConstants.CENTER);
        attribHeightLable.setFont(new Font(null,0,fontSize));
        setButton.addMouseListener(new RectangleActionListener());
        setButton.setFont(new Font(null,0,fontSize));
        scaleButton.setIcon(new ImageIcon(scaleImage));
        moveButton.setIcon(new ImageIcon(moveImage));


        if(frame.menuPanel.attribMenu != null) {
            attribHeight.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 2, frame.menuPanel.attribMenu.getSize().height / 3));
            attribWidth.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 2, frame.menuPanel.attribMenu.getSize().height / 3));
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
                    animations.add(new RectScale(5, Rectangle.this));
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
                    animations.add(new Move(5, Rectangle.this));
                    setAnimationMenu();
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });

    }

    private Rectangle(Point upperLeft , int width, int height, Color solidColor, Color borderColor, MainFrame frame) {
        super(upperLeft, solidColor, borderColor, frame);

    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getArea() {
        return (width*height);
    }

    @Override
    public void setAtrribPanel() {
        attribWidth.setText(String.valueOf((int)width));
        attribHeight.setText(String.valueOf((int)height));
        frame.menuPanel.attribMenu.add(attribWidthLable);
        frame.menuPanel.attribMenu.add(attribWidth);
        frame.menuPanel.attribMenu.add(attribHeightLable);
        frame.menuPanel.attribMenu.add(attribHeight);
        frame.menuPanel.attribMenu.add(setButton);
    }

    @Override
    public void setAnimationPanel() {
        frame.animationPanel.add(scaleButton);
        frame.animationPanel.add(moveButton);
    }

    @Override
    public void resetShape() {
        location = initLoc;
        width = initWidth;
        height = initHeight;
    }

    @Override
    public void render(Graphics2D G) {
        Stroke stroke=G.getStroke();
        G.setColor(solidColor);
        G.fillRect((int)location.x,(int)location.y,(int)width,(int)height);

        G.setStroke(new BasicStroke(thickness));
        G.setColor(borderColor);
        G.drawRect((int)location.x,(int)location.y,(int)width,(int)height);

        G.setStroke(stroke);
    }


    public boolean isIn(Point p) {
        Point dif=p.subtract(location);
        if(dif.getX()>0&&dif.getY()>0&&dif.getX()<width&&dif.getY()<height)
            return true;
        return false;
    }
}
