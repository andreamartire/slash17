/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelloTabellone.java
 *
 * Created on 17-nov-2011, 18.43.56
 */
package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import logica.Pedina;

/**
 * 
 * @author PiGix
 */
public class PanelloTabellone extends javax.swing.JPanel {

	private static final long serialVersionUID = 4138680276042468306L;
	Font font = new Font("TimesRoman", Font.BOLD, 14);
	Image image;
	ArrayList<Pedina> pedine;

	/** Creates new form PanelloTabellone */
	public PanelloTabellone() {
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL imageURL   = cldr.getResource("img/TabelloneDEF.jpg");
		image = new ImageIcon(imageURL).getImage();
		pedine = new ArrayList<Pedina>();
		initComponents();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
//		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		for (int j = 0; j < pedine.size(); j++) {
			Pedina p = pedine.get(j);
			Image immp = p.getImmaginePersonale();
			g.setFont(font);
			// g.setColor(Color.YELLOW);
			g.drawString("G" + (j + 1), p.getX() + 10, p.getY() - 5);
			g.drawImage(immp, p.getX(), p.getY(), immp.getWidth(null), immp.getHeight(null), null);
		}
	}

	public void addpedina(Pedina p) {
		pedine.add(p);
		repaint();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
//		setMaximumSize(new java.awt.Dimension(866, 559));
//		setMinimumSize(new java.awt.Dimension(866, 559));
//		setPreferredSize(new java.awt.Dimension(866, 599));
		
		JLabel image = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/TabelloneDEF.jpg")));
		add(image);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables
}
