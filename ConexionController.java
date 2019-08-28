/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class ConexionController {

    private Connection conexion;
    private Statement sentencia;

    private final static String usuario = "root";
    private final static String pass = "";
    private final static String host = "localhost";
    private final static int puerto = 3306;
    private final static String baseDatos = "alquiler_vehiculos";

    private static ConexionController instancia;

    public ConexionController() {
    }

    private ConexionController(String usuario, String pass, String host, int puerto, String baseDatos) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver"); //Cargo el driver de MySQL para permitir la conexión a la BBDD (carpeta lib)
        conexion = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos, usuario, pass); //Con .getConnection realizo la conexión a la BBDD

    }

    //Con este método aplico el patrón Singleton, para evitar que al abrir un programa se me abra de nuevo si ya está abierto
    public static ConexionController getInstancia() throws ClassNotFoundException, SQLException {
        if (instancia == null) {
            instancia = new ConexionController(usuario, pass, host, puerto, baseDatos);
        }
        return instancia;
    }

    public ResultSet ejecutarConsulta(String consulta) throws SQLException { //Método que me permite ejecutar consulta

        sentencia = conexion.createStatement();
        return sentencia.executeQuery(consulta);

    }

    public int ejecutarInstruccion(String instruccion) throws SQLException { //Método que me permite ejecutar instrucciones (insert, update, delete) en la BBDD

        int filas = 0;

        sentencia = conexion.createStatement();
        filas = sentencia.executeUpdate(instruccion);

        return filas;
    }

    public void cerrarSentencia() { //Cierra la sentencia
        try {
            sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarConexion() { //Cierro la conexión con la BBDD
        try {
            if (sentencia != null) {
                cerrarSentencia();
            }
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Añado los getters y setters de los atributos
    public String getUsuario() {
        return usuario;
    }

    public String getPass() {
        return pass;
    }

    public String getHost() {
        return host;
    }

    public int getPuerto() {
        return puerto;
    }

    public String getBaseDatos() {
        return baseDatos;
    }

}