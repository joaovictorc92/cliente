package br.ufma.sistemasdistribuidos.apresentacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.ufma.sistemasdistribuidos.form.IUsuario;
import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.form.Usuario;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private int idconexao;
	private boolean conectado;

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}
	
	public void limpaLogin(){
		textField.setText("");
		passwordField.setText("");
	}

	public Login(final ObjectOutputStream output) throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev){
				setConectado(false);
				Mensagem mensagem = new Mensagem(); //= (Mensagem) usuario;
				mensagem.setTipo(0);
			    try {
					Serializacao.serializa(output, mensagem);
			    	output.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(20, 44, 98, 27);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(20, 75, 145, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(20, 106, 77, 20);
		panel.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(20, 138, 145, 20);
		panel.add(passwordField);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUsuario usuario = new Usuario();
				if(!textField.getText().isEmpty() && passwordField.getPassword().length>0){
					usuario.setLogin(textField.getText());
					usuario.setSenha(String.valueOf(passwordField.getPassword()));
					Mensagem mensagem = new Mensagem(); //= (Mensagem) usuario;
				    mensagem.setObject(usuario);
					mensagem.setTipo(1);
					Serializacao.serializa(output, mensagem);
					
			    }
		}
		});
		btnEntrar.setBounds(20, 215, 98, 23);
		panel.add(btnEntrar);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				chamaTelaCadastro();
			}
		});
		btnNewButton.setBounds(135, 215, 98, 23);
		panel.add(btnNewButton);
		
		
		
	}
	
	public void chamaTelaCadastro(){
		Cadastro cadastro = new Cadastro(this);
		cadastro.setVisible(true);
	}

	public int getIdconexao() {
		return idconexao;
	}

	public void setIdconexao(int idconexao) {
		this.idconexao = idconexao;
	}
	
	
}
