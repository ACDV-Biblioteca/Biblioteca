package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Titulo;

public interface TituloDAO extends JpaRepository<Titulo, Long>{
	
	@Query(value = "SELECT * FROM DERBYUSER.titulo WHERE ISBN =?", nativeQuery = true)
	public List<Titulo> findByIsbn(Long isbn);

}