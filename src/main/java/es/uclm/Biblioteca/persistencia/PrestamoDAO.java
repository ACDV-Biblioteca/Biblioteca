package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;
import es.uclm.Biblioteca.domain.entities.Prestamo.PrestamoId;

@Repository
public interface PrestamoDAO extends JpaRepository<Prestamo, PrestamoId>{
	@Query(value="SELECT COUNT(*) FROM Prestamo WHERE usuario_id = ?", nativeQuery = true)
	public int findCountPrestamosUsuario(int usuario_id);
	
	
	
	


}