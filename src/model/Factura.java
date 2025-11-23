package model;

import java.util.ArrayList;
import java.util.List;
import util.Validations;

public class Factura {
    public String id;
    public String fecha;
    public String dniCliente;
    public double iva;
    public List<LineaFactura> lineas = new ArrayList<>();

    // Constructor recibe ID y fecha generados automáticamente, además de dniCliente e iva
    public Factura(String id, String fecha, String dniCliente, double iva) {
        this.id = Validations.sanitize(id);
        this.fecha = Validations.sanitize(fecha);
        this.dniCliente = Validations.sanitize(dniCliente);
        this.iva = iva;
    }

    // Calcula la base imponible sumando cantidad * precioUnitario de todas las líneas
    public double base() {
        double sum = 0;
        for (LineaFactura l : lineas) {
            sum += l.cantidad * l.precioUnitario;
        }
        return sum;
    }

    // Calcula el total incluyendo IVA
    public double total() {
        return base() * (1 + iva / 100);
    }

    // Devuelve los datos de la factura en formato CSV
    public String toCSV() {
        return id + ";" + fecha + ";" + dniCliente + ";" + iva;
    }
}


