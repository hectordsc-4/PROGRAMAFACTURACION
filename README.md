Sistema de Facturación Java
Descripción

Este proyecto es un sistema de facturación desarrollado en Java que permite gestionar clientes, artículos y facturas, tanto desde interfaz gráfica (Swing) como desde menú de consola.
Permite almacenar los datos en archivos CSV para su persistencia y generar facturas con cálculo automático de totales e IVA.

Características principales

Gestión de clientes: alta, listado y consulta por DNI.

Gestión de artículos: alta, listado y consulta por nombre.

Gestión de facturas: creación automática de ID y fecha, hasta 10 líneas de artículos, cálculo de base imponible y total con IVA.

Visualización de archivos: clientes, artículos y facturas completas con líneas y totales.

Validación de datos de entrada (DNI, teléfono, CP, precios, cantidades, IVA).

Persistencia mediante archivos de texto (CSV).

Interfaz gráfica amigable con Swing y menú de consola en hilo separado.

Requisitos

Java 11 o superior.

Sistema operativo compatible con Java (Windows, macOS, Linux).

No requiere base de datos externa, usa archivos en la carpeta data.

Estructura de archivos
project/
│
├─ data/
│  ├─ clients.txt         # Información de clientes
│  ├─ articles.txt        # Información de artículos
│  ├─ facturas.txt        # Información básica de facturas
│  └─ lineas_factura.txt  # Líneas de cada factura
│
├─ model/
│  ├─ Article.java
│  ├─ Client.java
│  ├─ Factura.java
│  └─ LineaFactura.java
│
├─ service/
│  └─ Service.java
│
├─ util/
│  └─ Validations.java
│
├─ ui/
│  └─ AppUI.java
│
└─ Main.java

Instalación y ejecución

Clonar el repositorio:

git clone https://github.com/tuusuario/sistema-facturacion-java.git
cd sistema-facturacion-java


Compilar el proyecto:

javac -d bin Main.java model/*.java service/*.java util/*.java ui/*.java


Ejecutar el programa:

java -cp bin Main


El programa abrirá la interfaz gráfica y simultáneamente estará disponible un menú de consola en segundo plano.

Uso del programa
Interfaz gráfica

Clientes: Alta, listar y consultar.

Artículos: Alta, listar y consultar.

Facturas: Crear nueva factura, consultar por ID.

Ver archivos: Mostrar clientes, artículos y facturas con líneas y totales.

Salir: Cierra el programa.

Menú de consola

Opciones:

1. Clientes
2. Artículos
3. Facturas
4. Ver archivos
5. Salir


Cada opción tiene submenús para alta, listar, consultar o ver información.

Ejemplos

Alta de cliente (consola o GUI):

DNI: 12345678A
Nombre: Juan Pérez
Dirección: Calle Falsa 123
Población: Ciudad
CP: 28001
Provincia: Madrid
Teléfono: 600123456


Alta de artículo:

Nombre: Teclado
Precio: 25.5


Alta de factura:

Seleccionar cliente existente por DNI

Introducir IVA

Añadir hasta 10 artículos con cantidad y precio

Salida de factura:

ID: 20251123-001
Fecha: 2025-11-23
DNI Cliente: 12345678A
IVA: 21%
Líneas:
  Artículo: Teclado, Cantidad: 2, Precio: 25.5
Total: 61.71

Validaciones

DNI: 8 números + 1 letra.

Teléfono: 9 dígitos.

Código postal: 5 dígitos.

Precios: ≥ 0

Cantidad: 1–9999

IVA: 0–100%

Textos: solo letras, espacios y acentos.

Fecha: formato AAAA-MM-DD
