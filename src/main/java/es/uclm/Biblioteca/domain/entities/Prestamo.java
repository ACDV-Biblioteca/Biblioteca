package es.uclm.Biblioteca.domain.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="Prestamo")
public class Prestamo {
		   @Id
		    @ManyToOne
		    @JoinColumn(name = "titulo_isbn", referencedColumnName = "isbn")
		    private Titulo titulo;
	
		    @Id
		    @ManyToOne
		    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
		    private Usuario usuario;

	@Column
    @Temporal(TemporalType.DATE)

	private Date fechaInicio;
	@Column
    @Temporal(TemporalType.DATE)

	private Date fechaFin;
	@Column

	private Boolean activo;

}