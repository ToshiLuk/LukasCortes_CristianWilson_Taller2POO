package logica;
import java.nio.charset.StandardCharsets;// Para manejar texto en formato UTF-8
import java.security.MessageDigest;// Para usar funciones de hash como SHA-256
import java.security.NoSuchAlgorithmException;// Para manejar posibles errores del algoritmo
import java.util.Base64;// Para codificar los bytes del hash en texto legible
public class Autentificacion {
	
	    public static String hashPassword(String plainPassword) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");//Para poder aplicar algoritmo SHA-256
	            byte[] digest = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));//Se transforma la contraseña en bytes y se aplica SHA-256
	            return Base64.getEncoder().encodeToString(digest);//Se transforma a Base64 y se devuelve
	        } catch (NoSuchAlgorithmException e) {

	            throw new RuntimeException("SHA-256 no disponible", e);
	        }
	    }

	    private static boolean comparadorBase64Sha256(String s) {
	        if (s == null) return false;//Si la cadena es null retorna false
	        s = s.trim();//Elimina los espacios

	        if (s.length() >= 43 && s.length() <= 45) {//Se compara la longitud
	            return s.matches("^[A-Za-z0-9+/]+={0,2}$");//Solo permite caracteres de Base64
	        }
	        return false;
	    }

	    public static boolean comprobarContraseña(String contraseñaIngresada, String contraseñaGuardada) {
	        if (contraseñaGuardada == null) return false;
	        contraseñaGuardada = contraseñaGuardada.trim();

	        if (comparadorBase64Sha256(contraseñaGuardada)) {
	            String hashedInput = hashPassword(contraseñaIngresada);
	            return hashedInput.equals(contraseñaGuardada);
	        } else {

	            return contraseñaGuardada.equals(contraseñaGuardada);
	        }
	    }
	}


