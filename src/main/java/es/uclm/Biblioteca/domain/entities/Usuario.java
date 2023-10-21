package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Usuario {
	@ManyToOne(targetEntity=Prestamo.class)

	Collection<Prestamo> prestamos;
	@ManyToOne(targetEntity=Reserva.class)

	Collection<Reserva> reservas;
	@Id
	private String id;
	private String nombre;
	private String apellidos;
	private Date fechaFinPenalizacion;
	private int attribute;

}