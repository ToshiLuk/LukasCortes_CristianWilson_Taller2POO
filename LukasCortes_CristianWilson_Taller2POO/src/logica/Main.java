//Lukas Paolo Toshisuke Cortes Alfaro 22.108.123-4 ICCI
//Cristian Harold Wilson Andreu 21.887.896-2 ICCI
package logica;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import dominio.PC;
import dominio.Puerto;
import dominio.Vulnerabilidad;

public class Main {
	private static String usuarioActual = "";
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
		
		do { //Menu de eleccion de ingreso
		    System.out.println("\nBienvenido a gestion de PCs de la red de SecureNet Ltda.");
		    System.out.println("1) Ingresar como Admin.");
		    System.out.println("2) Ingresar como Usuario");
		    System.out.println("3) Salir");
		    System.out.print("Ingrese una opción: ");

		    try {
		        opcion = s.nextInt(); // Intentamos leer número
		        s.nextLine(); // Limpiamos el buffer

		        switch (opcion) {
		            case 1:
		                if (login("ADMIN")) {
		                    do {
		                        System.out.println("\nIngresó como Admin");
		                        System.out.println("1) Ver lista de PCs");
		                        System.out.println("2) Agregar/Eliminar PCs con validaciones");
		                        System.out.println("3) Clasificación por nivel de riesgo");
		                        System.out.println("4) Volver al menu principal");
		                        System.out.print("Ingrese una opción: ");
		                        try {
		                            opcionAdmin = s.nextInt();
		                            s.nextLine();
		                            switch (opcionAdmin) {
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
		                                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
		                                    break;
		                            }
		                        } catch (java.util.InputMismatchException e) {
		                            System.out.println("Error: debe ingresar un número válido.");
		                            s.nextLine(); // limpiar buffer
		                            opcionAdmin = 0; // Reiniciamos para seguir en el menú
		                        }
		                    } while (opcionAdmin != 4);
		                } else {
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
		                        try {
		                            opcionUsuario = s.nextInt();
		                            s.nextLine();
		                            switch (opcionUsuario) {
		                                case 1:
		                                    verListaPCs(PCs);
		                                    break;
		                                case 2:
		                                    escanearPC(PCs);
		                                    break;
		                                case 3:
		                                    totalDePuertosAbiertosVulnerabilidades(PCs);
		                                    break;
		                                case 4:
		                                    clasificacionPorIP(PCs);
		                                    break;
		                                case 5:
		                                    System.out.println("Saliendo...");
		                                    break;
		                                default:
		                                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
		                                    break;
		                            }
		                        } catch (java.util.InputMismatchException e) {
		                            System.out.println("Error: debe ingresar un número válido.");
		                            s.nextLine();
		                            opcionUsuario = 0;
		                        }
		                    } while (opcionUsuario != 5);
		                } else {
		                    System.out.println("Acceso denegado.");
		                }
		                break;

		            case 3:
		                System.out.println("Saliendo del programa...");
		                break;

		            default:
		                System.out.println("Opción no válida. Por favor, intente de nuevo.");
		                break;
		        }

		    } catch (java.util.InputMismatchException e) {
		        System.out.println("Error: debe ingresar un número válido.");
		        s.nextLine(); // Limpiar buffer y evitar bucle infinito
		    }

		} while (opcion != 3);
		s.close();
	}
	
	private static void escanearPC(ArrayList<PC> PCs) {//Funcion para escanear PC
		String id = s.nextLine().trim();
	    System.out.print("Ingrese ID del PC a escanear: ");
	    PC target = null;
	    for (PC pc : PCs) {
	        if (pc.getId().equalsIgnoreCase(id)) {//Buscamos la id del PC que ingresamos
	            target = pc;//Guardamos el PC cuando lo encontremos
	            break;
	        }
	    }

	    if (target == null) {//Por si no se encuentra el PC que se ingreso
	        System.out.println("No se encontró un PC con ese ID.");
	        return;
	    }

	    // Mostrar en consola
	    System.out.println("Escaneando: " + target.getId());
	    System.out.println("IP: " + target.getIp() + "\nSO: " + target.getSo());
	    int totalVul = 0;
	    for (Puerto p : target.getPuertos()) {
	        System.out.println("Puerto " + p.getNumero() + " Estado: " + p.getEstado());
	        if (p.getVulnerabilidades() != null && !p.getVulnerabilidades().isEmpty()) {
	            for (Vulnerabilidad v : p.getVulnerabilidades()) {
	                System.out.println("  - Vul: " + v.getNombre() + " | " + v.getDescripcion());
	                totalVul++;
	            }
	        } else {
	            System.out.println("  - Sin vulnerabilidades conocidas.");
	        }
	    }

	    String nivel = calcularNivelRiesgo(totalVul);//Se calcula el riesgo del PC para ingresarlo al reporte
	    System.out.println("Nivel de riesgo: " + nivel);

	    // Guardar en reportes.txt
	    guardarReporteScan(target, nivel, totalVul);
	}

	//Guardar reporte en archivo
	private static void guardarReporteScan(PC pc, String nivel, int totalVul) {
	    java.time.LocalDateTime ahora = java.time.LocalDateTime.now();//Conseguimos la hora y fecha actual
	    java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String fecha = ahora.format(fmt);

	    StringBuilder sb = new StringBuilder();//Creamos un StringBuilder que es mas facil de modificar
	    sb.append("====================================\n");
	    sb.append("Fecha: ").append(fecha).append("\n");
	    sb.append("Usuario: ").append(usuarioActual.isEmpty() ? "DESCONOCIDO" : usuarioActual).append("\n");
	    sb.append("PC: ").append(pc.getId()).append(" IP: ").append(pc.getIp()).append(" SO: ").append(pc.getSo()).append("\n");
	    sb.append("Nivel de riesgo: ").append(nivel).append(" (").append(totalVul).append(" vulnerabilidades)").append("\n");
	    sb.append("Puertos:\n");
	    for (Puerto p : pc.getPuertos()) {
	        sb.append("  Puerto ").append(p.getNumero()).append(" Estado: ").append(p.getEstado()).append("\n");
	        if (p.getVulnerabilidades() != null && !p.getVulnerabilidades().isEmpty()) {
	            for (Vulnerabilidad v : p.getVulnerabilidades()) {
	                sb.append("    - ").append(v.getNombre()).append(" | ").append(v.getDescripcion()).append("\n");
	            }
	        } else {
	            sb.append("    - Sin vulnerabilidades conocidas\n");
	        }
	    }
	    sb.append("====================================\n\n");
	    //Creamos reportes.txt y le agregamos el reporte 
	    try (java.io.FileWriter fw = new java.io.FileWriter("reportes.txt", true);
	         java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
	        bw.write(sb.toString());
	        System.out.println("Reporte guardado en reportes.txt");
	    } catch (java.io.IOException e) {
	        System.out.println("Error guardando reporte: " + e.getMessage());
	    }
	}

	//Calcular nivel de riesgo 
	private static String calcularNivelRiesgo(int vul) {
	    if (vul == 0) return "SIN VULNERABILIDADES";
	    if (vul == 1) return "BAJO";
	    if (vul == 2) return "MEDIO";
	    return "ALTO";
	}

	//Mostrar total de puertos abiertos en toda la red con su vulnerabilidad
	private static void totalDePuertosAbiertosVulnerabilidades(ArrayList<PC> PCs) {
	    System.out.println("Puertos abiertos en la red:");
	    int contador = 0;
	    for (PC pc : PCs) {
	        for (Puerto p : pc.getPuertos()) {
	            String estado = p.getEstado() == null ? "" : p.getEstado().trim();
	            //Comprobamos el estado del puerto
	            boolean abierto = estado.equalsIgnoreCase("abierto");
	            if (abierto) {
	                contador++;
	                System.out.println("PC: " + pc.getId() + " IP: " + pc.getIp() + " -> Puerto " + p.getNumero());
	                if (p.getVulnerabilidades() != null && !p.getVulnerabilidades().isEmpty()) {
	                    for (Vulnerabilidad v : p.getVulnerabilidades()) {
	                        System.out.println("   - Vulnerabilidad: " + v.getNombre() + " | " + v.getDescripcion());
	                    }
	                } else {
	                    System.out.println("   - Sin vulnerabilidades conocidas.");
	                }
	            }
	        }
	    }
	    System.out.println("Total de puertos abiertos: " + contador);
	}

	private static ArrayList<PC> crearListaPcs() { //Creamos la lista de PCs leyendo pcs.txt y usando la clase PC
		ArrayList<PC> PCs = new ArrayList<>();
		try (Scanner sc = new Scanner(new File("pcs.txt"))) {//Leemos pcs.txt
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] part = line.split("\\|");
				
				PC Pc = new PC(part[0],part[1],part[2]);//Creamos un PC
				PCs.add(Pc);//Lo agregamos a la lista de PCs
				
				}
			
		} catch(FileNotFoundException e) {
			System.out.println("Error, no se encontró el archivo: pcs.txt");
		}
		
		return PCs;
	}
	
	private static void crearPuertos(ArrayList<PC> PCs) {//Creamos una lista con los puertos leyendo puertos.txt y la clase Puerto
		
		
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
	
	private static void CrearVulnerabilidades(ArrayList<PC> PCs) {//Creamos una lista de vulnerabilidades leyendo vulnerabilidades.txt y con la clase Vulnerabilidad
		
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
	

	private static void verListaPCs(ArrayList<PC> PCs) {//Se imprime todos los PCs con su informacion
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

	private static void agregarEliminarPCs(ArrayList<PC> PCs) {//Agregamos un PC con toda su info o eliminamos un PC ya existente

	    System.out.println("Desea agregar o eliminar un PC? Agregar(1)/Eliminar(2)");
	    int opcion = s.nextInt();
	    s.nextLine();
	    if (opcion == 1) {
	        System.out.print("Ingrese ID del nuevo PC: ");
	        String id = s.nextLine();
	        System.out.print("IP del PC: ");
	        String ip = s.nextLine();
	        System.out.print("SO del PC: ");
	        String so = s.nextLine();

	        PC nuevopc = new PC(id, ip, so);

	        System.out.println("Cuantos puerto desea registrar para este pc?: ");
	        int cant = s.nextInt();
	        s.nextLine(); // limpiar buffer
	        for (int i = 0; i < cant; i++) {
	            System.out.println("Puerto numero " + (i + 1));
	            System.out.print("Numero del puerto: ");
	            int num = s.nextInt();
	            s.nextLine();
	            System.out.print("Estado del puerto: ");
	            String est = s.nextLine();

	            Puerto p = new Puerto(num, est);
	            nuevopc.setPuertos(p);
	        }
	        asignarVulnerabilidadesAPuertos(nuevopc);//Buscamos vulnerabilidades al nuevo PC

	        PCs.add(nuevopc);//Agregamos el PC nuevo
	        System.out.println("PC agregado exitosamente.");
	    } else if (opcion == 2) {
	        System.out.print("Ingrese ID del PC a eliminar: ");
	        String id = s.nextLine().trim();

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


	private static void asignarVulnerabilidadesAPuertos(PC pc) {//Se crea lista con las vulnerabilidades leyendo vulnerabilidades.txt y con la clase Vulnerabilidad
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
		
	//Función para logearse
	private static boolean login(String tipoUsuario) throws FileNotFoundException {
		System.out.print("Ingrese nombre de usuario: ");
		String nombreDeUsuario = s.nextLine().trim();
		System.out.print("Ingrese contraseña: ");
		String contraseña = s.nextLine();
		ArrayList<String[]> usuarios = leerUsuarios();//Leemos usuarios.txt

		for (String[] usuario : usuarios) {
			if (usuario[0].equals(nombreDeUsuario)) {//Buscamos si el nombre de usuario ingresado está en usuarios.txt
				String contraseñaGuardada = usuario[1];//Guardamos la contraseña hasheada del usuario
				String rol = usuario[2]; //Guardamos rol del usuario

				// Verificamos contraseña con hash
				boolean ok = Autentificacion.comprobarContraseña(contraseña, contraseñaGuardada);

				// Comprobamos que el rol coincida con el tipo de login (Admin o Usuario)
				if (ok && rol.equalsIgnoreCase(tipoUsuario)) {
					usuarioActual = nombreDeUsuario; //Se guarda el usuario logueado
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
	//Clasificacion de PCs por IP
	private static void clasificacionPorIP(ArrayList<PC> PCs) {
		//Creamos ArrayList para cada clase
		ArrayList<PC> claseA = new ArrayList<>();
	    ArrayList<PC> claseB = new ArrayList<>();
	    ArrayList<PC> claseC = new ArrayList<>();
		
		for (PC pc : PCs) {//Vamos PC por PC
	        String ip = pc.getIp();//Sacamos la ip
	        String[] part = ip.split("\\.");//Separamos la IP en puntos
	        int primernum = Integer.parseInt(part[0].trim());//

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
