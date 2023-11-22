package es.uclm.Biblioteca.domain.entities;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Collection;

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
	@JoinColumn(name = "isbn_titulo",referencedColumnName="isbn")
	private Titulo titulo;
	
	
	@OneToMany(mappedBy = "ejemplar")
    private Collection<Prestamo> prestamos;
	@OneToMany(mappedBy = "ejemplar")
	private Collection<Reserva> reserva;

	public Ejemplar() {
		super();
	}

	public Ejemplar(int id, Titulo titulo) {
		super();
		this.titulo = titulo;
		this.id = id;
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
		return String.format("Ejemplar [id=%s, titulo=%s]", id, titulo.getTitulo());
	}

}