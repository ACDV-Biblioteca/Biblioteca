package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uclm.Biblioteca.domain.controllers.GestorTitulos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Titulo")
public class Titulo {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);

	@Id
	private Long isbn;

	@Column(name="nombre")
	private String nombre;

	@Column
	private int numReserva;

	@OneToMany(mappedBy = "titulo")
	private List<TituloAutor> autores;
	@OneToMany(mappedBy = "titulo")
	private Collection<Prestamo> prestamos;

	
	@OneToMany(mappedBy = "titulo")
	private Collection<Ejemplar> ejemplares;
	public Long getIsbn() {
		return isbn;
	}
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumReserva() {
		return numReserva;
	}
	public void setNumReserva(int numReserva) {
		this.numReserva = numReserva;
	}
	public List<TituloAutor> getAutores() {
		return autores;
	}
	public void setAutores(List<TituloAutor> autores) {
		this.autores = autores;
	}
	public Collection<Prestamo> getPrestamos() {
		return prestamos;
	}
	public void setPrestamos(Collection<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	
	public Collection<Ejemplar> getEjemplares() {
		return ejemplares;
	}
	public void setEjemplares(Collection<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}
	public Titulo() {
		super();
		this.autores=new ArrayList();
		
	}
	public Titulo(Long isbn, String nombre, int numReserva, List<TituloAutor> autores,
			Collection<Prestamo> prestamos, Collection<Ejemplar> ejemplares) {
		super();
		this.isbn = isbn;
		this.nombre = nombre;
		this.numReserva = numReserva;
		this.autores = autores;
		this.prestamos = prestamos;
		this.ejemplares = ejemplares;
	}

	@Override
	public String toString() {
		return "Titulo{" +
				"isbn=" + isbn +
				", titulo='" + nombre + '\'' +
				", numReserva=" + numReserva +
				", autores=" + autores +
				", prestamos=" + prestamos +
				", ejemplares=" + ejemplares +
				'}';
	}
	
	
}