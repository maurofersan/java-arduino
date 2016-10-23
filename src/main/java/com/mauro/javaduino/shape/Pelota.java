package com.mauro.javaduino.shape;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

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

		if ((y + TAMY + 15 >= limite.getMaxY()) && (x >= Rectangle.startX && x <= 480)) {
			dy = -dy;
		}
		if (x + TAMX >= limite.getMaxX()) {
			dx = -dx;
		}
		if (y == limite.getMinY()) {
			dy = -dy;
		}
		if (x == limite.getMinX()) {
			dx = -dx;
		}
	}

	public Ellipse2D getShade() {
		return new Ellipse2D.Double(x, y, TAMX, TAMY);
	}

}
