package es.uclm.Biblioteca.domain.entities;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Ejemplar {
	public Ejemplar() {

	}

	public Ejemplar(int titulo, int id) {
		super();
		this.titulo = titulo;
		this.id = id;
	}

	@Id
	private int id;

	private int titulo;

	public int getTitulo() {
		return titulo;
	}

	public void setTitulo(int titulo) {
		this.titulo = titulo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Ejemplar [id=%s, titulo=%s]", id, titulo);
	}

}