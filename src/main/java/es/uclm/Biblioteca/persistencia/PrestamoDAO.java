package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;
import es.uclm.Biblioteca.domain.entities.Prestamo.PrestamoId;
import es.uclm.Biblioteca.domain.entities.Titulo;
import es.uclm.Biblioteca.domain.entities.Usuario;

@Repository
public interface PrestamoDAO extends JpaRepository<Prestamo, PrestamoId>{
	@Query(value="SELECT COUNT(*) FROM Prestamo WHERE usuario_id = ?", nativeQuery = true)
	public int findCountPrestamosUsuario(int usuario_id);
    @Query(value="SELECT * FROM Prestamo WHERE usuario_id = :usuario AND ejemplar_id = :ejemplar AND titulo_id = :titulo",nativeQuery=true)
    public Prestamo findByPrestamoId(int ejemplar, int usuario, Long titulo);

	
	
	
	


}