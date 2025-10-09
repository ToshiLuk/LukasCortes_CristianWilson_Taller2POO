//Lukas Paolo Toshisuke Cortes Alfaro 22.108.123-4 ICCI
//Cristian Harold Wilson Andreu 21.887.896-2 ICCI
package logica;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

import dominio.PC;
import dominio.Puerto;

public class Main {
	private static Scanner s;
	public static void main(String[] args) throws FileNotFoundException {
		
		ArrayList<PC> PCs = crearListaPcs();
		crearPuertos(PCs);
		
		System.out.println(PCs.get(2).getPuertos().get(2).getNumero());
		leerVulnerabilidades();
		leerUsuarios();
		
		//Lectura de archivos mediante funciones
		
		int opcion = 0;
		int opcionAdmin = 0;
		int opcionUsuario = 0;
		//Inputs para elegir en el menu
		s = new Scanner(System.in);
		//Creación de scanner
		do {
			System.out.println("Bienvenido a gestion de PCs de la red de SecureNet Ltda.");
			System.out.println("1) Ingresar como Admin.");
			System.out.println("2) Ingresar como Usuario");
			System.out.println("3) Salir");
			System.out.println("Seleccione una opcion:");
			//Menu de eleccion de ingreso
			opcion = s.nextInt();//Input de eleccion
			s.nextLine();//Se limpia el buffer
			//Menu admin
			switch(opcion) {
			case 1://
				do {
				System.out.println("Ingresó como Admin");
				System.out.println("1) Ver lista de PCs");
				System.out.println("2) Agregar/Eliminar PCs con validaciones");
				System.out.println("3) Clasificación por nivel de riesgo");
				System.out.println("4) Volver a menu principal");
				opcionAdmin = s.nextInt();
				s.nextLine();//Se limpia buffer
				switch(opcionAdmin) {
				case 1:
					verListaPCs();
					break;
				case 2:
					agregarEliminarPCs();
					break;
				case 3:
					clasificacionPorNivelRiesgo();
					break;
				case 4:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no valida. Por favor, intente de nuevo.");
				}
			}while(opcionAdmin != 4);
					break;
			case 2:
				do {
					System.out.println("Ingresó como Usuario");
					System.out.println("1) Ver lista de PCs");
					System.out.println("2) Escanear PC");
					System.out.println("3) Total de puertos abiertos con vulnerabilidades");
				}while(opcionUsuario != 4);
				break;
		}
	
		}while(opcion != 3);
	}
	
	private static ArrayList<PC> crearListaPcs() {
		
	
		ArrayList<PC> PCs = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new File("pcs.txt"))) {
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] part = line.split("\\|");
				
				PC Pc = new PC(part[0],part[1],part[2]);
				PCs.add(Pc);
				
				}
			
		} catch(FileNotFoundException e) {
			System.out.println("Error, no se encontr el archivo: pcs.txt");
		}
		
		return PCs;
	}//FIN crearListaPcs()
	
	private static void crearPuertos(ArrayList<PC> PCs) {
		
		
		try (Scanner sc = new Scanner(new File("puertos.txt"))) {
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] part = line.split("\\|");
				String id = part[0];
				int num = Integer.parseInt(part[1]);
				String status = part[2];
				
				for (PC pc : PCs) {
					if (pc.getId().equals(id)) {
						pc.setPuertos(new Puerto(num, status));
						break;
					}
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error, no se encontr el archivo: puertos.txt");
		}
		
	}


	private static void verListaPCs() {
		// TODO Auto-generated method stub
		
	}

	private static void agregarEliminarPCs() {
		// TODO Auto-generated method stub
		
	}

	private static void clasificacionPorNivelRiesgo() {
		// TODO Auto-generated method stub
		
	}

	private static void leerPuertos() {
		
	}

	private static void leerUsuarios() {
		// TODO Auto-generated method stub
		
	}

	private static void leerVulnerabilidades() {
		// TODO Auto-generated method stub
		
	}

}
