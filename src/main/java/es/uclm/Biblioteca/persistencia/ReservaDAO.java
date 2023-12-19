package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.uclm.Biblioteca.domain.entities.Reserva;
import es.uclm.Biblioteca.domain.entities.TituloAutor;
import es.uclm.Biblioteca.domain.entities.Reserva.ReservaId;
import jakarta.transaction.Transactional;

@Repository
public interface ReservaDAO extends JpaRepository<Reserva,ReservaId>{
@Modifying
@Query(value="DELETE FROM DERBYUSER.RESERVA WHERE EJEMPLAR_ID =:ejemplar_id",nativeQuery=true)
@Transactional
public int deleteByEjemplar(int ejemplar_id);
@Query(value = "SELECT * FROM DERBYUSER.RESERVA WHERE TITULO_ISBN =?", nativeQuery = true)
public List<TituloAutor> findByIsbn(Long titulo_isbn);
@Modifying
@Query(value="DELETE FROM DERBYUSER.RESERVA WHERE EJEMPLAR_ID IN (SELECT ID FROM DERBYUSER.EJEMPLAR WHERE ISBN_TITULO = :titulo_isbn)", nativeQuery = true)
@Transactional
public int deleteByISBN(Long titulo_isbn);
}