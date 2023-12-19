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

@RunWith(MockitoJUnitRunner.class)
public class GestorTitulosTest {
	@Mock
	private EjemplarDAO ejemplarDAO;
	@Mock
	private TituloDAO tituloDAO;
	@Mock
	private AutorDAO autorDAO;

	@Mock
	private PrestamoDAO prestamoDAO;

	@Mock
	private ReservaDAO reservaDAO;

	@Mock
	private TituloAutorDAO tituloAutorDAO;
	private Model model;

	@InjectMocks
	private GestorTitulos gestorTitulos;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this); // Inicializar los mocks antes de cada prueba
		model = mock(Model.class);

	}

	@Test
	public void altaEjemplar() {
		// Configurar comportamiento del mock de TituloDAO
		Long tituloIsbnNoExistente = 978039832L; // ISBN de ejemplo que no existe
		when(tituloDAO.getById(tituloIsbnNoExistente)).thenReturn(null);

		// Llamar al método bajo prueba
		String resultadoNoExistente = gestorTitulos.altaEjemplar(null, mock(Model.class), tituloIsbnNoExistente);

		// Verificar que se recibe el mensaje correcto y que no se llamó al save de
		// ejemplarDAO
		verify(ejemplarDAO, never()).save(any(Ejemplar.class));
		assertEquals("AñadirEjemplar", resultadoNoExistente);

		Long tituloIsbnExistente = 9780312983286L; // ISBN de ejemplo que existe
		Titulo tituloExistente = new Titulo(); // Ajusta esto según tu implementación real
		when(tituloDAO.getById(tituloIsbnExistente)).thenReturn(tituloExistente);

		// Llamar al método bajo prueba
		String resultadoExistente = gestorTitulos.altaEjemplar(new Titulo(), mock(Model.class), tituloIsbnExistente);

		// Verificar que se recibe el mensaje correcto y que se llamó al save de
		// ejemplarDAO
		verify(ejemplarDAO).save(any(Ejemplar.class));
		assertEquals("AñadirEjemplar", resultadoExistente);

		// Configurar comportamiento del mock de TituloDAO para un título inexistente

	}

	@Test
	public void actualizarTituloPost() {
		Long isbnExistente = 9780312983286L;
		Long isbnNoExistente = 1234567890L; // ISBN que no existe en la base de datos

		Titulo tituloExistente = new Titulo(); // Objeto Titulo para simular uno existente en la base de datos

		// Simula el comportamiento del tituloDAO para el ISBN existente
		when(tituloDAO.findById(isbnExistente)).thenReturn(Optional.of(tituloExistente));

		// Simula el comportamiento del tituloDAO para el ISBN que no existe
		when(tituloDAO.findById(isbnNoExistente)).thenReturn(Optional.empty());

		// Prueba para ISBN existente
		String resultadoExistente = gestorTitulos.actualizarTituloPost(String.valueOf(isbnExistente), new Titulo(),
				model);
		assertEquals("actualizar-titulo", resultadoExistente);

		// Prueba para ISBN que no existe
		String resultadoNoExistente = gestorTitulos.actualizarTituloPost(String.valueOf(isbnNoExistente), new Titulo(),
				model);
		assertEquals("ActualizarTitulo", resultadoNoExistente);
	}

	@Test
	public void testActualizarTituloValoresPost() {
		Long isbnExistente = 9780312983286L;
		// Configurar un objeto Titulo para simular un título existente
		Titulo tituloExistente = new Titulo();
		tituloExistente.setIsbn(isbnExistente);
		tituloExistente.setNombre("Nombre Original");
		tituloExistente.setNumReserva(5);

		// Simular comportamiento del tituloDAO
		when(tituloDAO.getById(isbnExistente)).thenReturn(tituloExistente);

		// Simular un autor existente
		Autor autorExistente = new Autor();
		autorExistente.setNombre("NombreAutor");
		autorExistente.setApellidos("ApellidoAutor");

		// Simular comportamiento del autorDAO
		when(autorDAO.findByNombreApellidos("NombreAutor", "ApellidoAutor")).thenReturn(autorExistente);

		// Configurar un objeto Titulo con cambios
		Titulo tituloModificado = new Titulo();
		tituloModificado.setIsbn(isbnExistente);
		tituloModificado.setNombre("Nuevo Nombre");
		tituloModificado.setNumReserva(10);

		// Configurar un TituloAutor para simular un autor asignado al título
		TituloAutor tituloAutor = new TituloAutor();
		tituloAutor.setTitulo(tituloModificado);
		tituloAutor.setAutor(autorExistente);

		List<TituloAutor> autores = new ArrayList<>();
		autores.add(tituloAutor);
		tituloModificado.setAutores(autores);
	// Prueba del método con cambios en el título
		String resultado = gestorTitulos.actualizarTituloValoresPost(tituloModificado, model);
		assertEquals("actualizar-titulo", resultado);

		// Verificación para el caso de que el título se haya actualizado exitosamente
		verify(model, times(1)).addAttribute(eq("message"), eq("El titulo se ha actualizado con exito"));

		// Prueba del método sin cambios en el título
		resultado = gestorTitulos.actualizarTituloValoresPost(tituloExistente, model);
		assertEquals("actualizar-titulo", resultado);

		// Verificación para el caso de que no se hagan cambios en el título
		verify(model, times(1)).addAttribute(eq("message"), eq("No has hecho ningun cambio"));
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

	@Test
	public void bajaEjemplar() {
		// Configurar comportamiento simulado para el DAO que se llamará en el método
		// bajo prueba

		// Caso 1: Ejemplar no existe
		Long isbnNoExistente = 97803129832L; // ISBN de ejemplo que no existe
		when(ejemplarDAO.findByIsbn(isbnNoExistente)).thenReturn(Collections.emptyList());

		// Llamar al método bajo prueba
		String resultadoNoExistente = gestorTitulos.bajaEjemplar(new Ejemplar(), mock(Model.class), isbnNoExistente);

		// Verificar que se recibe el mensaje correcto y que no se llamó al delete de
		// ejemplarDAO
		verify(ejemplarDAO, never()).delete(any());
		assertEquals("BorrarEjemplar", resultadoNoExistente);

		// Caso 2: Ejemplar existe
		Long isbnExistente = 9780312983286L; // ISBN de ejemplo que existe
		Ejemplar ejemplarExistente = new Ejemplar();
		List<Ejemplar> ejemplares = Collections.singletonList(ejemplarExistente);
		when(ejemplarDAO.findByIsbn(isbnExistente)).thenReturn(ejemplares);

		// Llamar al método bajo prueba
		String resultadoExistente = gestorTitulos.bajaEjemplar(ejemplarExistente, mock(Model.class), isbnExistente);

		// Verificar que se recibe el mensaje correcto y que se llamó al delete de
		// ejemplarDAO
		verify(ejemplarDAO).delete(ejemplarExistente);
		assertEquals("BorrarEjemplar", resultadoExistente);
	}

}
