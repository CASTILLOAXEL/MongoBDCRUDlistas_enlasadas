package org.example;

import org.example.Menu.Menu;
import org.example.Telefono.Telefono;
import org.example.TelefonoManager.TelefonoManager;

import java.util.Scanner;
//

import org.example.Telefono.Telefono;
import org.example.TelefonoManager.TelefonoManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TelefonoManager manager = new TelefonoManager();
        Scanner scanner = new Scanner(System.in);
        Menu.mostrarMenu(manager, scanner);

        scanner.close();
    }
}
