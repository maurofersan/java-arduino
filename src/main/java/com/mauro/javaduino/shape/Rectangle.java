package com.mauro.javaduino.shape;

import com.mauro.javaduino.conection.ReaderWriterArduino;
import com.mauro.utilitario.util.ConvertUtil;

public class Rectangle implements Runnable {

	public static double startX = 380;
	public static double width = 150;
	public static double height = 15;

	@Override
	public void run() {
		ReaderWriterArduino lector = ReaderWriterArduino.getInstance();
		while (true) {
			try {
				String ultimoDato = lector.getUltimoDato("MOV_PTN");
				Double dato = ConvertUtil.toDouble(ultimoDato, 566.0);
				// System.out.println("'" + lector.getUltimoDato() + "'");
				startX = changeInterval(dato, 0, 1023, 0, 688);
				// System.out.println(startX);
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public double changeInterval(double x, double inMin, double inMax, double outMin, double outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}

}
