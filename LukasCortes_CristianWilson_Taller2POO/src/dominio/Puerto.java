package dominio;

import java.util.ArrayList;

public class Puerto {
	private int numero;
	private String estado;
	private ArrayList<Vulnerabilidad> vulnerabilidades;
	public Puerto(int numero, String estado, ArrayList<Vulnerabilidad> vulnerabilidades) {

		this.numero = numero;
		this.estado = estado;
		this.vulnerabilidades = vulnerabilidades;
	}
	
}
