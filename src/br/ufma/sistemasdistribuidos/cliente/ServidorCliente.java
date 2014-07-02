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
import br.ufma.sistemasdistribuidos.form.IUsuario;
import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.form.Usuario;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;

/*
 * Classe que faz o cliente como palestrante
 */
public class ServidorCliente implements Runnable {
	ServerSocket servidorCliente;
	Socket cliente;
	Slide slide;
	int ip, porta;
	ArrayList<ObjectOutputStream> listaClientesApresentacao;
	ArrayList<Usuario> listaUsuariosApresentacao;

	public ServidorCliente(Slide slide, int porta,IUsuario user) {
		this.slide = slide;
		this.porta = porta;
		listaClientesApresentacao = new ArrayList<ObjectOutputStream>();
		listaUsuariosApresentacao= new ArrayList<Usuario>();
		listaUsuariosApresentacao.add((Usuario) user);
		setUsuariosApresentacao();
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
				listaClientesApresentacao.add(output);
				Thread t = new Thread(new EscutaCliente(output, input));
				t.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
	public void enviarParaOuvintes(Mensagem msg){// Envia para a mensagem para todos os ouvintes
		for(ObjectOutputStream outputOuvinte : listaClientesApresentacao){
			Serializacao.serializa(outputOuvinte, msg);
		}
	}
	
	public void setUsuariosApresentacao(){// Seta os usuários presentes na apresentação
		String texto = "";
		for(Usuario usuario : listaUsuariosApresentacao){
			texto += usuario.getLogin() + "\n";
		}
		//System.out.println("Usuarios:"+texto);
		slide.setUsuariosApresentacao(texto);
		Mensagem msg = new Mensagem();
		msg.setTipo(22);
		msg.setMensagemTexto(texto);
		enviarParaOuvintes(msg);
	}
	
	public class EscutaCliente implements Runnable { // Escuta os palestrantes
		ObjectOutputStream output;
		ObjectInputStream input;

		public EscutaCliente(ObjectOutputStream output,
				ObjectInputStream input) {
			this.output = output;
			this.input = input;
		}

		@Override
		public void run() {
		    Mensagem mensagem;
			Mensagem msg = new Mensagem();
			int tipo=0;
			IUsuario usuario=null;
			
			while(tipo!=20){
			mensagem = Serializacao.deserializa(input);
			tipo = mensagem.getTipo();
			if(mensagem.getTipo()==19){ // Ouvinte entrando na apresentação em andamento
				usuario = (IUsuario) mensagem.getObject();
				listaUsuariosApresentacao.add((Usuario) usuario);
				setUsuariosApresentacao();
				msg.setTipo(17);
				msg.setMensagemTexto(slide.getLabel());
				msg.setObject(slide.getImagemCorrente());
				enviarParaOuvintes(msg);
				slide.setListaOuvintes(listaClientesApresentacao);
				if(slide.getModo()==1) // setar modo discussão
					msg.setTipo(24);
				else
					msg.setTipo(23);  // setar modo apresentação
				enviarParaOuvintes(msg);
			}
			if(mensagem.getTipo()==20){// Ouvinte querendo sair da apresentação
				listaUsuariosApresentacao.remove(usuario);
				msg.setTipo(21);
				Serializacao.serializa(output, msg);
				listaClientesApresentacao.remove(output);
				setUsuariosApresentacao();
				slide.setListaOuvintes(listaClientesApresentacao);
			}
			if(mensagem.getTipo()==25){ // Modo discussão
				int idSlide = mensagem.getIdApresentacao();
				System.out.println(idSlide);
				slide.setSlideDiscussao(idSlide);
			}
			
			}
		}

	}


}
