package es.uclm.Biblioteca.persistencia;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import es.uclm.Biblioteca.domain.entities.Autor;
import es.uclm.Biblioteca.domain.entities.Ejemplar;

public interface AutorDAO extends JpaRepository<Autor, Integer>{
	
	@Query(value = "SELECT * FROM DERBYUSER.autor WHERE NOMBRE =?", nativeQuery = true)
	public List<Autor> findById(String nombre);

}