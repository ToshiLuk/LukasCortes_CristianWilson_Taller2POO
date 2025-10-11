package dominio;

import java.util.ArrayList;

public class Puerto {
	private int numero;
	private String estado;
	private ArrayList<Vulnerabilidad> vulnerabilidades;
	public Puerto(int numero, String estado) {

		this.numero = numero;
		this.estado = estado;
		this.vulnerabilidades = new ArrayList<Vulnerabilidad>();
		
	}
	public int getNumero() {
		return numero;
	}
	public void setVulnerabilidades(Vulnerabilidad vulnerabilidad) {
		this.vulnerabilidades.add(vulnerabilidad);
	}
	public ArrayList<Vulnerabilidad> getVulnerabilidades() {
		return vulnerabilidades;
	}
	public String getEstado() {
		return estado;
	}
	
	
}
