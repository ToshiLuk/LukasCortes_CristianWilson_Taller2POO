package dominio;

public class Vulnerabilidad {
	private String nombre;
	private String descripcion;
	public Vulnerabilidad(String nombre, String descripcion) {

		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	
}
