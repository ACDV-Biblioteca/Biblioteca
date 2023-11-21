package es.uclm.Biblioteca.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.uclm.Biblioteca.domain.entities.Reserva;
import es.uclm.Biblioteca.domain.entities.Reserva.ReservaId;
import jakarta.transaction.Transactional;

@Repository
public interface ReservaDAO extends JpaRepository<Reserva,ReservaId>{
@Modifying
@Query(value="DELETE FROM DERBYUSER.RESERVA WHERE EJEMPLAR_ID =:ejemplar_id",nativeQuery=true)
@Transactional
public int deleteByEjemplar(int ejemplar_id);
}