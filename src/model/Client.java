package model;

import util.Validations;

public class Client {
    public String dni, nombre, direccion, poblacion, cp, provincia, telefono;

    public Client(String dni, String nombre, String direccion, String poblacion,
                  String cp, String provincia, String telefono) {

        this.dni = Validations.sanitize(dni);
        this.nombre = Validations.sanitize(nombre);
        this.direccion = Validations.sanitize(direccion);
        this.poblacion = Validations.sanitize(poblacion);
        this.cp = Validations.sanitize(cp);
        this.provincia = Validations.sanitize(provincia);
        this.telefono = Validations.sanitize(telefono);
    }

    public String toCSV() {
        return String.join(";", dni,nombre,direccion,poblacion,cp,provincia,telefono);
    }

    public static Client fromCSV(String line) {
        String[] p=line.split(";");
        if(p.length!=7) return null;
        return new Client(p[0],p[1],p[2],p[3],p[4],p[5],p[6]);
    }
}


