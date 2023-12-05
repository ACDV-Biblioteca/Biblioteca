package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.uclm.Biblioteca.domain.entities.Titulo;

@Repository
public interface TituloDAO extends JpaRepository<Titulo, Long> {

	@Query(value="SELECT * FROM DERBYUSER.Titulo WHERE isbn = :isbn",nativeQuery = true)
	public Titulo findByIsbn(Long isbn);
}

