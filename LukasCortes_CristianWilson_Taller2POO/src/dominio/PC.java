package dominio;

import java.util.ArrayList;

public class PC {
	private int id;
	private int ip;
	private String so;
	private ArrayList<Puerto> puertos;
	
	public PC(int id, int ip, String so, ArrayList<Puerto> puertos) {

		this.id = id;
		this.ip = ip;
		this.so = so;
		this.puertos = puertos;
	}
	
}
