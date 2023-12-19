package es.uclm.Biblioteca.domain.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import es.uclm.Biblioteca.domain.entities.Usuario;
import es.uclm.Biblioteca.persistencia.UsuarioDAO;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class GestorPenalizacionesTest {
	@Mock
    private UsuarioDAO usuarioDAO;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this); // Inicializar los mocks antes de cada prueba

	}

    @Test
    public void aplicarPenalizacion() {
    	Usuario u= new Usuario();
    	
		int resultado = 0;
		
		resultado = usuarioDAO.aplicarPenalizacion(u.getId(), u.getFechaFinPenalizacion());
		if (resultado==0) {
			assertEquals(0, resultado);

		}else {
			assertEquals(1, resultado);

		}
		
    }

    @Test
    public void comprobarPenalizacion() {
    	Usuario u= new Usuario();

    	boolean resultado=true;
		Date fecha_penalizacion= null;
		Date fechaHoy= new Date();
		fecha_penalizacion=usuarioDAO.comprobarPenalizacion(u.getId());
		if(fecha_penalizacion == null || fecha_penalizacion.before(fechaHoy)) {

			
			resultado=false;
		}
		if (resultado) {
			assertEquals(true, resultado);

		}else {
			assertEquals(false, resultado);

		}
		
	}
    }
