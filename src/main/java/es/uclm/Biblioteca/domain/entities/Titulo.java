package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Titulo {
	/*@ManyToOne(targetEntity=Autor.class)

	Collection<Autor> autores;
	@ManyToOne(targetEntity=Ejemplar.class)

	Collection<Ejemplar> ejemplares;
	@ManyToOne(targetEntity=Prestamo.class)

	Collection<Prestamo> prestamos;
	
	@ManyToOne(targetEntity=Reserva.class)

	Collection<Reserva> reservas;
	*/
	@Column
	private int titulo;
	@Column(columnDefinition= "default 0")
	private int numReserva;

	@Id
	private int isbn;

}