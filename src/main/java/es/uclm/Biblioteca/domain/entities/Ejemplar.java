package es.uclm.Biblioteca.domain.entities;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Ejemplar {
	public Ejemplar() {

	}

	public Ejemplar(Titulo titulo, String id) {
		super();
		this.titulo = titulo;
		this.id = id;
	}

	public Ejemplar(String titulo, String id) {
		super();
		this.t = titulo;
		this.id = id;
	}
	@ManyToOne(targetEntity=Titulo.class)
	Titulo titulo;

	@Id
	private String id;
	@Column
	private String t;

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Ejemplar [id=%s, titulo=%s]", id, titulo);
	}

}