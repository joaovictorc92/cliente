package br.ufma.sistemasdistribuidos.apresentacao;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import br.ufma.sistemasdistribuidos.form.IUsuario;
import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;
import java.awt.TextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/*
 * Janela do Ouvinte
 */
public class Ouvinte extends JFrame {

	private JPanel contentPane;
    private ImageIcon imagemCorrente;
    JLabel lblNewLabel;
    JLabel lblNewLabel_1;
    String label;
    private TextArea textArea;
    private JLabel lblNewLabel_2;
    String usuariosAssistindo;
    private JLabel lblNewLabel_3;
    private JComboBox comboBox;
    private JButton btnRequisitar;
    private String [] opcoesSlide;
    
	
    
    public String[] getOpcoesSlide() {
		return opcoesSlide;
	}
	public void setOpcoesSlide(String[] opcoesSlide) {
		this.opcoesSlide = opcoesSlide;
	}
	public void setModoApresentacao(){
    	lblNewLabel_3.setVisible(false);
    	comboBox.setVisible(false);
    	btnRequisitar.setVisible(false);
    }
    public void setModoDiscussao(){
    	lblNewLabel_3.setVisible(true);
    	comboBox.setModel(new DefaultComboBoxModel(opcoesSlide));
    	comboBox.setVisible(true);
    	btnRequisitar.setVisible(true);
    }
    
	public String getUsuariosAssistindo() {
		return usuariosAssistindo;
	}



	public void setUsuariosAssistindo(String usuariosAssistindo) {
		this.usuariosAssistindo = usuariosAssistindo;
		textArea.setText(usuariosAssistindo);
	}



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



	public Ouvinte(final ObjectOutputStream output,final IUsuario usuario) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				disable();
				Mensagem mensagem = new Mensagem(); // = (Mensagem) usuario;
				mensagem.setTipo(20);
				mensagem.setObject(usuario);
				Serializacao.serializa(output, mensagem);
			}
		});
		setBounds(100, 100, 585, 596);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel(imagemCorrente);
		lblNewLabel.setBounds(10, 25, 532, 416);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel(label);
		lblNewLabel_1.setBounds(448, 452, 53, 22);
		contentPane.add(lblNewLabel_1);
		
		textArea = new TextArea();
		textArea.setBounds(10, 492, 136, 55);
		contentPane.add(textArea);
		
		lblNewLabel_2 = new JLabel("Usu\u00E1rios na apresenta\u00E7\u00E3o");
		lblNewLabel_2.setBounds(10, 460, 151, 26);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Slide");
		lblNewLabel_3.setBounds(201, 492, 46, 14);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);
		
		comboBox = new JComboBox();
		comboBox.setBounds(242, 489, 53, 20);
		contentPane.add(comboBox);
		comboBox.setVisible(false);
		
		btnRequisitar = new JButton("Requisitar");
		btnRequisitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mensagem msg = new Mensagem();
				msg.setTipo(25);
				msg.setIdApresentacao(comboBox.getSelectedIndex());
				Serializacao.serializa(output, msg);
			}
		});
		btnRequisitar.setBounds(305, 488, 110, 23);
		contentPane.add(btnRequisitar);
		btnRequisitar.setVisible(false);
	}
}
