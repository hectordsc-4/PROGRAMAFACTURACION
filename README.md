Sistema de Gestión de Clientes, Artículos y Facturas

Este proyecto es un sistema de gestión desarrollado en Java que permite gestionar clientes, artículos y facturas. Ofrece tanto interfaz gráfica (Swing) como menú por consola para realizar operaciones de alta, consulta y listado de registros. Los datos se almacenan en archivos de texto (.txt).

Características

Gestión de Clientes

Alta de nuevos clientes.

Listado completo de clientes.

Consulta de clientes por DNI.

Gestión de Artículos

Alta de nuevos artículos con nombre y precio.

Listado completo de artículos.

Consulta de artículos por nombre.

Gestión de Facturas

Alta de facturas con fecha automática, ID secuencial y líneas de artículos.

Consulta de facturas por ID.

Cálculo automático de total incluyendo IVA.

Ver archivos

Muestra todos los clientes, artículos y facturas en un formato legible.

Validaciones

DNI, teléfono, código postal, IVA, precios y cantidades validadas.

Sanitización de entradas para evitar caracteres problemáticos.

Estructura del proyecto
/src
  /main
    Main.java                  # Clase principal, arranca UI y menú consola
  /model
    Article.java               # Modelo de artículo
    Client.java                # Modelo de cliente
    Factura.java               # Modelo de factura
    LineaFactura.java          # Línea de factura
  /service
    Service.java               # Lógica de acceso a archivos y gestión de datos
  /ui
    AppUI.java                 # Interfaz gráfica con Swing
  /util
    Validations.java           # Funciones de validación y sanitización
/data
  clients.txt                  # Datos de clientes
  articles.txt                 # Datos de artículos
  facturas.txt                 # Datos de facturas
  lineas_factura.txt           # Líneas de facturas

Dependencias

Java 8 o superior.

No requiere librerías externas.

Uso
Interfaz gráfica (Swing)

Ejecutar Main.java.

Aparecerá una ventana con botones para:

Clientes

Artículos

Facturas

Ver Archivos

Salir

Seleccionar la opción deseada y seguir las instrucciones en ventanas emergentes.

Cada apartado tiene un botón Info que muestra información contextual.

Menú por consola

Ejecutar Main.java.

Se mostrará un menú con opciones numeradas:

1. Clientes
2. Artículos
3. Facturas
4. Ver archivos
5. Salir


Ingresar el número de la opción y seguir las instrucciones.

Formato de almacenamiento

Clientes (clients.txt):
DNI;Nombre;Dirección;Población;CP;Provincia;Teléfono

Artículos (articles.txt):
Nombre;Precio

Facturas (facturas.txt):
ID;Fecha;DNI Cliente;IVA

Líneas de factura (lineas_factura.txt):
ID Factura;Cantidad;Nombre Artículo;Precio Unitario

Validaciones principales

DNI: 8 números + letra.

Teléfono: 9 dígitos.

CP: 5 dígitos.

Precio: ≥ 0.

Cantidad: 1–9999.

IVA: 0–100%.

Texto: solo letras, espacios y acentos.

Fecha: formato AAAA-MM-DD.
