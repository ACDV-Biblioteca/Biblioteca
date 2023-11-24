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
	    private List<TituloAutor> titulos;
		public Autor() {
		
		}

		public Autor(int id, String nombre, String apellidos, List<TituloAutor> titulos) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.apellidos = apellidos;
			this.titulos = titulos;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellidos() {
			return apellidos;
		}

		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}

		public List<TituloAutor> getTitulos() {
			return titulos;
		}

		public void setTitulos(List<TituloAutor> titulos) {
			this.titulos = titulos;
		}

		@Override
		public String toString() {
			return "Autor [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", titulos=" + titulos + "]";
		}
		@Override
		public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o == null || getClass() != o.getClass()) return false;
		    Autor autor = (Autor) o;
		    return Objects.equals(nombre, autor.nombre) &&
		            Objects.equals(apellidos, autor.apellidos) &&  Objects.equals(id, autor.id) ;
		}


    // Constructor, getters y setters
}
