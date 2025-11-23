package model;

import util.Validations;

public class LineaFactura {
    public String nombreArticulo;
    public int cantidad;
    public double precioUnitario;

    public LineaFactura(String nombreArticulo,int cantidad,double precioUnitario){
        this.nombreArticulo = Validations.sanitize(nombreArticulo);
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public String toCSV(String idFactura){
        return idFactura + ";" + cantidad + ";" + nombreArticulo + ";" + precioUnitario;
    }
}

