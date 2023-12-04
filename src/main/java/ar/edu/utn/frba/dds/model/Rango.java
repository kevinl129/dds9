package ar.edu.utn.frba.dds.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;

@Entity
@Table(name = "Rangos")
public class Rango {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Calendar fechaInicio;
  private Calendar fechaFinal;

  public Rango(Calendar fechaInicio, Calendar fechaFinal) {
    this.fechaInicio = fechaInicio;
    this.fechaFinal = fechaFinal;
  }

  public Rango(String fechaInicio, String fechaFinal) {
    this.fechaInicio = parseCalendarFromString(fechaInicio);
    this.fechaFinal = parseCalendarFromString(fechaFinal);
  }

  public Rango() {}

  private Calendar parseCalendarFromString(String date) {
    return DatatypeConverter.parseDateTime(date+":00Z");
  }

  public boolean esRangoValido() {
    return fechaInicio.before(fechaFinal);
  }

  private String parseCalendar(Calendar calendar) {
    Date date = calendar.getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    return dateFormat.format(date);
  }
  public String getFechaInicio() {
    return parseCalendar(fechaInicio);
  }

  public String getFechaFinal() {
    return parseCalendar(fechaFinal);
  }

  public boolean estaEnElRango(Calendar fecha) {
    return fecha.after(fechaInicio) && fecha.before(fechaFinal);
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
