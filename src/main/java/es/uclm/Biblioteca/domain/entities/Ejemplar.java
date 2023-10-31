package es.uclm.Biblioteca.domain.entities;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Ejemplar {
	public Ejemplar() {

	}

	public Ejemplar(int isbn_titulo, int id) {
		super();
		this.isbn_titulo = isbn_titulo;
		this.id = id;
	}

	@Id
	private int id;
	@Column
	private int isbn_titulo;

	public int getTitulo() {
		return isbn_titulo;
	}

	public void setTitulo(int titulo) {
		this.isbn_titulo = titulo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Ejemplar [id=%s, titulo=%s]", id, isbn_titulo);
	}

}