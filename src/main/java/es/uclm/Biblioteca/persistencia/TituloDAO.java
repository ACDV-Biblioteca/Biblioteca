package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Titulo;

public interface TituloDAO extends JpaRepository<Titulo, Long>{
	
	@Query(value = "SELECT a.Nombre, a.Apellidos FROM Autor JOIN TITULO_AUTOR ta ON a.ID = ta.Autor_ID JOIN Titulo t ON ta.Titulo_ISBN = t.ISBN WHERE t.ISBN =?", nativeQuery = true)
	public List<Object[]> findByIsbn(Long isbn);
	
	//@Query(value = "SELECT * FROM DERBYUSER.titulo WHERE I =?", nativeQuery = true)
	//public List<Titulo> findByIsb(Long isbn);

}