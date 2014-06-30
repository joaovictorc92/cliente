package br.ufma.sistemasdistribuidos.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.ufma.sistemasdistribuidos.apresentacao.Ouvinte;
import br.ufma.sistemasdistribuidos.apresentacao.Slide;
import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;

public class ServidorCliente implements Runnable {
	ServerSocket servidorCliente;
	Socket cliente;
	Slide slide;
	int ip, porta;
	ArrayList<ObjectOutputStream> listaUsuariosApresentacao;

	public ServidorCliente(Slide slide, int porta) {
		this.slide = slide;
		this.porta = porta;
		listaUsuariosApresentacao = new ArrayList<ObjectOutputStream>();
	}

	@Override
	public void run() {
		try {
			
			servidorCliente = new ServerSocket(porta);

			while (!Thread.currentThread().isInterrupted()) {
				cliente = servidorCliente.accept();
				ObjectInputStream input = new ObjectInputStream(
						cliente.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(
						cliente.getOutputStream());
				System.out.println("Nova conexão P2P com o cliente "
						+ cliente.getInetAddress().getHostAddress());
				listaUsuariosApresentacao.add(output);
				Thread t = new Thread(new EscutaCliente(output, input));
				t.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
	public void enviarParaOuvintes(Mensagem msg){
		for(ObjectOutputStream outputOuvinte : listaUsuariosApresentacao){
			Serializacao.serializa(outputOuvinte, msg);
		}
	}
	
	public class EscutaCliente implements Runnable {
		ObjectOutputStream output;
		ObjectInputStream input;

		public EscutaCliente(ObjectOutputStream output,
				ObjectInputStream input) {
			this.output = output;
			this.input = input;
		}

		@Override
		public void run() {
			int tipo = 99;
			Mensagem msg = new Mensagem();
			msg.setTipo(17);
			msg.setMensagemTexto(slide.getLabel());
			msg.setObject(slide.getImagemCorrente());
			enviarParaOuvintes(msg);
			slide.setListaOuvintes(listaUsuariosApresentacao);
			

		}

	}


}
