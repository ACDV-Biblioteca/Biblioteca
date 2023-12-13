package es.uclm.Biblioteca.domain.controllers;

import es.uclm.Biblioteca.persistencia.*;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import es.uclm.Biblioteca.domain.entities.*;
import es.uclm.Biblioteca.domain.entities.TituloAutor.TituloAutorId;

@Controller
public class GestorTitulos {
	private static final Logger log = LoggerFactory.getLogger(GestorTitulos.class);

	@Autowired
	private EjemplarDAO ejemplarDAO;
	@Autowired
	private TituloDAO tituloDAO;
	@Autowired
	private TituloAutorDAO tituloAutorDAO;

	@Autowired
	private PrestamoDAO prestamoDAO;
	@Autowired
	private ReservaDAO reservaDAO;
	@Autowired
	private AutorDAO autorDAO;
	@Autowired
	private EntityManager entityManager;

	/**
	 * @param titulo
	 * @param isbn
	 * @param autores
	 */
	@GetMapping("/dar-alta-titulo")
	public String showAltaTitulo(Model model) {
		model.addAttribute("Titulo", new Titulo());
		model.addAttribute("tituloTitulo", new String());
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío
		return "dar-alta-titulo";
	}

	@PostMapping("/dar-alta-titulo")
	public String altaTitulo(@ModelAttribute Titulo titulo, Model model) {
		model.addAttribute("Titulo", titulo);
		model.addAttribute("tituloTitulo", new String(titulo.getNombre()));
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío
		Titulo tituloNuevo = new Titulo();

		if (titulo.getIsbn() == null) {
			model.addAttribute("message", "Añade un ISBN");
			return "dar-alta-titulo";

		} else {
			if (titulo.getNombre() == null || titulo.getNombre().equals("")) {
				model.addAttribute("message", "Añade un Nombre");
				return "dar-alta-titulo";
			} else {
				Long isbn = titulo.getIsbn();
				Titulo t = tituloDAO.findById(isbn).orElse(null);
				if (t != null) {
					model.addAttribute("message", "El titulo con ISBN " + isbn + " ya existe");
					entityManager.detach(t);

					return "dar-alta-titulo";
				} else {

					if (titulo.getAutores().isEmpty() || titulo.getAutores()==null) {
						model.addAttribute("message", "Tienes que añadir al menos 1 autor");

						return "dar-alta-titulo";

					} else {

						List<Autor> autoresExistentes = new ArrayList<>();
						for (TituloAutor tituloAutor : titulo.getAutores()) {
							String nombreAutor = tituloAutor.getAutor().getNombre();
							String apellidoAutor = tituloAutor.getAutor().getApellidos();
							Autor autorExistente = autorDAO.findByNombreApellidos(nombreAutor, apellidoAutor);

							if (autorExistente == null) {
								// El autor no existe
								model.addAttribute("message", "El autor con nombre " + nombreAutor + " y apellido "
										+ apellidoAutor + " no existe ");
								return "dar-alta-titulo";
							} else {
								// El autor existe, añadirlo a la lista de autores existentes
								autoresExistentes.add(autorExistente);
							}
						}
						tituloNuevo.setIsbn(isbn);
						tituloNuevo.setNombre(titulo.getNombre());
						tituloNuevo.setNumReserva(0);
						tituloNuevo.setAutores(null);
						tituloNuevo.setEjemplares(null);
						tituloNuevo.setPrestamos(null);
						// ... (configurar otros atributos del título si es necesario)

						// Guardar el nuevo título
						Titulo tituloGuardado = tituloDAO.save(tituloNuevo);
						log.info("Saved :" + tituloGuardado);

						// Asociar autores existentes con el título guardado
						for (Autor autorExistente : autoresExistentes) {
							TituloAutor tituloAutor = new TituloAutor();
							tituloAutor.setAutor(autorExistente);
							tituloAutor.setTitulo(tituloGuardado);
							tituloAutorDAO.save(tituloAutor);
							log.info("Titulo Autor Saved:" + tituloAutor);
						}

						model.addAttribute("message", "Se ha añadido el titulo correctamente");

					}
				}
			}
		}
		return "dar-alta-titulo";
	}

	/**
	 * @param t
	 */

	@GetMapping("/ActualizarTitulo")
	public String actualizarTitulo(Model model) {
		model.addAttribute("Titulos", tituloDAO.findAll());
		model.addAttribute("Titulo", new Titulo());
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		return "ActualizarTitulo";
	}

	@PostMapping("/ActualizarTitulo")
	public String actualizarTituloPost(@RequestParam(value = "tituloIsbn", required = false) String tituloIsbn,
			@ModelAttribute Titulo titulo, Model model) {
		model.addAttribute("Titulos", tituloDAO.findAll());
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		Long idTituloSeleccionado = Long.parseLong(tituloIsbn);

		Titulo t = tituloDAO.findById(idTituloSeleccionado).orElse(null);
		if (t == null) {
			model.addAttribute("Titulos", tituloDAO.findAll());
			model.addAttribute("message", "El titulo con ISBN " + idTituloSeleccionado + " no existe");
			return "ActualizarTitulo";
		} else {

			model.addAttribute("Titulos", tituloDAO.findAll());

			model.addAttribute("Titulo", t);

			return "actualizar-titulo";
		}
		// return "ActualizarTitulo";
	}

	@GetMapping("/actualizar-titulo")
	public String actualizarTituloValores(Model model) {
		model.addAttribute("Titulo", new Titulo());
		model.addAttribute("tituloTitulo", new String());
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		return "actualizar-titulo";
	}

	@PostMapping("/actualizar-titulo")
	public String actualizarTituloValoresPost(@ModelAttribute Titulo titulo, Model model) {
		model.addAttribute("Titulo", titulo);
		model.addAttribute("tituloTitulo", new String(titulo.getNombre()));
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		Long isbn = titulo.getIsbn();
		String nombre = titulo.getNombre();
		int numReserva = titulo.getNumReserva();
		Titulo t = tituloDAO.getById(isbn);
		List<TituloAutor> autores = new ArrayList<TituloAutor>();
		if (t.equals(titulo)) {
			model.addAttribute("message", "No has hecho ningun cambio");
			return "actualizar-titulo";
		} else {

			if (titulo.getAutores()==null || titulo.getAutores().isEmpty()) {
				model.addAttribute("message", "Tienes que añadir al menos 1 autor");
				return "actualizar-titulo";
			} else {
				t.setNombre(nombre);
				t.setNumReserva(numReserva);

				for (TituloAutor tituloautor : titulo.getAutores()) {
					String nombreAutor = tituloautor.getAutor().getNombre();
					String apellidoAutor = tituloautor.getAutor().getApellidos();

					// Verificar si existe el primer nombre y primer apellido
					Autor autor = autorDAO.findByNombreApellidos(nombreAutor, apellidoAutor);

					if (autor == null) {
						model.addAttribute("message",
								"El autor con nombre " + nombreAutor + " y apellido " + apellidoAutor + " no existe ");

						return "actualizar-titulo";
					} else {
						TituloAutor ta = new TituloAutor();
						ta.setAutor(autor);
						ta.setTitulo(t);
						tituloAutorDAO.save(ta);

						autores.add(ta);

					}
				}
				t.setAutores(autores);
				tituloDAO.save(t);
				log.info("Saved : +" + t);
			}

		}
		return "actualizar-titulo";
	}

	@GetMapping("/DeleteAndUpdate")
	public String borrarTitulo(Model model) {
		model.addAttribute("titulos", tituloDAO.findAll());
		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		return "DeleteAndUpdate";
	}
	// implementar para borrar el libro de la base de datos

	@PostMapping("/DeleteAndUpdate")
	public String borrarTitulo(@RequestParam(value = "tituloIsbn", required = false) Long tituloIsbn,
			@ModelAttribute Titulo titulo, Model model) {

		Long idTituloSeleccionado = tituloIsbn;
		model.addAttribute("titulos", tituloDAO.findAll());

		Titulo t = tituloDAO.findById(idTituloSeleccionado).orElse(null);
		if (t == null) {
			model.addAttribute("titulos", tituloDAO.findAll());
			model.addAttribute("message", "El titulo con ISBN " + idTituloSeleccionado + " no existe");
			return "DeleteAndUpdate";
		} else {
			prestamoDAO.deleteByISBN(idTituloSeleccionado);
			reservaDAO.deleteByISBN(idTituloSeleccionado);

			ejemplarDAO.deleteByISBN(idTituloSeleccionado);
			tituloAutorDAO.deleteByISBN(idTituloSeleccionado);

			prestamoDAO.deleteByISBN(idTituloSeleccionado);

			tituloDAO.deleteById(idTituloSeleccionado);
			model.addAttribute("message", "Se ha borrado el titulo " + t.getNombre() + " con ISBN " + t.getIsbn());

		}
		return "DeleteAndUpdate";
	}

	/**
	 * @param t
	 */
	@GetMapping("/AñadirEjemplar")
	public String showAñadirEjemplarPage(Model model) {
		model.addAttribute("titulos",tituloDAO.findAll());
		model.addAttribute("titulo",new Titulo());

		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		return "AñadirEjemplar";
	}

	@PostMapping("/AñadirEjemplar")
	public String altaEjemplar(@ModelAttribute Titulo titulo, Model model,@RequestParam(value = "tituloIsbn", required = false) Long tituloIsbn) {
		model.addAttribute("titulo", titulo);
		model.addAttribute("titulos",tituloDAO.findAll());

		Titulo t = tituloDAO.getById(tituloIsbn);
		if (t != null) {
			Ejemplar ejemplar = new Ejemplar();
			ejemplar.setTitulo(t);
			ejemplar.setId((int) ejemplarDAO.count() + 1);
			ejemplarDAO.save(ejemplar);
			log.info("Saved: " + ejemplar);
			model.addAttribute("message",
					"Se ha añadido exitosamente el ejemplar con el titulo: " + ejemplar.getTitulo()); // Inicializa el
																										// mensaje como
																										// vací
		} else {

			model.addAttribute("message", "No existe ese Titulo para añadir el ejemplar"); // Inicializa el mensaje como
																							// vací
		}
		return "AñadirEjemplar";
	}

	/**
	 * @param t
	 */
	@GetMapping("/BorrarEjemplar")
	public String showBorrarEjemplarPage(Model model) {
		model.addAttribute("titulos",tituloDAO.findAll());
		model.addAttribute("ejemplar", new Ejemplar()); // Inicializa el mensaje como vacío

		model.addAttribute("message", ""); // Inicializa el mensaje como vacío

		return "BorrarEjemplar";
	}

	@PostMapping("/BorrarEjemplar")
	public String bajaEjemplar(@ModelAttribute Ejemplar ejemplar, Model model,@RequestParam(value = "tituloIsbn", required = false) Long tituloIsbn) {
		model.addAttribute("titulos",tituloDAO.findAll());
		model.addAttribute("ejemplar",ejemplar); // Inicializa el mensaje como vacío

		List<Ejemplar> ejemplarOpt2 = ejemplarDAO.findByIsbn(tituloIsbn);
		

		// Si el Ejemplar existe, eliminarlo
		if (!ejemplarOpt2.isEmpty()) {
			if (ejemplarOpt2.size() == 1) {
				prestamoDAO.deleteByISBN(tituloIsbn);
				reservaDAO.deleteByISBN(tituloIsbn);
				ejemplarDAO.delete(ejemplarOpt2.get(ejemplarOpt2.size() - 1));

				tituloAutorDAO.deleteByISBN(tituloIsbn);

				prestamoDAO.deleteByISBN(tituloIsbn);
				Titulo t = tituloDAO.getById(tituloIsbn);
				tituloDAO.delete(t);
				log.info("Se ha borrado el ejemplar " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getId()
						+ " junto titulo " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getTitulo().getNombre()+" ya que no se dispone de ningun ejemplar");
				model.addAttribute("message",
						"Se ha borrado el ejemplar " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getId()
								+ " junto titulo " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getTitulo().getNombre()+" ya que no se dispone de ningun ejemplar");
			
					
			} else {
				ejemplarDAO.delete(ejemplarOpt2.get(ejemplarOpt2.size() - 1));
				model.addAttribute("message",
						"Se ha borrado el ejemplar " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getId()
								+ " con titulo " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getTitulo().getNombre());

				log.info("Se ha borrado el ejemplar " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getId()
						+ " con titulo " + ejemplarOpt2.get(ejemplarOpt2.size() - 1).getTitulo().getNombre());

			}

		} else {
			// Manejar el caso en el que el Ejemplar no se encuentra

			model.addAttribute("message", "No se encontró el ejemplar con ese ISBN.");
			log.info("Este ejemplar no existe");

		}

		return "BorrarEjemplar";
	}

}