package br.ufma.sistemasdistribuidos.cliente;

import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.ufma.sistemasdistribuidos.apresentacao.Login;
import br.ufma.sistemasdistribuidos.apresentacao.Sistema;
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
  
	public Recebedor(ObjectInputStream input,Login login,Sistema sistema){
		this.input = input;
		this.login = login;
		this.sistema = sistema;
		this.texto = "";
	}
	
	@Override
	public void run() {
		while(login.isConectado()){
			Mensagem mensagem = new Mensagem();
			mensagem = Serializacao.deserializa(input);
			if(mensagem.getTipo()==2){
				JOptionPane.showMessageDialog(null, "Usuario logado");
				System.out.println("usuario recebido");
				usuario = (Usuario) mensagem.getObject();
				this.idconexao= usuario.getIdconexao();
				login.setVisible(false);
				sistema.setVisible(true);
			}
			
			if(mensagem.getTipo()==3){
				JOptionPane.showMessageDialog(null, "Usuario ou senha não conferem");
			}
			if(mensagem.getTipo()==4){
				ArrayList<Usuario> logados = (ArrayList<Usuario>)mensagem.getObject();
				System.out.println("Usuarios Logados: ");
				for(IUsuario usuario : logados){
					System.out.println(usuario.getLogin());
					texto += usuario.getLogin()+"\n";
				}
				sistema.setUsuariosLogados(texto);
				texto="";
			}
			if(mensagem.getTipo()==6){
				login.setVisible(true);
				login.limpaLogin();
				sistema.setVisible(false);
			}
		}
		
	}

}
