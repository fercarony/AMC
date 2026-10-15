package com.mycompany.practica1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * ============================================================================
 * CLASE: App
 * ============================================================================
 * Punto de entrada del ciclo de vida de la aplicación JavaFX.
 * 
 * Responsabilidades:
 * 1. Hereda de `javafx.application.Application`.
 * 2. Carga el archivo FXML principal (MainView.fxml) que define la interfaz de usuario.
 * 3. Crea la escena (`Scene`) con las dimensiones iniciales recomendadas (1100x760 px).
 * 4. Asigna el título a la ventana principal (`Stage`) y la hace visible con `stage.show()`.
 */
public class App extends Application {

    // Referencia a la escena principal de la aplicación
    private static Scene scene;

    /**
     * Método inicial invocado automáticamente por el framework JavaFX al arrancar.
     * 
     * @param stage La ventana principal (Stage) proporcionada por JavaFX.
     * @throws IOException Si ocurre algún error al localizar o parsear el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargamos la vista FXML que contiene las 3 pestañas y el lienzo de dibujo
        Parent root = loadFXML("/com/mycompany/practica1/view/MainView");
        
        // Creamos la escena con un tamaño amplio para acomodar gráficos y tablas
        scene = new Scene(root, 1100, 760);
        
        stage.setTitle("AMC - Práctica 1: Par de Puntos Más Cercanos");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Permite cambiar la raíz visual de la escena en tiempo de ejecución (navegación entre vistas).
     * 
     * @param fxml Nombre o ruta del archivo FXML sin extensión.
     * @throws IOException Si falla la carga del recurso.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Utilidad auxiliar para instanciar y parsear archivos FXML desde el classpath.
     * 
     * @param fxml Ruta al archivo .fxml.
     * @return El nodo raíz (Parent) del grafo de escena cargado.
     * @throws IOException Si el recurso no existe o contiene errores de sintaxis XML.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        String path = fxml.endsWith(".fxml") ? fxml : fxml + ".fxml";
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
        return fxmlLoader.load();
    }

    /**
     * Método main estándar de Java. Lanza la ejecución de JavaFX.
     */
    public static void main(String[] args) {
        launch();
    }
}