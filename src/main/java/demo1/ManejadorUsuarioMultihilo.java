package demo1;

import javafx.application.Platform;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ManejadorUsuarioMultihilo implements Runnable {
    // Socket para comunicarse con el cliente asignado a este manejador
    private final Socket socket;
    // Identificador simple del usuario;
    private final HelloController controller;
    private String nombreUsuario;

    //Constructor
    public ManejadorUsuarioMultihilo(Socket socket, HelloController controller) {
        this.socket = socket;
        this.controller = controller;
    }


    @Override
    public void run() {
        PrintWriter salida = null;
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            salida = new PrintWriter(socket.getOutputStream(), true);
            EchoServerMultihilo.usuarios.add(salida);

            this.nombreUsuario = entrada.readLine();

            //Mensaje de bienvenida al usuario
            salida.println("Bienvenido usuario: "+ nombreUsuario + " !");
            //Mensaje al chat grupal
            Platform.runLater(() -> System.out.println("\nSe ha unido al chat el usuario " + nombreUsuario));
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                final String msgFinal = mensaje;
                // Mensaje al chat
                Platform.runLater(() -> System.out.println("\n(" + nombreUsuario + ") : " + msgFinal));

                for (PrintWriter cliente : EchoServerMultihilo.usuarios) {
                    cliente.println(" (" + nombreUsuario + ") : " + msgFinal);

                }
                if (mensaje.equalsIgnoreCase("salir")) break;
            }
        } catch (IOException e) {
            Platform.runLater(() -> System.out.println("Error en el usurario de " + nombreUsuario + "\n"));
        } finally {
            if (salida != null) {
                EchoServerMultihilo.usuarios.remove(salida);
            }
            Platform.runLater(() -> System.out.println("Se ha desconectado " + nombreUsuario + "\n"));
            try {
                //Cerrar el socket
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
