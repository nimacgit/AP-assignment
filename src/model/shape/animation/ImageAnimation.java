package model.shape.animation;

import model.shape.Image;
import sun.applet.Main;
import view.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static model.panels.ViewPanel.isAnimating;

public class ImageAnimation extends Animation {

    ArrayList<java.awt.Image> images = new ArrayList<>();
    int fontSize = 40;
    int currentPicNum = 0;
    JButton addImage;
    java.awt.Image newImage;
    String newImageDir;

    class picButton extends JButton
    {
        MainFrame frame;
        java.awt.Image thisImage;

        public picButton(java.awt.Image thisImage, MainFrame frame, String text)
        {
            super(text);
            this.thisImage = thisImage;
            this.frame = frame;
            this.setBackground(Color.MAGENTA);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    images.remove(thisImage);
                    thisShape.frame.menuPanel.animationMenu.animationAttribPanel.removeAll();
                    setAnimationAttribPanel();
                    frame.repaint();
                    frame.revalidate();
                }
            });
        }
    }


    public ImageAnimation(int duration, Image image) {
        super(image);
        this.name = "ImageAnimation";
        this.durationTime = duration;


        addImage = new JButton("addImage");
        addImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isAnimating) {
                    JFileChooser fc = new JFileChooser();
                    fc.setCurrentDirectory(new java.io.File("."));
                    int returnVal = fc.showOpenDialog(thisShape.frame);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        newImageDir = fc.getSelectedFile().getPath();
                        try {
                            newImage = ImageIO.read(new File(newImageDir));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        images.add(newImage);
                        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.removeAll();
                        setAnimationAttribPanel();
                        thisShape.frame.repaint();
                        thisShape.frame.revalidate();
                    } else {
                        System.out.println("cant open");
                    }


                }
            }
        });
        addImage.setFont(new Font(null, 0, fontSize));
        setButton.addMouseListener(new ImageAnimationActionListener());


    }


    @Override
    public boolean step() {
        if(enable) {
            int sign = 1;
            if (forwardOrBackward) {
                if (currentPicNum == images.size() - 1) {
                    forwardOrBackward = false;
                } else
                    sign = 1;
                return true;
            } else if (doesRepeat) {
                if (currentPicNum == 0) {
                    forwardOrBackward = true;
                } else
                    sign = -1;
                return true;
            }
            if ((sign == -1 && !forwardOrBackward) || (sign == 1 && forwardOrBackward)) {
                ((Image) thisShape).image = images.get(currentPicNum);
                currentPicNum += sign;
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        currentPicNum = 0;
        forwardOrBackward = true;
    }

    @Override
    public void setAnimationAttribPanel() {
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel.removeAll();
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.scrollPanelContentPane.removeAll();
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.setLayout(new GridLayout(2,1));
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel.setLayout(new GridLayout(3,2));

        for(int i = 0; i < images.size(); i++)
        {
            thisShape.frame.menuPanel.animationMenu.animationAttribPanel.scrollPanelContentPane.add(new picButton(images.get(i), thisShape.frame, "nimac"));
        }

        attribTime.setText(String.valueOf(durationTime));
        repeatingBox.setSelected(doesRepeat);
        enableBox.setSelected(enable);

        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel.add(attribTimeLable);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel.add(attribTime);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel.add(repeatingBox);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel.add(enableBox);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel.add(addImage);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel.add(setButton);

        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageScrollablePanel);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(thisShape.frame.menuPanel.animationMenu.animationAttribPanel.imageAnimationAtrribPanel);



    }


    class ImageAnimationActionListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!isAnimating) {
                currentPicNum = 0;
                double t = Double.parseDouble(attribTime.getText());
                enable = enableBox.isSelected();
                doesRepeat = repeatingBox.isSelected();
                stepDelay = (int) (1000*t/images.size());
                durationTime = t;
                currentPicNum = 0;
                ((Image)thisShape).image = ((Image)thisShape).animationIconImage;
                thisShape.frame.menuPanel.animationMenu.animationAttribPanel.removeAll();
                setAnimationAttribPanel();
                thisShape.frame.repaint();
                thisShape.frame.revalidate();

            }
        }
    }


}

