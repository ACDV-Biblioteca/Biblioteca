package es.uclm.Biblioteca.persistencia;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.uclm.Biblioteca.domain.entities.Autor;
import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Titulo;
import es.uclm.Biblioteca.domain.entities.TituloAutor;

public interface TituloAutorDAO extends JpaRepository<Titulo, Long>{
	
	@Query(value = "SELECT * FROM DERBYUSER.tituloAutor WHERE TITULOAUTOR =?", nativeQuery = true)
	public List<TituloAutor> findById(Titulo titulo);
	
	@Query(value = "SELECT * FROM DERBYUSER.tituloAutor WHERE TITULOAUTOR =?", nativeQuery = true)
	public List<TituloAutor> findById(Autor autor);
}