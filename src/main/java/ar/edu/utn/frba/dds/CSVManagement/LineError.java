package ar.edu.utn.frba.dds.CSVManagement;

public class LineError {
  int row;
  String line;
  String message;

  public LineError(int row, String line, String message) {
    this.row = row;
    this.line = line;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getLine() {
    return line;
  }

  public int getRow() {
    return row;
  }
}
