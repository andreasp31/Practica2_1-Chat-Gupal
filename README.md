# Chat Grupal Multihilo - JavaFX & Sockets

Este proyecto es una aplicación de chat cliente-servidor desarrollada en Java.

## Funcionalidades
* **Gestión Multihilo:** Uso de ExecutorService con un pool de hilos  para manejar hasta 4 usuarios en este caso, simultáneos sin que se bloquee.
* **Comunicación en Tiempo Real:** Intercambio de mensajes en ambios sentidos mediante Sockets.
* **Broadcast de Mensajes:** El servidor reenvía automáticamente cada mensaje recibido a todos los usuarios conectados al servidor.
* **Interfaz Gráfica (JavaFX):** Interfaz intuitiva que tiene un panel de acceso para el nombre de usuario y un área de chat común a todos los usuarios.

## Estructura del Proyecto
El proyecto está organizado en las siguientes clases principales:

* **EchoServerMultihilo**: Inicializa el servidor en el puerto 8080 y gestiona el pool de conexiones.
* **ManejadorUsuarioMultihilo**: Clase que procesa la entrada-salida de cada usuario de manera individual.
* **UsuarioInterfaz**: Implementa la lógica del cliente, encargándose de conectar con el host y escuchar mensajes del servidor.
* **HelloController**: Gestiona los eventos de la interfaz FXML, como el envío de mensajes y la conexión inicial del servidor.
* **Launcher**: Punto de entrada que lanza la aplicación JavaFX para su inicialización.

## Utilización
* Java con IntelliJ.
* JavaFX SDK configurado.
* SceneBuilder para el diseño de la vista.

## Instrucciones de Uso
1.  **Ejecución:** Inicia el proyecto a través de la clase Launcher.
2.  **Login:** Al arrancar, el servidor se inicia automáticamente. Y pide introducir un nombre de usuario en la pantalla de bienvenida y pulsa "Entrar".
3.  **Chat:** Escribe tu mensaje en el campo de texto y haz clic en "Enviar". El mensaje aparecerá en las pantallas de todos los usuarios conectados.
4.  **Desconexión:** Pulsa el botón "Salir" para cerrar la conexión en el que cerrará el Socket.
