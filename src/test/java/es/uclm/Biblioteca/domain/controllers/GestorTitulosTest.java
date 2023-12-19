package es.uclm.Biblioteca.domain.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import es.uclm.Biblioteca.domain.entities.Autor;
import es.uclm.Biblioteca.domain.entities.Ejemplar;
import es.uclm.Biblioteca.domain.entities.Titulo;
import es.uclm.Biblioteca.domain.entities.TituloAutor;
import es.uclm.Biblioteca.persistencia.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GestorTitulosTest {
	@Mock
    private EjemplarDAO ejemplarDAO;
	@Mock
    private TituloDAO tituloDAO ;
	@Mock
    private AutorDAO autorDAO ;

    @Mock
    private PrestamoDAO prestamoDAO;

    @Mock
    private ReservaDAO reservaDAO;

    @Mock
    private TituloAutorDAO tituloAutorDAO;

    @InjectMocks
    private GestorTitulos gestorTitulos;
        
    private Model model;

	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this); // Inicializar los mocks antes de cada prueba
        model = mock(Model.class);

    }
    
	@Test
    public void showAltaTitulo() {
    }

    @Test
    public void altaTitulo() {
    }

    @Test
    public void actualizarTitulo() {
    }

    @Test
    public void actualizarTituloPost() {
    }

    @Test
    public void actualizarTituloValores() {
    }

    @Test
    public void actualizarTituloValoresPost() {
    }

    @Test
    public void borrarTitulo() {
    }

    @Test
    public void testBorrarTitulo() {
    }

    @Test
    public void showAñadirEjemplarPage() {
    }

    @Test
    public void altaEjemplar() {
    	 // Configurar comportamiento del mock de TituloDAO
        Long tituloIsbnExistente = 9780312983286L; // ISBN de ejemplo que existe
        Titulo tituloExistente = new Titulo(); // Ajusta esto según tu implementación real
        when(tituloDAO.getById(tituloIsbnExistente)).thenReturn(tituloExistente);

        // Llamar al método bajo prueba
        String resultadoExistente = gestorTitulos.altaEjemplar(new Titulo(), mock(Model.class), tituloIsbnExistente);

        // Verificar que se recibe el mensaje correcto y que se llamó al save de ejemplarDAO
        verify(ejemplarDAO).save(any(Ejemplar.class));
        assertEquals("AñadirEjemplar", resultadoExistente);

        // Configurar comportamiento del mock de TituloDAO para un título inexistente
        Long tituloIsbnNoExistente = 97803129832L; // ISBN de ejemplo que no existe
        when(tituloDAO.getById(tituloIsbnNoExistente)).thenReturn(null);

        // Llamar al método bajo prueba
        String resultadoNoExistente = gestorTitulos.altaEjemplar(new Titulo(), mock(Model.class), tituloIsbnNoExistente);

        // Verificar que se recibe el mensaje correcto y que no se llamó al save de ejemplarDAO
        verify(ejemplarDAO, never()).save(any(Ejemplar.class));
        assertEquals("AñadirEjemplar", resultadoNoExistente);
    }

    @Test
    public void showBorrarEjemplarPage() {
    }

    @Test
    public void bajaEjemplar() {
    }
}