package br.ufma.sistemasdistribuidos.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import br.ufma.sistemasdistribuidos.apresentacao.Login;
import br.ufma.sistemasdistribuidos.apresentacao.Sistema;
import br.ufma.sistemasdistribuidos.apresentacao.Slide;
import br.ufma.sistemasdistribuidos.form.Apresentacao;
import br.ufma.sistemasdistribuidos.form.IUsuario;
import br.ufma.sistemasdistribuidos.form.Mensagem;
import br.ufma.sistemasdistribuidos.form.Usuario;
import br.ufma.sistemasdistribuidos.servidor.Serializacao;
/*
 * Classe para ouvir o servidor
 */
public class Recebedor implements Runnable {
	ObjectInputStream input;
	ObjectOutputStream output;
	Login login;
	Sistema sistema;
	int idconexao;
	IUsuario usuario;
	String texto;
	ArrayList<Apresentacao> listaApresentacoes;
	ArrayList<Apresentacao> listaApresentacoesAndamento;
	ArrayList<ImageIcon> listaImagens;
	Socket cliente;

	public Recebedor(ObjectInputStream input, ObjectOutputStream output,
			Login login, Sistema sistema) {
		this.input = input;
		this.output = output;
		this.login = login;
		this.sistema = sistema;
		this.texto = "";
		listaApresentacoes = new ArrayList<Apresentacao>();
		listaApresentacoesAndamento = new ArrayList<Apresentacao>();

	}

	@Override
	public void run() {
		Mensagem mensagem = new Mensagem();
		Thread t = null;
		Slide slide = null;
		while (login.isEnabled()) {// Enquanto a janela de login estiver em
									// execu��o o cliente escutar� o servidor
			mensagem = Serializacao.deserializa(input);
			if (mensagem.getTipo() == 2) {// Recebe confirma��o de login
				JOptionPane.showMessageDialog(null, "Usuario logado");
				System.out.println("usuario recebido");
				usuario = (IUsuario) mensagem.getObject();
				this.idconexao = usuario.getIdconexao();
				login.setVisible(false);
				sistema.setUsuario(usuario);
				sistema.setVisible(true);
			}

			if (mensagem.getTipo() == 3) {// Recebe confirma��o que n�o houve
											// login
				JOptionPane.showMessageDialog(null,
						"Usuario ou senha n�o conferem");
			}
			if (mensagem.getTipo() == 4) {// Recebe lista de usuarios logados
				ArrayList<Usuario> logados = (ArrayList<Usuario>) mensagem
						.getObject();
				System.out.println("Usuarios Logados: ");
				for (IUsuario usuario : logados) {
					System.out.println(usuario.getLogin());
					texto += usuario.getLogin() + "\n";
				}
				sistema.setUsuariosLogados(texto);
				texto = "";
			}
			if (mensagem.getTipo() == 6) {// Recebe confirma��o de logout
				login.setVisible(true);
				login.limpaLogin();
				sistema.setVisible(false);
			}
			if (mensagem.getTipo() == 7) {// Recebe lista de apresenta��es
				listaApresentacoes = (ArrayList<Apresentacao>) mensagem
						.getObject();
				sistema.carregarListaApresentacoesDisponiveis(listaApresentacoes);
			}
			if (mensagem.getTipo() == 9) {// Recebe a apresenta��o selecionada
											// em uma lista de imagens
				
				listaImagens = (ArrayList<ImageIcon>) mensagem.getObject();
				System.out.println("Numero de imagens:" + listaImagens.size());
				slide = new Slide(listaImagens, output);
				slide.setIdApresentacao(mensagem.getIdApresentacao());
				slide.setVisible(true);
				t = new Thread(new ServidorCliente(slide, usuario.getPorta(),usuario)); // Abre a thread para escutar requisi��es de novos ouvintes
				t.start();

			}
			if (mensagem.getTipo() == 10) { // Carrega lista de apresenta��es em andamento
				listaApresentacoesAndamento = (ArrayList<Apresentacao>) mensagem
						.getObject();
				sistema.carregarListaApresentacoesAndamento(listaApresentacoesAndamento);

			}
			if (mensagem.getTipo() == 12) { //Atualiza Usu�rio
				usuario = (IUsuario) mensagem.getObject();
			}
			if (mensagem.getTipo() == 15) { // Depois de se conectar com a apresenta��o em andamento, o cliente tenta se conectar com o palestrante
				IUsuario palestrante;
				palestrante = (IUsuario) mensagem.getObject();
				try {
					ClientePonto clientePonto = new ClientePonto(
							palestrante.getIp(), palestrante.getPorta(),usuario); // Chama a classe para ser ouvinte
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(mensagem.getTipo()==16){ // Terminar apresenta��o por parte do palestrante
				t.interrupt();
			}

		}
		
	}
}
