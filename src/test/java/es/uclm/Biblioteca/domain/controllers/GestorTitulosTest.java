package es.uclm.Biblioteca.domain.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import es.uclm.Biblioteca.domain.entities.Titulo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import es.uclm.Biblioteca.domain.controllers.GestorTitulos;
import es.uclm.Biblioteca.persistencia.EjemplarDAO;
import es.uclm.Biblioteca.persistencia.PrestamoDAO;
import es.uclm.Biblioteca.persistencia.ReservaDAO;
import es.uclm.Biblioteca.persistencia.TituloAutorDAO;
import es.uclm.Biblioteca.persistencia.TituloDAO;

import java.util.Collections;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class GestorTitulosTest {

    @InjectMocks
    private GestorTitulos gestorTitulos;

    @Mock
    private TituloDAO tituloDAO;

    @Mock
    private PrestamoDAO prestamoDAO;

    @Mock
    private ReservaDAO reservaDAO;

    @Mock
    private EjemplarDAO ejemplarDAO;

    @Mock
    private TituloAutorDAO tituloAutorDAO;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        // Setup any necessary mock objects or data before each test
    }

    @Test
    public void testBorrarTitulo() {
        // Mock data
        Long tituloIsbn = 9780312983286L;

        // Mock behavior
        when(tituloDAO.findById(tituloIsbn)).thenReturn(Optional.of(new Titulo()));
        when(tituloDAO.findAll()).thenReturn(Collections.emptyList());

        // Call the method to test
        String result = gestorTitulos.borrarTitulo(tituloIsbn, new Titulo(), model);

        // Assertions
        assertEquals("DeleteAndUpdate", result);
      /*  verify(prestamoDAO).deleteByISBN(tituloIsbn);
        verify(reservaDAO).deleteByISBN(tituloIsbn);
        verify(ejemplarDAO).deleteByISBN(tituloIsbn);
        verify(tituloAutorDAO).deleteByISBN(tituloIsbn);
        verify(prestamoDAO).deleteByISBN(tituloIsbn);*/
        verify(tituloDAO).deleteById(tituloIsbn);

    }
}
