package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Titulo")
public class Titulo {
	@Id
	private Long isbn;

	@Column
	private String titulo;

	@Column
	private int numReserva;

	@OneToMany(mappedBy = "titulo")
	private Collection<TituloAutor> autores;
	@OneToMany(mappedBy = "titulo")
	private Collection<Prestamo> prestamos;

	@OneToMany(mappedBy = "titulo")
	private Collection<Reserva> reserva;
	@OneToMany(mappedBy = "titulo")
	private Collection<Ejemplar> ejemplares;
	@Transient
	private List<Autor> autores2;
	
	public Long getIsbn() {
		return isbn;
	}
	public void setIsbn(Long isbn) {
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
	public Collection<TituloAutor> getTitulosAutores() {
		return autores;
	}
	public Collection<Autor> getAutores() {
		return autores2;
	}
	public void setAutores(Collection<TituloAutor> autores) {
		this.autores = autores;
	}
	public Collection<Prestamo> getPrestamos() {
		return prestamos;
	}
	public void setPrestamos(Collection<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	public Collection<Reserva> getReserva() {
		return reserva;
	}
	public void setReserva(Collection<Reserva> reserva) {
		this.reserva = reserva;
	}
	public Collection<Ejemplar> getEjemplares() {
		return ejemplares;
	}
	public void setEjemplares(Collection<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}
	public Titulo() {
		super();
		
	}
	public Titulo(Long isbn, String titulo, int numReserva, Collection<TituloAutor> autores,
			Collection<Prestamo> prestamos, Collection<Reserva> reserva, Collection<Ejemplar> ejemplares) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.numReserva = numReserva;
		this.autores = autores;
		this.prestamos = prestamos;
		this.reserva = reserva;
		this.ejemplares = ejemplares;
	}
	@Override
	public String toString() {
		return "Titulo [isbn=" + isbn + ", titulo=" + titulo + ", numReserva=" + numReserva + ", autores=" + autores
				+ ", prestamos=" + prestamos + ", reserva=" + reserva + ", ejemplares=" + ejemplares + ", autores2="
				+ autores2 + "]";
	}
	
}