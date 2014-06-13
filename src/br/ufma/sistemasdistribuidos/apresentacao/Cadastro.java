package br.ufma.sistemasdistribuidos.apresentacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.ufma.sistemasdistribuidos.form.IUsuario;
import br.ufma.sistemasdistribuidos.form.Usuario;

public class Cadastro extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */

	public Cadastro(final Login login) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cadastro");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setBounds(170, 11, 76, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNome.setBounds(27, 48, 61, 31);
		contentPane.add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(84, 55, 324, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLogin.setBounds(27, 103, 49, 31);
		contentPane.add(lblLogin);
		
		textField_1 = new JTextField();
		textField_1.setBounds(84, 110, 149, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblSenha.setBounds(243, 111, 46, 14);
		contentPane.add(lblSenha);
		
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(293, 109, 115, 21);
		contentPane.add(passwordField);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUsuario usuario = new Usuario();
				usuario.setNome(textField.getText());
				usuario.setLogin(textField_1.getText());
				usuario.setSenha(String.valueOf(passwordField.getPassword()));
				setVisible(false);
			}
		});
		
		btnConfirmar.setBounds(243, 191, 143, 23);
		contentPane.add(btnConfirmar);
		
		
		JButton btnNewButton = new JButton("Voltar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				login.setVisible(true);
				
			}
		});
		btnNewButton.setBounds(27, 191, 149, 23);
		contentPane.add(btnNewButton);
	}

}
