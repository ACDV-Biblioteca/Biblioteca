package es.uclm.Biblioteca.domain.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import es.uclm.Biblioteca.domain.controllers.GestorLogin;
import es.uclm.Biblioteca.domain.entities.Usuario;
import es.uclm.Biblioteca.persistencia.UsuarioDAO;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

public class GestorLoginTest {

    private GestorLogin gestorLogin;
    private UsuarioDAO usuarioDAO;
    private HttpSession session;

    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this); // Inicializar los mocks antes de cada prueba

        gestorLogin = new GestorLogin();
        usuarioDAO = mock(UsuarioDAO.class);
        session = mock(HttpSession.class);

        gestorLogin.usuariorDAO = usuarioDAO;
    }

    @Test
    public void testPostLoginUsuarioWithValidCredentials() {
        // Arrange
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(1);
        mockUsuario.setContraseña("contraseña123");

        when(usuarioDAO.findById(1)).thenReturn(Optional.of(mockUsuario));
        when(session.getAttribute("usuario")).thenReturn(null);

        Model model = mock(Model.class);
        String userId = "1";
        String userPassword = "contraseña1";

        // Act
        String result = gestorLogin.PostLoginUsuario(mockUsuario, model, session, userId);

        // Assert
        assertEquals("PanelUsuario", result);
    }

    @Test
    public void testPostLoginUsuarioWithInvalidCredentials() {
        // Arrange
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(2);
        mockUsuario.setContraseña("contraseña456");

        when(usuarioDAO.findById(2)).thenReturn(Optional.of(mockUsuario));
        when(session.getAttribute("usuario")).thenReturn(null);

        Model model = mock(Model.class);
        String userId = "2";
        String userPassword = "contraseñaIncorrecta"; // Cambia esta contraseña para probar credenciales incorrectas

        // Act
        String result = gestorLogin.PostLoginUsuario(mockUsuario, model, session, userId);

        // Assert
        assertEquals("PanelUsuario", result);
    }

}
