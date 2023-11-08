package es.uclm.Biblioteca.domain.entities;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "Ejemplar")
public class Ejemplar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "isbn_titulo")
	private Titulo titulo;
	
	@Transient
	private Long ejemplar_isbn;

	public Ejemplar() {

	}

	public Ejemplar(int id, Titulo titulo) {
		super();
		this.titulo = titulo;
		this.id = id;
	}

	public Ejemplar(int id, Long ejemplar_isbn) {
		super();
		this.ejemplar_isbn = ejemplar_isbn;
		this.id = id;
	}
	

	public Long getEjemplar_isbn() {
		return ejemplar_isbn;
	}

	public void setEjemplar_isbn(Long ejemplar_isbn) {
		this.ejemplar_isbn = ejemplar_isbn;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
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
		return String.format("Ejemplar [id=%s, titulo=%s,isbn=%s]", id, titulo,ejemplar_isbn);
	}

}