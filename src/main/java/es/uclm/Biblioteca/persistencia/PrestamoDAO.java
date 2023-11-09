package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;

@Repository
public interface PrestamoDAO extends JpaRepository<Prestamo, Integer>{
	@Query(value="SELECT COUNT(*) FROM Prestamo WHERE usuario_id = ?", nativeQuery = true)
	public int findCountPrestamosUsuario(int usuario_id);
	


}