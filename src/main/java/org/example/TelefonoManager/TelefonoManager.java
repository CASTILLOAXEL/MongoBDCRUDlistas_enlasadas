package org.example.TelefonoManager;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.Nodo.Nodo;
import org.example.Telefono.Telefono;

public class TelefonoManager {
    private Nodo cabeza;
    private final MongoCollection<Document> collection;

    public TelefonoManager() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("telefonos");
        collection = database.getCollection("telefonos");
    }

    public void agregarTelefono(Telefono telefono) {
        Nodo nuevoNodo = new Nodo(telefono);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }

        // Insertar en MongoDB
        Document doc = new Document("marca", telefono.getMarca())
                .append("modelo", telefono.getModelo())
                .append("sistemaOperativo", telefono.getSistemaOperativo())
                .append("tamanoPantalla", telefono.getTamanoPantalla())
                .append("memoriaRAM", telefono.getMemoriaRAM())
                .append("almacenamientoInterno", telefono.getAlmacenamientoInterno())
                .append("tieneCamara", telefono.isTieneCamara())
                .append("resolucionCamara", telefono.getResolucionCamara())
                .append("esSmartphone", telefono.isEsSmartphone() ? "si" : "no")
                .append("imei", telefono.getImei());
        collection.insertOne(doc);
    }

    public void mostrarTelefonos() {
        if (cabeza == null) {
            System.out.println("No hay teléfonos para mostrar.");
        } else {
            System.out.println("Lista de teléfonos:");
            Nodo actual = cabeza;
            while (actual != null) {
                System.out.println(actual.getTelefono());
                actual = actual.getSiguiente();
            }
        }

        // Mostrar desde MongoDB
        for (Document doc : collection.find()) {
            String esSmartphone = doc.getString("esSmartphone").equals("si") ? "Sí" : "No";
            System.out.println("Teléfono en MongoDB: " + doc.getString("marca") + " " + doc.getString("modelo") +
                    ", Es smartphone: " + esSmartphone);
        }
    }

    public void actualizarTelefono(String marca, String modelo, Telefono telefonoNuevo) {
        // Actualizar en lista enlazada
        Nodo actual = cabeza;
        while (actual != null) {
            Telefono telefono = actual.getTelefono();
            if (telefono.getMarca().equals(marca) && telefono.getModelo().equals(modelo)) {
                actual.setTelefono(telefonoNuevo);
                break;
            }
            actual = actual.getSiguiente();
        }

        // Actualizar en MongoDB
        collection.updateOne(Filters.and(Filters.eq("marca", marca), Filters.eq("modelo", modelo)),
                new Document("$set", new Document("marca", telefonoNuevo.getMarca())
                        .append("modelo", telefonoNuevo.getModelo())
                        .append("sistemaOperativo", telefonoNuevo.getSistemaOperativo())
                        .append("tamanoPantalla", telefonoNuevo.getTamanoPantalla())
                        .append("memoriaRAM", telefonoNuevo.getMemoriaRAM())
                        .append("almacenamientoInterno", telefonoNuevo.getAlmacenamientoInterno())
                        .append("tieneCamara", telefonoNuevo.isTieneCamara())
                        .append("resolucionCamara", telefonoNuevo.getResolucionCamara())
                        .append("esSmartphone", telefonoNuevo.isEsSmartphone() ? "si" : "no")
                        .append("imei", telefonoNuevo.getImei())));
    }

    public void eliminarTelefono(String marca, String modelo) {
        // Eliminar en lista enlazada
        if (cabeza == null) {
            System.out.println("La lista de teléfonos está vacía.");
            return;
        }

        if (cabeza.getTelefono().getMarca().equals(marca) && cabeza.getTelefono().getModelo().equals(modelo)) {
            cabeza = cabeza.getSiguiente();
            System.out.println("Teléfono eliminado de la lista enlazada.");
            // Eliminar en MongoDB
            collection.deleteOne(Filters.and(Filters.eq("marca", marca), Filters.eq("modelo", modelo)));
            return;
        }

        Nodo anterior = cabeza;
        Nodo actual = cabeza.getSiguiente();
        while (actual != null) {
            Telefono telefono = actual.getTelefono();
            if (telefono.getMarca().equals(marca) && telefono.getModelo().equals(modelo)) {
                anterior.setSiguiente(actual.getSiguiente());
                System.out.println("Teléfono eliminado de la lista enlazada.");
                // Eliminar en MongoDB
                collection.deleteOne(Filters.and(Filters.eq("marca", marca), Filters.eq("modelo", modelo)));
                return;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }

        System.out.println("El teléfono no se encontró en la lista enlazada.");
    }
}




//import com.mongodb.ConnectionString;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import org.bson.Document;
//import org.example.Telefono.Telefono;
//
//import java.util.LinkedList;
//
//public class TelefonoManager {
//    private LinkedList<Telefono> listaTelefonos;
//    private final MongoCollection<Document> collection;
//
//
//    public TelefonoManager() {
//        listaTelefonos = new LinkedList<>();
//        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
//        MongoClient mongoClient = MongoClients.create(connectionString);
//        MongoDatabase database = mongoClient.getDatabase("telefonoss");
//        collection = database.getCollection("telefonos");
//    }
//
//
//
//    public void agregarTelefono(Telefono telefono) {
//
//        listaTelefonos.add(telefono);
//        // Insertar en MongoDB
//        Document doc = new Document("marca", telefono.getMarca())
//                .append("modelo", telefono.getModelo())
//                .append("sistemaOperativo", telefono.getSistemaOperativo())
//                .append("tamanoPantalla", telefono.getTamanoPantalla())
//                .append("memoriaRAM", telefono.getMemoriaRAM())
//                .append("almacenamientoInterno", telefono.getAlmacenamientoInterno())
//                .append("tieneCamara", telefono.isTieneCamara())
//                .append("resolucionCamara", telefono.getResolucionCamara())
//                .append("esSmartphone", telefono.isEsSmartphone())
//                .append("imei", telefono.getImei());
//        collection.insertOne(doc);
//    }
//
//    public void mostrarTelefonos() {
//        if (listaTelefonos.isEmpty()) {
//            System.out.println("No hay teléfonos para mostrar.");
//        } else {
//            System.out.println("Lista de teléfonos:");
//            for (Telefono telefono : listaTelefonos) {
//                System.out.println(telefono);
//            }
//        }
//
//        // Mostrar desde MongoDB
//        for (Document doc : collection.find()) {
//            System.out.println("Teléfono en MongoDB: " + doc.getString("marca") + " " + doc.getString("modelo"));
//        }
//    }
//    public void actualizarTelefono(String marca, String modelo, Telefono telefonoNuevo) {
//        // Verificar si el teléfono existe en la lista y actualizarlo si es así
//        for (int i = 0; i < listaTelefonos.size(); i++) {
//            Telefono telefono = listaTelefonos.get(i);
//            if (telefono.getMarca().equals(marca) && telefono.getModelo().equals(modelo)) {
//                listaTelefonos.set(i, telefonoNuevo);
//                break;
//            }
//        }
//
//        // Actualizar en MongoDB
//        collection.updateOne(Filters.and(Filters.eq("marca", marca), Filters.eq("modelo", modelo)),
//                new Document("$set", new Document("marca", telefonoNuevo.getMarca())
//                        .append("modelo", telefonoNuevo.getModelo())
//                        .append("sistemaOperativo", telefonoNuevo.getSistemaOperativo())
//                        .append("tamanoPantalla", telefonoNuevo.getTamanoPantalla())
//                        .append("memoriaRAM", telefonoNuevo.getMemoriaRAM())
//                        .append("almacenamientoInterno", telefonoNuevo.getAlmacenamientoInterno())
//                        .append("tieneCamara", telefonoNuevo.isTieneCamara())
//                        .append("resolucionCamara", telefonoNuevo.getResolucionCamara())
//                        .append("esSmartphone", telefonoNuevo.isEsSmartphone())
//                        .append("imei", telefonoNuevo.getImei())));
//    }
//
//
//
//    public void eliminarTelefono(String marca, String modelo) {
//        for (int i = 0; i < listaTelefonos.size(); i++) {
//            Telefono telefono = listaTelefonos.get(i);
//            if (telefono.getMarca().equals(marca) && telefono.getModelo().equals(modelo)) {
//                listaTelefonos.remove(i);
//                break;
//            }
//        }
//
//        // Eliminar en MongoDB
//        collection.deleteOne(Filters.and(Filters.eq("marca", marca), Filters.eq("modelo", modelo)));
//    }
//}


//import com.mongodb.ConnectionString;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import org.bson.Document;
//import org.example.Nodo.Nodo;
//import org.example.Telefono.Telefono;
//
//public class TelefonoManager {
//    private Nodo cabeza;
//    private final MongoCollection<Document> collection;
//
//    public TelefonoManager() {
//        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
//        MongoClient mongoClient = MongoClients.create(connectionString);
//        MongoDatabase database = mongoClient.getDatabase("telefonoDB");
//        collection = database.getCollection("telefonos");
//    }
//
//    public void agregarTelefono(Telefono telefono) {
//        Nodo nuevoNodo = new Nodo(telefono);
//        if (cabeza == null) {
//            cabeza = nuevoNodo;
//        } else {
//            Nodo actual = cabeza;
//            while (actual.getSiguiente() != null) {
//                actual = actual.getSiguiente();
//            }
//            actual.setSiguiente(nuevoNodo);
//        }
//
//        // Insertar en MongoDB
//        Document doc = new Document("marca", telefono.getMarca())
//                .append("modelo", telefono.getModelo())
//                .append("sistemaOperativo", telefono.getSistemaOperativo())
//                .append("tamanoPantalla", telefono.getTamanoPantalla())
//                .append("memoriaRAM", telefono.getMemoriaRAM())
//                .append("almacenamientoInterno", telefono.getAlmacenamientoInterno())
//                .append("tieneCamara", telefono.isTieneCamara())
//                .append("resolucionCamara", telefono.getResolucionCamara())
//                .append("esSmartphone", telefono.isEsSmartphone())
//                .append("imei", telefono.getImei());
//        collection.insertOne(doc);
//    }
//
//    public void mostrarTelefonos() {
//        if (cabeza == null) {
//            System.out.println("No hay teléfonos para mostrar.");
//        } else {
//            System.out.println("Lista de teléfonos:");
//            Nodo actual = cabeza;
//            while (actual != null) {
//                System.out.println(actual.getTelefono());
//                actual = actual.getSiguiente();
//            }
//        }
//
//        // Mostrar desde MongoDB
//        for (Document doc : collection.find()) {
//            System.out.println("Teléfono en MongoDB: " + doc.getString("marca") + " " + doc.getString("modelo"));
//        }
//    }
//
//    public void actualizarTelefono(String marca, String modelo, Telefono telefonoNuevo) {
//        // Actualizar en lista enlazada
//        Nodo actual = cabeza;
//        while (actual != null) {
//            Telefono telefono = actual.getTelefono();
//            if (telefono.getMarca().equals(marca) && telefono.getModelo().equals(modelo)) {
//                actual.setTelefono(telefonoNuevo);
//                break;
//            }
//            actual = actual.getSiguiente();
//        }
//
//        // Actualizar en MongoDB
//        collection.updateOne(Filters.and(Filters.eq("marca", marca), Filters.eq("modelo", modelo)),
//                new Document("$set", new Document("marca", telefonoNuevo.getMarca())
//                        .append("modelo", telefonoNuevo.getModelo())
//                        .append("sistemaOperativo", telefonoNuevo.getSistemaOperativo())
//                        .append("tamanoPantalla", telefonoNuevo.getTamanoPantalla())
//                        .append("memoriaRAM", telefonoNuevo.getMemoriaRAM())
//                        .append("almacenamientoInterno", telefonoNuevo.getAlmacenamientoInterno())
//                        .append("tieneCamara", telefonoNuevo.isTieneCamara())
//                        .append("resolucionCamara", telefonoNuevo.getResolucionCamara())
//                        .append("esSmartphone", telefonoNuevo.isEsSmartphone())
//                        .append("imei", telefonoNuevo.getImei())));
//
//    }
//
//    public void eliminarTelefono(String marca, String modelo) {
//        // Eliminar en lista enlazada
//        if (cabeza == null) {
//            System.out.println("La lista de teléfonos está vacía.");
//            return;
//        }
//
//        if (cabeza.getTelefono().getMarca().equals(marca) && cabeza.getTelefono().getModelo().equals(modelo)) {
//            cabeza = cabeza.getSiguiente();
//            System.out.println("Teléfono eliminado de la lista enlazada.");
//            // Eliminar en MongoDB
//            collection.deleteOne(Filters.and(Filters.eq("marca", marca), Filters.eq("modelo", modelo)));
//            return;
//        }
//
//        Nodo anterior = cabeza;
//        Nodo actual = cabeza.getSiguiente();
//        while (actual != null) {
//            Telefono telefono = actual.getTelefono();
//            if (telefono.getMarca().equals(marca) && telefono.getModelo().equals(modelo)) {
//                anterior.setSiguiente(actual.getSiguiente());
//                System.out.println("Teléfono eliminado de la lista enlazada.");
//                // Eliminar en MongoDB
//                collection.deleteOne(Filters.and(Filters.eq("marca", marca), Filters.eq("modelo", modelo)));
//                return;
//            }
//            anterior = actual;
//            actual = actual.getSiguiente();
//        }
//
//        System.out.println("El teléfono no se encontró en la lista enlazada.");
//    }
//
//
//}
