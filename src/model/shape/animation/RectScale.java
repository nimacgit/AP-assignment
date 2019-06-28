package model.shape.animation;

import model.shape.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.panels.ViewPanel.isAnimating;

public class RectScale extends Animation {

    double scale = 2;
    JTextField attribScale;
    JLabel attribScaleLable;
    int fontSize = 40;
    double dw;
    double dh;
    double endWidth;
    double endHeight;

    public RectScale(int duration, Rectangle rectangle) {
        super(rectangle);
        this.name = "RectScale";
        this.durationTime = duration;
        this.scale = scale;
        endWidth = ((Rectangle) thisShape).getWidth() * scale;
        endHeight = ((Rectangle) thisShape).getHeight() * scale;

        attribScale = new JTextField("scale");
        attribScaleLable = new JLabel("Scale");

        attribScale.setHorizontalAlignment(SwingConstants.CENTER);
        attribScale.setBackground(Color.YELLOW);
        attribScale.setFont(new Font(null, 0, fontSize));
        attribScale.setSelectedTextColor(Color.BLUE);
        attribScaleLable.setFont(new Font(null,0,fontSize));
        setButton.addMouseListener(new rectangleScaleActionListener());
        dw = endWidth - ((Rectangle) thisShape).getWidth();
        dw /= (100*durationTime);
        dh = endHeight - ((Rectangle) thisShape).getHeight();
        dh /= (100*durationTime);



    }


    @Override
    public boolean step() {
        if(enable) {
            int sign = 1;
            if (forwardOrBackward) {
                if (Math.abs(((Rectangle) thisShape).getWidth() + dw - endWidth) > Math.abs(((Rectangle) thisShape).getWidth() - endWidth)) {
                    forwardOrBackward = false;
                    endWidth = endWidth / scale;
                    endHeight = endHeight / scale;
                } else
                    sign = 1;
            } else if (doesRepeat) {
                if (Math.abs(((Rectangle) thisShape).getWidth() - dw - endWidth) > Math.abs(((Rectangle) thisShape).getWidth() - endWidth)) {
                    forwardOrBackward = true;
                    endWidth = endWidth * scale;
                    endHeight = endHeight * scale;
                } else
                    sign = -1;
            }
            else
                return false;
            if ((sign == -1 && !forwardOrBackward) || (sign == 1 && forwardOrBackward)) {
                ((Rectangle) thisShape).setWidth(((Rectangle) thisShape).getWidth() + sign * dw);
                ((Rectangle) thisShape).setHeight(((Rectangle) thisShape).getHeight() + sign * dh);
                return true;
            }
        }
        return true;
    }

    @Override
    public void reset() {
        forwardOrBackward = true;
        endWidth = ((Rectangle) thisShape).getWidth() * scale;
        endHeight = ((Rectangle) thisShape).getHeight() * scale;
    }

    @SuppressWarnings("Duplicates")
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

    class rectangleScaleActionListener extends MouseAdapter {

        //stepDelay = duration;
        @Override
        public void mouseReleased(MouseEvent e) {
            if (!isAnimating) {
                if (forwardOrBackward) {
                    ((Rectangle) thisShape).setWidth(endWidth / scale);
                    ((Rectangle) thisShape).setHeight(endHeight / scale);
                } else {
                    ((Rectangle) thisShape).setWidth(endWidth);
                    ((Rectangle) thisShape).setHeight(endHeight);
                }

                scale = Double.parseDouble(attribScale.getText());
                double t = Double.parseDouble(attribTime.getText());
                enable = enableBox.isSelected();
                doesRepeat = repeatingBox.isSelected();
                endWidth = ((Rectangle) thisShape).getWidth() * scale;
                endHeight = ((Rectangle) thisShape).getHeight() * scale;
                dw = endWidth - ((Rectangle) thisShape).getWidth();
                dw /= (100 * t);
                dh = endHeight - ((Rectangle) thisShape).getHeight();
                dh /= (100 * t);
                durationTime = t;
                thisShape.frame.repaint();
                thisShape.frame.revalidate();
            }
        }
    }
}
