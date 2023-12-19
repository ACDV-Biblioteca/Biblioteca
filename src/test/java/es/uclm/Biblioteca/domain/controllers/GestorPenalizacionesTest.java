package es.uclm.Biblioteca.domain.controllers;

import es.uclm.Biblioteca.persistencia.UsuarioDAO;
import es.uclm.Biblioteca.domain.entities.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.Date;

@Controller
public class GestorPenalizaciones {

    private static final Logger log = LoggerFactory.getLogger(GestorPenalizaciones.class);

    private UsuarioDAO usuarioDAO;

    // Constructor que recibe la instancia de UsuarioDAO
    @Autowired
    public GestorPenalizaciones(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Método para aplicar penalización
    public int aplicarPenalizacion(Usuario usuario) {
        int resultado = usuarioDAO.aplicarPenalizacion(usuario.getId(), usuario.getFechaFinPenalizacion());
        return resultado;
    }

    // Método para comprobar penalización
    public boolean comprobarPenalizacion(Usuario usuario, Date fechaHoy) {
        boolean resultado = true;
        Date fechaPenalizacion = usuarioDAO.comprobarPenalizacion(usuario.getId());

        if (fechaPenalizacion == null || fechaPenalizacion.before(fechaHoy)) {
            resultado = false;
        }

        return resultado;
    }
}
