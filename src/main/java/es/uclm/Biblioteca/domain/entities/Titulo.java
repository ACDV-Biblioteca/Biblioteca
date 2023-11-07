package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Titulo")
public class Titulo {

	/*
	 * 
	 * @ManyToOne(targetEntity=Ejemplar.class)
	 * 
	 * Collection<Ejemplar> ejemplares;
	 * 
	 * @ManyToOne(targetEntity=Prestamo.class)
	 * 
	 * Collection<Prestamo> prestamos;
	 * 
	 * @ManyToOne(targetEntity=Reserva.class)
	 * 
	 * Collection<Reserva> reservas;
	 */
	@Id
	private int isbn;

	@Column
	private String titulo;
	@Column(columnDefinition = "default 0")
	private int numReserva;
	@OneToMany(mappedBy = "titulo")
	Collection<Ejemplar> ejemplares;

	@ManyToMany
	@JoinTable(
		    name = "Titulo_Autor",
		    joinColumns = @JoinColumn(name = "isbn"),
		    inverseJoinColumns = {
		        @JoinColumn(name = "autor_nombre", referencedColumnName = "nombre"),
		        @JoinColumn(name = "autor_apellidos", referencedColumnName = "apellidos")
		    }
		)
	Collection<Autor> autores;

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getNumReserva() {
		return numReserva;
	}

	public void setNumReserva(int numReserva) {
		this.numReserva = numReserva;
	}

	public Collection<Ejemplar> getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(Collection<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}

}