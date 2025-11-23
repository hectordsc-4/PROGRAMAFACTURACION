package main;

import model.*;
import service.Service;
import util.Validations;
import ui.AppUI;

import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        UIManager.put("OptionPane.informationIcon", new ImageIcon(new byte[0]));
        UIManager.put("OptionPane.questionIcon", new ImageIcon(new byte[0]));
        UIManager.put("OptionPane.warningIcon", new ImageIcon(new byte[0]));
        UIManager.put("OptionPane.errorIcon", new ImageIcon(new byte[0]));
        // ------- INTERFAZ GRÁFICA -------
        javax.swing.SwingUtilities.invokeLater(() -> {
            AppUI ui = new AppUI();
            ui.showUI();
        });
        // ------- MENÚ CONSOLA EN HILO SEPARADO -------
        Thread consoleThread = new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            Service srv = new Service();
            int op;

            while (true) {
                System.out.println("\n1.Clientes 2.Articulos 3.Facturas 4.Ver archivos 5.Salir");
                System.out.print("Opción: ");
                if (!sc.hasNextInt()) { sc.nextLine(); continue; }
                op = sc.nextInt(); sc.nextLine();

                switch (op) {
                    // --- CLIENTES ---
                    case 1:
                        System.out.println("1.Alta 2.Listar 3.Consultar");
                        int c = sc.nextInt(); sc.nextLine();
                        if (c == 1) {
                            System.out.print("DNI (9 caracteres): ");
                            String dni = Validations.sanitize(sc.nextLine());
                            System.out.print("Nombre: ");
                            String nom = Validations.sanitize(sc.nextLine());
                            System.out.print("Dirección: ");
                            String dir = Validations.sanitize(sc.nextLine());
                            System.out.print("Población: ");
                            String pob = Validations.sanitize(sc.nextLine());
                            System.out.print("CP (5 dígitos): ");
                            String cp = Validations.sanitize(sc.nextLine());
                            System.out.print("Provincia: ");
                            String prov = Validations.sanitize(sc.nextLine());
                            System.out.print("Teléfono (9 dígitos): ");
                            String tel = Validations.sanitize(sc.nextLine());

                            if (!Validations.validDNI(dni) || !Validations.validPhone(tel) || !Validations.validCP(cp)
                                    || !Validations.validText(nom) || !Validations.validText(dir)) {
                                System.out.println("Datos inválidos. Cliente no guardado.");
                                break;
                            }

                            srv.saveClient(new Client(dni, nom, dir, pob, cp, prov, tel));
                            System.out.println("Cliente guardado.");
                        } else if (c == 2) {
                            for (Client cl : srv.getClients()) System.out.println(cl.toCSV());
                        } else if (c == 3) {
                            System.out.print("DNI: ");
                            String dniC = Validations.sanitize(sc.nextLine());
                            Client cl = srv.findClient(dniC);
                            if (cl != null) {
                                System.out.println("DNI: " + cl.dni);
                                System.out.println("Nombre: " + cl.nombre);
                                System.out.println("Dirección: " + cl.direccion);
                                System.out.println("Población: " + cl.poblacion);
                                System.out.println("CP: " + cl.cp);
                                System.out.println("Provincia: " + cl.provincia);
                                System.out.println("Teléfono: " + cl.telefono);
                            } else System.out.println("Cliente no encontrado.");
                        }
                        break;

                    // --- ARTÍCULOS ---
                    case 2:
                        System.out.println("1.Alta 2.Listar 3.Consultar");
                        int a = sc.nextInt(); sc.nextLine();
                        if (a == 1) {
                            System.out.print("Nombre: ");
                            String nomA = Validations.sanitize(sc.nextLine());
                            System.out.print("Precio: ");
                            double p = sc.nextDouble(); sc.nextLine();
                            if (!Validations.validPrecio(p) || !Validations.validText(nomA)) {
                                System.out.println("Datos inválidos. Artículo no guardado.");
                                break;
                            }
                            srv.saveArticle(new Article(nomA, p));
                            System.out.println("Artículo guardado.");
                        } else if (a == 2) {
                            for (Article ar : srv.getArticles()) System.out.println(ar.toCSV());
                        } else if (a == 3) {
                            System.out.print("Nombre: ");
                            String nomA = Validations.sanitize(sc.nextLine());
                            Article ar = srv.findArticle(nomA);
                            if (ar != null) {
                                System.out.println("Nombre: " + ar.nombre);
                                System.out.println("Precio: " + ar.precio);
                            } else System.out.println("Artículo no encontrado.");
                        }
                        break;

                    // --- FACTURAS ---
                    case 3:
                        System.out.println("1.Alta 2.Consultar");
                        int fOpt = sc.nextInt(); sc.nextLine();
                        if (fOpt == 1) {
                            System.out.print("DNI Cliente: ");
                            String dniF = Validations.sanitize(sc.nextLine());

                            Client clF = srv.findClient(dniF);
                            if (clF == null) {
                                System.out.println("Cliente no existe. Factura no creada.");
                                break;
                            }

                            System.out.print("IVA (%): ");
                            double iva = sc.nextDouble(); sc.nextLine();
                            if (!Validations.validIVA(iva)) {
                                System.out.println("IVA inválido. Factura no creada.");
                                break;
                            }

                            // Fecha automática
                            String fecha = java.time.LocalDate.now().toString();

                            // ID automático
                            int seq = srv.getNextFacturaSequence(fecha);
                            String idF = fecha.replace("-", "") + "-" + String.format("%03d", seq);

                            Factura fac = new Factura(idF, fecha, dniF, iva);

                            int lineas = 0;
                            while (lineas < 10) {
                                System.out.print("Nombre artículo (enter para terminar): ");
                                String nomArt = Validations.sanitize(sc.nextLine());
                                if (nomArt.isEmpty()) break;

                                Article art = srv.findArticle(nomArt);
                                double precio = (art != null) ? art.precio : 0;

                                System.out.print("Cantidad: ");
                                int q = sc.nextInt(); sc.nextLine();
                                if (!Validations.validCantidad(q)) {
                                    System.out.println("Cantidad inválida. Línea no agregada.");
                                    continue;
                                }

                                fac.lineas.add(new LineaFactura(nomArt, q, precio));
                                lineas++;
                            }

                            srv.saveFactura(fac);
                            System.out.println("Factura guardada.");
                            System.out.println("ID de la factura: " + fac.id);
                            System.out.println("Total: " + fac.total());

                        } else if (fOpt == 2) {
                            System.out.print("ID Factura: ");
                            String id = Validations.sanitize(sc.nextLine());
                            Factura fac = srv.findFactura(id);
                            if (fac != null) {
                                System.out.println("ID: " + fac.id);
                                System.out.println("Fecha: " + fac.fecha);
                                System.out.println("DNI Cliente: " + fac.dniCliente);
                                System.out.println("IVA: " + fac.iva + "%");
                                System.out.println("Líneas:");
                                for (LineaFactura lf : fac.lineas) {
                                    System.out.println("  Artículo: " + lf.nombreArticulo);
                                    System.out.println("  Cantidad: " + lf.cantidad);
                                    System.out.println("  Precio unitario: " + lf.precioUnitario);
                                }
                                System.out.println("Total factura: " + fac.total());
                            } else System.out.println("Factura no encontrada.");
                        }
                        break;

                    // --- VER ARCHIVOS ---
                    case 4:
                        System.out.println("1.Clientes 2.Artículos 3.Facturas");
                        int v = sc.nextInt(); sc.nextLine();
                        switch (v) {
                            case 1:
                                for (Client cli : srv.getClients()) System.out.println(cli.toCSV());
                                break;
                            case 2:
                                for (Article aItem : srv.getArticles()) System.out.println(aItem.toCSV());
                                break;
                            case 3:
                                for (Factura facItem : srv.getFacturas()) {
                                    System.out.println(facItem.toCSV());
                                    for (LineaFactura lf : facItem.lineas)
                                        System.out.println("   " + lf.toCSV(facItem.id));
                                }
                                break;
                            default:
                                System.out.println("Opción inválida");
                        }
                        break;

                    case 5:
                        System.out.println("Saliendo...");
                        return;

                    default:
                        System.out.println("Opción inválida.");
                }
            }
        });

        consoleThread.setDaemon(true);
        consoleThread.start();
    }
}


