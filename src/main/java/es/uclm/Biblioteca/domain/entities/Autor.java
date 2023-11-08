package es.uclm.Biblioteca.domain.entities;

import java.util.*;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "Autor")
public class Autor {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	 	@Column
	    private String nombre;
	 	@Column
	    private String apellidos;

	    @OneToMany(mappedBy = "autor")
	    private Collection<TituloAutor> titulos;


    // Constructor, getters y setters
}
