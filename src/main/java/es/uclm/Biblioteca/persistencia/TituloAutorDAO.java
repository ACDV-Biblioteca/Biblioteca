package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.uclm.Biblioteca.domain.entities.TituloAutor;
import es.uclm.Biblioteca.domain.entities.TituloAutor.TituloAutorId;
import jakarta.transaction.Transactional;

@Repository
public interface TituloAutorDAO extends JpaRepository<TituloAutor, TituloAutorId> {
	@Query(value = "SELECT * FROM DERBYUSER.Titulo_Autor WHERE TITULO_ISBN =?", nativeQuery = true)
	public List<TituloAutor> findByIsbn(Long titulo_isbn);
	@Query(value = "SELECT * FROM DERBYUSER.Titulo_Autor WHERE Autor_id =?", nativeQuery = true)
	public List<TituloAutor> findByAutor(int titulo_isbn);
	@Modifying
    @Query(value="DELETE FROM DERBYUSER.Titulo_Autor WHERE TITULO_ISBN = :titulo_isbn", nativeQuery = true)
    @Transactional
	public int deleteByISBN(Long titulo_isbn);

}
