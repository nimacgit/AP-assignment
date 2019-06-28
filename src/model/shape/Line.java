package model.shape;

import model.shape.animation.Drawable;
import model.shape.animation.LineScale;
import model.shape.animation.Move;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.panels.ViewPanel.isAnimating;

public class Line extends Shape implements Drawable {

    Point end;
    int acceptableDiff = 10;
    JButton setButton;
    JTextField attribVectorY;
    JTextField attribVectorX;
    JLabel attribVectorYLable;
    JLabel attribVectorXLable;
    JButton moveButton;
    JButton scaleButton;
    int fontSize = 40;
    public Point initend;


    class LineActionListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (!isAnimating) {
                initend = location.add(new Point(Integer.parseInt(attribVectorX.getText()), Integer.parseInt(attribVectorY.getText())));
                end = initend.add(new Point(0, 0));
                frame.viewPanel.repaint();
            }
        }
    }


    public Line(Point start, Point end, MainFrame frame) {
        this(start, end, Color.WHITE, Color.WHITE, frame);
        this.end = end;
        initend = end.add(new Point(0, 0));
        attribVectorX = new JTextField();
        attribVectorY = new JTextField();
        setButton = new JButton("set it");
        scaleButton = new JButton();
        moveButton = new JButton();
        attribVectorXLable = new JLabel("endVector X :");
        attribVectorYLable = new JLabel("endVector Y :");
        attribVectorXLable.setHorizontalAlignment(SwingConstants.CENTER);
        attribVectorXLable.setFont(new Font(null, 0, fontSize));
        attribVectorYLable.setHorizontalAlignment(SwingConstants.CENTER);
        attribVectorYLable.setFont(new Font(null, 0, fontSize));
        attribVectorX.setHorizontalAlignment(SwingConstants.CENTER);
        attribVectorX.setBackground(Color.YELLOW);
        attribVectorX.setFont(new Font(null, 0, fontSize));
        attribVectorX.setSelectedTextColor(Color.BLUE);
        attribVectorY.setHorizontalAlignment(SwingConstants.CENTER);
        attribVectorY.setBackground(Color.YELLOW);
        attribVectorY.setFont(new Font(null, 0, fontSize));
        attribVectorY.setSelectedTextColor(Color.BLUE);
        setButton.addMouseListener(new LineActionListener());
        setButton.setFont(new Font(null, 0, fontSize));
        scaleButton.setIcon(new ImageIcon(scaleImage));
        moveButton.setIcon(new ImageIcon(moveImage));


        if (frame.menuPanel.attribMenu != null) {
            attribVectorX.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 2, frame.menuPanel.attribMenu.getSize().height / 3));
            attribVectorY.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 2, frame.menuPanel.attribMenu.getSize().height / 3));
            setButton.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 3, frame.menuPanel.attribMenu.getSize().height / 3));
        }

        if (frame.animationPanel != null) {
            scaleButton.setSize(new Dimension(frame.animationPanel.getWidth() / 2, frame.animationPanel.getHeight() / 6));
            moveButton.setSize(new Dimension(frame.animationPanel.getWidth() / 2, frame.animationPanel.getHeight() / 6));
        }

        scaleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isAnimating) {
                    animations.add(new LineScale(10, Line.this));
                    setAnimationMenu();
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });
        moveButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isAnimating) {
                    animations.add(new Move(10, Line.this));
                    setAnimationMenu();
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });


    }

    private Line(Point start, Point end, Color solidColor, Color borderColor, MainFrame frame) {
        super(start, solidColor, borderColor, frame);
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }


    public double getArea() {
        return 0;
    }

    @Override
    public void setAtrribPanel() {
        attribVectorX.setText(String.valueOf((int) (end.subtract(location).getX())));
        attribVectorY.setText(String.valueOf((int) end.subtract(location).getY()));
        frame.menuPanel.attribMenu.add(attribVectorXLable);
        frame.menuPanel.attribMenu.add(attribVectorX);
        frame.menuPanel.attribMenu.add(attribVectorYLable);
        frame.menuPanel.attribMenu.add(attribVectorY);
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
        end = initend.add(new Point(0, 0));
    }


    @Override
    public void render(Graphics2D G) {
        ((Graphics2D) G).setStroke(new BasicStroke(thickness));
        G.setColor(this.solidColor);
        G.drawLine((int) location.getX(), (int) location.getY(), (int) end.getX(), (int) end.getY());
    }

    public boolean isIn(Point p) {
        Point difF = p.subtract(location);
        Point difL = p.subtract(end);
        Point difLF = location.subtract(end);
        if (difF.getRad() + difL.getRad() <= difLF.getRad() + acceptableDiff)
            return true;
        return false;
    }

    public double getLength() {
        return end.subtract(location).getRad();
    }

    public void setLength(double len) {
        double l = end.subtract(location).getRad();
        end.setX(location.getX() + (end.subtract(location).getX() * len / l));
        end.setY(location.getY() + (end.subtract(location).getY() * len / l));
    }

    @Override
    public void setLocation(Point location) {
        Point diff = end.subtract(this.location);
        this.location = location;
        end = this.location.add(diff);
        if (!isAnimating)
            initend = this.location.add(diff);

    }
}
