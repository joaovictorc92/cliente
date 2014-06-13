package br.ufma.sistemasdistribuidos.cliente;

import java.io.Serializable;
import java.net.Socket;

import br.ufma.sistemasdistribuidos.apresentacao.Login;

public class Cliente implements Serializable{
    String url;
    int porta;
    Socket cliente;
	public Cliente(String url, int porta){
		this.url = url;
		this.porta = porta;
		try {
			this.cliente = new Socket(url,porta);
			Login login = new Login(cliente);
			login.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		
		new Cliente("127.0.0.1",12345);

	}

}
