package model.shape.animation;

import model.shape.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.panels.ViewPanel.isAnimating;

@SuppressWarnings("Duplicates")
public class LineScale extends Animation {

    double scale = 2;
    JTextField attribScale;
    JLabel attribScaleLable;
    int fontSize = 40;
    double dl;
    double endLen;

    public LineScale(int duration, Line line) {
        super(line);
        this.name = "LineScale";
        this.scale = scale;
        endLen = ((Line) thisShape).getLength() * scale;
        this.durationTime = duration;


        attribScale = new JTextField("scale");
        attribScaleLable = new JLabel("Scale");

        attribScale.setHorizontalAlignment(SwingConstants.CENTER);
        attribScale.setBackground(Color.YELLOW);
        attribScale.setFont(new Font(null, 0, fontSize));
        attribScale.setSelectedTextColor(Color.BLUE);
        attribScaleLable.setFont(new Font(null,0,fontSize));
        setButton.addMouseListener(new LineScaleActionListener());
        dl = endLen - ((Line) thisShape).getLength();
        dl /= (100*durationTime);
    }


    @Override
    public boolean step() {
        if(enable) {
            int sign = 1;
            if (forwardOrBackward) {
                if (Math.abs(((Line) thisShape).getLength() + dl - endLen) > Math.abs(((Line) thisShape).getLength() - endLen)) {
                    forwardOrBackward = false;
                    endLen = endLen / scale;
                } else
                    sign = 1;
            } else if (doesRepeat) {
                if (Math.abs(((Line) thisShape).getLength() - dl - endLen) > Math.abs(((Line) thisShape).getLength() - endLen)) {
                    forwardOrBackward = true;
                    endLen = endLen * scale;
                } else
                    sign = -1;
            }
            else
                return false;
            if ((sign == -1 && !forwardOrBackward) || (sign == 1 && forwardOrBackward)) {
                ((Line) thisShape).setLength(((Line) thisShape).getLength() + sign * dl);
                return true;
            }
        }
        return true;

    }

    @Override
    public void reset() {
        forwardOrBackward = true;
        endLen = ((Line) thisShape).initend.subtract(((Line) thisShape).initLoc).getRad() * scale;
        ((Line) thisShape).setLength(((Line) thisShape).initend.subtract(((Line) thisShape).initLoc).getRad());

    }

    @Override
    public void setAnimationAttribPanel() {
        thisShape.frame.menuPanel.animationMenu.animationAttribPanel.setLayout(new GridLayout(5,2));
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

    class LineScaleActionListener extends MouseAdapter {

        //stepDelay = duration;
        @Override
        public void mouseReleased(MouseEvent e) {
            if(!isAnimating) {
                if (forwardOrBackward)
                    ((Line) thisShape).setLength(endLen / scale);
                else
                    ((Line) thisShape).setLength(endLen);
                scale = Double.parseDouble(attribScale.getText());
                double t = Double.parseDouble(attribTime.getText());
                enable = enableBox.isSelected();
                doesRepeat = repeatingBox.isSelected();
                endLen = ((Line) thisShape).getLength() * scale;
                dl = endLen - ((Line) thisShape).getLength();
                dl /= (100 * t);
                durationTime = t;
                thisShape.frame.repaint();
                thisShape.frame.revalidate();
            }
        }
    }

}
