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
public class Coche {

    private String matricula;
    private String descripcion;
    private int klm;
    private String marca;
    private char combustible;
    private boolean ocupado;
    private int precio;

    public Coche() {
    }

    public Coche(String descripcion, int klm, String marca, int precio) {
        this.descripcion = descripcion;
        this.klm = klm;
        this.marca = marca;
        this.precio = precio;
    }

    public Coche(String matricula, String descripcion, int klm, String marca, char combustible, boolean ocupado, int precio) {
        this.matricula = matricula;
        this.descripcion = descripcion;
        this.klm = klm;
        this.marca = marca;
        this.combustible = combustible;
        this.ocupado = ocupado;
        this.precio = precio;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getKlm() {
        return klm;
    }

    public void setKlm(int klm) {
        this.klm = klm;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public char getCombustible() {
        return combustible;
    }

    public void setCombustible(char combustible) {
        this.combustible = combustible;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return matricula;
    }

    public ObservableList<Coche> cargarCoche() throws ClassNotFoundException, SQLException { //Con este método devuelvo un ObservableList, que voy a necesitar 
        //para meter los datos en el Combobox correspondiente

        //Primero cojo los datos
        ConexionController c = ConexionController.getInstancia();

        ObservableList<Coche> obcoche = FXCollections.observableArrayList();//Crear ArrayList

        String SQL = "select matricula, descripcion, klm, marca, combustible, ocupado, precio " //Hago la consulta
                + "FROM coches "
                + "WHERE ocupado=0 ";

        ResultSet rs = c.ejecutarConsulta(SQL); //Almaceno los datos en el ResultSet

        while (rs.next()) { //Cojo los datos
            String matricula = rs.getString("matricula");
            String descripcion = rs.getString("descripcion");
            int klm = rs.getInt("klm");
            String marca = rs.getString("marca");
            char combustible = rs.getString("combustible").charAt(0);
            if (rs.getInt("ocupado") == 1) {
                ocupado = true;
            }
            int precio = rs.getInt("precio");

            Coche coche = new Coche(matricula, descripcion, klm, marca, combustible, ocupado, precio); //Creo un objeto para meterle los datos
            obcoche.add(coche); //Ahora ese objeto lo meto en el ObservableList

        }
        return obcoche; //Devuelvo el ObservableList

    }

    public boolean actualizarOcupado() throws ClassNotFoundException, SQLException {
        ConexionController c = ConexionController.getInstancia();

        String SQL = "UPDATE coches SET ocupado=1 WHERE matricula='" + this.getMatricula() + "'"; //Hago el UPDATE
        int filas = c.ejecutarInstruccion(SQL); //Ejecutar instrucción te devuelve el número de filas que se han actualizado

        if (filas == 0) {
            return false;
        } else {
            return true;
        }

    }

}
