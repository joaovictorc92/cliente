package br.ufma.sistemasdistribuidos.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import br.ufma.sistemasdistribuidos.apresentacao.Ouvinte;
import br.ufma.sistemasdistribuidos.apresentacao.Slide;
import br.ufma.sistemasdistribuidos.form.IUsuario;
import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.form.Usuario;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;
/*
 * Classe respons�vel por fazer um novo cliente que se conectou palestrante
 */
public class ClientePonto {
	String url;
	int porta;
	Socket cliente;
	Slide slide;
	IUsuario usuario;
	public ClientePonto(String url, int porta,IUsuario usuario) throws UnknownHostException, IOException{
		this.url = url;
		this.porta = porta;
		this.cliente = new Socket(url,porta);
		this.usuario = usuario;
		ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream()); // Cria fluxo de saida para serializar objetos
		ObjectInputStream input =  new ObjectInputStream(cliente.getInputStream());
		Thread t = new Thread(new EscutaServidorCliente(output,input));
		slide = new Slide(new ArrayList<ImageIcon>(), output);
		t.start();
	}
	
	private class EscutaServidorCliente implements Runnable{
        ObjectInputStream input;
        ObjectOutputStream output;
		
		public EscutaServidorCliente(ObjectOutputStream output,ObjectInputStream input){
			this.input = input;
			this.output = output;
		}
		
		@Override
		public void run() {
			Ouvinte ouvinte = new Ouvinte(output,usuario); // Envia mensagem para se conectar com palestrante
			Mensagem mensagem;
			Mensagem msg = new Mensagem();
			msg.setTipo(19);
			msg.setObject(usuario);
			Serializacao.serializa(output, msg);
			int tipo=0;
			while(ouvinte.isEnabled()&&tipo!=18){
				mensagem = Serializacao.deserializa(input);
				tipo = mensagem.getTipo();
				if(mensagem.getTipo()==17){ // Recebe imagem de palestrante
					ImageIcon imagem = (ImageIcon) mensagem.getObject();
					ouvinte.setLabel(mensagem.getMensagemTexto());
					ouvinte.setImagemCorrente(imagem);
                    ouvinte.setVisible(true);
				}
				if(mensagem.getTipo()==18){ // Se palestrante interrompeu a apresenta��o
					ouvinte.setVisible(false);
					JOptionPane.showMessageDialog(null, "Palestrante interrompeu a apresenta��o");
				}
				if(mensagem.getTipo()==22){ // recebe os usuarios que est�o presentes na apresenta��o
					ouvinte.setUsuariosAssistindo(mensagem.getMensagemTexto());
				}
				if(mensagem.getTipo()==23){ // Seta o modo apresenta��o
					ouvinte.setModoApresentacao();
				}
				if(mensagem.getTipo()==24){ // Seta o modo discuss�o
					JOptionPane.showMessageDialog(null, "Palestrante mudou o modo para discuss�o");
					int slideSize = mensagem.getIdApresentacao();
					String[] texto = new String[slideSize];
					for(int i=0;i<slideSize;i++){
						texto[i]=""+(i+1);
					}
					ouvinte.setOpcoesSlide(texto);
					ouvinte.setModoDiscussao();
				}
			}
			
		}
		
	}
	

}
