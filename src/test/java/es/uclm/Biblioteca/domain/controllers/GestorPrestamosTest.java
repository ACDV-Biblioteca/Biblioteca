package es.uclm.Biblioteca.domain.controllers;

import org.junit.Test;

import static org.junit.Assert.*;

public class GestorPrestamosTest {

    @Test
    public void showPrestarEjemplarPageUsuario() {
    }

    @Test
    public void prestarEjemplarUsuario() {
    }

    @Test
    public void showPrestarEjemplarPage() {
    }

    @Test
    public void prestarEjemplar() {
    }

    @Test
    public void mostrarFormularioDevolucionUsuario() {
    }


    public class GestorDevolucion {

        private PrestamoDAO prestamoDAO;
        private UsuarioDAO usuarioDAO;
        private GestorPenalizacion gestorPenalizacion;

        // Constructor que recibe las instancias necesarias (prestamoDAO, usuarioDAO, gestorPenalizacion)
        public GestorDevolucion(PrestamoDAO prestamoDAO, UsuarioDAO usuarioDAO, GestorPenalizacion gestorPenalizacion) {
            this.prestamoDAO = prestamoDAO;
            this.usuarioDAO = usuarioDAO;
            this.gestorPenalizacion = gestorPenalizacion;
        }

        public void realizarDevolucion(Prestamo prestamo, Model model) {
            LocalDate fechaHoy = LocalDate.now();
            Date fechaFinPrestamo = prestamo.getFechaFin();

            if (fechaFinPrestamo.before(Date.from(fechaHoy.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                LocalDate fechaFutura = fechaHoy.plusWeeks(2);
                Date fechaFuturaPenalizacion = Date.from(fechaFutura.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Usuario usuario = prestamo.getUsuario();

                usuario.setFechaFinPenalizacion(fechaFuturaPenalizacion);

                if (gestorPenalizacion.aplicarPenalizacion(usuario, usuarioDAO) == 1) {
                    prestamoDAO.delete(prestamo);
                    log.info("Delete: " + prestamo);

                    model.addAttribute("message", "Devolución realizada con éxito con Penalización hasta " + fechaFutura.toString());
                } else {
                    model.addAttribute("message", "Ha ocurrido un problema al aplicar la penalización");
                }
            } else {
                prestamoDAO.delete(prestamo);
                log.info("Delete: " + prestamo);
                model.addAttribute("message", "Devolución realizada con éxito");
            }
        }
    }


    @Test

    public String realizarDevolucionUsuario(
            @RequestParam(value = "prestamoEjemplarId", required = false) Integer prestamoEjemplarId,
            Model model, HttpSession session, PrestamoDAO prestamoDAO, GestorDevolucion gestorDevolucion) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("message", "No existe dicho usuario");
            return "DevolucionUsuario";
        }

        List<Prestamo> prestamos = prestamoDAO.findByUsuarioId(usuario.getId());
        model.addAttribute("prestamos", prestamos);

        int idEjemplarSeleccionado = prestamoEjemplarId;
        Prestamo prestamo = prestamoDAO.findByEjemplarId(idEjemplarSeleccionado);

        if (prestamo == null) {
            model.addAttribute("message",
                    "El prestamo con el ID del ejemplar : " + idEjemplarSeleccionado + " no existe");
            return "DevolucionUsuario";
        } else {
            gestorDevolucion.realizarDevolucion(prestamo, model);
        }

        return "DevolucionUsuario";
    }


    @Test
    public void mostrarFormularioDevolucionBibliotecario() {
    }

    @Test
    public void realizarDevolucionBibliotecario() {
    }

    @Test
    public void mostrarListaYFormulario() {
    }

    @Test
    public void procesarEjemplar() {
    }

    @Test
    public void mostrarListaYFormularioUsuario() {
    }

    @Test
    public void procesarEjemplarUsuario() {
    }
}