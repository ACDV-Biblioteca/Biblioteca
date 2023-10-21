package es.uclm.Biblioteca.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import es.uclm.Biblioteca.domain.entities.Ejemplar;


@Repository
public interface EjemplarDAO extends JpaRepository<Ejemplar, Integer>{

   
}
