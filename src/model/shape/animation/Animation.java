package model.shape.animation;

import model.shape.Shape;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public abstract class Animation implements Animatable {
    boolean animating = false;
    Date nextAnimation=new Date();
    int stepDelay = 5;
    public String name = "";
    boolean isActivate = false;
    double durationTime = 2;
    boolean doesRepeat = false;
    boolean forwardOrBackward = true;
    public Shape thisShape;
    boolean enable = true;
    JTextField attribTime;
    JLabel attribTimeLable;
    JCheckBox repeatingBox;
    JCheckBox enableBox;
    JButton setButton;

    int fontSize = 40;


    public boolean getAnimating() {
        return animating;
    }

    public void setAnimating(boolean animating) {
        this.animating = animating;
    }

    public int getStepDelay() {
        return stepDelay;
    }

    public void setStepDelay(int stepDelay) {
        this.stepDelay = stepDelay;
    }

    Animation(Shape shape) {
        nextAnimation=new Date();
        animating=true;
        thisShape = shape;

        repeatingBox = new JCheckBox("repeat");
        enableBox = new JCheckBox("enable");
        attribTimeLable = new JLabel("duration");
        attribTime = new JTextField("time");
        setButton = new JButton("set it");


        attribTime.setHorizontalAlignment(SwingConstants.CENTER);
        attribTime.setBackground(Color.YELLOW);
        attribTime.setFont(new Font(null,0,fontSize));
        attribTime.setSelectedTextColor(Color.BLUE);
        attribTimeLable.setFont(new Font(null,0,fontSize));
        setButton.setFont(new Font(null,0,fontSize));

        repeatingBox.setFont(new Font(null,0,fontSize));
        enableBox.setFont(new Font(null,0,fontSize));




    }

    public void animate() {
        if(animating) {
            if (nextAnimation.before(new Date())) {
                step();
                nextAnimation.setTime(new Date().getTime() + stepDelay);
            }
        }
    }
    public abstract void setAnimationAttribPanel();
}
