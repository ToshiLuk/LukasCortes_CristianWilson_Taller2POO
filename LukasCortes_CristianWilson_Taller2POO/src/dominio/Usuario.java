package dominio;

public class Usuario {
	private String nombre;
	private String contraseña; //algo de hashmap nse el que
	private String rol;
	public Usuario(String nombre, String contraseña, String rol) {

		this.nombre = nombre;
		this.contraseña = contraseña;
		this.rol = rol;
	}
	
}
