package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="Autor")
public class Autor {
	
	@Id
    @Column(name = "nombre", length = 50)
	private String nombre;
	@Id
    @Column(name = "apellidos", length = 50)

	private String apellidos;
	
	@ManyToMany(mappedBy = "autores")
	Collection<Titulo> titulos;

}