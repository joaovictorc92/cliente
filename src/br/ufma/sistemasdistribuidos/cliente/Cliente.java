package br.ufma.sistemasdistribuidos.cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import br.ufma.sistemasdistribuidos.apresentacao.Login;
import br.ufma.sistemasdistribuidos.apresentacao.Sistema;

public class Cliente implements Serializable{
    String url;
    int porta;
    Socket cliente;
    int idConeccao;
	public Cliente(String url, int porta){
		this.url = url;
		this.porta = porta;
		try {
			this.cliente = new Socket(url,porta);
			ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
			ObjectInputStream input =  new ObjectInputStream(cliente.getInputStream());
			Login login = new Login(output);
			login.setVisible(true);
			login.setConectado(true);
			Sistema sistema = new Sistema(login,output);
			Recebedor r = new Recebedor(input, login,sistema);
			new Thread(r).start();
			//cliente.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		
		new Cliente("127.0.0.1",12345);

	}

}
