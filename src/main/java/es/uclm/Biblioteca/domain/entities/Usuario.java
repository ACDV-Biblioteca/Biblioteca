package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
@Table(name="Usuario")
public class Usuario {
	
	@Id
	private int id;
	@Column

	private String nombre;
	@Column

	private String apellidos;
	@Column
    @Temporal(TemporalType.DATE)

	private Date fechaFinPenalizacion;
	
	@OneToMany(mappedBy = "usuario")
	private Collection<Prestamo> prestamos;
	@OneToMany(mappedBy = "usuario")
	private Collection<Reserva> reservas;



}