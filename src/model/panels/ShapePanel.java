package model.panels;

import model.shape.*;
import model.shape.Image;
import model.shape.Point;
import model.shape.Rectangle;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.panels.ViewPanel.isAnimating;


public class ShapePanel extends JPanel {


    int width = 400;
    int height = 850;
    int fontSize = 40;
    public static Rectangle rectangle;
    public static Circle circle;
    public static Line line;
    public static Image image;
    Point posCircle, posRectangle, fposLine, lposLine, posImage;
    MainFrame frame;

    int rectangleWidth = 100, rectangleHeight = 100, circleRadius = 100;


    Point curMouseLocation = null;

    public ShapePanel(MainFrame frame) {
        this.frame = frame;
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.ORANGE);


        posRectangle = new Point(getLocation().getX() + 150, getLocation().getY() + 50);
        posCircle = posRectangle.add(new Point(circleRadius - 50, circleRadius + rectangleHeight + 50));
        fposLine = posCircle.add(new Point(-circleRadius + 50, circleRadius + 50));
        lposLine = fposLine.add(new Point(100, 100));
        posImage = fposLine.add(new Point(-100, 120));

        rectangle = new Rectangle(posRectangle, rectangleWidth, rectangleHeight, frame);
        circle = new Circle(posCircle, circleRadius, frame);
        line = new Line(fposLine, lposLine, frame);
        image = new Image(posImage, frame);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(!isAnimating) {
                    curMouseLocation = new Point(e.getX(), e.getY());
                    if (circle.isIn(curMouseLocation)) {
                        frame.viewPanel.shapes.add(new Circle(new Point(frame.viewPanel.getX() + 100, frame.viewPanel.getY() + 100), circleRadius, frame));
                        frame.viewPanel.repaint();
                    } else if (rectangle.isIn(curMouseLocation)) {
                        frame.viewPanel.shapes.add(new Rectangle(new Point(frame.viewPanel.getX() + 100, frame.viewPanel.getY() + 100), rectangleWidth, rectangleHeight, frame));
                        frame.viewPanel.repaint();
                    } else if (line.isIn(curMouseLocation)) {
                        frame.viewPanel.shapes.add(new Line(new Point(frame.viewPanel.getX() + 100, frame.viewPanel.getY() + 100), new Point(frame.viewPanel.getX() + 200, frame.viewPanel.getY() + 200), frame));
                        frame.viewPanel.repaint();
                    } else if (image.isIn(curMouseLocation)) {
                        frame.viewPanel.shapes.add(new Image(new Point(frame.viewPanel.getX() + 100, frame.viewPanel.getY() + 100), frame));
                        frame.viewPanel.repaint();
                    }
                }
            }
        });


    }

    @Override
    protected void paintComponent(Graphics G) {
        super.paintComponent(G);
        circle.render((Graphics2D) G);
        rectangle.render((Graphics2D) G);
        line.render((Graphics2D) G);
        image.render((Graphics2D) G);

    }


}
