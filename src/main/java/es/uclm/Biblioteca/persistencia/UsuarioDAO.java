package es.uclm.Biblioteca.persistencia;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.uclm.Biblioteca.domain.entities.Usuario;
import jakarta.transaction.Transactional;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
	@Modifying
	@Query("UPDATE Usuario  SET fecha_Fin_Penalizacion = :fecha WHERE id = :id_usuario")
	@Transactional
	public int aplicarPenalizacion(@Param("id_usuario") int id_usuario, @Param("fecha") java.util.Date fecha);

	@Query(value="Select Fecha_fin_penalizacion from usuario where id = :id_usuario",nativeQuery=true)
	public java.util.Date comprobarPenalizacion(@Param("id_usuario") int id_usuario);
}