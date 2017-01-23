/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package background;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Faiz Rahiemy
 */
public class setting extends JPanel{
    public Image image;

    public setting() {
        image = new ImageIcon(getClass().getResource("setting.jpg")).getImage();
    }
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics gd = (Graphics2D) grphcs.create();
        gd.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        gd.dispose();
    }
}
