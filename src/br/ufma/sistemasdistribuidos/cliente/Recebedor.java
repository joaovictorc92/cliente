package br.ufma.sistemasdistribuidos.cliente;

import java.io.ObjectInputStream;
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

public class Recebedor implements Runnable {
    ObjectInputStream input;
    Login login;
    Sistema sistema;
    int idconexao;
    Usuario usuario;
    String texto;
    ArrayList<Apresentacao> listaApresentacoes;
    ArrayList<ImageIcon> listaImagens;
	public Recebedor(ObjectInputStream input,Login login,Sistema sistema){
		this.input = input;
		this.login = login;
		this.sistema = sistema;
		this.texto = "";
		listaApresentacoes = new ArrayList<Apresentacao>();
	}
	
	@Override
	public void run() {
		while(login.isConectado()){// Enquanto a janela de login estiver em execução o cliente escutará o servidor
			Mensagem mensagem = new Mensagem();
			mensagem = Serializacao.deserializa(input);
			if(mensagem.getTipo()==2){//Recebe confirmação de login
				JOptionPane.showMessageDialog(null, "Usuario logado");
				System.out.println("usuario recebido");
				usuario = (Usuario) mensagem.getObject();
				this.idconexao= usuario.getIdconexao();
				login.setVisible(false);
				sistema.setVisible(true);
			}
			
			if(mensagem.getTipo()==3){// Recebe confirmação que não houve login
				JOptionPane.showMessageDialog(null, "Usuario ou senha não conferem");
			}
			if(mensagem.getTipo()==4){// Recebe lista de usuarios logados
				ArrayList<Usuario> logados = (ArrayList<Usuario>)mensagem.getObject();
				System.out.println("Usuarios Logados: ");
				for(IUsuario usuario : logados){
					System.out.println(usuario.getLogin());
					texto += usuario.getLogin()+"\n";
				}
				sistema.setUsuariosLogados(texto);
				texto="";
			}
			if(mensagem.getTipo()==6){// Recebe confirmação de logout
				login.setVisible(true);
				login.limpaLogin();
				sistema.setVisible(false);
			}
			if(mensagem.getTipo()==7){//Recebe lista de apresentações
				listaApresentacoes = (ArrayList<Apresentacao>) mensagem.getObject();
				sistema.carregarListaApresentacoesDisponiveis(listaApresentacoes);
			}
			if(mensagem.getTipo()==9){// Recebe a apresentação selecionada em uma lista de imagens
				listaImagens = (ArrayList<ImageIcon>) mensagem.getObject();
				System.out.println("Numero de imagens:"+listaImagens.size());
				Slide slide = new Slide(listaImagens);
				slide.setVisible(true);
		
			}
		}
		
		
	}

}
