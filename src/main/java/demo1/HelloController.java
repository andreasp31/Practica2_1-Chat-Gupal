package demo1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.sql.DriverManager.println;

public class HelloController {
    @FXML
    private Button botonEnviar1;
    @FXML
    private TextField mensajeU1;
    @FXML
    private TextArea chat1;
    @FXML
    public Button botonDesconectar1;
    @FXML
    public Pane pantallaNombre;
    @FXML
    public TextField nombreUsuario;
    @FXML
    public Button botonEntrar;

    @FXML
    public void initialize(){
        //Iniciar pool para clientes
        ExecutorService poolUsuarios = Executors.newFixedThreadPool(4);
        iniciarServidor();
        botonEntrar.setOnAction(evento -> {
            String nombreUsuarioFinal = nombreUsuario.getText();
            //Antes de iniciar el servidor los botones de los usuarios debería estar desactivados
            configurarUsuario(nombreUsuarioFinal,botonEnviar1,mensajeU1,chat1,botonDesconectar1);
            pantallaNombre.setVisible(false);
            pantallaNombre.setDisable(true);
        });
    }

    public void configurarUsuario(String nombreUsuarioFinal, Button botonEnviar, TextField campoMensaje, TextArea zonaChat, Button botonDesconectar){
        //Lista de los usuarios que se le pasan a la clase UsuarioInterfaz
        final UsuarioInterfaz[] usuario = {null};
        usuario[0] = new UsuarioInterfaz("localhost",8080,zonaChat,botonDesconectar,nombreUsuarioFinal);
        //Iniciamos un hilo de ese usuario
        new Thread(usuario[0]).start();
        //Primero escribimos un mensaje que si es nulo no hace nada, y ese mensaje se envia
        botonEnviar.setOnAction(evento -> {
            if(usuario[0] != null){
                usuario[0].enviarMensaje(campoMensaje.getText());
                campoMensaje.clear();
            }
        });
        //Al darle a desconectar, se desconecta en la clase de UusarioInterfaz
        botonDesconectar.setOnAction(evento -> {
            if(usuario[0] != null){
                usuario[0].desconectar();
                println("\nUsuario desconectado");
                //Y se cambia los datos de los botones
                botonDesconectar.setText("Ha salido");
            }
        });
    }
    //Función para iniciar el servidor
    public void iniciarServidor(){
        //Para iniciar el servidor
        EchoServerMultihilo servidor = new EchoServerMultihilo(this);
        servidor.iniciarServidor();
        //Esto aparece en el panel del chat grupal
        println("Servidor Iniciado");
        //Activar todos los botones de los usuarios
        botonEnviar1.setDisable(false);

    }
}
