//Lukas Paolo Toshisuke Cortes Alfaro 22.108.123-4 ICCI
//Cristian Harold Wilson Andreu 21.887.896-2 ICCI
package logica;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import dominio.PC;
import dominio.Puerto;
import dominio.Vulnerabilidad;

public class Main {
	private static Scanner s;
	public static void main(String[] args) throws FileNotFoundException {
		
		ArrayList<PC> PCs = crearListaPcs();
		crearPuertos(PCs);
		CrearVulnerabilidades(PCs);

		
		//Lectura de archivos mediante funciones
		
		int opcion = 0;
		int opcionAdmin = 0;
		int opcionUsuario = 0;
		//Inputs para elegir en el menu
		s = new Scanner(System.in);
		//Creación de scanner
		
		do {
			System.out.println("\nBienvenido a gestion de PCs de la red de SecureNet Ltda.");
			System.out.println("1) Ingresar como Admin.");
			System.out.println("2) Ingresar como Usuario");
			System.out.println("3) Salir");
			System.out.print("Ingrese una opción: ");
			//Menu de eleccion de ingreso
			opcion = s.nextInt();//Input de eleccion
			s.nextLine();//Se limpia el buffer
			//Menu admin
			switch(opcion) {
			case 1:
				if (login("ADMIN")) {	
				do {				 
					System.out.println("\nIngresó como Admin");
					System.out.println("1) Ver lista de PCs");
					System.out.println("2) Agregar/Eliminar PCs con validaciones");
					System.out.println("3) Clasificación por nivel de riesgo");
					System.out.println("4) Volver al menu principal");
					System.out.print("Ingrese una opción: ");
					opcionAdmin = s.nextInt();
					s.nextLine();//Se limpia buffer
				switch(opcionAdmin) {
				case 1:
					verListaPCs(PCs);
					break;
				case 2:
					agregarEliminarPCs(PCs);
					break;
				case 3:
					clasificacionPorNivelRiesgo(PCs);
					break;
				case 4:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no valida. Por favor, intente de nuevo.");
					break;
				}
			}while(opcionAdmin != 4);
			
				}else {
					System.out.println("Acceso denegado.");
				}
				break;
			case 2:
				if (login("USER")) {
				do {
					System.out.println("\nIngresó como Usuario");
					System.out.println("1) Ver lista de PCs");
					System.out.println("2) Escanear PC");
					System.out.println("3) Total de puertos abiertos con vulnerabilidades");
					System.out.println("4) Ordenar PCs según IP");
					System.out.println("5) Volver al menú principal");
					System.out.print("Ingrese una opción: ");
					opcionUsuario = s.nextInt();
					s.nextLine();
				switch(opcionUsuario) {
				case 1:
					verListaPCs(PCs);
					break;
				case 2:
					escanearPC();
					break;
				case 3:
					totalDePuertosAbiertosVulnerabilidades();
					break;
				case 4:
					clasificacionPorIP(PCs);
					break;
				case 5:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no valida. Por favor, intente de nuevo.");
					break;
						
				}
					
				}while(opcionUsuario != 5);
				
				}else{
					System.out.println("Acceso denegado.");
				}
				break;
		default:
			System.out.println("Opción no valida. Por favor, intente de nuevo.");
			break;
		}
	
		}while(opcion != 3);
		
	}
	
	private static void totalDePuertosAbiertosVulnerabilidades() {
		// TODO Auto-generated method stub
		
	}

	private static void escanearPC() {
		// TODO Auto-generated method stub
		
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
			System.out.println("Error, no se encontró el archivo: pcs.txt");
		}
		
		return PCs;
	}
	
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
			System.out.println("Error, no se encontró el archivo: puertos.txt");
		}
		
	}
	
	private static void CrearVulnerabilidades(ArrayList<PC> PCs) {
		
		try (Scanner sc = new Scanner(new File("vulnerabilidades.txt"))) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] part = line.split("\\|");
				
				int num = Integer.parseInt(part[0]);
				String nom = part[1];
				String desc = part[2];
				
				for (PC pc : PCs) {
					ArrayList<Puerto> puertos = pc.getPuertos();
					for (Puerto puert : puertos) {
						if (puert.getNumero() == num) {
							puert.setVulnerabilidades(new Vulnerabilidad(nom,desc));
						}
						
					}
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error, no se encontró el archivo: vulnerabilidades.txt");
		}
	}
	

	private static void verListaPCs(ArrayList<PC> PCs) {
		System.out.println("Lista completa de PCs: ");
		for (PC pc : PCs) {
			System.out.println(pc.getId() + ": IP-"+pc.getIp()+" SO-"+pc.getSo() );
			System.out.println("Puertos:");
			for (Puerto p : pc.getPuertos()) {
				System.out.println("Puerto "+p.getNumero()+" Estado: "+p.getEstado());
			}
			System.out.println();
		}
		
	}

	private static void agregarEliminarPCs(ArrayList<PC> PCs) {
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Desea agregar o eliminar un PC? Agregar(1)/Eliminar(2)");
	    int opcion = sc.nextInt();
	    sc.nextLine();
	    if (opcion == 1) {
	        System.out.print("Ingrese ID del nuevo PC: ");
	        String id = sc.nextLine();
	        System.out.print("IP del PC: ");
	        String ip = sc.nextLine();
	        System.out.print("SO del PC: ");
	        String so = sc.nextLine();

	        PC nuevopc = new PC(id, ip, so);

	        System.out.println("Cuantos puerto desea registrar para este pc?: ");
	        int cant = sc.nextInt();
	        sc.nextLine(); // limpiar buffer
	        for (int i = 0; i < cant; i++) {
	            System.out.println("Puerto numero " + (i + 1));
	            System.out.print("Numero del puerto: ");
	            int num = sc.nextInt();
	            sc.nextLine();
	            System.out.print("Estado del puerto: ");
	            String est = sc.nextLine();

	            Puerto p = new Puerto(num, est);
	            nuevopc.setPuertos(p);
	        }
	        asignarVulnerabilidadesAPuertos(nuevopc);

	        PCs.add(nuevopc);
	        System.out.println("PC agregado exitosamente.");
	    } else if (opcion == 2) {
	        System.out.print("Ingrese ID del PC a eliminar: ");
	        String id = sc.nextLine().trim();

	        PC pcdelete = null;
	        for (PC pc : PCs) {
	            if (pc.getId().equalsIgnoreCase(id)) {
	                pcdelete = pc;
	                break;
	            }
	        }
	        if (pcdelete != null) {
	            for (Puerto p : pcdelete.getPuertos()) {
	                if (p.getVulnerabilidades() != null) {
	                    p.getVulnerabilidades().clear();
	                }
	            }
	            pcdelete.getPuertos().clear();
	            PCs.remove(pcdelete);
	            System.out.println("PC eliminado exitosamente");
	        } else {
	            System.out.println("No se encuentra un PC con ese ID");
	        }
	    } else {
	        System.out.println("Opcion no valida, debe ingresar 1 o 2");
	    }

	}


	private static void asignarVulnerabilidadesAPuertos(PC pc) {
	    File arch = new File("vulnerabilidades.txt");
	    try (Scanner sc = new Scanner(arch)) {
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine().trim();
	            if (line.isEmpty()) continue;
	            String[] part = line.split("\\|");
	            if (part.length < 3) continue; 
	            int num;
	            try {
	                num = Integer.parseInt(part[0].trim());
	            } catch (NumberFormatException e) {
	                continue; 
	            }
	            String nom = part[1].trim();
	            String desc = part[2].trim();

	            for (Puerto puert : pc.getPuertos()) {
	                if (puert.getNumero() == num) {	                    
	                    puert.setVulnerabilidades(new Vulnerabilidad(nom, desc));
	                }
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.out.println("Error, no se encontró el archivo: vulnerabilidades.txt");
	    }
	}


	private static void clasificacionPorNivelRiesgo(ArrayList<PC> PCs) {
		System.out.println("Lista de PCs segun riesgo: ");
		for (PC pc : PCs) {
			System.out.println(pc.getId()+":");
			int vul = 0;
			for (Puerto p : pc.getPuertos()) {
				vul += p.getVulnerabilidades().size();
			}
			System.out.println("Vulnerabilidades: "+vul);
			if (vul < 2) {
				if (vul == 0) {
					System.out.println("Sin vulnerabilidades");
				} else if (vul == 1) {
					System.out.println("Numero de vulnerabilidades: 1");
				}
				System.out.println("Riesgo BAJO");
			} else if (vul < 3) {
				System.out.println("Numero de vulnerabilidades: "+vul);
				System.out.println("Riesgo MEDIO");
			} else if (vul >= 3) {
				System.out.println("Numero de vulnerabilidades: "+vul);
				System.out.println("Riesgo ALTO");
			}
		}
		
	}

	
	private static ArrayList<String[]> leerUsuarios() throws FileNotFoundException {
		File arch = new File("usuarios.txt");
		ArrayList<String[]> lista = new ArrayList<>();
		
		try (Scanner s = new Scanner(arch)){
			
			while(s.hasNextLine()) {
				String linea = s.nextLine();
				String[] partes = linea.split(";");
				lista.add(partes);
			
			}
		}catch (FileNotFoundException e) {
			System.out.println("Error al leer el archivo (usuarios.txt): " + e.getMessage());
		}
		return lista;
	}
		
	
	private static boolean login(String tipoUsuario) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Ingrese nombre de usuario: ");
		String nombreDeUsuario = sc.nextLine().trim();
		System.out.print("Ingrese contraseña: ");
		String contraseña = sc.nextLine();

		// archivo usuarios.txt debe tener formato: nombre;contraseña;ROL
		ArrayList<String[]> usuarios = leerUsuarios();

		for (String[] usuario : usuarios) {
			if (usuario[0].equals(nombreDeUsuario)) {
				String contraseñaGuardada = usuario[1];
				String rol = usuario[2];

				// Verificamos contraseña con hash
				boolean ok = Autentificacion.comprobarContraseña(contraseña, contraseñaGuardada);

				// Comprobamos que el rol coincida con el tipo de login (Admin o Usuario)
				if (ok && rol.equalsIgnoreCase(tipoUsuario)) {
					System.out.println("Login correcto. Bienvenido, " + nombreDeUsuario + ".");
					return true;
				} else if (ok) {
					System.out.println("Usuario correcto pero no tiene permisos de " + tipoUsuario + ".");
					return false;
				}
			}
		}
		System.out.println("Usuario o contraseña incorrectos.");
	return false;
	}
	
	private static void clasificacionPorIP(ArrayList<PC> PCs) {
		
		ArrayList<PC> claseA = new ArrayList<>();
	    ArrayList<PC> claseB = new ArrayList<>();
	    ArrayList<PC> claseC = new ArrayList<>();
		
		for (PC pc : PCs) {
	        String ip = pc.getIp();
	        String[] part = ip.split("\\.");
	        int primernum = Integer.parseInt(part[0].trim());

	        if (primernum >= 1 && primernum <= 126) {
	            claseA.add(pc);
	        } else if (primernum >= 128 && primernum <= 191) {
	            claseB.add(pc);
	        } else if (primernum >= 192 && primernum <= 223) {
	            claseC.add(pc);
	        }
	    }
		
		System.out.println("Pcs ordenados por categorias segun IP:");
	    System.out.println("Clase A (" + claseA.size() + "):");
	    for (PC pc : claseA) {
	        System.out.println(pc.getId() + " IP: " + pc.getIp());
	    }

	    System.out.println("\nClase B (" + claseB.size() + "):");
	    for (PC pc : claseB) {
	        System.out.println(pc.getId() + " IP: " + pc.getIp());
	    }

	    System.out.println("\nClase C (" + claseC.size() + "):");
	    for (PC pc : claseC) {
	        System.out.println(pc.getId() + " IP: " + pc.getIp());
	    }
		
	}
	
	
}
