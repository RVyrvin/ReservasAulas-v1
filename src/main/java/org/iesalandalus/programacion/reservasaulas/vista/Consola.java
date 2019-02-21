package org.iesalandalus.programacion.reservasaulas.vista;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String ERROR = "ERROR: ";
	private static final String ER_TELEFONO = "[69][0-9]{8}";
	private static final String ER_CORREO = "\\w[.\\w]*@[a-zA-Z]+\\.[a-zA-Z]{2,5}";

	private Consola() {

	}

	public static void mostrarMenu() {
		mostrarCabecera("\nGestión de clientes");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}

	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String cadena = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(cadena, 0).replace("0", "-"));
	}

	public static int elegirOpcion() {
		int ordinalOpcion = 0;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}

	public static Aula leerAula() {
		
		Aula aula = null;
		String nombreAula = "";
		
		do {
			nombreAula = leerNombreAula();
		} while (nombreAula.trim().equals(""));		
		
		try {
			aula = new Aula(nombreAula);
		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}
		
		return aula;
	}
	

	public static String leerNombreAula() {
		System.out.print("Introduce el nombre da la aula: ");
		return Entrada.cadena();
	}
	

	public static Profesor leerProfesor() {
		
		Profesor profesor = null;
		String nombreProfesor = "";
		
		do {
			nombreProfesor = leerNombreProfesor();
		} while (nombreProfesor.trim().equals(""));

		String correoProfesor = "";
		do {
			System.out.print("Introduce el correo valido: ");
			correoProfesor = Entrada.cadena();
		} while (correoProfesor.trim().equals("") || !correoProfesor.matches(ER_CORREO));

		System.out.print("Introduce el telefono valido: ");
		String telefonoProfesor = Entrada.cadena();		
		
		try {
			if (telefonoProfesor.matches(ER_TELEFONO))
				profesor = new Profesor(nombreProfesor, correoProfesor,telefonoProfesor);
			else
				profesor = new Profesor(nombreProfesor, correoProfesor);
		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}

		return profesor;
	}

	public static String leerNombreProfesor() {
		System.out.print("Introduce el nombre del profesor: ");
		return Entrada.cadena();
	}

	public static Tramo Tramo() {

		Tramo tramo = null;

		do {
			System.out.print("Introduce el tramo (M/T): ");
			char ch = Entrada.caracter();
			if (ch == 'M' || ch == 'm') {
				tramo = Tramo.MANANA;
			} else if (ch == 'T' || ch == 't') {
				tramo = Tramo.TARDE;
			}
		} while (tramo == null);

		return tramo;
	}

	public static LocalDate leerDia() {

		LocalDate data = null;
		LocalDate curDate = LocalDate.now();
		
		do {

			int anno = 0;
			do {
				System.out.print("Introduce el anno (yyyy): ");
				anno = Entrada.entero();				
				// Comprobamos si el año introducido es mayour o igual al corriente
				if (anno < curDate.getYear()) {
					anno = 0;
					System.out.println("el año tiene que ser mayor o igual al actual");
				}
				
			} while (anno <= 0);

			int mes = 0;
			do {
				System.out.print("Introduce el mes (mm): ");
				mes = Entrada.entero();
				//validamos el numero del mes entre 1 y 12
				if (mes < 1 || mes > 12) {
					mes = 0;
					System.out.println("incorrecto valor del mes, tiene que ser del 1 a 12");
					// no pasamo con el mes si es anterior al actual
				} else if(anno == curDate.getYear() && mes < curDate.getMonthValue()) {
					mes = 0;
					System.out.println("El mes no tiene que ser pasado");
				}
				
				

			} while (mes <= 0);

			int dia = 0;
			do {
				System.out.print("Introduce el día (dd): ");
				dia = Entrada.entero();
				// comprobamos si el dia tecleado esta entre dia minimo y dia maximo por lo alto 
				if (dia < 1 || dia > 31) {
					dia = 0;
					System.out.println("incorrecto valor del día");
					// no pasamos el dia si es anterior al actual
				} else if (mes == curDate.getMonthValue() && dia < curDate.getDayOfMonth()){
					dia = 0;
					System.out.println("El dia no tiene que ser pasado");
				} else{
					//sacamos el numero de los dias que tiene el mes en el año tecleado
					YearMonth ym = YearMonth.of(anno, mes);
					int maxDay = ym.lengthOfMonth();
					// comprobamos si el dia tecledo no sobrepasa el maximo de los dias que tiene el mes
					if (dia > maxDay) {
						dia = 0;
						System.out.println("mes " + Month.of(mes) + " del año " + anno + " tiene solo " + maxDay + " dias");
					}
				}

			} while (dia <= 0);

			try {
				data = LocalDate.of(anno, mes, dia);
			} catch (Exception e) {
				System.out.println(ERROR + e.getMessage());
			}
			
		} while (data == null);

		/* mes+
		do {

			int anno = 0;
			do {
				System.out.print("Introduce el anno (yyyy): ");
				anno = Entrada.entero();
				if (anno < curDate.getYear()) {
					anno = 0;
					System.out.println("el año tiene que ser mayor o igual al actual");
				}

				if (curDate.getMonthValue() == 12 && anno == curDate.getYear()) {
					anno = 0;
					System.out.println("Para este año no se aceptan más reervas. Este año se acaba ya!!!");
				}

			} while (anno <= 0);

			int mes = 0;
			do {
				System.out.print("Introduce el mes (mm): ");
				mes = Entrada.entero();

				if (mes < 1 || mes > 12) {
					mes = 0;
					System.out.println("incorrecto valor del mes, tiene que ser del 1 a 12");
					continue;
				}

				if (anno == curDate.getYear()) {
					if (mes <= curDate.getMonthValue()) {
						mes = 0;
						System.out.println("el mes tiene que ser como minimo siguente al actual");
					}
				}

			} while (mes <= 0);

			int dia = 0;
			do {
				System.out.print("Introduce el día (dd): ");
				dia = Entrada.entero();
				if (dia < 0 || dia > 31) {
					dia = 0;
					System.out.println("incorrecto valor del día");
				} else {
					YearMonth ym = YearMonth.of(anno, mes);
					int maxDay = ym.lengthOfMonth();
					if (dia > maxDay) {
						dia = 0;
						System.out.println("mes " + Month.of(mes) + " del año " + anno + " tiene solo " + maxDay);
					}
				}

			} while (dia <= 0);

			data = LocalDate.of(anno, mes, dia);
			
		} while (data == null);
		
		*/

		return data;
	}

}