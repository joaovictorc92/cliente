package br.ufma.sistemasdistribuidos.apresentacao;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.TextArea;
import javax.swing.JComboBox;
/*
 * Janela do Palestrante
 */
public class Slide extends JFrame {

	private JPanel contentPane;
	private int i = 0;
	private int idApresentacao;
	JLabel lblNewLabel;
	JLabel lblNewLabel_1;
	ImageIcon imagemCorrente;
	ArrayList<ObjectOutputStream> listaOuvintes=null;
	String label;
	TextArea textArea;
	String usuariosApresentacao;
	JComboBox comboBox;
	JButton btnNewButton,btnNewButton_1;
	ArrayList<ImageIcon> listaImagens;
	int modo;
	
	public int getListaImagensSize(){
		
		return listaImagens.size();
	}
	
	public int getModo() {
		return modo;
	}

	public void setModo(int modo) {
		this.modo = modo;
	}

	public String getUsuariosApresentacao() {
		return usuariosApresentacao;
	}

	public void setUsuariosApresentacao(String usuariosApresentacao) {
		this.usuariosApresentacao = usuariosApresentacao;
		textArea.setText(usuariosApresentacao);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Create the frame.
	 */
    
	public Slide(final ArrayList<ImageIcon> listaImagens,
			final ObjectOutputStream output) {
		this.listaImagens = listaImagens;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				disable();
				Mensagem mensagem = new Mensagem(); // = (Mensagem) usuario;
				mensagem.setTipo(11);
				mensagem.setIdApresentacao(idApresentacao);
				Serializacao.serializa(output, mensagem);// Envia mensagem de encerramento de execução da apresentação
				if(listaOuvintes!=null){
					mensagem.setTipo(18);
					enviarParaOuvintes(mensagem);
				}
				
			}
		});
		setBounds(100, 100, 643, 649);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		if (!listaImagens.isEmpty() && listaImagens != null) {
			imagemCorrente = listaImagens.get(0);
		}
		lblNewLabel = new JLabel(imagemCorrente);
		lblNewLabel.setBounds(30, 25, 529, 447);
		contentPane.add(lblNewLabel);

		btnNewButton = new JButton("Voltar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (i > 0) {
					i--;
					imagemCorrente = listaImagens.get(i);
					lblNewLabel.setIcon(imagemCorrente);
					label = i + 1 + "/" + listaImagens.size();
					lblNewLabel_1.setText(label);
					if(listaOuvintes!=null){
						Mensagem msg = new Mensagem();
						msg.setTipo(17);
						msg.setMensagemTexto(label);
						msg.setObject(imagemCorrente);
						enviarParaOuvintes(msg);
					}
				}
			}
		});
		btnNewButton.setBounds(202, 481, 89, 23);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("Avançar");
		if (listaImagens.isEmpty()) {
			btnNewButton.setEnabled(false);
			btnNewButton_1.setEnabled(false);
		}
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (i < listaImagens.size() - 1) {
					i++;
					imagemCorrente = listaImagens.get(i);
					lblNewLabel.setIcon(imagemCorrente);
					label = i + 1 + "/" + listaImagens.size();
					lblNewLabel_1.setText(label);
					if(listaOuvintes!=null){
						Mensagem msg = new Mensagem();
						msg.setTipo(17);
						msg.setMensagemTexto(label);
						msg.setObject(imagemCorrente);
						enviarParaOuvintes(msg);
					}
				}
			}
		});
		btnNewButton_1.setBounds(300, 481, 89, 23);
		contentPane.add(btnNewButton_1);
		label = i + 1 + "/" + listaImagens.size();
		lblNewLabel_1 = new JLabel(label);
		lblNewLabel_1.setBounds(459, 483, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		textArea = new TextArea();
		textArea.setBounds(10, 547, 162, 53);
		contentPane.add(textArea);
		
		JLabel lblNewLabel_2 = new JLabel("Usu\u00E1rios assistindo");
		lblNewLabel_2.setBounds(30, 515, 142, 26);
		contentPane.add(lblNewLabel_2);
		
		String[] opcao = {"Apresentação","Discussão"};
		comboBox = new JComboBox(opcao);
		comboBox.setBounds(384, 547, 106, 20);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_3 = new JLabel("Modo:");
		lblNewLabel_3.setBounds(313, 547, 61, 20);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton_2 = new JButton("Carregar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()==0){//setar modo apresentação
					modo=0;
					btnNewButton.setEnabled(true);
					btnNewButton_1.setEnabled(true);
					if(listaOuvintes!=null){
						Mensagem msg = new Mensagem();
						msg.setTipo(23);
						enviarParaOuvintes(msg);
					}
				}
				else{ // setar modo discussão
		            modo=1;
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(false);
					if(listaOuvintes!=null){
						Mensagem msg = new Mensagem();
						msg.setTipo(24);
						msg.setIdApresentacao(listaImagens.size());
						enviarParaOuvintes(msg);
					}
				}
				
			}
		});
		btnNewButton_2.setBounds(500, 547, 89, 23);
		contentPane.add(btnNewButton_2);
	}

	public void enviarParaOuvintes(Mensagem msg){
		for(ObjectOutputStream outputOuvinte : listaOuvintes){
			Serializacao.serializa(outputOuvinte, msg);
		}
	}
	
	public ArrayList<ObjectOutputStream> getListaOuvintes() {
		return listaOuvintes;
	}

	public void setListaOuvintes(ArrayList<ObjectOutputStream> listaOuvintes) {
		this.listaOuvintes = listaOuvintes;
	}

	public void setImagemCorrente(ImageIcon imagem) {
		this.imagemCorrente = imagem;
	}

	public ImageIcon getImagemCorrente() {
		return this.imagemCorrente;
	}

	public int getIdApresentacao() {
		return idApresentacao;
	}

	public void setIdApresentacao(int idApresentacao) {
		this.idApresentacao = idApresentacao;
	}
	public void setSlideDiscussao(int idSlide){
		imagemCorrente = listaImagens.get(idSlide);
		lblNewLabel.setIcon(imagemCorrente);
		if(listaOuvintes!=null){
			Mensagem msg = new Mensagem();
			msg.setTipo(17);
			msg.setMensagemTexto(label);
			msg.setObject(imagemCorrente);
			enviarParaOuvintes(msg);
		}
		
	}
}
