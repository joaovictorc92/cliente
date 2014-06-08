package br.ufma.sistemasdistribuidos.cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Serializacao {
	
	ObjectOutputStream output;
	ObjectInputStream input;
	OutputStream fluxodesaida;
	InputStream fluxodeentrada;
	
	public Serializacao(OutputStream fluxodesaida, InputStream fluxodeentrada) {
		super();
		this.fluxodesaida = fluxodesaida;
		this.fluxodeentrada = fluxodeentrada;
	}

	public void serializa(Object object,OutputStream fluxo){
		
		try {
			this.output =  new ObjectOutputStream(fluxo);
	        output.writeObject(object);
	        output.flush();
	        output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public Object deserializa(InputStream fluxo) throws ClassNotFoundException, IOException{
		try {
			this.input = new ObjectInputStream(fluxo);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return input.readObject();
	}

}
