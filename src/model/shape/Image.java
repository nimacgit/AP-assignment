package model.shape;

import model.shape.animation.*;
import view.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static model.panels.ViewPanel.isAnimating;

public class Image extends Shape implements Drawable {

    double width, height;
    public java.awt.Image image;
    public java.awt.Image animationIconImage;
    String imageDir = "src/view/icons/test.png";
    JButton setButton;
    JButton selectButton;
    JButton moveButton;
    JButton scaleButton;
    JButton animateButton;
    JTextField attribWidth;
    JTextField attribHeight;
    JLabel attribWidthLable;
    JLabel attribHeightLable;
    int fontSize = 30;
    double initWidth,initHeight;


    class ImageSelectionActionListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (!isAnimating) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("."));
                int returnVal = fc.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    imageDir = fc.getSelectedFile().getPath();
                } else {
                    System.out.println("cant open");
                }
                try {
                    animationIconImage = ImageIO.read(new File(imageDir));
                    image = animationIconImage;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //frame.viewPanel.repaint();
                frame.repaint();
            }
        }

    }

    class ImageActionListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if(!isAnimating) {
                width = Integer.parseInt(attribWidth.getText());
                height = Integer.parseInt(attribHeight.getText());
                initHeight = height;
                initWidth = width;
                frame.viewPanel.repaint();
                frame.repaint();
            }
        }
    }


    public Image(Point location, MainFrame frame) {
        super(location, frame);
        try {
            animationIconImage = ImageIO.read(new File(imageDir));
            image = animationIconImage;
            width = ((BufferedImage) image).getWidth();
            height = ((BufferedImage) image).getHeight();
            initHeight = height;
            initWidth = width;
        } catch (Exception e) {
            System.out.println(e);
        }


        attribHeight = new JTextField();
        attribWidth = new JTextField();
        attribWidthLable = new JLabel("Width :");
        attribHeightLable = new JLabel("Height :");
        setButton = new JButton("set it");
        selectButton = new JButton("selectpic");
        scaleButton = new JButton();
        moveButton = new JButton();
        animateButton = new JButton();


        setButton.addMouseListener(new ImageActionListener());
        setButton.setFont(new Font(null,0,fontSize));
        selectButton.setFont(new Font(null,0,fontSize));
        selectButton.addMouseListener(new ImageSelectionActionListener());
        attribWidthLable.setHorizontalAlignment(SwingConstants.CENTER);
        attribWidthLable.setFont(new Font(null,0,fontSize));
        attribHeightLable.setHorizontalAlignment(SwingConstants.CENTER);
        attribHeightLable.setFont(new Font(null,0,fontSize));
        attribWidth.setHorizontalAlignment(SwingConstants.CENTER);
        attribWidth.setBackground(Color.YELLOW);
        attribWidth.setFont(new Font(null,0,fontSize));
        attribWidth.setSelectedTextColor(Color.BLUE);
        attribHeight.setHorizontalAlignment(SwingConstants.CENTER);
        attribHeight.setBackground(Color.YELLOW);
        attribHeight.setFont(new Font(null,0,fontSize));
        attribHeight.setSelectedTextColor(Color.BLUE);
        scaleButton.setIcon(new ImageIcon(scaleImage));
        moveButton.setIcon(new ImageIcon(moveImage));
        animateButton.setIcon(new ImageIcon(animateImage));



        if(frame.menuPanel.attribMenu != null) {
            setButton.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 5, frame.menuPanel.attribMenu.getSize().height / 3));
            selectButton.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 2, frame.menuPanel.attribMenu.getSize().height / 3));
            attribHeight.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 5, frame.menuPanel.attribMenu.getSize().height / 3));
            attribWidth.setMaximumSize(new Dimension(frame.menuPanel.attribMenu.getSize().width / 5, frame.menuPanel.attribMenu.getSize().height / 3));
        }
        if(frame.animationPanel != null) {
            scaleButton.setSize(new Dimension(frame.animationPanel.getWidth() / 2, frame.animationPanel.getHeight() / 6));
            moveButton.setSize(new Dimension(frame.animationPanel.getWidth() / 2, frame.animationPanel.getHeight() / 6));
        }

        scaleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!isAnimating) {
                    animations.add(new ImageScale(10, Image.this));
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
                    animations.add(new Move(10, Image.this));
                    setAnimationMenu();
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });
        animateButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if(!isAnimating) {
                    animations.add(new ImageAnimation(5, Image.this));
                    setAnimationMenu();
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });


    }



    @Override
    public void render(Graphics2D G) {
        G.drawImage(image, (int)location.getX(), (int)location.getY(), (int)width, (int)height, null);
    }

    public boolean isIn(Point p) {
        Point dif = p.subtract(location);
        if(dif.getX()>0&&dif.getY()>0&&dif.getX()<width&&dif.getY()<height)
            return true;
        return false;
    }

    @Override
    public double getArea() {
        return 0;
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
        frame.menuPanel.attribMenu.add(selectButton);

    }

    @Override
    public void setAnimationPanel() {

        frame.animationPanel.add(scaleButton);
        frame.animationPanel.add(moveButton);
        frame.animationPanel.add(animateButton);
    }

    @Override
    public void resetShape() {
        width = initWidth;
        height = initHeight;
        location = initLoc;
        image = animationIconImage;
    }

    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
    public void setWidth(double width){
        this.width = width;
    }
    public void setHeight(double height){
        this.height = height;
    }


}
