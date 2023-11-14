package es.uclm.Biblioteca.domain.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uclm.Biblioteca.domain.entities.Usuario;
import es.uclm.Biblioteca.persistencia.UsuarioDAO;
import jakarta.servlet.http.HttpSession;

@Controller

public class GestorLogin {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);

	@Autowired
	private UsuarioDAO usuariorDAO;

	@GetMapping("/pagina-de-inicio")
	public String mostrarFormularioInicioSesion() {
		return "pagina-de-inicio"; // Nombre del archivo HTML
	}

	@GetMapping("/procesarInicioSesion")
	public String LoginUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		return "procesarInicioSesion"; // Nombre del archivo HTML
	}

	@PostMapping("/procesarInicioSesion")
	public String PostLoginUsuario(@ModelAttribute Usuario usuario, Model model,HttpSession session) {
		model.addAttribute("usuario", usuario);
		Optional<Usuario> u = usuariorDAO.findById(usuario.getId());

		if (u.isPresent()) {
			if ((u.get().getContraseña()).equals(usuario.getContraseña())) {
				usuario.setNombre(u.get().getNombre());
				session.setAttribute("usuario", u.get());
				return "PanelUsuario";
			} else {
				model.addAttribute("message", "La contraseña no es la misma"); // Inicializa el mensaje como vacío

			}
		} else {
			model.addAttribute("message", "No existe ningun Usuario con ese id"); // Inicializa el mensaje como vacío

		}

		return "procesarInicioSesion"; // Nombre del archivo HTML
	}
	
	@GetMapping("/PanelAdministrador")
	public String mostrarFormularioAdministrador() {
		return "PanelAdministrador"; // Nombre del archivo HTML
	}

}
