package model.panels;

import model.shape.Point;
import model.shape.Shape;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewPanel extends JPanel {


    int width = 1000;
    int height = 850;
    int fontSize = 40;

    public MainFrame frame;


    Point previousMouseLocation = null;
    Shape selectedShape = null;
    public ArrayList<Shape> shapes = new ArrayList<>();

    public static boolean isAnimating = false;
    Thread animation;
    ThreadAnimation threadAnimation;


    public ViewPanel(MainFrame frame) {
        this.frame = frame;
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.BLACK);


        threadAnimation = new ThreadAnimation();
        animation = new Thread(threadAnimation);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!isAnimating) {
                    Point curLocation = new Point(e.getX(), e.getY());
                    Point dif = curLocation.subtract(previousMouseLocation);
                    previousMouseLocation = curLocation;
                    if (selectedShape != null) {
                        Point newLocation = selectedShape.getLocation().add(dif);
                        if (newLocation.getX() >= frame.viewPanel.getX() - 400 && newLocation.getX() < frame.viewPanel.getLocation().getX() - 400 + frame.viewPanel.getWidth() &&
                                newLocation.getY() >= frame.viewPanel.getY() - 100 && newLocation.getY() < frame.viewPanel.getLocation().getY() - 100 + frame.viewPanel.getHeight()) {
                            selectedShape.initLoc = selectedShape.getLocation().add(dif);
                            selectedShape.setLocation(selectedShape.getLocation().add(dif));
                        }

                    }
                    repaint();
                }
            }
        });


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!isAnimating) {
                    if (e.getKeyCode() == KeyEvent.VK_DELETE && selectedShape != null) {
                        shapes.remove(selectedShape);
                        selectedShape = null;
                        frame.animationPanel.removeAll();
                        frame.menuPanel.attribMenu.removeAll();
                        frame.menuPanel.animationMenu.animationAttribPanel.removeAll();
                        frame.menuPanel.animationMenu.animationScrollPanel.contentPanel.removeAll();
                        frame.repaint();
                        frame.revalidate();

                    }
                }
            }
        });
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                selectedShape = null;
                if (!isAnimating) {
                    previousMouseLocation = new Point(e.getX(), e.getY());
                    Iterator<Shape> iterator = shapes.iterator();
                    while (iterator.hasNext()) {
                        Shape cur = iterator.next();
                        if (cur.isIn(previousMouseLocation)) {
                            selectedShape = cur;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedShape != null) {
                    frame.menuPanel.attribMenu.removeAll();
                    frame.animationPanel.removeAll();
                    selectedShape.setAtrribPanel();
                    selectedShape.setAnimationPanel();
                    selectedShape.setAnimationMenu();
                    frame.repaint();
                    frame.revalidate();
                }
                previousMouseLocation = null;
                requestFocus();
                //selectedShape = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        animation = new Thread(threadAnimation);
        animation.start();

    }


    public class ThreadAnimation implements Runnable {


        @SuppressWarnings("Duplicates")
        @Override
        public void run() {
            while (true) {
                if (isAnimating == false) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                Iterator<Shape> iterator = shapes.iterator();
                while (iterator.hasNext()) {
                    Shape cur = (Shape) iterator.next();
                    cur.step();
                }
                try {
                    repaint();
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics G) {
        super.paintComponent(G);
        Iterator<Shape> iterator = shapes.iterator();
        while (iterator.hasNext()) {
            Shape cur = iterator.next();
            cur.render((Graphics2D) G);
        }
    }


}
