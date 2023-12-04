package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.repos.RepoUsuario;
import javax.swing.text.View;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController extends GeneralController {
  private RepoUsuario repoUsuario = RepoUsuario.getSingletonInstance();

  public ModelAndView login(Request request, Response response) {
    if (request.session().attribute("currentUser") != null) {
      response.redirect("/");
    }
    String email = request.queryParams("email");
    String password = request.queryParams("password");
    String redirectPath = request.queryParams("path");
    if (email != null && password != null) {
      Usuario posibleUsuario = repoUsuario.get(email, password);
      if (posibleUsuario != null) {
        request.session().attribute("currentUser", posibleUsuario.getId());
        response.redirect(redirectPath == null ? "/" : redirectPath);
        return new ModelAndView(null, "incidentes.html.hbs");
      } else {
        modelo.put("incorrectUserText", "El usuario o la contraseña son incorrectas");
      }
    }
    modelo.put("redirectPath", redirectPath == null ? "" : redirectPath);
    return new ModelAndView(modelo, "login.html.hbs");
  }

  public View logout(Request request, Response response) {
    request.session().removeAttribute("currentUser");
    response.redirect("/login");
    return null;
  };
}
