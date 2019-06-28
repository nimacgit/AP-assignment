package model.shape.animation;

import model.shape.Circle;
import model.shape.Point;
import model.shape.Shape;
import view.MainFrame;

import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.panels.ViewPanel.isAnimating;

public class CircleScale extends Animation {

    double scale = 2;
    JTextField attribScale;
    JLabel attribScaleLable;
    int fontSize = 40;
    double dr;
    double endRadius;

    public CircleScale(int duration, Shape shape, double scale) {
        super(shape);
        this.name = "CircleScale";
        this.scale = scale;
        endRadius = ((Circle) thisShape).getRadius() * scale;
        this.durationTime = duration;


        attribScale = new JTextField("scale");
        attribScaleLable = new JLabel("Scale");

        attribScale.setHorizontalAlignment(SwingConstants.CENTER);
        attribScale.setBackground(Color.YELLOW);
        attribScale.setFont(new Font(null, 0, fontSize));
        attribScale.setSelectedTextColor(Color.BLUE);
        attribScaleLable.setFont(new Font(null, 0, fontSize));
        setButton.addMouseListener(new circleScaleActionListener());
        dr = endRadius - ((Circle) thisShape).getRadius();
        dr /= (100 * durationTime);
    }

    @Override
    public void setAnimationAttribPanel() {
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.setLayout(new GridLayout(5, 2));
        if (thisShape.frame.menuPanel.animationMenu.animationAttribPanel != null) {
            attribScale.setMaximumSize(new Dimension(thisShape.frame.menuPanel.animationMenu.animationAttribPanel.getSize().width / 2, thisShape.frame.menuPanel.animationMenu.animationAttribPanel.getSize().height / 3));
            setButton.setMaximumSize(new Dimension(thisShape.frame.menuPanel.animationMenu.animationAttribPanel.getSize().width / 3, thisShape.frame.menuPanel.animationMenu.animationAttribPanel.getSize().height / 3));
            repeatingBox.setMaximumSize(new Dimension(thisShape.frame.menuPanel.animationMenu.animationAttribPanel.getSize().width / 3, thisShape.frame.menuPanel.animationMenu.animationAttribPanel.getSize().height / 3));
        }
        attribScale.setText(String.valueOf(scale));
        attribTime.setText(String.valueOf(durationTime));
        repeatingBox.setSelected(doesRepeat);
        enableBox.setSelected(enable);

        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribScaleLable);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribScale);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribTimeLable);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribTime);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(repeatingBox);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(enableBox);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(setButton);
    }


    @Override
    public boolean step() {
        if (enable) {
            int sign = 1;
            if (forwardOrBackward) {
                if (Math.abs(((Circle) thisShape).getRadius() + dr - endRadius) > Math.abs(((Circle) thisShape).getRadius() - endRadius)) {
                    forwardOrBackward = false;
                    endRadius = endRadius / scale;
                } else
                    sign = 1;
            } else if (doesRepeat) {
                if (Math.abs(((Circle) thisShape).getRadius() - dr - endRadius) > Math.abs(((Circle) thisShape).getRadius() - endRadius)) {
                    forwardOrBackward = true;
                    endRadius = endRadius * scale;
                } else
                    sign = -1;

            }
            else
                return false;
            if ((sign == -1 && !forwardOrBackward) || (sign == 1 && forwardOrBackward)) {
                ((Circle) thisShape).setRadius(((Circle) thisShape).getRadius() + sign * dr);
                return true;
            }
        }
        return true;
    }

    @Override
    public void reset() {
        forwardOrBackward = true;
        endRadius = ((Circle) thisShape).getRadius() * scale;
    }


    class circleScaleActionListener extends MouseAdapter {

        //stepDelay = duration;
        @Override
        public void mouseReleased(MouseEvent e) {
            if (!isAnimating) {
                if (forwardOrBackward)
                    ((Circle) thisShape).setRadius(endRadius / scale);
                else
                    ((Circle) thisShape).setRadius(endRadius);
                scale = Double.parseDouble(attribScale.getText());
                double t = Double.parseDouble(attribTime.getText());
                enable = enableBox.isSelected();
                doesRepeat = repeatingBox.isSelected();
                endRadius = ((Circle) thisShape).getRadius() * scale;
                dr = endRadius - ((Circle) thisShape).getRadius();
                dr /= (100 * t);
                durationTime = t;
                thisShape.frame.repaint();
                thisShape.frame.revalidate();
            }
        }
    }

}
