package com.mauro.javaduino.shape;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JOptionPane;

public class Pelota {

	private final double TAMX = 15;
	private final double TAMY = 15;
	private double x;
	private double y;
	private double dx = 1;
	private double dy = 1;

	public void muevePelota(Rectangle2D limite) {
		x += dx;
		y += dy;

		if ((y + TAMY + Rectangle.height >= limite.getMaxY()) && (x >= Rectangle.startX && x <= Rectangle.width + Rectangle.startX)) {
			dy = -dy;
			System.out.println("REBOTA");
			return;
		}
		if (x + TAMX >= limite.getMaxX()) {
			dx = -dx;
			System.out.println("CHOCO PARED DERECHA");
			return;
		}
		if (y == limite.getMinY()) {
			dy = -dy;
			System.out.println("CHOCO ARRIBA");
			return;
		}
		if (x == limite.getMinX()) {
			dx = -dx;
			System.out.println("CHOCO PARED IZQUIERDA");
			return;
		}
		if (y + TAMY >= limite.getMaxY()) {
			JOptionPane.showMessageDialog(null, "GAME OVER!!!");
			System.exit(0);
		}
	}

	public Ellipse2D getShade() {
		return new Ellipse2D.Double(x, y, TAMX, TAMY);
	}

}
