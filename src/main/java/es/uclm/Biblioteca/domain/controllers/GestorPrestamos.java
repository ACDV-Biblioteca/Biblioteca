package es.uclm.Biblioteca.domain.controllers;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;
import es.uclm.Biblioteca.domain.entities.Titulo;
import es.uclm.Biblioteca.domain.entities.Usuario;
import es.uclm.Biblioteca.persistencia.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class GestorPrestamos {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);

	@Autowired
	private PrestamoDAO prestamoDAO;
	@Autowired
	private TituloDAO tituloDAO;
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

		Usuario usuario = usuarioDAO.getById(prestamo.getUsuario().getId());
		LocalDate fechahoy = LocalDate.now();

		Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());

		if (prestamoDAO.findCountPrestamosUsuario(prestamo.getUsuario().getId()) > 10) {
			model.addAttribute("message", "El usuario tiene el cupo de libros completo.");
		} else if ((usuario.getFechaFinPenalizacion() == null) || usuario.getFechaFinPenalizacion().after(fechaHoy)) {
			model.addAttribute("message", "El usuario tiene penalizaciones pendientes.");
		} else if (ejemplarOpt2.size() == 0) {
			model.addAttribute("message", "No se dispone de ejemplares de este titulo para hacer prestamos");

		} else {
			Ejemplar ej = ejemplarOpt2.get(ejemplarOpt2.size() - 1);
			Titulo titulo = tituloDAO.getById(prestamo.getTitulo().getIsbn());

			LocalDate fechafutura = fechahoy.plusWeeks(2);

			Date fechaFutura = Date.from(fechafutura.atStartOfDay(ZoneId.systemDefault()).toInstant());
			prestamo.setActivo(true);
			prestamo.setFechaFin(fechaFutura);
			prestamo.setFechaInicio(fechaHoy);
			prestamo.setEjemplar(ej);
			prestamo.setTitulo(titulo);
			prestamo.setUsuario(usuario);

			log.info("Saved " + prestamoDAO.save(prestamo));

			model.addAttribute("message", "Préstamo realizado con éxito.");
		}

		return "PrestarEjemplar";
	}

	/**
	 * 
	 * @param isbn
	 * @param idEjemplar
	 * @param idUsuario
	 */
	@GetMapping("/DevolucionEjemplar")
	public String mostrarFormularioDevolucion(HttpSession session,Model model) {
		Usuario u=(Usuario) session.getAttribute("usuario");
		 if (u != null) {
	            model.addAttribute("usuario", u);

		 }
		return "DevolucionEjemplar";
	}

	@PostMapping("/DevolucionEjemplar")
	public String realizarDevolucion(@RequestParam("userId") int userId, @RequestParam("isbn") Long isbn,
			@RequestParam("ejemplarId") int ejemplarId, Model model,HttpSession session) {
		 	//log.info(((Usuario) session.getAttribute("usuario")).toString());

		
		@SuppressWarnings("deprecation")
		Usuario usuario = usuarioDAO.getById(userId);
		Titulo titulo = tituloDAO.getById(isbn);
		Ejemplar ejemplar = ejemplarDAO.getById(ejemplarId);
		if (!ejemplar.getTitulo().getIsbn().equals(titulo.getIsbn())) {
			model.addAttribute("message", "El Isbn no concuerda con el id del ejemplar dado");
		} else {

			Prestamo prestamo = prestamoDAO.findByPrestamoId(ejemplar.getId(), usuario.getId(), titulo.getIsbn());
			if (prestamo!=null) {
				LocalDate fechahoy = LocalDate.now();

				Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
				if (prestamo.getFechaFin().before(fechaHoy)) {
					LocalDate fechafutura = fechahoy.plusWeeks(2);

					Date fechaFutura = Date.from(fechafutura.atStartOfDay(ZoneId.systemDefault()).toInstant());
					usuario.setFechaFinPenalizacion(fechaFutura);
					GestorPenalizaciones gestor = new GestorPenalizaciones();
					if (gestor.aplicarPenalizacion(usuario, usuarioDAO) == 1) {
						prestamoDAO.delete(prestamo);
						log.info("Delete: " + prestamo);

						model.addAttribute("message",
								"Devolución realizada con éxito con Penalizacion hasta " + fechaFutura.toString());

					} else {
						model.addAttribute("message", "Ha ocurrido un problema al aplicar la penalizacion");

					}

				} else {
					prestamoDAO.delete(prestamo);
					log.info("Delete: " + prestamo);
					model.addAttribute("message", "Devolución realizada con éxito");

				}
			}else {
				model.addAttribute("message", "No hay ningun prestamo con los datos obtenidos");

			}
		}
		return "DevolucionEjemplar";
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