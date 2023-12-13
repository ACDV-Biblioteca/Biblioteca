package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;
import es.uclm.Biblioteca.domain.entities.Prestamo.PrestamoId;
import jakarta.transaction.Transactional;
import es.uclm.Biblioteca.domain.entities.Titulo;
import es.uclm.Biblioteca.domain.entities.Usuario;

@Repository
public interface PrestamoDAO extends JpaRepository<Prestamo, PrestamoId>{
	@Query(value="SELECT COUNT(*) FROM Prestamo WHERE usuario_id = ?", nativeQuery = true)
	public int findCountPrestamosUsuario(int usuario_id);
    @Query(value="SELECT * FROM Prestamo WHERE usuario_id = :usuario AND ejemplar_id = :ejemplar AND titulo_id = :titulo",nativeQuery=true)
    public Prestamo findByPrestamoId(int ejemplar, int usuario, Long titulo);
    @Modifying
    @Query(value="DELETE FROM DERBYUSER.PRESTAMO WHERE TITULO_ID = :titulo_isbn", nativeQuery = true)
    @Transactional
	public int deleteByISBN(Long titulo_isbn);
    @Query(value="SELECT * FROM Prestamo WHERE ejemplar_id = :ejemplar AND activo = true",nativeQuery=true)
    public Prestamo findByEjemplarId(int ejemplar);
    @Query(value="Select * from Prestamo where activo=true",nativeQuery=true)
    public List<Prestamo> findByPrestados();
    
	
	
	
	


}