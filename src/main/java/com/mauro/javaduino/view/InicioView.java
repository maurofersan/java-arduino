package com.mauro.javaduino.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;

public class InicioView {

	private JFrame frame;
	private JLabel lblJava;
	private JLabel lblArduino;
	private JButton btnPlay;
	private JLabel lblLogo;
	private JDesktopPane desktop;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioView view = new InicioView();
					view.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InicioView() {
		frame = new JFrame("Java & Arduino");
		frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("img/playGames.png")).getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 850, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		desktop = new JDesktopPane(){

			private static final long serialVersionUID = 1L;

			private Image imagen = new ImageIcon(getClass().getClassLoader().getResource("img/fondo1.png")).getImage();
			@Override
			public void paintChildren( Graphics g){
				g.drawImage(imagen,0,0, getWidth(), getHeight(),this);
				setOpaque(true);
				super.paintChildren(g);
			}
		};
		frame.setContentPane(desktop);

		lblJava = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/java.png")));
		lblJava.setBounds(196, 66, 165, 165);
		frame.getContentPane().add(lblJava);

		lblArduino = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/arduino.png")));
		lblArduino.setBounds(335, 178, 157, 165);
		frame.getContentPane().add(lblArduino);
		
		lblLogo = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("img/arduino2.png")));
		lblLogo.setBounds(517, 83, 128, 140);
		frame.getContentPane().add(lblLogo);

		btnPlay = new JButton("Play", new ImageIcon(getClass().getClassLoader().getResource("img/playGames.png")));
		btnPlay.setFont(new Font("Play", Font.ITALIC, 30));
		btnPlay.setBounds(335, 412, 165, 73);
		btnPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JuegoView view = JuegoView.getInstance();
				frame.dispose();
				view.frame.setVisible(true);

				view.comienzaJuego();
			}
		});
		frame.getContentPane().add(btnPlay);

	}
}
