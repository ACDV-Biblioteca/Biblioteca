	package es.uclm.Biblioteca.domain.entities;
	
	import java.io.Serializable;
	import java.util.Date;
	
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.Id;
	import jakarta.persistence.IdClass;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.JoinColumns;
	
	import jakarta.persistence.ManyToOne;
	import jakarta.persistence.Table;
	import jakarta.persistence.Temporal;
	import jakarta.persistence.TemporalType;
	import jakarta.persistence.Transient;
	
	@Entity
	@Table(name = "Prestamo")
	@IdClass(Prestamo.PrestamoId.class)
	public class Prestamo implements Serializable {

	    @Id
	    @ManyToOne
	    @JoinColumn(name = "ejemplar_id", referencedColumnName = "id")
	    private Ejemplar ejemplar;

	    @Id
	    @ManyToOne
	    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
	    private Usuario usuario;

	    @Id
	    @ManyToOne
	    @JoinColumn(name = "titulo_id", referencedColumnName = "isbn")
	    private Titulo titulo;

	    @Column
	    @Temporal(TemporalType.DATE)
	    private Date fechaInicio;

	    @Column
	    @Temporal(TemporalType.DATE)
	    private Date fechaFin;

	    @Column
	    private Boolean activo;


	    public Prestamo() {
	    	
	    }

	    public Prestamo(Ejemplar ejemplar, Usuario usuario, Titulo titulo, Date fechaInicio, Date fechaFin, Boolean activo) {
	        this.ejemplar = ejemplar;
	        this.usuario = usuario;
	        this.titulo = titulo;
	        this.fechaInicio = fechaInicio;
	        this.fechaFin = fechaFin;
	        this.activo = activo;
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

	    public Titulo getTitulo() {
	        return titulo;
	    }

	    public void setTitulo(Titulo titulo) {
	        this.titulo = titulo;
	    }

	    public Date getFechaInicio() {
	        return fechaInicio;
	    }

	    public void setFechaInicio(Date fechaInicio) {
	        this.fechaInicio = fechaInicio;
	    }

	    public Date getFechaFin() {
	        return fechaFin;
	    }

	    public void setFechaFin(Date fechaFin) {
	        this.fechaFin = fechaFin;
	    }

	    public Boolean getActivo() {
	        return activo;
	    }

	    public void setActivo(Boolean activo) {
	        this.activo = activo;
	    }

	    // Debes crear una clase estática para la clave primaria compuesta
	    public static class PrestamoId implements Serializable {
	        private Ejemplar ejemplar;
	        private Usuario usuario;
	        private Titulo titulo;

	        public PrestamoId() {
	        }

	        public PrestamoId(Ejemplar ejemplar, Usuario usuario, Titulo titulo) {
	            this.ejemplar = ejemplar;
	            this.usuario = usuario;
	            this.titulo = titulo;
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

	        public Titulo getTitulo() {
	            return titulo;
	        }

	        public void setTitulo(Titulo titulo) {
	            this.titulo = titulo;
	        }

	        // Puedes implementar equals y hashCode aquí si es necesario
	    }
	}
