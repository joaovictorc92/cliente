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
import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;

public class ClientePonto {
	String url;
	int porta;
	Socket cliente;
	Slide slide;
	public ClientePonto(String url, int porta) throws UnknownHostException, IOException{
		this.url = url;
		this.porta = porta;
		this.cliente = new Socket(url,porta);
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
			Ouvinte ouvinte = new Ouvinte();
			Mensagem mensagem;
			while(slide.isEnabled()){
				mensagem = Serializacao.deserializa(input);
				if(mensagem.getTipo()==17){
					ImageIcon imagem = (ImageIcon) mensagem.getObject();
					ouvinte.setLabel(mensagem.getMensagemTexto());
					ouvinte.setImagemCorrente(imagem);
                    ouvinte.setVisible(true);
				}
				if(mensagem.getTipo()==18){
					ouvinte.setVisible(false);
					JOptionPane.showMessageDialog(null, "Palestrante interrompeu a apresentação");
				}
			}
			
		}
		
	}
	

}
