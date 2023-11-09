package es.uclm.Biblioteca.domain.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;
import es.uclm.Biblioteca.domain.entities.Usuario;
import es.uclm.Biblioteca.persistencia.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class GestorPrestamos {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);

	@Autowired
	private PrestamoDAO prestamoDAO;
	
	ReservaDAO reservaDAO;
	@Autowired
	private EjemplarDAO ejemplarDAO;
	@Autowired
	private UsuarioDAO usuarioDAO;

	/**
	 * 
	 * @param isbn
	 * @param idEjemplar
	 * @param idUsuario
	 */
    @GetMapping("/PrestarEjemplar")
    public String showPrestarEjemplarPage(Model model) {
    	model.addAttribute("prestamo", new Prestamo());
    	model.addAttribute("message", ""); 
        return "PrestarEjemplar";
    }

    // Método para procesar la solicitud de préstamo de ejemplar
    @PostMapping("/PrestarEjemplar")
    public String prestarEjemplar(@ModelAttribute Prestamo prestamo, Model model) {
		model.addAttribute("prestamo", prestamo);
		List<Ejemplar> ejemplarOpt2 = ejemplarDAO.findByIsbnNoPrestados(prestamo.getTitulo().getIsbn());
		
		Usuario usuario=usuarioDAO.getById(prestamo.getUsuario().getId());
		
		
        // Aquí deberías implementar la lógica de comprobación de cupo y penalizaciones
        // Puedes acceder a los datos del formulario a través del objeto 'prestamo'

        // Ejemplo de lógica de comprobación (puedes adaptar según tus necesidades)
        if (prestamoDAO.findCountPrestamosUsuario(prestamo.getUsuario().getId())>=10) {
            model.addAttribute("message", "El usuario tiene el cupo de libros completo.");
        } else if (prestamo.getUsuario().getFechaFinPenalizacion()==null) {
            model.addAttribute("message", "El usuario tiene penalizaciones pendientes.");
        } else {
            // Aquí deberías realizar el proceso de préstamo
            // Puedes agregar la lógica para actualizar la base de datos, etc.
        	
            model.addAttribute("message", "Préstamo realizado con éxito.");
        }

        // Retorna la vista correspondiente (puede ser la misma página de préstamo con el mensaje)
        return "PrestarEjemplar";
    }

	/**
	 * 
	 * @param isbn
	 * @param idEjemplar
	 * @param idUsuario
	 */
	public void realizarDevolucion(String isbn, String idEjemplar, String idUsuario) {
		// TODO - implement GestorPrestamos.realizarDevolucion
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUsuario
	 * @param isbn
	 */
	public void realizarReserva(String idUsuario, String isbn) {
		// TODO - implement GestorPrestamos.realizarReserva
		throw new UnsupportedOperationException();
	}

}