package es.uclm.Biblioteca.domain.controllers;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;
import es.uclm.Biblioteca.domain.entities.Reserva;
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
	@Autowired
	private ReservaDAO reservaDAO;
	@Autowired
	private EjemplarDAO ejemplarDAO;
	@Autowired
	private UsuarioDAO usuarioDAO;

	GestorPenalizaciones gestor = new GestorPenalizaciones();

	/**
	 * 
	 * @param isbn
	 * @param idEjemplar
	 * @param idUsuario
	 */
	@GetMapping("/PrestarEjemplar")
	public String showPrestarEjemplarPage(Model model) {
		model.addAttribute("prestamo", new Prestamo());
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByNoPrestados();// Obtener la lista de ejemplares desde tu
																			// repositorio o
		// servicio
		model.addAttribute("ejemplares", listaEjemplares);
		model.addAttribute("message", "");
		return "PrestarEjemplar";
	}

	// Método para procesar la solicitud de préstamo de ejemplar
	@PostMapping("/PrestarEjemplar")
	public String prestarEjemplar(@ModelAttribute Prestamo prestamo,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "ejemplarId", required = false) Integer ejemplarId, Model model) {
		model.addAttribute("prestamo", prestamo);
		List<Ejemplar> ejemplares = ejemplarDAO.findByNoPrestados();

		if (userId == null || userId.isEmpty()) {
			model.addAttribute("ejemplares", ejemplares);

			model.addAttribute("message", "Por favor, introduce un ID de usuario válido");
			return "PrestarEjemplar";

		} else if (ejemplarId == null) {
			model.addAttribute("message", "Debes seleccionar un ejemplar.");

			model.addAttribute("ejemplares", ejemplares);
			model.addAttribute("userId", userId);

			return "PrestarEjemplar";
		} else {

			int idUsuario = Integer.parseInt(userId);

			Usuario usuario = usuarioDAO.findById(idUsuario).orElse(null);
			if (usuario == null) {
				model.addAttribute("ejemplares", ejemplares);
				model.addAttribute("message", "El usuario con ID " + idUsuario + " no existe");
				return "PrestarEjemplar";
			}

			int idEjemplarSeleccionado = ejemplarId;
			Ejemplar ejemplar = ejemplarDAO.findById(idEjemplarSeleccionado).orElse(null);
			if (ejemplar == null) {
				model.addAttribute("ejemplares", ejemplares);
				model.addAttribute("message", "El ejemplar con ID " + idEjemplarSeleccionado + " no existe");
				return "PrestarEjemplar";
			}
			model.addAttribute("ejemplares", ejemplares);

			model.addAttribute("message",
					"Ejemplar " + idEjemplarSeleccionado + " seleccionado por el usuario " + usuario.getNombre());

			LocalDate fechahoy = LocalDate.now();

			Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());

			if (prestamoDAO.findCountPrestamosUsuario(usuario.getId()) > 10) {
				model.addAttribute("message", "El usuario tiene el cupo de libros completo.");

			} else if (gestor.comprobarPenalizacion(usuario, usuarioDAO, fechaHoy)) {
				model.addAttribute("message", "El usuario tiene penalizaciones pendientes.");

			} else {

				LocalDate fechafutura = fechahoy.plusWeeks(2);

				Date fechaFutura = Date.from(fechafutura.atStartOfDay(ZoneId.systemDefault()).toInstant());
				prestamo.setActivo(true);
				prestamo.setFechaFin(fechaFutura);
				prestamo.setFechaInicio(fechaHoy);
				prestamo.setEjemplar(ejemplar);
				prestamo.setTitulo(ejemplar.getTitulo());
				prestamo.setUsuario(usuario);
				log.info("Se han eliminado  "+ reservaDAO.deleteByEjemplar(ejemplar.getId())+" reservas con el ejemplar :"+ ejemplar.getId());
				log.info("Saved " + prestamoDAO.save(prestamo));

				model.addAttribute("message", "Préstamo realizado con éxito.");

			}
		}

		return "PrestarEjemplar";
	}

	/**
	 * 
	 * @param isbn
	 * @param idEjemplar
	 * @param idUsuario
	 */
	@GetMapping("/DevolucionUsuario")
	public String mostrarFormularioDevolucion(HttpSession session,Model model) {
		Usuario u=(Usuario) session.getAttribute("usuario");
		 if (u != null) {
	            model.addAttribute("usuario", u);

		 }
		return "DevolucionUsuario";
	}
	@GetMapping("/DevolucionEjemplar")
	public String mostrarFormularioDevolucion(HttpSession session, Model model) {
		Usuario u = (Usuario) session.getAttribute("usuario");
		if (u != null) {
			model.addAttribute("usuario", u);

		}
		return "DevolucionEjemplar";
	}
	@PostMapping("/DevolucionUsuario")
	public String realizarDevolucion(@RequestParam("isbn") Long isbn,
									 @RequestParam("ejemplarId") int ejemplarId, Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario != null) {
      	            model.addAttribute("usuario", u);

    }
    
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestadosUsuario(u.getId());// Obtener la lista de ejemplares desde tu repositorio o
		model.addAttribute("ejemplares", listaEjemplares);
		@SuppressWarnings("deprecation")
		int idEjemplarSeleccionado = ejemplarId;
		Ejemplar ejemplar = ejemplarDAO.findById(idEjemplarSeleccionado).orElse(null);
		if (ejemplar == null) {
			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("message", "El ejemplar con ID " + idEjemplarSeleccionado + " no existe");
			return "DevolucionUsuario";
		}

			Prestamo prestamo = prestamoDAO.findByPrestamoId(ejemplar.getId(), u.getId(), ejemplar.getTitulo().getIsbn());
			if (prestamo != null) {
				LocalDate fechahoy = LocalDate.now();
			@SuppressWarnings("deprecation")

			Titulo titulo = tituloDAO.getById(isbn);
			Ejemplar ejemplar = ejemplarDAO.getById(ejemplarId);
			if (!ejemplar.getTitulo().getIsbn().equals(titulo.getIsbn())) {
				model.addAttribute("message", "El Isbn no concuerda con el id del ejemplar dado");
			} else {

				Prestamo prestamo = prestamoDAO.findByPrestamoId(ejemplar.getId(), usuario.getId(), titulo.getIsbn());
				if (prestamo != null) {
					LocalDate fechahoy = LocalDate.now();

					Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
					if (prestamo.getFechaFin().before(fechaHoy)) {
						LocalDate fechafutura = fechahoy.plusWeeks(2);
					Date fechaFutura = Date.from(fechafutura.atStartOfDay(ZoneId.systemDefault()).toInstant());
					u.setFechaFinPenalizacion(fechaFutura);
					if (gestor.aplicarPenalizacion(u, usuarioDAO) == 1) {
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
				} else {
					model.addAttribute("message", "No hay ningun prestamo con los datos obtenidos");

				}
			}
			return "DevolucionUsuario";
		}
  }


	@PostMapping("/DevolucionEjemplar")
	public String realizarDevolucion( @RequestParam("isbn") Long isbn,
			@RequestParam(value = "ejemplarId", required = false) Integer ejemplarId, Model model, HttpSession session) {
		// log.info(((Usuario) session.getAttribute("usuario")).toString());
		Usuario u = (Usuario) session.getAttribute("usuario");
		if (u != null) {
			model.addAttribute("usuario", u);

		}
	
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestadosUsuario(u.getId());// Obtener la lista de ejemplares desde tu repositorio o
		model.addAttribute("ejemplares", listaEjemplares);
		@SuppressWarnings("deprecation")
		int idEjemplarSeleccionado = ejemplarId;
		Ejemplar ejemplar = ejemplarDAO.findById(idEjemplarSeleccionado).orElse(null);
		if (ejemplar == null) {
			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("message", "El ejemplar con ID " + idEjemplarSeleccionado + " no existe");
			return "DevolucionEjemplar";
		}

			Prestamo prestamo = prestamoDAO.findByPrestamoId(ejemplar.getId(), u.getId(), ejemplar.getTitulo().getIsbn());
			if (prestamo != null) {
				LocalDate fechahoy = LocalDate.now();
			@SuppressWarnings("deprecation")

			Titulo titulo = tituloDAO.getById(isbn);
			Ejemplar ejemplar = ejemplarDAO.getById(ejemplarId);
			if (!ejemplar.getTitulo().getIsbn().equals(titulo.getIsbn())) {
				model.addAttribute("message", "El Isbn no concuerda con el id del ejemplar dado");
			} else {

				Prestamo prestamo = prestamoDAO.findByPrestamoId(ejemplar.getId(), usuario.getId(), titulo.getIsbn());
				if (prestamo != null) {
					LocalDate fechahoy = LocalDate.now();

					Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
					if (prestamo.getFechaFin().before(fechaHoy)) {
						LocalDate fechafutura = fechahoy.plusWeeks(2);
					Date fechaFutura = Date.from(fechafutura.atStartOfDay(ZoneId.systemDefault()).toInstant());
					u.setFechaFinPenalizacion(fechaFutura);
					if (gestor.aplicarPenalizacion(u, usuarioDAO) == 1) {
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
				} else {
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

        return null;
    }
    }
			} else {
				model.addAttribute("message", "No hay ningun prestamo con los datos obtenidos");

			}
		
		return "DevolucionEjemplar";
	}

	/**
	 * 
	 * @param idUsuario
	 * @param isbn
	 */
	@GetMapping("/ReservaEjemplar")
	public String mostrarListaYFormulario(Model model) {
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestados();// Obtener la lista de ejemplares desde tu repositorio o
																// servicio
		model.addAttribute("ejemplares", listaEjemplares);
		model.addAttribute("reserva", new Reserva());

		// Formulario para la acción que realizarás
		return "ReservaEjemplar"; // Devuelve el nombre de la vista Thymeleaf
	}

	@PostMapping("/ReservaEjemplar")
	public String procesarEjemplar(@ModelAttribute Reserva reserva,@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "ejemplarId", required = false) Integer ejemplarId, Model model) {
		model.addAttribute("reserva", reserva);
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestados();// Obtener la lista de ejemplares desde tu repositorio o

		if (userId == null || userId.isEmpty()) {
			model.addAttribute("ejemplares", listaEjemplares);

			model.addAttribute("mensaje", "Por favor, introduce un ID de usuario válido");
			return "ReservaEjemplar";

		} else if (ejemplarId == null) {
			model.addAttribute("mensaje", "Debes seleccionar un ejemplar.");

			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("userId", userId);

			return "ReservaEjemplar";
		} else {

			int idUsuario = Integer.parseInt(userId);

			Usuario usuario = usuarioDAO.findById(idUsuario).orElse(null);
			if (usuario == null) {
				model.addAttribute("ejemplares", listaEjemplares);
				model.addAttribute("mensaje", "El usuario con ID " + idUsuario + " no existe");
				return "ReservaEjemplar";
			}

			int idEjemplarSeleccionado = ejemplarId;
			Ejemplar ejemplar = ejemplarDAO.findById(idEjemplarSeleccionado).orElse(null);
			if (ejemplar == null) {
				model.addAttribute("ejemplares", listaEjemplares);
				model.addAttribute("mensaje", "El ejemplar con ID " + idEjemplarSeleccionado + " no existe");
				return "ReservaEjemplar";
			}
			model.addAttribute("mensaje",
					"Ejemplar " + idEjemplarSeleccionado + " seleccionado por el usuario " + usuario.getNombre());

			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("userId", usuario.getId());
			LocalDate fechahoy = LocalDate.now();

			Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
			reserva.setUsuario(usuario);
			reserva.setEjemplar(ejemplar);
			reserva.setFecha(fechaHoy);

			log.info("Saved " + reservaDAO.save(reserva));

			model.addAttribute("message", "Préstamo realizado con éxito.");

		}
		return "ReservaEjemplar";
	}

}
