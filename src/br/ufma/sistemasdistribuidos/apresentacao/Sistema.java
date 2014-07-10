package br.ufma.sistemasdistribuidos.apresentacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import br.ufma.sistemasdistribuidos.form.Apresentacao;
import br.ufma.sistemasdistribuidos.form.IApresentacao;
import br.ufma.sistemasdistribuidos.form.IUsuario;
import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;
/*
 * Janela do sistema
 */
public class Sistema extends JFrame {

	private JPanel contentPane;
	JTextArea textArea;
	private ArrayList<IUsuario> usuariosLogados;
	Login login;
	JComboBox comboBox;
	JComboBox comboBox_1;
	IUsuario usuario;
	
	public IUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(IUsuario usuario) {
		this.usuario = usuario;
	}
	public void carregarListaApresentacoesDisponiveis(ArrayList<Apresentacao> listaApresentacoes){
		String[] opcoes = new String[listaApresentacoes.size()+1];
		opcoes[0] = "";
		int i=1;
		for(IApresentacao apresentacao: listaApresentacoes){
			opcoes[i]= apresentacao.getNome();
			i++;
		}
		comboBox.setModel(new DefaultComboBoxModel(opcoes));
	}
	public void carregarListaApresentacoesAndamento(ArrayList<Apresentacao> listaApresentacoesAndamento){
		System.out.println("Tamanho da apresentação:"+listaApresentacoesAndamento.size());
		if(listaApresentacoesAndamento.size()>0){
			String[] opcoes = new String[listaApresentacoesAndamento.size()+1];
			opcoes[0] = "";
			int i=1;
			for(IApresentacao apresentacao: listaApresentacoesAndamento){
				opcoes[i]= apresentacao.getNome();
				i++;
			}
			comboBox_1.setModel(new DefaultComboBoxModel(opcoes));
		}
		else{
			comboBox_1.setModel(new DefaultComboBoxModel());
		}
	}
	
	public void setUsuariosLogados(String texto){
		textArea.setText(texto);
	}

	public Sistema(final Login login,final ObjectOutputStream output) {
		this.login = login;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev){
				login.setConectado(false);
				Mensagem mensagem = new Mensagem(); //= (Mensagem) usuario;
				mensagem.setTipo(0);
			    Serializacao.serializa(output, mensagem);
			}
		});
		setBounds(100, 100, 585, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblSistemaDeApresentaes = new JLabel("              Sistema de Apresenta\u00E7\u00F5es");
		lblSistemaDeApresentaes.setFont(new Font("Times New Roman", Font.BOLD, 18));

	    comboBox = new JComboBox(new DefaultComboBoxModel());
		JLabel lblNewLabel = new JLabel("Apresenta\u00E7\u00F5es Dispon\u00EDveis");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));

		JLabel lblApresentaesEmAndamento = new JLabel("Apresenta\u00E7\u00F5es em andamento");
		lblApresentaesEmAndamento.setFont(new Font("Times New Roman", Font.BOLD, 12));

		comboBox_1 = new JComboBox();
		
		JButton btnNewButton = new JButton("Carregar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()>0){
					Mensagem mensagem = new Mensagem();
					mensagem.setTipo(8);
					mensagem.setObject(usuario);
					mensagem.setIdApresentacao(comboBox.getSelectedIndex());
					Serializacao.serializa(output, mensagem);
				}
			}
		});

		JButton btnNewButton_1 = new JButton("Entrar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox_1.getSelectedIndex()>0){
					Mensagem mensagem = new Mensagem();
					mensagem.setTipo(14);
					mensagem.setObject(usuario);
					mensagem.setIdApresentacao(comboBox_1.getSelectedIndex());
					Serializacao.serializa(output, mensagem);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();

		JScrollPane scrollPane_2 = new JScrollPane();

		JLabel lblNewLabel_1 = new JLabel("      Usu\u00E1rios Logados");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 12));

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mensagem mensagem = new Mensagem();
				mensagem.setTipo(5);
				Serializacao.serializa(output, mensagem);
			
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(105)
							.addComponent(lblSistemaDeApresentaes, GroupLayout.PREFERRED_SIZE, 346, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
							.addComponent(btnSair))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(comboBox, Alignment.LEADING, 0, 117, Short.MAX_VALUE))
								.addComponent(scrollPane_2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
							.addGap(45)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(296)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblApresentaesEmAndamento, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSistemaDeApresentaes, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSair))
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
					.addComponent(lblApresentaesEmAndamento, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(52))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);

		textArea = new JTextArea();
		textArea.setEnabled(false);
		scrollPane_2.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);
	}
}