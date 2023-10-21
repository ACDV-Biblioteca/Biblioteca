package es.uclm.Biblioteca.domain.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Prestamo {
	@Id
	@ManyToOne(targetEntity=Usuario.class)
	Usuario usuario;
	@Id
	@ManyToOne(targetEntity=Titulo.class)
	Titulo titulo;
	
	private Date fechaInicio;
	private Date fechaFin;
	private Boolean activo;

}