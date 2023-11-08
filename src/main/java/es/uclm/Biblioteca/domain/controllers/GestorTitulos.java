package es.uclm.Biblioteca.domain.controllers;

import es.uclm.Biblioteca.persistencia.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import es.uclm.Biblioteca.domain.entities.*;

@Controller
public class GestorTitulos {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);
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
		model.addAttribute("ejemplar", new Ejemplar());
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		return "BorrarEjemplar";
	}

	@PostMapping("/BorrarEjemplar")
	public String bajaEjemplar(@ModelAttribute Ejemplar ejemplar, Model model) {
		model.addAttribute("ejemplar", ejemplar);

		List<Ejemplar> ejemplarOpt2 = ejemplarDAO.findByIsbn(ejemplar.getEjemplar_isbn());

		// Si el Ejemplar existe, eliminarlo
		if (!ejemplarOpt2.isEmpty()) {
			if (ejemplarOpt2.size() == 1) {
				ejemplarDAO.delete(ejemplarOpt2.get(ejemplarOpt2.size() - 1));

				log.info("Se ha borrado el ejemplar " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getId()
						+ " con titulo " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getTitulo().getTitulo());
				model.addAttribute("message",
						"Se ha borrado el ejemplar " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getId()
								+ " con titulo " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getTitulo().getTitulo());
				Titulo titulo = new Titulo();
				titulo.setIsbn(ejemplar.getEjemplar_isbn());
				borrarTitulo(titulo);

			} else {
				ejemplarDAO.delete(ejemplarOpt2.get(ejemplarOpt2.size() - 1));
				model.addAttribute("message",
						"Se ha borrado el ejemplar " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getId()
								+ " con titulo " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getTitulo().getTitulo());

				log.info("Se ha borrado el ejemplar " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getId()
						+ " con titulo " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getTitulo().getTitulo());

			}

		} else {
			// Manejar el caso en el que el Ejemplar no se encuentra

			model.addAttribute("message", "No se encontró el ejemplar con ese ISBN.");
			log.info("Este ejemplar no existe");

		}

		return "BorrarEjemplar";
	}

}