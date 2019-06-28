package model.shape.animation;

import model.shape.Circle;
import model.shape.Point;
import model.shape.Rectangle;
import model.shape.Shape;

import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.panels.ViewPanel.isAnimating;

public class Move extends Animation {


    JTextField attribY;
    JLabel attribYLable;
    JTextField attribX;
    JLabel attribXLable;
    int fontSize = 40;
    Point curVector = new Point(0, 0);
    Point endVector = new Point(0, 0);
    double dx, dy;

    public Move(int duration, Shape shape) {
        super(shape);
        this.durationTime = duration;
        this.name = "Move";

        attribY = new JTextField("Y");
        attribX = new JTextField("X");
        attribXLable = new JLabel("X");
        attribYLable = new JLabel("Y");

        attribX.setHorizontalAlignment(SwingConstants.CENTER);
        attribX.setBackground(Color.YELLOW);
        attribX.setFont(new Font(null,0,fontSize));
        attribX.setSelectedTextColor(Color.BLUE);
        attribY.setHorizontalAlignment(SwingConstants.CENTER);
        attribY.setBackground(Color.YELLOW);
        attribY.setFont(new Font(null,0,fontSize));
        attribY.setSelectedTextColor(Color.BLUE);
        attribXLable.setFont(new Font(null,0,fontSize));
        attribYLable.setFont(new Font(null,0,fontSize));

        setButton.addMouseListener(new Move.MoveActionListener());




    }



    @Override
    public void setAnimationAttribPanel() {
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.setLayout(new GridLayout(5,2));
        attribX.setText(String.valueOf((int)endVector.getX()));
        attribY.setText(String.valueOf((int)endVector.getY()));
        attribTime.setText(String.valueOf(durationTime));
        repeatingBox.setSelected(doesRepeat);
        enableBox.setSelected(enable);

        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribXLable);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribX);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribYLable);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribY);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribTimeLable);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(attribTime);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(repeatingBox);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(enableBox);
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.add(setButton);

    }


    @Override
    public boolean step() {
        if(enable) {
            if ((curVector.getX() < Math.abs(endVector.getX()) && curVector.getY() < Math.abs(endVector.getY())) || !forwardOrBackward) {

                if (curVector.getX() < 0 && curVector.getY() < 0)
                    forwardOrBackward = true;
                int sign = 1;
                if (!forwardOrBackward)
                    sign = -1;
                curVector = curVector.add(new Point(sign * Math.abs(dx), sign * Math.abs(dy)));
                thisShape.setLocation(thisShape.getLocation().add(new Point(sign * dx, sign * dy)));
                return true;
            } else if (doesRepeat) {
                forwardOrBackward = false;
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        forwardOrBackward = true;
        curVector = new Point(0, 0);
    }


    class MoveActionListener extends MouseAdapter {


        //stepDelay = duration;
        @Override
        public void mouseReleased(MouseEvent e) {
            if(!isAnimating) {
                int x = Integer.parseInt(attribX.getText());
                int y = Integer.parseInt(attribY.getText());
                double t = Double.parseDouble(attribTime.getText());
                enable = enableBox.isSelected();
                doesRepeat = repeatingBox.isSelected();
                endVector = new Point(x, y);
                curVector = new Point(0, 0);
                dx = endVector.getX() / (t * 100);
                dy = endVector.getY() / (t * 100);
                durationTime = t;
                thisShape.frame.repaint();
                thisShape.frame.revalidate();
            }
        }
    }

}

