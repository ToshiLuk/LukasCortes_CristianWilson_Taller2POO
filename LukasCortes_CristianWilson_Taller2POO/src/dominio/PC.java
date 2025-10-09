package dominio;

import java.util.ArrayList;

public class PC {
	private String id;
	private String ip;
	private String so;
	private ArrayList<Puerto> puertos;
	
	public PC(String id, String ip, String so) {

		this.id = id;
		this.ip = ip;
		this.so = so;
		this.puertos = new ArrayList<Puerto>();
	}

	public void setPuertos(Puerto puerto) {
		this.puertos.add(puerto);
	}

	public String getIp() {
		return ip;
	}

	public String getId() {
		return id;
	}

	public String getSo() {
		return so;
	}

	public ArrayList<Puerto> getPuertos() {
		return puertos;
	}
	
	
	
}
