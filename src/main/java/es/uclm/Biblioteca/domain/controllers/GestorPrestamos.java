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
	private ReservaDAO reservaDAO;
	@Autowired
	private EjemplarDAO ejemplarDAO;
	@Autowired
	private UsuarioDAO usuarioDAO;

	GestorPenalizaciones gestor = new GestorPenalizaciones();

	@GetMapping("/PrestarEjemplarUsuario")
	public String showPrestarEjemplarPageUsuario(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		model.addAttribute("prestamo", new Prestamo());
		return extractedPrestar(model, usuario);

	}

	
	@PostMapping("/PrestarEjemplarUsuario")
	public String prestarEjemplarUsuario(@ModelAttribute Prestamo prestamo,
			@RequestParam(value = "ejemplarId", required = false) Integer ejemplarId, Model model,
			HttpSession session) {
		model.addAttribute("prestamo", prestamo);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		List<Ejemplar> ejemplares = ejemplarDAO.findByNoPrestados();

		if (usuario == null) {
			model.addAttribute("message", "No existe dicho usuario");
			model.addAttribute("ejemplares", ejemplares);
			return "PrestarEjemplarUsuario";

		}
		model.addAttribute("usuario", usuario);
		model.addAttribute("message", "");
		model.addAttribute("ejemplares", ejemplares);

		if (ejemplarId == null) {
			model.addAttribute("message", "Debes seleccionar un ejemplar.");

			model.addAttribute("ejemplares", ejemplares);

			return "PrestarEjemplarUsuario";
		} else {

			int idEjemplarSeleccionado = ejemplarId;
			Ejemplar ejemplar = ejemplarDAO.findById(idEjemplarSeleccionado).orElse(null);
			if (ejemplar == null) {
				model.addAttribute("ejemplares", ejemplares);
				model.addAttribute("message", "El ejemplar con ID " + idEjemplarSeleccionado + " no existe");
				return "PrestarEjemplarUsuario";
			}
			RealizarPrestamo(prestamo,usuario,model, ejemplar);
		}
		return "PrestarEjemplarUsuario";
	}
	private String extractedPrestar(Model model, Usuario usuario) {
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByNoPrestados();// Obtener la lista de ejemplares desde tu

		
		if (usuario != null) {
			model.addAttribute("usuario", usuario);

			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("message", "");
			return "PrestarEjemplarUsuario";
		} else {
			model.addAttribute("usuario", usuario);

			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("message", "No existe dicho usuario");
			return "PrestarEjemplarUsuario";
		}
	}
	private void RealizarPrestamo(Prestamo prestamo,Usuario usuario,Model model, Ejemplar ejemplar) {
		LocalDate fechahoy = LocalDate.now();

		Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());

		if (prestamoDAO.findCountPrestamosUsuario(usuario.getId()) > 10) {
			model.addAttribute("message", "El usuario tiene el cupo de libros completo.");

		} else if (gestor.comprobarPenalizacion(usuario, usuarioDAO, fechaHoy)) {
			model.addAttribute("message", "El usuario tiene penalizaciones pendientes hasta "+ usuario.getFechaFinPenalizacion());

		} else {

			LocalDate fechafutura = fechahoy.plusWeeks(2);

			Date fechaFutura = Date.from(fechafutura.atStartOfDay(ZoneId.systemDefault()).toInstant());
			prestamo.setActivo(true);
			prestamo.setFechaFin(fechaFutura);
			prestamo.setFechaInicio(fechaHoy);
			prestamo.setEjemplar(ejemplar);
			prestamo.setTitulo(ejemplar.getTitulo());
			prestamo.setUsuario(usuario);
			log.info("Se han eliminado  " + reservaDAO.deleteByEjemplar(ejemplar.getId())
					+ " reservas con el ejemplar :" + ejemplar.getId());
			log.info("Saved " + prestamoDAO.save(prestamo));

			model.addAttribute("message", "Préstamo realizado con éxito.");

		}
	

	}
	@GetMapping("/PrestarEjemplar")
	public String showPrestarEjemplarPage(Model model) {
		model.addAttribute("prestamo", new Prestamo());
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByNoPrestados();// Obtener la lista de ejemplares desde tu
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
			RealizarPrestamo(prestamo,usuario,model,ejemplar);
		}
		return "PrestarEjemplar";
	}

	@GetMapping("/DevolucionUsuario")
	public String mostrarFormularioDevolucionUsuario(HttpSession session, Model model) {
		Usuario u = (Usuario) session.getAttribute("usuario");
		return extractedDevolucion(model, u);
	}


	private String extractedDevolucion(Model model, Usuario u) {
		if (u == null) {
			model.addAttribute("usuario", u);
			model.addAttribute("message", "No existe dicho usuario");
			return "DevolucionUsuario";

		} else {
			model.addAttribute("usuario", u);
			List<Ejemplar> prestamos = ejemplarDAO.findByPrestadosUsuario(u.getId());
			model.addAttribute("prestamos", prestamos);// Obtener la lista de
			if (prestamos.isEmpty()) {
				model.addAttribute("message", "Este usuario no dispone de ningun prestamo realizado");

			}
			return "DevolucionUsuario";
		}
	}

	private void RealizarDevolucion(Prestamo prestamo,Ejemplar ejemplar,Usuario usuario,Model model) {
		LocalDate fechahoy = LocalDate.now();
		Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());

		if (prestamo.getFechaFin().before(fechaHoy)) {
			LocalDate fechafutura = fechahoy.plusWeeks(2);
			Date fechaFutura = Date.from(fechafutura.atStartOfDay(ZoneId.systemDefault()).toInstant());
			usuario.setFechaFinPenalizacion(fechaFutura);
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
	}
	@PostMapping("/DevolucionUsuario")
	public String realizarDevolucionUsuario(@RequestParam(value = "ejemplarId", required = false) Integer ejemplarId,
			Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario == null) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("message", "No existe dicho usuario");
			return "DevolucionUsuario";
		}

		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestadosUsuario(usuario.getId());// Obtener la lista de
																								// ejemplares desde tu
																								// repositorio o
		model.addAttribute("ejemplares", listaEjemplares);
		int idEjemplarSeleccionado = ejemplarId;
		Ejemplar ejemplar = ejemplarDAO.findById(idEjemplarSeleccionado).orElse(null);
		if (ejemplar == null) {
			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("message", "El ejemplar con ID " + idEjemplarSeleccionado + " no existe");
			return "DevolucionUsuario";
		}

		Prestamo prestamo = prestamoDAO.findByPrestamoId(ejemplar.getId(), usuario.getId(),
				ejemplar.getTitulo().getIsbn());
		if (prestamo != null) {
			
			RealizarDevolucion(prestamo,ejemplar,usuario,model);
		}
		return "DevolucionUsuario";

	}

	@GetMapping("/DevolucionEjemplar")
	public String mostrarFormularioDevolucionBibliotecario(Model model) {
		model.addAttribute("prestamos", prestamoDAO.findByPrestados());

		return "DevolucionEjemplar";
	}

	@PostMapping("/DevolucionEjemplar")
	public String realizarDevolucionBibliotecario(
			@RequestParam(value = "prestamoEjemplarId", required = false) Integer prestamoEjemplarId, Model model) {
		model.addAttribute("prestamos", prestamoDAO.findByPrestados());

		int idEjemplarSeleccionado = prestamoEjemplarId;
		Prestamo prestamo = prestamoDAO.findByEjemplarId(idEjemplarSeleccionado);
		if (prestamo == null) {
			model.addAttribute("message",
					"El prestamo con el ID del ejemplar : " + idEjemplarSeleccionado + " no existe");
			return "DevolucionEjemplar";
		} else {
			RealizarDevolucion(prestamo,prestamo.getEjemplar(),prestamo.getUsuario(),model);

			
		}
		return "DevolucionEjemplar";

	}

	@GetMapping("/ReservaEjemplar")
	public String mostrarListaYFormulario(Model model) {
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestados();// Obtener la lista de ejemplares desde tu
																		// repositorio o
		// servicio
		model.addAttribute("ejemplares", listaEjemplares);
		model.addAttribute("reserva", new Reserva());

		// Formulario para la acción que realizarás
		return "ReservaEjemplar"; // Devuelve el nombre de la vista Thymeleaf
	}

	private void ReservarEjemplar(Reserva reserva, Usuario usuario, Ejemplar ejemplar,Model model) {
		LocalDate fechahoy = LocalDate.now();

		Date fechaHoy = Date.from(fechahoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
		reserva.setUsuario(usuario);
		reserva.setEjemplar(ejemplar);
		reserva.setFecha(fechaHoy);

		log.info("Saved " + reservaDAO.save(reserva));

		model.addAttribute("message", "Reserva realizada con éxito.");

	}
	@PostMapping("/ReservaEjemplar")
	public String procesarEjemplar(@ModelAttribute Reserva reserva,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "ejemplarId", required = false) Integer ejemplarId, Model model) {
		model.addAttribute("reserva", reserva);
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestados();// Obtener la lista de ejemplares desde tu
																		// repositorio o

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
			ReservarEjemplar(reserva,usuario,ejemplar,model);
		}
		return "ReservaEjemplar";
	}

	@GetMapping("/ReservarEjemplarUsuario")
	public String mostrarListaYFormularioUsuario(HttpSession session, Model model) {
		// repositorio o
		// servicio
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		return extractedReserva(model, usuario);

	}


	private String extractedReserva(Model model, Usuario usuario) {
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestados();// Obtener la lista de ejemplares desde tu

		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("mensaje", "");
			model.addAttribute("reserva", new Reserva());
			return "ReservarEjemplarUsuario";

		} else {
			model.addAttribute("usuario", usuario);
			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("reserva", new Reserva());
			model.addAttribute("mensaje", "No hay ningun usuario registrado");

			return "ReservarEjemplarUsuario";
		}
	}

	@PostMapping("/ReservarEjemplarUsuario")
	public String procesarEjemplarUsuario(@ModelAttribute Reserva reserva,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "ejemplarId", required = false) Integer ejemplarId, Model model,
			HttpSession session) {
		model.addAttribute("reserva", reserva);
		List<Ejemplar> listaEjemplares = ejemplarDAO.findByPrestados();// Obtener la lista de ejemplares desde tu
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario == null) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("mensaje", "El usuario no existe.");

		} else{// repositorio o

		if (ejemplarId == null) {
			model.addAttribute("mensaje", "Debes seleccionar un ejemplar.");

			model.addAttribute("ejemplares", listaEjemplares);

			return "ReservarEjemplarUsuario";
		} else {

			int idEjemplarSeleccionado = ejemplarId;
			Ejemplar ejemplar = ejemplarDAO.findById(idEjemplarSeleccionado).orElse(null);
			if (ejemplar == null) {
				model.addAttribute("ejemplares", listaEjemplares);
				model.addAttribute("mensaje", "El ejemplar con ID " + idEjemplarSeleccionado + " no existe");
				return "ReservaEjemplarUsuario";
			}
			model.addAttribute("mensaje",
					"Ejemplar " + idEjemplarSeleccionado + " seleccionado por el usuario " + usuario.getNombre());

			model.addAttribute("ejemplares", listaEjemplares);
			model.addAttribute("userId", usuario.getId());
			ReservarEjemplar(reserva,usuario,ejemplar,model);


		}
	}
		return "ReservarEjemplarUsuario";

	}
	
}
