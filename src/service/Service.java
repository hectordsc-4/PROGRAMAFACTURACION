package service;

import model.*;
import util.Validations;

import java.io.*;
import java.util.*;

public class Service {
    private final String pathClients="data/clients.txt";
    private final String pathArticles="data/articles.txt";
    private final String pathFacturas="data/facturas.txt";
    private final String pathLineas="data/lineas_factura.txt";

    public Service() {
        File folder = new File("data");
        if (!folder.exists()) folder.mkdirs();
    }

    // --- CLIENTES ---
    public List<Client> getClients() {
        List<Client> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathClients))) {
            String line;
            while((line = br.readLine()) != null)
                list.add(Client.fromCSV(line));
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return list;
    }

    public void saveClient(Client c) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathClients,true))) {
            bw.write(c.toCSV());
            bw.newLine();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public Client findClient(String dni){
        dni = Validations.sanitize(dni);
        for(Client c:getClients()) if(c.dni.equalsIgnoreCase(dni)) return c;
        return null;
    }

    // --- ARTICULOS ---
    public List<Article> getArticles(){
        List<Article> list=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(pathArticles))){
            String line;
            while((line=br.readLine())!=null) list.add(Article.fromCSV(line));
        }catch(Exception e){ System.out.println(e.getMessage()); }
        return list;
    }

    public void saveArticle(Article a){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(pathArticles,true))){
            bw.write(a.toCSV());
            bw.newLine();
        }catch(Exception e){ System.out.println(e.getMessage()); }
    }

    public Article findArticle(String nombre){
        nombre = Validations.sanitize(nombre);
        for(Article a:getArticles()) if(a.nombre.equalsIgnoreCase(nombre)) return a;
        return null;
    }

    // --- FACTURAS ---
    // --- FACTURAS ---
    public List<Factura> getFacturas() {
        List<Factura> list = new ArrayList<>();
        Map<String, Factura> map = new HashMap<>();

        // Cargar facturas
        try (BufferedReader br = new BufferedReader(new FileReader(pathFacturas))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length < 4) continue; // evitar líneas corruptas
                String id = p[0];
                String fecha = p[1];
                String dniCliente = p[2];
                double iva = Double.parseDouble(p[3]);
                Factura f = new Factura(id, fecha, dniCliente, iva);
                list.add(f);
                map.put(id, f); // para asociar líneas
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Cargar líneas
        try (BufferedReader br = new BufferedReader(new FileReader(pathLineas))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length < 4) continue;
                String facturaId = p[0];
                int cantidad = Integer.parseInt(p[1]);
                String nombreArticulo = p[2];
                double precioUnitario = Double.parseDouble(p[3]);

                LineaFactura l = new LineaFactura(nombreArticulo, cantidad, precioUnitario);
                if (map.containsKey(facturaId)) {
                    map.get(facturaId).lineas.add(l);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    // Buscar factura por ID
    public Factura findFactura(String id) {
        id = Validations.sanitize(id);
        for (Factura f : getFacturas()) {
            if (f.id.equalsIgnoreCase(id)) return f;
        }
        return null;
    }

    // Guardar factura y sus líneas
    public void saveFactura(Factura f) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathFacturas, true))) {
            bw.write(f.toCSV());
            bw.newLine();
        } catch (Exception e) { System.out.println(e.getMessage()); }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathLineas, true))) {
            for (LineaFactura l : f.lineas) {
                bw.write(l.toCSV(f.id));
                bw.newLine();
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    // Secuencia automática para ID
    public int getNextFacturaSequence(String fecha) {
        int max = 0;
        List<Factura> facs = getFacturas();
        for (Factura f : facs) {
            if (f.fecha.equals(fecha)) {
                try {
                    String[] parts = f.id.split("-");
                    int num = Integer.parseInt(parts[1]);
                    if (num > max) max = num;
                } catch (Exception ignored) {}
            }
        }
        return max + 1;
    }

}


