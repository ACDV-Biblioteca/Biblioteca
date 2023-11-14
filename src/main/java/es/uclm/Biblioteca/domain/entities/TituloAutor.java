package es.uclm.Biblioteca.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Titulo_Autor")
public class TituloAutor {
    @Id
    @ManyToOne
    @JoinColumn(name = "titulo_isbn", referencedColumnName = "isbn")
    private Titulo titulo;

    @Id
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Getters y setters
}

