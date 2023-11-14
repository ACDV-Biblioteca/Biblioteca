package es.uclm.Biblioteca.persistencia;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.uclm.Biblioteca.domain.entities.Autor;
import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Titulo;
import es.uclm.Biblioteca.domain.entities.TituloAutor;
import es.uclm.Biblioteca.domain.entities.TituloAutor.TituloAutorId;

public interface TituloAutorDAO extends JpaRepository<TituloAutor, TituloAutorId>{
	
	@Query(value = "SELECT Autor_ID FROM DERBYUSER.titulo_Autor WHERE TITULO_ISBN =?", nativeQuery = true)
	public List<Autor> findByTitulo(Long titulo);
	
	@Query(value = "SELECT * FROM DERBYUSER.titulo_Autor WHERE AUTOR_ID =?", nativeQuery = true)
	public List<TituloAutor> findByAutor(int autor);

	@Query(value = "SELECT * FROM TituloAutor WHERE isbn_titulo =:isbn AND autor_id =:autor", nativeQuery = true)
	public TituloAutor findByTituloAutorId(long isbn, int autor);
}