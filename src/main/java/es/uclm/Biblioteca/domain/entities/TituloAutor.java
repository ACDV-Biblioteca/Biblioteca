package es.uclm.Biblioteca.domain.entities;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "Titulo_Autor")
@IdClass(TituloAutor.TituloAutorId.class)
public class TituloAutor {
  

	@Id
    @ManyToOne
    @JoinColumn(name = "titulo_isbn", referencedColumnName = "isbn")
    private Titulo titulo;

	@Id
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
	  @Override
		public String toString() {
			return "TituloAutor [titulo=" + titulo + ", autor=" + autor + "]";
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

		public static class TituloAutorId implements Serializable{
			private Titulo titulo;
			private Autor autor;
			
			public TituloAutorId() {
				
			}
			
			public TituloAutorId(Titulo titulo, Autor autor) {
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
}

