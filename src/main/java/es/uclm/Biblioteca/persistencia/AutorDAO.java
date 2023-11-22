package es.uclm.Biblioteca.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uclm.Biblioteca.domain.entities.Autor;

public interface AutorDAO extends JpaRepository<Autor, Integer>{

}