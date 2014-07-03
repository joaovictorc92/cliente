package br.ufma.sistemasdistribuidos.cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JOptionPane;

import br.ufma.sistemasdistribuidos.apresentacao.Login;
import br.ufma.sistemasdistribuidos.apresentacao.Sistema;
import br.ufma.sistemasdistribuidos.apresentacao.Slide;

public class Cliente{
    String url;
    int porta;
    Socket cliente;
	public Cliente(String url, int porta){
		this.url = url;
		this.porta = porta;
		try {
			this.cliente = new Socket(url,porta);
			ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream()); // Cria fluxo de saida para serializar objetos
			ObjectInputStream input =  new ObjectInputStream(cliente.getInputStream());// Cria fluxo de entrada para deserializar objetos
			Login login = new Login(output); // Cria a janela de login
			login.setVisible(true);
			login.setConectado(true);
			Sistema sistema = new Sistema(login,output);
			Recebedor r = new Recebedor(input,output, login,sistema); // Cria thread para escutar o servidor enquanto
			new Thread(r).start();
			//cliente.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		
		String url = JOptionPane.showInputDialog("IP da maquina servidora");
		new Cliente(url,12345);

	}

}
