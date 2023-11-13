package es.uclm.Biblioteca.domain.controllers;

import es.uclm.Biblioteca.persistencia.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.uclm.Biblioteca.domain.entities.*;

@Controller
public class GestorPenalizaciones {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);

	/**
	 * 
	 * @param u
	 */
	public int aplicarPenalizacion(Usuario u, UsuarioDAO usuarioDAO) {

		int resultado = 0;
		resultado = usuarioDAO.aplicarPenalizacion(u.getId(), u.getFechaFinPenalizacion());

		return resultado;
	}

	/**
	 * 
	 * @param u
	 */
	public void comprobarPenalizacion(Usuario u) {
		// TODO - implement GestorPenalizaciones.comprobarPenalizaci�n
		throw new UnsupportedOperationException();
	}

}