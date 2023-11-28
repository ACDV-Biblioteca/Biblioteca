package es.uclm.Biblioteca.domain.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "Titulo_Autor")
@IdClass(TituloAutor.TituloAutorId.class)

public class TituloAutor  implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "titulo_isbn", referencedColumnName = "isbn")
    private Titulo titulo;

    @Id
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public static class TituloAutorId implements Serializable {
        private Autor autor;
        private Titulo titulo;

        public TituloAutorId() {
        }

        public TituloAutorId(Ejemplar ejemplar, Autor autor, Titulo titulo) {
            this.autor = autor;
            this.titulo = titulo;
        }

        public Autor getAutor() {
            return autor;
        }

        public void setAutor(Autor autor) {
            this.autor = autor;
        }

        public Titulo getTitulo() {
            return titulo;
        }

        public void setTitulo(Titulo titulo) {
            this.titulo = titulo;
        }

    }
    public TituloAutor() {
		super();
    }
	public TituloAutor(Titulo titulo, Autor autor) {
		super();
		this.titulo = titulo;
		this.autor = autor;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	
    
}

