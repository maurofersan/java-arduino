package com.mauro.javaduino.thread;

import javax.swing.JPanel;

import com.mauro.javaduino.shape.Pelota;

public class MoverPelotaThread extends Thread {

	private Pelota pelota;
	private JPanel panel;
	private boolean isPaused;

	public MoverPelotaThread(Pelota pelota, JPanel panel) {
		this.pelota = pelota;
		this.panel = panel;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			if (!isPaused) {
				pelota.muevePelota(panel.getBounds());
				//panel.repaint();
				panel.paint(panel.getGraphics());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	public void pausar() {
		isPaused = true;
	}
	
	public void continuar() {
		isPaused = false;
	}

	public boolean isPaused() {
		return isPaused;
	}

}
