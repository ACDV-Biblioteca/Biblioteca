package es.uclm.Biblioteca.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.uclm.Biblioteca.domain.entities.Reserva;
import es.uclm.Biblioteca.domain.entities.Reserva.ReservaId;

@Repository
public interface ReservaDAO extends JpaRepository<Reserva,ReservaId>{
@Query(value="",nativeQuery=true)
public int deleteByEjemplar(int ejemplar_id);
}