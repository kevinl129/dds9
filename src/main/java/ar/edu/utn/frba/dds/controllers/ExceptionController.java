package ar.edu.utn.frba.dds.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ExceptionController extends GeneralController {
  public void exceptionHandler(Exception e, Request request, Response response) {
    System.out.println(e.getMessage());
    e.printStackTrace();
    response.redirect("/error?error="+e.getMessage(), 303);
  }

  public ModelAndView internalServerError(Request request, Response response) {
    modelo.put("error", request.queryParams("error"));
    if (!isUserLoggedIn(request)) {
      return new ModelAndView(modelo, "500-sin-sesion.html.hbs");
    }
    return new ModelAndView(modelo, "500.html.hbs");
  }

  public ModelAndView notFound(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
    return new ModelAndView(modelo, "404.html.hbs");
  }
}
