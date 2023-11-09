package es.uclm.Biblioteca.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uclm.Biblioteca.domain.entities.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer>{
	

}