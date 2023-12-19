package es.uclm.Biblioteca.domain.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="Reserva")
@IdClass(Reserva.ReservaId.class)
public class Reserva implements Serializable{
	@Id
	@ManyToOne
	@JoinColumn(name = "ejemplar_id", referencedColumnName = "id")
	private Ejemplar ejemplar;

	@Id
	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	@Column
    @Temporal(TemporalType.DATE)
	private Date fecha;
	public Reserva(Ejemplar ejemplar, Usuario usuario, Date fecha) {
		super();
		this.ejemplar = ejemplar;
		this.usuario = usuario;
		this.fecha = fecha;
	}
	
	public Reserva() {
		
	}

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	} // Debes crear una clase estática para la clave primaria compuesta
	
   

	public static class ReservaId implements Serializable {
        private Ejemplar ejemplar;
        private Usuario usuario;

        public ReservaId() {
        }

        public ReservaId(Ejemplar ejemplar, Usuario usuario) {
            this.ejemplar = ejemplar;
            this.usuario = usuario;
        }

        public Ejemplar getEjemplar() {
            return ejemplar;
        }

        public void setEjemplar(Ejemplar ejemplar) {
            this.ejemplar = ejemplar;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

      
    }
	
	

}