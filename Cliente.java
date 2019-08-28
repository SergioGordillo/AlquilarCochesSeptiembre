/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ConexionController;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sergio
 */
public class Cliente {

    private String nif;
    private String nombre;
    private String direccion;
    private String poblacion;
    private String cpostal;
    private String email;

    public Cliente() {
    }

    public Cliente(String nif, String direccion, String poblacion) {
        this.nif = nif;
        this.direccion = direccion;
        this.poblacion = poblacion;
    }

    public Cliente(String nif, String nombre, String direccion, String poblacion, String cpostal, String email) {
        this.nif = nif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.cpostal = cpostal;
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getCpostal() {
        return cpostal;
    }

    public void setCpostal(String cpostal) {
        this.cpostal = cpostal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    

    public ObservableList<Cliente> cargarCliente() throws ClassNotFoundException, SQLException { //Con este método devuelvo un ObservableList, que voy a necesitar 
        //para meter los datos en el Combobox correspondiente

        //Primero cojo los datos
        ConexionController c = ConexionController.getInstancia();

        ObservableList<Cliente> obcliente = FXCollections.observableArrayList();//Crear ArrayList

        String SQL = "select nif, nombre, direccion, poblacion, cpostal, email " //Hago la consulta
                + "FROM clientes ";

        ResultSet rs = c.ejecutarConsulta(SQL); //Almaceno los datos en el ResultSet

        while (rs.next()) { //Cojo los datos
            String nif=rs.getString("nif");
            String nombre=rs.getString("nombre");
            String direccion=rs.getString("direccion");
            String poblacion=rs.getString("poblacion");
            String cpostal=rs.getString("cpostal");
            String email=rs.getString("email");

            Cliente cliente = new Cliente(nif, nombre, direccion, poblacion, cpostal, email); //Creo un objeto para meterle los datos
            obcliente.add(cliente); //Ahora ese objeto lo meto en el ObservableList

        }
        return obcliente; //Devuelvo el ObservableList

    }
}
