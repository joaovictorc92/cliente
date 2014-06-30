package br.ufma.sistemasdistribuidos.apresentacao;

import java.awt.BorderLayout;
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
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Slide extends JFrame {

	private JPanel contentPane;
	private int i = 0;
	private int idApresentacao;
	JLabel lblNewLabel;
	JLabel lblNewLabel_1;
	ImageIcon imagemCorrente;
	ArrayList<ObjectOutputStream> listaOuvintes=null;
	String label;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				disable();
				Mensagem mensagem = new Mensagem(); // = (Mensagem) usuario;
				mensagem.setTipo(11);
				mensagem.setIdApresentacao(idApresentacao);
				if(listaOuvintes!=null){
					Serializacao.serializa(output, mensagem);// Envia mensagem de encerramento de execução da apresentação
					mensagem.setTipo(18);
					enviarParaOuvintes(mensagem);
				}
			}
		});
		setBounds(100, 100, 600, 573);
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

		JButton btnNewButton = new JButton("Voltar");
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
		btnNewButton.setBounds(202, 481, 89, 42);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Avançar");
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
		btnNewButton_1.setBounds(300, 481, 89, 42);
		contentPane.add(btnNewButton_1);
		String label = i + 1 + "/" + listaImagens.size();
		lblNewLabel_1 = new JLabel(label);
		lblNewLabel_1.setBounds(508, 481, 46, 14);
		contentPane.add(lblNewLabel_1);
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
}
