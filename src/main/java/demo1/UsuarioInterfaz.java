package demo1;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UsuarioInterfaz implements Runnable{
    //Atributos del usuario
    private String host;
    private int puerto;
    private TextArea areaChat;
    private PrintWriter salida;
    private Button botonDesconectar;
    private Socket socket;
    private boolean conectado = true;
    private String nombreUsuario;

    //Constructor
    public UsuarioInterfaz(String host, int puerto, TextArea areaChat, Button botonDesconectar, String nombreUsuario ) {
        this.host = host;
        this.puerto = puerto;
        this.areaChat = areaChat;
        this.botonDesconectar = botonDesconectar;
        this.nombreUsuario = nombreUsuario;
    }
    //Función de enviar mensaje
    public void enviarMensaje(String msg) {
        if (salida != null) salida.println(msg);
    }

    public void nombreUsuario(String nombre){

    }

    @Override
    public void run() {
        //Iniciar el socket
        try (Socket socket = new Socket(host, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            salida = new PrintWriter(socket.getOutputStream(), true);

            enviarMensaje(nombreUsuario);

            String respuesta;
            //Mensajes de los usuarios
            while (conectado && (respuesta = entrada.readLine()) != null) {
                String finalRespuesta = respuesta;
                Platform.runLater(() -> areaChat.appendText(finalRespuesta + "\n"));
            }
        } catch (IOException e) {
            //Si hay un error
            Platform.runLater(() -> areaChat.appendText("Desconectado del servidor.\n"));
        }
    }

    //Cerrar el socket para desconectar el puente del cliente
    public void desconectar() {
        conectado = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                Platform.runLater(() -> System.out.println("Se ha desconectado " + nombreUsuario + "\n"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(() ->
                areaChat.appendText("Te has desconectado.\n"));
    }
}