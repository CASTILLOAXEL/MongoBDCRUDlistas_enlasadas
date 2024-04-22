package org.example.Menu;

import org.example.Telefono.Telefono;
import org.example.TelefonoManager.TelefonoManager;

import java.util.Scanner;
/**
 * La clase Menu proporciona un menú interactivo para que el usuario interactúe con la aplicación de gestión de teléfonos.
 */

public class Menu {
    /**
     * Muestra el menú interactivo y gestiona las opciones seleccionadas por el usuario.
     *
     * @param manager El objeto TelefonoManager para gestionar los teléfonos.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */


    public static void mostrarMenu(TelefonoManager manager, Scanner scanner) {
        boolean continuar = true; // Variable para controlar el bucle del menú
        // Bucle para mostrar el menú y gestionar las opciones del usuario
        while (continuar) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Agregar teléfono");
            System.out.println("2. Mostrar teléfonos");
            System.out.println("3. Actualizar teléfono");
            System.out.println("4. Eliminar teléfono");
            System.out.println("5. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner
           // switch aca se usa para seleccionar la opcion que el usuario desea
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese la marca del teléfono:");
                    String marca = scanner.nextLine();
                    System.out.println("Ingrese el modelo del teléfono:");
                    String modelo = scanner.nextLine();
                    System.out.println("Ingrese el sistema operativo del teléfono:");
                    String sistemaOperativo = scanner.nextLine();
                    System.out.println("Ingrese el tamaño de pantalla del teléfono:");
                    double tamanoPantalla = scanner.nextDouble();
                    System.out.println("Ingrese la memoria RAM del teléfono:");
                    int memoriaRAM = scanner.nextInt();
                    System.out.println("Ingrese el almacenamiento interno del teléfono:");
                    int almacenamientoInterno = scanner.nextInt();
                    System.out.println("¿El teléfono tiene cámara? (si/no):");
                    String tieneCamaraStr = scanner.next();
                    boolean tieneCamara = tieneCamaraStr.equalsIgnoreCase("si");
                    System.out.println("Ingrese la resolución de la cámara del teléfono:");
                    int resolucionCamara = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer del scanner
                    System.out.println("¿El teléfono es un smartphone? (si/no):");
                    String esSmartphoneStr = scanner.nextLine();
                    boolean esSmartphone = esSmartphoneStr.equalsIgnoreCase("si");
                    System.out.println("Ingrese el IMEI del teléfono:");
                    String imei = scanner.nextLine();

                    Telefono nuevoTelefono = new Telefono(marca, modelo, sistemaOperativo, tamanoPantalla,
                            memoriaRAM, almacenamientoInterno, tieneCamara,
                            resolucionCamara, esSmartphone, imei);
                    manager.agregarTelefono(nuevoTelefono);
                    System.out.println("Teléfono agregado exitosamente.");
                    break;
                case 2:
                    manager.mostrarTelefonos();
                    break;
                case 3:
                    System.out.println("Ingrese la marca del teléfono que desea actualizar:");
                    String marcaActualizar = scanner.nextLine();
                    System.out.println("Ingrese el modelo del teléfono que desea actualizar:");
                    String modeloActualizar = scanner.nextLine();

                    // Solicitar los nuevos datos del teléfono
                    System.out.println("Ingrese la nueva marca del teléfono:");
                    String nuevaMarca = scanner.nextLine();
                    System.out.println("Ingrese el nuevo modelo del teléfono:");
                    String nuevoModelo = scanner.nextLine();
                    System.out.println("Ingrese el nuevo sistema operativo del teléfono:");
                    String nuevoSistemaOperativo = scanner.nextLine();
                    System.out.println("Ingrese el nuevo tamaño de pantalla del teléfono:");
                    double nuevoTamanoPantalla = scanner.nextDouble();
                    System.out.println("Ingrese la nueva memoria RAM del teléfono:");
                    int nuevaMemoriaRAM = scanner.nextInt();
                    System.out.println("Ingrese el nuevo almacenamiento interno del teléfono:");
                    int nuevoAlmacenamientoInterno = scanner.nextInt();
                    System.out.println("¿El teléfono tiene nueva cámara? (si/no):");
                    String nuevaTieneCamaraStr = scanner.next();
                    boolean nuevaTieneCamara = nuevaTieneCamaraStr.equalsIgnoreCase("si");
                    System.out.println("Ingrese la nueva resolución de la cámara del teléfono:");
                    int nuevaResolucionCamara = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer del scanner
                    System.out.println("¿El teléfono es un nuevo smartphone? (si/no):");
                    String EsSmartphoneStr = scanner.nextLine();
                    boolean nuevoEsSmartphone = EsSmartphoneStr.equalsIgnoreCase("si");
                    System.out.println("Ingrese el nuevo IMEI del teléfono:");
                    String nuevoImei = scanner.nextLine();

                    // Crear un objeto Telefono con los nuevos datos
                    Telefono telefonoActualizado = new Telefono(nuevaMarca, nuevoModelo, nuevoSistemaOperativo,
                            nuevoTamanoPantalla, nuevaMemoriaRAM,
                            nuevoAlmacenamientoInterno, nuevaTieneCamara,
                            nuevaResolucionCamara, nuevoEsSmartphone, nuevoImei);

                    // Llamar al método de TelefonoManager para actualizar el teléfono
                    manager.actualizarTelefono(marcaActualizar, modeloActualizar, telefonoActualizado);
                    System.out.println("Teléfono actualizado exitosamente.");
                    break;
                case 4:
                    System.out.println("Ingrese la marca del teléfono que desea eliminar:");
                    String marcaEliminar = scanner.nextLine();
                    System.out.println("Ingrese el modelo del teléfono que desea eliminar:");
                    String modeloEliminar = scanner.nextLine();
                    manager.eliminarTelefono(marcaEliminar, modeloEliminar);
                    System.out.println("Teléfono eliminado exitosamente.");
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }

    }
}