package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Autor {
	/*@ManyToOne(targetEntity=Titulo.class)

	Collection<Titulo> titulos;*/
	@Id
	private String nombre;
	@Id
	private String apellidos;

}