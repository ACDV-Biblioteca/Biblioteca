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
import org.springframework.web.bind.annotation.RequestParam;

import es.uclm.Biblioteca.domain.entities.Usuario;
import es.uclm.Biblioteca.persistencia.UsuarioDAO;
import jakarta.servlet.http.HttpSession;

@Controller

public class GestorLogin {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);

	@Autowired
	UsuarioDAO usuarioDAO;

	@GetMapping("/pagina-de-inicio")
	public String mostrarFormularioInicioSesion() {
		return "pagina-de-inicio"; // Nombre del archivo HTML
	}

	@GetMapping("/procesarInicioSesion")
	public String LoginUsuario(Model model,HttpSession session) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		return "procesarInicioSesion"; // Nombre del archivo HTML
	}

	@PostMapping("/procesarInicioSesion")
	public String PostLoginUsuario(@ModelAttribute Usuario usuario, Model model,HttpSession session,@RequestParam(value = "UserId", required = false) String UserId) {
		model.addAttribute("usuario", usuario);
		if (UserId==null || UserId.isEmpty()) {
	        model.addAttribute("message", "Introduzca un usuario");
	        return "procesarInicioSesion";
		}else if(usuario.getContraseña()==null || usuario.getContraseña().isEmpty()) {
			  model.addAttribute("message", "Introduzca la contraseña");
		        return "procesarInicioSesion";
		}else {
			int idUsuario=0;
			if(UserId.matches("-?\\d+")) {
			idUsuario=Integer.valueOf(UserId);
			if(idUsuario<0) {
				model.addAttribute("message", "Que el id sea postivo");
		        return "procesarInicioSesion";
			}
			}else {
				model.addAttribute("message", "Introduzca un numero positivo en el id de usuario");
		        return "procesarInicioSesion";
			}
		Optional<Usuario> u = usuarioDAO.findById(idUsuario);

		if (u.isPresent()) {
			if ((u.get().getContraseña()).equals(usuario.getContraseña())) {
				session.setAttribute("usuario", (Usuario) u.get());
				return "PanelUsuario";
			} else {
				model.addAttribute("message", "La contraseña no es la misma"); // Inicializa el mensaje como vacío
				return "procesarInicioSesion"; // Nombre del archivo HTML

			}
		} else {
			model.addAttribute("message", "No existe ningun Usuario con ese id"); // Inicializa el mensaje como vacío
			return "procesarInicioSesion"; // Nombre del archivo HTML

		}

	}
	}
	
	@GetMapping("/PanelAdministrador")
	public String mostrarFormularioAdministrador() {
		return "PanelAdministrador"; // Nombre del archivo HTML
	}

	@GetMapping("/Bibliotecario")
	public String mostrarFormularioABibliotecario() {
		return "Bibliotecario"; // Nombre del archivo HTML
	}
	@GetMapping("/PanelUsuario")
	public String mostrarFormularioAUsuario(Model model,HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		model.addAttribute("usuario", usuario);
		return "PanelUsuario"; // Nombre del archivo HTML
	}

}
