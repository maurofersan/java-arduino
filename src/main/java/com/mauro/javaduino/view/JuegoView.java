package com.mauro.javaduino.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mauro.javaduino.shape.Pelota;
import com.mauro.javaduino.shape.Rectangle;
import com.mauro.javaduino.thread.MoverPelotaThread;

public class JuegoView {

	protected JFrame frame;
	private JPanel panel;
	private JPanel panelLower;
	private JButton btnSalir;
	private Pelota pelota;
	private static JuegoView instance;
	private JButton btnPause;
	private MoverPelotaThread hilo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JuegoView view = new JuegoView();
					view.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static JuegoView getInstance() {
		if (instance == null) {
			return instance = new JuegoView();
		}
		return instance;
	}

	public JuegoView() {
		initializate();
	}

	public void initializate() {
		frame = new JFrame("Juego Pelota");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("img/playGames.png")).getImage());
		frame.setBounds(100, 100, 850, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		panel = new JPanel() {

			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				if (pelota != null) {
					g2.fill(pelota.getShade());
				}
				Rectangle2D rectangle = new Rectangle2D.Double(Rectangle.startX, 441, Rectangle.width,
						Rectangle.height);
				g2.fill(rectangle);
			}
		};
		panel.setBounds(0, 0, 834, 460);
		panel.setLayout(null);
		frame.getContentPane().add(panel);

		panelLower = new JPanel();
		panelLower.setBounds(0, 460, 834, 102);
		panelLower.setBackground(Color.CYAN);
		panelLower.setLayout(null);
		frame.getContentPane().add(panelLower);
		// SALIR----------------------------------------------------------------------------------
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(526, 37, 89, 23);
		btnSalir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				frame.dispose();
			}
		});
		panelLower.add(btnSalir);
		//PAUSA-----------------------------------------------------------------------------------
		btnPause = new JButton("Pause");
		btnPause.setBounds(385, 37, 89, 23);
		btnPause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnPause.getText().equalsIgnoreCase("Pause")) {
					hilo.pausar();
					btnPause.setText("Continue");
				} else {
					hilo.continuar();
					btnPause.setText("Pause");
				}
			}
		});
		panelLower.add(btnPause);
		
		frame.getContentPane().add(panelLower);
	}
	
	public void comienzaJuego() {
		pelota = new Pelota();
		
		hilo = new MoverPelotaThread(pelota, panel);
		hilo.start();
		
		Thread hiloRectangle = new Thread(new Rectangle());
		hiloRectangle.start();
	}

}
