package br.ufma.sistemasdistribuidos.apresentacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Slide extends JFrame {

	private JPanel contentPane;
    private int i = 0;
    JLabel lblNewLabel;
    JLabel lblNewLabel_1;
	
	/**
	 * Create the frame.
	 */
	public Slide(final ArrayList<ImageIcon> listaImagens) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 573);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel(listaImagens.get(0));
		lblNewLabel.setBounds(30, 25, 529, 447);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Voltar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(i>0){
					i--;
					lblNewLabel.setIcon(listaImagens.get(i));
					String label = i+1 + "/" + listaImagens.size();
					lblNewLabel_1.setText(label);
				}
			}
		});
		btnNewButton.setBounds(202, 481, 89, 42);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Avançar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(i< listaImagens.size()-1){
					i++;
					lblNewLabel.setIcon(listaImagens.get(i));
					String label = i+1 + "/" + listaImagens.size();
					lblNewLabel_1.setText(label);
				}
			}
		});
		btnNewButton_1.setBounds(300, 481, 89, 42);
		contentPane.add(btnNewButton_1);
		String label = i+1 +"/"+listaImagens.size();
		lblNewLabel_1 = new JLabel(label);
		lblNewLabel_1.setBounds(508, 481, 46, 14);
		contentPane.add(lblNewLabel_1);
	}
}
