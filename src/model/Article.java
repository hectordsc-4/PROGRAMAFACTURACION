package model;

import util.Validations;

public class Article {
    public String nombre;
    public double precio;

    public Article(String nombre,double precio){
        this.nombre = Validations.sanitize(nombre);
        this.precio = precio;
    }

    public String toCSV(){
        return nombre + ";" + precio;
    }

    public static Article fromCSV(String line){
        String[] p=line.split(";");
        return new Article(p[0],Double.parseDouble(p[1]));
    }
}
