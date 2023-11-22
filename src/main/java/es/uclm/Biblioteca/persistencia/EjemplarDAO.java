package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Titulo;


@Repository
public interface EjemplarDAO extends JpaRepository<Ejemplar, Integer>{
	@Query(value = "SELECT * FROM DERBYUSER.ejemplar WHERE ISBN_TITULO =?", nativeQuery = true)
	public List<Ejemplar> findByIsbn(Long ejemplar_isbn);
	@Query(value = "SELECT * FROM Ejemplar e WHERE NOT EXISTS (SELECT 1 FROM Prestamo p WHERE p.ejemplar_id = e.id) AND e.isbn_titulo = ?", nativeQuery = true)
	public List<Ejemplar> findByIsbnNoPrestados(Long isbn);
	@Query(value="SELECT * FROM Ejemplar e WHERE NOT EXISTS (SELECT 1 FROM Prestamo p WHERE p.ejemplar_id = e.id AND p.ACTIVO=TRUE)", nativeQuery = true)
	public List<Ejemplar> findByNoPrestados();
	@Query(value="SELECT * FROM Ejemplar e WHERE EXISTS (SELECT 1 FROM Prestamo p WHERE p.ejemplar_id = e.id AND p.ACTIVO=TRUE)", nativeQuery = true)
	public List<Ejemplar> findByPrestados();
	@Query(value="SELECT * FROM Ejemplar e WHERE EXISTS (SELECT 1 FROM Prestamo p WHERE p.ejemplar_id = e.id AND p.ACTIVO=TRUE and p.usuario_id=:id_usuario)", nativeQuery = true)
	public List<Ejemplar> findByPrestadosUsuario(int id_usuario);
}
