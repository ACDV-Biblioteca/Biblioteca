package es.uclm.Biblioteca.domain.entities;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
@Table(name="Usuario")
public class Usuario {
	
	@Id
	private int id;
	@Column
	
	private String nombre;
	@Column

	private String apellidos;
	@Column(name = "FECHA_FIN_PENALIZACION")
    @Temporal(TemporalType.DATE)

	private Date fecha_Fin_Penalizacion;
	
	@Column 
	private String contraseña;
	
	@OneToMany(mappedBy = "usuario")
	private Collection<Prestamo> prestamos;
	@OneToMany(mappedBy = "usuario")
	private Collection<Reserva> reservas;
	
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
	public Date getFechaFinPenalizacion() {
		return fecha_Fin_Penalizacion;
	}
	public void setFechaFinPenalizacion(Date fecha_Fin_Penalizacion) {
		this.fecha_Fin_Penalizacion = fecha_Fin_Penalizacion;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public Collection<Prestamo> getPrestamos() {
		return prestamos;
	}
	public void setPrestamos(Collection<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	public Usuario() {
		super();
	}
	public Usuario(int id, String nombre, String apellidos, Date fecha_Fin_Penalizacion, String contraseña,
			Collection<Prestamo> prestamos, Collection<Reserva> reservas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fecha_Fin_Penalizacion = fecha_Fin_Penalizacion;
		this.contraseña = contraseña;
		this.prestamos = prestamos;
		this.reservas = reservas;
	}
	public Collection<Reserva> getReservas() {
		return reservas;
	}
	public void setReservas(Collection<Reserva> reservas) {
		this.reservas = reservas;
	}



}