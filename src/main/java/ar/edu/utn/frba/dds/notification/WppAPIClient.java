package ar.edu.utn.frba.dds.notification;

import com.google.gson.Gson;
import java.net.http.HttpClient;

public class WppAPIClient {

  static final String BASE_URL = "https://apis.whatsappApiEjemplo.com/send/api/";
  HttpClient client = HttpClient.newHttpClient();
  Gson gson = new Gson();


}
