package demo1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class EchoServerMultihilo {

    private static final int PUERTO = 8080;
    // número máximo de usuarios que usen el chat al mismo tiempo
    private static final int MAX_CLIENTES = 4;

    // Contador de usuarios
    private static final AtomicInteger contadorUsuarios = new AtomicInteger(0);

    //Lista de todos los usuarios conectados y cada usuario tiene un PrintWriter para enviarle mensajes
    public static final ArrayList<PrintWriter> usuarios = new ArrayList<>();
    //El controlador
    private final HelloController controller;

    //Constructor
    public EchoServerMultihilo(HelloController controller) {
        this.controller = controller;
    }

    public void iniciarServidor() {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTES);

        //Nuevo hilo para iniciar el servidor
        new Thread(() -> {
            //nuevo socket del server
            try(ServerSocket serverSocket = new ServerSocket(PUERTO)) {
                while(true){
                    Socket usuarioSocket = serverSocket.accept();
                    pool.execute(new ManejadorUsuarioMultihilo(usuarioSocket,controller));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }).start();

    }
}