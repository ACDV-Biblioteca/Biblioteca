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

@RunWith(MockitoJUnitRunner.class)
public class GestorPrestamosTest {

    @Mock
    private PrestamoDAO prestamoDAO;

    @Mock
    private ReservaDAO reservaDAO;

    @Mock
    private EjemplarDAO ejemplarDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private GestorPrestamos gestorPrestamos;

    private Model model;

    @Before
    public void setUp() {
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


}
