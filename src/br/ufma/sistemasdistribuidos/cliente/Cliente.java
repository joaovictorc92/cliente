package br.ufma.sistemasdistribuidos.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufma.sistemasdistribuidos.form.Usuario;

public class Cliente implements Serializable{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket cliente = new Socket("192.168.0.103",54321);
			ObjectInputStream input = new ObjectInputStream(cliente.getInputStream());
			Usuario usuario = new Usuario();
			usuario = (Usuario) input.readObject();
			System.out.println("Nome: "+usuario.getNome()+" Senha: "+usuario.getSenha());
			
		} catch(Exception e){
			e.printStackTrace();
			
		}
		

	}

}
