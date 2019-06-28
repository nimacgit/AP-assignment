package model.button;

import model.shape.animation.Animation;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static model.panels.ViewPanel.isAnimating;

public class ShapesAnimations extends JButton {
    Animation thisAnimation;
    int fontSize = 40;

    public ShapesAnimations(Animation animation, int width, int height)
    {
        super(animation.name);
        thisAnimation = animation;
        setPreferredSize(new Dimension(width, height));
        setFont(new Font(null,0,fontSize));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(!isAnimating) {
                    super.mouseReleased(e);
                    thisAnimation.thisShape.frame.menuPanel.animationMenu.animationAttribPanel.removeAll();
                    thisAnimation.setAnimationAttribPanel();
                    thisAnimation.thisShape.frame.repaint();
                    thisAnimation.thisShape.frame.revalidate();
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    thisAnimation.thisShape.animations.remove(thisAnimation);

                    thisAnimation.thisShape.setAnimationMenu();
                    thisAnimation.thisShape.frame.revalidate();
                    thisAnimation.thisShape.frame.repaint();
                }


            }
        });
    }
}
