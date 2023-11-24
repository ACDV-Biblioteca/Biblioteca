package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.uclm.Biblioteca.domain.entities.Autor;

public interface AutorDAO extends JpaRepository<Autor, Integer>{
	@Query(value="SELECT a.ID ,a.NOMBRE, a.APELLIDOS FROM Autor a JOIN Titulo_Autor ta ON a.ID = ta.AUTOR_ID JOIN Titulo t ON ta.TITULO_ISBN = t.ISBN WHERE t.ISBN = :isbn_titulo",nativeQuery=true)
	public List<Autor> findAutoresByIsbn(Long isbn_titulo);
	@Query(value="SELECT * FROM DERBYUSER.Autor WHERE nombre = :nombre AND apellidos = :apellidos ",nativeQuery = true)
	public Autor findByNombreApellidos(String nombre, String apellidos);
	

}