package org.iesalandalus.programacion.reservasaulas.vista;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.ModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Tramo;

public class IUTextual {

	private static final String ERROR = "ERROR: ";
	// private static final String NOMBRE_VALIDO = "\\w+";
	private static final String CORREO_VALIDO = "good@mail.com";

	private ModeloReservasAulas modelo;

	public IUTextual() {
		modelo = new ModeloReservasAulas();
		Opcion.setVista(this);
	}

	public void comenzar() {
		int ordinalOpcion;
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());

	}

	public void salir() {
		System.out.println("\nHasta luego ...!!!");
	}

	// -----aulas-----
	public void insertarAula() {

		Consola.mostrarCabecera("\nInsertar aula");

		try {
			Aula aula = Consola.leerAula();
			modelo.insertarAula(aula);
			System.out.println("Aula insertada correctamente");
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void borrarAula() {

		Consola.mostrarCabecera("\nBorrar aula");

		try {
			Aula aula = Consola.leerAula();
			modelo.borrarAula(aula);
			System.out.println("Aula borrada correctamente");
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void buscarAula() {

		Consola.mostrarCabecera("\nBuscar aula");
		Aula aula = null;

		try {
			aula = Consola.leerAula();
			aula = modelo.buscarAula(aula);
			if (aula != null) {
				System.out.println("El aula buscado es: " + aula);
			} else {
				System.out.println("No existe ningún aula con dicho nombre");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarAulas() {

		Consola.mostrarCabecera("\nListar aulas");

		List<String> aulas = modelo.representarAulas();

		if (aulas.size() > 0) {
			for (String aula : aulas) {
				System.out.println(aula);
			}
		} else {
			System.out.println("No hay aulas para listar");
		}
	}

	// ----profesores--------
	public void insertarProfesor() {

		Consola.mostrarCabecera("\nInsertar profesor");

		try {
			Profesor profesor = Consola.leerProfesor();
			modelo.insertarProfesor(profesor);
			System.out.println("Profesor insertado correctamente");
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void borrarProfesor() {

		Consola.mostrarCabecera("\nBorrar prfesor");

		try {
			String nombreProfesor = Consola.leerNombreProfesor().trim();

			Profesor profesor = null;
			profesor = modelo.buscarProfesor(new Profesor(nombreProfesor, CORREO_VALIDO));

			if (profesor != null) {
				modelo.borrarProfesor(profesor);
				System.out.println("Profesor borrado correctamente");
			} else {
				System.out.println("Profesor que queres borrar todavia no se ha registrado...");
			}

		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void buscarProfesor() {

		Consola.mostrarCabecera("\nBuscar profesor");

		try {
			String nombreProfesor = Consola.leerNombreProfesor().trim();

			Profesor profesor = null;
			profesor = modelo.buscarProfesor(new Profesor(nombreProfesor, CORREO_VALIDO));

			if (profesor != null) {
				System.out.println("El profesor buscado es: " + profesor.toString());
			} else {
				System.out.println("No existe ningún profesor con dicho nombre");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarProfesor() {

		Consola.mostrarCabecera("\nListar profesores");
		List<String> profesores = modelo.representarProfesores();
		if (profesores.size() > 0) {
			for (String profesor : profesores) {
				System.out.println(profesor);
			}
		} else {
			System.out.println("No hay profesores para listar");
		}
	}

	// ------Reservas------

	public void realizarReserva() {

		Consola.mostrarCabecera("\nRealizar reserva");

		try {

			boolean yaExiste = false;
			String nombreProfesor = Consola.leerNombreProfesor().trim();
			Profesor profesor = modelo.buscarProfesor(new Profesor(nombreProfesor, CORREO_VALIDO));

			if (profesor != null) {

				Reserva reserva = leerReserva(profesor);

				List<Reserva> reservas = modelo.getReservas();

				for (Reserva res : reservas) {
					if (res.equals(reserva))
						yaExiste = true;
				}

				if (!yaExiste) {
					modelo.realizarReserva(reserva);
					System.out.println("La reserva fue realizada co exito!!!");
				} else
					System.out.println("La misma reserva ya existe.");

			} else {
				System.out.println("Profesor " + nombreProfesor + " no esta registrado en el sistema");
			}

		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	private Reserva leerReserva(Profesor profesor) {

		// Consola.mostrarCabecera("Leer reserva");

		Reserva reserva = null;

		try {

			Aula aula = Consola.leerAula();

			if (modelo.buscarAula(aula) != null) {

				// creamos la nueva reserva
				LocalDate dia = Consola.leerDia();
				Tramo tramo = Consola.Tramo();

				reserva = new Reserva(profesor, aula, new Permanencia(dia, tramo));

			} else {
				System.out.println("Alua " + aula.getNombre() + " no eta registrada en el sistema");
			}

		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}

		return reserva;
	}

	public void anularReserva() {

		Consola.mostrarCabecera("\nAnular reserva");

		try {
			String nombreProfesor = Consola.leerNombreProfesor().trim();
			Profesor profesor = modelo.buscarProfesor(new Profesor(nombreProfesor, CORREO_VALIDO));

			if (profesor != null) {

				Reserva reserva = leerReserva(profesor);

				if (reserva != null) {
					modelo.anularReserva(reserva);
					System.out.println("La reserva fue borrada correctamente");
				}

			} else {
				System.out.println("Profesor " + nombreProfesor + " no esta registrado en el sistema");
			}
		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarReserva() {

		Consola.mostrarCabecera("\nListar reserva");

		List<String> reservasSTR = modelo.representarReservas();

		if (reservasSTR.size() > 0) {
			for (String reserva : reservasSTR) {
				System.out.println(reserva.toString());
			}
		} else {
			System.out.println("No hay reservas para listar ...");
		}
	}

	public void listarReservasAulas() {

		Consola.mostrarCabecera("\nlista reservas de aulas");

		try {
			Aula aula = Consola.leerAula();

			if (modelo.buscarAula(aula) != null) {

				List<Reserva> aulas = modelo.getReservasAula(aula);

				if (aulas.size() > 0) {
					for (Reserva reserva : aulas) {
						System.out.println(reserva.toString());
					}
				} else {
					System.out.println("No hay reservas para esta aula ...");
				}
			} else {
				System.out.println("Alua " + aula.getNombre() + " no eta registrada en el sistema");
			}
		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarReservasProfesores() {

		Consola.mostrarCabecera("\nlista reservas de profesores");

		try {

			String nombreProfesor = Consola.leerNombreProfesor();
			Profesor profesor = new Profesor(nombreProfesor, CORREO_VALIDO);

			if (modelo.buscarProfesor(profesor) != null) {

				List<Reserva> profesores = modelo.getReservaProfesor(profesor);

				if (profesores.size() > 0) {
					for (Reserva reserva : profesores) {
						System.out.println(reserva.toString());
					}
				} else {
					System.out.println("No hay reservas para este profesor ...");
				}

			} else {
				System.out.println("Profesor " + nombreProfesor + " no esta registrado en el sistema");
			}
		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarReservasPermanencia() {

		Consola.mostrarCabecera("\nlista reservas de permanencia");

		try {
			LocalDate dia = Consola.leerDia();
			Tramo tramo = Consola.Tramo();
			Permanencia permanencia = new Permanencia(dia, tramo);

			List<Reserva> permanencias = modelo.getReservaPermanencia(permanencia);

			if (permanencias.size() > 0) {
				boolean anyReserva = false;
				for (Reserva reserva : permanencias) {
					if (reserva != null) {
						System.out.println(reserva.toString());
						anyReserva = true;
					}
				}
				if (!anyReserva)
					System.out.println("No hay reservas para esta permanencia ...");
			} else {
				System.out.println("No hay reservas con esta permanencia");
			}
		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void consultarDisponibilidad() {

		Consola.mostrarCabecera("\nConsultar disponibilidad");

		boolean isDisponible = true;

		try {

			Aula aula = Consola.leerAula();

			if (modelo.buscarAula(aula) != null) {

				LocalDate dia = Consola.leerDia();
				Tramo tramo = Consola.Tramo();

				isDisponible = modelo.consultarDisponibilidad(aula, new Permanencia(dia, tramo));

				if (isDisponible)
					System.out.println("\naula " + aula.getNombre() + " esta disponible para la " + tramo.toString()
							+ " al día " + dia.toString());
				else
					System.out.println("\naula " + aula.getNombre() + " no esta disponible para la " + tramo.toString()
							+ " al día " + dia.toString());

			} else {
				System.out.println("Alua " + aula.getNombre() + " no eta registrada en el sistema");
			}
		} catch (Exception e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

}