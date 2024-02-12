package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.errors.ValidationError;
import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.notification.EnvioNotificacionMail;
import ar.edu.utn.frba.dds.notification.EnvioNotificacionWpp;
import ar.edu.utn.frba.dds.repos.RepoEntidades;
import ar.edu.utn.frba.dds.repos.RepoUsuario;
import ar.edu.utn.frba.dds.validator.PasswordValidator;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.swing.text.View;
import org.hibernate.exception.ConstraintViolationException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsuariosController extends GeneralController {
    RepoUsuario repoUsuario = RepoUsuario.getSingletonInstance();
    PasswordValidator passwordValidator = new PasswordValidator();

    private void getErrors(String error) {
        if (error != null) {
            List<String> errors = new ArrayList<>(Arrays.stream(error.split("-")).map((str) -> str.contains("caracteres especiales") ? str + "-" : str).toList());
            errors.remove(0);
            modelo.put("errors", errors);
        }
    }

    public ModelAndView mostrarUsuario(Request request, Response response) {
        if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
        Long id = Long.parseLong(request.params("id"));
        if (!checkUserIsAdmin() && !checkLoggedUserHasId(id)) {
            response.redirect("/not-found");
            return new ModelAndView(null, "login.html.hbs");
        }
        String notificacionEdicion = request.queryParams("edited");
        if (notificacionEdicion != null) {
            addNotification("Se ha editado el usuario", "Se edito el usuario " + notificacionEdicion);
        }
        String errorContrasenia = request.queryParams("errorContrasenia");
        getErrors(errorContrasenia);
        Usuario usuario = repoUsuario.get(id);
        if (usuario == null) {
            modelo.put("entity", "usuario");
            return new ModelAndView(modelo, "no-entity.html.hbs");
        }
        modelo.put("user", usuario);
        String emailExiste = request.queryParams("emailExiste");
        modelo.put("emailExiste", emailExiste);
        return new ModelAndView(modelo, "usuario.html.hbs");
    }

    public ModelAndView notificacionesUsuario(Request request, Response response) {
        if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
        Long id = Long.parseLong(request.params("id"));
        if (!checkUserIsAdmin() && !checkLoggedUserHasId(id)) {
            response.redirect("/not-found");
            return new ModelAndView(null, "login.html.hbs");
        }
        String errorRango = request.queryParams("errorRango");
        if (errorRango != null) {
            addNotification("Rango invalido", "La fecha de inicio es posterior a la final");
        }
        Usuario usuario = repoUsuario.get(id);
        if (usuario == null) {
            modelo.put("entity", "usuario");
            return new ModelAndView(modelo, "no-entity.html.hbs");
        }
        modelo.put("user", usuario);
        return new ModelAndView(modelo, "usuario-notificaciones.html.hbs");
    }

    public View editarUsuario(Request request, Response response) {
        if (checkUserIsLogged(request, response)) return null;
        Long userId = Long.parseLong(request.params("id"));
        if (!checkUserIsAdmin() && !checkLoggedUserHasId(userId)) return null;
        Usuario usuario = repoUsuario.get(userId);
        if (usuario != null) {
            String nombre = request.queryParams("nombre");
            String apellido = request.queryParams("apellido");
            String email = request.queryParams("email");
            String telefono = request.queryParams("telefono");
            String tipoNotificacion = request.queryParams("tipoNotificacion");
            String contrasenia = request.queryParams("contrasenia");
            usuario.setApellido(apellido != null && !apellido.trim().isEmpty() ? apellido.trim() : usuario.getApellido());
            usuario.setTelefono(telefono != null && !telefono.trim().isEmpty() ? telefono.trim() : usuario.getTelefono());
            usuario.setNombre(nombre != null && !nombre.trim().isEmpty() ? nombre.trim() : usuario.getNombre());
            usuario.setEmail(email != null && !email.trim().isEmpty() ? email.trim() : usuario.getEmail());
            if (tipoNotificacion != null) {
                switch (tipoNotificacion) {
                    case "Mail":
                        usuario.setFormaEnvioNotificacion(new EnvioNotificacionMail());
                        break;
                    case "Whatsapp":
                        usuario.setFormaEnvioNotificacion(new EnvioNotificacionWpp());
                        break;
                    default:
                        response.status(400);
                        return null;
                }
            }
            if (contrasenia != null) {
                contrasenia = contrasenia.trim();
                try {
                    passwordValidator.Validate(contrasenia);
                } catch (ValidationError e) {
                    try {
                        response.redirect("/usuarios/" + usuario.getId() + "?errorContrasenia="+ URLEncoder.encode(e.getMessage(), "UTF-8"), 303);
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }
                    return null;
                }
                usuario.setContrasenia(contrasenia);
            }
            try {
                repoUsuario.add(usuario);
            } catch (ConstraintViolationException e) {
                response.redirect("/usuarios/" + usuario.getId() + "?emailExiste=1", 303);
                return null;
            }
            try {
                response.redirect("/usuarios/" + usuario.getId() + "?edited="+ URLEncoder.encode(usuario.getNombre(), "UTF-8"), 303);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        response.status(404);
        return null;
    };

    public ModelAndView verCrearUsuario(Request request, Response response) {
        if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
        if (!checkUserIsAdmin()) {
            response.redirect("/not-found");
            return new ModelAndView(null, "login.html.hbs");
        }
        String error = request.queryParams("error");
        getErrors(error);
        String emailExiste = request.queryParams("emailExiste");
        modelo.put("emailExiste", emailExiste);
        modelo.put("nombre", request.queryParams("nombre"));
        modelo.put("apellido", request.queryParams("apellido"));
        modelo.put("email", request.queryParams("email"));
        modelo.put("telefono", request.queryParams("telefono"));
        modelo.put("contrasenia", request.queryParams("contrasenia"));
        return new ModelAndView(modelo,"crearUsuario.html.hbs");
    }

    public View eliminar(Request request, Response response) {
        if (checkUserIsLogged(request, response)) return null;
        if (!checkUserIsAdmin()) return null;
        String id = request.params("id");
        repoUsuario.remove(Long.parseLong(id));
        response.redirect("/usuarios",303);
        return null;
    }

    public View crear(Request request, Response response) {
        if (checkUserIsLogged(request, response)) return null;
        if (!checkUserIsAdmin()) return null;
        String nombre = request.queryParams("nombre");
        String apellido = request.queryParams("apellido");
        String email = request.queryParams("email");
        String telefono = request.queryParams("telefono");
        String contrasenia = request.queryParams("contrasenia");
        if (nombre == null || apellido == null || email == null || telefono == null || contrasenia == null) {
            response.status(400);
            return null;
        }
        nombre = nombre.trim();
        apellido = apellido.trim();
        email = email.trim();
        telefono = telefono.trim();
        contrasenia = contrasenia.trim();
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || contrasenia.isEmpty()) {
            response.status(400);
            return null;
        }
        try{
            passwordValidator.Validate(contrasenia);
        } catch (ValidationError e) {
            try {
                response.redirect("/usuarios/crear?error="+URLEncoder.encode(e.getMessage(), "UTF-8")+"&nombre="+URLEncoder.encode(nombre, "UTF-8")
                    +"&apellido="+URLEncoder.encode(apellido, "UTF-8")
                    +"&contrasenia="+URLEncoder.encode(contrasenia, "UTF-8")
                    +"&telefono="+URLEncoder.encode(telefono, "UTF-8")
                    +"&email="+URLEncoder.encode(email, "UTF-8"),303);
                return null;
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        }
        String admin = request.queryParams("admin");
        Boolean isAdmin = admin != null && Boolean.getBoolean(admin);
        Usuario usuario = new Usuario(nombre,apellido,contrasenia,telefono,email, isAdmin);
        try {
            repoUsuario.add(usuario);
        } catch (PersistenceException e) {
            try {
                response.redirect("/usuarios/crear?emailExiste=1&nombre="+URLEncoder.encode(nombre, "UTF-8")
                        +"&apellido="+URLEncoder.encode(apellido, "UTF-8")
                        +"&contrasenia="+URLEncoder.encode(contrasenia, "UTF-8")
                        +"&telefono="+URLEncoder.encode(telefono, "UTF-8")
                        +"&email="+URLEncoder.encode(email, "UTF-8"), 303);
            } catch (UnsupportedEncodingException ex) {
                return null;
            }
            return null;
        }
        response.redirect("/usuarios",303);
        return null;
    }
    public ModelAndView mostrarUsuarios(Request request, Response response) {
        if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
        if (!checkUserIsAdmin()) {
            response.redirect("/not-found");
            return new ModelAndView(null, "login.html.hbs");
        }
        String nombreUsuario = request.queryParams("nombreUsuario");
        List<Usuario> usuarios = repoUsuario.get(nombreUsuario);
        modelo.put("nombreUsuario", nombreUsuario == null ? "" : nombreUsuario);
        modelo.put("usuarios",usuarios);
        return new ModelAndView(modelo,"verUsuarios.html.hbs");
    }
}
