package es.uclm.Biblioteca.domain.controllers;

import es.uclm.Biblioteca.persistencia.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import es.uclm.Biblioteca.domain.entities.*;

@Controller
public class GestorTitulos {

	TituloDAO tituloDAO;
	
	@Autowired
	private EjemplarDAO ejemplarDAO;
	
	AutorDAO autorDAO;

	/**
	 * 
	 * @param titulo
	 * @param isbn
	 * @param autores
	 */
	public Titulo altaTitulo(String titulo, String isbn, String[] autores) {
		// TODO - implement GestorTitulos.altaT�tulo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	public void actualizarTitulo(Titulo t) {
		// TODO - implement GestorTitulos.actualizarTitulo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	public void borrarTitulo(Titulo t) {
		// TODO - implement GestorTitulos.borrarTitulo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	public void altaEjemplar(Titulo t) {
		// TODO - implement GestorTitulos.altaEjemplar
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	@GetMapping("/BorrarEjemplar")
	public String showBorrarEjemplarPage(Model model) {
	    model.addAttribute("Ejemplar", new Ejemplar());
	    return "BorrarEjemplar";
	}

	@PostMapping("/BorrarEjemplar")
	public String bajaEjemplar(@ModelAttribute Ejemplar ejemplar, Model model) {
	    // Buscar el Ejemplar por ID
	    Optional<Ejemplar> ejemplarOpt = ejemplarDAO.findById(ejemplar.getId());

	    // Si el Ejemplar existe, eliminarlo
	    if(ejemplarOpt.isPresent()) {
	        ejemplarDAO.delete(ejemplarOpt.get());
	        // Puedes agregar un mensaje de confirmación si lo deseas
	        model.addAttribute("message", "El ejemplar ha sido borrado correctamente.");
	    } else {
	        // Manejar el caso en el que el Ejemplar no se encuentra

	        model.addAttribute("message", "No se encontró el ejemplar con ese ID.");
	       
	    }

	    return "BorrarEjemplar";
	}


}