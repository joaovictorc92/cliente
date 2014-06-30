package br.ufma.sistemasdistribuidos.apresentacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class Ouvinte extends JFrame {

	private JPanel contentPane;
    private ImageIcon imagemCorrente;
    JLabel lblNewLabel;
    JLabel lblNewLabel_1;
    String label;
    
	
	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public ImageIcon getImagemCorrente() {
		return imagemCorrente;
	}



	public void setImagemCorrente(ImageIcon imagemCorrente) {
		this.imagemCorrente = imagemCorrente;
		lblNewLabel.setIcon(imagemCorrente);
		lblNewLabel_1.setText(label);
	}



	public Ouvinte() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel(imagemCorrente);
		lblNewLabel.setBounds(10, 11, 491, 438);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel(label);
		lblNewLabel_1.setBounds(448, 452, 53, 22);
		contentPane.add(lblNewLabel_1);
	}
}
