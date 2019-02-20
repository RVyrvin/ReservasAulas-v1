package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import java.util.ArrayList;
import java.util.List;

public class Aulas {

	private List<Aula> coleccionAulas;

	public Aulas() {
		coleccionAulas = new ArrayList<>();
	}

	public Aulas(Aulas aulas) {
		setAulas(aulas);
	}

	private void setAulas(Aulas aulas) {
		if (aulas == null)
			throw new IllegalArgumentException("No se pueden copiar aulas nulas.");
		else {
			this.coleccionAulas = copiaProfundaAulas(aulas.coleccionAulas);
		}
	}

	private List<Aula> copiaProfundaAulas(List<Aula> aulas) {
		List<Aula> cpyAlua = new ArrayList<>();
		for (Aula aula : aulas) {
			cpyAlua.add(new Aula(aula));
		}
		return cpyAlua;
	}

	public List<Aula> getAulas() {
		return copiaProfundaAulas(coleccionAulas);
	}

	public int getNumAulas() {
		return this.coleccionAulas.size();
	}

	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null)
			throw new IllegalArgumentException("No se puede insertar un aula nula.");
		else {
			if (coleccionAulas.contains(aula)) {
				throw new OperationNotSupportedException("El aula ya existe.");
			} else {
				coleccionAulas.add(new Aula(aula));
			}
		}

	}

	public Aula buscar(Aula aula) {
		int index = coleccionAulas.indexOf(aula);
		if (index != -1)
			return new Aula(this.coleccionAulas.get(index));
		else
			return null;
	}

	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede borrar un aula nula.");
		} else {
			if (!coleccionAulas.remove(aula)) {
				throw new OperationNotSupportedException("El aula a borrar no existe.");
			}
		}
	}

	public List<String> representar() {
		List<String> str = new ArrayList<>();
		for (Aula aula : coleccionAulas) {
			str.add(aula.toString());
		}
		return str;
	}
}