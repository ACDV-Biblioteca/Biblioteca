package es.uclm.Biblioteca.domain.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;
import es.uclm.Biblioteca.domain.entities.Usuario;
import es.uclm.Biblioteca.persistencia.EjemplarDAO;
import es.uclm.Biblioteca.persistencia.PrestamoDAO;
import es.uclm.Biblioteca.persistencia.ReservaDAO;
import es.uclm.Biblioteca.persistencia.UsuarioDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Prestamo;
import es.uclm.Biblioteca.domain.entities.Reserva;
import es.uclm.Biblioteca.domain.entities.Titulo;
import es.uclm.Biblioteca.domain.entities.Usuario;
import es.uclm.Biblioteca.persistencia.AutorDAO;
import es.uclm.Biblioteca.persistencia.EjemplarDAO;
import es.uclm.Biblioteca.persistencia.PrestamoDAO;
import es.uclm.Biblioteca.persistencia.ReservaDAO;
import es.uclm.Biblioteca.persistencia.TituloAutorDAO;
import es.uclm.Biblioteca.persistencia.TituloDAO;
import es.uclm.Biblioteca.persistencia.UsuarioDAO;
import jakarta.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;

@RunWith(MockitoJUnitRunner.class)
public class GestorPrestamosTest {
	@Mock
    private EjemplarDAO ejemplarDAO;
	@Mock
    private TituloDAO tituloDAO ;
	@Mock
    private AutorDAO autorDAO ;
	@Mock
    private UsuarioDAO usuarioDAO;
    @Mock
    private PrestamoDAO prestamoDAO;
  @Mock
    private ReservaDAO reservaDAO;
     @Mock
    private HttpSession session;
    
    private Model model;
  @Mock
    private TituloAutorDAO tituloAutorDAO;

    @InjectMocks
    private GestorPrestamos gestorPrestamos;

    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this); // Inicializar los mocks antes de cada prueba

        model = mock(Model.class);
    }

    
    @Test
    public void testRealizarPrestamo_Success() {
        // Mock data
        Prestamo prestamo = new Prestamo();
        int usuarioId = 1;
        Ejemplar ejemplar = new Ejemplar();

        // Mock behavior
        when(usuarioDAO.getById(usuarioId)).thenReturn(new Usuario());
       // when(ejemplarDAO.findById(anyInt())).thenReturn(Optional.of(ejemplar));
        when(prestamoDAO.findCountPrestamosUsuario(anyInt())).thenReturn(0);
        when(reservaDAO.deleteByEjemplar(anyInt())).thenReturn(0);

        // Perform the test
        gestorPrestamos.RealizarPrestamo(prestamo, usuarioId, model, ejemplar);

        // Verify the results or behavior
        verify(prestamoDAO).save(prestamo);
        verify(model).addAttribute(eq("message"), eq("Préstamo realizado con éxito."));
    }
    @Test
    public void testPrestarEjemplarUsuario() {
        Model model = mock(Model.class);
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);

        String result = gestorPrestamos.prestarEjemplarUsuario(new Prestamo(), 1, model, session);

        assertEquals("PrestarEjemplarUsuario", result);
    }





	@Test
	public void testPrestarEjemplar() {
		// Configurar comportamiento del mock de EjemplarDAO
        when(ejemplarDAO.findByNoPrestados()).thenReturn(Collections.emptyList());

        // Llamar al método bajo prueba
        String resultado = gestorPrestamos.prestarEjemplar(new Prestamo(), "1", 1, mock(Model.class));

        // Verificar el comportamiento esperado
        // Ajusta esto según cómo debería comportarse tu método
        verify(prestamoDAO, never()).save(any(Prestamo.class));
        assertEquals("PrestarEjemplar", resultado);
    
	}
@Test
	public void testRealizarPrestamo() {
		 // Configurar comportamiento del mock de UsuarioDAO
        when(usuarioDAO.getById(anyInt())).thenReturn(new Usuario());

        // Configurar comportamiento del mock de ReservaDAO
        when(reservaDAO.deleteByEjemplar(anyInt())).thenReturn(0);

        // Llamar al método bajo prueba
        gestorPrestamos.RealizarPrestamo(new Prestamo(), 1, mock(Model.class), new Ejemplar());

        // Verificar el comportamiento esperado
        // Ajusta esto según cómo debería comportarse tu método
        verify(prestamoDAO).save(any(Prestamo.class));
	}


	@Test
	public void testProcesarEjemplar() {
		// Configurar comportamiento del mock de EjemplarDAO
        when(ejemplarDAO.findByPrestados()).thenReturn(Collections.emptyList());

        // Llamar al método bajo prueba
        String resultado = gestorPrestamos.procesarEjemplar(new Reserva(), "1", 1, mock(Model.class));

        // Verificar el comportamiento esperado
        // Ajusta esto según cómo debería comportarse tu método
        verify(reservaDAO, never()).save(any(Reserva.class));
        assertEquals("ReservaEjemplar", resultado);
	}

	@Test
	public void testReservarEjemplar() {
		// Configurar comportamiento del mock de UsuarioDAO
        when(usuarioDAO.getById(anyInt())).thenReturn(new Usuario());

        // Llamar al método bajo prueba
        gestorPrestamos.ReservarEjemplar(new Reserva(), 1, new Ejemplar(), mock(Model.class));

        // Verificar el comportamiento esperado
        // Ajusta esto según cómo debería comportarse tu método
        verify(reservaDAO).save(any(Reserva.class));

	}

	@Test
	public void testProcesarEjemplarUsuario() {
	    // Arrange
	    Reserva reserva = new Reserva();
	    String userId = "5";
	    Integer ejemplarId = 1;
	    Usuario usuario = new Usuario();
	    usuario.setNombre("NombreUsuario");

	    // Simular un escenario donde el usuario está presente en la sesión
	    when(session.getAttribute("usuario")).thenReturn(usuario);

	    // Simular una lista de ejemplares prestados
	    List<Ejemplar> listaEjemplares = Arrays.asList(new Ejemplar(), new Ejemplar());
	    when(ejemplarDAO.findByPrestados()).thenReturn(listaEjemplares);

	    // Simular la búsqueda de un ejemplar por su ID
	    Ejemplar ejemplar = new Ejemplar();
	    when(ejemplarDAO.findById(ejemplarId)).thenReturn(Optional.of(ejemplar));

	    // Act
	    String result = gestorPrestamos.procesarEjemplarUsuario(reserva, userId, ejemplarId, model, session);

	    // Assert
	    // Verificar el caso de éxito cuando tanto el usuario como el ejemplar son válidos
	    assertEquals("ReservarEjemplarUsuario", result);
	    verify(model, times(1)).addAttribute(eq("reserva"), eq(reserva));
	    verify(model, times(1)).addAttribute("ejemplares", listaEjemplares);
	    verify(model, times(1)).addAttribute(eq("mensaje"), eq("Ejemplar "+ejemplarId+" seleccionado por el usuario NombreUsuario"));

	    // Verificar el caso cuando el usuario no está presente en la sesión
	    when(session.getAttribute("usuario")).thenReturn(null);
	    result = gestorPrestamos.procesarEjemplarUsuario(reserva, userId, ejemplarId, model, session);
	    assertEquals("ReservarEjemplarUsuario", result);
	    verify(model, times(1)).addAttribute(eq("mensaje"), eq("El usuario no existe."));

	    // Verificar el caso cuando el ejemplar no se encuentra en la base de datos
	    when(session.getAttribute("usuario")).thenReturn(usuario);
	    when(ejemplarDAO.findById(ejemplarId)).thenReturn(Optional.empty());
	    result = gestorPrestamos.procesarEjemplarUsuario(reserva, userId, ejemplarId, model, session);
	    assertEquals("ReservarEjemplarUsuario", result);
	    verify(model, times(1)).addAttribute(eq("mensaje"), eq("El ejemplar con ID "+ejemplarId+" no existe"));

	}



    @Test
    public void realizarDevolucionBibliotecario() {
    			int idEjemplarExistente=2;
    			int idEjemplarNoExistente=1234;
    	        // Simular un prestamo existente
    	        Prestamo prestamoExistente = new Prestamo();
    	        Ejemplar ejemplar = new Ejemplar(); // Simula un Ejemplar
    	        Usuario usuario = new Usuario(); // Simula un Usuario
    	        Titulo titulo = new Titulo(); // Simula un Titulo
    	        prestamoExistente.setEjemplar(ejemplar);
    	        prestamoExistente.setUsuario(usuario);
    	        prestamoExistente.setTitulo(titulo);
    	        prestamoExistente.setFechaInicio(new Date());
    	        prestamoExistente.setFechaFin(new Date());
    	        prestamoExistente.setActivo(true);

    	        // Simular comportamiento del prestamoDAO para un prestamo existente
    	        when(prestamoDAO.findByEjemplarId(idEjemplarExistente)).thenReturn(prestamoExistente);

    	        // Simular comportamiento del prestamoDAO para un prestamo que no existe
    	        when(prestamoDAO.findByEjemplarId(idEjemplarNoExistente)).thenReturn(null); // ID de un préstamo que no existe

    	        // Prueba del método con un prestamo existente
    	        String resultadoExistente = gestorPrestamos.realizarDevolucionBibliotecario(idEjemplarExistente, model);
    	        assertEquals("DevolucionEjemplar", resultadoExistente);

    	        // Prueba del método con un prestamo que no existe
    	        String resultadoNoExistente = gestorPrestamos.realizarDevolucionBibliotecario(idEjemplarNoExistente, model);
    	        assertEquals("DevolucionEjemplar", resultadoNoExistente);
    	    }
    	}


