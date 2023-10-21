package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Autor {
	@ManyToOne(targetEntity=Titulo.class)

	Collection<Titulo> titulos;
	@Id
	private int id;
	private String nombre;
	private String apellido;

}