package es.uclm.Biblioteca.domain.controllers;
import es.uclm.Biblioteca.persistencia.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.uclm.Biblioteca.domain.entities.*;

@Controller
public class GestorPenalizaciones {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);
	public int aplicarPenalizacion(Usuario u, UsuarioDAO usuarioDAO) {

		int resultado = 0;
		resultado = usuarioDAO.aplicarPenalizacion(u.getId(), u.getFechaFinPenalizacion());
		return resultado;
	}
	public boolean comprobarPenalizacion(Usuario u, UsuarioDAO usuarioDAO,Date fechaHoy) {
		boolean resultado=true;
		Date fecha_penalizacion= null;
		
		fecha_penalizacion=usuarioDAO.comprobarPenalizacion(u.getId());
		if(fecha_penalizacion == null || fecha_penalizacion.before(fechaHoy)) {

			
			resultado=false;
		}
		return resultado;
	}

}
