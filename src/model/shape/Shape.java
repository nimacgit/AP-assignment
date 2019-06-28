package model.shape;


import model.button.ShapesAnimations;
import model.shape.animation.Animatable;
import model.shape.animation.Animation;
import model.shape.animation.Drawable;
import view.MainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Shape implements Drawable, Animatable {
    protected Point location;
    static String scaleIconDir = "src/view/icons/scale.png";
    static String moveIconDir = "src/view/icons/border.png";
    static String animateIconDir = "src/view/icons/animate.png";
    protected Color solidColor=Color.WHITE, borderColor=Color.BLACK;
    protected int thickness=3;
    public ArrayList<Animation> animations=new ArrayList<>();
    static java.awt.Image moveImage;
    static java.awt.Image scaleImage;
    static java.awt.Image animateImage;
    int animationWidth = 150;
    int animationHight = 50;
    public int curAnim = 0;
    public Point initLoc;


    public MainFrame frame;

    Shape(Point location, MainFrame frame) {
        this.location=location;
        this.frame = frame;
        try {
            moveImage = ImageIO.read(new File(moveIconDir));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            scaleImage = ImageIO.read(new File(scaleIconDir));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            animateImage = ImageIO.read(new File(animateIconDir));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    Shape(Point location,Color solidColor,Color borderColor, MainFrame frame) {
        this.location=location;
        this.initLoc=location;
        this.borderColor=borderColor;
        this.solidColor=solidColor;
        this.frame = frame;
        try {
            moveImage = ImageIO.read(new File(moveIconDir));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            scaleImage = ImageIO.read(new File(scaleIconDir));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            animateImage = ImageIO.read(new File(animateIconDir));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public Color getSolidColor() {
        return solidColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setSolidColor(Color color) {
        this.solidColor=color;
    }

    public void setBorderColor(Color color) {
        this.borderColor=color;
    }

    public abstract boolean isIn(Point p);

    public abstract double getArea();

    public void addAnimation(Animation animation) {
        animations.add(animation);
    }

    @Override
    public boolean step() {
        if(curAnim < animations.size())
            if(!animations.get(curAnim).step())
                curAnim++;
        return true;
    }

    abstract public void setAtrribPanel();
    abstract public void setAnimationPanel();
    public void setAnimationMenu()
    {
        frame.menuPanel.animationMenu.animationScrollPanel.contentPanel.removeAll();
        for(Animation anim : animations)
        {
            frame.menuPanel.animationMenu.animationScrollPanel.contentPanel.add(new ShapesAnimations(anim, animationWidth, animationHight));
        }
        frame.menuPanel.animationMenu.animationAttribPanel.removeAll();

    }
    public void reset() {
        resetShape();
        for(Animation a : animations)
            a.reset();

    }
    public abstract void resetShape();
}
