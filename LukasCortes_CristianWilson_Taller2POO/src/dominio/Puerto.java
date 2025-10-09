package dominio;

import java.util.ArrayList;

public class Puerto {
	private int numero;
	private String estado;
	private ArrayList<Vulnerabilidad> vulnerabilidades;
	public Puerto(int numero, String estado) {

		this.numero = numero;
		this.estado = estado;
		
	}
	public int getNumero() {
		return numero;
	}
	
}
