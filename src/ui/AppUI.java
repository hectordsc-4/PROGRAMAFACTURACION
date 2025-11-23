package ui;

import model.*;
import service.Service;
import util.Validations;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AppUI {

    private Service srv;

    public AppUI() {
        srv = new Service();
    }

    private String beautify(String txt) {
        if (txt == null) return "";
        return txt.replace(",", " ").replace(";", " ").trim();
    }

    private void showPretty(String txt) {
        JTextArea area = new JTextArea(beautify(txt).replace("\n", "\n\n"));
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 250));
        JOptionPane.showMessageDialog(null, scroll, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showUI() {
        JFrame frame = new JFrame("Sistema de Gestión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 450);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        frame.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(5, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnClientes = new JButton("Clientes");
        JButton btnArticulos = new JButton("Artículos");
        JButton btnFacturas = new JButton("Facturas");
        JButton btnVerArchivos = new JButton("Ver Archivos");
        JButton btnSalir = new JButton("Salir");

        JButton[] botones = {btnClientes, btnArticulos, btnFacturas, btnVerArchivos, btnSalir};
        for (JButton b : botones) b.setFocusable(false);

        panel.add(btnClientes);
        panel.add(btnArticulos);
        panel.add(btnFacturas);
        panel.add(btnVerArchivos);
        panel.add(btnSalir);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        btnClientes.addActionListener(e -> gestionClientes());
        btnArticulos.addActionListener(e -> gestionArticulos());
        btnFacturas.addActionListener(e -> gestionFacturas());
        btnVerArchivos.addActionListener(e -> verArchivos());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void gestionClientes() {
        String[] opciones = {"Alta", "Listar", "Consultar", "Info"};
        int choice = JOptionPane.showOptionDialog(null, "Seleccione acción Clientes", "Clientes",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (choice == 3) {
            JOptionPane.showMessageDialog(null,
                    "Información Clientes:\n- Alta: crear un nuevo cliente\n- Listar: mostrar todos los clientes\n- Consultar: buscar por DNI",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        switch (choice) {
            case 0:
                String dni = Validations.sanitize(JOptionPane.showInputDialog("DNI (9 caracteres):"));
                String nombre = Validations.sanitize(JOptionPane.showInputDialog("Nombre:"));
                String direccion = Validations.sanitize(JOptionPane.showInputDialog("Dirección:"));
                String poblacion = Validations.sanitize(JOptionPane.showInputDialog("Población:"));
                String cp = Validations.sanitize(JOptionPane.showInputDialog("CP (5 dígitos):"));
                String provincia = Validations.sanitize(JOptionPane.showInputDialog("Provincia:"));
                String telefono = Validations.sanitize(JOptionPane.showInputDialog("Teléfono (9 dígitos):"));

                if (!Validations.validDNI(dni) || !Validations.validPhone(telefono) || !Validations.validCP(cp) ||
                        !Validations.validText(nombre) || !Validations.validText(direccion)) {
                    showPretty("Datos inválidos. Cliente no guardado."); return;
                }
                srv.saveClient(new Client(dni, nombre, direccion, poblacion, cp, provincia, telefono));
                showPretty("Cliente guardado correctamente.");
                break;

            case 1:
                List<Client> clients = srv.getClients();
                StringBuilder sb = new StringBuilder();
                for (Client c : clients) sb.append(c.toCSV().replace(",", " ").replace(";", " ")).append("\n");
                showPretty(sb.length() == 0 ? "No hay clientes" : sb.toString());
                break;

            case 2:
                String dniBuscar = Validations.sanitize(JOptionPane.showInputDialog("DNI a buscar:"));
                Client c = srv.findClient(dniBuscar);
                if (c != null) showPretty("DNI: " + c.dni + "\nNombre: " + c.nombre + "\nDirección: " + c.direccion +
                        "\nPoblación: " + c.poblacion + "\nCP: " + c.cp + "\nProvincia: " + c.provincia + "\nTeléfono: " + c.telefono);
                else showPretty("Cliente no encontrado");
                break;
        }
    }

    private void gestionArticulos() {
        String[] opciones = {"Alta", "Listar", "Consultar", "Info"};
        int choice = JOptionPane.showOptionDialog(null, "Seleccione acción Artículos", "Artículos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        if (choice == 3) {
            JOptionPane.showMessageDialog(null,
                    "Información Artículos:\n- Alta: crear un nuevo artículo\n- Listar: mostrar todos los artículos\n- Consultar: buscar por nombre",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        switch (choice) {
            case 0:
                String nombre = Validations.sanitize(JOptionPane.showInputDialog("Nombre:"));
                double precio = 0;
                try { precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:")); } catch (Exception ex) { showPretty("Precio inválido"); return; }
                if (!Validations.validPrecio(precio) || !Validations.validText(nombre)) { showPretty("Datos inválidos. Artículo no guardado."); return; }
                srv.saveArticle(new Article(nombre, precio));
                showPretty("Artículo guardado.");
                break;

            case 1:
                List<Article> articles = srv.getArticles();
                StringBuilder sb = new StringBuilder();
                for (Article a : articles) sb.append(a.toCSV().replace(",", " ").replace(";", " ")).append("\n");
                showPretty(sb.length() == 0 ? "No hay artículos" : sb.toString());
                break;

            case 2:
                String nombreBuscar = Validations.sanitize(JOptionPane.showInputDialog("Nombre a buscar:"));
                Article a = srv.findArticle(nombreBuscar);
                if (a != null) showPretty("Nombre: " + a.nombre + "\nPrecio: " + a.precio);
                else showPretty("Artículo no encontrado");
                break;
        }
    }

    private void gestionFacturas() {
        String[] opciones = {"Alta", "Consultar", "Info"};
        int choice = JOptionPane.showOptionDialog(null, "Seleccione acción Facturas", "Facturas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (choice == 2) {
            JOptionPane.showMessageDialog(null,
                    "Información Facturas:\n- Alta: crear nueva factura\n- Consultar: buscar factura por ID\n- Incluye líneas de artículos con cantidad y precio",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        switch (choice) {
            case 0:
                String dniF = Validations.sanitize(JOptionPane.showInputDialog("DNI Cliente:"));

                // Fecha de hoy automática
                LocalDate hoy = LocalDate.now();
                String fecha = hoy.toString(); // AAAA-MM-DD

                // Secuencia automática para ID
                int secuencia = srv.getNextFacturaSequence(fecha);
                String idF = fecha.replace("-", "") + "-" + String.format("%03d", secuencia);
                if (!Validations.validDate(fecha) || !Validations.validDNI(dniF)) { showPretty("Datos inválidos. Factura no creada."); return; }

                Client cl = srv.findClient(dniF);
                if (cl == null) { showPretty("Cliente no existe"); return; }

                double iva = 0;
                try { iva = Double.parseDouble(JOptionPane.showInputDialog("IVA (%):")); } catch (Exception ex) { showPretty("IVA inválido"); return; }
                if (!Validations.validIVA(iva)) { showPretty("IVA inválido"); return; }

                Factura fac = new Factura(idF, fecha, dniF, iva);

                for (int i = 0; i < 10; i++) {
                    String artName = Validations.sanitize(JOptionPane.showInputDialog("Nombre artículo (enter para terminar):"));
                    if (artName == null || artName.isEmpty()) break;

                    Article art = srv.findArticle(artName);
                    double precioArt = art != null ? art.precio : 0;

                    int cant = 0;
                    try { cant = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:")); } catch (Exception ex) { showPretty("Cantidad inválida"); continue; }
                    if (!Validations.validCantidad(cant)) { showPretty("Cantidad inválida"); continue; }

                    fac.lineas.add(new LineaFactura(artName, cant, precioArt));
                }

                srv.saveFactura(fac);
                showPretty("Factura guardada. Total: " + fac.total());
                break;

            case 1:
                String id = Validations.sanitize(JOptionPane.showInputDialog("ID Factura:"));
                Factura f = srv.findFactura(id);
                if (f != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("ID: ").append(f.id).append("\n");
                    sb.append("Fecha: ").append(f.fecha).append("\n");
                    sb.append("DNI Cliente: ").append(f.dniCliente).append("\n");
                    sb.append("IVA: ").append(f.iva).append("%\n");
                    sb.append("Líneas:\n");
                    for (LineaFactura lf : f.lineas) {
                        sb.append("  Artículo: ").append(lf.nombreArticulo)
                                .append(", Cantidad: ").append(lf.cantidad)
                                .append(", Precio: ").append(lf.precioUnitario)
                                .append("\n");
                    }
                    sb.append("Total: ").append(f.total());
                    showPretty(sb.toString());
                } else showPretty("Factura no encontrada");
                break;
        }
    }

    private void verArchivos() {
        String[] opciones = {"Clientes", "Artículos", "Facturas", "Info"};
        int choice = JOptionPane.showOptionDialog(null, "Ver archivos", "Archivos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (choice == 3) {
            JOptionPane.showMessageDialog(null,
                    "Información Archivos:\n- Clientes: listado completo de clientes\n- Artículos: listado completo de artículos\n- Facturas: listado completo de facturas con líneas",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        switch (choice) {
            case 0:
                List<Client> clients = srv.getClients();
                StringBuilder sbc = new StringBuilder();
                for (Client c : clients) sbc.append(c.toCSV().replace(",", " ").replace(";", " ")).append("\n");
                showPretty(sbc.length() == 0 ? "No hay clientes" : sbc.toString());
                break;
            case 1:
                List<Article> arts = srv.getArticles();
                StringBuilder sba = new StringBuilder();
                for (Article a : arts) sba.append(a.toCSV().replace(",", " ").replace(";", " ")).append("\n");
                showPretty(sba.length() == 0 ? "No hay artículos" : sba.toString());
                break;
            case 2: // Facturas
                List<Factura> facs = srv.getFacturas();
                if (facs.isEmpty()) {
                    showPretty("No hay facturas");
                    break;
                }

                StringBuilder sbf = new StringBuilder();

                // Encabezados
                sbf.append(String.format("%-12s %-12s %-12s %-6s %-10s", "ID", "FECHA", "DNI CLIENTE", "IVA", "TOTAL")).append("\n");
                sbf.append("--------------------------------------------------------------").append("\n");

                // Recorremos facturas
                for (Factura f : facs) {
                    double total = f.total();
                    sbf.append(String.format("%-12s %-12s %-12s %-6.2f %-10.2f",
                            f.id, f.fecha, f.dniCliente, f.iva, total)).append("\n");

                    // Líneas de la factura
                    for (LineaFactura lf : f.lineas) {
                        double subtotal = lf.cantidad * lf.precioUnitario;
                        sbf.append(String.format("    %-12s Cant: %-3d Precio: %-6.2f Subtotal: %-6.2f",
                                lf.nombreArticulo, lf.cantidad, lf.precioUnitario, subtotal)).append("\n");
                    }

                    sbf.append("\n"); // Separación entre facturas
                }

                showPretty(sbf.toString());
                break;


        }
    }
}

