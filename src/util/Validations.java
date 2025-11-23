package util;

public class Validations {

    // Limpia eliminando ; y saltos de línea
    public static String sanitize(String input) {
        if (input == null) return "";
        return input.replaceAll("[;\n\r]", " ").trim();
    }

    // DNI válido: 8 números + 1 letra
    public static boolean validDNI(String dni) {
        if (dni == null) return false;
        return dni.matches("^\\d{8}[A-Za-z]$");
    }

    // Teléfono 9 dígitos
    public static boolean validPhone(String tel) {
        if (tel == null) return false;
        return tel.matches("\\d{9}");
    }

    // Código postal 5 dígitos
    public static boolean validCP(String cp) {
        if (cp == null) return false;
        return cp.matches("\\d{5}");
    }

    // Precio ≥ 0
    public static boolean validPrecio(double p) {
        return p >= 0;
    }

    // Cantidad 1–9999
    public static boolean validCantidad(int c) {
        return c > 0 && c <= 9999;
    }

    // IVA 0–100%
    public static boolean validIVA(double iva) {
        return iva >= 0 && iva <= 100;
    }

    // Solo letras, espacios y acentos
    public static boolean validText(String s) {
        if (s == null) return false;
        return s.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$");
    }

    // Fecha formato AAAA-MM-DD
    public static boolean validDate(String date) {
        if (date == null) return false;
        return date.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }
}



