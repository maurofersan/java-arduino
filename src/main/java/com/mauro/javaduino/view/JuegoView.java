package com.mauro.javaduino.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	private JPanel panelFondo;
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
		
		panelFondo = new JPanel()  {
			
			
		};
		panelFondo.setBounds(0, 0, 844, 460);
		panelFondo.setLayout(null);
		frame.getContentPane().add(panelFondo);

		panel = new JPanel() {

			private static final long serialVersionUID = 1L;
			
			Image image = new ImageIcon(getClass().getClassLoader().getResource("img/fondoJuego472.png")).getImage();
			
			public void paintComponent(Graphics g) {
				System.out.println("exec");
				super.paintComponent(g);
				
				g.drawImage(image, 0, 0, null);

//				setOpaque(false);
				Graphics2D g2 = (Graphics2D) g;
				if (pelota != null) {
					g2.fill(pelota.getShade());
				}
				Rectangle2D rectangle = new Rectangle2D.Double(Rectangle.startX, 441, Rectangle.width, Rectangle.height);
				g2.fill(rectangle);
				
			}
		};
		panel.setBounds(0, 0, 844, 460);
		panel.setLayout(null);
		panel.setOpaque(false);
		frame.getContentPane().add(panel);

		panelLower = new JPanel();
		panelLower.setBounds(0, 460, 844, 112);
		panelLower.setBackground(new Color(135, 144, 23));
		panelLower.setLayout(null);
		frame.getContentPane().add(panelLower);
		// SALIR----------------------------------------------------------------------------------
		btnSalir = new JButton(new ImageIcon(getClass().getClassLoader().getResource("img/quit.png")));
		btnSalir.setBounds(481, 22, 57, 57);
		btnSalir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				frame.dispose();
			}
		});
		panelLower.add(btnSalir);
		//PAUSA-----------------------------------------------------------------------------------
		btnPause = new JButton();
		btnPause.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/pause.png")));
		btnPause.setBounds(319, 22, 57, 57);
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hilo.isPaused()) {
					hilo.continuar();
					btnPause.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/pause.png")));
				} else {
					hilo.pausar();
					btnPause.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/continue.png")));
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
