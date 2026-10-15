/**
 * Descriptor de módulo de Java (Java Platform Module System).
 * 
 * ¿Por qué se necesita este archivo?
 * A partir de Java 9 y JavaFX 11+, JavaFX se distribuye de forma modular.
 * Para que JavaFX pueda arrancar y la biblioteca FXML pueda comunicarse con nuestras clases,
 * debemos declarar explícitamente qué dependencias requerimos y qué paquetes abrimos a la reflexión.
 */
module com.mycompany.practica1 {
    
    // Requiere los controles visuales estándar de JavaFX (Button, Label, Canvas, TableView, etc.)
    requires javafx.controls;
    
    // Requiere el cargador y procesador de vistas XML (FXMLLoader)
    requires javafx.fxml;

    // Permite que FXMLLoader acceda e instancie las clases de la aplicación principal
    opens com.mycompany.practica1 to javafx.fxml;
    
    // Permite que FXMLLoader inyecte componentes (@FXML) y vincule métodos en el controlador
    opens com.mycompany.practica1.controller to javafx.fxml;
    
    // Permite que el sistema de propiedades de JavaFX (TableView / TableColumn) acceda a los getters del modelo
    opens com.mycompany.practica1.modelo to javafx.base, javafx.fxml;

    // Exporta los paquetes para que puedan ser utilizados públicamente si el módulo se distribuye
    exports com.mycompany.practica1;
    exports com.mycompany.practica1.modelo;
    exports com.mycompany.practica1.controller;
}
